package org.luncert.objpool;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Configure {

    public static int port = 6428;

    public static String host = "localhost";

    public static void autoConfigByFile() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
			DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(Configure.class.getClassLoader().getResourceAsStream("config.xml"));
            Node server = doc.getElementsByTagName("server").item(0);
            NodeList serverChild = server.getChildNodes();
            for (int i = 0, limit = serverChild.getLength(); i < limit; i++) {
                Node child = serverChild.item(i);
                if (child.getNodeName().compareTo("host") == 0) {
                    host = child.getTextContent();
                }
                else if (child.getNodeName().compareTo("port") == 0) {
                    port = Integer.valueOf(child.getTextContent());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
        
    }

}