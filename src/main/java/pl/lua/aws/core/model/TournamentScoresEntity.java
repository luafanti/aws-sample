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
    private String points;
    private String place;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private TournamentEntity tournament;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PokerPlayerEntity player;
}
