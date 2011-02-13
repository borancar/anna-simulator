package hr.fer.anna.oisc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hr.fer.anna.GUI.ArchDisplay;
import hr.fer.anna.exceptions.BusAddressTaken;
import hr.fer.anna.exceptions.UnknownAddressException;
import hr.fer.anna.interfaces.IWordChangeListener;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Address;
import hr.fer.anna.uniform.Constant;
import hr.fer.anna.uniform.PlainBus;
import hr.fer.anna.uniform.SimpleMemory;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.util.concurrent.*;

public class OISCArchDisplay extends ArchDisplay {
	private JPanel modelPanel;
	private JPanel simPanel;	
	
	private JPanel parent;
	
	private Cpu cpu;
	private PlainBus bus;
	private SimpleMemory memory;
//	private long startingSimulationTime;
	private static final int MEMORY_SIZE = 1024;	
	
	private String code;
	
	
	public OISCArchDisplay(JPanel myParent){
		parent = myParent;
	
		this.bus = new PlainBus();
		this.cpu = new Cpu(this.bus);
		this.memory = new SimpleMemory(MEMORY_SIZE, 32);
		try {
			this.bus.registerBusUnit(this.memory, Address.fromWord(new Constant(0)));
		} catch (UnknownAddressException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (BusAddressTaken e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
//		this.startingSimulationTime = Simulator.getSimulator().getSimulatorTime();
		
		modelPanel = new JPanel();
		modelPanel.add(new JLabel("Nema nekih opcija za OISC :)"));
		
		simPanel = new JPanel();
		
		simPanel.setLayout(new BorderLayout());
		
		// add z status
		final JPanel statusPane = new JPanel();
		statusPane.setLayout(new BoxLayout(statusPane, BoxLayout.PAGE_AXIS));
		statusPane.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
		
		JPanel pcPane = new JPanel();
		final JLabel pcLabel = new JLabel("PC: ");
		pcPane.add(pcLabel);		
		statusPane.add(pcPane);
		
		// Memory lane :)))
		final JPanel memoryPane = new JPanel();
		memoryPane.setLayout(new BoxLayout(memoryPane, BoxLayout.PAGE_AXIS));
		memoryPane.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
		memoryPane.add(new JLabel("Memory:"));
		memoryPane.add(Box.createRigidArea(new Dimension(0, 10)));
		final JLabel memoryLabel = new JLabel("");
		memoryPane.add(memoryLabel);		
		
		statusPane.add(memoryPane);
		
		simPanel.add(BorderLayout.CENTER, statusPane);

		// add z buttonz
		JPanel buttonPane = new JPanel();
		JButton startSimButton = new JButton("Run simulation");
		
		startSimButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bus = new PlainBus();
				cpu = new Cpu(bus);
				memory = new SimpleMemory(MEMORY_SIZE, 32);
				try {
					bus.registerBusUnit(memory, Address.fromWord(new Constant(0)));
				} catch (UnknownAddressException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BusAddressTaken e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				startingSimulationTime = Simulator.getSimulator().getSimulatorTime();
				
				if ( code != null )
					assemble(code);	
				memoryLabel.setText("<running sim>");
				pcLabel.setText("PC: <running sim>");
				
				ExecutorService exec = Executors.newFixedThreadPool(1);
//				exec.execute(Simulator.getSimulator());
				exec.shutdown();
			}
		});
		
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(startSimButton);		
		
		simPanel.add(BorderLayout.SOUTH, buttonPane);
		
		this.cpu.describe().getRegister("PC").registerListener(new IWordChangeListener() {
			
			public void update(Word word) {
				if(word.getHexString().equals("10")) {					
					pcLabel.setText("PC: " + word.getHexString());

					Word peek;

					try { 
						peek = memory.read(Address.fromWord(new Constant(0)));
					} catch (Exception e){
						memoryLabel.setText("Memory exploded");
						return;
					}

					memoryLabel.setText("<html>");
					memoryLabel.setText(memoryLabel.getText() + peek.getHexString() + "<br>");
					cpu.halt();					
				}
			}
		
		});	

	}
	
	public String assemble(String code) {
		
		this.code = code;
		
		try {
			this.memory.write(Assembler.assemble(code), Address.fromWord(new Constant(0)));
		} catch (Exception e){
			return e.toString();
		}
		
		return new String("Assembly successfull!");
	}
	
	
	public JPanel getSimPanel(){
		return simPanel;
	}
	
	public JPanel getModelPanel(){
		return modelPanel;
	}
}
