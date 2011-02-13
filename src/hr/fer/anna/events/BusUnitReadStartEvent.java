package hr.fer.anna.events;

import hr.fer.anna.interfaces.IBus;
import hr.fer.anna.interfaces.IBusUnit;
import hr.fer.anna.uniform.Address;

/**
 * Event početka čitanja sa jedinice na sabirnici.
 * @author Boran
 *
 */
public class BusUnitReadStartEvent extends BusUnitOperationEvent {

	/**
	 * Konstruktor eventa početka čitanja sa vanjske jedinice
	 * @param bus sabirnica koja je postavila zahtjev
	 * @param busUnit jedinica na sabirnici koja treba obaviti operaciju čitanja
	 * @param localAddress lokalna adresa jedinice s koje se čita
	 */
	public BusUnitReadStartEvent(IBus bus, IBusUnit busUnit, Address localAddress) {
		super(bus, busUnit, localAddress);
		this.reading = true;
		this.writing = false;
		this.completed = false;
	}
}
