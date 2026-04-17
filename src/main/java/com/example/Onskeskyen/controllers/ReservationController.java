package com.example.Onskeskyen.controllers;

import com.example.Onskeskyen.models.Onske;
import com.example.Onskeskyen.models.Onskeliste;
import com.example.Onskeskyen.models.Reservation;
import com.example.Onskeskyen.services.OnskeService;
import com.example.Onskeskyen.services.OnskelisteService;
import com.example.Onskeskyen.services.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReservationController {

    private final ReservationService service;
    private final OnskeService onskeService;
    private final OnskelisteService onskelisteService;

    public ReservationController(ReservationService service,
                                 OnskeService onskeService,
                                 OnskelisteService onskelisteService) {
        this.service = service;
        this.onskeService = onskeService;
        this.onskelisteService = onskelisteService;
    }

    @PostMapping("/reservationer/{onskeId}")
    public String reserverOnske(@PathVariable int onskeId,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            redirectAttributes.addFlashAttribute("fejl", "Du skal være logget ind for at reservere et ønske.");
            return "redirect:/login";
        }

        Onske onske = onskeService.findById(onskeId);
        if (onske == null) {
            redirectAttributes.addFlashAttribute("fejl", "Ønsket findes ikke.");
            return "redirect:/onskelister";
        }

        Onskeliste liste = onskelisteService.findById(onske.getOnskelisteId());
        if (liste == null) {
            redirectAttributes.addFlashAttribute("fejl", "Ønskelisten findes ikke.");
            return "redirect:/onskelister";
        }

        if (liste.getBrugerId() == brugerId) {
            redirectAttributes.addFlashAttribute("fejl", "Du kan ikke reservere dine egne ønsker.");
            return "redirect:/onskelister/delt/" + liste.getOnskelisteId();
        }

        if (onske.isKobt()) {
            redirectAttributes.addFlashAttribute("fejl", "Ønsket er allerede købt.");
            return "redirect:/onskelister/delt/" + liste.getOnskelisteId();
        }

        if (service.erReserveret(onskeId)) {
            redirectAttributes.addFlashAttribute("fejl", "Ønsket er allerede reserveret.");
            return "redirect:/onskelister/delt/" + liste.getOnskelisteId();
        }

        Reservation reservation = new Reservation(onskeId, brugerId);
        service.save(reservation);

        redirectAttributes.addFlashAttribute("success", "Ønsket er nu reserveret.");
        return "redirect:/onskelister/delt/" + liste.getOnskelisteId();
    }

    @PostMapping("/reservationer/{onskeId}/annuller")
    public String annullerReservation(@PathVariable int onskeId,
                                      HttpSession session,
                                      RedirectAttributes redirectAttributes) {

        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            return "redirect:/login";
        }

        Onske onske = onskeService.findById(onskeId);
        if (onske == null) {
            return "redirect:/onskelister";
        }

        Onskeliste liste = onskelisteService.findById(onske.getOnskelisteId());
        if (liste == null) {
            return "redirect:/onskelister";
        }

        Reservation reservation = service.findByOnskeId(onskeId);

        if (reservation == null) {
            redirectAttributes.addFlashAttribute("fejl", "Der findes ingen reservation.");
            return "redirect:/onskelister/delt/" + liste.getOnskelisteId();
        }

        if (reservation.getBrugerId() != brugerId) {
            redirectAttributes.addFlashAttribute("fejl", "Du kan kun annullere din egen reservation.");
            return "redirect:/onskelister/delt/" + liste.getOnskelisteId();
        }

        service.deleteByOnskeIdAndBrugerId(onskeId, brugerId);

        redirectAttributes.addFlashAttribute("success", "Reservationen er annulleret.");
        return "redirect:/onskelister/delt/" + liste.getOnskelisteId();
    }

    @PostMapping("/onsker/{onskeId}/kobt")
    public String markerSomKobt(@PathVariable int onskeId,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            return "redirect:/login";
        }

        Onske onske = onskeService.findById(onskeId);
        if (onske == null) {
            return "redirect:/onskelister";
        }

        Onskeliste liste = onskelisteService.findById(onske.getOnskelisteId());
        if (liste == null) {
            return "redirect:/onskelister";
        }

        Reservation reservation = service.findByOnskeId(onskeId);

        if (reservation == null || reservation.getBrugerId() != brugerId) {
            redirectAttributes.addFlashAttribute("fejl", "Du kan kun markere dit eget reserverede ønske som købt.");
            return "redirect:/onskelister/delt/" + liste.getOnskelisteId();
        }

        onskeService.markAsKobt(onskeId);
        service.deleteByOnskeId(onskeId);

        redirectAttributes.addFlashAttribute("success", "Ønsket er markeret som købt.");
        return "redirect:/onskelister/delt/" + liste.getOnskelisteId();
    }
}