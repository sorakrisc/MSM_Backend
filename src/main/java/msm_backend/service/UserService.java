package msm_backend.service;

import java.util.Arrays;
import java.util.HashSet;

import msm_backend.domain.Role;
import msm_backend.domain.User;
import msm_backend.repo.RoleRepo;
import msm_backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService{

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private RoleRepo roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
    public boolean authenticate(String username, String password){
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()){
            return false;
        }
        User user = userRepository.findByName(username);
        return user!=null && bCryptPasswordEncoder.matches(password, user.getPassword());
    }

}