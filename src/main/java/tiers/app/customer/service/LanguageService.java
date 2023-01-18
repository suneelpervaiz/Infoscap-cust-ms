package tiers.app.customer.service;

import tiers.app.customer.model.Device;
import tiers.app.customer.model.Language;

import java.util.List;
import java.util.Optional;

public interface LanguageService {
    Language saveLanguage(Language language);
    Optional<Language> getLanguageById(Long id);
    Language getLanguage(String languageName);
    List<Language> getLanguages();
    Language getLanguageByName(String languageName);
}
