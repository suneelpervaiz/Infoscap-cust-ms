package tiers.app.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tiers.app.customer.model.Country;
import tiers.app.customer.model.Device;
import tiers.app.customer.model.Enum.DeviceType;
import tiers.app.customer.model.Language;
import tiers.app.customer.model.dto.CreateCustomerRequest;
import tiers.app.customer.repository.CustomerRepository;
import tiers.app.customer.service.CustomerService;


import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerControllerTest {

    private static final String DUPLICATE_EMAIL = "Alert ! Email has already been used in another account. please use the sign in";
    private static final String DUPLICATE_PHONE_NUMBER = "Alert ! Phone number has already been used in another account. please use the sign in";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createCustomerTest() throws Exception {
        CreateCustomerRequest customer = CreateCustomerRequest.builder()
                .email("suneelpervaiz@gmail.com")
                .approvedAt(null)
                .approvedBy(null)
                .device(new Device(1, "123456", DeviceType.MOBILE, true, "onfido","Nokia",null))
                .country(new Country(null, "DE", "DEU", "Germany", null, null))
                .language(new Language(1, "de-LU", "English", Collections.emptySet(), Collections.emptySet()))
                .isApproved(false)
                .pinCode("12345")
                .roleName("USER")
                .phoneNumber("03214027791")
                .build();
        ResultActions response = mockMvc.perform(post("/api/customers/v1/auth/applicant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect( jsonPath("$.phoneNumber",
                        is(customer.getPhoneNumber())))

                .andExpect( jsonPath("$.country.isoAlpha2CountryCode",
                        is(customer.getCountry().getIsoAlpha2CountryCode())))
                .andExpect( jsonPath("$.country.isoAlpha3CountryCode",
                        is(customer.getCountry().getIsoAlpha3CountryCode())))
                .andExpect( jsonPath("$.country.countryLanguages",
                        is(customer.getCountry().getCountryLanguages())))
                .andExpect( jsonPath("$.country.countryName",
                        is(customer.getCountry().getCountryName())))

                .andExpect( jsonPath("$.language.isoLocaleLanguageCode",
                        is(customer.getLanguage().getIsoLocaleLanguageCode())))
                .andExpect( jsonPath("$.language.languageName",
                        is(customer.getLanguage().getLanguageName())));
    }
    @Test  /* Phone number is required */
    void createCustomerPhoneNumberValidationsTest() throws Exception {
        CreateCustomerRequest customer = CreateCustomerRequest.builder()
                .email("suneelpervaiz@gmail.com")
                .approvedAt(null)
                .approvedBy(null)
                .device(new
                        Device(1, "123456", DeviceType.MOBILE, true, "onfido","Nokia",null))
                .country(new
                        Country(null, "DE", "DEU", "Germany", null, null))
                .language(new
                        Language(1, "de-LU", "English", Collections.emptySet(), Collections.emptySet()))
                .isApproved(false)
                .pinCode("12345")
                .roleName("USER")
                .phoneNumber(null)
                .build();
        ResultActions response = mockMvc.perform(post("/api/customers/v1/auth/applicant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));
        response.andDo(print()).
                andExpect(status().isBadRequest())
                .andExpect( jsonPath("$.phoneNumber.message",
                        is("Phone number is required")))

        ;
    }

    @Test /* Email is required */
    void createCustomerEmailValidationsTest() throws Exception {
        CreateCustomerRequest customer = CreateCustomerRequest.builder()
                .email("")
                .approvedAt(null)
                .approvedBy(null)
                .device(new Device(1, "123456", DeviceType.MOBILE, true, "onfido","Nokia",null))
                .country(new Country(null, "DE", "DEU", "Germany", null, null))
                .language(new Language(1, "de-LU", "English", Collections.emptySet(), Collections.emptySet()))
                .isApproved(false)
                .pinCode("12345")
                .roleName("USER")
                .phoneNumber("03214027791")
                .build();
        ResultActions response = mockMvc.perform(post("/api/customers/v1/auth/applicant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));
        response.andDo(print()).
                andExpect(status().isBadRequest())
                .andExpect( jsonPath("$.email.message",
                        is("Email is required")))
        ;
    }

    @Test /* Phone Number should be unique */
    void customerDuplicatePhoneNumberValidationsTest() throws Exception {
        CreateCustomerRequest customer = CreateCustomerRequest.builder()
                .email("suneelpervaiz@gmail.com")
                .approvedAt(null)
                .approvedBy(null)
                .device(new Device(1, "123456", DeviceType.MOBILE, true, "onfido","Nokia",null))
                .country(new Country(null, "DE", "DEU", "Germany", null, null))
                .language(new Language(1, "de-LU", "English", Collections.emptySet(), Collections.emptySet()))
                .isApproved(false)
                .pinCode("12345")
                .roleName("USER")
                .phoneNumber("03214027791")
                .build();
        ResultActions response = mockMvc.perform(post("/api/customers/v1/auth/applicant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));

        response = mockMvc.perform(post("/api/customers/v1/auth/applicant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));
        response.andDo(print()).
                andExpect(status().isConflict())

                .andExpect( jsonPath("$.message",
                        is(DUPLICATE_PHONE_NUMBER)))
        ;
    }

    @Test /* Email/Username should be unique */
    void customerDuplicateEmailValidationsTest() throws Exception {
        CreateCustomerRequest customer = CreateCustomerRequest.builder()
                .email("suneelpervaiz@gmail.com")
                .approvedAt(null)
                .approvedBy(null)
                .device(new Device(1, "123456", DeviceType.MOBILE, true, "onfido","Nokia",null))
                .country(new Country(null, "DE", "DEU", "Germany", null, null))
                .language(new Language(1, "de-LU", "English", Collections.emptySet(), Collections.emptySet()))
                .isApproved(false)
                .pinCode("12345")
                .roleName("USER")
                .phoneNumber("032140277911")
                .build();
        ResultActions response = mockMvc.perform(post("/api/customers/v1/auth/applicant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));

        response = mockMvc.perform(post("/api/customers/v1/auth/applicant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CreateCustomerRequest.builder()
                        .email("suneelpervaiz@gmail.com")
                        .approvedAt(null)
                        .approvedBy(null)
                        .device(new Device(1, "123456", DeviceType.MOBILE, true, "onfido","Nokia",null))
                        .country(new Country(null, "DE", "DEU", "Germany", null, null))
                        .language(new Language(1, "de-LU", "English", Collections.emptySet(), Collections.emptySet()))
                        .isApproved(false)
                        .pinCode("12345")
                        .roleName("USER")
                        .phoneNumber("03214027791")
                        .build())));
        response.andDo(print()).
                andExpect(status().isConflict())

                .andExpect( jsonPath("$.message",
                        is(DUPLICATE_EMAIL)))
        ;
    }

}