package upce.nnpda.sema.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
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

@RestController
@RequestMapping("/api/sensor")
@CrossOrigin("http://localhost:3000")
public class SensorController {
    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    ListOfDevicesRepository listOfDevicesRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Sensor> getSensors(Authentication authentication, @RequestBody DeviceDTO deviceDTO) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        List<Sensor> sensorList = new ArrayList<>();
        if (user != null){
            sensorList = sensorRepository.findAllByDevice_Id(deviceDTO.getId());
        }
        return sensorList;
    }
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Sensor addSensor(Authentication authentication,@RequestBody AddSensorDTO addSensorDTO) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        if (user != null){
            Sensor sensor=new Sensor();
            sensor.setDescription(addSensorDTO.getDescription());
            sensor.setDevice(deviceRepository.findById(addSensorDTO.getId()).get());
            sensorRepository.save(sensor);
        }
        return sensorRepository.findByDescription(addSensorDTO.getDescription());
    }
}
