package pl.lua.aws.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lua.aws.core.domain.Tournament;
import pl.lua.aws.core.model.TournamentEntity;
import pl.lua.aws.core.repository.TournamentRepository;
import pl.lua.aws.core.util.Mapper;

import java.util.List;

@Service
@Slf4j
public class TournamentService {

    @Autowired
    TournamentRepository tournamentRepository;

    @Transactional
    public void createTournament(Tournament tournament){
        TournamentEntity tournamentEntity = Mapper.map(tournament,TournamentEntity.class);
        tournamentEntity = tournamentRepository.save(tournamentEntity);
        log.info("Create new Tournament {} ",tournamentEntity);
    }

    public List<Tournament> getAllTournaments() {
        List<TournamentEntity> tournamentEntities = tournamentRepository.findAll();
        return Mapper.mapAsList(tournamentEntities,Tournament.class);
    }
}
