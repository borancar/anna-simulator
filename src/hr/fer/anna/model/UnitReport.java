package hr.fer.anna.model;

import hr.fer.anna.uniform.Register;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Opisnik komponente.
 * 
 * @author Ivan, Boran
 */
public class UnitReport {

	/** Registri komponente. */
	private Map<String, Register> registers;
	
	/** Opis pojedinog registra. */
	private Map<String, String> registerDescription;
	
	/** Naziv komponente koju opisujemo. */
	private String unitName;
	
	/** Kratak opis komponente. */
	private String unitDescription;
	
	/**
	 * Konstruktor.
	 */
	public UnitReport(String unitName, String unitDecription) {
		this.unitName = unitName;
		this.unitDescription = unitDecription;
		this.registers = new LinkedHashMap<String, Register>();
		this.registerDescription = new LinkedHashMap<String, String>();
	}

	/**
	 * Dodavanje registra.
	 * 
	 * @param reg Registar koji dodajemo.
	 * @param registerName Naziv registra koji dodajemo.
	 * @param registerDescription Opis registra kojeg dodajemo.
	 */
	public void addRegister(Register register, String registerName, String registerDescription) {
		this.registers.put(registerName, register);
		this.registerDescription.put(registerName, registerDescription);
	}

	/**
	 * Enumeracija svih registara komponente.
	 * 
	 * @return Komplet registara komponente (njihova imena).
	 */
	public Set<String> enumerateRegisters() {
		return this.registers.keySet();
	}

	/**
	 * Dohvat određenog registra komponente.
	 * 
	 * @param name Naziv registra kojeg želimo dohvatiti.
	 * @return Traženi registar.
	 */
	public Register getRegister(String registerName) {
		return this.registers.get(registerName);
	}
	
	/**
	 * Dohvat opisa registra.
	 * 
	 * @param registerName Naziv registra čiji opis želimo dohvatiti.
	 * @return Opis registra.
	 */
	public String getRegisterDescription(String registerName) {
		return this.registerDescription.get(registerName);
	}
	
	/**
	 * Dohvat naziva komponente.
	 * 
	 * @return Naziv komponente.
	 */
	public String getUnitName() {
		return this.unitName;
	}
	
	/**
	 * Dohvat kratkog opisa komponente.
	 * 
	 * @return Kratak opis.
	 */
	public String getUnitDescription() {
		return this.unitDescription;
	}
}
