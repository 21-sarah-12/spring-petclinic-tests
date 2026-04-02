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

/**
 * PetType formatter for Spring MVC type conversion.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Pet Management<br>
 * <strong>Purpose:</strong> Provides bidirectional conversion between
 * {@link PetType} objects and their string representations for Spring MVC
 * form binding and display.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Formatter (Web Layer)<br>
 * This class implements Spring's {@link org.springframework.format.Formatter}
 * interface to enable automatic conversion of PetType objects to and from
 * strings in web forms and request parameters.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>Implements {@link org.springframework.format.Formatter} for Spring MVC
 * integration.</li>
 * <li>Annotated with {@code @Component} for automatic Spring registration.</li>
 * <li>Provides {@code print()} method to convert PetType to string (name).</li>
 * <li>Provides {@code parse()} method to convert string to PetType by name
 * lookup.</li>
 * <li>Thread-safe: stateless formatter.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Dependencies:</strong>
 * <ul>
 * <li>Spring Framework: Formatter interface and Component annotation.</li>
 * <li>PetRepository: For looking up PetType by name.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Usage Example:</strong>
 * <pre>{@code
 * // Automatically registered by Spring and used in form binding
 * // In a form, a PetType field is rendered as:
 * // <select name="type">
 * //   <option value="Dog">Dog</option>
 * //   <option value="Cat">Cat</option>
 * // </select>
 *
 * // When the form is submitted, the string "Dog" is automatically
 * // converted to the corresponding PetType object using this formatter.
 * }</pre>
 * </p>
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Michael Isvy
 * @version 1.0
 * @since 1.0
 * @see PetType
 * @see PetRepository
 * @see org.springframework.format.Formatter
 */
package org.springframework.samples.petclinic.owner;


import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

/**
 * Instructs Spring MVC on how to parse and print elements of type 'PetType'.
 *
 * <p>
 * Starting from Spring 3.0, Formatters have come as an improvement in
 * comparison to legacy PropertyEditors. See the following links for more
 * details:
 * <ul>
 * <li><a href="http://static.springsource.org/spring/docs/current/spring-framework-reference/html/validation.html#format-Formatter-SPI">
 * Spring Formatter SPI</a></li>
 * <li><a href="http://gordondickens.com/wordpress/2010/09/30/using-spring-3-0-custom-type-converter/">
 * Using Spring 3.0 Custom Type Converter</a></li>
 * </ul>
 * </p>
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Michael Isvy
 * @version 1.0
 * @since 1.0
 */
@Component
public class PetTypeFormatter implements Formatter<PetType> {

	/**
	 * Repository for accessing PetType data.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Used to look up PetType objects by name during
	 * parsing.
	 * </p>
	 */
	private final PetRepository pets;


	/**
	 * Constructs a PetTypeFormatter with the given PetRepository.
	 *
	 * <p>
	 * Initializes the formatter with a repository for looking up PetType objects.
	 * </p>
	 *
	 * @param pets the PetRepository to use for PetType lookups. Must not be
	 *             {@code null}.
	 *
	 * @throws NullPointerException if {@code pets} is {@code null}.
	 */
	@Autowired
	public PetTypeFormatter(PetRepository pets) {
		this.pets = pets;
	}

	/**
	 * Converts a PetType object to its string representation.
	 *
	 * <p>
	 * Returns the name of the given PetType for display in forms and views.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(1) constant-time operation.
	 * </p>
	 *
	 * @param petType the PetType to convert. Must not be {@code null}.
	 * @param locale the locale for formatting (not used in this implementation).
	 *
	 * @return the string representation (name) of the PetType.
	 *
	 * @throws NullPointerException if {@code petType} is {@code null}.
	 *
	 * @see #parse(String, Locale)
	 */
	@Override
	public String print(PetType petType, Locale locale) {
		return petType.getName();
	}

	/**
	 * Converts a string to a PetType object.
	 *
	 * <p>
	 * Parses the given string and returns the corresponding PetType object by
	 * looking up the name in the repository. Throws {@link ParseException} if
	 * no matching PetType is found.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(n) linear search, where n is the number of
	 * pet types in the database.
	 * </p>
	 *
	 * @param text the string to parse (PetType name). Must not be {@code null}.
	 * @param locale the locale for parsing (not used in this implementation).
	 *
	 * @return the PetType object corresponding to the given name.
	 *
	 * @throws ParseException if no PetType with the given name is found.
	 * @throws NullPointerException if {@code text} is {@code null}.
	 *
	 * @see #print(PetType, Locale)
	 */
	@Override
	public PetType parse(String text, Locale locale) throws ParseException {
		Collection<PetType> findPetTypes = this.pets.findPetTypes();
		for (PetType type : findPetTypes) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
