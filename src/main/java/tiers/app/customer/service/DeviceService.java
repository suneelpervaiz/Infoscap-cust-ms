package tiers.app.customer.service;

import tiers.app.customer.model.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceService {
    Device saveDevice(Device device);
    Optional<Device> getDeviceById(long id);
    Device getDevice(String deviceName);
    List<Device> getDevices();
    Device getDeviceByPhysicalUniqueId(String physicalUniqueId);
}
