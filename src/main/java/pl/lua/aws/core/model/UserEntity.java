package pl.lua.aws.core.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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

    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name="user_roles",
            joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
    )
    private List<RoleEntity> roles;
}
