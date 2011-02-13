package hr.fer.anna.events;

import hr.fer.anna.interfaces.IBus;
import hr.fer.anna.interfaces.IBusUnit;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Address;

/**
 * Event početka pisanja u jedinicu na sabirnici.
 * @author Boran
 *
 */
public class BusUnitWriteStartEvent extends BusUnitOperationEvent {

	/**
	 * Konstruktor eventa početka pisanja u jedinicu
	 * @param bus sabirnica koja je postavila zahtjev
	 * @param busUnit jedinica na sabirnici koja treba obaviti operaciju pisanja
	 * @param localAddress lokalna adresa jedinice na koju se piše
	 * @param words riječi koje se upisuju
	 */
	public BusUnitWriteStartEvent(IBus bus, IBusUnit busUnit, Address localAddress, Word[] words) {
		super(bus, busUnit, localAddress);
		this.words = words;
		this.reading = false;
		this.writing = true;
		this.completed = false;
	}
}
