package msm_backend.repo;

import msm_backend.domain.Config;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepo extends JpaRepository<Config, Integer> {
    Config findOneByConfigid(int id);
    Config findOneByKey(String key);
}
