package hr.fer.anna.exceptions;

/**
 * Iznimka za slučaj kada ne postoji adresa kojoj se želi pristupati.
 * @author Boran
 *
 */
public class UnknownAddressException extends SimulationException {

	private static final long serialVersionUID = 1L;

	public UnknownAddressException(String msg) {
		super(msg);
	}
}
