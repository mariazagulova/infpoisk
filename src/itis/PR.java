package itis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class PR {

	HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
	
	public HashMap<String,Double> iteration(HashMap<String, ListOfProbabilities> matrix, HashMap<String,Double> vector) {
	
		for (String vectorkey: vector.keySet()) {
			//ищем все встречающиеся упоминания, умножаем на соотв.куски вектора и складываем всё
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
		
		
		return vector;
	}
	
	public void fillDict(HashMap<String,ListOfDestinations> matrix) {
		//ЗАПОЛНИМ СЛОВАРЬ
		System.out.println("словарь:");
		int i=0;
		for (String link: matrix.keySet()) {
			dictionary.put(link, i);
			System.out.println(link + i);
			i++;
		}
	}
			
	
	public HashMap<String,Double> PageRank(HashMap<String,ListOfDestinations> matrix) throws IOException {
		HashMap<String, ListOfProbabilities> probMatrix = formProbabilityMatrix(matrix);
		HashMap<String,Double> vector = new HashMap<String,Double>();
		
		/*for (String s: matrix.keySet()) {
			System.out.println(s);
			System.out.println(matrix.get(s).size());
		}*/
		
		
		
		//заполнить вектор 1/n
		double msize = matrix.keySet().size();
		
		for (String key: matrix.keySet()) {
			vector.put(key, 1/msize);
		}
		
		//System.out.println("МАТРИЦА РАЗМЕРОВ " + probMatrix.keySet().size());
		//System.out.println("ВЕКТОР ТОЖЕ " + vector.keySet().size());
		
		
		//итерации
		
		double difference = 0;
		for (int i=0; i<20; i++) {
			System.out.println("ИТЕРАЦИЯ " + i);
			FileWriter fw = new FileWriter(new File("/home/mariya/workspace/PageRank/vector.csv"),false);
			HashMap<String,Double> oldVector = vector;
			double sum = 0;
			double sum2 = 0;
			
			vector = iteration(probMatrix, vector);
			
			Double[] vec = new Double[vector.keySet().size()];
			for (String s: vector.keySet()) {
				//System.out.printf(" %.4f",vector.get(s));
				vec[dictionary.get(s)] = vector.get(s);
				fw.write(s + "\t" + vector.get(s) + "\n");
				fw.flush();
				
			}
			for (int k=0; k < vec.length; k++) {
				System.out.printf(" %.4f",vec[k]);
				
			}
			System.out.println();
			fw.close();
			
		}
		
		
		return vector;
	}
	
	public HashMap<String, ListOfProbabilities> formProbabilityMatrix(HashMap<String, ListOfDestinations> matrix) {
		HashMap<String, ListOfProbabilities> probs = new HashMap<String, ListOfProbabilities>();
		
		for (String key: matrix.keySet()) {
			ListOfDestinations links = matrix.get(key);
			ListOfProbabilities currentprob = new ListOfProbabilities();
			
			for (String link: links.getDestinations()) {
				//System.out.println(link);
				//каждой линке присвоить значение 1/matrix.get(key).size()
				if (matrix.containsKey(link)) {
					if (matrix.get(link).size() != 0) {
						currentprob.add(link, 1/matrix.get(link).size());
						//System.out.println(matrix.get(link).size());
						//System.out.println(1/matrix.get(link).size());
					}
					
				}
			}
			
			probs.put(key, currentprob);		
			
		}
		
		
		
		return probs;
	}
	
	
}
