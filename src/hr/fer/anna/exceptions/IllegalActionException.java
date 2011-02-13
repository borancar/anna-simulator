package hr.fer.anna.exceptions;

/**
 * Iznimka koja se baca u slučaju kada se pokušava raditi nešto što nije dopušteno. Primjer
 * je čitanje s lokacije koja ne podržava čitanje, pisanje u lokaciju koja ne podržava pisanja
 * ili pristupanje zauzetoj komponenti.
 * @author Boran
 *
 */
public class IllegalActionException extends SimulationException {
	
	private static final long serialVersionUID = 1L;

	public IllegalActionException(String msg) {
		super(msg);
	}
}
