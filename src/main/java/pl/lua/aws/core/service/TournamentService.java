package pl.lua.aws.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lua.aws.core.domain.PokerPlayer;
import pl.lua.aws.core.domain.Tournament;
import pl.lua.aws.core.domain.TournamentForm;
import pl.lua.aws.core.model.PokerPlayerEntity;
import pl.lua.aws.core.model.TournamentEntity;
import pl.lua.aws.core.model.TournamentScoresEntity;
import pl.lua.aws.core.repository.PokerPlayerRepository;
import pl.lua.aws.core.repository.TournamentRepository;
import pl.lua.aws.core.repository.TournamentScoresRepository;
import pl.lua.aws.core.repository.UserRepository;
import pl.lua.aws.core.util.Mapper;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private PokerPlayerRepository pokerPlayerRepository;

    @Autowired
    private TournamentScoresRepository tournamentScoresRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void createTournament(TournamentForm tournament){
        TournamentEntity tournamentEntity = Mapper.map(tournament,TournamentEntity.class);
        tournamentEntity = tournamentRepository.save(tournamentEntity);
        log.info("Create new Tournament {} ",tournamentEntity);
    }

    @Transactional
    public void registerPlayer(Long tournamentId){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getName();
        Long playerId = userRepository.getPlayerIdByUserId(authentication.getName());

        TournamentScoresEntity existingScore = tournamentScoresRepository.findByTournament_IdAndPlayer_Id(tournamentId,playerId);
        if(existingScore!=null){
            log.warn("Player with id: {} already registered for Tournament with id: {} ",playerId,tournamentId);
        }else{
            TournamentEntity tournament = tournamentRepository.getOne(tournamentId);
            PokerPlayerEntity player = pokerPlayerRepository.getOne(playerId);

            TournamentScoresEntity score = new TournamentScoresEntity();
            score.setTournament(tournament);
            score.setPlayer(player);

            tournamentScoresRepository.save(score);
            log.info("Register player with id: {} for Tournament with id: {} ",playerId,tournamentId);
        }
    }

    @Transactional
    public void unregisterPlayer(Long tournamentId){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getName();
        Long playerId = userRepository.getPlayerIdByUserId(authentication.getName());

        TournamentScoresEntity existingScore = tournamentScoresRepository.findByTournament_IdAndPlayer_Id(tournamentId,playerId);
        if(existingScore!=null){
            tournamentScoresRepository.delete(existingScore);
            log.info("Player with id: {} unregistered from Tournament with id: {} ",playerId,tournamentId);
        }else{
            log.warn("Player with id: {} isn't registered for Tournament with id: {} yet",playerId,tournamentId);
        }
    }

    public List<Tournament> getAllTournaments() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getName();
        Long playerId = userRepository.getPlayerIdByUserId(authentication.getName());


        List<TournamentEntity> tournamentEntities = tournamentRepository.findAll();
        List<Tournament> tournaments = new ArrayList<>();

        tournamentEntities.stream().forEach(t ->{
            Tournament tournament = Mapper.map(t,Tournament.class);
            if(t.getScores().stream().filter(s -> s.getPlayer().getId().equals(playerId)).findAny().isPresent()) {
                tournament.setRegistered(true);
            }
            tournaments.add(tournament);
        });
        return tournaments;
    }

    public Tournament getTouranment(Long tournamentId){
        TournamentEntity tournamentEntity = tournamentRepository.getOne(tournamentId);
        if(tournamentEntity !=null ){
            Tournament tournament = Mapper.map(tournamentEntity,Tournament.class);
            List<PokerPlayer> participants = new ArrayList<>();

            tournamentEntity.getScores().stream()
                    .forEach(score -> {
                        PokerPlayer pokerPlayer = Mapper.map(score.getPlayer(),PokerPlayer.class);
                        participants.add(pokerPlayer);
                    });
            tournament.setParticipants(participants);
            return tournament;
        }else {
            return null;
        }
    }
}
