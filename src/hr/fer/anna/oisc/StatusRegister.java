package hr.fer.anna.oisc;

import hr.fer.anna.interfaces.IFlagsRegister;
import hr.fer.anna.uniform.Register;

/**
 * Status register OISC-a. Organizacija:
 * x...x|NMI0|IRQ1|IRQ0|GIE|ENMI2|EIRQ1|EIRQ0|Z|V|C|N<br />
 * NMI2, IRQ1 i IRQ0 su flagovi koji označavaju postavljen interrupt.
 * 
 * @author Ivan
 */
public class StatusRegister extends Register implements IFlagsRegister {
	
	/**
	 * Konstruktor.
	 */
	public StatusRegister() {
		super(32);
	}
	
	public boolean getCarry() {		
		return getBit(1);
	}

	public boolean getZero() {
		return getBit(3);
	}
	
	public boolean getOverflow() {
		return getBit(2);
	}

	public boolean getNegative() {
		return getBit(0);
	}

	public boolean getPositive() {
		return !getNegative();
	}

	public void setCarry(boolean state) {
		setBit(1, state);
	}

	public void setZero(boolean state) {
		setBit(3, state);
	}

	public void setOverflow(boolean state) {
		setBit(2, state);
	}
	
	public void setNegative() {
		setBit(0, true);
	}

	public void setPositive() {
		setBit(0, false);
	}
	
	/**
	 * Postavljanje/brisanje interrupta.
	 * 
	 * @param port Port na koji postavljamo ili sa kojeg brišemo interrupt.
	 * @param value True ako postavljamo interrupt, false ako brišemo.
	 */
	public void setInterrupt(String port, boolean value) {
		// TODO: Moj je prijedlog da se ovo stavi unutar mape za kompleksnije interrupte,
		// naravno da kod ovoga to nećemo raditi :). Recimo mapa koja bi dohvaćala index bita
		// koji se postavlja ili briše.
		if (port.equals("NMI0")) {
			setBit(10, value);
		} else if (port.equals("IRQ1")) {
			setBit(9, value);
		} else if (port.equals("IRQ0")) {
			setBit(8, value);
		} else {
			throw new IllegalArgumentException("Interrupt port \"" + port + "\" ne postoji!");
		}
	}
	
	/**
	 * Je li omogućeno postavljanje interrupta na port <code>port</code>.
	 * 
	 * @param port Port koji provjeravamo.
	 * @return True ako je postavljanje omogućeno, false ako nije.
	 */
	public boolean isInterruptEnabled(String port) {
		boolean global;
		global = getBit(7);
		
		if (port.equals("NMI0")) {
			// TODO: I nemaskirajući se može ugasiti, razlika je samo da se nemaskirajući može
			// pojaviti unutar maskirajućeg.
			return getBit(6) && global;
		} else if (port.equals("IRQ1")) {
			return getBit(5) && global;
		} else if (port.equals("IRQ0")) {
			return getBit(4) && global;
		} else {
			throw new IllegalArgumentException("Interrupt port \"" + port + "\" ne postoji!");
		}
	}
}
