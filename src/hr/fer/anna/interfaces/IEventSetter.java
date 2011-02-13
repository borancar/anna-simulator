package hr.fer.anna.interfaces;

import hr.fer.anna.model.UnitReport;
import hr.fer.anna.simulator.Simulator;

/**
 * Sučelje komponenti koje mogu postaviti evente simulatoru.
 */
public interface IEventSetter {
	
	/**
	 * Inicijaliziranje postavki (usklađivanje vremenskog pomaka, postavljanje poč. evenata)
	 * za simulator sim.
	 * 
	 * @param sim Simulator na koji se pripremamo. 
	 */
	public void init(Simulator sim);
	
	/**
	 * Dohvat opisa komponente.
	 * 
	 * @return Opis komponente.
	 */
	public UnitReport describe();
}
