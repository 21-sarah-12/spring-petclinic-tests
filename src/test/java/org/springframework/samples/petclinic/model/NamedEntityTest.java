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
 * Unit tests for {@link NamedEntity} class.
 * 
 * Tests cover:
 * - Name getter/setter functionality
 * - toString() method
 * - Inheritance from BaseEntity
 */
@DisplayName("NamedEntity Unit Tests")
class NamedEntityTest {

    private NamedEntity namedEntity;

    @BeforeEach
    void setUp() {
        namedEntity = new NamedEntity();
    }

    // ==================== Happy Path Tests ====================

    @Test
    @DisplayName("Should initialize with null name")
    void testInitializeWithNullName() {
        assertNull(namedEntity.getName());
    }

    @Test
    @DisplayName("Should set and get name correctly")
    void testSetAndGetName() {
        namedEntity.setName("Dog");
        assertEquals("Dog", namedEntity.getName());
    }

    @Test
    @DisplayName("Should set and get different name values")
    void testSetAndGetDifferentNames() {
        namedEntity.setName("Cat");
        assertEquals("Cat", namedEntity.getName());

        namedEntity.setName("Bird");
        assertEquals("Bird", namedEntity.getName());
    }

    @Test
    @DisplayName("Should return name in toString()")
    void testToStringReturnsName() {
        namedEntity.setName("Dentistry");
        assertEquals("Dentistry", namedEntity.toString());
    }

    @Test
    @DisplayName("Should handle empty string name")
    void testEmptyStringName() {
        namedEntity.setName("");
        assertEquals("", namedEntity.getName());
        assertEquals("", namedEntity.toString());
    }

    @Test
    @DisplayName("Should handle name with spaces")
    void testNameWithSpaces() {
        namedEntity.setName("Pet Type");
        assertEquals("Pet Type", namedEntity.getName());
    }

    @Test
    @DisplayName("Should handle name with special characters")
    void testNameWithSpecialCharacters() {
        namedEntity.setName("Type-1 (Special)");
        assertEquals("Type-1 (Special)", namedEntity.getName());
    }

    @Test
    @DisplayName("Should handle long name")
    void testLongName() {
        String longName = new String(new char[255]).replace('\0', 'A');
        namedEntity.setName(longName);
        assertEquals(longName, namedEntity.getName());
    }

    @Test
    @DisplayName("Should inherit ID functionality from BaseEntity")
    void testInheritIdFromBaseEntity() {
        assertTrue(namedEntity.isNew());
        namedEntity.setId(1);
        assertFalse(namedEntity.isNew());
        assertEquals((Integer) 1, namedEntity.getId());
    }

    // ==================== Error Path Tests ====================

    @Test
    @DisplayName("Should reset name to null")
    void testResetNameToNull() {
        namedEntity.setName("Dog");
        assertEquals("Dog", namedEntity.getName());

        namedEntity.setName(null);
        assertNull(namedEntity.getName());
        assertNull(namedEntity.toString());
    }

    @Test
    @DisplayName("Should handle multiple name changes")
    void testMultipleNameChanges() {
        namedEntity.setName("Dog");
        assertEquals("Dog", namedEntity.getName());

        namedEntity.setName("Cat");
        assertEquals("Cat", namedEntity.getName());

        namedEntity.setName("Bird");
        assertEquals("Bird", namedEntity.getName());
    }

    @Test
    @DisplayName("Should maintain name state across multiple calls")
    void testNameStateConsistency() {
        namedEntity.setName("Specialty");
        assertEquals("Specialty", namedEntity.getName());
        assertEquals("Specialty", namedEntity.getName());
        assertEquals("Specialty", namedEntity.getName());
    }

    @Test
    @DisplayName("Should handle name with unicode characters")
    void testNameWithUnicodeCharacters() {
        namedEntity.setName("Café");
        assertEquals("Café", namedEntity.getName());
    }

    @Test
    @DisplayName("Should handle name with numbers")
    void testNameWithNumbers() {
        namedEntity.setName("Type123");
        assertEquals("Type123", namedEntity.getName());
    }

    @Test
    @DisplayName("Should combine ID and name functionality")
    void testCombineIdAndNameFunctionality() {
        namedEntity.setId(5);
        namedEntity.setName("TestName");
        
        assertEquals((Integer) 5, namedEntity.getId());
        assertEquals("TestName", namedEntity.getName());
        assertEquals("TestName", namedEntity.toString());
        assertFalse(namedEntity.isNew());
    }
}
