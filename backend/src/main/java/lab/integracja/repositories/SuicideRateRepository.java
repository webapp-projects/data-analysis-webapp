package lab.integracja.repositories;

import lab.integracja.entities.SuicideRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuicideRateRepository extends JpaRepository<SuicideRate, Long> {

}
