package pl.lua.aws.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lua.aws.core.model.TournamentEntity;

@Repository
public interface TournamentRepository extends JpaRepository<TournamentEntity,Long> {
}
