package tiers.app.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tiers.app.customer.config.exceptions.ApiException;
import tiers.app.customer.config.exceptions.ApiExceptionHandler;
import tiers.app.customer.model.*;
import tiers.app.customer.model.Enum.DeviceType;
import tiers.app.customer.model.dto.CreateCustomerRequest;
import tiers.app.customer.repository.*;
import tiers.app.customer.service.*;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
class TiersAppMaverickApplicationTests {


	@Test
	void contextLoads() {
	}

	@Autowired
	UserService userService;

	@MockBean
	UserRepository userRepository;

	@Autowired
	LanguageService languageService;

	@MockBean
	LanguageRepository languageRepository;

	@MockBean
	DeviceRepository deviceRepository;
	@Autowired
	DeviceService deviceService;

	@MockBean
	CountryRepository countryRepository;
	@Autowired
	CountryService countryService;


	@Test
	public void getUsersTest(){
       when(userRepository.findAll()).thenReturn( Stream.of(new User(null, "suneelpervaiz@gmail.com","123456",Collections.emptySet(), Collections.emptySet(), Collections.emptySet()),
			   new User(null, "teirstest@gmail.com", "345678", Collections.emptySet(), Collections.emptySet(), Collections.emptySet())).collect(Collectors.toList()));
	   assertEquals(2,userService.getUsers().size());
	}

	@Test
	public void saveUserTest(){
		User user = new User(null, "teirstest@gmail.com", "345678", Collections.emptySet(), Collections.emptySet(), Collections.emptySet());
        when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, userService.saveUser(user));
	}
	@Test
	void saveLanguageTest() {
		Language language = new
				Language(1, "de-LU", "English", Collections.emptySet(), Collections.emptySet());
		when(languageRepository.save(language)).thenReturn(language);
		assertEquals(language, languageService.saveLanguage(language));
		assertEquals("de-LU", language.getIsoLocaleLanguageCode());
		assertEquals("English",language.getLanguageName());
		assertEquals(1, language.getId());
	}

	@Test
	void getLanguageById() {
	}

	@Test
	void getLanguageTest() {
		String languageName = "English";
		Language language = new
				Language(1, "de-LU", "English", Collections.emptySet(), Collections.emptySet());
		when(languageRepository.getLanguageByLanguageName(languageName))
				.thenReturn(language);
		assertEquals(language, languageService.getLanguageByName(languageName));
	}

	@Test
	public void saveDeviceTest(){
		Device device = new Device(1, "12345",DeviceType.MOBILE, true, "Yes", "SAMSUNG", Collections.emptySet());
	    when(deviceRepository.save(device)).thenReturn(device);
		assertEquals(device, deviceService.saveDevice(device));
		assertEquals(1, device.getId());
		assertEquals("12345", device.getPhysicalUniqueId());
		assertEquals(DeviceType.MOBILE, device.getDeviceType());
		assertEquals("SAMSUNG", device.getDeviceName());
	}

	@Test
	public void getDeviceTest(){
		String physicalUniqueId = "12345";
		Device device = new Device(1, "12345",DeviceType.MOBILE, true, "Yes", "SAMSUNG", Collections.emptySet());
		when(deviceRepository.findDeviceByPhysicalUniqueId(physicalUniqueId)).thenReturn((device));
		assertEquals(device, deviceService.getDeviceByPhysicalUniqueId(physicalUniqueId));
	}

	@Test
	public void saveCountryTest(){
		Country country = new Country(1L, "DE","DEU","Germany", Collections.emptySet(),Collections.emptySet());
		when(countryRepository.save(country)).thenReturn(country);
		assertEquals(country, countryService.saveCountry(country));
		assertEquals(1L, country.getId());
		assertEquals("DE", country.getIsoAlpha2CountryCode());
		assertEquals("DEU", country.getIsoAlpha3CountryCode());
		assertEquals("Germany", country.getCountryName());
	}

	@Test
	public void getCountryTest(){
		String countryName = "Germany";
		Country country = new Country(1L, "DE","DEU","Germany", Collections.emptySet(),Collections.emptySet());
		when(countryRepository.findByCountryName(countryName)).thenReturn(country);
		assertEquals(country, countryService.getCountry(countryName));
	}

}
