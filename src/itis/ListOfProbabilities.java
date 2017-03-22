package itis;

import java.util.HashMap;

public class ListOfProbabilities {

	HashMap<String, Double> prob = new HashMap<String, Double>();
	
	public ListOfProbabilities() {
		// TODO Auto-generated constructor stub
	}
	
	public void add(String key, Double probability) {
		prob.put(key, probability);
	}
	
	public boolean contains(String key) {
		return prob.containsKey(key);
		
	}
	
	public Double get(String key) {
		return prob.get(key);
	}
}
