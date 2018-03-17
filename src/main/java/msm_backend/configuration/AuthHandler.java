package msm_backend.configuration;

import msm_backend.domain.User;
import msm_backend.repo.UserRepo;
import msm_backend.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AuthHandler implements AuthenticationProvider {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserRepo userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Check with Database

        System.out.println("username: "+username);
        System.out.println("password: "+password);

        if (!username.equalsIgnoreCase("admin") || !password.equalsIgnoreCase("password")){
            return null;
        }

        if (userService.authenticate(username, password)){
            return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        }
        if (userService.authenticate(username, password)){

            User user = userRepository.findByName(username);
            return new UsernamePasswordAuthenticationToken(user.getRoles(), true, new ArrayList<>());

        }


        return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
