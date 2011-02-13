package hr.fer.anna.microcode;

import hr.fer.anna.interfaces.IFlagsRegister;
import hr.fer.anna.interfaces.IMicroinstruction;
import hr.fer.anna.uniform.Register;

/**
 * Metode koje služe za implementiranje mikroinstrukcija posmaka i rotacije.
 * Metode su generičke i za upotrebu ih je potrebno omotati u wrapper.
 * @author Boran
 *
 */
public abstract class GenericShiftMicroinstruction implements IMicroinstruction {

	/** Registar unutar kojeg će se odviti općenito posmicanje */
	protected Register reg;
	
	/** Registar zastavica */
	protected IFlagsRegister flags;
	
	/**
	 * Stvara novu mikroinstrukciju općenitog posmaka.
	 * @param reg registar unutar kojeg treba obaviti posmak
	 * @param flags registar zastavica koji treba osvježiti
	 */
	public GenericShiftMicroinstruction(Register reg, IFlagsRegister flags) {
		this.reg = reg;
		this.flags = flags;
	}

//	/**
//	 * Rotira registar udesno
//	 * @param reg registar
//	 */
//	protected void rotateRegisterRight(Register reg) {
//		shiftRegisterRight(reg, reg.getBit(0));
//	}
//	
//	/**
//	 * Rotira registar ulijevo
//	 * @param reg registar
//	 */
//	protected void rotateRegisterLeft(Register reg) {
//		shiftRegisterLeft(reg, reg.getBit(reg.getWidth()));
//	}
//	
//	/**
//	 * Posmiče registar reg ulijevo, moguće je postaviti bit koji će nadopuniti prazno mjesto.
//	 * @param reg registar koji treba posmaknuti u lijevu stranu
//	 * @param bit koji će nadopuniti prazno mjesto
//	 * @return bit koji je <q>ispao</q>
//	 */
//	protected boolean shiftRegisterLeft(Register reg, boolean fill) {
//		long result = (reg.getValue() << 1) | (fill ? 1 : 0);
//		boolean fallout = reg.getBit(reg.getWidth() - 1);
//	
//		
//		return fallout;
//	}
//	
//	/**
//	 * Posmiče registar udesno, moguće je postaviti bit koji će nadopuniti prazno mjesto.
//	 * @param reg registar koji treba posmaknuti u desnu stranu
//	 * @param fill bit koji će nadopuniti prazno mjesto
//	 * @return bit koji je <q>ispao</q>
//	 */
//	protected boolean shiftRegisterRight(Register reg, boolean fill) {
//		long result = (reg.getValue() >>> 1) | (fill  ? 1L << (reg.getWidth() * 8 - 1) : 0);
//		boolean fallout = reg.getBit(0);
//		
//		reg.setValue(result);
//		
//		return fallout;
//	}
}
