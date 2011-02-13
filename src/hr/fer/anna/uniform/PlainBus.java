package hr.fer.anna.uniform;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import hr.fer.anna.exceptions.BusAddressTaken;
import hr.fer.anna.exceptions.IllegalActionException;
import hr.fer.anna.exceptions.UnknownAddressException;
import hr.fer.anna.interfaces.IBus;
import hr.fer.anna.interfaces.IBusMaster;
import hr.fer.anna.interfaces.IBusUnit;
import hr.fer.anna.model.Word;

/**
 * Implementacija vrlo jednostavne sabirnice. Uloga sabirnici je dvostrano
 * mappiranje globalnih adresa na lokalne adrese na jedinici koja pripada sabirnici.
 * Sabirnica prima zahtjeve čitanja/pisanja i prosljeđuje ih odgovarajućoj jedinici.
 * Sve dok jedinica ne obavi operaciju, smatra se da ni sabirnica nije obavila operaciju
 * te ne može primati više operacija.
 * @author Boran
 *
 */
public class PlainBus implements IBus {

	/** Trenutni upravljač ovom sabirnicom */
	private IBusMaster busMaster;
	
	/** true ako je bus zauzet i ne može primati operacije, false inače */
	private boolean busy;

	/** Mapiranje jedinica u globalne adrese */
	private Map<IBusUnit, AddressRange> unitToAddress;
	
	/** Mapiranje globalnih adresa na jedinice */
	private Map<AddressRange, IBusUnit> addressToUnit;
	
	public PlainBus() {
		unitToAddress = new HashMap<IBusUnit, AddressRange>();
		addressToUnit = new LinkedHashMap<AddressRange, IBusUnit>();
	}
	
	@Override
	public void setBusMaster(IBusMaster newBusMaster) throws IllegalActionException {
		if (this.busy) {
			throw new IllegalActionException("Sabirnica je zauzeta!");
		} else {
			this.busMaster = newBusMaster;
		}
	}

	@Override
	public IBusMaster getBusMaster() {
		return this.busMaster;
	}

	@Override
	public boolean isBusy() {
		return this.busy;
	}

	@Override
	public void registerBusUnit(IBusUnit busUnit, Address startAddress)
			throws UnknownAddressException, BusAddressTaken {
		AddressRange addressRange = new AddressRange(startAddress, new Address(Word.add(startAddress, new Constant(busUnit.getLocalAddresses() - 1))));
	
		unitToAddress.put(busUnit, addressRange);
		addressToUnit.put(addressRange, busUnit);
	}

	@Override
	public void requestRead(IBusMaster busMaster, Address globalAddress)
			throws UnknownAddressException, IllegalActionException {
		IBusUnit busUnit = getBusUnitFromGlobalAddress(globalAddress);
		Address localAddress = getLocalAddressFromGlobal(busUnit, globalAddress);
		
		this.acquire(busMaster);
		busUnit.requestRead(this, localAddress);
	}

	@Override
	public void busUnitReadCallback(IBusUnit busUnit, Address localAddress,
			Word word) {
		this.busMaster.busReadCallback(this, getGlobalAddressFromLocal(busUnit, localAddress), word);
		this.release(busMaster);
	}

	@Override
	public void requestWrite(IBusMaster busMaster, Address globalAddress,
			Word word) throws UnknownAddressException, IllegalActionException {
		IBusUnit busUnit = getBusUnitFromGlobalAddress(globalAddress);
		Address localAddress = getLocalAddressFromGlobal(busUnit, globalAddress);
		
		this.acquire(busMaster);
		busUnit.requestWrite(this, localAddress, word);
	}

	@Override
	public void busUnitWriteCallback(IBusUnit busUnit, Address localAddress) {
		this.busMaster.busWriteCallback(this, getGlobalAddressFromLocal(busUnit, localAddress));
		this.release(busMaster);
	}

	/**
	 * Zauzimanje sabirnice od strane upravljača sabirnice
	 * @param busMaster upravljač sabirnice
	 * @throws IllegalActionException u slučaju da je sabirnica već zauzeta
	 */
	private void acquire(IBusMaster busMaster) throws IllegalActionException {
		setBusMaster(busMaster);
		
		this.busy = true;
		busMaster.waitBus(this, true);
	}

	/**
	 * Otpuštanje sabirnice od strane upravljača sabirnice
	 * @param busMaster upravljač sabirnice
	 */
	private void release(IBusMaster busMaster) {
		this.busy = false;
		busMaster.waitBus(this, false);
	}

	/**
	 * Dohvaća globalnu adresu na sabirnici iz para (jedinica, lokalnaAdresa)
	 * @param busUnit jedinica
	 * @param localAddress lokalna adresa (na jedinice)
	 * @return globalna adresa na sabirnici
	 */
	private Address getGlobalAddressFromLocal(IBusUnit busUnit,
			Address localAddress) {
		return new Address(Word.add(unitToAddress.get(busUnit).getStartAddress(), localAddress));
	}

	/**
	 * Dohvaća lokalnu adresu na jedinici iz globalne adrese na sabirnici
	 * @param busUnit jedinica
	 * @param globalAddress globalna adresa (na sabirnici)
	 * @return lokalna adresa (na jedinici)
	 */
	private Address getLocalAddressFromGlobal(IBusUnit busUnit, Address globalAddress) {
		return new Address(Word.sub(globalAddress, unitToAddress.get(busUnit).getStartAddress()));
	}

	/**
	 * Dohvaća jedinicu kojoj pripada predana globalna adresa na sabirnici.
	 * @param globalAddress globalna adresa (na sabirnici)
	 * @return jedinica
	 */
	private IBusUnit getBusUnitFromGlobalAddress(Address globalAddress) throws UnknownAddressException {
		
		for(AddressRange addressRange: addressToUnit.keySet()) {
			if(addressRange.inRange(globalAddress)) {
				return addressToUnit.get(addressRange);
			}
		}
		
		throw new UnknownAddressException("Ta adresa nije nigdje mapirana!");
	}
}
