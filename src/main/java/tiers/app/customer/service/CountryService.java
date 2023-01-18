package tiers.app.customer.service;

import tiers.app.customer.model.Country;


import java.util.List;
import java.util.Optional;

public interface CountryService {
    Country saveCountry(Country country);
    Optional<Country> getCountryById(Long id);
    Country getCountry(String countryName);
    List<Country> getCountries();

    Country findCountryByName(String countryName);
}
