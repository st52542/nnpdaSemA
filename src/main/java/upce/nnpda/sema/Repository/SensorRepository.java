package upce.nnpda.sema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upce.nnpda.sema.Entity.Sensor;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor,Long> {
    List<Sensor> findAllByDevice_Id(Long id);
    Sensor findByDescription(String description);
}
