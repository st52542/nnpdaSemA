package upce.nnpda.sema.Service;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import upce.nnpda.sema.DTO.AddSensorDTO;
import upce.nnpda.sema.DTO.DeviceDTO;
import upce.nnpda.sema.Entity.Sensor;

import java.util.List;

public interface SensorService {
    List<Sensor> getSensors(Authentication authentication, DeviceDTO deviceDTO);
    Sensor addSensor(Authentication authentication, AddSensorDTO addSensorDTO);
}
