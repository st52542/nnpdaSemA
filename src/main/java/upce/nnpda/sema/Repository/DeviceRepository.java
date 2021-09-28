package upce.nnpda.sema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upce.nnpda.sema.Entity.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device,Long> {
    Optional<Device> findById(Long id);
    List<Device> findAllById(Long id);
    Device findByDescription(String description);
    List<Device> findAll();
    void removeDeviceById(Long id);
    void removeDeviceByDescription(String description);
}
