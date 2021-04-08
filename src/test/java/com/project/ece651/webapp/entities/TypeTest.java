package com.project.ece651.webapp.entities;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class TypeTest {

    @Test
    void testToString() {
        Type type = Type.Apartment;
        assertEquals("Apartment", type.toString());
    }
}