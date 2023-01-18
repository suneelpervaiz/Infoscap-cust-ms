package tiers.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiers.app.user.repository.DeviceRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    DeviceRepository deviceRepository;

}
