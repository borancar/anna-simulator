package hr.fer.anna.simulator;

import hr.fer.anna.events.SimulationEvent;
import hr.fer.anna.exceptions.SimulationException;
import hr.fer.anna.interfaces.IEventListener;
import hr.fer.anna.interfaces.IEventSetter;
import hr.fer.anna.interfaces.IInternalDebugger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Simulator. Glavna komponenta simulacije koja spaja i kontrolira ostale
 * komponente.
 */
public class Simulator implements Runnable {

	/** Vrijeme simulacije. */
	protected long time;

	/** Flag kojim se zaustavlja simulacija. */
	protected volatile boolean isRunning;

	/** Flag kojim se pauzira simulacija. */
	protected volatile boolean isPaused;

	/** Kolekcija evenata koji se trebaju izvršiti. */
	protected EventQueue eventQueue;

	/** Za svaki event vraća slušače koji su za njega zainteresirani. */
	protected Map<SimulationEvent, Set<IEventListener>> eventListeners;

	/** Debugger kojemu se šalju sve iznimke tokom simulacije */
	protected IInternalDebugger internalDebugger;
	
	/**
	 * Konstruktor simulatora.
	 */
	public Simulator() {
		this.eventQueue = new EventQueue();
		this.isRunning = false;
		this.isPaused = false;
		this.eventListeners = new HashMap<SimulationEvent, Set<IEventListener>>();
	}

	/**
	 * Postavljanje novog eventa u eventQueue, ako unutar eventa nije zadan
	 * trenutak izvršavanja, vrijeme izvršavanja će postati trenutno vrijeme
	 * simulatora.
	 * 
	 * @param e Novi event.
	 */
	public void setNewEvent(SimulationEvent e) {
		if (e.getTimeIndex() < this.time) {
			e.setTimeIndex(this.time);
		}

		this.eventQueue.add(e);
	}

	/**
	 * Postavljanje novog eventa koji se treba izvršiti u nekom određenom
	 * trenutku.
	 * 
	 * @param e Novi event
	 * @param timeIndex Trenutak u kojem se treba izvršiti
	 */
	public void setNewEvent(SimulationEvent e, long timeIndex) {
		e.setTimeIndex(timeIndex);

		setNewEvent(e);
	}
	
	/**
	 * Postavljanje novog eventa koji se treba izvršiti nakon što protekne određeno
	 * vrijeme od trenutnog trenutka.
	 * 
	 * @param e Novi event
	 * @param timeOffset Za koliko se treba izvršiti event
	 */
	public void setNewEvent(SimulationEvent e, int timeOffset) {
		e.setTimeIndex(time + timeOffset);

		setNewEvent(e);
	}

	/**
	 * Odregistrira slušača.
	 * 
	 * @param eventListener Slušač.
	 */
	public void unregisterEventListener(IEventListener eventListener) {
		for(SimulationEvent event : eventListener.getEvents()) {
			this.eventListeners.get(event).remove(eventListener);			
		}
	}
	
	/**
	 * Registrira postavljatela eventova
	 * @param eventSetter postavljatelj eventova
	 */
	public void registerEventSetter(IEventSetter eventSetter) {
		eventSetter.init(this);
	}
	
	/**
	 * Registrira postavljatelje eventova
	 * @param eventSetters postavljatelji eventova
	 */
	public void registerEventSetters(IEventSetter[] eventSetters) {
		for(IEventSetter eventSetter : eventSetters) {
			registerEventSetter(eventSetter);
		}
	}
	
	/**
	 * Registrira slušača na eventove koje slušača interesiraju.
	 * 
	 * @param eventListener Slušač.
	 */
	public void registerEventListener(IEventListener eventListener) {
		
		for (Method method : eventListener.getClass().getMethods()) {
			if(method.isAnnotationPresent(handles.class)) {
				handles what = method.getAnnotation(handles.class);
				
				for (Class<SimulationEvent> event : what.events()) {
					System.out.println(event.toString());
				}
			}
		}
		
		for(SimulationEvent event : eventListener.getEvents()) {
			if (!this.eventListeners.containsKey(event)) {
				this.eventListeners.put(event, new HashSet<IEventListener>());
			}
	
			this.eventListeners.get(event).add(eventListener);			
		}
	}

	/**
	 * Registrira slušače na eventove koje slušače interesiraju.
	 * Svakog slušača registrira samo na eventove koji njega interesiraju.
	 * @param eventListeners Slušači.
	 */
	public void registerEventListeners(IEventListener[] eventListeners) {
		for(IEventListener eventListener : eventListeners) {
			registerEventListener(eventListener);
		}
	}

	/**
	 * Nastavak simulacije nakon pauze.
	 */
	public synchronized void continueSimulation() {
		this.isPaused = false;
	}
	
	/**
	 * Pauziranje simulacije.
	 */
	public synchronized void pauseSimulation() {
		this.isPaused = true;
	}
	
	/**
	 * Zaustavljanje simulacije.
	 */
	public void stopSimulation() {
		this.eventQueue.clear();
		this.isRunning = false;
	}
	
	/**
	 * Dohvaća debuggera simulacije
	 * @return debugger simulacije
	 */
	public IInternalDebugger getInternalDebugger() {
		return this.internalDebugger;
	}
	
	/**
	 * Postavlja novog debuggera simulacije
	 * @param internalDebugger novi debugger simulacije
	 */
	public void setInternalDebugger(IInternalDebugger internalDebugger) {
		this.internalDebugger = internalDebugger;
	}
	
	/**
	 * Simuliranje. Pseudokod glavnog dijela: <br />
	 * 1) Dohvati event. <br />
	 * 2) Postavi novo simulacijsko vrijeme. <br />
	 * 3) Prosljedi event slušačima koji su se pretplatili na njega. <br />
	 */
	public void run() {

		this.time = 0;
		this.isRunning = true;
		SimulationEvent event = null;

		while (this.isRunning) {
			
			while (this.isPaused);	// Pauziranje simulatora
			
			if (this.eventQueue.isEmpty()) {
				this.isRunning = false;
				break;
			}

			event = this.eventQueue.remove();

			this.time = event.getTimeIndex();

			if (this.eventListeners.containsKey(event)) {
				for (IEventListener eventListener : this.eventListeners.get(event)) {
					try {
						eventListener.act(event);
					} catch (SimulationException e) {
						// TODO: Što ako ne postoji interni debugger?
						
						if(!(this.internalDebugger.reportException(e))) {
							this.stopSimulation();
						}
					}
				}
			}
		}
	}

	/**
	 * Getter vremena simulacije.
	 * 
	 * @return Vrijeme simulacije.
	 */
	public long getSimulatorTime() {
		return this.time;
	}

	/**
	 * Je li simulacija pauzirana.
	 * 
	 * @return True ako je simulacija pauzirana, inače false.
	 */
	public boolean isPaused() {
		return this.isPaused;
	}
}
