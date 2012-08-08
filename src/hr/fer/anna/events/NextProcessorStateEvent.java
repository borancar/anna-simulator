package hr.fer.anna.events;

import hr.fer.anna.interfaces.IEventSetter;
import hr.fer.anna.interfaces.IProcessor;

/**
 * Procesor odašilje ovaj event kao signal sebi da u idućem trenutku treba preći
 * u iduće stanje.
 * @author Boran
 *
 */
public class NextProcessorStateEvent extends ProcessorEvent {

	/**
	 * Konstruktor eventa prelaska u iduće stanje.
	 * @param eventSource jedinica koja je odaslala ovaj event, obično sam procesor
	 * @param processor procesor kome je namijenjen ovaj event prelaska u iduće stanje
	 */
	public NextProcessorStateEvent(IEventSetter eventSource, IProcessor processor) {
		super(eventSource, processor);
	}	
}
