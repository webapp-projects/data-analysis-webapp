package lab.integracja.repositories;

import lab.integracja.entities.Subject;
import lab.integracja.entities.SuicideRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuicideRateRepository extends JpaRepository<SuicideRate, Long> {
    List<SuicideRate> findAllByTimeAndSubjectOrderByTime(int time, Subject subject);
}
