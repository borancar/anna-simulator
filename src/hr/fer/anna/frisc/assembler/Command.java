package hr.fer.anna.frisc.assembler;

/**
 * Naredba programa.
 * 
 * @author Ivan
 */
public class Command {
	private byte[] byteCode;
	private int column;
	private int line;
	private Command nextCommand;

	public Command(byte[] byteCode, int column, int line) {
		super();
		this.column = column;
		this.line = line;
		this.byteCode = byteCode;
	}
	
	public int getColumn() {
		return column;
	}

	public int getLine() {
		return line;
	}
	
	/**
	 * @return Strojni kod naredbe.
	 */
	public byte[] getByteCode() {
		return byteCode;
	}

	public Command getNextCommand() {
		return nextCommand;
	}

	public void setNextCommand(Command nextCommand) {
		this.nextCommand = nextCommand;
	}
}
