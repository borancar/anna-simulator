package hr.fer.anna.GUI;

import hr.fer.anna.exceptions.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.UIManager;

/**
 * Main frame
 * 
 * @author Ivan
 * @version 0.2
 */
public class Window extends JFrame {
	public static final long serialVersionUID = 1;
	
	// TODO: array or something....
	private ArrayList<Project> project;
	/**
	 * GUI Constructor
	 * 
	 * @author Ivan
	 * @since 0.1
	 */
	public Window() {
		initGUI();

		// TODO: Set some normal size, dynamic?
		this.setSize(500, 400);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("ANNA");
		this.setLocationByPlatform(true);
		this.setVisible(true);
	}
	
	private JLabel statusBar;
	private JMenuBar menuBar;
	private JTabbedPane mainArea;

	protected void addTab(String name, JPanel tab){
		mainArea.addTab(name, tab);
	}
	
	protected int getTabIndex(JComponent tab) throws InvalidRequest{
		int i = mainArea.indexOfComponent(tab);
		if ( i == -1 ) throw new InvalidRequest("");
		return i;
	}
	
	protected int getTabIndex(String name){
		return mainArea.indexOfTab(name);
	}
	
	protected void setTabName(int tab, String name){
		mainArea.setTitleAt(tab, name);
	}
	
	protected void setTabFocus(int tab){
		mainArea.setSelectedIndex(tab);
	}
	
	
	/**
	 * Initialize the GUI.
	 * 
	 * @author Marko
	 * @since 0.2
	 */
	protected void initGUI() {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e){
			// TODO: ajdontnow, blow shit up
		}
		
		getContentPane().setLayout(new BorderLayout());
		
		// Adds the menu bar
		menuBar = new JMenuBar();
		
		JMenu menuFile = new JMenu("File");
		JMenu menuHelp = new JMenu("Help");
		
		menuBar.add(menuFile);
		JMenuItem menuExit = new JMenuItem("Exit");
		JMenuItem menuNew = new JMenuItem("New");
		
		menuFile.add(menuNew);
		menuFile.add(menuExit);
		
		final Window me = this;
		
		project = new ArrayList<Project>(0);
		
		menuNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				// TODO: add to thread, could be slow
				project.add(new Project(me));
			}
		});

		
		menuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				// TODO: cleanup?
				System.exit(0);
			}
		});
		
		menuBar.add(menuHelp);		
		JMenuItem menuAbout = new JMenuItem("About");
		menuHelp.add(menuAbout);
		
		// About box, TODO: organize this a lot! :)
		final JPanel aboutPanel = new JPanel();
		final JLabel aboutLabel = new JLabel("ANNA Nije Novi Atlas :)");
		aboutPanel.add(aboutLabel);
		
		menuAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				mainArea.addTab("About", aboutPanel);
			}
		});
		
		add(BorderLayout.NORTH, menuBar);
		
		// Adds the status bar
		statusBar = new JLabel("Welcome to ANNA, the only simulator that cares!");
		add(BorderLayout.SOUTH, statusBar);
		
		
		// Adds the main work area, tabbed, oh yeah! :)
		mainArea = new JTabbedPane();
		
		add(BorderLayout.CENTER, mainArea);
	}

	/**
	 * Main entry point for the application
	 * 
	 * @param args
	 *            Command line arguments
	 * @author Ivan
	 * @since 0.1
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Window();
			}

		});
		return;

	}

}
