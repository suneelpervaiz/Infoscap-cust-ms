package tiers.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiers.app.user.repository.CustomerRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;
}
