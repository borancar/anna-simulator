package hr.fer.anna.interfaces;

import hr.fer.anna.model.Word;

/**
 * Sučelje razreda koji prikazuje promjene neke riječi.
 * 
 * @author Boran
 * 
 */
public interface IWordChangeListener {

	/**
	 * Dojava promjene modela.
	 * 
	 * @param word Riječ koja se prati.
	 */
	public void update(Word word);

}
