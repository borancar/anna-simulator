package hr.fer.anna.frisc.assembler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Niz naredbi koji predstavlja program.
 * 
 * @author Ivan
 * 
 * TODO: Prebaciti u odgovarajući paket
 */
public class Commands implements Iterable<Command> {
	private List<Command> commands;
	
	public Commands() {
		this.commands = new ArrayList<Command>();
	}
	
	public void addCommand(byte[] byteCode, int column, int line) {
		this.commands.add(new Command(byteCode, column, line));
	}
	
	public Command getCommand(int index) {
		return commands.get(index);
	}

	@Override
	public Iterator<Command> iterator() {
		return new Iterator<Command>() {
			private int nextCommand = 0;
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException("Method is not supported for this class");
			}
			
			@Override
			public Command next() {
				return commands.get(nextCommand++);
			}
			
			@Override
			public boolean hasNext() {
				return (nextCommand < commands.size());
			}
		};
	}
	
	
}
