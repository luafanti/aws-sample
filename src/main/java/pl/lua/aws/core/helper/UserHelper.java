package pl.lua.aws.core.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.lua.aws.core.repository.UserRepository;

import java.util.Collection;

@Component
public class UserHelper {

    @Autowired
    UserRepository userRepository;

    public Long getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getName();

        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    authentication.getAuthorities();


        return userRepository.getPlayerIdByUserId(authentication.getName());
    }
}
