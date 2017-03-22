package itis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class ListOfDestinations {
	
	HashSet<String> list = new HashSet<String>();
	
	public ListOfDestinations() {
		// TODO Auto-generated constructor stub
	}
	
	public void add(String value) {
		list.add(value);
	}

	public HashSet<String> getDestinations() {
		return list;
	}

	public Double size() {
		return ((double)list.size());
	}
	
	
}
