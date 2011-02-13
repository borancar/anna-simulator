package hr.fer.anna.exceptions;

// TODO: Neka netko komentira ovo, inače ću špatulom ovo izbrisati :P
public class InvalidRequest extends SimulationException {
	
	private static final long serialVersionUID = 1L;

	public InvalidRequest(String msg) {
		super(msg);
	}
}
