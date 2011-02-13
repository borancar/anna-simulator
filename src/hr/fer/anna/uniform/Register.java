package hr.fer.anna.uniform;

import hr.fer.anna.model.Word;

/**
 * Registar procesora (privatni i javni).
 * 
 * @author Boran
 */
public class Register extends Word {

	/**
	 * Defaultni konstruktor, stvara 32-bitni registar i
	 * postavlja vrijednost na 0.
	 */
	public Register() {
		this(32);
	}

	/**
	 * Stvara registar zadane širine i postavlja vrijednost na 0.
	 * 
	 * @param width Širina registra (u bitovima).
	 */
	public Register(int width) {
		super(width);
	}
	
	/**
	 * Dohvat jednog bita iz registra pod indeksom <code>index</code>.
	 *  
	 * @param index Indeks bita čija vrijednost nas zanima.
	 * @return Vrijednost bita pod indeksom <code>index</code>, true ako je postavljen, false inače.
	 */
	public boolean getBit(int index) {
		if(index >= getWidth() || index < 0) {
			throw new IllegalArgumentException(
					"Index mora biti unutar intervala [0, " + (getWidth() - 1) + "]. Unijeli ste: " + index);
		}
		
		int group = (int) Math.round(Math.floor(index / (double)super.groupSize));
		int offset = index - group*super.groupSize;
		
		return ((super.data[group] >> offset) & 1) == 1 ? true : false;
	}
	
	/**
	 * Postavljanje jednog bita iz registra pod indeksom <code>index</code>.
	 *  
	 * @param index Indeks bita kojeg postavljamo.
	 * @param value Vrijednost na koju treba postaviti bit
	 */
	public void setBit(int index, boolean value) {
		if(index >= getWidth() || index < 0) {
			throw new IllegalArgumentException(
					"Index mora biti unutar intervala [0, " + (getWidth() - 1) + "]. Unijeli ste: " + index);
		}
		
		int group = (int) Math.round(Math.floor(index / (double)super.groupSize));
		int offset = index - group*super.groupSize;
		
		if(value) {
			super.data[group] |= (1 << offset);
		} else {
			super.data[group] &= ~(1 << offset);
		}
		
		updateListeners();
	}

	/**
	 * Briše vrijednost registra tako da su svi bitovi jednaki 0
	 *
	 */
	public void clear() {
		for (int i = 0; i < this.data.length; i++) {
			this.data[i] = 0;
		}
	}
	
	/**
	 * Vraća adresu zapisanu u registru
	 * @return adresa
	 */
	public Address getAddress() {
		return new Address(this);
	}
}
