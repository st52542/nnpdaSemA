package upce.nnpda.sema.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import upce.nnpda.sema.DTO.AddDeviceDTO;
import upce.nnpda.sema.Entity.Device;
import upce.nnpda.sema.Entity.ListOfDevices;
import upce.nnpda.sema.Entity.User;
import upce.nnpda.sema.Repository.DeviceRepository;
import upce.nnpda.sema.Repository.ListOfDevicesRepository;
import upce.nnpda.sema.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService{
    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ListOfDevicesRepository listOfDevicesRepository;
    @Override
    public List<Optional<Device>> getDevices(Authentication authentication) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        List<ListOfDevices> listOfDevices = listOfDevicesRepository.findAllByUserId(user.get().getId());
        List<Optional<Device>> deviceList = new ArrayList<>();
        listOfDevices.forEach(deviceRecord->{deviceList.add(deviceRepository.findById(deviceRecord.getDevices().getId()));});
        return deviceList;
    }

    @Override
    public Device addDevice(Authentication authentication, AddDeviceDTO device) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        Device addDevice = new Device();
        addDevice.setDescription(device.getDescription());
        deviceRepository.save(addDevice);
        ListOfDevices newDevice = new ListOfDevices();
        newDevice.setDevices(deviceRepository.findByDescription(device.getDescription()));
        newDevice.setUser(user.get());
        listOfDevicesRepository.save(newDevice);
        return deviceRepository.findByDescription(device.getDescription());
    }
}
