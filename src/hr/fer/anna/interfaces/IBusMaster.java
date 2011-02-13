package hr.fer.anna.interfaces;

import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Address;

/**
 * Komponente koje čitaju sa sabirnice moraju implementirati ovo sučelje jer ovo sučelje sadrži
 * povratnu vezu i metode za sinkronizirano čitanje.
 * @author Boran
 */

public interface IBusMaster {

	/**
	 * Sabirnica ovom metodom postavlja čekanje upravljaču sve dok opet nije u stanju
	 * prihvaćati zahtjeve upravljača.
	 * 
	 * @param bus sabirnica koja postavlja čekanje, obično <code>this</code>
	 * @param state true za postavljanje čekanja, false za isključivanje čekanja
	 */
	public void waitBus(IBus bus, boolean state);
	
	/**
	 * Sabirnica ovom metodom dojavljuje završetak čitanja i vraća pročitanu riječ.
	 * 
	 * @param bus sabirnica koja javlja završetak, obično <code>this</code>
	 * @param globalAddress adresa s koje se čitalo
	 * @param word pročitana riječ
	 */
	public void busReadCallback(IBus bus, Address globalAddress, Word word);
	
	/**
	 * Sabirnica ovom metodom dojavljuje završetak pisanja.
	 * 
	 * @param bus sabirnica koja javlja završetak, obično <code>this</code>
	 * @param globalAddress adresa na koju se pisalo
	 */
	public void busWriteCallback(IBus bus, Address globalAddress);
}
