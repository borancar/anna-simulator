package hr.fer.anna.model;

import hr.fer.anna.interfaces.IWordChangeListener;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Riječ je elementarna jedinica modela za zapisivanje podataka. Varijabilne je širine (u bitovima).
 * @author Boran
 *
 */
public class Word {
	
	/** Podatkovni bitovi grupirani u 24-bitne grupe (int se koristi radi predznaka i drugih ograničenja VM-a) */
	protected int[] data;
	
	/** Predefinirana veličina grupe - 24-bita */
	protected final int groupSize = 24;
	
	/** Broj 24-bitnih grupa koje riječ zauzima */
	protected int groups;
	
	/** Širina (u bitovima) */
	protected int width;

	/** Bit viška pri određenim operacijama, služi kasnije za aritmetičke i logičke operacije */
	protected boolean excessBit;
	
	/** Slušači promjene vrijednosti podatkovne riječi. */
	protected Set<IWordChangeListener> listeners;
		
	/**
	 * Defaultni konstruktor, stvara podatkovnu riječ širine 32 bita
	 */
	public Word() {
		this(32);
	}

	/**
	 * Konstrukor koji stvara podatkovnu riječ zadane širine
	 * @param width širina (u bitovima)
	 */
	public Word(int width) {
		this.width = width;
		
		groups = (int) Math.round(Math.ceil(width / (double) groupSize));
		
		data = new int[groups];
		
		excessBit = false;
	}
	
	/**
	 * Stvara podatkovnu riječ istog sadržaja (kloniranog) kao predana podatkovna riječ
	 * @param word podatkovna riječ
	 */
	public Word(Word word) {
		this.groups = word.groups;
		this.excessBit = false;
		this.width = word.width;
		this.data = word.data.clone();
	}
	
	/**
	 * Dohvaća širinu podatkovne riječi.
	 * 
	 * @return Širina podatkovne riječi (u bitovima)
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Registriranje slušača promjene vrijednosti podatkovne riječi.
	 * 
	 * @param listener Pratitelj promjene koji se registrira.
	 */
	public void registerListener(IWordChangeListener listener) {
		if (this.listeners == null) {
			this.listeners = new HashSet<IWordChangeListener>();
		}
	
		this.listeners.add(listener);
	}

	/**
	 * Micanje registracije slušača promjene vrijednosti podatkovne riječi.
	 * 
	 * @param listener Pratitelj promjene koji miče registraciju.
	 */
	public void unregisterListener(IWordChangeListener listener) {
		if (this.listeners == null) {
			return;
		}
	
		this.listeners.remove(listener);
	}

	/**
	 * Javljanje slušačima da je došlo do promjene.
	 */
	protected void updateListeners() {
		if (this.listeners == null) {
			return;
		}
	
		for (IWordChangeListener listener : this.listeners) {
			listener.update(this);
		}
	}
	
	/**
	 * Postavlja novu vrijednost podatkovne riječi (klonira predanu). Ovu metodu
	 * je preporučeno koristiti za postavljanje riječi. Samo u rijetkim slučajevima
	 * je dozvoljeno pridruživanje reference jer to može dovesti do neželjenih posljedica.
	 * @param word nova vrijednost
	 * @return riječ
	 */
	public Word set(Word word) {
		if (word.width != this.width) {
			throw new IllegalArgumentException("Širina nove vrijednosti je veća od širine podatkovne riječi! Nije moguće pridružiti novu vrijednost!");
		}
		
		for (int i = 0; i < this.data.length; i++) {
			this.data[i] = word.data[i];
		}
		
		updateListeners();
		
		return this;
	}
	
