package br.com.FelipeTomazoti.desafioUOL.controller;

import br.com.FelipeTomazoti.desafioUOL.domain.Player;
import br.com.FelipeTomazoti.desafioUOL.service.PlayerService;
import jakarta.validation.Valid;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("player")
public record PlayerController(PlayerService playerService) {

    @PostMapping
    private ResponseEntity playerRegistration(@RequestBody @Valid Player player) throws IOException, ParserConfigurationException, SAXException, JSONException {
        playerService.playerRegistration(player);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    private ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        return ResponseEntity.ok().body(players);
    }

    @GetMapping("/{codename}")
    private ResponseEntity<List<Player>> getAllSameGroup(@PathVariable String codename) throws IOException, ParserConfigurationException, SAXException, JSONException {
        List<Player> players = playerService.getAllSameGroup(codename);
        return ResponseEntity.ok().body(players);
    }
}
