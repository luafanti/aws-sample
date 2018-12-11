package pl.lua.aws.core.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class Tournament implements Serializable{

    private Long id;
    private Date date;
    private String name;
    private String description;
    private boolean registered;
}
