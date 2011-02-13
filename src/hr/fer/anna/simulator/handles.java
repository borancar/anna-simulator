package hr.fer.anna.simulator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacija obilježava eventove koji interesiraju slušača. Dodaje se metodi koja
 * treba reagirati na te eventove.
 * @author Boran
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface handles {
	
	/**
	 * Eventovi na koje je metoda spremna reagirati.
	 * @return eventovi
	 */
	public Class[] events();
}
