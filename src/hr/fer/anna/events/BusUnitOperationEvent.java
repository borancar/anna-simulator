package hr.fer.anna.events;

import hr.fer.anna.exceptions.IllegalActionException;
import hr.fer.anna.interfaces.IBus;
import hr.fer.anna.interfaces.IBusUnit;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Address;

/**
 * Općeniti event koji se javlja prilikom rada jedinice na sabirnici
 * @author Boran
 * 
 */
public class BusUnitOperationEvent extends SimulationEvent {
	
	/** Sabirnica koja je odaslala ovaj event */
	protected IBus bus;
	
	/** Jedinica na sabirnici od koje zahtijevamo početak operacije */
	protected IBusUnit busUnit;
	
	/** Adresa unutar jedinice */
	protected Address localAddress;
	
	/** Riječi koje treba zapisati, ako pišemo */
	protected Word[] words;
	
	/** true ako pišemo, false ako ne pišemo */
	protected boolean writing;
	
	/** true ako čitamo, false ako ne čitamo */
	protected boolean reading;
	
	/** true ako je obavljena operacija, false inače */
	protected boolean completed;
	
	/**
	 * Defaultni konstruktor, služi isključivo za registriranje slušača.
	 * 
	 * @param busUnit jedinica na sabirnici kojoj je event namijenjen
	 */
	public BusUnitOperationEvent(IBusUnit busUnit) {
		super();
		this.busUnit = busUnit;
	}
	
	/**
	 * Konstruktor za stvaranje općenitog eventa koji se javlja na sabirnici.
	 * 
	 * @param bus sabirnica koja je postavila event
	 * @param busUnit jedinica kojoj se pristupa
	 * @param localAddress adresa unutar jedinice na sabirnici kojoj se pristupa
	 */
	public BusUnitOperationEvent(IBus bus, IBusUnit busUnit, Address localAddress) {
		this.bus = bus;
		this.busUnit = busUnit;
		this.localAddress = localAddress;
	}
	
	/**
	 * Vraća adresu unutar jedinice na sabirnici.
	 * 
	 * @return adresa
	 */
	public Address getLocalAddress() {
		return this.localAddress;
	}
	
	/**
	 * Da li je obavljena operacija?
	 * 
	 * @return true ako da, false inače
	 */
	public boolean isCompleted() {
		return this.completed;
	}
	
	/**
	 * Vraća jedinicu na sabirnici kojoj je odaslan ovaj event.
	 * 
	 * @return jedinica na sabirnici
	 */
	public IBusUnit getBusUnit() {
		return this.busUnit;
	}
	
	/**
	 * Da li čitamo?
	 * 
	 * @return true ako da, false inače
	 */
	public boolean reading() {
		return this.reading;
	}
	
	/**
	 * Da li pišemo?
	 * 
	 * @return true ako da, false inače
	 */
	public boolean writing() {
		return this.writing;
	}
	
	/**
	 * Vraća podatke koje treba zapisati, ako se radi o zapisivanju. Ako se radi o čitanju,
	 * vraća podatke samo ako je obavljena operacija.
	 * @return riječi koje treba zapisati
	 * 
	 * @throws IllegalActionException u slučaju da se radi o čitanju i operacija još nije završena
	 */
	public Word[] getWords() throws IllegalActionException {
		
		if(!writing()) {
			if(reading() && !isCompleted()) {
				throw new IllegalActionException("Podaci još nisu dostupni jer nije obavljena operacija čitanja!");
			}
		}
		
		return this.words;
	}
	
	/**
	 * Vraća bus koji je postavio ovaj event
	 * 
	 * @return bus
	 */
	public IBus getBus() {
		return this.bus;
	}
	
	/**
	 * Dva su eventa ovog tipa jednaka ako se radi o istoj jedinici na sabirnici. Jednakost
	 * služi za prosljeđivanje eventova slušačima koji su se na njih pretplatili.
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof BusUnitOperationEvent)) {
			return false;
		}
		
		BusUnitOperationEvent other = (BusUnitOperationEvent) obj;
		
		return this.busUnit == other.busUnit;
	}
	
	public int hashCode() {
		return this.busUnit.hashCode();
	}
}
