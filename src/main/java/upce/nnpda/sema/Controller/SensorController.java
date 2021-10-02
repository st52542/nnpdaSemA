package upce.nnpda.sema.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import upce.nnpda.sema.DTO.AddSensorDTO;
import upce.nnpda.sema.DTO.DeviceDTO;
import upce.nnpda.sema.Entity.Sensor;
import upce.nnpda.sema.Service.SensorService;

import java.util.List;

@RestController
@RequestMapping("/api/sensor")
@CrossOrigin("http://localhost:3000")
public class SensorController {

    @Autowired
    SensorService sensorService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Sensor> getSensors(Authentication authentication, @RequestBody DeviceDTO deviceDTO) {
        try {
            return sensorService.getSensors(authentication, deviceDTO);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Sensor addSensor(Authentication authentication,@RequestBody AddSensorDTO addSensorDTO) {
        try {
            return sensorService.addSensor(authentication, addSensorDTO);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
