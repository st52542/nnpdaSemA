package upce.nnpda.sema.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import upce.nnpda.sema.Entity.Device;
import upce.nnpda.sema.Entity.ListOfDevices;
import upce.nnpda.sema.Entity.User;
import upce.nnpda.sema.Repository.DeviceRepository;
import upce.nnpda.sema.Repository.ListOfDevicesRepository;
import upce.nnpda.sema.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/device")
@CrossOrigin("http://localhost:3000")
public class DeviceController {
    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ListOfDevicesRepository listOfDevicesRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @GetMapping(value = {"","/"})
    public List<Optional<Device>> getProducts(Authentication authentication) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        List<ListOfDevices> listOfDevices = listOfDevicesRepository.findAllByUserId(user.get().getId());
        List<Optional<Device>> deviceList = new ArrayList<>();
        listOfDevices.forEach(deviceRecord->{deviceList.add(deviceRepository.findById(deviceRecord.getId()));});
        return deviceList;
    }
}
