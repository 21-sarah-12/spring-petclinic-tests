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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PetValidator} class.
 */
@DisplayName("PetValidator Unit Tests")
class PetValidatorTest {

    private PetValidator validator;
    private Pet pet;
    private Errors errors;

    @BeforeEach
    void setUp() {
        validator = new PetValidator();
        pet = new Pet();
        errors = new BeanPropertyBindingResult(pet, "pet");
    }

    @Test
    @DisplayName("Should support Pet class")
    void testSupports() {
        assertTrue(validator.supports(Pet.class));
    }

    @Test
    @DisplayName("Should not support other classes")
    void testDoesNotSupport() {
        assertFalse(validator.supports(String.class));
    }

    @Test
    @DisplayName("Should reject pet with null name")
    void testRejectNullName() {
        pet.setName(null);
        validator.validate(pet, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    @DisplayName("Should reject pet with empty name")
    void testRejectEmptyName() {
        pet.setName("");
        validator.validate(pet, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    @DisplayName("Should accept pet with valid name")
    void testAcceptValidName() {
        pet.setName("Fluffy");
        validator.validate(pet, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    @DisplayName("Should reject pet with null type")
    void testRejectNullType() {
        pet.setName("Fluffy");
        pet.setType(null);
        validator.validate(pet, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    @DisplayName("Should accept pet with valid type")
    void testAcceptValidType() {
        pet.setName("Fluffy");
        PetType petType = new PetType();
        petType.setName("Dog");
        pet.setType(petType);
        validator.validate(pet, errors);
        assertFalse(errors.hasErrors());
    }
}
