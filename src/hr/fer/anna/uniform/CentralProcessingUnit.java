package hr.fer.anna.uniform;

import java.util.Set;

import hr.fer.anna.exceptions.IllegalActionException;
import hr.fer.anna.interfaces.IBus;
import hr.fer.anna.interfaces.IBusMaster;

/**
 * Upravljačka jedinica. TODO: Ovo je daleko od gotovog...
 */
public abstract class CentralProcessingUnit implements IBusMaster {

	/** Frekvencija procesora. */
	protected long frequency;

	/** Glavna sabirnica. */
	protected IBus bus;

	/** Spremnik reference na bus zbog "odspajanja". */
	protected IBus busCopy;

	/**
	 * Određivanje sabirnice preko koje upravljačka jedinica komunicira s
	 * ostalim sklopovima.
	 * 
	 * @param bus Sabirnica za komunikaciju s ostalim sklopovima.
	 */
	public void setBus(IBus bus) {
		this.bus = bus;
		this.busCopy = bus;
	}

	/**
	 * Odspajanje sabirnice.
	 */
	public void disconnectBus() {
		this.bus = null;
	}

	/**
	 * Ponovno spajanje sabirnice.
	 */
	public void restoreBus() {
		this.bus = this.busCopy;
	}

	/**
	 * Getter niza naziva svih prekidnih portova sklopa.
	 * 
	 * @return Niz naziva prekidnih portova.
	 */
	public abstract Set<String> getPorts();

	/**
	 * Primanje prekida na portu <i>port</i>.
	 * 
	 * @param port Port na kojem smo primili prekid.
	 * @return True ako je prekid prihvaćen, inače false.
	 * @throws IllegalActionException Ukoliko CPU nema zadani interrupt port.
	 */
	public abstract boolean receiveInterrupt(String port)
			throws IllegalActionException;

	/**
	 * Provjeravanje je li port otvoren.
	 * 
	 * @param port Port koji provjeravamo.
	 * @return True ako je port otvoren, inače false.
	 * @throws IllegalActionException Ukoliko CPU nema zadani interrupt port.
	 */
	public abstract boolean isPortOpened(String port)
			throws IllegalActionException;

	/**
	 * Omogućavanje postavljanja prekida na port <i>port</i>.
	 * 
	 * @param port Port koji otvaramo.
	 */
	protected abstract void openInterruptPort(String port)
			throws IllegalActionException;

	/**
	 * Onemogućavanje postavljanja prekida na port <i>port</i>.
	 * 
	 * @param port Port koji zatvaramo.
	 */
	protected abstract void closeInterruptPort(String port)
			throws IllegalActionException;

	/**
	 * Zaustavljanje upravljačke jedinice.
	 */
	public abstract void halt();

	/**
	 * Getter frekvencije.
	 * 
	 * @return Takt procesora.
	 */
	public long getFreq() {
		return this.frequency;
	}
	
	/**
	 * Postavljanje frekvencije.
	 * 
	 * @param freq Takt procesora.
	 */
	public void setFreq(long freq) {
		this.frequency = freq;
	}
}
