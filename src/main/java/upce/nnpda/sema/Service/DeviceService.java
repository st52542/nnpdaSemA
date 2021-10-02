package upce.nnpda.sema.Service;

import org.springframework.security.core.Authentication;
import upce.nnpda.sema.DTO.AddDeviceDTO;
import upce.nnpda.sema.Entity.Device;
import upce.nnpda.sema.Entity.User;

import java.util.List;
import java.util.Optional;

public interface DeviceService {
    List<Optional<Device>> getDevices(Authentication authentication);
    Device addDevice(Authentication authentication,AddDeviceDTO device);
}
