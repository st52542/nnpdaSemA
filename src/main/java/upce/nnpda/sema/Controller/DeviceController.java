package upce.nnpda.sema.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import upce.nnpda.sema.DTO.AddDeviceDTO;
import upce.nnpda.sema.Entity.Device;
import upce.nnpda.sema.Repository.DeviceRepository;
import upce.nnpda.sema.Repository.ListOfDevicesRepository;
import upce.nnpda.sema.Repository.UserRepository;
import upce.nnpda.sema.Service.DeviceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/device")
@CrossOrigin("http://localhost:3000")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Optional<Device>> getDevices(Authentication authentication) {
        try {
            return deviceService.getDevices(authentication);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @GetMapping(value = {"","/"})
    public Device addDevice(Authentication authentication,@RequestBody AddDeviceDTO device) {
        try {
            return deviceService.addDevice(authentication,device);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
