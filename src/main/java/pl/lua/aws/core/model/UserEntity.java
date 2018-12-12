package pl.lua.aws.core.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    private String id;
    @NotNull
    private String name;
    private String email;

    private String authenticationProvider;
    private Date registrationDate;
    private Date lastLoginDate;

    private Long playerId;
}
