package hr.fer.anna.microcode;

import hr.fer.anna.exceptions.MicrocodeException;
import hr.fer.anna.interfaces.IFlagsRegister;
import hr.fer.anna.interfaces.IMicroinstruction;

/**
 * Mikroinstrukcija uvjetnog izvođenja mikroinstrukcije koju omotava. Ova mikroinstrukcija
 * provjerava uvjete i ako su uvjeti zadovoljeni, izvodi omotanu mikroinstrukciju. Uvjeti
 * su stanja zastavica.
 * @author Boran
 *
 */
public class ConditionalMicroinstruction implements IMicroinstruction {

	/** Da li testiramo zastavicu negative/positive */
	private boolean testNegative;
	
	/** Da li testiramo zastavicu zero */
	private boolean testZero;
	
	/** Da li testiramo zastavicu carry */
	private boolean testCarry;
	
	/** Da li testiramo zastavicu overflow */
	private boolean testOverflow;
	
	/** Tražena vrijednost negative zastavice */
	private boolean negative;
	
	/** Tražena vrijednost zero zastavice */
	private boolean zero;
	
	/** Tražena vrijednost carry zastavice */
	private boolean carry;
	
	/** Tražena vrijednost overflow zastavice */
	private boolean overflow;
	
	/** Registar zastavica unutar kojeg se provjeravaju uvjeti */
	private IFlagsRegister flagsRegister;
	
	/** Začahurena mikroinstrukcija koja se izvršava samo ako su zadovoljeni uvjeti */
	private IMicroinstruction encapsulated;
	
	/**
	 * Stvara mikroinstrukciju uvjetnog izvođenja koja izvodi omotanu mikroinstrukciju
	 * ako su zadovoljeni uvjeti stanja registra zastavica. Nakon stvaranja objekta nije
	 * postavljen niti jedan uvjet i treba ih dodatno postaviti metodama test...
	 * @param flagsRegister registar zastavica koje se ispituju
	 * @param encapsulated omotana mikroinstrukcija koja se treba izvesti ako su zadovoljeni uvjeti
	 */
	public ConditionalMicroinstruction(IFlagsRegister flagsRegister, IMicroinstruction encapsulated) {
		super();
		this.testNegative = false;
		this.testZero = false;
		this.testCarry = false;
		this.testOverflow = false;
		this.flagsRegister = flagsRegister;
		this.encapsulated = encapsulated;
	}
	
	/**
	 * Mikroinstrukcija će izvesti omotanu mikroinstrukciju ako je zastavica zero
	 * postavljena u traženo stanje stanje.
	 * @param status traženo stanje zero zastavice
	 * @return trenutna mikroinstrukcija
	 */
	public ConditionalMicroinstruction testZero(boolean status) {
		this.testZero = true;
		this.zero = status;
		
		return this;
	}
	
	/**
	 * Mikroinstrukcija će izvesti omotanu mikroinstrukciju ako je zastavica carry
	 * postavljena u traženo stanje stanje.
	 * @param status traženo stanje carry zastavice
	 * @return trenutna mikroinstrukcija
	 */
	public ConditionalMicroinstruction testCarry(boolean status) {
		this.testCarry = true;
		this.carry = status;
		
		return this;
	}

	/**
	 * Mikroinstrukcija će izvesti omotanu mikroinstrukciju ako je zastavica negative
	 * postavljena u traženo stanje stanje.
	 * @param status traženo stanje negative zastavice
	 * @return trenutna mikroinstrukcija
	 */
	public ConditionalMicroinstruction testNegative(boolean status) {
		this.testNegative = true;
		this.negative = status;
		
		return this;
	}

	/**
	 * Mikroinstrukcija će izvesti omotanu mikroinstrukciju ako je zastavica overflow
	 * postavljena u traženo stanje stanje.
	 * @param status traženo stanje overflow zastavice
	 * @return trenutna mikroinstrukcija
	 */
	public ConditionalMicroinstruction testOverflow(boolean status) {
		this.testOverflow = true;
		this.overflow = status;
		
		return this;
	}
	
	/**
	 * Mikroinstrukcija se izvodi tako da se izvodi omotana mikroinstrukcija ako su
	 * zadovoljena stanja zastavica postavljena u konstruktoru, inače se izvela samo provjera uvjeta.
	 * 
	 * @return stanje hazarda ovisi o omotanoj mikroinstrukciji
	 * 
	 * @throws MicrocodeException u slučaju nekakve greške
	 */
	public boolean execute() throws MicrocodeException {
		
		if(testNegative && flagsRegister.getNegative() != negative ||
				testZero && flagsRegister.getZero() != zero ||
				testCarry && flagsRegister.getCarry() != carry ||
				testOverflow && flagsRegister.getOverflow() != overflow) {
			return false;
		}
		
		return encapsulated.execute();
	}

	/**
	 * Računa virtualnu zastavicu greater iz zastavica flags registra.
	 * 
	 * @return true ako bi virtualna zastavica greater bila postavljena nakon
	 *         ALU operacije, false inače
	 */
	protected boolean getGreater() {
		// (N XOR V) = 0 AND Z = 0
		boolean N, V, Z;

		N = flagsRegister.getNegative();
		V = flagsRegister.getOverflow();
		Z = flagsRegister.getZero();

		return !(N ^ V) && !Z;
	}

	/**
	 * Računa virtualnu zastavicu less iz zastavica flags registra.
	 * 
	 * @return true ako bi virtualna zastavica less bila postavljena nakon ALU
	 *         operacije, false inače
	 */
	protected boolean getLess() {
		// (N XOR V) = 1
		boolean N, V;

		N = flagsRegister.getNegative();
		V = flagsRegister.getOverflow();

		return N ^ V;
	}

	/**
	 * Računa virtualnu zastavicu equal iz zastavica flags registra.
	 * 
	 * @return true ako bi virtualna zastavca equal bila postavljena nakon ALU
	 *         operacije, false inače
	 */
	protected boolean getEqual() {
		// Z = 1
		boolean Z;

		Z = flagsRegister.getZero();

		return Z;
	}
}
