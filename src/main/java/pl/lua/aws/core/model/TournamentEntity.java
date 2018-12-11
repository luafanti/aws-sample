package pl.lua.aws.core.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tournament")
@ToString(exclude = "scores")
public class TournamentEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue
    private Long id;

    @NotNull
    private Date date;

    @NotNull
    private String name;

    private String description;

    @OneToMany(mappedBy = "player", cascade=CascadeType.ALL)
    private List<TournamentScoresEntity> scores = new ArrayList<>();

}
