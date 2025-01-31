package br.com.FelipeTomazoti.desafioUOL.domain;

import br.com.FelipeTomazoti.desafioUOL.domain.enums.PlayerEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Table(name = "players")
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "O Nome é obrigatório")
    private String name;

    @NotNull(message = "O email é obrigatório")
    private String mail;

    private String contact;

    private String codename;

    @Enumerated(EnumType.STRING)
    @Column(name = "player_group")
    private PlayerEnum playerGroup;

    public Player() {
    }

    public Player(Player player) {
        this.id = player.id;
        this.name = player.name;
        this.mail = player.mail;
        this.contact = player.contact;
        this.codename = player.codename;
        this.playerGroup = player.playerGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "O Nome é obrigatório") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "O Nome é obrigatório") String name) {
        this.name = name;
    }

    public @NotNull(message = "O email é obrigatório") String getMail() {
        return mail;
    }

    public void setMail(@NotNull(message = "O email é obrigatório") String mail) {
        this.mail = mail;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public PlayerEnum getPlayerGroup() {
        return playerGroup;
    }

    public void setPlayerGroup(PlayerEnum playerGroup) {
        this.playerGroup = playerGroup;
    }
}
