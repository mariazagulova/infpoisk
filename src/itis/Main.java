package itis;

import java.io.File;
import java.io.IOException;

import org.htmlparser.util.ParserException;

public class Main {

	public static void main(String[] args) throws ParserException, IOException {
		// TODO Auto-generated method stub

		Matrix m = new Matrix();
		m.fillMatrix("vashdom.ru",200);
		//m.makeArray();
		//m.matrixFromFile(new File("/home/mariya/workspace/PageRank/разреженная.csv"));
		
		PR pagerank = new PR();
		pagerank.fillDict(m.getMatrix());
		//считать pagerank со значением multithread = true
		pagerank.PageRank(m.getMatrix(), true);
	}

}
