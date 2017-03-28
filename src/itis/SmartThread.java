package itis;

import java.util.HashMap;

public class SmartThread extends Thread {
	
	HashMap<String, ListOfProbabilities> matrix = new HashMap<String, ListOfProbabilities>(); 
	HashMap<String,Double> vector = new HashMap<String,Double>();
	int start=0; int end=0;
	String[] order = new String[vector.keySet().size()];
	
	public SmartThread(HashMap<String,Double> vector, HashMap<String, ListOfProbabilities> matrix, int start, int end, String[] order) {
		// TODO Auto-generated constructor stub
		this.matrix = matrix;
		this.vector = vector;
		this.start = start;
		this.end = end;
		this.order = order;
	}
	
	
	@Override
    public void run() {
        try {
            for (int i=start; i<=end; i++) {
            	String vectorkey = order[i];
            	double old = vector.get(vectorkey);
    			double sum = 0.15;
    			for (String matrixkey: matrix.keySet()) {
    				if (matrix.get(matrixkey).contains(vectorkey)) {
    					//вот тут умножаем (и сразу в сумму)
    					sum = sum + 0.85*(matrix.get(matrixkey).get(vectorkey) * vector.get(vectorkey));
    				}
    			}
    			//if (sum > old) sum = sum - old;
    			vector.put(vectorkey, sum);
            }
            Thread.sleep(5000);
           // System.out.println("Smart Thread "+start);
        } catch(InterruptedException i_ex) {
            //System.out.println();
        }
    }

}
