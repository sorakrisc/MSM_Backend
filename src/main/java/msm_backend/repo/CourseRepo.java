package msm_backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import msm_backend.domain.Course;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course, Integer> {
    Course findOneBySkyid(String skyid);
    List<Course> findByTermid(String term);
}
