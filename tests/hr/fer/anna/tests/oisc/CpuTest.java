package hr.fer.anna.tests.oisc;

import java.util.Random;

import hr.fer.anna.exceptions.BusAddressTaken;
import hr.fer.anna.exceptions.UnknownAddressException;
import hr.fer.anna.interfaces.IEventListener;
import hr.fer.anna.interfaces.IEventSetter;
import hr.fer.anna.oisc.Assembler;
import hr.fer.anna.oisc.Cpu;
import hr.fer.anna.simulator.Simulator;
import hr.fer.anna.uniform.Address;
import hr.fer.anna.uniform.Constant;
import hr.fer.anna.uniform.PlainBus;
import hr.fer.anna.uniform.SimpleMemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testovi procesora, njegovog načina izvršavanja. Simuliranje samog rada procesora.
 * @author Boran
 *
 */
public class CpuTest {

	/** Veličina memorije (u bajtovima) */
	private static final int MEMORY_SIZE = 1024;

	/** Instanca procesora koja će se testirati */
	private Cpu cpu;
	
	/** Bus koji je spojen na procesor */
	private PlainBus bus;
	
	/** Memorija koja je spojena na bus */
	private SimpleMemory memory;
	
	/** Početno vrijeme simulacije */
	private long startingSimulationTime;
	
	/** Simulator koji koristimo prilikom simulacije */
	private Simulator simulator;
	
	/** Generator slučajnih brojeva */
	private Random random;
	
	@Before
	public void setUp() {
		this.bus = new PlainBus();
		this.cpu = new Cpu(this.bus);
		this.memory = new SimpleMemory(MEMORY_SIZE, 32);
		
		try {
			this.bus.registerBusUnit(this.memory, Address.fromWord(new Constant(0)));
		} catch (UnknownAddressException ignorable) {
		} catch (BusAddressTaken ignorable) {
		}
		
		this.simulator = new Simulator();
		this.startingSimulationTime = simulator.getSimulatorTime();
		
		simulator.registerEventSetters(new IEventSetter[] {this.cpu, this.memory});
		simulator.registerEventListeners(new IEventListener[] {this.cpu, this.memory});
	
		random = new Random();
	}
	
	/**
	 * Testira zbrajanje dvaju vrijednosti.
	 * 0: ADD a,b koji zbraja a i b i rezultat stavlja u b prelazi u:
	 * 0: SUBNEG a Z 4
	 * 4: SUBNEG Z b 8
	 * 8: SUBNEG Z Z 12
	 * 
	 * Z je na lokaciji 1000 i mora biti vrijednosti 0
	 * a je na lokaciji 1004
	 * b je na lokaciji 1008
	 */
	@Test
	public void simpleAddTest() {
		final int a = 409658; //random.nextInt(1000000);
		final int b = 945509; //random.nextInt(1000000);
		
		try {
			this.memory.write(Assembler.assemble(
					"SUBNEG 1001 1000 1\n" +
					"SUBNEG 1000 1002 2\n" +
					"SUBNEG 1000 1000 3\n"
					),
					Address.fromWord(new Constant(0))
					);
			
			// halt
			this.memory.write(new Constant(0), Address.fromWord(new Constant(4)));

			this.memory.write(new Constant(0), Address.fromWord(new Constant(1000)));
			this.memory.write(new Constant(a), Address.fromWord(new Constant(1001)));
			this.memory.write(new Constant(b), Address.fromWord(new Constant(1002)));
			
			simulator.run();
			
			Assert.assertEquals("Zbroj nije točan!", Integer.toHexString(a+b).toUpperCase(), memory.read(Address.fromWord(new Constant(1002))).getHexString());
			Assert.assertEquals("Trajanje simulacije neispravno!", startingSimulationTime + 24L + 2L, simulator.getSimulatorTime());
		} catch (Exception e){
			Assert.fail("Dogodila se neočekivana greška: " + e.getMessage());
		}
	}
	
	/**
	 * Testira bezuvjetno skakanje na određenu adresu.
	 * 0: JMP c skoči na adresu c prelazi u:
	 * 0: SUBNEG POS Z c
	 * ...
	 * c: SUBNEG Z Z
	 * 
	 * Z je na lokaciji 1000 i iznosi 0
	 * POS je na lokaciji 1004 i to je neki pozitivan broj
	 * c je neka proizvoljna lokacija od 4 do 996
	 */
	@Test
	public void unconditionalJumpTest() {
		final int pos = random.nextInt(1000000);
		final int c = random.nextInt(MEMORY_SIZE);
		
		try {
			this.memory.write(
					Assembler.assemble("SUBNEG 1001 1000 " + c + "\n"),
					Address.fromWord(new Constant(0))
					);
			//halt
			this.memory.write(new Constant(0), Address.fromWord(new Constant(1)));
			
			this.memory.write(new Constant(0), Address.fromWord(new Constant(1000)));
			this.memory.write(new Constant(pos), Address.fromWord(new Constant(1001)));

			simulator.run();
			
			Assert.assertEquals("Neispravna vrijednost na lokaciji 1000!", Integer.toHexString(-pos).toUpperCase(), memory.read(Address.fromWord(new Constant(1000))).toString());
			Assert.assertEquals("Neispravan skok!", Integer.toHexString(c+1).toUpperCase(), this.cpu.describe().getRegister("PC").getHexString());
			Assert.assertEquals("Trajanje simulacije neispravno!", startingSimulationTime + 8L + 2L, simulator.getSimulatorTime());		
		} catch (Exception e){
			Assert.fail("Dogodila se neočekivana greška: " + e.getMessage());
		}
	}
}
