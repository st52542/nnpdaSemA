package upce.nnpda.sema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upce.nnpda.sema.Entity.ListOfDevices;

import java.util.List;
import java.util.Optional;

public interface ListOfDevicesRepository extends JpaRepository<ListOfDevices,Long> {
    List<ListOfDevices> findAllByUserId(Long id);
}
