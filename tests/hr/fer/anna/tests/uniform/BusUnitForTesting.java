package hr.fer.anna.tests.uniform;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;

import hr.fer.anna.exceptions.IllegalActionException;
import hr.fer.anna.exceptions.UnknownAddressException;
import hr.fer.anna.interfaces.IBus;
import hr.fer.anna.interfaces.IBusUnit;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Address;

/**
 * Klasa vanjske jedinice koja se koristi za testiranje.
 * @author boran
 *
 */
public class BusUnitForTesting implements IBusUnit {
	
	/**
	 * Adresa za koju očekujemo da će joj sabirnica pristupiti.
	 */
	private Address expectedAddress;

	/**
	 * Broj lokalnih adresa ove jedinice.
	 */
	private int localAddresses;
	
	/**
	 * Da li jedinica treba odgovoriti na zahtjev? True ako da, false inače.
	 */
	private boolean doCallback;
	
	/**
	 * Adresabilno spremište podataka.
	 */
	private Map<Address, Word> data;

	/**
	 * Konstruktor koji stvara vanjsku jedinicu zadanog raspona lokalnih adresa
	 * @param localAddresses raspon (broj) lokalnih adresa
	 */
	public BusUnitForTesting(int localAddresses) {
		this.localAddresses = localAddresses;
		
		this.data = new LinkedHashMap<Address, Word>(localAddresses);
	}
	
	public int getLocalAddresses() {
		return this.localAddresses;
	}

	public boolean isBusy() {
		return false;
	}

	public void requestRead(IBus bus, Address localAddress)
			throws IllegalActionException, UnknownAddressException {

		Assert.assertEquals("Krivoj adresi se pristupa!", this.expectedAddress, localAddress);
	
		if (this.doCallback) {
			if (!data.containsKey(localAddress)) {
				data.put(localAddress, new Word());
			}
			
			bus.busUnitReadCallback(this, localAddress, data.get(localAddress));
		}
	}

	public void requestWrite(IBus bus, Address localAddress, Word word)
			throws IllegalActionException, UnknownAddressException {
		
		Assert.assertEquals("Krivoj adresi se pristupa!", this.expectedAddress, localAddress);
	
		if (this.doCallback) {
			if (!data.containsKey(localAddress)) {
				data.put(localAddress, new Word());
			}
			
			data.get(localAddress).set(word);
			
			bus.busUnitWriteCallback(this, localAddress);
		}
	}
	
	/**
	 * Postavlja da li jedinica treba odgovoriti na zahtjeve sabirnice
	 * @param state true ako treba, false inače
	 */
	public void setDoCallback(boolean state) {
		this.doCallback = state;
	}

	/**
	 * Postavlja lokalnu adresu koju očekujemo da nam sabirnica mapira.
	 * @param expectedAddress lokalna adresa (na jedinici)
	 */
	public void setExpectedAddress(Address expectedAddress) {
		this.expectedAddress = expectedAddress;
	}

}
