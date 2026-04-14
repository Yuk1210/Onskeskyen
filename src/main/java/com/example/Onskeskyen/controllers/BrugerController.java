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
public class BrugerController {

    private final BrugerService services;
    private final OnskeService onskeService;

    public BrugerController(BrugerService services, OnskeService onskeService) {
        this.services = services;
        this.onskeService = onskeService;
    }

    @GetMapping({"/", "/index"})
    public String startside() {
        return "index";
    }

    @GetMapping("/login")
    public String visLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String kodeord,
                        Model model,
                        HttpSession session) {

        boolean gyldigLogin = services.godkendLogin(email, kodeord);

        if (gyldigLogin) {
            session.setAttribute("loggetIndEmail", email);
            return "redirect:/onskeliste";
        } else {
            model.addAttribute("fejl", "Forkert email eller adgangskode");
            return "login";
        }
    }

    @PostMapping("/opret")
    public String opretBruger(@RequestParam String navn,
                              @RequestParam String email,
                              @RequestParam String kodeord,
                              HttpSession session) {

        services.opretBruger(navn, email, kodeord);
        session.setAttribute("loggetIndEmail", email);
        return "redirect:/onskeliste";
    }

    @GetMapping("/onskeliste")
    public String visOnskeliste(HttpSession session, Model model) {
        String email = (String) session.getAttribute("loggetIndEmail");

        if (email == null) {
            return "redirect:/login";
        }

        Bruger bruger = services.findBrugerByEmail(email);

        if (bruger == null) {
            return "redirect:/login";
        }

        model.addAttribute("bruger", bruger);
        model.addAttribute("onsker", onskeService.hentOnsker(bruger.getBrugerId()));

        return "onskeliste";
    }

    @PostMapping("/tilfoej-onske")
    public String tilfoejOnske(@RequestParam String navn,
                               @RequestParam String link,
                               @RequestParam double pris,
                               @RequestParam(required = false) String billede,
                               HttpSession session) {

        String email = (String) session.getAttribute("loggetIndEmail");

        if (email == null) {
            return "redirect:/login";
        }

        Bruger bruger = services.findBrugerByEmail(email);

        if (bruger == null) {
            return "redirect:/login";
        }

        onskeService.opretOnske(bruger.getBrugerId(), navn, link, pris, billede);

        return "redirect:/onskeliste";
    }
}