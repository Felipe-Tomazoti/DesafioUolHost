package br.com.FelipeTomazoti.desafioUOL.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class JSONReaderService {

    public static JSONArray jsonList() throws IOException, ParserConfigurationException, SAXException, JSONException {
        URL url = new URL("https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/vingadores.json");
        InputStream in = url.openStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder jsonText = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            jsonText.append(line);
        }
        reader.close();

        JSONObject jsonObject = new JSONObject(jsonText.toString());
        return jsonObject.getJSONArray("vingadores");
    }
}
