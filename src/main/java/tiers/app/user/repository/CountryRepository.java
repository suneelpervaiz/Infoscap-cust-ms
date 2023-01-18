package tiers.app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tiers.app.user.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
