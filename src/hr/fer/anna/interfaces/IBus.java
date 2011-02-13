package hr.fer.anna.interfaces;

import hr.fer.anna.exceptions.BusAddressTaken;
import hr.fer.anna.exceptions.IllegalActionException;
import hr.fer.anna.exceptions.UnknownAddressException;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Address;

/**
 * Sučelje sabirnice.
 * @author Boran
 *
 */
public interface IBus {

	/**
	 * Registriranje jedinica s kojih sabirnica čita podatke i na koje sabirnica
	 * piše podatke.
	 * 
	 * @param busUnit jedinica koju treba registrirati
	 * @param startAddress zahtjevana početna adresa jedinice
	 * 
	 * @throws UnknownAddressException Ukoliko na sabirnici ne postoji adresa na koju bi se preslikala neka od lokalnih adresa jedinice
	 * 
	 * @throws BusAddressTaken Ukoliko je na sabirnici već zauzeta neka od adresa na koju bi se jedinica preslikala.
	 */
	public void registerBusUnit(IBusUnit busUnit, Address startAddress) throws UnknownAddressException, BusAddressTaken;

	/**
	 * Postavljanje zahtjeva za pisanje na sabirnicu.
	 * 
	 * @param busMaster upravljač sabirnice koji postavlja zahtjev, obično <code>this</code>
	 * @param globalAddress adresa (na sabirnici) na koju se želi pisati
	 * @param word riječ koja se želi upisati
	 * 
	 * @throws UnknownAddressException Ukoliko adresa ne postoji na sabirnici.
	 * @throws IllegalActionException Ukoliko nije moguće pisati na traženu adressu.
	 */
	public void requestWrite(IBusMaster busMaster, Address globalAddress, Word word) throws UnknownAddressException, IllegalActionException;

	/**
	 * Postavljanje zahtjeva za čitanjem sa sabirnice.
	 * 
	 * @param busMaster upravljač sabirnice koji postavlja ovaj zahjev, obično <code>this</code>
	 * @param globalAddress adresa (na sabirnici) s koje se želi čitati
	 * 
	 * @throws UnknownAddressException Ukoliko adresa na postoji na sabirnici.
	 * @throws IllegalActionException Ukoliko nije moguće čitati s tražene adrese.
	 */
	public void requestRead(IBusMaster busMaster, Address globalAddress) throws UnknownAddressException, IllegalActionException;
	
	/**
	 * Dojava sabirnici da je završeno upisivanje na jedinicu.
	 * 
	 * @param busUnit jedinica koja dojavljuje završetak, obično <code>this</code>
	 * @param localAddress lokalna adresa na jedinici
	 */
	public void busUnitWriteCallback(IBusUnit busUnit, Address localAddress);
	
	/**
	 * Dojava sabirnici da je završeno čitanje i slanje sabirnici pročitane riječi.
	 * 
	 * @param busUnit jedinica koja dojavljuje završetak, obično <code>this</code>
	 * @param localAddress lokalna adresa na jedinici
	 * @param word pročitana riječ
	 */
	public void busUnitReadCallback(IBusUnit busUnit, Address localAddress, Word word);
	
	/**
	 * Da li je trenutno sabirnica zauzeta, da li izvršava neku akciju čitanja ili pisanja sa neke
	 * jedinice.
	 * 
	 * @return true ako je bus zauzet, false inače
	 */
	public boolean isBusy();
	
	/**
	 * Dohvaća trenutnog upravljača ovom sabirnicom.
	 * 
	 * @return upravljač sabirnice
	 */
	public IBusMaster getBusMaster();
	
	/**
	 * Postavlja novog upravljača ovom sabirnicom.
	 * 
	 * @param newBusMaster novi upravljač sabirnice
	 * 
	 * @throws IllegalActionException Ako je sabirnica zauzeta ili ako ju trenutni upravljač ne želi pustiti.
	 */
	public void setBusMaster(IBusMaster newBusMaster) throws IllegalActionException;
}
