package hr.fer.anna.tests.model;

import java.util.Random;

import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Constant;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testovi elementarne riječi
 * @author Boran
 *
 */
public class WordTest {

	/** Broj numeričkih testova (zbrajanje, oduzimanje, ...) */
	private static final int NUM_OF_NUMERIC_TESTS = 1000;
	
	/** Generator pseudo-slučajnih brojeva */
	private Random random;
	
	/**
	 * Inicijalizacija (generator slučajnih brojeva, ...)
	 */
	@Before
	public void init() {
		this.random = new Random();
	}
	
	/**
	 * Provjerava ispitivanje jednakosti 2 riječi
	 */
	@Test
	public void testEquals() {
		Word wordA = new Constant(16);
		
		Word wordB = new Constant(16);
		
		Assert.assertEquals("Riječi trebaju biti jednake!", true, wordA.equals(wordB));
		
		wordA = new Constant(15600);
		
		wordB = new Constant(15600);
		
		Assert.assertEquals("Riječi trebaju biti jednake!", true, wordA.equals(wordB));
		
		wordA = new Constant(32600);
		
		wordB = new Constant(15600);
		
		Assert.assertEquals("Riječi ne smiju biti jednake!", false, wordA.equals(wordB));		
	}
	
	/**
	 * Provjerava ispravnost metode getHexString kao i sam zapis vrijednosti
	 * unutar riječi
	 */
	@Test
	public void testHexString() {
		
		for (int i = 0; i < NUM_OF_NUMERIC_TESTS; i++) {
			long a = random.nextLong();
			
			Word wordA = new Constant(a);
			
			Assert.assertEquals("Zapis ili getHexString() nije dobro implementiran!", Long.toHexString(a).toUpperCase(), wordA.getHexString().toUpperCase());			
		}			
	}
	
	/**
	 * Testira postavljanje riječi
	 *
	 */
	@Test
	public void testSet() {
		
		for (int i = 0; i < NUM_OF_NUMERIC_TESTS; i++) {
			long a = random.nextLong();
			
			Word expected = new Constant(a);
			Word actual = new Word(expected.getWidth());
			
			actual.set(expected);
			
			Assert.assertEquals("Postavljanje nije dobro implementirano!", expected, actual);			
		}			
	}
	
	/**
	 * Testira operaciju zbrajanja riječi
	 */
	@Test
	public void testAdd() {
		
		for (int i = 0; i < NUM_OF_NUMERIC_TESTS; i++) {
			long a = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
			long b = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
			
			Word wordA = new Constant(a);
			Word wordB = new Constant(b);
			
			Word actual = Word.add(wordA, wordB);
			Word expected = new Constant(a+b);
			
			Assert.assertEquals("Zbrojevi nisu jednaki!", expected, actual);
		}
	}

	/**
	 * Testira operaciju oduzimanja riječi
	 */
	@Test
	public void testSub() {
		
		for (int i = 0; i < NUM_OF_NUMERIC_TESTS; i++) {
			long a = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
			long b = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
			
			Word wordA = new Constant(a);
			Word wordB = new Constant(b);
			
			Word actual = Word.sub(wordA, wordB);
			Word expected = new Constant(a-b);
			
			Assert.assertEquals("Razlike nisu jednaki!", expected, actual);
		}
	}
	
