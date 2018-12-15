package pl.lua.aws.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lua.aws.core.domain.PokerPlayer;
import pl.lua.aws.core.model.PokerPlayerEntity;
import pl.lua.aws.core.model.TournamentEntity;
import pl.lua.aws.core.model.TournamentScoresEntity;
import pl.lua.aws.core.repository.PokerPlayerRepository;
import pl.lua.aws.core.repository.TournamentRepository;
import pl.lua.aws.core.repository.TournamentScoresRepository;

import java.util.List;

@Service
@Slf4j
public class ScoresService {

    @Autowired
    private PokerPlayerRepository pokerPlayerRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private TournamentScoresRepository scoresRepository;

    public void saveScores(List<PokerPlayer> scores,Long tournamentId){

        TournamentEntity tournamentEntity = tournamentRepository.getOne(tournamentId);
        scores.stream().forEach(score->{
            TournamentScoresEntity scoresEntity;
            PokerPlayerEntity pokerPlayerEntity = pokerPlayerRepository.getOne(score.getId());
             scoresEntity = scoresRepository.findByTournament_IdAndPlayer_Id(tournamentId,score.getId());
            if(scoresEntity==null){
                scoresEntity = new TournamentScoresEntity();
            }
            scoresEntity.setTournament(tournamentEntity);
            scoresEntity.setPlayer(pokerPlayerEntity);
            scoresEntity.setPlace(score.getPlace());
            scoresEntity.setPoints(score.getPoints());
            scoresEntity.setPrize(score.getPrize());
            scoresRepository.save(scoresEntity);
            log.info("Saved score for player {}",score.getNickName());
        });

    }
}
