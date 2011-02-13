package hr.fer.anna.uniform;

public class AddressRange implements Comparable<AddressRange> {

	/** Početna adresa (uključena) */
	private Address startAddress;
	
	/** Konačna adresa (uključena) */
	private Address endAddress;
	
	/**
	 * Stvara novi interval adresa predstavljen početnom i konačnom adresom
	 * @param startAddress početna adresa intervala (uključena)
	 * @param endAddress konačna adresa intervala (uključena)
	 */
	public AddressRange(Address startAddress, Address endAddress) {
		this.startAddress = startAddress;
		this.endAddress = endAddress;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof AddressRange)) {
			return false;
		}
		
		AddressRange other = (AddressRange) obj;
		
		return this.startAddress.equals(other.startAddress) && this.endAddress.equals(other.endAddress);
	}
	
	@Override
	public int hashCode() {
		return this.startAddress.hashCode() ^ this.endAddress.hashCode();
	}

	public int compareTo(AddressRange o) {
		int start = this.startAddress.compareTo(o.startAddress);
		
		if(start == 0) {
			return this.endAddress.compareTo(o.endAddress);
		} else {
			return start;
		}
	}
	
	/**
	 * Dohvaća početnu adresu intervala koja je uključena u interval
	 * @return početna adresa
	 */
	public Address getStartAddress() {
		return startAddress;
	}

	/**
	 * Dohvaća krajnju adresu intervala koja je uključena u interval
	 * @return krajnja adresa
	 */
	public Address getEndAddress() {
		return endAddress;
	}

	/**
	 * Provjerava da li se predana adresa nalazi u intervalu [a, b]
	 * @param address predana adresa
	 * @return true ako se predana adresa nalazi u intervalu, false inače
	 */
	public boolean inRange(Address address) {
		return (address.compareTo(this.startAddress) >= 0 && address.compareTo(this.endAddress) <= 0);
	}
}
