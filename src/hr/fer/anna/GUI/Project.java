package hr.fer.anna.GUI;

import hr.fer.anna.exceptions.*;

/**
 * Class that contains all stuff behind managing one project.
 * Including but not limited to:
 *	Model view, Editor view, Simulation view
 *	Filename, Project configuration
 *
 * @author marko
 * @version 0.1
 */
public class Project {
	private ModelView modelView;
	private EditorView editorView;
	private SimulatorView simView;
	
	private Window parent;
	
	private String code;
	
	private String name;
	
	private ArchDisplay arch;
	
	public ArchDisplay getArch() {
		return arch;
	}

	public void setArch(ArchDisplay arch) {
		this.arch = arch;		
		simView.getSimPanel().add( arch.getSimPanel() );
	}

	/**
	 * Creates "unnamed" project.
	 * 
	 * TODO: everything
	 */	
	Project(Window myParent){	
		setParent(myParent);
		
		
		editorView = new EditorView(this);
		simView = new SimulatorView(this);
		modelView = new ModelView(this);
		
		setProjectName("New project");
		
		showModelView();
	}
	
	public void setProjectName(String newName){
		name = newName;
		modelView.updateName();
		if ( modelView.isOpen() ){
			try {
				parent.setTabName(parent.getTabIndex(modelView), name + " - model");
			}	
			catch (InvalidRequest e){
				//TODO: nist
			}
		}
		if ( editorView.isOpen() ){
			try {
				parent.setTabName(parent.getTabIndex(editorView), name + " - editor");
			}	
			catch (InvalidRequest e){
				//TODO: nist
			}
		}		
		if ( simView.isOpen() ){
			try {
				parent.setTabName(parent.getTabIndex(simView), name + " - status");
			}	
			catch (InvalidRequest e){
				//TODO: nist
			}
		}				
	}
	
	public String getProjectName(){
		return name;
	}
	
	public void showModelView(){
		if ( !modelView.isOpen() ) {
			modelView.setOpen(true);
			parent.addTab(getProjectName() + " - model", modelView);
		}
	}

	public void showEditorView(){
		if ( !editorView.isOpen() ) {
			editorView.setOpen(true);
			parent.addTab(getProjectName() + " - code", editorView);
		}
		try {
			parent.setTabFocus(parent.getTabIndex(editorView));
		}
		catch ( InvalidRequest e ){
			
		}		
	}
	
	public void showSimView(){
		if ( !simView.isOpen() ) {
			simView.setOpen(true);
			parent.addTab(getProjectName() + " - status", simView);
		}
		try {
			parent.setTabFocus(parent.getTabIndex(simView));
		}
		catch ( InvalidRequest e ){
			
		}		
	}	
		
	/**
	 * Returns the ModelView component for this project.
	 * 
	 * @author marko
	 */
	public ModelView getModelView(){
		return modelView;
	}
	
	/**
	 * Return the EditorView component for this project.
	 */
	public EditorView getEditorView(){
		return editorView;
	}

	public Window getParent() {
		return parent;
	}

	public void setParent(Window parent) {
		this.parent = parent;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String assemble(){
		return arch.assemble(code);
	}
}
