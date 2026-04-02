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
 * Named entity model extending BaseEntity with a name property.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Domain Model<br>
 * <strong>Purpose:</strong> Provides a reusable base class for JPA entities that
 * require a name attribute, such as {@link PetType} and {@link Specialty}.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Entity (Domain Model Layer)<br>
 * This class extends {@link BaseEntity} and adds a {@code name} field, serving as
 * an intermediate superclass for domain entities that need both identifier and
 * name management.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>JPA {@code @MappedSuperclass} annotation enables inheritance of both
 * {@code id} and {@code name} fields to subclasses.</li>
 * <li>Provides a {@code toString()} implementation that returns the entity's name.</li>
 * <li>Thread-safe: immutable after construction.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Dependencies:</strong>
 * <ul>
 * <li>JPA (javax.persistence): ORM framework for entity mapping.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Usage Example:</strong>
 * <pre>{@code
 * // NamedEntity is not instantiated directly; it is inherited by domain entities
 * PetType petType = new PetType();
 * petType.setName("Dog");
 * petTypeRepository.save(petType);
 * assert petType.toString().equals("Dog"); // toString returns the name
 * }</pre>
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @version 1.0
 * @since 1.0
 * @see BaseEntity
 * @see PetType
 * @see Specialty
 */
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


/**
 * Simple JavaBean domain object adds a name property to {@link BaseEntity}.
 *
 * <p>
 * Used as a base class for objects needing both identifier and name properties.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Entity (Domain Model Layer)<br>
 * Mapped superclass that extends BaseEntity with a name field.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @version 1.0
 * @since 1.0
 */
@MappedSuperclass
public class NamedEntity extends BaseEntity {

	/**
	 * Name of this entity.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Descriptive name for the entity (e.g., "Dog", "Cat",
	 * "Dentistry").<br>
	 * <strong>Valid Range:</strong> Non-null string values.<br>
	 * <strong>Constraints:</strong> Typically required for business logic.
	 * </p>
	 */
	@Column(name = "name")
	private String name;

	/**
	 * Retrieves the name of this entity.
	 *
	 * <p>
	 * Returns the descriptive name assigned to this entity.
	 * </p>
	 *
	 * @return the entity's name, or {@code null} if not set.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of this entity.
	 *
	 * <p>
	 * Assigns a descriptive name to this entity.
	 * </p>
	 *
	 * @param name the name to assign. May be {@code null}.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the string representation of this entity.
	 *
	 * <p>
	 * Returns the entity's name as its string representation.
	 * </p>
	 *
	 * @return the entity's name.
	 */
	@Override
	public String toString() {
		return this.getName();
	}

}
