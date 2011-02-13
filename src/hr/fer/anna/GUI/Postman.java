package hr.fer.anna.GUI;

/**
 * Poštar koji doprema razne poruke korisničkom sučelju.
 * 
 * @author Ivan
 */
public class Postman {
	
	private Postman() {
	}

	private static class SingletonHolder {
		private static final Postman INSTANCE = new Postman();
	}

	public static Postman get() {
		return SingletonHolder.INSTANCE;
	}
	
	/**
	 * Slanje poruke o greški.
	 * 
	 * @param errMsg
	 */
	public void sendErrorMsg(String errMsg) {
		// TODO: Napisati...
	}
	
	// TODO: Napisati sučelje err receivera i implementirati observer pattern
	public void registerErrorReceiver() {
		
	}

}
