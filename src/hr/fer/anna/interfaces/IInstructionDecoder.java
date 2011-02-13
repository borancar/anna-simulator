package hr.fer.anna.interfaces;

import hr.fer.anna.uniform.Register;

import java.util.List;

/**
 * Sučelje dekodera instrukcija koji primljenu instrukciju pretvara u mikroinstrukcije
 * koje svi procesori koji se koriste mikroinstrukcijskim paketom znaju izvršavati.
 * @author Boran
 *
 */
public interface IInstructionDecoder {
	
	/**
	 * Dekodira instrukciju zapisanu u instrukcijskom registru u mikroinstrukcije.
	 * @param instructionRegister instrukcijski registar
	 * @return lista mikroinstrukcija koje su ekvivalentne instrukciji u instrukcijskom registru
	 */
	public List<IMicroinstruction> decode(Register instructionRegister);
}
