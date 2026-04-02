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

package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Person} class.
 * 
 * Tests cover:
 * - First name getter/setter
 * - Last name getter/setter
 * - Inheritance from BaseEntity
 */
@DisplayName("Person Unit Tests")
class PersonTest {

    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person();
    }

    // ==================== Happy Path Tests ====================

    @Test
    @DisplayName("Should initialize with null first name")
    void testInitializeWithNullFirstName() {
        assertNull(person.getFirstName());
    }

    @Test
    @DisplayName("Should initialize with null last name")
    void testInitializeWithNullLastName() {
        assertNull(person.getLastName());
    }

    @Test
    @DisplayName("Should set and get first name correctly")
    void testSetAndGetFirstName() {
        person.setFirstName("John");
        assertEquals("John", person.getFirstName());
    }

    @Test
    @DisplayName("Should set and get last name correctly")
    void testSetAndGetLastName() {
        person.setLastName("Doe");
        assertEquals("Doe", person.getLastName());
    }

    @Test
    @DisplayName("Should set and get both names")
    void testSetAndGetBothNames() {
        person.setFirstName("Jane");
        person.setLastName("Smith");
        
        assertEquals("Jane", person.getFirstName());
        assertEquals("Smith", person.getLastName());
    }

    @Test
    @DisplayName("Should handle first name with spaces")
    void testFirstNameWithSpaces() {
        person.setFirstName("Jean Pierre");
        assertEquals("Jean Pierre", person.getFirstName());
    }

    @Test
    @DisplayName("Should handle last name with spaces")
    void testLastNameWithSpaces() {
        person.setLastName("Van Der Berg");
        assertEquals("Van Der Berg", person.getLastName());
    }

    @Test
    @DisplayName("Should handle first name with special characters")
    void testFirstNameWithSpecialCharacters() {
        person.setFirstName("José");
        assertEquals("José", person.getFirstName());
    }

    @Test
    @DisplayName("Should handle last name with special characters")
    void testLastNameWithSpecialCharacters() {
        person.setLastName("O'Brien");
        assertEquals("O'Brien", person.getLastName());
    }

    @Test
    @DisplayName("Should inherit ID functionality from BaseEntity")
    void testInheritIdFromBaseEntity() {
        assertTrue(person.isNew());
        person.setId(1);
        assertFalse(person.isNew());
        assertEquals((Integer) 1, person.getId());
    }

    // ==================== Error Path Tests ====================

    @Test
    @DisplayName("Should reset first name to null")
    void testResetFirstNameToNull() {
        person.setFirstName("John");
        assertEquals("John", person.getFirstName());

        person.setFirstName(null);
        assertNull(person.getFirstName());
    }

    @Test
    @DisplayName("Should reset last name to null")
    void testResetLastNameToNull() {
        person.setLastName("Doe");
        assertEquals("Doe", person.getLastName());

        person.setLastName(null);
        assertNull(person.getLastName());
    }

    @Test
    @DisplayName("Should handle empty first name")
    void testEmptyFirstName() {
        person.setFirstName("");
        assertEquals("", person.getFirstName());
    }

    @Test
    @DisplayName("Should handle empty last name")
    void testEmptyLastName() {
        person.setLastName("");
        assertEquals("", person.getLastName());
    }

    @Test
    @DisplayName("Should handle multiple first name changes")
    void testMultipleFirstNameChanges() {
        person.setFirstName("John");
        assertEquals("John", person.getFirstName());

        person.setFirstName("Jane");
        assertEquals("Jane", person.getFirstName());

        person.setFirstName("Bob");
        assertEquals("Bob", person.getFirstName());
    }

    @Test
    @DisplayName("Should handle multiple last name changes")
    void testMultipleLastNameChanges() {
        person.setLastName("Doe");
        assertEquals("Doe", person.getLastName());

        person.setLastName("Smith");
        assertEquals("Smith", person.getLastName());

        person.setLastName("Johnson");
        assertEquals("Johnson", person.getLastName());
    }

    @Test
    @DisplayName("Should maintain name state consistency")
    void testNameStateConsistency() {
        person.setFirstName("Alice");
        person.setLastName("Wonder");
        
        assertEquals("Alice", person.getFirstName());
        assertEquals("Wonder", person.getLastName());
        assertEquals("Alice", person.getFirstName());
        assertEquals("Wonder", person.getLastName());
    }

    @Test
    @DisplayName("Should handle first name with numbers")
    void testFirstNameWithNumbers() {
        person.setFirstName("John123");
        assertEquals("John123", person.getFirstName());
    }

    @Test
    @DisplayName("Should handle last name with numbers")
    void testLastNameWithNumbers() {
        person.setLastName("Doe456");
        assertEquals("Doe456", person.getLastName());
    }

    @Test
    @DisplayName("Should combine ID and name functionality")
    void testCombineIdAndNameFunctionality() {
        person.setId(10);
        person.setFirstName("Robert");
        person.setLastName("Brown");
        
        assertEquals((Integer) 10, person.getId());
        assertEquals("Robert", person.getFirstName());
        assertEquals("Brown", person.getLastName());
        assertFalse(person.isNew());
    }
}
