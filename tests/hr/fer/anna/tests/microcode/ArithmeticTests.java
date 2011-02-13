package hr.fer.anna.tests.microcode;

import java.util.Random;

import hr.fer.anna.interfaces.IFlagsRegister;
import hr.fer.anna.microcode.AddMicroinstruction;
import hr.fer.anna.microcode.SubtractMicroinstruction;
import hr.fer.anna.oisc.StatusRegister;
import hr.fer.anna.uniform.Constant;
import hr.fer.anna.uniform.Register;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Testovi ALU mikrokoda.
 * @author Boran
 *
 */
public class ArithmeticTests {

	/** Broj testova */
	private static final int NUM_OF_TESTS = 1000;
	
	/** Širina općih registara koje koristimo (u bitovima) */
	private static final int REGISTER_WIDTH = 32;
	
	/**
	 * Registar zastavica koji se koristi prilikom testiranja. ALU naredbe postavljaju
	 * zastavice tokom svog izvršavanja.
	 */
	private static IFlagsRegister flagsRegister;
	
	/**
	 * Općeniti registar 1 koji služi za testiranje.
	 */
	private static Register regA;
	
	/**
	 * Općeniti registar 2 koji služi za testiranje.
	 */
	private static Register regB;
	
	/**
	 * Generator slučajnih brojeva
	 */
	private static Random random;
	
	/**
	 * Inicijalizacija registra zastavica i općenitih registara.
	 */
	@BeforeClass
	public static void setUp() {
		// Koristimo OISC-ov status registar kao registar zastavica
		flagsRegister = new StatusRegister();
		
		regA = new Register(REGISTER_WIDTH);
		regB = new Register(REGISTER_WIDTH);
		
		random = new Random();
	}
	
	/**
	 * Testira aritmetičku operaciju zbrajanja nad registrima.
	 */
	@Test
	public void testAdd() {
	
		Register result = new Register(REGISTER_WIDTH);
		
		for (int i = 0; i < NUM_OF_TESTS; i++) {
			int a = random.nextInt() & 0x3FFFFFFF;
			int b = random.nextInt() & 0x3FFFFFFF;
			
			regA.set(new Constant(a));
			regB.set(new Constant(b));
			
			try {
				new AddMicroinstruction(result, regA, regB, flagsRegister).execute();
			} catch (Exception e) {
				Assert.fail(e.getLocalizedMessage());
			}
			
			Assert.assertEquals("Oduzimanje ne radi pravilno!", Integer.toHexString(a+b).toUpperCase(), result.getHexString().toUpperCase());
			Assert.assertEquals("Zastavica negative nije pravilno postavljena!", (a+b) < 0, flagsRegister.getNegative());
			Assert.assertEquals("Zastavica greške nije ispravno postavljena!", false, flagsRegister.getOverflow());
		}
	}

	/**
	 * Testira aritmetičku operaciju oduzimanja nad registrima.
	 */
	@Test
	public void testSubtract() {

		Register result = new Register(REGISTER_WIDTH);
		
		for (int i = 0; i < NUM_OF_TESTS; i++) {
			int a = random.nextInt() & 0x3FFFFFFF;
			int b = random.nextInt() & 0x3FFFFFFF;
			
			regA.set(new Constant(a));
			regB.set(new Constant(b));
			
			try {
				new SubtractMicroinstruction(result, regA, regB, flagsRegister).execute();
			} catch (Exception e) {
				Assert.fail(e.getLocalizedMessage());
			}
			
			Assert.assertEquals("Oduzimanje ne radi pravilno!", Integer.toHexString(a-b).toUpperCase(), result.getHexString().toUpperCase());
			Assert.assertEquals("Zastavica negative nije pravilno postavljena!", (a-b) < 0, flagsRegister.getNegative());
			Assert.assertEquals("Zastavica greške nije ispravno postavljena!", false, flagsRegister.getOverflow());
		}
	}
	
	/**
	 * Testira nastupanje greške tokom računanja dvaju vrijednosti. Greška nastupa kada su
	 * predznaka obaju operanada ista, a predznak rezultata različit.
	 */
	@Test
	public void testOverflow() {
		
		Register result = new Register(REGISTER_WIDTH);
		
		for (int i = 0; i < NUM_OF_TESTS; i++) {
			int a = random.nextInt();
			int b = random.nextInt();
			
			regA.set(new Constant(a));
			regB.set(new Constant(b));
			
			try {
				new AddMicroinstruction(result, regA, regB, flagsRegister).execute();
			} catch (Exception e) {
				Assert.fail(e.getLocalizedMessage());
			}
			
			boolean overflow = (a > 0 && b > 0 && (a+b) < 0) || (a < 0 && b < 0 && (a+b) > 0);
			
			Assert.assertEquals("Testiranje greške ne radi pravilno!", overflow, flagsRegister.getOverflow());
		}
	}
}
