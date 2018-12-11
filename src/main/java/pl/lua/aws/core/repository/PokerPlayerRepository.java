package pl.lua.aws.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lua.aws.core.model.PokerPlayerEntity;

@Repository
public interface PokerPlayerRepository extends JpaRepository<PokerPlayerEntity,Long> {
}
