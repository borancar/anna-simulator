package hr.fer.anna.microcode;

import hr.fer.anna.interfaces.IFlagsRegister;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Register;

/**
 * Mikroinstrukcija aritmetičke operacije oduzimanja. Ova mikroinstrukcije prvo briše
 * registar zastavica, a zatim postavlja zastavice ovisno o rezultatu operacije oduzimanja.
 * @author Boran
 *
 */
// Oduzimanje je samo poseban slučaj zbrajanja
public class SubtractMicroinstruction extends AddMicroinstruction {
	
	/**
	 * Stvara novu mikroinstrukciju koja oduzima od vrijednosti predanog registra vrijednost
	 * unutar drugog predanog registra i rezultat sprema u odredišni registar.
	 * @param regDest odredišni registar
	 * @param Word registar od čije vrijednost oduzimamo
	 * @param Word registra čiju vrijednost oduzimamo
	 * @param flagsRegister registar zastavica koji treba osvježiti nakon obavljene operacije
	 */
	public SubtractMicroinstruction(Register regDest, Word wordA, Word wordB, IFlagsRegister flagsRegister) {
		super(regDest, wordA, wordB, flagsRegister);
		super.subtract = true;
	}

}
