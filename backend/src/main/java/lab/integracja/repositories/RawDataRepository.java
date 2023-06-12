package lab.integracja.repositories;

import lab.integracja.entities.RawData;
import org.hibernate.query.spi.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RawDataRepository extends JpaRepository<RawData, Long> {
}
