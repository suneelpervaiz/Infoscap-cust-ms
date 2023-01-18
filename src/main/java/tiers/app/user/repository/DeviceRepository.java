package tiers.app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tiers.app.user.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
