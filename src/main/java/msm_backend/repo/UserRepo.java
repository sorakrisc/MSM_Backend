package msm_backend.repo;

import msm_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepo")
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findOneByName(String username);

}
