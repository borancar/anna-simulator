package hr.fer.anna.microcode;

import hr.fer.anna.exceptions.MicrocodeException;
import hr.fer.anna.exceptions.SimulationException;
import hr.fer.anna.interfaces.IBus;
import hr.fer.anna.interfaces.IBusMaster;
import hr.fer.anna.interfaces.IMicroinstruction;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Address;

/**
 * Mikroinstrukcija čitanja sa sabirnice. Za ovu mikroinstrukciju se ne može garantirati
 * da će rezultat biti trenutno vidljiv, čak se ne može garantirati niti vidljivost rezultata
 * u istom ciklusu. Potreban je stoga oprez prilikom korištenja ove mikroinstrukcije.
 * @author Boran
 *
 */
public class LoadMicroinstruction implements IMicroinstruction {

	/** Upravljač sabirnice koji izvršava ovu mikroinstrukciju (čita podatke) */
	private IBusMaster busMaster;
	
	/** Sabirnica s koje se čitaju podaci */
	private IBus bus;
	
	/** Podatkovna riječ koja sadrži adresu */
	private Word addressWord;
	
	/**
	 * Stvara novu mikroinstrukciju čitanja sa adrese na sabirnici u podatkovni registar
	 * @param busMaster upravljač sabirnicom koji želi čitati, obično <code>this</code>
	 * @param bus sabirnica
	 * @param addressWord registar koji sadrži adresu (adresni registar)
	 */
	public LoadMicroinstruction(IBusMaster busMaster, IBus bus, Word addressWord) {
		this.busMaster = busMaster;
		this.bus = bus;
		this.addressWord = addressWord;
	}
	
	
	/**
	 * Izvršava ovu mikroinstrukciju tako da sa određene adrese na sabirnici pročita podatak
	 * u podatkovni registar. Svi ovi parametri su zadani prilikom stvaranja instance
	 * ove mikroinstrukcije. Nije moguće garantirati vidljive rezultate niti trenutno
	 * niti unutar trenutnog ciklusa.
	 * 
	 * @return true hazard se uvijek događa prilikom čitanja memorije
	 * 
	 * @throws MicrocodeException u slučaju greške prilikom obavljanja mikroinstrukcije
	 */
	public boolean execute() throws MicrocodeException {
		try {
			this.bus.requestRead(busMaster, Address.fromWord(addressWord));
		} catch (SimulationException e) {
			throw new MicrocodeException("Čitanje sa sabirnice nije uspjelo!", e);
		}
		
		return true;
	}
	
}
