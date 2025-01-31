package br.com.FelipeTomazoti.desafioUOL.repository;

import br.com.FelipeTomazoti.desafioUOL.domain.Player;
import br.com.FelipeTomazoti.desafioUOL.domain.enums.PlayerEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByCodename(String codename);
    List<Player> findByPlayerGroup(PlayerEnum playerGroup);
}
