package pl.lua.aws.core.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.lua.aws.core.model.RoleEntity;
import pl.lua.aws.core.model.UserEntity;
import pl.lua.aws.core.repository.UserRepository;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserHelper {

    @Autowired
    UserRepository userRepository;

    public Long getPlayerId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getName();
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    authentication.getAuthorities();
        return userRepository.getPlayerIdByUserId(authentication.getName());
    }

    public boolean isAdminUser(){
        if(getUserRoles().contains("Admin")){
            return true;
        }else{
            return false;
        }
    }

    private List<String> getUserRoles(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getName();
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    authentication.getAuthorities();
        UserEntity userEntity = userRepository.getOne(authentication.getName());
        List<String> roles = userEntity.getRoles().stream().map(RoleEntity::getAuthority).collect(Collectors.toList());
        return roles;
    }
}
