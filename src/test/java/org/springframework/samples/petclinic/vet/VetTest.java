/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.vet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Vet} class.
 */
@DisplayName("Vet Unit Tests")
class VetTest {

    private Vet vet;
    private Specialty specialty;

    @BeforeEach
    void setUp() {
        vet = new Vet();
        specialty = new Specialty();
    }

    @Test
    @DisplayName("Should initialize with empty specialties")
    void testInitializeWithEmptySpecialties() {
        List<Specialty> specialties = vet.getSpecialties();
        assertNotNull(specialties);
        assertTrue(specialties.isEmpty());
    }

    @Test
    @DisplayName("Should add specialty")
    void testAddSpecialty() {
        specialty.setName("Dentistry");
        vet.addSpecialty(specialty);
        assertTrue(vet.getSpecialties().contains(specialty));
    }

    @Test
    @DisplayName("Should get number of specialties")
    void testGetNrOfSpecialties() {
        specialty.setName("Dentistry");
        vet.addSpecialty(specialty);
        assertEquals(1, vet.getNrOfSpecialties());
    }

    @Test
    @DisplayName("Should set first name")
    void testSetFirstName() {
        vet.setFirstName("James");
        assertEquals("James", vet.getFirstName());
    }

    @Test
    @DisplayName("Should set last name")
    void testSetLastName() {
        vet.setLastName("Carter");
        assertEquals("Carter", vet.getLastName());
    }

    @Test
    @DisplayName("Should be new when ID is null")
    void testIsNewWhenIdNull() {
        assertTrue(vet.isNew());
    }

    @Test
    @DisplayName("Should not be new when ID is set")
    void testIsNotNewWhenIdSet() {
        vet.setId(1);
        assertFalse(vet.isNew());
    }
}
