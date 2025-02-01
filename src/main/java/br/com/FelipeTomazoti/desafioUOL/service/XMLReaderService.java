package br.com.FelipeTomazoti.desafioUOL.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class XMLReaderService {

    public static String[] xmlArray() throws IOException, ParserConfigurationException, SAXException {
        java.net.URL url = new URL("https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml");
        InputStream in = url.openStream();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(in);
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("codinomes");
        String codenameString = nodeList.item(0).getTextContent().trim();

        return codenameString.split("\n");
    }
}
