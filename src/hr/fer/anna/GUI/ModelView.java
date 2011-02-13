package hr.fer.anna.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComboBox;


/**
 * Component to be used as a tabComponent.
 * Contains the model configuration dialogue.
 * 
 * @author marko
 * @version 0.1
 */
public class ModelView extends JPanel {
	static final long serialVersionUID = 1;
	
	private Project parent;
	boolean open;
	
	JTextField nameTextField;
	
	/**
	 * Default ModelView constructor.
	 * 
	 * TODO: everything
	 * @author Marko	
	 */
	
	public void updateName(){
		nameTextField.setText(parent.getProjectName());
	}
	
	public ModelView(Project myParent){
		parent = myParent;
		open = false;		
		
		setLayout(new BorderLayout());
		
		// add z properties
		JPanel modelPane = new JPanel();
		modelPane.setLayout(new BoxLayout(modelPane, BoxLayout.PAGE_AXIS));
		modelPane.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
		
		// project name
		JPanel namePane = new JPanel();
		namePane.setLayout(new BoxLayout(namePane, BoxLayout.LINE_AXIS));
		
		JLabel nameLabel = new JLabel("Project name:");		
		namePane.add(nameLabel);
		namePane.add(Box.createRigidArea(new Dimension(10, 0)));
		
		nameTextField = new JTextField(20);
		
		//nameTextField.setText(parent.getProjectName());
		
		nameTextField.setMaximumSize(new Dimension(100, 30));
		nameTextField.setText(parent.getProjectName());
		
		nameTextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				parent.setProjectName(nameTextField.getText());
			}
		});
		
		namePane.add(nameTextField);
		namePane.add(Box.createHorizontalGlue());	
		
		modelPane.add(namePane);
		
		// Arch chooser
		JPanel classPane = new JPanel();
		classPane.setLayout(new BoxLayout(classPane, BoxLayout.LINE_AXIS));
		classPane.add(new JLabel("Architecture:"));
		classPane.add(Box.createRigidArea(new Dimension(10, 0)));
		
		String[] defaultClasses = { "OISC" };
		
		JComboBox classCombo = new JComboBox(defaultClasses);
		classCombo.setMaximumSize(new Dimension(200, 30));
			
		classPane.add(classCombo);		
		classPane.add(Box.createHorizontalGlue());
		
		modelPane.add(classPane);
		
		// determine the correct arch
		ArchDisplay selectedArch = new hr.fer.anna.oisc.OISCArchDisplay(this);
		if ( classCombo.getSelectedItem().toString() == "OISC" )
			selectedArch = new hr.fer.anna.oisc.OISCArchDisplay(this);
		
		parent.setArch(selectedArch);
		
		// Arch pane
		JPanel archPane = selectedArch.getModelPanel();
				
		modelPane.add(archPane);
		modelPane.add(Box.createVerticalGlue());
		
		add(BorderLayout.CENTER, modelPane);
		
		// add z buttonz
		JPanel buttonPane = new JPanel();
		JButton codeButton = new JButton("Editor");
		JButton simulateButton = new JButton("Simulator");
		
		codeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				parent.showEditorView();
			}
		});
		
		simulateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				parent.showSimView();
			}
		});
		
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(codeButton);		
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(simulateButton);		
		
		add(BorderLayout.SOUTH, buttonPane);
	}
	
	public void setOpen(boolean value){
		open = value;
	}
	
	public boolean isOpen(){	
		return open;
	}
}
