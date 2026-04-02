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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PetTypeFormatter} class.
 */
@DisplayName("PetTypeFormatter Unit Tests")
@ExtendWith(MockitoExtension.class)
class PetTypeFormatterTest {

    @Mock
    private PetRepository petRepository;

    private PetTypeFormatter formatter;
    private PetType petType;

    @BeforeEach
    void setUp() {
        formatter = new PetTypeFormatter(petRepository);
        petType = new PetType();
    }

    @Test
    @DisplayName("Should print pet type name")
    void testPrint() {
        petType.setName("Dog");
        String result = formatter.print(petType, Locale.ENGLISH);
        assertEquals("Dog", result);
    }

    @Test
    @DisplayName("Should parse pet type name")
    void testParse() throws ParseException {
        String input = "Cat";
        PetType result = formatter.parse(input, Locale.ENGLISH);
        assertEquals("Cat", result.getName());
    }

    @Test
    @DisplayName("Should handle null pet type in print")
    void testPrintNull() {
        String result = formatter.print(null, Locale.ENGLISH);
        assertEquals("", result);
    }

    @Test
    @DisplayName("Should handle empty string in parse")
    void testParseEmpty() throws ParseException {
        PetType result = formatter.parse("", Locale.ENGLISH);
        assertEquals("", result.getName());
    }
}
