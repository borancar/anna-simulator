package hr.fer.anna.interfaces;

import hr.fer.anna.exceptions.MicrocodeException;

/**
 * Općenita klasa mikroinstrukcije koju procesorska jezgra izvršava. Mikroinstrukcije su
 * namijenene kao pomoć pri dizajnu procesora. Rade samo interno unutar procesorske jezgre
 * i svaka mikroinstrukcija se mora izvršiti unutar jednog ciklusa. Rezultati mikroinstrukcija
 * su trenutno vidljivi osim ako nije drukčije napomenuto.
 * @author Boran
 *
 */
public interface IMicroinstruction {

	/**
	 * Izvršava mikroinstrukciju. Rezultati su trenutno vidljivi nakon uspješnog završetka
	 * ove metode osim ako nije drukčije napomenuto. Povratna vrijednost označava da li
	 * smo stvorili nekakav hazard zbog čeka treba pričekati i izvesti ostale mikroinstrukcije
	 * tek nakon što se hazard riješi.
	 * 
	 * @return true ako se dogodio hazard, false inače
	 * 
	 * @throws MicrocodeException u slučaju nekakve greške prilikom izvođenja mikroinstrukcije
	 */
	public boolean execute() throws MicrocodeException;
}
