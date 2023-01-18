package tiers.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiers.app.user.repository.LanguageRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class LanguageServiceImpl implements LanguageService{

    @Autowired
    LanguageRepository languageRepository;

}
