package lab.integracja.repositories;

import lab.integracja.entities.AlcoholConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlcoholConsumptionRepository extends JpaRepository<AlcoholConsumption, Long> {

}
