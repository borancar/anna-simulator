package hr.fer.anna.GUI;

import org.jedit.syntax.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Component to be used as a tabComponent.
 * Contains the assembly editor and debug info.
 * 
 * @author marko
 * @version 0.1
 */
public class EditorView extends JPanel {
	static final long serialVersionUID = 1;
	
	private JLabel editorArea;
	private Project parent;
	
	private boolean open;
	
	/**
	 * Default ModelView constructor.
	 * 
	 * TODO: everything
	 */
	public EditorView(Project myParent){
		parent = myParent;
		open = false;
		
		setLayout(new BorderLayout());
		
		// add z text area
		final JPanel editorPane = new JPanel();
		editorPane.setLayout(new BoxLayout(editorPane, BoxLayout.PAGE_AXIS));
		editorPane.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
		
		final JEditTextArea textArea = new JEditTextArea();
		textArea.setTokenMarker(new PythonTokenMarker());
		textArea.setCaretBlinkEnabled(true);
		editorPane.add(textArea);
		
		add(BorderLayout.CENTER, editorPane);

		// add z buttonz
		JPanel buttonPane = new JPanel();
		JButton assembleButton = new JButton("Assemble");
		
		assembleButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				parent.setCode( textArea.getText() );
				String ret = parent.assemble();
				
				JOptionPane.showMessageDialog(editorPane, ret, "Assembly return", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(assembleButton);		
		
		add(BorderLayout.SOUTH, buttonPane);
		}
	
	public void setOpen(boolean value){
		open = value;
	}
	
	public boolean isOpen(){
		return open;
	}	
}
