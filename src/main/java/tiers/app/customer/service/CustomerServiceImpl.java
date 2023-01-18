package tiers.app.customer.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tiers.app.customer.config.exceptions.ApiExceptionHandler;
import tiers.app.customer.config.exceptions.RecordAlreadyExisitsException;
import tiers.app.customer.config.security.helper.EmailService;
import tiers.app.customer.config.security.helper.EmailTemplate;
import tiers.app.customer.config.sms.SmsService;
import tiers.app.customer.config.sms.cache.CacheService;
import tiers.app.customer.config.sms.otp.OtpGenerator;
import tiers.app.customer.model.*;
import tiers.app.customer.model.dto.CreateCustomerRequest;
import tiers.app.customer.repository.*;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService{

    private static final String DUPLICATE_EMAIL = "Alert ! Email has already been used in another account. please use the sign in";
    private static final String DUPLICATE_PHONE_NUMBER = "Alert ! Phone number has already been used in another account. please use the sign in";
    private static final String EMAIL_SUBJECT = "TiersApp email confirmation";
    private static final String PHONE_SMS = "TiersApp Phone Confirmation Code";


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpGenerator otpGenerator;

    @Autowired
    private CacheService cacheService;


    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }


    public Customer createCustomer(CreateCustomerRequest createCustomerRequest) {

        Customer customer = new Customer();
        Optional<CreateCustomerRequest> createCustomerRequestOptional = Optional.ofNullable(createCustomerRequest);
        createCustomerRequestOptional.ifPresent(request ->{
            customer.setApproved(request.isApproved());
            customer.setApprovedAt(request.getApprovedAt());
            customer.setPhoneNumber(request.getPhoneNumber());
            Optional<Language> language = Optional.ofNullable(languageService.getLanguageByName(request.getLanguage().getLanguageName()));
            if (language.isPresent())
                customer.setLanguage(language.get());
            else {
                customer.setLanguage(languageService.saveLanguage(request.getLanguage()));
            }
            Optional<Country> country = Optional.ofNullable(countryService.findCountryByName(request.getCountry().getCountryName()));
            if (country.isPresent())
                customer.setCountry(country.get());
            else {
                customer.setCountry(countryService.saveCountry(request.getCountry()));
            }
            User user = new User();
            user.setUserName(request.getEmail());
            user.setPassword(request.getPinCode());
            Optional<Device> device = Optional.ofNullable(deviceService.getDevice(request.getDevice().getPhysicalUniqueId()));
            if (device.isPresent())
                user.getUserDevices().add(device.get());
            else {
                user.getUserDevices().add(deviceService.saveDevice(request.getDevice()));
            }
            Optional<Role> role = Optional.ofNullable(userService.findRoleByName(request.getRoleName()));
            if (role.isPresent()){
                user.getRoles().add(role.get());
            }
            customer.setUser(userService.saveUser(user));
            sendSms(request.getPhoneNumber());
            sendMail(request.getEmail());
        });
           return customerRepository.save(customer);
    }

    private void sendSms(String to){
        String confirmationSMS = otpGenerator.generateToken();
        cacheService.putToken(to, confirmationSMS);
        smsService.sendSMS(PHONE_SMS+" "+confirmationSMS, to);
    }

    private void sendMail(String to){
        try {
            String confirmationSMS = otpGenerator.generateToken();
            cacheService.putToken(to, confirmationSMS);
            emailService.sendOtpMessage(to, EMAIL_SUBJECT, confirmationSMS);
         } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generatePhoneNumberVerificationCode(String phoneNumber) {
        String confirmationSMS = otpGenerator.generateToken();
        cacheService.putToken(phoneNumber, confirmationSMS);
        smsService.sendSMS(PHONE_SMS+" "+confirmationSMS, phoneNumber);
        return "Code sent successfully at "+phoneNumber;
    }

    @Override
    public String generateEmailVerificationCode(String email) {
        String confirmationSMS = otpGenerator.generateToken();
        cacheService.putToken(email, confirmationSMS);
        try {
            emailService.sendOtpMessage(email, EMAIL_SUBJECT, confirmationSMS);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "Code sent successfully at "+email;
    }

}
