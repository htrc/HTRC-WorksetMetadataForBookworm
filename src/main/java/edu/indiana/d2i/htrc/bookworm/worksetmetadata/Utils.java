package edu.indiana.d2i.htrc.bookworm.worksetmetadata;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Utils {
	public static String extractTextContent(Element e, String tagName) {
		NodeList nl = e.getElementsByTagName(tagName);
		if (nl != null) {
			Element firstElem = (Element)nl.item(0);
			return getCharacterDataFromElement(firstElem);
		}
		else return null;
	}
	
	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}
}
