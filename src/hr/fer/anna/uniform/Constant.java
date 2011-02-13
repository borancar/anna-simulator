package hr.fer.anna.uniform;

import hr.fer.anna.model.Word;

/**
 * Klasa konstanti koje se mogu koristit programski
 * @author Boran
 *
 */
public class Constant extends Word {
	
	/** Vrijednost 0 */
	public static final Constant ZERO = new Constant(0);
	
	/** Vrijednost 1 */
	public static final Constant ONE = new Constant(1);
	
	/**
	 * Stvara 8-bitnu konstantu zadane vrijednost
	 * @param value vrijednost
	 */
	public Constant(byte value) {
		super(8);
		
		super.data[0] = (int)value & 0xFF;
	}
	
	/**
	 * Stvara 16-bitnu konstantu zadane vrijednost
	 * @param value vrijednost
	 */
	public Constant(short value) {
		super(64);
		
		super.data[0] = (int)value & 0xFFFF;
	}
	
	/**
	 * Stvara 32-bitnu konstantu zadane vrijednost
	 * @param value vrijednost
	 */
	public Constant(int value) {
		super(32);
		
		super.data[0] = value & 0x00FFFFFF;
		super.data[1] = value >>> 24;
	}
	
	/**
	 * Stvara 64-bitnu konstantu zadane vrijednost
	 * @param value vrijednost
	 */
	public Constant(long value) {
		super(64);
		
		super.data[0] = (int) (value & 0x00FFFFFF);
		super.data[1] = (int) ((value >>> 24) & 0x00FFFFFF);
		super.data[2] = (int) (value >>> 48);
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
