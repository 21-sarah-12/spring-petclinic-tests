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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Vets} class.
 */
@DisplayName("Vets Unit Tests")
class VetsTest {

    private Vets vets;

    @BeforeEach
    void setUp() {
        vets = new Vets();
    }

    @Test
    @DisplayName("Should initialize with empty vet list")
    void testInitializeWithEmptyList() {
        assertNotNull(vets.getVetList());
        assertTrue(vets.getVetList().isEmpty());
    }

    @Test
    @DisplayName("Should get vet list")
    void testGetVetList() {
        List<Vet> vetList = vets.getVetList();
        assertNotNull(vetList);
    }
}
