package hr.fer.anna.tests.uniform;

import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Constant;

import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testira klasu konstanata
 * @author Boran
 *
 */
public class ConstantTest {

	/** Broj testova */
	private static final int NUM_OF_TEST = 0;

	/** Generator slučajnih brojeva */
	private Random random;
	
	/**
	 * Inicijalizacija
	 */
	@Before
	public void init() {
		this.random = new Random();
	}
	
	/**
	 * Testira ispravno zapisivanje konstanata kao riječi
	 */
	@Test
	public void testData() {

		int i;
		
		for (i = 0; i < NUM_OF_TEST; i++) {
			byte a = (byte)(random.nextInt() & 0x7F);
			
			Word word = new Constant(a);
			
			Assert.assertEquals("Zapisi nisu jednaki!", a, Byte.parseByte(word.getHexString(), 16));
		}

		for (i = 0; i < NUM_OF_TEST; i++) {
			int a = random.nextInt() & 0x7FFFFFFF; 

			Word word = new Constant(a);
			
			Assert.assertEquals("Zapisi nisu jednaki!", a, Integer.parseInt(word.getHexString(), 16));
		}

		for (i = 0; i < NUM_OF_TEST; i++) {
			long a = random.nextLong() & 0x7FFFFFFFFFFFFFFFL;

			Word word = new Constant(a);
			
			Assert.assertEquals("Zapisi nisu jednaki!", a, Long.parseLong(word.getHexString(), 16));
		}
	}
}
