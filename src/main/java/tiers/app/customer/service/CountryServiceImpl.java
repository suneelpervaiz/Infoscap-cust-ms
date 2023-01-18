package tiers.app.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiers.app.customer.model.Country;
import tiers.app.customer.repository.CountryRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CountryServiceImpl implements CountryService{

    @Autowired
    CountryRepository countryRepository;

    @Override
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Optional<Country> getCountryById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public Country getCountry(String countryName) {
        return countryRepository.findByCountryName(countryName);
    }

    @Override
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country findCountryByName(String countryName) {
        return countryRepository.findByCountryName(countryName);
    }
}
