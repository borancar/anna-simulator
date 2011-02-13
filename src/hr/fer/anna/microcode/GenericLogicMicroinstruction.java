package hr.fer.anna.microcode;

import hr.fer.anna.interfaces.IFlagsRegister;
import hr.fer.anna.interfaces.IMicroinstruction;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Register;

public abstract class GenericLogicMicroinstruction implements IMicroinstruction {
	
	/** Prvi operand */
	protected Word wordA;
	
	/** Drugi operand */
	protected Word wordB;
	
	/** Rezultat */
	protected Register regRes;
	
	/** Registar zastavica */
	protected IFlagsRegister flags;
	
	/**
	 * Stvara novu mikroinstrukciju općenite logičke operacije nad 2 registra
	 * @param wordA prvi operand
	 * @param wordB drugi operand
	 * @param regRes rezultat
	 * @param flags zastavice
	 */
	public GenericLogicMicroinstruction(Word wordA, Word wordB, Register regRes, IFlagsRegister flags) {
		this.wordA = wordA;
		this.wordB = wordB;
		this.regRes = regRes;
		this.flags = flags;
	}
}
