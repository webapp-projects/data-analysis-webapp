package lab.integracja.repositories;

import lab.integracja.entities.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeasureRepository extends JpaRepository<Measure, Long> {
    Optional<Measure> findByName(String name);
}
