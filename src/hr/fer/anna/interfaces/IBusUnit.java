package hr.fer.anna.interfaces;

import hr.fer.anna.exceptions.IllegalActionException;
import hr.fer.anna.exceptions.UnknownAddressException;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Address;

/**
 * Sučelje sklopa s kojeg sabirnica čita podatke i na koji se preko sabirnice
 * podatci pišu.
 */
public interface IBusUnit {
	
	/**
	 * Veličina adresabilnog prostora jedinice.
	 * 
	 * @return broj lokacija kojima se može pristupati (čitati ili pisati).
	 */
	public int getLocalAddresses();

	/**
	 * Postavljanje zahtjeva jedinici na sabirnici za pisanje 1 riječi
	 * 
	 * @param bus sabirnica koja postavlja zahtjev, uobičajeno <code>this</code>
	 * @param localAddress lokalna adresa (adresa na jedinici)
	 * @param word riječ koju želimo upisati
	 * 
	 * @throws IllegalActionException
	 *             Ukoliko se riječ ne može upisati.
	 * @throws UnknownAddressException
	 *             Ukoliko ne postoji <code>localAddress</code> na jedinici.
	 */
	public void requestWrite(IBus bus, Address localAddress, Word word) throws IllegalActionException, UnknownAddressException;

	/**
	 * Postavljanje zahtjeva za čitanje 1 riječi sa jedinice na sabirnici.
	 * 
	 * @param bus sabrnici koja postavlja zahtjev, uobičajeno <code>this</code>
	 * @param localAddress lokalna adresa (adresa na jedinici)
	 * 
	 * @throws IllegalActionException
	 *             Ukoliko nije moguće pročitati riječ.
	 * @throws UnknownAddressException
	 *             Ukoliko ne postoji <code>localAddress</code> na jedinici.
	 */
	public void requestRead(IBus bus, Address localAddress) throws IllegalActionException, UnknownAddressException;
	
	/**
	 * Ispituje da li je vanjska jedinica zauzeta. Zauzeta vanjska jedinica ne može primati više operacija.
	 * 
	 * @return true ako je zauzeta, false inače
	 */
	public boolean isBusy();
}
