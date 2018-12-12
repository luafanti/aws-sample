package pl.lua.aws.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.lua.aws.core.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {

    @Query("select user.playerId from UserEntity user " +
            "WHERE user.id = :userId")
    Long getPlayerIdByUserId(@Param("userId")String userId);
}
