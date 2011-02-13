package hr.fer.anna.GUI;

import java.awt.GridLayout;
import javax.swing.JPanel;

public class SimulatorView extends JPanel {
	static final long serialVersionUID = 1;
	
	private Project parent;	
	private boolean open;
	
	private JPanel simPanel;
	
	/**
	 * Default ModelView constructor.
	 * 
	 * TODO: everything
	 */
	public SimulatorView(Project myParent){
		parent = myParent;
		open = false;		
		simPanel = new JPanel();
		
		this.setLayout(new GridLayout());
		add(simPanel);
	}
	
	public void setOpen(boolean value){
		open = value;
	}
	
	public boolean isOpen(){
		return open;
	}

	public JPanel getSimPanel() {
		return simPanel;
	}

	public void setSimPanel(JPanel simmPanel) {
		this.simPanel = simmPanel;
	}	
}
