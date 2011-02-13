package hr.fer.anna.interfaces;

import hr.fer.anna.events.SimulationEvent;
import hr.fer.anna.exceptions.SimulationException;

/**
 * Sučelje komponenti koje mogu slušati i reagirati na eventove.
 * @author Boran
 *
 */
public interface IEventListener {

	/**
	 * Metoda koja implementira djelovanje komponente na zaprimljeni event.
	 * @param event zaprimljeni event
	 * @throws SimulationException u slučaju greške prilikom obrade eventa
	 */
	public void act(SimulationEvent event) throws SimulationException;
	
	/**
	 * Metoda koja vraća eventove koji interesiraju ovog slušača.
	 */
	public SimulationEvent[] getEvents();
}
