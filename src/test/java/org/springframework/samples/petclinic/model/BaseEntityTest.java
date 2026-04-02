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
 * Unit tests for {@link BaseEntity} class.
 * 
 * Tests cover:
 * - ID getter/setter functionality
 * - isNew() method for entity lifecycle detection
 * - Edge cases with null values
 */
@DisplayName("BaseEntity Unit Tests")
class BaseEntityTest {

    private BaseEntity baseEntity;

    @BeforeEach
    void setUp() {
        baseEntity = new BaseEntity();
    }

    // ==================== Happy Path Tests ====================

    @Test
    @DisplayName("Should initialize with null ID")
    void testInitializeWithNullId() {
        assertNull(baseEntity.getId());
    }

    @Test
    @DisplayName("Should set and get ID correctly")
    void testSetAndGetId() {
        baseEntity.setId(1);
        assertEquals((Integer) 1, baseEntity.getId());
    }

    @Test
    @DisplayName("Should set and get different ID values")
    void testSetAndGetDifferentIds() {
        baseEntity.setId(100);
        assertEquals((Integer) 100, baseEntity.getId());

        baseEntity.setId(999);
        assertEquals((Integer) 999, baseEntity.getId());
    }

    @Test
    @DisplayName("Should return true for isNew() when ID is null")
    void testIsNewWhenIdIsNull() {
        assertTrue(baseEntity.isNew());
    }

    @Test
    @DisplayName("Should return false for isNew() when ID is set")
    void testIsNewWhenIdIsSet() {
        baseEntity.setId(1);
        assertFalse(baseEntity.isNew());
    }

    @Test
    @DisplayName("Should return false for isNew() with positive ID")
    void testIsNewWithPositiveId() {
        baseEntity.setId(42);
        assertFalse(baseEntity.isNew());
    }

    @Test
    @DisplayName("Should handle ID of 0")
    void testIdOfZero() {
        baseEntity.setId(0);
        assertEquals((Integer) 0, baseEntity.getId());
        assertFalse(baseEntity.isNew());
    }

    @Test
    @DisplayName("Should handle large ID values")
    void testLargeIdValues() {
        baseEntity.setId(Integer.MAX_VALUE);
        assertEquals((Integer) Integer.MAX_VALUE, baseEntity.getId());
        assertFalse(baseEntity.isNew());
    }

    // ==================== Error Path Tests ====================

    @Test
    @DisplayName("Should reset ID to null")
    void testResetIdToNull() {
        baseEntity.setId(1);
        assertFalse(baseEntity.isNew());

        baseEntity.setId(null);
        assertTrue(baseEntity.isNew());
        assertNull(baseEntity.getId());
    }

    @Test
    @DisplayName("Should handle multiple ID changes")
    void testMultipleIdChanges() {
        baseEntity.setId(1);
        assertFalse(baseEntity.isNew());

        baseEntity.setId(2);
        assertFalse(baseEntity.isNew());
        assertEquals((Integer) 2, baseEntity.getId());

        baseEntity.setId(null);
        assertTrue(baseEntity.isNew());
    }

    @Test
    @DisplayName("Should handle negative ID values")
    void testNegativeIdValues() {
        baseEntity.setId(-1);
        assertEquals((Integer) (-1), baseEntity.getId());
        assertFalse(baseEntity.isNew());
    }

    @Test
    @DisplayName("Should be serializable")
    void testSerializable() {
        assertTrue(baseEntity instanceof java.io.Serializable);
    }

    @Test
    @DisplayName("Should maintain ID state across multiple calls")
    void testIdStateConsistency() {
        baseEntity.setId(5);
        assertEquals((Integer) 5, baseEntity.getId());
        assertEquals((Integer) 5, baseEntity.getId());
        assertEquals((Integer) 5, baseEntity.getId());
    }

    @Test
    @DisplayName("Should transition from new to persisted state")
    void testTransitionFromNewToPersistedState() {
        assertTrue(baseEntity.isNew());
        baseEntity.setId(1);
        assertFalse(baseEntity.isNew());
    }

    @Test
    @DisplayName("Should transition from persisted to new state")
    void testTransitionFromPersistedToNewState() {
        baseEntity.setId(1);
        assertFalse(baseEntity.isNew());
        baseEntity.setId(null);
        assertTrue(baseEntity.isNew());
    }
}
