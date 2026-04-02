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
 * Base entity model providing common identifier management for all domain objects.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Domain Model<br>
 * <strong>Purpose:</strong> Provides a reusable base class for JPA entities with
 * auto-generated primary key support and entity lifecycle detection.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Entity (Domain Model Layer)<br>
 * This class serves as the root superclass for all JPA-managed domain entities in the
 * PetClinic application. It encapsulates the common identifier pattern used across
 * the domain model hierarchy, including {@link Person}, {@link Owner}, {@link Pet},
 * and {@link Vet}.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>JPA {@code @MappedSuperclass} annotation enables inheritance of the {@code id}
 * field to all subclasses without requiring a separate table.</li>
 * <li>Auto-generated identity strategy using database-native identity columns
 * ({@code IDENTITY} strategy).</li>
 * <li>Implements {@link Serializable} for distributed caching and session
 * persistence support.</li>
 * <li>Thread-safe: immutable after construction; identity is set by JPA provider.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Dependencies:</strong>
 * <ul>
 * <li>JPA (javax.persistence): Core ORM framework for entity mapping and lifecycle
 * management.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Related Configuration:</strong> Database schema defines the primary key
 * column with auto-increment enabled. See {@code db/hsqldb/schema.sql} and
 * {@code db/mysql/schema.sql} for table definitions.
 * </p>
 *
 * <p>
 * <strong>Usage Example:</strong>
 * <pre>{@code
 * // BaseEntity is not instantiated directly; it is inherited by domain entities
 * Owner owner = new Owner();
 * owner.setFirstName("John");
 * owner.setLastName("Doe");
 * // After persistence, JPA auto-generates the id
 * ownerRepository.save(owner);
 * assert !owner.isNew(); // id is now set
 * }</pre>
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @version 1.0
 * @since 1.0
 * @see Person
 * @see Owner
 * @see Pet
 * @see Vet
 */
package org.springframework.samples.petclinic.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Simple JavaBean domain object with an id property.
 *
 * <p>
 * This class serves as the base class for all JPA-managed domain entities in the
 * PetClinic application. It provides common identifier management and entity
 * lifecycle detection through the {@link #isNew()} method.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Entity (Domain Model Layer)<br>
 * Mapped superclass that defines the primary key strategy for all subclasses.
 * </p>
 *
 * <p>
 * <strong>Key Collaborators:</strong>
 * <ul>
 * <li>JPA Provider (Hibernate): Manages entity lifecycle, identity generation, and
 * persistence.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Thread-Safety:</strong> This class is thread-safe after construction.
 * The {@code id} field is managed exclusively by the JPA provider and should not
 * be modified directly in multi-threaded contexts.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @version 1.0
 * @since 1.0
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	/**
	 * Unique identifier for this entity.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Primary key column in the database.<br>
	 * <strong>Generation Strategy:</strong> {@code IDENTITY} (database auto-increment).<br>
	 * <strong>Valid Range:</strong> Positive integers; {@code null} indicates a
	 * transient (unsaved) entity.<br>
	 * <strong>Constraints:</strong> Must not be manually set; JPA provider manages
	 * this field exclusively.
	 * </p>
	 *
	 * @see #isNew()
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * Retrieves the unique identifier for this entity.
	 *
	 * <p>
	 * Returns the primary key value assigned by the database. A {@code null} return
	 * value indicates that this entity is transient (not yet persisted).
	 * </p>
	 *
	 * @return the entity's unique identifier, or {@code null} if the entity is
	 *         transient (not yet persisted to the database).
	 *
	 * @see #isNew()
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the unique identifier for this entity.
	 *
	 * <p>
	 * <strong>Warning:</strong> This method should not be called directly in
	 * application code. The JPA provider manages the {@code id} field exclusively
	 * during entity lifecycle operations. Manual invocation may result in
	 * inconsistent entity state or database constraint violations.
	 * </p>
	 *
	 * @param id the unique identifier to assign. May be {@code null} to mark the
	 *           entity as transient.
	 *
	 * @see #isNew()
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Determines whether this entity is new (transient) or persistent.
	 *
	 * <p>
	 * An entity is considered "new" if its identifier has not been assigned by the
	 * database. This method is commonly used to distinguish between insert and update
	 * operations during entity lifecycle management.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(1) constant-time operation; performs only a
	 * null check on the {@code id} field.
	 * </p>
	 *
	 * @return {@code true} if the entity's identifier is {@code null} (transient,
	 *         not yet persisted); {@code false} if the entity has been assigned an
	 *         identifier (persistent, already saved to the database).
	 *
	 * <pre>{@code
	 * Owner owner = new Owner();
	 * assert owner.isNew(); // true, id is null
	 *
	 * ownerRepository.save(owner);
	 * assert !owner.isNew(); // false, id is now assigned
	 * }</pre>
	 *
	 * @see #getId()
	 */
	public boolean isNew() {
		return this.id == null;
	}

}
