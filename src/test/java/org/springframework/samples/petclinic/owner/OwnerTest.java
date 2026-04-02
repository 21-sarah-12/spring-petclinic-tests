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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Owner} class.
 * 
 * Tests cover:
 * - Address, city, telephone getters/setters
 * - Pet management (add, get, getPets)
 * - toString() method
 */
@DisplayName("Owner Unit Tests")
class OwnerTest {

    private Owner owner;
    private Pet pet;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        pet = new Pet();
    }

    // ==================== Happy Path Tests ====================

    @Test
    @DisplayName("Should initialize with null address")
    void testInitializeWithNullAddress() {
        assertNull(owner.getAddress());
    }

    @Test
    @DisplayName("Should set and get address correctly")
    void testSetAndGetAddress() {
        owner.setAddress("123 Main St");
        assertEquals("123 Main St", owner.getAddress());
    }

    @Test
    @DisplayName("Should set and get city correctly")
    void testSetAndGetCity() {
        owner.setCity("Springfield");
        assertEquals("Springfield", owner.getCity());
    }

    @Test
    @DisplayName("Should set and get telephone correctly")
    void testSetAndGetTelephone() {
        owner.setTelephone("5551234567");
        assertEquals("5551234567", owner.getTelephone());
    }

    @Test
    @DisplayName("Should initialize with empty pet list")
    void testInitializeWithEmptyPetList() {
        List<Pet> pets = owner.getPets();
        assertNotNull(pets);
        assertTrue(pets.isEmpty());
    }

    @Test
    @DisplayName("Should add pet to owner")
    void testAddPet() {
        pet.setName("Fluffy");
        owner.addPet(pet);
        
        assertEquals(owner, pet.getOwner());
        assertTrue(owner.getPets().contains(pet));
    }

    @Test
    @DisplayName("Should get pet by name")
    void testGetPetByName() {
        pet.setName("Fluffy");
        owner.addPet(pet);
        
        Pet foundPet = owner.getPet("Fluffy");
        assertNotNull(foundPet);
        assertEquals("Fluffy", foundPet.getName());
    }

    @Test
    @DisplayName("Should get pet by name case-insensitive")
    void testGetPetByNameCaseInsensitive() {
        pet.setName("Fluffy");
        owner.addPet(pet);
        
        Pet foundPet = owner.getPet("FLUFFY");
        assertNotNull(foundPet);
        assertEquals("Fluffy", foundPet.getName());
    }

    @Test
    @DisplayName("Should return null when pet not found")
    void testGetPetNotFound() {
        pet.setName("Fluffy");
        owner.addPet(pet);
        
        Pet notFound = owner.getPet("Fido");
        assertNull(notFound);
    }

    @Test
    @DisplayName("Should return sorted pet list")
    void testGetPetsSorted() {
        Pet pet1 = new Pet();
        pet1.setName("Zebra");
        Pet pet2 = new Pet();
        pet2.setName("Apple");
        Pet pet3 = new Pet();
        pet3.setName("Monkey");
        
        owner.addPet(pet1);
        owner.addPet(pet2);
        owner.addPet(pet3);
        
        List<Pet> pets = owner.getPets();
        assertEquals(3, pets.size());
        assertEquals("Apple", pets.get(0).getName());
        assertEquals("Monkey", pets.get(1).getName());
        assertEquals("Zebra", pets.get(2).getName());
    }

    @Test
    @DisplayName("Should return unmodifiable pet list")
    void testGetPetsUnmodifiable() {
        pet.setName("Fluffy");
        owner.addPet(pet);
        
        List<Pet> pets = owner.getPets();
        assertThrows(UnsupportedOperationException.class, () -> pets.add(new Pet()));
    }

    @Test
    @DisplayName("Should set owner information")
    void testSetOwnerInformation() {
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setAddress("123 Main St");
        owner.setCity("Springfield");
        owner.setTelephone("5551234567");
        
        assertEquals("John", owner.getFirstName());
        assertEquals("Doe", owner.getLastName());
        assertEquals("123 Main St", owner.getAddress());
        assertEquals("Springfield", owner.getCity());
        assertEquals("5551234567", owner.getTelephone());
    }

    // ==================== Error Path Tests ====================

    @Test
    @DisplayName("Should handle null address")
    void testNullAddress() {
        owner.setAddress("123 Main St");
        owner.setAddress(null);
        assertNull(owner.getAddress());
    }

    @Test
    @DisplayName("Should handle empty address")
    void testEmptyAddress() {
        owner.setAddress("");
        assertEquals("", owner.getAddress());
    }

    @Test
    @DisplayName("Should handle null city")
    void testNullCity() {
        owner.setCity("Springfield");
        owner.setCity(null);
        assertNull(owner.getCity());
    }

    @Test
    @DisplayName("Should handle null telephone")
    void testNullTelephone() {
        owner.setTelephone("5551234567");
        owner.setTelephone(null);
        assertNull(owner.getTelephone());
    }

    @Test
    @DisplayName("Should get pet by name with ignoreNew flag true")
    void testGetPetByNameIgnoreNewTrue() {
        pet.setName("Fluffy");
        owner.addPet(pet);
        
        Pet foundPet = owner.getPet("Fluffy", true);
        assertNull(foundPet);
    }

    @Test
    @DisplayName("Should get pet by name with ignoreNew flag false")
    void testGetPetByNameIgnoreNewFalse() {
        pet.setName("Fluffy");
        owner.addPet(pet);
        
        Pet foundPet = owner.getPet("Fluffy", false);
        assertNotNull(foundPet);
    }

    @Test
    @DisplayName("Should handle multiple pets with same name prefix")
    void testMultiplePetsWithSamePrefix() {
        Pet pet1 = new Pet();
        pet1.setName("Fluffy");
        Pet pet2 = new Pet();
        pet2.setName("Fluff");
        
        owner.addPet(pet1);
        owner.addPet(pet2);
        
        Pet found = owner.getPet("Fluffy");
        assertEquals("Fluffy", found.getName());
    }

    @Test
    @DisplayName("Should return toString representation")
    void testToString() {
        owner.setId(1);
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setAddress("123 Main St");
        owner.setCity("Springfield");
        owner.setTelephone("5551234567");
        
        String toString = owner.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("John"));
        assertTrue(toString.contains("Doe"));
    }
}
