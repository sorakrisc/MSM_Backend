package msm_backend.repo;

import msm_backend.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("roleRepo")
public interface RoleRepo extends JpaRepository<Role, Integer>{
    Role findByRole(String role);

}