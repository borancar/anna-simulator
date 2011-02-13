package hr.fer.anna.simulator;

import hr.fer.anna.events.SimulationEvent;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Queue koji drži elemente koje treba izvršavati, elementi su poredani prema
 * rastućim vremenskim indeksima i tako da se elementi o kojima su neki drugi
 * elementi ovisni nalaze prije njih, formalno, za bilo koja 2 elementa A i B
 * gdje se A nalazi prije B vrijedi: A.getTimeIndex() < B.getTimeIndex &&
 * B.checkDependency(A) == true
 * 
 * @author Boran
 * 
 */
public class EventQueue extends LinkedList<SimulationEvent> {

	private static final long serialVersionUID = 1L;

	/**
	 * Defaultni konstruktor, vraća prazan EventQueue.
	 */
	public EventQueue() {

		super();
	}

	/**
	 * Dodaje element e tako da i dalje vrijede svojstva EventQueue-a.
	 */
	@Override
	public boolean add(SimulationEvent e) {

		// TODO: Ovo treba bolje implementirati (npr, možda čak preko Set-a, ali
		// ne dirati onda equals i hashCode metode

		int newPos = 0;

		Iterator<SimulationEvent> iterator = iterator();

		while (iterator.hasNext()) {

			SimulationEvent compareToEvent = iterator.next();

			if (e.getTimeIndex() < compareToEvent.getTimeIndex()
					|| compareToEvent.checkDependency(e)) {
				break;
			}

			newPos++;
		}

		super.add(newPos, e);

		return true;
	}

	/**
	 * Dodaje kolekciju elemenata tako da i dalje vrijede svojstva EventQueue-a.
	 */
	@Override
	public boolean addAll(Collection<? extends SimulationEvent> c) {

		// TODO: Optimizirati ovo
		for (SimulationEvent e : c) {
			if (add(e) == false) {
				return false;
			}
		}

		return true;
	}
}
