package hr.fer.anna.exceptions;


public class SyntaxError extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor koji prima tekstulano objašnjenje iznimke.
	 * @param message tekst iznimke
	 */
	public SyntaxError(String message) {
		super(message);
	}
	
	/**
	 * Konstruktor koji prima tekstulano objašnjenje i uzrok iznimke
	 * @param message tekst iznimke
	 * @param cause uzrok
	 */
	public SyntaxError(String message, SimulationException cause) {
		super(message, cause);
	}
	
	@Override
	public String toString() {
		return getMessage();
	}
}
