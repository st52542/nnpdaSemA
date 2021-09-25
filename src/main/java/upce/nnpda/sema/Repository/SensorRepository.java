package upce.nnpda.sema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upce.nnpda.sema.Entity.Sensor;

public interface SensorRepository extends JpaRepository<Sensor,Long> {
}
