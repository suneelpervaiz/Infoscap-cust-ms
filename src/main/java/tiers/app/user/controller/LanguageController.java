package tiers.app.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tiers.app.user.service.LanguageService;

@RestController
@RequestMapping("/api/v1/languages")
public class LanguageController {

    @Autowired
    LanguageService languageService;

}
