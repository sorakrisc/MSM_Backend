package msm_backend.service;

import msm_backend.domain.User;

public interface UserService {
    public User findUserByEmail(String email);
    public void saveUser(User user);
}
