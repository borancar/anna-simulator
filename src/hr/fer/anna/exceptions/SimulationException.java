package hr.fer.anna.exceptions;

/**
 * Klasa koja predstavlja općenitu iznimku koja se može javiti pri simulaciji. Od ove klase
 * se deriviraju sve ostale klase iznimaka koje se mogu dogoditi pri simulaciji.
 * @author Boran
 *
 */
public class SimulationException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Defaultni konstruktor iznimke.
	 */
	public SimulationException() {

	}
	
	/**
	 * Konstruktor iznimke kojemu se može predati tekstualni opis iznimke.
	 * @param message tekstulani opis
	 */
	public SimulationException(String message) {
		super(message);
	}
	
	/**
	 * Konstruktor iznimke kojemu se može predati uzrok i tekstualni opis iznimke.
	 * @param message tekstulani opis
	 * @param cause uzrok iznimke
	 */
	public SimulationException(String message, Exception cause) {
		super(message, cause);
	}
}
