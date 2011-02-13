package hr.fer.anna.GUI;

import javax.swing.JPanel;

public abstract class ArchDisplay {
	
	public abstract JPanel getModelPanel();
	public abstract JPanel getSimPanel();
	
	public abstract String assemble(String code);
}
