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

package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PetType} class.
 */
@DisplayName("PetType Unit Tests")
class PetTypeTest {

    private PetType petType;

    @BeforeEach
    void setUp() {
        petType = new PetType();
    }

    @Test
    @DisplayName("Should initialize with null name")
    void testInitializeWithNullName() {
        assertNull(petType.getName());
    }

    @Test
    @DisplayName("Should set and get name")
    void testSetAndGetName() {
        petType.setName("Dog");
        assertEquals("Dog", petType.getName());
    }

    @Test
    @DisplayName("Should return name in toString")
    void testToString() {
        petType.setName("Cat");
        assertEquals("Cat", petType.toString());
    }

    @Test
    @DisplayName("Should handle null name")
    void testNullName() {
        petType.setName("Dog");
        petType.setName(null);
        assertNull(petType.getName());
    }

    @Test
    @DisplayName("Should be new when ID is null")
    void testIsNewWhenIdNull() {
        assertTrue(petType.isNew());
    }

    @Test
    @DisplayName("Should not be new when ID is set")
    void testIsNotNewWhenIdSet() {
        petType.setId(1);
        assertFalse(petType.isNew());
    }
}
