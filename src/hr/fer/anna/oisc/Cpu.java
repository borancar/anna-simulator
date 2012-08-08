package hr.fer.anna.oisc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import hr.fer.anna.events.NextProcessorStateEvent;
import hr.fer.anna.events.ProcessorEvent;
import hr.fer.anna.events.SimulationEvent;
import hr.fer.anna.exceptions.IllegalActionException;
import hr.fer.anna.exceptions.MicrocodeException;
import hr.fer.anna.exceptions.SimulationException;
import hr.fer.anna.exceptions.UnknownAddressException;
import hr.fer.anna.interfaces.IBus;
import hr.fer.anna.interfaces.IBusMaster;
import hr.fer.anna.interfaces.IEventListener;
import hr.fer.anna.interfaces.IEventSetter;
import hr.fer.anna.interfaces.IInstructionDecoder;
import hr.fer.anna.interfaces.IMicroinstruction;
import hr.fer.anna.interfaces.IProcessor;

import hr.fer.anna.simulator.Simulator;
import hr.fer.anna.simulator.handles;
import hr.fer.anna.uniform.Address;
import hr.fer.anna.uniform.Constant;
import hr.fer.anna.uniform.Register;
import hr.fer.anna.model.UnitReport;
import hr.fer.anna.model.Word;

/**
 * Realizacija OISC CPU-a.
 */
public class Cpu implements IProcessor, IBusMaster, IEventSetter, IEventListener {

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
	
	/** Red mikroinstrukcija koje treba izvršavati */
	private Queue<IMicroinstruction> microinstructionCache;
	
	/** Dekoder instrukcija */
	private IInstructionDecoder instructionDecoder;
	
	/** Simulator na kojeg smo spojeni */
	private Simulator simulator;
	
	/** Sabirnica spojena na procesor */
	private IBus bus;
	
	private static interface IState {
		
		public ProcessorState run() throws MicrocodeException, IllegalActionException, UnknownAddressException;
	}
	
	private IState loadState = new IState() {
		
		@Override
		public ProcessorState run() throws MicrocodeException, IllegalActionException, UnknownAddressException {
			bus.requestRead(Cpu.this, pc.getAddress());
			
			pc.set(Word.add(pc, Constant.ONE));
			
			return ProcessorState.DECODE_INSTRUCTION;
		}
	};
	
	private IState decodeState = new IState() {
		
		@Override
		public ProcessorState run() throws MicrocodeException {
			microinstructionCache.addAll(instructionDecoder.decode(codeReq));
			
			if (microinstructionCache.isEmpty()) {
				halt();
			}

			simulator.setNewEvent(new NextProcessorStateEvent(Cpu.this, Cpu.this));

			return ProcessorState.EXECUTE_INSTRUCTION;
		}
	};
	
	private IState executeState = new IState() {
		
		@Override
		public ProcessorState run() throws MicrocodeException {
			while (!microinstructionCache.isEmpty()) {
				// Ako se dogodio hazard, izlazimo i čekamo da se hazard riješi
				// prije nastavka izvršavanja
				IMicroinstruction microinstruction = microinstructionCache.remove();
				
				if (microinstruction.execute()) {
					break;
				}
			}
			
			if (!microinstructionCache.isEmpty()) {
				// Ako ima još mikroinstrukcija, to moramo u sljedećem ciklusu obavit
				// čekamo otklanjanje hazarda koje će nam biti dojavljeno
				
				return ProcessorState.EXECUTE_INSTRUCTION;
			} else {
				simulator.setNewEvent(new NextProcessorStateEvent(Cpu.this, Cpu.this));

				return ProcessorState.LOAD_INSTRUCTION;
			}
		}
	};
	
	/** Stanja procesora (procesor je stroj s konačnim brojem stanja) */
	private enum ProcessorState {		
		/** Učitavanje instrukcije iz memorije */
		LOAD_INSTRUCTION,
		
		/** Dekodiranje pročitane instrukcije */
		DECODE_INSTRUCTION,
		
		/** Izvršavanje instrukcije nakon što je ona dekodirana */
		EXECUTE_INSTRUCTION;
	}
	
	private Map<ProcessorState, IState> states = new HashMap<Cpu.ProcessorState, Cpu.IState>();
	
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
		
		this.microinstructionCache = new LinkedList<IMicroinstruction>();
		
		this.instructionDecoder = new InstructionDecoder(this, this.bus, this.dataReg, this.accuReg, this.pc, this.statusReg);
	
		this.states.put(ProcessorState.LOAD_INSTRUCTION, loadState);
		this.states.put(ProcessorState.DECODE_INSTRUCTION, decodeState);
		this.states.put(ProcessorState.EXECUTE_INSTRUCTION, executeState);
	}

	@Override
	public void halt() {
		this.isRunning = false;
	}
	
	@Override
	public void reset() {
		this.isRunning = true;
		this.nextProcessorState = ProcessorState.LOAD_INSTRUCTION;
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
		
		try {
			nextProcessorState = states.get(currentProcessorState).run();
		} catch (MicrocodeException e) {
			throw new SimulationException("CPU Exception", e);
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
