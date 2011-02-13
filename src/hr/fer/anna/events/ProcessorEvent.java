package hr.fer.anna.events;

import hr.fer.anna.interfaces.IEventSetter;
import hr.fer.anna.uniform.CentralProcessingUnit;

/**
 * Općeniti eventovi koji su namijenjeni procesoru.
 * @author Boran
 *
 */
public class ProcessorEvent extends SimulationEvent {

	/** Procesor koji treba primiti i obraditi ovaj event */
	private CentralProcessingUnit cpu;
	
	/**
	 * Defaultni konstruktor, isključiva svrha mu je za uspoređivanje eventova.
	 * @param cpu procesor kojemu je namijenjen event
	 */
	public ProcessorEvent(CentralProcessingUnit cpu) {
		super();
		this.cpu = cpu;
	}
	
	/**
	 * Konstruktor eventa.
	 * 
	 * @param eventSource Objekt koji je postavio event.
	 * @param cpu Procesor koji treba primiti ovaj event.
	 */
	public ProcessorEvent(IEventSetter eventSource, CentralProcessingUnit cpu) {
		super(eventSource);
		this.cpu = cpu;
	}
	
	/**
	 * Konstruktor eventa.
	 * 
	 * @param eventSource Objekt koji je postavio event.
	 * @param myTimeIndex Vrijeme kada se event treba obraditi.
	 * @param cpu procesor koji treba primiti i obraditi ovaj event
	 */
	public ProcessorEvent(IEventSetter eventSource, long myTimeIndex, CentralProcessingUnit cpu) {
		super(eventSource, myTimeIndex);
		this.cpu = cpu;
	}
	
	/**
	 * Dohvaća procesor koji treba primiti i obraditi ovaj event.
	 * 
	 * @return procesor
	 */
	public CentralProcessingUnit getCpu() {
		return this.cpu;
	}
	
	/**
	 * Dva su procesorska eventa jednaka ako su namijenjena su istom procesoru.
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof ProcessorEvent)) {
			return false;
		}
		
		ProcessorEvent other = (ProcessorEvent) obj;
		
		return this.cpu == other.cpu;
	}
	
	public int hashCode() {
		return this.cpu.hashCode();
	}
}
