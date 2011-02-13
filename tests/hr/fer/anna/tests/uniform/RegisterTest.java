package hr.fer.anna.tests.uniform;

import hr.fer.anna.uniform.Constant;
import hr.fer.anna.uniform.Register;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testovi registara
 * 
 * @author Boran
 * 
 */
public class RegisterTest {
	
	/**
	 * Testira postavljanje bitova unutar registra
	 */
	@Test
	public void runSetBitTest() {
		Register reg = new Register(64);
		
		long value = 1;
		
		for (int index = 0; index < 64; value = 2*value, index++) {
			reg.setBit(index, true);
			Assert.assertEquals("Neispravno postavljanje bitova! Bit na poziciji " + index + " nije postavljen!", Long.toHexString(value).toUpperCase(), reg.getHexString().toUpperCase());
			reg.setBit(index, false);
		}
	}
	
	/**
	 * Testira dohvaćanje bita unutar registra
	 */
	@Test
	public void runGetBitTest() {
		Register reg = new Register(64);
		
		long value = 1;
		
		for (int index = 0; index < 64; value = 2*value, index++) {
			reg.set(new Constant(value));
			Assert.assertEquals("Neispravno dohvaćanje bitova! Bit na poziciji " + index + " nije postavljen!", true, reg.getBit(index));
		
			for (int others = 0; others < 64; others++) {
				if (others != index) {
					Assert.assertEquals("Neispravno dohvaćanje bitova! Bit na poziciji " + others + " ne smije biti postavljen!", false, reg.getBit(others));
				}
			}
		}
	}
}
