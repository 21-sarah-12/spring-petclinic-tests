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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Pet} class.
 */
@DisplayName("Pet Unit Tests")
class PetTest {

    private Pet pet;
    private Owner owner;
    private PetType petType;

    @BeforeEach
    void setUp() {
        pet = new Pet();
        owner = new Owner();
        petType = new PetType();
    }

    @Test
    @DisplayName("Should initialize with null name")
    void testInitializeWithNullName() {
        assertNull(pet.getName());
    }

    @Test
    @DisplayName("Should set and get name")
    void testSetAndGetName() {
        pet.setName("Fluffy");
        assertEquals("Fluffy", pet.getName());
    }

    @Test
    @DisplayName("Should set and get birth date")
    void testSetAndGetBirthDate() {
        LocalDate birthDate = LocalDate.of(2020, 1, 15);
        pet.setBirthDate(birthDate);
        assertEquals(birthDate, pet.getBirthDate());
    }

    @Test
    @DisplayName("Should set and get pet type")
    void testSetAndGetPetType() {
        petType.setName("Dog");
        pet.setType(petType);
        assertEquals(petType, pet.getType());
    }

    @Test
    @DisplayName("Should set and get owner")
    void testSetAndGetOwner() {
        pet.setOwner(owner);
        assertEquals(owner, pet.getOwner());
    }

    @Test
    @DisplayName("Should be new when ID is null")
    void testIsNewWhenIdNull() {
        assertTrue(pet.isNew());
    }

    @Test
    @DisplayName("Should not be new when ID is set")
    void testIsNotNewWhenIdSet() {
        pet.setId(1);
        assertFalse(pet.isNew());
    }

    @Test
    @DisplayName("Should handle null owner")
    void testNullOwner() {
        pet.setOwner(null);
        assertNull(pet.getOwner());
    }

    @Test
    @DisplayName("Should handle null pet type")
    void testNullPetType() {
        pet.setType(null);
        assertNull(pet.getType());
    }

    @Test
    @DisplayName("Should handle null birth date")
    void testNullBirthDate() {
        pet.setBirthDate(LocalDate.now());
        pet.setBirthDate(null);
        assertNull(pet.getBirthDate());
    }
}
