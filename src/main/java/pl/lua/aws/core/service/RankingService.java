package pl.lua.aws.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lua.aws.core.domain.PlayerScore;
import pl.lua.aws.core.model.PokerPlayerEntity;
import pl.lua.aws.core.model.TournamentScoresEntity;
import pl.lua.aws.core.repository.PokerPlayerRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RankingService {

    @Autowired
    private PokerPlayerRepository pokerPlayerRepository;

    public List<PlayerScore> getRankingOfAllPlayers(){

        List<PokerPlayerEntity> pokerPlayerEntities = pokerPlayerRepository.findAll();
        List<PlayerScore> playerScores = new ArrayList<>();

        pokerPlayerEntities.stream().forEach(player->{
            PlayerScore playerScore = new PlayerScore();
            playerScore.setNickName(player.getNickName());
            playerScore.setPlayerId(player.getId());

            List<TournamentScoresEntity> scoresEntities = player.getScores();
            Integer totalPoints = new Integer(0);
            Integer wins = new Integer(0);
            Double totalPrize = new Double(0L);

            for(TournamentScoresEntity s : scoresEntities){
                if(s.getPoints()!=null){
                    totalPoints += s.getPoints();
                }
                if(s.getPrize()!=null){
                    totalPrize+= s.getPrize();
                }
                if(s.getPlace()!=null && s.getPlace().equals("1")){
                    wins+=1;
                }
            }
            playerScore.setTotalPrize(totalPrize);
            playerScore.setTotalPoints(totalPoints);
            playerScore.setWins(wins);
            playerScores.add(playerScore);
        });

        playerScores.sort(Comparator.comparing(PlayerScore::getTotalPoints).reversed());

        return playerScores;
    }
}
