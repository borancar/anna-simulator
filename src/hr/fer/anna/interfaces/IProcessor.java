package hr.fer.anna.interfaces;

public interface IProcessor {

	/**
	 * Zaustavljanje upravljačke jedinice.
	 */
	void halt();
	
	/**
	 * Resetira procesor
	 */
	void reset();
}
