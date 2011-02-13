package hr.fer.anna.microcode;

import hr.fer.anna.exceptions.MicrocodeException;
import hr.fer.anna.interfaces.IMicroinstruction;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Register;

/**
 * Mikroinstrukcija skoka (promjene programskog brojila). Ova mikroinstrukcija obavlja skok
 * na adresu (ukoliko se radi o apsolutnom skoku) inače skače za relativan odmak (ukoliko
 * se radi o relativnom skoku). Pod pojmom skakanja podrazumijeva se samo promjena programskog
 * brojila na novu vrijednost.
 * @author Boran
 *
 */
public class JumpMicroinstruction implements IMicroinstruction {

	/**
	 * Registar programskog brojila.
	 */
	private Register pc;
	
	/**
	 * Riječ koja sadrži adresu na koju treba skočiti ili za koju treba skočiti.
	 */
	private Word addressWord;
	
	/**
	 * true ako se radi o apsolutnom skoku, false ako se radi o relativnom skoku
	 */
	private boolean absolute;
	
	/**
	 * Stvara primjerak mikroinstrukcija skoka (promjene programskog brojila).
	 * @param pc registar programskog brojila
	 * @param addressWord riječ koja sadrži relativnu ili apsolutnu adresu
	 * @param absolute true ako se radi o apsolutnom skoku, false ako se radi o relativnom
	 */
	public JumpMicroinstruction(Register pc, Word addressWord, boolean absolute) {
		this.pc = pc;
		this.addressWord = addressWord;
		this.absolute = absolute;
	}

	/**
	 * Obavlja mikroinstrukciju tako da mijenja vrijednost registra programskog brojila.
	 * Ukoliko se radi o apsolutnom skoku postavlja novu vrijednost, a ukoliko se radi o
	 * relativnom skoku, pribraja vrijednosti programskog brojila odmak. Svi parametri su
	 * predani konstruktoru tokom inicijalizacije ove mikroinstrukcije.
	 * 
	 * @return false hazard se ne događa kod mikroinstrukcija grananja
	 * 
	 * @throws MicrocodeException ukoliko dođe do nekakve greške
	 */
	public boolean execute() throws MicrocodeException {
		if (absolute == true) {
			pc.set(addressWord);
		} else {
			pc.set(Word.add(pc, addressWord));
		}
		
		return false;
	}
}
