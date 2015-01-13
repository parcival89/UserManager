package com.sander.usermanager.controller;

import com.sander.usermanager.domain.Gebruiker;
import com.sander.usermanager.repository.GebruikerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by SanderP on 12/01/2015.
 */
@Controller
@ResponseBody
public class GebruikerController {

    private final GebruikerRepository gebruikerRepository = GebruikerRepository.gebruikerRepository();

    @RequestMapping(value = "/gebruiker", method= RequestMethod.GET)
    public Gebruiker geefGebruiker(@RequestParam(value="id", required = true)Long id) {
        return gebruikerRepository.zoekMetId(id);
    }
}
