package tiers.app.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiers.app.customer.model.Device;
import tiers.app.customer.repository.DeviceRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Optional<Device> getDeviceById(long id) {
        return deviceRepository.findById(id);
    }

    @Override
    public Device getDevice(String deviceName) {
        return deviceRepository.getDeviceByDeviceName(deviceName);
    }

    @Override
    public List<Device> getDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Device getDeviceByPhysicalUniqueId(String physicalUniqueId) {
        return deviceRepository.findDeviceByPhysicalUniqueId(physicalUniqueId);
    }


}
