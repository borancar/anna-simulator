package hr.fer.anna.tests.uniform;

import java.util.Random;

import hr.fer.anna.exceptions.IllegalActionException;
import hr.fer.anna.exceptions.UnknownAddressException;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Address;
import hr.fer.anna.uniform.Constant;
import hr.fer.anna.uniform.PlainBus;

import org.junit.Before;
import org.junit.Test;

/**
 * Testovi obične sabirnice (PlainBus)
 * @author Boran
 *
 */
public class PlainBusTest {
	
	/** Broj vanjskih jedinica koje će se spojit i testirat */
	private static final int NUM_OF_UNITS = 4;
	
	/** Broj testova za svaku vanjsku jedinicu */
	private static final int NUM_OF_TESTS = 1000;
	
	/** Sabirnica koju testiramo */
	private PlainBus plainBus;
	
	/** Generator slučajnih brojeva */
	private Random random;
	
	/** Upravljač sabirnice kojeg koristimo radi testiranja */
	private BusMasterForTesting busMaster;
	
	@Before
	public void init() {
		this.random = new Random();
		this.plainBus = new PlainBus();
		this.busMaster = new BusMasterForTesting();
	}
	
	/**
	 * Testira da li bus ispravno mappira globalne adrese na lokalne adrese jedinice
	 * kojoj treba pristupiti.
	 *
	 */
	@Test
	public void testUnitMapping() throws Exception {
		
		int startAddress = 0;
		
		for (int i = 0; i < NUM_OF_UNITS; i++) {
			int addressSpaceSize = random.nextInt(256) + 1;

			BusUnitForTesting busUnit = new BusUnitForTesting(addressSpaceSize);

			busUnit.setDoCallback(true);

			this.plainBus.registerBusUnit(busUnit, Address.fromWord(new Constant(startAddress)));

			for (int j = 0; j < NUM_OF_TESTS; j++) {
				int expectedLocalAddress = random.nextInt(addressSpaceSize);
				
				busUnit.setExpectedAddress(Address.fromWord((new Constant(expectedLocalAddress))));
				
				this.plainBus.requestRead(busMaster, Address.fromWord(new Constant(startAddress + expectedLocalAddress)));
			}
			
			startAddress += addressSpaceSize;
		}
	}
	
	/**
	 * Testira da li bus ispravno dojavljuje da je zauzet. Dojavlivanje se vrši prikladnom iznimkom.
	 *
	 */
	@Test(expected = IllegalActionException.class)
	public void testBusBusy() throws Exception {
		
		int startAddress = 0;
		
		for (int i = 0; i < NUM_OF_UNITS; i++) {
			int addressSpaceSize = random.nextInt(256) + 1;

			BusUnitForTesting busUnit = new BusUnitForTesting(addressSpaceSize);
			
			this.plainBus.registerBusUnit(busUnit, Address.fromWord(new Constant(startAddress)));


			for (int j = 0; j < NUM_OF_TESTS; j++) {
				int expectedLocalAddress = random.nextInt(addressSpaceSize);

				busUnit.setExpectedAddress(Address.fromWord((new Constant(expectedLocalAddress))));
						
				this.plainBus.requestRead(busMaster, Address.fromWord(new Constant(startAddress + expectedLocalAddress)));
			}	

			startAddress += addressSpaceSize;
		}
	}

	/**
	 * Testira pristupanje još nepoznatim adresama (adresama na koje nije mappirana nijedna jedinica još)
	 *
	 */
	@Test(expected = UnknownAddressException.class)
	public void testBusUnknownAddress() throws Exception {
		this.plainBus.requestRead(busMaster, Address.fromWord(new Constant(random.nextInt())));
	}
	
	/**
	 * Testira pisanje po sabirnice, zatim čitanje sa sabirnice. Metoda zapravo
	 * testira transparentnost sabirnice pri prosljeđivanju podataka.
	 */
	@Test
	public void testBusWriteRead() throws Exception {
		int startAddress = 0;
		
		for (int i = 0; i < NUM_OF_UNITS; i++) {
			int addressSpaceSize = random.nextInt(256) + 1;

			BusUnitForTesting busUnit = new BusUnitForTesting(addressSpaceSize);
			busUnit.setDoCallback(true);

			this.plainBus.registerBusUnit(busUnit, Address.fromWord(new Constant(startAddress)));

			for (int j = 0; j < NUM_OF_TESTS; j++) {
				int expectedLocalAddress = random.nextInt(addressSpaceSize);
				Word expectedWord = new Constant(random.nextInt());
				
				
				busUnit.setExpectedAddress(Address.fromWord((new Constant(expectedLocalAddress))));
				
				busMaster.setExpectedAddress(Address.fromWord(new Constant(startAddress + expectedLocalAddress)));

				this.plainBus.requestWrite(busMaster, Address.fromWord(new Constant(startAddress + expectedLocalAddress)), expectedWord);
				
				busMaster.setExpecetedWord(expectedWord);
				this.plainBus.requestRead(busMaster, Address.fromWord(new Constant(startAddress + expectedLocalAddress)));
					
			}

			startAddress += addressSpaceSize;
		}
	}
}
