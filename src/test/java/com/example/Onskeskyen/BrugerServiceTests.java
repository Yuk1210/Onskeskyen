package com.example.Onskeskyen;

import com.example.Onskeskyen.models.Bruger;
import com.example.Onskeskyen.services.BrugerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BrugerServiceTests {

    @Autowired
    private BrugerService brugerService;

    @Test
    void opretBruger() {
        Bruger bruger = brugerService.opretBruger("Anna", "anna@mail.com", "1234");

        assertNotNull(bruger);
        assertEquals("Anna", bruger.getNavn());
        assertEquals("anna@mail.com", bruger.getEmail());
        assertEquals("1234", bruger.getKodeord());
    }
}