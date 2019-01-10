package pl.lua.aws.core.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tournament_scores")
public class TournamentScoresEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue
    private Long id;
    private Integer points;
    private Integer rebuy;
    private String place;
    @Column(columnDefinition = "DECIMAL(19,2)")
    private Double prize;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private TournamentEntity tournament;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PokerPlayerEntity player;
}

