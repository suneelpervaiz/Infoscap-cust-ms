package tiers.app.customer.service;

import tiers.app.customer.model.Customer;
import tiers.app.customer.model.dto.CreateCustomerRequest;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    Optional<Customer> getCustomerById(Long id);
    List<Customer> getCustomers();

    Customer getCustomerByPhoneNumber(String phoneNumber);

    Customer createCustomer(CreateCustomerRequest customer);
    String generatePhoneNumberVerificationCode(String phoneNumber);
    String generateEmailVerificationCode(String email);
}
