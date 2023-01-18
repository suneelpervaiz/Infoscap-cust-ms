package tiers.app.customer.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiers.app.customer.config.exceptions.ApiExceptionHandler;
import tiers.app.customer.config.exceptions.RecordAlreadyExisitsException;
import tiers.app.customer.config.sms.cache.CacheService;
import tiers.app.customer.model.*;
import tiers.app.customer.model.dto.CreateCustomerRequest;
import tiers.app.customer.model.dto.EmailVerificationRequest;
import tiers.app.customer.model.dto.PhoneVerificationRequest;
import tiers.app.customer.service.CustomerService;
import tiers.app.customer.service.UserService;

import javax.mail.MessagingException;
import javax.validation.Valid;
@CrossOrigin
@RestController
@RequestMapping("/api/customers/v1")
public class CustomerController {

    private static final String DUPLICATE_EMAIL = "Alert ! Email has already been used in another account. please use the sign in";
    private static final String DUPLICATE_PHONE_NUMBER = "Alert ! Phone number has already been used in another account. please use the sign in";
    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    private CacheService cacheService;

    @PostMapping("/auth/applicant")
    @ResponseStatus(HttpStatus.CREATED)
        public ResponseEntity<Object> createCustomer( @Valid @RequestBody CreateCustomerRequest createCustomer) throws MessagingException {

        User existingUser = userService.getUser(createCustomer.getEmail());
        Customer customer = customerService.getCustomerByPhoneNumber(createCustomer.getPhoneNumber());
        if (customer != null)
            return new ApiExceptionHandler().HandleRecordAlreadyExistsException(new RecordAlreadyExisitsException(DUPLICATE_PHONE_NUMBER));
        if (existingUser != null)
            return new ApiExceptionHandler().HandleRecordAlreadyExistsException(new RecordAlreadyExisitsException(DUPLICATE_EMAIL));
            return ResponseEntity.ok().body(customerService.createCustomer(createCustomer));
        }

    @PostMapping("/applicant/sms_validation")
        public ResponseEntity<Object> validateSMS(@Valid @RequestBody PhoneVerificationRequest phoneVerificationRequest){
        String validation = cacheService.verifySMS(phoneVerificationRequest.getPhoneNumber(),
                phoneVerificationRequest.getVerificationCode());
        return ResponseEntity.ok().body(validation);
        }

    @PostMapping("/applicant/email_validation")
        public ResponseEntity<Object> validateEmail(@Valid @RequestBody EmailVerificationRequest emailVerificationRequest){
        String validation = cacheService.verifyEmail(emailVerificationRequest.getEmail(),
                emailVerificationRequest.getVerificationCode());
         return ResponseEntity.ok().body(validation);
        }

    @PostMapping("/applicant/phone_sms")
    @ResponseStatus(HttpStatus.CREATED)
        private String getPhoneValidationCode(@Valid @RequestParam String phoneNumber){
        return customerService.generatePhoneNumberVerificationCode(phoneNumber);
        }

    @PostMapping("/applicant/email_sms")
    @ResponseStatus(HttpStatus.CREATED)
        private String getEmailValidationCode(@Valid @RequestParam String email){
        return customerService.generateEmailVerificationCode(email);
        }

}
