package hr.fer.anna.tests.uniform;

import org.junit.Assert;

import hr.fer.anna.exceptions.BusAddressTaken;
import hr.fer.anna.exceptions.IllegalActionException;
import hr.fer.anna.exceptions.UnknownAddressException;
import hr.fer.anna.interfaces.IBus;
import hr.fer.anna.interfaces.IBusMaster;
import hr.fer.anna.interfaces.IBusUnit;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Address;

public class BusForTesting implements IBus {

	/**
	 * Vanjska jedinica koja se testira
	 */
	private IBusUnit expectedBusUnit;
	
	/**
	 * Lokalna adresa kojoj treba biti pristupljeno
	 */
	private Address expectedAddress;
	
	/**
	 * Riječ koja se treba dohvatiti s jedinice
	 */
	private Word expectedWord;

	/**
	 * Postavlja lokalnu adresu koju trebamo dobiti
	 * @param expectedAddress lokalna adresa na jedinici
	 */
	public void setExpectedAddress(Address expectedAddress) {
		this.expectedAddress = expectedAddress;
	}

	/**
	 * Postavlja jedinicu kojoj je sabirnica trebala pristupiti
	 * @param expectedBusUnit jedinica
	 */
	public void setExpectedBusUnit(IBusUnit expectedBusUnit) {
		this.expectedBusUnit = expectedBusUnit;
	}

	/**
	 * Postavlja riječ koja je trebala biti pročitana
	 * @param expectedWord očekivana riječ
	 */
	public void setExpectedWord(Word expectedWord) {
		this.expectedWord = expectedWord;
	}

	public void busUnitReadCallback(IBusUnit busUnit, Address localAddress, Word word) {
		Assert.assertEquals("S krive jedinice je pročitan podatak!", this.expectedBusUnit, busUnit);
		Assert.assertEquals("S krive adrese je pročitan podatak!", this.expectedAddress, localAddress);
		
		if(this.expectedWord != null) {
			Assert.assertEquals("Neispravno čitanje podatka!", this.expectedWord, word);
		}
	}

	public void busUnitWriteCallback(IBusUnit busUnit, Address localAddress) {
		Assert.assertEquals("Na krivu jedinicu je zapisan podatak!", this.expectedBusUnit, busUnit);
		Assert.assertEquals("Na krivu adresu je zapisan podatak!", this.expectedAddress, localAddress);
	}
	
	public IBusMaster getBusMaster() {
		return null;
	}

	public boolean isBusy() {
		return false;
	}

	public void registerBusUnit(IBusUnit busUnit, Address startAddress) throws UnknownAddressException, BusAddressTaken {
		// Nepotrebno
	}

	public void requestRead(IBusMaster busMaster, Address globalAddress) throws UnknownAddressException, IllegalActionException {
		// Nepotrebno
	}

	public void requestWrite(IBusMaster busMaster, Address globalAddress, Word word) throws UnknownAddressException, IllegalActionException {
		// Nepotrebno
	}

	public void setBusMaster(IBusMaster newBusMaster) throws IllegalActionException {
		// Nepotrebno
	}
}
