package com.project.ece651.webapp.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    @Test
    void testToString() {
        Type type = Type.APARTMENT;
        assertEquals("Apartment", type.toString());
    }
}