package msm_backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import msm_backend.domain.Course;

public interface CourseRepo extends JpaRepository<Course, Integer> {
    Course findOneBySkyid(String skyid);
}
