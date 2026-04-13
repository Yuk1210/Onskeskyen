package com.example.Onskeskyen.controllers;

import com.example.Onskeskyen.services.BrugerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BrugerController {

    private final BrugerService services;
    public BrugerController(BrugerService services) {
        this.services = services;
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
    public String login(@RequestParam String email, @RequestParam String kodeord, Model model, HttpSession session) {
        boolean gyldigLogin = services.godkendLogin(email, kodeord);
        if (gyldigLogin) {
            session.setAttribute("loggetIndEmail", email);
            return "redirect:/onskeliste";
        } else {
            model.addAttribute("fejl", "Forkert email eller adgangskode, eller ugyldigt format");
            return "login";
        }
    }

    @PostMapping("/opret")
    public String opretBruger(@RequestParam String navn, @RequestParam String email, @RequestParam String kodeord) {
        services.opretBruger(navn, email, kodeord);
        return "redirect:/";
    }

    @GetMapping("/onskeliste")
    public String visOnskeliste(HttpSession session, Model model) {
        String email = (String) session.getAttribute("loggetIndEmail");
        if (email == null) { return "redirect:/login"; }

        model.addAttribute("email", email);
        return "onskeliste";
    }
}




