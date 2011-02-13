package hr.fer.anna.tests.uniform;

import org.junit.Assert;

import hr.fer.anna.interfaces.IBus;
import hr.fer.anna.interfaces.IBusMaster;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Address;

public class BusMasterForTesting implements IBusMaster {

	/** Očekivana adresa */
	private Address expectedAddress;
	
	/** Očekivana riječ */
	private Word expecetedWord;
	
	/** Očekivana sabirnica */
	private IBus expectedBus;
	
	/**
	 * 
	 * @param expecetedWord
	 */
	public void setExpecetedWord(Word expecetedWord) {
		this.expecetedWord = expecetedWord;
	}

	public void setExpectedAddress(Address expectedAddress) {
		this.expectedAddress = expectedAddress;
	}

	public void busReadCallback(IBus bus, Address globalAddress, Word word) {
		if (this.expectedBus != null) {
			Assert.assertEquals("Sabirnica nije jednaka očekivanoj!", this.expectedBus, bus);
		}
		
		if (this.expectedAddress != null) {
			Assert.assertEquals("Adresa nije jednaka očekivanoj!", this.expectedAddress, globalAddress);
		}
		
		if (this.expecetedWord != null) {
			Assert.assertEquals("Riječ nije jednaka očekivanoj!", this.expecetedWord, word);
		}
	}

	public void busWriteCallback(IBus bus, Address globalAddress) {
		if (this.expectedBus != null) {
			Assert.assertEquals("Sabirnica nije jednaka očekivanoj!", this.expectedBus, bus);
		}
		
		if (this.expectedAddress != null) {
			Assert.assertEquals("Adresa nije jednaka očekivanoj!", this.expectedAddress, globalAddress);
		}
	}

	public void waitBus(IBus bus, boolean state) {

	}
}
