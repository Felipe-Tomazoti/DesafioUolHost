package br.com.FelipeTomazoti.desafioUOL.service;

import br.com.FelipeTomazoti.desafioUOL.domain.Player;
import br.com.FelipeTomazoti.desafioUOL.domain.enums.PlayerEnum;
import br.com.FelipeTomazoti.desafioUOL.repository.PlayerRepository;
import br.com.FelipeTomazoti.desafioUOL.service.exception.NoCodenamesAvailable;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.json.JSONObject;
import org.json.JSONArray;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public record PlayerService(PlayerRepository playerRepository) {

    public Player playerRegistration(Player player) throws IOException, ParserConfigurationException, SAXException, JSONException {
        if (player.getPlayerGroup() == null) {
            player.setPlayerGroup(PlayerEnum.LIGA_DA_JUSTICA);
        }
        player.setCodename(codename(player.getPlayerGroup()));
        if (player.getCodename() == null) {
            throw new NoCodenamesAvailable("Sorry!, no codenames available");
        }
        return playerRepository.save(player);
    }

    private boolean verifyCodename(String codename) {
        return !playerRepository.existsByCodename(codename);
    }

    public String codename(PlayerEnum group) throws IOException, ParserConfigurationException, SAXException, JSONException {
        List<String> availableCodenames = new ArrayList<>();

        if (group.equals(PlayerEnum.LIGA_DA_JUSTICA)) {
            java.net.URL url = new URL("https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml");
            InputStream in = url.openStream();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(in);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("codinomes");
            String codenameString = nodeList.item(0).getTextContent().trim();

            String[] codenamesArray = codenameString.split("\n");

            for (String codename : codenamesArray) {
                availableCodenames.add(codename.trim());
            }

        } else if (group.equals(PlayerEnum.VINGADORES)) {
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
            JSONArray jsonArray = jsonObject.getJSONArray("vingadores");

            for (int i = 0; i < jsonArray.length(); i++) {
                availableCodenames.add(jsonArray.getJSONObject(i).getString("codinome").trim());
            }
        }

        for (String codenome : availableCodenames) {
            if (verifyCodename(codenome)) {
                return codenome;
            }
        }

        return null;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }


    public List<Player> getAllSameGroup(String codename) throws IOException, ParserConfigurationException, SAXException, JSONException {

        java.net.URL url = new URL("https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml");
        InputStream in = url.openStream();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(in);
        document.getDocumentElement().normalize();
        NodeList nodeList = document.getElementsByTagName("codinomes");

        String codenameString = nodeList.item(0).getTextContent().trim();
        String[] codenamesArray = codenameString.split("\n");

        for (String cod : codenamesArray) {
            if (cod.trim().equals(codename)) {
                return playerRepository.findByPlayerGroup(PlayerEnum.LIGA_DA_JUSTICA);
            }
        }

        URL urlJSON = new URL("https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/vingadores.json");
        InputStream input = urlJSON.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder jsonText = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonText.append(line);
        }
        reader.close();

        JSONObject jsonObject = new JSONObject(jsonText.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("vingadores");

        for (int i = 0; i < jsonArray.length(); i++) {
            if (codename.equals(jsonArray.getJSONObject(i).getString("codinome").trim())) {
                return playerRepository.findByPlayerGroup(PlayerEnum.VINGADORES);
            }
        }
        return null;
    }
}
