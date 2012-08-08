package hr.fer.anna.events;

import hr.fer.anna.interfaces.IEventSetter;
import hr.fer.anna.interfaces.IProcessor;

/**
 * Općeniti eventovi koji su namijenjeni procesoru.
 * @author Boran
 *
 */
public class ProcessorEvent extends SimulationEvent {

	/** Procesor koji treba primiti i obraditi ovaj event */
	private IProcessor processor;
	
	/**
	 * Defaultni konstruktor, isključiva svrha mu je za uspoređivanje eventova.
	 * @param processor procesor kojemu je namijenjen event
	 */
	public ProcessorEvent(IProcessor processor) {
		super();
		this.processor = processor;
	}
	
	/**
	 * Konstruktor eventa.
	 * 
	 * @param eventSource Objekt koji je postavio event.
	 * @param cpu Procesor koji treba primiti ovaj event.
	 */
	public ProcessorEvent(IEventSetter eventSource, IProcessor processor) {
		super(eventSource);
		this.processor = processor;
	}
	
	/**
	 * Konstruktor eventa.
	 * 
	 * @param eventSource Objekt koji je postavio event.
	 * @param myTimeIndex Vrijeme kada se event treba obraditi.
	 * @param cpu procesor koji treba primiti i obraditi ovaj event
	 */
	public ProcessorEvent(IEventSetter eventSource, long myTimeIndex, IProcessor processor) {
		super(eventSource, myTimeIndex);
		this.processor = processor;
	}
	
	/**
	 * Dohvaća procesor koji treba primiti i obraditi ovaj event.
	 * 
	 * @return procesor
	 */
	public IProcessor getCpu() {
		return this.processor;
	}
	
	/**
	 * Dva su procesorska eventa jednaka ako su namijenjena su istom procesoru.
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof ProcessorEvent)) {
			return false;
		}
		
		ProcessorEvent other = (ProcessorEvent) obj;
		
		return this.processor == other.processor;
	}
	
	public int hashCode() {
		return this.processor.hashCode();
	}
}
