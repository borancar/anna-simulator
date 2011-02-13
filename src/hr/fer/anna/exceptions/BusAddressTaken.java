package hr.fer.anna.exceptions;

/**
 * Iznimka koja se baca kada se pokuša preslikati nova vanjska jedinica na neku od adresa
 * koja je već pridjeljenja nekoj drugoj jedinici.
 * @author Boran
 *
 */
public class BusAddressTaken extends Exception {
	
	private static final long serialVersionUID = 1L;

	public BusAddressTaken(String errorMsg) {
		super(errorMsg);
	}
}
