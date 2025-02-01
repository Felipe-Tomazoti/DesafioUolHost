package br.com.FelipeTomazoti.desafioUOL.service;

import br.com.FelipeTomazoti.desafioUOL.domain.Player;
import br.com.FelipeTomazoti.desafioUOL.domain.enums.PlayerEnum;
import br.com.FelipeTomazoti.desafioUOL.repository.PlayerRepository;
import br.com.FelipeTomazoti.desafioUOL.service.exception.NoCodenamesAvailable;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.json.JSONArray;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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
            String[] codenamesArray = XMLReaderService.xmlArray();

            for (String codename : codenamesArray) {
                availableCodenames.add(codename.trim());
            }

        } else if (group.equals(PlayerEnum.VINGADORES)) {
            JSONArray jsonArray = JSONReaderService.jsonList();

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
        String[] codenamesArray = XMLReaderService.xmlArray();
        for (String cod : codenamesArray) {
            if (cod.trim().equals(codename)) {
                return playerRepository.findByPlayerGroup(PlayerEnum.LIGA_DA_JUSTICA);
            }
        }

        JSONArray jsonArray = JSONReaderService.jsonList();
        for (int i = 0; i < jsonArray.length(); i++) {
            if (codename.equals(jsonArray.getJSONObject(i).getString("codinome").trim())) {
                return playerRepository.findByPlayerGroup(PlayerEnum.VINGADORES);
            }
        }
        return null;
    }
}
