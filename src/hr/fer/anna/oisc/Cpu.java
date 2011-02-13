package hr.fer.anna.oisc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import hr.fer.anna.events.NextProcessorStateEvent;
import hr.fer.anna.events.ProcessorEvent;
import hr.fer.anna.events.SimulationEvent;
import hr.fer.anna.exceptions.IllegalActionException;
import hr.fer.anna.exceptions.MicrocodeException;
import hr.fer.anna.exceptions.SimulationException;
import hr.fer.anna.interfaces.IBus;
import hr.fer.anna.interfaces.IEventListener;
import hr.fer.anna.interfaces.IEventSetter;
import hr.fer.anna.interfaces.IInstructionDecoder;
import hr.fer.anna.interfaces.IMicroinstruction;

import hr.fer.anna.simulator.Simulator;
import hr.fer.anna.simulator.handles;
import hr.fer.anna.uniform.Address;
import hr.fer.anna.uniform.CentralProcessingUnit;
import hr.fer.anna.uniform.Constant;
import hr.fer.anna.uniform.InterruptPort;
import hr.fer.anna.uniform.Register;
import hr.fer.anna.model.UnitReport;
import hr.fer.anna.model.Word;

/**
 * Realizacija OISC CPU-a.
 */
public class Cpu extends CentralProcessingUnit implements IEventSetter, IEventListener {

	/** Flag koji određuje je li procesor aktivan. */
	private boolean isRunning;

	/** Registar stanja i zastavica. */
	private StatusRegister statusReg;

	/** PC registar. */
	private Register pc;

	/** Data registar. */
	private Register dataReg;

	/** Address registar. */
	private Register addressReq;

	/** Code registar. */
	private Register codeReq;

	/** Akumulator. */
	private Register accuReg;
	
	/** Interrupt portovi <Naziv, otvoren/zatvoren>. */
	private Map<String, InterruptPort> interruptPorts;
	
	/** Red mikroinstrukcija koje treba izvršavati */
	private Queue<IMicroinstruction> microinstructionCache;
	
	/** Dekoder instrukcija */
	private IInstructionDecoder instructionDecoder;
	
	/** Simulator na kojeg smo spojeni */
	private Simulator simulator;
	
	/** Stanja procesora (procesor je stroj s konačnim brojem stanja) */
	private enum ProcessorState {
		/** Učitavanje instrukcije iz memorije */
		LOAD_INSTRUCTION,
		
		/** Dekodiranje pročitane instrukcije */
		DECODE_INSTRUCTION,
		
		/** Izvršavanje instrukcije nakon što je ona dekodirana */
		EXECUTE_INSTRUCTION
	}
	
	/** Trenutno stanje u kojem se nalazi procesor */
	private ProcessorState currentProcessorState;

	/** Iduće stanje u koje procesor treba ići */
	private ProcessorState nextProcessorState;
	
	/**
	 * Konstruktor.
	 */
	public Cpu(IBus bus) {
		this.bus = bus;
		
		try {
			this.bus.setBusMaster(this);
		} catch (IllegalActionException ignorable) {

		}
		
		this.nextProcessorState = ProcessorState.LOAD_INSTRUCTION;
		
		this.isRunning = true;

		// Specijalni registri
		this.statusReg = new StatusRegister();
		this.pc = new Register();
		this.dataReg = new Register();
		this.codeReq = new Register();
		this.addressReq = new Register();
		this.accuReg = new Register();
		
		// Postavljanje interrupt portova
		this.interruptPorts = new HashMap<String, InterruptPort>();
		this.interruptPorts.put("irq0", new InterruptPort("irq0", 8));
		this.interruptPorts.put("irq1", new InterruptPort("irq1", 8));
		this.interruptPorts.put("nmi0", new InterruptPort("nmi0", 12));
		
		this.microinstructionCache = new LinkedList<IMicroinstruction>();
		
		this.instructionDecoder = new InstructionDecoder(this, this.bus, this.dataReg, this.accuReg, this.pc, this.statusReg);
	}

	@Override
	protected void closeInterruptPort(String port) throws IllegalActionException {
		if (this.interruptPorts.containsKey(port)) {
			this.interruptPorts.get(port).setIsOpen(false);
		} else {
			throw new IllegalActionException("Ne postoji port s nazivom: " + port);
		}
	}
	
