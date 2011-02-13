package hr.fer.anna.uniform;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import hr.fer.anna.events.BusUnitOperationCompletedEvent;
import hr.fer.anna.events.BusUnitOperationEvent;
import hr.fer.anna.events.BusUnitReadStartEvent;
import hr.fer.anna.events.BusUnitWriteStartEvent;
import hr.fer.anna.events.SimulationEvent;
import hr.fer.anna.exceptions.IllegalActionException;
import hr.fer.anna.exceptions.SimulationException;
import hr.fer.anna.exceptions.UnknownAddressException;
import hr.fer.anna.interfaces.IBus;
import hr.fer.anna.interfaces.IBusUnit;
import hr.fer.anna.interfaces.IEventListener;
import hr.fer.anna.interfaces.IEventSetter;
import hr.fer.anna.interfaces.IMemoryModel;
import hr.fer.anna.interfaces.IMemoryModelWatcher;
import hr.fer.anna.model.UnitReport;
import hr.fer.anna.model.Word;
import hr.fer.anna.simulator.Simulator;

/**
 * Jednostavna memorija koja ne podržava više istodobnih akcija. Moguće je samo obaviti čitanje
 * jedne lokacije ili upisivanje u jednu lokacije u isto vrijeme. Nakon zatraživanja jedne od
 * ovih dviju operacija, potrebno je pričekati da memorija obavi operaciju prije zahtijevanja
 * druge operacije.
 * @author Boran
 *
 */
public class SimpleMemory implements IMemoryModel, IBusUnit, IEventListener, IEventSetter {
	
	/**
	 * Čekanje prilikom pisanja u memoriju
	 */
	private static final int writeDelay = 2;

	/**
	 * Čekanje priliko čitanja iz memorije
	 */
	private static final int readDelay = 2;
	
	/**
	 * Slušači promjene memorijskog modela.
	 */
	private List<IMemoryModelWatcher> watchers;
	
	/** true ako je komponenta zauzeta, false inače */
	private boolean busy;
	
	/**
	 * Simulator na koji smo spojeni
	 */
	private Simulator simulator;

	/** Spremnik podataka. */
	private Map<Address, Word> memoryData;
	
	/** Veličina memorije. */
	private int memSize;
	
	/** Širina riječi memorije. */
	private int width;
		
	/**
	 * Konstruktor.
	 * 
	 * @param size Veličina memorije (u riječima).
	 * @param width Širina riječi memorije.
	 */
	public SimpleMemory (int size, int width) {
		this.watchers = new LinkedList<IMemoryModelWatcher>();
		this.memSize = size;
		this.width = width;
		this.memoryData = new HashMap<Address, Word>(size);
		busy = false;
	}
	
	/**
	 * Metoda implementira djelovanje komponente na eventove upućene toj komponenti.
	 */
	public void act(SimulationEvent event) throws SimulationException {
		BusUnitOperationEvent busUnitOperationEvent = (BusUnitOperationEvent) event;

		if(busUnitOperationEvent.isCompleted()) {
			if(busUnitOperationEvent.reading()) {
				busUnitOperationEvent.getBus().busUnitReadCallback(this, busUnitOperationEvent.getLocalAddress(), read(busUnitOperationEvent.getLocalAddress()));
			} else if (busUnitOperationEvent.writing()) {
				busUnitOperationEvent.getBus().busUnitWriteCallback(this, busUnitOperationEvent.getLocalAddress());
			}
			
			this.release(busUnitOperationEvent.getBus());
		} else {
			this.acquire(busUnitOperationEvent.getBus());
			
			if(busUnitOperationEvent.reading()) {
				this.simulator.setNewEvent(new BusUnitOperationCompletedEvent(busUnitOperationEvent), readDelay);
			} else if(busUnitOperationEvent.writing()) {
				this.simulator.setNewEvent(new BusUnitOperationCompletedEvent(busUnitOperationEvent), writeDelay);				

				if(busUnitOperationEvent.getWords()[0].getWidth() != this.width) {
					throw new IllegalActionException("Pokušaj zapisivanja riječi šire od širine memorije!");
				}
				
				write(busUnitOperationEvent.getWords(), busUnitOperationEvent.getLocalAddress());
			}
		}
	}

	/**
	 * Preuzimanje memorije od sabirnice prilikom čitanja/pisanja
	 * @param bus sabirnica
	 */
	private void acquire(IBus bus) throws IllegalActionException {
		if (this.busy) {
			throw new IllegalActionException("Memorija je već zauzeta!");
		}
		
		this.busy = true;
	}

	/**
	 * Otpuštanje memorije od sabirnice prilikom čitanja/pisanja
	 * @param bus sabirnica
	 */
	private void release(IBus bus) {
		this.busy = false;
	}

	public void init(Simulator sim) {
		this.simulator = sim;
	}

	public UnitReport describe() {
		return null;
	}

	public int getLocalAddresses() {
		return this.memSize;
	}

	public SimulationEvent[] getEvents() {
		return new SimulationEvent[]{new BusUnitOperationEvent(this)};
	}

	@Override
	public void requestRead(IBus bus, Address localAddress)
			throws IllegalActionException, UnknownAddressException {
		this.simulator.setNewEvent(new BusUnitReadStartEvent(bus, this, localAddress));
	}

	@Override
	public void requestWrite(IBus bus, Address localAddress, Word word)
			throws IllegalActionException, UnknownAddressException {
		this.simulator.setNewEvent(new BusUnitWriteStartEvent(bus, this, localAddress, new Word[]{word}));
	}

	public boolean isBusy() {
		return this.busy;
	}

	public Word read(Address address) throws UnknownAddressException, IllegalActionException {
		if(!this.memoryData.containsKey(address)) {
			return new Word(this.width);
		} else {
			return this.memoryData.get(address);
		}
	}

	public void write(Word word, Address address) throws UnknownAddressException, IllegalActionException {
		if(!this.memoryData.containsKey(address)) {
			this.memoryData.put(address, new Word(this.width));
		}
		
		this.memoryData.get(address).set(word);
		
		for(IMemoryModelWatcher watcher : this.watchers) {
			watcher.update(this, address, word);
		}
	}

	public void write(Word[] words, Address address) throws UnknownAddressException, IllegalActionException {
		for (int i = 0; i < words.length; i++) {
			write(words[i], Address.fromWord(Word.add(address, new Constant(i))));
		}
	}

	public void registerWatcher(IMemoryModelWatcher watcher) {
		this.watchers.add(watcher);
	}

	public void unregisterWatcher(IMemoryModelWatcher watcher) {
		this.watchers.remove(watcher);
	}
}
