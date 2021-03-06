package pl.lua.aws.core.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "poker_player")
public class PokerPlayerEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue
    private Long id;

    @NotNull
    private String nickName;
    private String avatarUrl;
    private String avatarFileName;

    @OneToMany(mappedBy = "player", cascade=CascadeType.ALL)
    private List<TournamentScoresEntity> scores = new ArrayList<>();
}
