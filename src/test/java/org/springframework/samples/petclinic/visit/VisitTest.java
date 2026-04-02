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

package org.springframework.samples.petclinic.visit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Visit} class.
 */
@DisplayName("Visit Unit Tests")
class VisitTest {

    private Visit visit;

    @BeforeEach
    void setUp() {
        visit = new Visit();
    }

    @Test
    @DisplayName("Should initialize with current date")
    void testInitializeWithCurrentDate() {
        assertNotNull(visit.getDate());
        assertEquals(LocalDate.now(), visit.getDate());
    }

    @Test
    @DisplayName("Should set and get date")
    void testSetAndGetDate() {
        LocalDate visitDate = LocalDate.of(2023, 5, 15);
        visit.setDate(visitDate);
        assertEquals(visitDate, visit.getDate());
    }

    @Test
    @DisplayName("Should set and get description")
    void testSetAndGetDescription() {
        visit.setDescription("Routine checkup");
        assertEquals("Routine checkup", visit.getDescription());
    }

    @Test
    @DisplayName("Should set and get pet ID")
    void testSetAndGetPetId() {
        visit.setPetId(5);
        assertEquals((Integer) 5, visit.getPetId());
    }

    @Test
    @DisplayName("Should be new when ID is null")
    void testIsNewWhenIdNull() {
        assertTrue(visit.isNew());
    }

    @Test
    @DisplayName("Should not be new when ID is set")
    void testIsNotNewWhenIdSet() {
        visit.setId(1);
        assertFalse(visit.isNew());
    }

    @Test
    @DisplayName("Should handle null description")
    void testNullDescription() {
        visit.setDescription("Checkup");
        visit.setDescription(null);
        assertNull(visit.getDescription());
    }

    @Test
    @DisplayName("Should handle null pet ID")
    void testNullPetId() {
        visit.setPetId(5);
        visit.setPetId(null);
        assertNull(visit.getPetId());
    }
}
