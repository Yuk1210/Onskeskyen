package com.example.Onskeskyen.controllers;

import com.example.Onskeskyen.models.Bruger;
import com.example.Onskeskyen.services.BrugerService;
import com.example.Onskeskyen.services.OnskeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OnskeController {

    private final OnskeService onskeService;
    private final BrugerService brugerService;

    public OnskeController(OnskeService onskeService, BrugerService brugerService) {
        this.onskeService = onskeService;
        this.brugerService = brugerService;
    }

    @GetMapping("/onskeliste")
    public String visOnskeliste(HttpSession session, Model model) {
        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            return "redirect:/login";
        }

        Bruger bruger = brugerService.findBruger(brugerId);

        if (bruger == null) {
            return "redirect:/login";
        }

        model.addAttribute("bruger", bruger);
        model.addAttribute("onsker", onskeService.hentOnsker(brugerId));

        return "onskeliste";
    }

    @GetMapping("/tilfoej-onske")
    public String visTilfoejOnske(HttpSession session) {
        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            return "redirect:/login";
        }

        return "opret-onske";
    }

    @PostMapping("/tilfoej-onske")
    public String tilfoejOnske(@RequestParam String navn,
                               @RequestParam String link,
                               @RequestParam double pris,
                               @RequestParam(required = false) String billede,
                               HttpSession session) {

        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            return "redirect:/login";
        }

        onskeService.opretOnske(brugerId, navn, link, pris, billede);

        return "redirect:/onskeliste";
    }
}