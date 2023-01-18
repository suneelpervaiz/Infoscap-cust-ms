package tiers.app.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tiers.app.customer.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    Device getDeviceByDeviceName(String deviceName);

    Device findDeviceByPhysicalUniqueId(String physicalUniqueId);

}
