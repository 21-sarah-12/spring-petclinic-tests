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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for {@link OwnerRepository} class.
 */
@DisplayName("OwnerRepository Integration Tests")
@DataJpaTest
class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    @DisplayName("Should find owner by last name")
    void testFindByLastName() {
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setAddress("123 Main St");
        owner.setCity("Springfield");
        owner.setTelephone("5551234567");
        ownerRepository.save(owner);

        Collection<Owner> found = ownerRepository.findByLastName("Doe");
        assertNotNull(found);
        assertFalse(found.isEmpty());
    }

    @Test
    @DisplayName("Should return empty collection when owner not found")
    void testFindByLastNameNotFound() {
        Collection<Owner> found = ownerRepository.findByLastName("NonExistent");
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }
}
