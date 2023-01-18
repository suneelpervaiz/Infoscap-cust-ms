package tiers.app.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tiers.app.customer.service.CountryService;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    @Autowired
    CountryService countryService;
}
