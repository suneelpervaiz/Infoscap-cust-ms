package tiers.app.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tiers.app.customer.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByCountryName(String countryName);
}