	/**
	 * Testira operaciju posmicanja ulijevo
	 */
	@Test
	public void testShiftLeft() {
		
		int i;
		
		System.out.println("8-bit tests:");
		
		for (i = 0; i < NUM_OF_NUMERIC_TESTS; i++) {
			byte a = (byte) (random.nextInt() & 0x0F);
			int loc = random.nextInt() & 0x07;
			
			Word wordA = new Constant(a);
			
			Word actual = Word.shiftLeft(wordA, loc);
			Word expected = new Constant((byte)((a & 0xFF) << loc));

			System.out.println(Integer.toHexString(a) + " << " + loc);
			
			Assert.assertEquals("Posmak ulijevo nije dobro implementiran!", expected, actual);
		}
		
		System.out.println("32-bit tests:");
		
		for (i = 0; i < NUM_OF_NUMERIC_TESTS; i++) {
			int a = random.nextInt();
			int loc = random.nextInt() & 0x1F;
			
			Word wordA = new Constant(a);
			
			Word actual = Word.shiftLeft(wordA, loc);
			Word expected = new Constant((a << loc));
			
			System.out.println(Integer.toHexString(a) + " << " + loc);
			
			Assert.assertEquals("Posmak ulijevo nije dobro implementiran!", expected, actual);
		}
		
		System.out.println("64-bit tests:");
		
		for (i = 0; i < NUM_OF_NUMERIC_TESTS; i++) {
			long a = random.nextLong();
			int loc = random.nextInt() & 0x3F;
			
			Word wordA = new Constant(a);
			
			Word actual = Word.shiftLeft(wordA, loc);
			Word expected = new Constant((long)(a << loc));

			System.out.println(Long.toHexString(a) + " << " + loc);			
			
			Assert.assertEquals("Posmak ulijevo nije dobro implementiran!", expected, actual);
		}
		
	}
	
	/**
	 * Testira operaciju posmicanja udesno
	 */
	@Test
	public void testShiftRight() {
		
		int i;
		
		System.out.println("8-bit tests:");
		
		for (i = 0; i < NUM_OF_NUMERIC_TESTS; i++) {
			byte a = (byte) (random.nextInt() & 0x0F);
			int loc = random.nextInt() & 0x07;
			
			Word wordA = new Constant(a);
			
			Word actual = Word.shiftRight(wordA, loc);
			Word expected = new Constant((byte)(a >>> loc));

			System.out.println(Integer.toHexString(a) + " >> " + loc);
			
			Assert.assertEquals("Posmak udesno nije dobro implementiran!", expected, actual);
		}
		
		System.out.println("32-bit tests:");
		
		for (i = 0; i < NUM_OF_NUMERIC_TESTS; i++) {
			int a = random.nextInt();
			int loc = random.nextInt() & 0x1F;
			
			Word wordA = new Constant(a);
			
			Word actual = Word.shiftRight(wordA, loc);
			Word expected = new Constant((int)(a >>> loc));
			
			System.out.println(Integer.toHexString(a) + " >> " + loc);
			
			Assert.assertEquals("Posmak udesno nije dobro implementiran!", expected, actual);
		}
		
		System.out.println("64-bit tests:");
		
		for (i = 0; i < NUM_OF_NUMERIC_TESTS; i++) {
			long a = random.nextLong();
			int loc = random.nextInt() & 0x3F;
			
			Word wordA = new Constant(a);
			
			Word actual = Word.shiftRight(wordA, loc);
			Word expected = new Constant((long)(a >>> loc));

			System.out.println(Long.toHexString(a) + " >> " + loc);			
			
			Assert.assertEquals("Posmak udesno nije dobro implementiran!", expected, actual);
		}
	}
	
	/**
	 * Testira invertiranje (logički not)
	 */
	@Test
	public void testInvert() {
		
		for (int i = 0; i < NUM_OF_NUMERIC_TESTS; i++) {
			long a = random.nextLong();
			
			Word wordA = new Constant(a);
			
			Word actual = Word.not(wordA);
			Word expected = new Constant(~a);
			
			Assert.assertEquals("NOT operacija nije dobro implementirana!", expected, actual);			
		}		
	}
	
	/**
	 * Testira provođenje ostalih logičkih operacija (xor, or, and)
	 */
	@Test
	public void testLogical() {
		
		for (int i = 0; i < NUM_OF_NUMERIC_TESTS; i++) {
			long a = random.nextLong();
			long b = random.nextLong();
			
			Word wordA = new Constant(a);
			Word wordB = new Constant(b);
			
			Word actual = Word.xor(wordA, wordB);
			Word expected = new Constant(a ^ b);
			
			Assert.assertEquals("XOR operacija nije dobro implementirana!", expected, actual);
		
			actual = Word.or(wordA, wordB);
			expected = new Constant(a | b);
			
			Assert.assertEquals("OR operacija nije dobro implementirana!", expected, actual);
			
			actual = Word.and(wordA, wordB);
			expected = new Constant(a & b);
			
			Assert.assertEquals("AND operacija nije dobro implementirana!", expected, actual);
			
		}
	}
}
