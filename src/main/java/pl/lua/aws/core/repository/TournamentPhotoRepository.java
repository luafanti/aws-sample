package pl.lua.aws.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lua.aws.core.model.TournamentPhotoEntity;

import java.util.List;

@Repository
public interface TournamentPhotoRepository  extends JpaRepository<TournamentPhotoEntity,Long> {

    List<TournamentPhotoEntity> findAllByTournamentId(Long tournamentId);
}
