package hr.fer.anna.events;

import java.util.HashSet;
import java.util.Set;

import hr.fer.anna.interfaces.IEventSetter;

/**
 * Container događaja (eventa).
 * Služi za upravljanje eventom.
 */
public class SimulationEvent {
	
	/** Komponenta koja je postavila event. */
	private IEventSetter eventSource;
	
	/** Vrijeme kad se event treba izvršiti. */
	private long timeIndex;
	
	/** Lista evenata o kojima trenutni event ovisi */
	private Set<SimulationEvent> dependencies;
	
	/** Event koji je zaslužan za stvaranje ovog eventa */
	private SimulationEvent eventCause;
	
	/**
	 * Defaultni konstruktor, služi isključivo za registriranje slušača.
	 */
	public SimulationEvent() {
		this.dependencies = new HashSet<SimulationEvent>();
	}
	
	/**
	 * Konstruktor koji stvara tek osnovni event koji se mora izvršiti trenutno.
	 * 
	 * @param eventSource Komponenta koja je postavila event
	 */
	public SimulationEvent(IEventSetter eventSource) {
		this();
		this.timeIndex = 0;
		this.eventSource = eventSource;
	}
	
	/**
	 * Konstruktor koji stvara event koji se mora izvršiti u nekom trenutku.
	 * 
	 * @param eventSource Komponenta koja je postavila event.
	 * @param myTimeIndex Vrijeme kad se event treba izvršiti.
	 */
	public SimulationEvent(IEventSetter eventSource, long myTimeIndex) {
		this(eventSource);
		this.timeIndex = myTimeIndex;
		this.dependencies = new HashSet<SimulationEvent>();
	}
	
	/**
	 * Getter eventSourcea.
	 * 
	 * @return Komponentu koja je postavila event.
	 */
	public IEventSetter getEventSource() {
		return this.eventSource;
	}
	
	/**
	 * Da li trenutni event ovisi o eventu koji je prosljeđen.
	 * 
	 * @param event Prosljeđen event (onaj za kojem želimo znati da li trenutni o njemu ovisi).
	 * @return True ako ovisi, inače false.
	 */
	public boolean checkDependency(SimulationEvent event) {
		return this.dependencies.contains(event);
	}
	
	/**
	 * Postavlja trenutni event ovisan o prosljeđenom eventu.
	 * 
	 * @param event Prosljeđen event (onaj za koji želimo da trenutni event bude ovisan o njemu).
	 */
	public void addDependency(SimulationEvent event) {
		this.dependencies.add(event);
	}
	
	/**
	 * Dohvaća event koji je bio uzrok stvaranju ovog eventa.
	 * @return event uzročnik
	 */
	public SimulationEvent getCause() {
		return this.eventCause;
	}
	
	/**
	 * Postavlja uzročnika nastajanja ovog eventa.
	 * 
	 * @param eventCause event radi kojeg je stvoren ovaj event
	 */
	public void setCause(SimulationEvent eventCause) {
		this.eventCause = eventCause;
	}
	
	/**
	 * Getter timeIndexa.
	 * 
	 * @return Vrijeme kad se event treba izvršiti.
	 */
	public long getTimeIndex() {
		return this.timeIndex;
	}
	
	/**
	 * Setter timeIndexa.
	 * 
	 * @param timeIndex Vrijeme kad se event treba izvršiti.
	 */
	public void setTimeIndex(long timeIndex) {
		this.timeIndex = timeIndex;
	}
	
	/**
	 * Dva eventa su jednaka ako su istog tipa.
	 * 
	 * @return True ako su objekti ekvivalentni, inače false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (!(obj instanceof SimulationEvent)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Hash kod objekta. Jedini uvjet koji se postavlja je da dva jednaka prema metodi equals
	 * objekta imaju isti hash kod.
	 * 
	 * @return Hash objekta.
	 */
	@Override
	public int hashCode() {
		return 0;
	}
}
