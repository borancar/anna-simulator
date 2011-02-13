package hr.fer.anna.events;

/**
 * Event završetka operacije na jedinici. Event se odašilje kada je jedinica izvršila
 * operaciju i sada treba dojaviti to sabinici.
 * @author Boran
 *
 */
public class BusUnitOperationCompletedEvent extends BusUnitOperationEvent {

	/**
	 * Konstruktor koji stvara event završetka na temelju eventa početka operacije
	 * na jedinici.
	 * @param cause uzročni event koji je prouzrokovao ovaj event
	 */
	public BusUnitOperationCompletedEvent(BusUnitOperationEvent cause) {
		super(cause.getBus(), cause.getBusUnit(), cause.getLocalAddress());
		this.words = cause.words;
		this.reading = cause.reading;
		this.writing = cause.writing;
		this.completed = true;
		this.setCause(cause);
	}
}