	@Override
	protected void openInterruptPort(String port) throws IllegalActionException {
		if (this.interruptPorts.containsKey(port)) {
			this.interruptPorts.get(port).setIsOpen(true);
		} else {
			throw new IllegalActionException("Ne postoji port s nazivom: " + port);
		}

	}

	@Override
	public Set<String> getPorts() {
		return this.interruptPorts.keySet();
	}

	@Override
	public void halt() {
		this.isRunning = false;
	}

	@Override
	public boolean isPortOpened(String port) throws IllegalActionException {
		if (this.interruptPorts.containsKey(port)) {
			return this.interruptPorts.get(port).isOpen();
		} else {
			throw new IllegalActionException("Ne postoji port s nazivom: " + port);
		}
	}

	@Override
	public boolean receiveInterrupt(String port) throws IllegalActionException {
		if (this.interruptPorts.containsKey(port)) {
			if (!this.statusReg.isInterruptEnabled(port)
					|| !this.interruptPorts.get(port).isOpen()) {
				return false;
			}
			
			this.statusReg.setInterrupt(port, true);
			
			return true;
		} else {
			throw new IllegalActionException("Ne postoji port s nazivom: " + port);
		}
	}

	public void init(Simulator sim) {
		this.simulator = sim;
		
		simulator.setNewEvent(new NextProcessorStateEvent(this, this));
	}

	public UnitReport describe() {
		UnitReport report = new UnitReport("CPU", "OISC v1.0");
		
		report.addRegister(this.pc, "PC", "Programsko brojilo");
		report.addRegister(this.dataReg, "DR", "Podatkovni registar");
		report.addRegister(this.addressReq, "AR", "Adresni registar");
		report.addRegister(this.codeReq, "IR", "Instrukcijski registar");
		report.addRegister(this.accuReg, "A", "Akumulator");
		
		return report;
	}

	public void waitBus(IBus bus, boolean state) {

	}

	@handles(events = ProcessorEvent.class)
	public void act(SimulationEvent event) throws SimulationException {
		
		if (!this.isRunning) {
			return;
		}
		
		currentProcessorState = nextProcessorState;
		
		switch (currentProcessorState) {
			case LOAD_INSTRUCTION:
				
				this.bus.requestRead(this, pc.getAddress());
				
				pc.set(Word.add(pc, Constant.ONE));
								
				this.nextProcessorState = ProcessorState.DECODE_INSTRUCTION;
				
				break;
			
			case DECODE_INSTRUCTION:
				this.microinstructionCache.addAll(instructionDecoder.decode(codeReq));
				
				if (this.microinstructionCache.isEmpty()) {
					this.halt();
				}
								
				this.nextProcessorState = ProcessorState.EXECUTE_INSTRUCTION;

				simulator.setNewEvent(new NextProcessorStateEvent(this, this));
				
				break;
				
			case EXECUTE_INSTRUCTION:
				
				while (!this.microinstructionCache.isEmpty()) {
					// Ako se dogodio hazard, izlazimo i čekamo da se hazard riješi
					// prije nastavka izvršavanja
					try {
						IMicroinstruction microinstruction = this.microinstructionCache.remove();
						
						if (microinstruction.execute()) {
							break;
						}
					} catch (MicrocodeException e) {
						throw new SimulationException("Mikroinstrukcija se nije mogla izvršiti!", e);
					}
				}
				
				if (!this.microinstructionCache.isEmpty()) {
					// Ako ima još mikroinstrukcija, to moramo u sljedećem ciklusu obavit
					// čekamo otklanjanje hazarda koje će nam biti dojavljeno
					this.nextProcessorState = ProcessorState.EXECUTE_INSTRUCTION;
				} else {
					this.nextProcessorState = ProcessorState.LOAD_INSTRUCTION;
					simulator.setNewEvent(new NextProcessorStateEvent(this, this));
				}

				break;
								
			default:
				break;
		}
	}

	public SimulationEvent[] getEvents() {
		return new SimulationEvent[]{new ProcessorEvent(this)};
	}

	public void busReadCallback(IBus bus, Address globalAddress, Word word) {
		if(currentProcessorState == ProcessorState.LOAD_INSTRUCTION) {
			this.codeReq.set(word);
		} else if(currentProcessorState == ProcessorState.EXECUTE_INSTRUCTION) {
			this.dataReg.set(word);
		}
		
		simulator.setNewEvent(new NextProcessorStateEvent(this, this));
	}

	public void busWriteCallback(IBus bus, Address globalAddress) {
		simulator.setNewEvent(new NextProcessorStateEvent(this, this));
	}
}
