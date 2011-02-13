package hr.fer.anna.interfaces;

/**
 * Sučelje registra koji čuva zastavice.
 * @author Boran
 * 
 */
public interface IFlagsRegister {

	public void setCarry(boolean state);
	public boolean getCarry();
		
	public void setZero(boolean state);
	public boolean getZero();
		
	public void setOverflow(boolean state);
	public boolean getOverflow();
	
	public void setNegative();
	public boolean getNegative();
	
	public void setPositive();
	public boolean getPositive();
	
	/**
	 * Briše sve zastavice osim negative ili positive koje su uvijek definirane kao komplement
	 * jedna drugoj. Kod njih briše onu primarnu koja je definirana, a druga poprima komplementarnu
	 * vrijednost.
	 */
	public void clear();
}
