package hr.fer.anna.interfaces;

import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Address;

/**
 * Pratitelj promjena memorijskog modela. Kao promjena memorijskog modela smatra
 * se promjena jedne od riječi koje sadržava memorijski model.
 * @author Boran
 *
 */
public interface IMemoryModelWatcher {

	/**
	 * Dojavljuje promjenu modela slušaču.
	 * @param model memorijski model koji se promijenio
	 * @param address adresa na kojoj se dogodila promjena
	 * @param word nova riječ na lokaciji
	 */
	public void update(IMemoryModel model, Address address, Word word);
}
