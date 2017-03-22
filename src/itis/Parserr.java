package itis;

import java.util.LinkedList;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


public class Parserr {

	public static List<String> getLinksOnPage(final String url) throws ParserException {
	    final Parser htmlParser = new Parser(url);
	    final List<String> result = new LinkedList<String>();

	    try {
	        final NodeList tagNodeList = htmlParser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
	        //for (int j = 0; j < tagNodeList.size(); j++) {
	        	int j=0;
	        	while (j < tagNodeList.size() && j<30) {
	        		final LinkTag loopLink = (LinkTag) tagNodeList.elementAt(j);
	        		final String loopLinkStr = loopLink.getLink();
	        		result.add(loopLinkStr);
	        		j++;
	        	}
	            
	        //    System.out.println(loopLinkStr);
	        
	    } catch (ParserException e) {
	        e.printStackTrace(); // TODO handle error
	    }

	    return result;
	}
}
