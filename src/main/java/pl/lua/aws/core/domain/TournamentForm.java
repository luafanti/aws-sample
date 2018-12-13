package pl.lua.aws.core.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
public class TournamentForm implements Serializable {

    @NotNull
    private Date date;
    @NotNull
    @Size(min=3, max=30)
    private String name;
    private String description;
}