	/**
	 * Vraća vrijednost riječi zapisanu kao heksadecimalni broj
	 * @return hex-vrijednost riječi
	 */
	public String getHexString() {
		StringBuilder sb = new StringBuilder((int) Math.round(Math.ceil(this.width / 8.0)));
		
		int i = this.groups - 1;
		
		while (this.data[i] == 0 && i > 0) {
			i--;
		}

		sb.append(Integer.toHexString(this.data[i]));

		for (i--; i >= 0; i--) {
			for (int shift = 20; shift >= 0; shift -= 4) {
				int hex = (this.data[i] >>> shift) & 0xF;
				
				if (hex < 10) {
					sb.append(hex);
				} else {
					sb.append((char)((int)'a' + hex - 10));
				}
			}
		}
		
		return sb.toString().toUpperCase();
	}
	
	/**
	 * Dvije su podatkovne riječi jednake ako su iste širine i istog su sadržaja
	 * (bitovi na pozicijama <code>i</code> su jednaki).
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Word)) {
			return false;
		}
		
		Word other = (Word) obj;
		
		if (this.width != other.width) {
			return false;
		}
		
		return Arrays.equals(this.data, other.data);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(this.data);
	}
	
	/**
	 * Obavlja operaciju <code>or</code> između podatkovnih riječi i vraća rezultat.
	 * @param word1 prvi operand
	 * @param word2 drugi operand
	 * @return rezultat je bitwise-or podatkovnih riječi
	 */
	public static Word or(Word word1, Word word2) {
		assertEqualWordLengths(word1, word2);
		
		Word res = new Word(word1.width);
		
		for (int i = 0; i < res.data.length; i++) {
			res.data[i] = (word1.data[i] | word2.data[i]);
		}
		
		return res;
	}
	
	/**
	 * Obavlja operaciju <code>and</code> između podatkovnih riječi i vraća rezultat.
	 * @param word1 prvi operand
	 * @param word2 drugi operand
	 * @return rezultat je bitwise-and podatkovnih riječi
	 */
	public static Word and(Word word1, Word word2) {
		assertEqualWordLengths(word1, word2);
		
		Word res = new Word(word1.width);
		
		for (int i = 0; i < res.data.length; i++) {
			res.data[i] = (word1.data[i] & word2.data[i]);
		}
		
		return res;
	}
	
	/**
	 * Obavlja operaciju <code>xor</code> između podatkovnih riječi i vraća rezultat.
	 * @param word1 prvi operand
	 * @param word2 drugi operand
	 * @return rezultat je bitwise-xor podatkovnih riječi
	 */
	public static Word xor(Word word1, Word word2) {
		assertEqualWordLengths(word1, word2);
		
		Word res = new Word(word1.width);
		
		for (int i = 0; i < res.data.length; i++) {
			res.data[i] = (word1.data[i] ^ word2.data[i]);
		}
		
		return res;
	}
	
	/**
	 * Obavlja operaciju <code>not</code> podatkovne riječi i vraća rezultat.
	 * @param word podatkovna riječ
	 * @return rezultat je bitwise-not podatkovne riječi
	 */
	public static Word not(Word word) {
		Word res = new Word(word.width);
		
		for (int i = 0; i < res.data.length; i++) {
			res.data[i] = ~word.data[i];
		}
		
		cleanExtraBits(res);
		
		return res;
	}
	
	public static Word add(Word word1, Word word2) {
		Word result = new Word(word1.width);
		
		add(result, word1, word2, false);
		
		return result;
	}
	
	/**
	 * Obavlja operaciju zbrajanja dviju podatkovnih riječi i sprema rezultat
	 * @param result riječ u koju će biti spremljen rezultat
	 * @param word1 prvi operand
	 * @param word2 drugi operand
	 * @param carryIn ulazni prijenos
	 * @return izlazni prijenos
	 */
	public static boolean add(Word result, Word word1, Word word2, boolean carryIn) {
		assertEqualWordLengths(word1, word2);
		
		int carryNext = carryIn ? 1 : 0;
		
		for (int i = 0; i < result.data.length; i++) {
			int tempRes = (int)word1.data[i] + (int)word2.data[i] + carryNext;
			
			if(tempRes > 0xFFFFFF) {
				carryNext = 1;
			} else {
				carryNext = 0;
			}
			
			result.data[i] = tempRes;
		}
		
		cleanExtraBits(result);
		
		return result.excessBit;
	}

