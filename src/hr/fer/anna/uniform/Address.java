package hr.fer.anna.uniform;

import hr.fer.anna.model.Word;

/**
 * Klasa koja predstavlja adrese. Adrese su predstavljene NBC-om. Uspoređuju se
 * po svom sadržaju, neovisno o širini riječi kojom su predstavljene.
 * @author Boran
 *
 */
public class Address extends Word implements Comparable<Address> {

	/**
	 * Stvara adresu iz zadane podatkovne riječi u kojoj je zapisana adresa
	 * @param addressWord podatkovna riječ koja sadrži adresu
	 */
	public Address(Word addressWord) {
		super(addressWord);
	}

	/**
	 * Stvara adresu iz zadane podatkovne riječi u kojoj je zapisana adresa
	 * @param addressWord podatkovna riječ koja sadrži adresu
	 */
	public static Address fromWord(Word addressWord) {
		return new Address(addressWord);
	}
	
	/**
	 * Dvije adrese su jednake ako pokazuju na istu lokaciju, neovisno o tome kako
	 * su predstavljene.
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Word)) {
			return false;
		}
		
		Word other = (Word) obj;
		
		return this.getHexString().equals(other.getHexString());
	}
	
	@Override
	public int hashCode() {
		return this.getHexString().hashCode();
	}


	/**
	 * Adrese se uspoređuju prema lokaciji na koju pokazuju, neovisno o tome kako
	 * su predstavljene.
	 */
	public int compareTo(Address o) {
	
		if (this.groups > o.groups) {
			for (int i = this.groups - 1; i >= o.groups; i--) {
				if (this.data[i] > 0) {
					return 1;
				}
			}
		}

		if (this.groups < o.groups) {
			for (int i = o.groups - 1; i >= this.groups; i--) {
				if (o.data[i] > 0) {
					return -1;
				}
			}
		}
		
		for (int i = this.groups - 1; i >= 0; i--) {
			if (this.data[i] > o.data[i]) {
				return 1;
			} else if (this.data[i] < o.data[i]) {
				return -1;
			}
		}

		return 0;
	}
}
