package pl.lua.aws.core.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import pl.lua.aws.core.model.PokerPlayerEntity;
import pl.lua.aws.core.model.UserEntity;
import pl.lua.aws.core.repository.PokerPlayerRepository;
import pl.lua.aws.core.repository.UserRepository;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;


@Slf4j
@Data
public class UserFilter implements Filter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PokerPlayerRepository pokerPlayerRepository;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null && authentication.getPrincipal()!=null && request.getRequestURI().equals("/loginSuccess") ){
            if(authentication.getPrincipal() instanceof DefaultOidcUser){
                if(userRepository.existsById(authentication.getName())){
                    log.info("User with id {} already registered in application. Log in success",authentication.getName());
                }else{
                    createUser(authentication);
                    log.info("Create new user with id {}  in application. Log in success",authentication.getName());
                }
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    private void createUser(Authentication authentication) {
        DefaultOidcUser oidcUser = (DefaultOidcUser)authentication.getPrincipal();
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(authentication.getName());
        userEntity.setEmail(oidcUser.getEmail());
        userEntity.setName(oidcUser.getFullName());
        userEntity.setRegistrationDate(new Date());
        userEntity.setAuthenticationProvider(auth2AuthenticationToken.getAuthorizedClientRegistrationId());

        PokerPlayerEntity pokerPlayerEntity = new PokerPlayerEntity();
        pokerPlayerEntity.setNickName(oidcUser.getFullName());
        pokerPlayerEntity = pokerPlayerRepository.save(pokerPlayerEntity);

        userEntity.setPlayerId(pokerPlayerEntity.getId());
        userRepository.save(userEntity);
    }
}
