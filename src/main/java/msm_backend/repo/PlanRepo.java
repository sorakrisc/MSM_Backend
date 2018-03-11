package msm_backend.repo;

import msm_backend.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import msm_backend.domain.Course;

public interface PlanRepo extends JpaRepository<Plan, Integer> {

}
