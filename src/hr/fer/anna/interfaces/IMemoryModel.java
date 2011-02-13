package hr.fer.anna.interfaces;

import hr.fer.anna.exceptions.IllegalActionException;
import hr.fer.anna.exceptions.UnknownAddressException;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Address;

/**
 * Sučelje memorijskog modela. Svaka kontrola koja sadržava veću količinu memorijskog
 * prostora u sebi i ne želi mappirati registre može koristiti memorijski model.
 */
public interface IMemoryModel {
	
	/**
	 * Registrira novog slušača promjene memorijskog modela.
	 * @param watcher slušač
	 */
	public void registerWatcher(IMemoryModelWatcher watcher);
	
	/**
	 * Odregistrira slušača promjene memorijskog modela.
	 * @param watcher slušač
	 */
	public void unregisterWatcher(IMemoryModelWatcher watcher);
	
	/**
	 * Zapisuje zadanu riječ na zadanu adresu memorijskog spremišta.
	 * @param word riječ
	 * @param address adresa
	 * @throws UnknownAddressException u slučaju da ne postoji zadana adresa
	 * @throws IllegalActionException u slučaju da nije moguće pisati po zadanoj adresi
	 */
	public void write(Word word, Address address) throws UnknownAddressException, IllegalActionException;

	/**
	 * Zapisuje slijedno više riječi počevši od zadane početne adrese
	 * @param words riječi
	 * @param address početna adresa
	 * @throws UnknownAddressException u slučaju da neka od adresa ne postoji
	 * @throws IllegalActionException u slučaju da nije moguće zapisati na neku adresu
	 */
	public void write(Word[] words, Address address) throws UnknownAddressException, IllegalActionException;
	
	/**
	 * Čita riječ sa zadane adrese memorijskog spremišta
	 * @param address adresa
	 * @return riječ koje se nalazi na zadanoj adresi
	 * @throws UnknownAddressException u slučaju da ne postoji zadana adresa
	 * @throws IllegalActionException u slučaju da nije moguće čitati zadanu adresu
	 */
	public Word read(Address address) throws UnknownAddressException, IllegalActionException;
}
