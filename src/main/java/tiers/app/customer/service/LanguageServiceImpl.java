package tiers.app.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiers.app.customer.model.Language;
import tiers.app.customer.repository.LanguageRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LanguageServiceImpl implements LanguageService{

    @Autowired
    LanguageRepository languageRepository;

    @Override
    public Language saveLanguage(Language language) {
        return languageRepository.save(language);
    }

    @Override
    public Optional<Language> getLanguageById(Long id) {
        return languageRepository.findById(id);
    }

    @Override
    public Language getLanguage(String languageName) {
        return languageRepository.getLanguageByLanguageName(languageName);
    }

    @Override
    public List<Language> getLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public Language getLanguageByName(String languageName) {
        return languageRepository.getLanguageByLanguageName(languageName);
    }
}
