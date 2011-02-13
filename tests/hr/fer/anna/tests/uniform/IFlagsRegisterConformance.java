package hr.fer.anna.tests.uniform;

import hr.fer.anna.interfaces.IFlagsRegister;

import org.junit.Assert;

/**
 * Klasa koja implementira metode za provjeru i testiranje nekog registra. Testira se
 * zadovoljanje sučelja IFlagsRegister i njihova ispravna implementacija. Testovi su
 * osmišljeni tako da se pozivaju unutar testova određenog registra.
 * @author Boran
 *
 */
public class IFlagsRegisterConformance {
	
	/**
	 * Testira postavljanje i brisanje carry zastavice.
	 */
	public static void testCarry(IFlagsRegister register) {
		register.setCarry(true);	
		Assert.assertEquals("Postavljanje carry ne radi!", true, register.getCarry());
		
		register.setCarry(false);	
		Assert.assertEquals("Brisanje carry ne radi!", false, register.getCarry());
	}
	
	/**
	 * Testira postavljanje i brisanje equal zastavice.
	 */
	public static void testZero(IFlagsRegister register) {
		register.setZero(true);	
		Assert.assertEquals("Postavljanje zero ne radi!", true, register.getZero());
		
		register.setZero(false);	
		Assert.assertEquals("Brisanje zero ne radi!", false, register.getZero());
	}
	
	/**
	 * Testira postavljanje i brisanje overflow zastavice.
	 */
	public static void testOverflow(IFlagsRegister register) {
		register.setOverflow(true);	
		Assert.assertEquals("Postavljanje overflow ne radi!", true, register.getOverflow());
		
		register.setOverflow(false);	
		Assert.assertEquals("Brisanje overflow ne radi!", false, register.getOverflow());
	}
	
	/**
	 * Testira postavljanje i brisanje positive i negative zastavica i njihovu međusobnu ovisnost.
	 * Positive je ekvivalentan komplementu negative i to mora vrijediti uvijek.
	 */
	public static void testPositiveNegative(IFlagsRegister register) {
		register.setPositive();
		Assert.assertEquals("Postavljanje positive ne radi!", true, register.getPositive());
		Assert.assertEquals("Positive i negative nisu dobro povezani!", false, register.getNegative());
		
		register.setNegative();
		Assert.assertEquals("Postavljanje negative ne radi!", true, register.getNegative());
		Assert.assertEquals("Negative i positive nisu dobro povezani!", false, register.getPositive());
	}
}
