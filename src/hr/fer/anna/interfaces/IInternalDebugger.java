package hr.fer.anna.interfaces;

import hr.fer.anna.exceptions.SimulationException;

/**
 * Debugger koji mora moći primiti sve iznimke koje se mogu dogoditi tokom simulacije.
 * Preko ovog sučelja debugger može zaustaviti simulaciju ako procijeni da se simulator
 * ne može oporaviti.
 * @author Boran
 *
 */
public interface IInternalDebugger {

	/**
	 * Prijavljuje iznimku debuggeru. Debugger uzvraća da li se simulacija može nastaviti
	 * ili je treba prekinuti.
	 * 
	 * @param simulationException iznimka koja se dogodila tokom simulacije
	 * @return true ako se simulacija može nastaviti, false inače
	 */
	public boolean reportException(SimulationException simulationException);
}
