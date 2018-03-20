package msm_backend.repo;

import msm_backend.domain.Plan;
import msm_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import msm_backend.domain.Course;

import java.util.List;

public interface PlanRepo extends JpaRepository<Plan, Integer> {
    List<Plan> findByUser(User user);
}
