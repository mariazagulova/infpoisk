package itis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import org.htmlparser.util.ParserException;


public class Matrix {

	HashMap<String, ListOfDestinations> matrix = new HashMap<String, ListOfDestinations>();
	//ОЧЕРЕДЬ, ПО КАКИМ ССЫЛКАМ СКАЧЕМ
	LinkedList<String> queue = new LinkedList<String>();
			
	public HashMap<String, ListOfDestinations> getMatrix() {
		return matrix;
	}
	
	
	public Matrix() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void addVertex(String vertex) {
		if (! vertex.contains("void(0)") && ! vertex.equals("")) matrix.put(vertex, new ListOfDestinations());
	}
	
	
	public void addEdge(String from, String to) {
		ListOfDestinations list = matrix.get(from);
		if (! to.contains("void(0)")) list.add(to);
		matrix.put(from, list);
	}
	
	//ПОЛУЧИТЬ СПИСОК, КУДА ВЕДЁТ ВЕРШИНА
	public ListOfDestinations getDestinationsOf(String vertex) {
		return matrix.get(vertex);
	}
	
	public void runOneLink(Parserr parser, String link, String startPage) throws ParserException {
		
		if (! matrix.containsKey(link)) {
			addVertex(link);
			//System.out.println(link);
		}
		
		List<String> destinations = parser.getLinksOnPage(link);
		for (String destination: destinations) {	
			//ЗАПОЛНИТЬ СТОЛБЕЦ
			addEdge(link, destination);
			
			//ДОБАВИТЬ СТРОКУ
			if (! matrix.containsKey(destination)) {
				addVertex(destination);
				if ((! destination.contains("void(0)")) && (! destination.contains("account")) && (! destination.contains("members")) &&(! destination.contains("attachments")) && (destination.contains("http")) && (destination.contains(startPage)))
					queue.addLast(destination);
			}
		}
	}
	
	public void fillMatrix(String startPage, int maxSize) throws ParserException, IOException {
	
		int i=0;
		
		Parserr parser = new Parserr();
		
		queue.addLast("http://" + startPage + "/");
		
		while ((! queue.isEmpty()) && (matrix.keySet().size() < maxSize)) {
			String currentLink = queue.removeFirst();
			
			
			if ((! currentLink.contains("void(0)")) && (! currentLink.contains("account")) && (! currentLink.contains("attachments")) &&(! currentLink.contains("members")) &&(currentLink.contains("http")) && (currentLink.contains(startPage))) { // && (currentLink.startsWith(startPage))) {
				System.out.println(currentLink);
				runOneLink(parser, currentLink, startPage);
				i++;
			}
			
		}
		
		//сохраним в файл
		FileWriter fw = new FileWriter(new File("/home/mariya/workspace/PageRank/разреженная.csv"));
		for (String key: matrix.keySet()) {
			fw.write(key);
			for (String link: matrix.get(key).getDestinations()) {
				fw.write("\t" + link);	
			}
			fw.write("\n");
			fw.flush();
			
		}
		fw.close();
	}

	public void matrixFromFile(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		while (sc.hasNextLine()) {
			String[] temp = sc.nextLine().split("\t");
			String from = temp[0];
			addVertex(from);
			for (int i=1; i<temp.length; i++) {
				if (! temp[i].equals("")) addEdge(from, temp[i]); 
			}
		}
		
		sc.close();
	}
	
	public int getSize() {
		return matrix.size();
	}
	
	
	
	public boolean[][] makeArray() throws IOException {
		
		HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
		//изначально вся таблица из нулей
		boolean[][] array = new boolean[matrix.size()][matrix.size()];
		for (int i=0; i<matrix.size(); i++) {
			for (int j=0; j<matrix.size(); j++) {
				array[i][j] = false;
			}
		}
		
		
		//ЗАПОЛНИМ СЛОВАРЬ
		System.out.println("словарь:");
		int i=0;
		for (String link: matrix.keySet()) {
			dictionary.put(link, i);
			System.out.println(link + i);
			i++;
		}
		
		
		System.out.println("таблица:");
		//ЗАПОЛНИМ ТАБЛИЦУ СМЕЖНОСТИ
		for (String key: matrix.keySet()) {
			ListOfDestinations destinations = matrix.get(key);
			for (String destination: destinations.list) {
				if (dictionary.containsKey(key) && dictionary.containsKey(destination)) {
					if (dictionary.get(key) != dictionary.get(destination)) {
						array[dictionary.get(key)][dictionary.get(destination)] = true;
						//System.out.println(key + destination);
					}
					
				}
				
			}
		}
		
		System.out.println("таблица готова");
		//выведем таблицу
		for (int k=0; k<matrix.size(); k++) {
			for (int j=0; j<matrix.size(); j++) {
				
				System.out.print(" " + array[k][j]);
			}
			System.out.println();
		}
		
		//и сохраним в файл
		toFile(dictionary, array);
		toFile(dictionary);
		
		return array;
	}
	
	public void toFile(HashMap<String, Integer> dictionary, boolean[][] array) throws IOException {
		FileWriter fw = new FileWriter(new File("/home/mariya/workspace/PageRank/таблица смежности веб-страниц.csv"));
		String[] columnNames = new String[matrix.size()];
		for (int k=0; k<matrix.size(); k++) {
			columnNames[k] = "";
		}
		for (String http: dictionary.keySet()) {
			columnNames[dictionary.get(http)] = http;
		}
		
		for (int k=0; k<matrix.size(); k++) {
			fw.write("\t" + columnNames[k]);
		}
			
		for (int k=0; k<matrix.size(); k++) {
			fw.write("\n");
			fw.write("\t" + columnNames[k]);
			for (int j=0; j<matrix.size(); j++) {
				if (array[k][j] == true) {
					fw.write("\t1");
				} else {
					fw.write("\t0");
				}
			}
			fw.flush();
		}
		fw.close();
	}
	
	public void toFile(HashMap<String, Integer> dictionary) throws IOException {
		FileWriter fw = new FileWriter(new File("/home/mariya/workspace/PageRank/словарь.csv"));
		String[] dict = new String[matrix.size()];
		for (int i=0; i<matrix.size(); i++) {
			dict[i] = "";
		}
		for (String http: dictionary.keySet()) {
			dict[dictionary.get(http)] = http;
		}
		for (int i=0; i<matrix.size(); i++) {
			fw.write(dict[i] + "\t" + i + "\n");
		}
		fw.close();
	}
	
}
