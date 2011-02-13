package hr.fer.anna.exceptions;

/**
 * Klasa općenite iznimke koja se može dogoditi prilikom izvođenja mikroinstrukcije.
 * @author Boran
 *
 */
public class MicrocodeException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor koji prima tekstulano objašnjenje iznimke.
	 * @param message tekst iznimke
	 */
	public MicrocodeException(String message) {
		super(message);
	}
	
	/**
	 * Konstruktor koji prima tekstulano objašnjenje i uzrok iznimke
	 * @param message tekst iznimke
	 * @param cause uzrok
	 */
	public MicrocodeException(String message, SimulationException cause) {
		super(message, cause);
	}
}
