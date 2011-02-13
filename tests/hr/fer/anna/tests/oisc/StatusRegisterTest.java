package hr.fer.anna.tests.oisc;

import hr.fer.anna.oisc.StatusRegister;
import hr.fer.anna.tests.uniform.IFlagsRegisterConformance;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit testovi OISC-ovog StatusRegistera
 * @author Boran
 *
 */
public class StatusRegisterTest {

	/**
	 * Primjerak status registra koji ćemo testirati.
	 */
	private StatusRegister register;
	
	/**
	 * Inicijalizira sve potrebno.
	 */
	@Before
	public void setUp() {
		 register = new StatusRegister();
	}
	
	/**
	 * Testira ispravnu implementaciju ovog registra kao flags registra.
	 */
	@Test
	public void testIFlagsRegisterCompliance() {
		IFlagsRegisterConformance.testCarry(register);
		IFlagsRegisterConformance.testZero(register);
		IFlagsRegisterConformance.testOverflow(register);
		IFlagsRegisterConformance.testPositiveNegative(register);
	}	
}
