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
 * Validator for Pet form submissions.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Pet Management<br>
 * <strong>Purpose:</strong> Provides validation logic for {@link Pet} objects
 * during form submission. Validates required fields such as name, type, and
 * birth date.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Validator (Web Layer)<br>
 * This class implements Spring's {@link org.springframework.validation.Validator}
 * interface to provide custom validation logic for Pet objects in web forms.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>Implements {@link org.springframework.validation.Validator} for Spring
 * integration.</li>
 * <li>Validates pet name (must not be empty).</li>
 * <li>Validates pet type (must not be null for new pets).</li>
 * <li>Validates birth date (must not be null).</li>
 * <li>Uses programmatic validation instead of Bean Validation annotations for
 * more control.</li>
 * <li>Thread-safe: stateless validator.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Dependencies:</strong>
 * <ul>
 * <li>Spring Framework: Validator interface and validation utilities.</li>
 * <li>Pet: Domain entity being validated.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Validation Rules:</strong>
 * <ul>
 * <li><strong>Name:</strong> Must not be empty. Error code: "required".</li>
 * <li><strong>Type:</strong> Must not be null for new pets. Error code:
 * "required".</li>
 * <li><strong>Birth Date:</strong> Must not be null. Error code: "required".</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Usage Example:</strong>
 * <pre>{@code
 * // Registered in a controller's InitBinder
 * @InitBinder("pet")
 * public void initPetBinder(WebDataBinder dataBinder) {
 *     dataBinder.setValidator(new PetValidator());
 * }
 *
 * // Automatically invoked during form submission
 * @PostMapping("/owners/{ownerId}/pets/new")
 * public String processCreationForm(@Valid Pet pet, BindingResult result) {
 *     if (result.hasErrors()) {
 *         return "pets/createOrUpdatePetForm";
 *     }
 *     // Process valid pet
 * }
 * }</pre>
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @version 1.0
 * @since 1.0
 * @see Pet
 * @see org.springframework.validation.Validator
 */
package org.springframework.samples.petclinic.owner;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * {@link Validator} for {@link Pet} forms.
 *
 * <p>
 * We're not using Bean Validation annotations here because it is easier to
 * define such validation rule in Java.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @version 1.0
 * @since 1.0
 */
public class PetValidator implements Validator {

	/**
	 * Error code for required field validation failures.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Used to identify validation errors for required
	 * fields.
	 * </p>
	 */
	private static final String REQUIRED = "required";

	/**
	 * Validates the given Pet object.
	 *
	 * <p>
	 * Performs validation of the pet's name, type, and birth date. Rejects
	 * validation errors using the provided {@link Errors} object.
	 * </p>
	 *
	 * <p>
	 * <strong>Validation Rules:</strong>
	 * <ul>
	 * <li>Name must not be empty.</li>
	 * <li>Type must not be null for new pets.</li>
	 * <li>Birth date must not be null.</li>
	 * </ul>
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(1) constant-time operation.
	 * </p>
	 *
	 * @param obj the object to validate. Must be a {@link Pet} instance.
	 * @param errors the errors object to collect validation errors. Must not be
	 *               {@code null}.
	 *
	 * @throws ClassCastException if {@code obj} is not a {@link Pet} instance.
	 *
	 * @see #supports(Class)
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		Pet pet = (Pet) obj;
		String name = pet.getName();
		// name validation
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}

		// type validation
		if (pet.isNew() && pet.getType() == null) {
			errors.rejectValue("type", REQUIRED, REQUIRED);
		}

		// birth date validation
		if (pet.getBirthDate() == null) {
			errors.rejectValue("birthDate", REQUIRED, REQUIRED);
		}
	}

	/**
	 * Determines whether this validator supports the given class.
	 *
	 * <p>
	 * Returns {@code true} if the given class is {@link Pet} or a subclass of
	 * {@link Pet}.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(1) constant-time operation.
	 * </p>
	 *
	 * @param clazz the class to check. Must not be {@code null}.
	 *
	 * @return {@code true} if this validator supports the given class;
	 *         {@code false} otherwise.
	 *
	 * @see #validate(Object, Errors)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Pet.class.isAssignableFrom(clazz);
	}


}
