package lab.integracja.repositories;

import lab.integracja.entities.AlcoholConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlcoholConsumptionRepository extends JpaRepository<AlcoholConsumption, Long> {
    @Query("select avg(value) " +
            "from AlcoholConsumption " +
            "where country.id = :countryId " +
            "and subject = lab.integracja.entities.Subject.TOT")
    float avgAlcoholConsumptionForCountry(@Param("countryId") Long countryId);
}