	/**
	 * Obavlja operaciju oduzimanja drugog operanda od prvog operanda
	 * @param word1 prvi operand
	 * @param word2 drugi operand
	 * @return zbroj podatkovnih riječi
	 */
	public static Word sub(Word word1, Word word2) {
		assertEqualWordLengths(word1, word2);
		
		Word result = new Word(word1.width);
		
		Word inverted = Word.not(word2);
		
		add(result, word1, inverted, true);
		
		return result;
	}

	/**
	 * Obavlja logički posmak ulijevo za <code>locations</code> mjesta. Pomicanje ulijevo je
	 * ekvivalentno množenju s 2.
	 * @param word riječ koju treba posmaknuti
	 * @param locations za koliko pozicija posmaknuti
	 * @return posmaknuta riječ
	 */
	public static Word shiftLeft(Word word, int locations) {
		if(locations < 0) {
			throw new IllegalArgumentException("Broj lokacija mora biti pozitivan broj!");
		}
		
		Word res = new Word(word.width);
		
		for (int i = 0; i < word.groups; i++) {
			int groupShift = locations / word.groupSize;

			int offset = locations - groupShift*word.groupSize;
			
			if(i+groupShift < word.groups) {
				res.data[i+groupShift] |= (word.data[i] << offset);
			}
			
			if(i+groupShift < word.groups-1) {
				res.data[i+groupShift+1] |= (word.data[i] >>> (word.groupSize - offset));
			}
		}

		cleanExtraBits(res);
		
		return res;
	}
	
	/**
	 * Obavlja logički posmak udesno predane riječi za <code>locations</code> mjesta. Posmicanje
	 * udesno ekvivalentno je dijeljenju s 2.
	 * @param word riječ koju treba posmaknuti
	 * @param locations za koliko treba posmaknuti
	 * @return posmaknuta riječ
	 */
	public static Word shiftRight(Word word, int locations) {	
		if(locations < 0) {
			throw new IllegalArgumentException("Broj lokacija mora biti pozitivan broj!");
		}

		Word res = new Word(word.width);
		
		for (int i = 0; i < word.groups; i++) {
			int groupShift = locations / word.groupSize;

			int offset = locations - groupShift*word.groupSize;
			
			if(i-groupShift >= 0) {
				res.data[i-groupShift] |= (word.data[i] >>> offset);
			}
			
			if(i-groupShift > 0) {
				res.data[i-groupShift-1] |= (word.data[i] << (word.groupSize - offset));
			}
		}

		cleanExtraBits(res);
		
		return res;
	}
	
	/**
	 * Brine se da su riječi jednake širine. U slučaju da nisu, baca iznimku.
	 * @param word1 prva riječ
	 * @param word2 druga riječ
	 */
	private static void assertEqualWordLengths(Word word1, Word word2) {
		if (word1.width != word2.width) {
			throw new IllegalArgumentException("Širine podaktovnih riječi nisu jednake");
		}
	}

	/**
	 * Čisti bitove riječi koji su višak unutar grupe
	 * @param word riječ
	 */
	private static void cleanExtraBits(Word word) {
		
		for (int i = 0; i < word.groups; i++) {
			word.data[i] &= 0xFFFFFF;
		}
		
		int lastGroupCount = word.width - (word.groups-1)*word.groupSize;
		
		word.excessBit = ((word.data[word.data.length-1] >> lastGroupCount) & 1) == 1 ? true : false;
		
		// Čisti sve bitove zadnje grupe koji su viškovi
		word.data[word.groups-1] &= ((1 << lastGroupCount) - 1);
	}
	
	@Override
	public String toString() {
		return this.getHexString();
	}
}
