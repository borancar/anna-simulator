package hr.fer.anna.tests.uniform;

import java.util.Random;

import hr.fer.anna.exceptions.IllegalActionException;
import hr.fer.anna.exceptions.SimulationException;
import hr.fer.anna.exceptions.UnknownAddressException;
import hr.fer.anna.interfaces.IInternalDebugger;
import hr.fer.anna.model.Word;
import hr.fer.anna.simulator.Simulator;
import hr.fer.anna.uniform.Address;
import hr.fer.anna.uniform.Constant;
import hr.fer.anna.uniform.SimpleMemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testira SimpleMemory - jednostavnu memoriju
 * @author Boran
 *
 */
public class SimpleMemoryTest {
	
	/**
	 * Memorija koju testiramo
	 */
	private SimpleMemory memory;
	
	/**
	 * Sabirnica koja nam služi za testiranje
	 */
	private BusForTesting busForTesting;
	
	/**
	 * Veličina memorije (u riječima)
	 */
	private static int MEMORY_SIZE = 1024;
	
	/**
	 * Širina riječi memorije
	 */
	private static int WORD_WIDTH = 8;
	
	/**
	 * Broj testova po slučaju testiranja
	 */
	private static int NUMBER_OF_TESTS = 84000;
	
	/**
	 * Simulator koji obavlja simulaciju
	 */
	private Simulator simulator;
	
	/**
	 * Generator slučajnih brojeva
	 */
	private Random random;
	
	/**
	 * Testira odašiljanje IllegalActionExceptiona prilikom obavljanja operacija gdje
	 * se očekuje ta iznimka.
	 */
	private static IInternalDebugger acceptOnlyIllegalActionException = new IInternalDebugger() {
	
		public boolean reportException(SimulationException simulationException) {
			Assert.assertEquals("Nedopuštena iznimka!", IllegalActionException.class, simulationException.getClass());
			
			return false;
		}
	};
	
	/**
	 * Proglašava test neuspjelim ako se dogodi ikakva iznimka tokom simulacije.
	 */
	private static IInternalDebugger noAllowedExceptions = new IInternalDebugger() {
		
		public boolean reportException(SimulationException simulationException) {
			Assert.fail("Nedopuštena iznimka: " + simulationException.getMessage());
			
			return false;
		}
	};

	
	/**
	 * Inicijalizira sve potrebno za pojedini test
	 *
	 */
	@Before
	public void init() {
		this.simulator = new Simulator();
		this.memory = new SimpleMemory(MEMORY_SIZE, WORD_WIDTH);
		this.simulator.registerEventListener(this.memory);
		this.simulator.registerEventSetter(this.memory);
		this.random = new Random();
		this.busForTesting = new BusForTesting();
		this.busForTesting.setExpectedBusUnit(this.memory);
	}
	
	/**
	 * Testira pisanje i čitanje memorije tako što zapisuje pojedini podatak i kasnije
	 * ga čita iz memorije.
	 */
	@Test
	public void testReadWrite() {
		
		for (int i = 0; i < NUMBER_OF_TESTS; i++) {
			Address address = Address.fromWord(new Constant(random.nextInt(MEMORY_SIZE)));
			Word word = new Constant((byte)(random.nextInt() & 0xFF));
		
			busForTesting.setExpectedAddress(address);
			busForTesting.setExpectedWord(word);
			
			try {
				this.memory.requestWrite(busForTesting, address, word);
			} catch (IllegalActionException ignorable) {
			} catch (UnknownAddressException ignorable) {
			}
			
			simulator.run();
			
			try {
				this.memory.requestRead(busForTesting, address);
			} catch (IllegalActionException ignorable) {
			} catch (UnknownAddressException ignorable) {
			}
			
			simulator.run();
		}
	}

	/**
	 * Provjerava ograničenje pristupa memoriji koja već obavlja neku operaciju.
	 *
	 */
	@Test
	public void testAccessingBusy() throws IllegalActionException {
		this.simulator.setInternalDebugger(acceptOnlyIllegalActionException);
		
		try {
			this.memory.requestRead(busForTesting, Address.fromWord(new Constant(random.nextInt(1024))));
			this.memory.requestRead(busForTesting, Address.fromWord(new Constant(random.nextInt(1024))));
			this.memory.requestRead(busForTesting, Address.fromWord(new Constant(random.nextInt(1024))));		
			this.simulator.run();
		} catch (UnknownAddressException ignorable) {
		}
	}
	
	/**
	 * Testira čitanje proizvoljnih adresa
	 *
	 */
	@Test
	public void testReadRandomAddress() {
		
		this.simulator.setInternalDebugger(noAllowedExceptions);
		
		for (int i = 0; i < NUMBER_OF_TESTS; i++) {
			try {
				Address address = Address.fromWord(new Constant(random.nextInt(1024)));

				busForTesting.setExpectedAddress(address);
				busForTesting.setExpectedWord(null);
				
				this.memory.requestRead(busForTesting, address);
			} catch (Exception e) {
				Assert.fail("Dogodila se greška: " + e.getMessage());
			}
			
			this.simulator.run();
		}
	}
}
