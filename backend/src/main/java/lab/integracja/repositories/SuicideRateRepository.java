package lab.integracja.repositories;

import lab.integracja.entities.Subject;
import lab.integracja.entities.SuicideRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuicideRateRepository extends JpaRepository<SuicideRate, Long> {
    List<SuicideRate> findAllByTimeAndSubjectOrderByTime(int time, Subject subject);
    List<SuicideRate> findAllByCountry_CodeAndSubjectOrderByTime(String countryCode, Subject subject);

    @Query("select avg(value) " +
            "from SuicideRate " +
            "where country.id = :countryId " +
                "and subject = lab.integracja.entities.Subject.TOT")
    float avgSuicideRateForCountry(@Param("countryId") Long countryId);
}
