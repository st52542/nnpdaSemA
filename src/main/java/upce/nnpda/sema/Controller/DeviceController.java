package upce.nnpda.sema.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upce.nnpda.sema.Entity.Device;
import upce.nnpda.sema.Repository.DeviceRepository;

import java.util.List;

@RestController
@RequestMapping("/device")
@CrossOrigin("http://localhost:3000")
public class DeviceController {
    @Autowired
    DeviceRepository deviceRepository;

    @GetMapping(value = {"","/"})
    public List<Device> getProducts() {
        return deviceRepository.findAll();
    }
}
