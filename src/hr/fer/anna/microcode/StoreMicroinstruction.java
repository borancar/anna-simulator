package hr.fer.anna.microcode;

import hr.fer.anna.exceptions.MicrocodeException;
import hr.fer.anna.exceptions.SimulationException;
import hr.fer.anna.interfaces.IBus;
import hr.fer.anna.interfaces.IBusMaster;
import hr.fer.anna.interfaces.IMicroinstruction;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Address;

/**
 * Mikroinstrukcija spremanja na sabirnicu. Za ovu mikroinstrukciju se ne može garantirati
 * da će rezultat biti trenutno vidljiv, čak se ne može garantirati niti vidljivost rezultata
 * u istom ciklusu. Potreban je stoga oprez prilikom korištenja ove mikroinstrukcije.
 * @author Boran
 *
 */
public class StoreMicroinstruction implements IMicroinstruction {

	/** Upravljač sabirnice koji izvršava ovu mikroinstrukciju (čita podatke) */
	private IBusMaster busMaster;
	
	/** Riječ iz koje se zapisuju podaci */
	private Word dataWord;
	
	/** Bus na koji se zapisuju podaci */
	private IBus bus;
	
	/** Riječ koja sadrži adresu */
	private Word addressWord;
	
	/**
	 * Stvara novu mikroinstrukciju spremanja na adresu na sabirnici podatkovne riječi
	 * @param busMaster upravljač sabirnicom koji želi čitati, obično <code>this</code>
	 * @param dataWord podatkovna riječ
	 * @param bus sabirnica
	 * @param addressWord riječ koja sadrži adresu
	 */
	public StoreMicroinstruction(IBusMaster busMaster, Word dataWord, IBus bus, Word addressWord) {
		this.busMaster = busMaster;
		this.dataWord = dataWord;
		this.bus = bus;
		this.addressWord = addressWord;
	}
	
	/**
	 * Izvršava ovu mikroinstrukciju tako da spremi na određenu adresu na sabirnici podatak
	 * koji se nalazi u podatkovnom registru. Svi ovi parametri su zadani prilikom stvaranja
	 * instance ove mikroinstrukcije. Nije moguće garantirati vidljive rezultate niti trenutno
	 * niti unutar trenutnog ciklusa.
	 * 
	 * @return true hazard se događa
	 * 
	 * @throws MicrocodeException u slučaju greške prilikom obavljanja mikroinstrukcije
	 */
	public boolean execute() throws MicrocodeException {
		try {
			this.bus.requestWrite(busMaster, Address.fromWord(addressWord), dataWord);
		} catch (SimulationException e) {
			throw new MicrocodeException("Pisanje na sabirnicu nije uspjelo!", e);
		}
		
		return true;
	}
	
}
