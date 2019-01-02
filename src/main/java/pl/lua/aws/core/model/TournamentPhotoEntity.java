package pl.lua.aws.core.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "tournament_photo")
public class TournamentPhotoEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue
    private Long Id;
    @NotNull
    private Long tournamentId;
    @NotNull
    private String photoUrl;
}
