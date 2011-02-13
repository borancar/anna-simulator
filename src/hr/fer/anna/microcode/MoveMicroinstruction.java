package hr.fer.anna.microcode;

import hr.fer.anna.exceptions.MicrocodeException;
import hr.fer.anna.interfaces.IMicroinstruction;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Register;

/**
 * Mikroinstrukcija postavlja zadanu vrijednost ili vrijednost zadanog registra u drugi
 * (odredišni) registar. Pri tome se mijenja samo odredišni registar.
 * @author Boran
 *
 */
public class MoveMicroinstruction implements IMicroinstruction {

	/** Izvorišna podatkovna riječ */
	private Word srcWord;
	
	/** Odredišni registar */
	private Register destReg;


	/**
	 * Stvara primjerak mikroinstrukcije koja sprema vrijednost podatkovne riječi u registar
	 * @param srcWord podatkovna riječ
	 * @param destReg registar
	 */
	public MoveMicroinstruction(Word srcWord, Register destReg) {
		this.srcWord = srcWord;
		this.destReg = destReg;
	}
	
	/**
	 * Obavlja mikroinstrukciju tako da spremi zadanu vrijednost ili vrijednost zadanog
	 * registra u drugi (odredišni) registar.
	 * 
	 * @return false hazard se ne događa
	 * 
	 * @throws MicrocodeException u slučaju nekakve greške
	 */
	public boolean execute() throws MicrocodeException {
		try {
			destReg.set(srcWord);
		} catch (IllegalArgumentException e) {
			throw new MicrocodeException("Nije moguće spremiti vrijednost u registar! Širine se razlikuju!");
		}
		
		return false;
	}

}
