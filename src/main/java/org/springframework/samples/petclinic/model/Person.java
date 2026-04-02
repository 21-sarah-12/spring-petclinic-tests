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
 * Person entity model extending BaseEntity with first and last name properties.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Domain Model<br>
 * <strong>Purpose:</strong> Provides a reusable base class for JPA entities
 * representing persons, such as {@link Owner} and {@link Vet}.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Entity (Domain Model Layer)<br>
 * This class extends {@link BaseEntity} and adds first and last name fields,
 * serving as an intermediate superclass for domain entities representing people.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>JPA {@code @MappedSuperclass} annotation enables inheritance of {@code id},
 * {@code firstName}, and {@code lastName} fields to subclasses.</li>
 * <li>Both {@code firstName} and {@code lastName} are validated with
 * {@code @NotEmpty} constraint.</li>
 * <li>Thread-safe: immutable after construction.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Dependencies:</strong>
 * <ul>
 * <li>JPA (javax.persistence): ORM framework for entity mapping.</li>
 * <li>Bean Validation (javax.validation): Constraint validation for required fields.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Usage Example:</strong>
 * <pre>{@code
 * // Person is not instantiated directly; it is inherited by domain entities
 * Owner owner = new Owner();
 * owner.setFirstName("John");
 * owner.setLastName("Doe");
 * ownerRepository.save(owner);
 * }</pre>
 * </p>
 *
 * @author Ken Krebs
 * @version 1.0
 * @since 1.0
 * @see BaseEntity
 * @see Owner
 * @see Vet
 */
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

/**
 * Simple JavaBean domain object representing a person.
 *
 * <p>
 * This class extends {@link BaseEntity} and adds first and last name properties
 * for representing persons in the PetClinic application.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Entity (Domain Model Layer)<br>
 * Mapped superclass that extends BaseEntity with person-specific fields.
 * </p>
 *
 * @author Ken Krebs
 * @version 1.0
 * @since 1.0
 */
@MappedSuperclass
public class Person extends BaseEntity {

	/**
	 * First name of this person.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Given name of the person.<br>
	 * <strong>Constraints:</strong> Must not be empty (validated with
	 * {@code @NotEmpty}).<br>
	 * <strong>Valid Range:</strong> Non-empty string values.
	 * </p>
	 */
	@Column(name = "first_name")
	@NotEmpty
	private String firstName;

	/**
	 * Last name of this person.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Family name of the person.<br>
	 * <strong>Constraints:</strong> Must not be empty (validated with
	 * {@code @NotEmpty}).<br>
	 * <strong>Valid Range:</strong> Non-empty string values.
	 * </p>
	 */
	@Column(name = "last_name")
	@NotEmpty
	private String lastName;

	/**
	 * Retrieves the first name of this person.
	 *
	 * <p>
	 * Returns the given name assigned to this person.
	 * </p>
	 *
	 * @return the person's first name, or {@code null} if not set.
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets the first name of this person.
	 *
	 * <p>
	 * Assigns a given name to this person. Must not be empty.
	 * </p>
	 *
	 * @param firstName the first name to assign. Must not be empty.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Retrieves the last name of this person.
	 *
	 * <p>
	 * Returns the family name assigned to this person.
	 * </p>
	 *
	 * @return the person's last name, or {@code null} if not set.
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets the last name of this person.
	 *
	 * <p>
	 * Assigns a family name to this person. Must not be empty.
	 * </p>
	 *
	 * @param lastName the last name to assign. Must not be empty.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
