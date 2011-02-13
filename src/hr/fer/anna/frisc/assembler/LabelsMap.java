package hr.fer.anna.frisc.assembler;

import java.util.HashMap;
import java.util.Map;

/**
 * Mapiranje labela.
 * Inicijalizacija i način korištenja malo krše ideju singletona,
 * no i ovo je ok.
 * 
 * @author Ivan
 */
public class LabelsMap {
	private Map<String, Integer> labels;
	
	
	private LabelsMap() {
		this.labels = new HashMap<String, Integer>();
	}
	
	/**
	 * Inicijalizacija
	 */
	public void init() {
		this.labels.clear();
	}

	private static class SingletonHolder {
		private static final LabelsMap INSTANCE = new LabelsMap();
	}

	public static LabelsMap get() {
		return SingletonHolder.INSTANCE;
	}
	
	public void mapLabel(String label, Integer value) {
		this.labels.put(label, value);
	}
	
	public Integer getLabelValue(String label) {
		return this.labels.get(label);
	}
	
	public void printContents() {
		for (String key : this.labels.keySet()) {
			System.out.println(key + " - " + this.labels.get(key));
		}
	}

}
