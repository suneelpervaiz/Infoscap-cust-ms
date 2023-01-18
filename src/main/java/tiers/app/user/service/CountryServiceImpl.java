package tiers.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiers.app.user.repository.CountryRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class CountryServiceImpl implements CountryService{

    @Autowired
    CountryRepository countryRepository;

}
