package hr.fer.anna.uniform;

/**
 * Interrupt port procesora.
 * 
 * @author Ivan
 */
public class InterruptPort {
	
	/** Naziv porta. */
	protected String name;
	
	/** Početna adresa prekidnog potprograma. */
	protected long address;
	
	/** Je li port otvoren ili zatvoren. */
	protected boolean isOpen;
	
	/** Radi li se o portu za maskirajuće ili nemaskirajuće prekide. */
	protected boolean isMaskable;
	
	/**
	 * Konstruktor otvorenog porta za maskirajuće prekide.
	 * 
	 * @param name Naziv porta.
	 * @param address Početna adresa prekidnog potprograma.
	 */
	public InterruptPort(String name, long address) {
		this.name = name;
		this.address = address;
		this.isOpen = true;
		this.isMaskable = true;
	}
	
	/**
	 * Konstruktor.
	 * 
	 * @param name Naziv porta.
	 * @param address Početna adresa prekidnog potprograma.
	 * @param isOpen Je li port otvoren (true) ili zatvoren (false).
	 * @param isMaskable Radi li se o maskirajućem ili nemaskirajućem prekidnom portu.
	 */
	public InterruptPort(String name, long address, boolean isOpen, boolean isMaskable) {
		this.name = name;
		this.address = address;
		this.isOpen = isOpen;
		this.isMaskable = isMaskable;
	}

	/**
	 * Dohvat početne adresa prekidnog potprograma.
	 * @return Početna adresa prekidnog potprograma.
	 */
	public long getAddress() {
		return this.address;
	}

	/**
	 * Dohvat naziva porta.
	 * 
	 * @return Naziv porta.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Je li port otvoren ili zatvoren.
	 * 
	 * @return Je li port otvoren ili zatvoren.
	 */
	public boolean isOpen() {
		return this.isOpen;
	}
	
	/**
	 * Radi li se o portu za maskirajuće ili nemaskirajuće prekide. 
	 * 
	 * @return Radi li se o portu za maskirajuće ili nemaskirajuće prekide. 
	 */
	public boolean isMaskable() {
		return this.isMaskable;
	}

	/**
	 * Otvaranje ili zatvaranje porta.
	 * 
	 * @param isOpen True ako želimo port otvoriti, odnosno false ako želimo port zatvoriti.
	 */
	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}	
}
