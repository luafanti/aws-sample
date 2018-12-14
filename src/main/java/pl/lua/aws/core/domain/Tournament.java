package pl.lua.aws.core.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Tournament implements Serializable{

    private Long id;
    private Date date;
    private String name;
    private String description;
    private boolean registered;
    private boolean finished;
    private List<PokerPlayer> participants;
}
