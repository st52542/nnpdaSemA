package upce.nnpda.sema.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = {"/{id}"})
    public Device getProductByID(@PathVariable(required = false) Long id, Model model) {
        return deviceRepository.findById(id).get();
    }
}
