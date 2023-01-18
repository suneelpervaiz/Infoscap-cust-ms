package tiers.app.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tiers.app.customer.model.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    Language getLanguageByLanguageName(String languageName);

}
