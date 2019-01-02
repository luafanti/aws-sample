package pl.lua.aws.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lua.aws.core.domain.PokerPlayer;
import pl.lua.aws.core.domain.Tournament;
import pl.lua.aws.core.domain.TournamentForm;
import pl.lua.aws.core.domain.TournamentGallery;
import pl.lua.aws.core.helper.UserHelper;
import pl.lua.aws.core.model.PokerPlayerEntity;
import pl.lua.aws.core.model.TournamentEntity;
import pl.lua.aws.core.model.TournamentPhotoEntity;
import pl.lua.aws.core.model.TournamentScoresEntity;
import pl.lua.aws.core.repository.*;
import pl.lua.aws.core.util.Mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private TournamentPhotoRepository tournamentPhotoRepository;


    @Transactional
    public void createTournament(TournamentForm tournament){
        TournamentEntity tournamentEntity = Mapper.map(tournament,TournamentEntity.class);
        tournamentEntity = tournamentRepository.save(tournamentEntity);
        log.info("Create new Tournament {} ",tournamentEntity);
    }

    @Transactional
    public void registerPlayer(Long tournamentId){

        Long playerId = userHelper.getPlayerId();
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

        Long playerId = userHelper.getPlayerId();
        TournamentScoresEntity existingScore = tournamentScoresRepository.findByTournament_IdAndPlayer_Id(tournamentId,playerId);
        if(existingScore!=null){
            tournamentScoresRepository.delete(existingScore);
            log.info("Player with id: {} unregistered from Tournament with id: {} ",playerId,tournamentId);
        }else{
            log.warn("Player with id: {} isn't registered for Tournament with id: {} yet",playerId,tournamentId);
        }
    }

    public List<Tournament> getAllTournaments() {

        Long playerId = userHelper.getPlayerId();
        List<TournamentEntity> tournamentEntities = tournamentRepository.findAll();
        List<Tournament> tournaments = new ArrayList<>();

        tournamentEntities.stream().forEach(t ->{
            Tournament tournament = Mapper.map(t,Tournament.class);
            if(tournament.getDate()!=null && checkTournamentFinished(tournament.getDate())){
                tournament.setFinished(true);
            }
            if(t.getScores().stream().filter(s -> s.getPlayer().getId().equals(playerId)).findAny().isPresent()) {
                tournament.setRegistered(true);
            }
            tournaments.add(tournament);
        });

        tournaments.sort((d1,d2)->d1.getDate().compareTo(d2.getDate()));
        return tournaments;
    }

    public TournamentGallery getTournamentGallery(Long tournamentId){
        List<TournamentPhotoEntity> tournamentPhotoEntities = tournamentPhotoRepository.findAllByTournamentId(tournamentId);
        TournamentGallery tournamentGallery  = new TournamentGallery();
        tournamentPhotoEntities.stream().forEach(photo->{
            tournamentGallery.getPhotoUrls().add(photo.getPhotoUrl());
        });
        tournamentGallery.setTournamentId(tournamentId);
        return tournamentGallery;
    }

    public Tournament getTournament(Long tournamentId){
        TournamentEntity tournamentEntity = tournamentRepository.getOne(tournamentId);
        if(tournamentEntity !=null ){
            Tournament tournament = Mapper.map(tournamentEntity,Tournament.class);
            if(tournament.getDate()!=null && checkTournamentFinished(tournament.getDate())){
                tournament.setFinished(true);
            }
            List<PokerPlayer> participants = new ArrayList<>();

            tournamentEntity.getScores().stream()
                    .forEach(score -> {
                        PokerPlayer pokerPlayer = Mapper.map(score.getPlayer(),PokerPlayer.class);
                        pokerPlayer.setPlace(score.getPlace());
                        pokerPlayer.setPoints(score.getPoints());
                        pokerPlayer.setPrize(score.getPrize());
                        participants.add(pokerPlayer);
                    });
            tournament.setParticipants(participants);
            return tournament;
        }else {
            return null;
        }
    }

    private boolean checkTournamentFinished(Date tournamentDate){
        LocalDateTime actualDateLDT = LocalDateTime.from(new Date().toInstant().atZone(ZoneId.of("UTC")));
        LocalDateTime tournamentDateLDT = LocalDateTime.from(tournamentDate.toInstant().atZone(ZoneId.of("UTC"))).plusDays(1);

        return tournamentDateLDT.isBefore(actualDateLDT);
    }
}
