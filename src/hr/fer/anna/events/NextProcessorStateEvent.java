package hr.fer.anna.events;

import hr.fer.anna.interfaces.IEventSetter;
import hr.fer.anna.uniform.CentralProcessingUnit;

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
	 * @param cpu procesor kome je namijenjen ovaj event prelaska u iduće stanje
	 */
	public NextProcessorStateEvent(IEventSetter eventSource, CentralProcessingUnit cpu) {
		super(eventSource, cpu);
	}	
}
