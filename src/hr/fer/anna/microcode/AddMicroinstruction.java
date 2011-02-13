package hr.fer.anna.microcode;

import hr.fer.anna.exceptions.MicrocodeException;
import hr.fer.anna.interfaces.IFlagsRegister;
import hr.fer.anna.interfaces.IMicroinstruction;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Constant;
import hr.fer.anna.uniform.Register;

/**
 * Mikroinstrukcija aritmetičke operacije zbrajanja. Ova mikroinstrukcije prvo briše
 * registar zastavica, a zatim postavlja zastavice ovisno o rezultatu operacije zbrajanja.
 * @author Boran
 *
 */
public class AddMicroinstruction implements IMicroinstruction {

	/** Prvi pribrojnik */
	private Word wordA;
	
	/** Drugi pribrojnik */
	private Word wordB;
	
	/** Registar u koji spremamo rezultat */
	private Register regDest;
	
	/** Registar zastavica kojem osvježavamo zastavice */
	private IFlagsRegister flagsRegister;
	
	/** True ako se zapravo radi o oduzimanju (što je samo poseban slučaj zbrajanja) */
	protected boolean subtract;
	
	/**
	 * Stvara novu mikroinstrukciju koja zbraja vrijednosti predanih podatkovnih riječi
	 * @param regDest odredišni registar
	 * @param wordA prvi operand
	 * @param wordB drugi operand
	 * @param flagsRegister registar zastavica koji treba osvježiti nakon obavljene operacije
	 */
	public AddMicroinstruction(Register regDest, Word wordA, Word wordB, IFlagsRegister flagsRegister) {
		this.regDest = regDest;
		this.wordA = wordA;
		this.wordB = wordB;
		this.flagsRegister = flagsRegister;
		this.subtract = false;
	}
	
	/**
	 * Bit predznaka vrijednosti registra u 2-komplement zapisu
	 * @param reg registar
	 * @return true ako je vrijednost negativna, false inače
	 */
	private boolean registerSign(Register reg) {
		return reg.getBit(reg.getWidth() - 1);
	}
	
	/**
	 * Izvršava mikroistrukciju zbrajanja. Zastavice se prije izvršavanja operacije brišu,
	 * a nakon izvršanja se postavljaju ovisno o rezultatu. Svi parametri uključeni se predaju
	 * tokom stvaranja primjerka mikroinstrukcije.
	 * 
	 * @return false hazard se ne događa
	 * 
	 * @throws MicrocodeException ukoliko dođe do nekakve greške
	 */
	public boolean execute() throws MicrocodeException {
		
		// TODO: Dovršiti ovo
		
		flagsRegister.clear();
		
		Register regA = new Register(regDest.getWidth());
		Register regB = new Register(regDest.getWidth());
		
		regA.set(wordA);
		
		if(subtract) {
			regB.set(Word.not(wordB));
		} else {
			regB.set(wordB);
		}
		
		flagsRegister.setCarry(Word.add(regDest, regA, regB, subtract));

		if(regDest.equals(new Constant(0))) {
			flagsRegister.setZero(true);
		}
		
		boolean signResult = registerSign(regDest);
		
		if(signResult == true) {
			flagsRegister.setNegative();
		} else {
			flagsRegister.setPositive();
		}
		
		boolean signA = registerSign(regA);
		boolean signB = registerSign(regB);
		
		// Overflow nastupa kada su predznaci operanada isti, a predznak rezultata različit
		flagsRegister.setOverflow(signA && signB && !signResult || !signA && !signB && signResult);
		
		return false;
	}

}
