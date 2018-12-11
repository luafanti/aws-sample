package pl.lua.aws.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lua.aws.core.model.TournamentScoresEntity;

@Repository
public interface TournamentScoresRepository extends JpaRepository<TournamentScoresEntity,Long> {

    TournamentScoresEntity findByTournament_IdAndPlayer_Id(Long tournamentId, Long playerId);
}
