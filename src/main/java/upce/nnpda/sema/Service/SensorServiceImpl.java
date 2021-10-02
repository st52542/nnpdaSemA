package upce.nnpda.sema.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import upce.nnpda.sema.DTO.AddSensorDTO;
import upce.nnpda.sema.DTO.DeviceDTO;
import upce.nnpda.sema.Entity.Sensor;
import upce.nnpda.sema.Entity.User;
import upce.nnpda.sema.Repository.DeviceRepository;
import upce.nnpda.sema.Repository.ListOfDevicesRepository;
import upce.nnpda.sema.Repository.SensorRepository;
import upce.nnpda.sema.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SensorServiceImpl implements SensorService{
    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SensorService sensorService;

    @Autowired
    SensorRepository sensorRepository;

    @Autowired
    ListOfDevicesRepository listOfDevicesRepository;

    @Override
    public List<Sensor> getSensors(Authentication authentication, DeviceDTO deviceDTO) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        List<Sensor> sensorList = new ArrayList<>();
        if (user != null){
            sensorList = sensorRepository.findAllByDevice_Id(deviceDTO.getId());
        }
        return sensorList;
    }

    @Override
    public Sensor addSensor(Authentication authentication, AddSensorDTO addSensorDTO) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        if (user != null){
            Sensor sensor=new Sensor();
            sensor.setType(addSensorDTO.getType());
            sensor.setDescription(addSensorDTO.getDescription());
            sensor.setDevice(deviceRepository.findById(addSensorDTO.getId()).get());
            sensorRepository.save(sensor);
        }
        return sensorRepository.findByDescription(addSensorDTO.getDescription());
    }
}
