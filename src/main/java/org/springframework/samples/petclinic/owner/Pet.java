/*
 * Copyright 2002-2018 the original author or authors.
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
 * Pet entity model representing a pet in the PetClinic application.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Pet Management<br>
 * <strong>Purpose:</strong> Represents a pet with type, birth date, owner
 * reference, and associated visits. Serves as the central entity for managing
 * pet data and their relationships with owners and visits.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Entity (Domain Model Layer)<br>
 * This class extends {@link NamedEntity} and represents a pet in the PetClinic
 * application. It manages pet information and maintains relationships with
 * {@link Owner} and {@link Visit} entities.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>JPA {@code @Entity} mapped to the {@code pets} table.</li>
 * <li>Extends {@link NamedEntity} to inherit identifier and name management.</li>
 * <li>Maintains a many-to-one relationship with {@link Owner}.</li>
 * <li>Maintains a many-to-one relationship with {@link PetType}.</li>
 * <li>Maintains a one-to-many relationship with {@link Visit} entities using
 * eager loading.</li>
 * <li>Provides methods for managing the pet's visit collection.</li>
 * <li>Thread-safe: immutable after construction.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Dependencies:</strong>
 * <ul>
 * <li>JPA (javax.persistence): ORM framework for entity mapping.</li>
 * <li>Spring Framework: Utility classes for sorting and date formatting.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Related Configuration:</strong> Database schema defines the
 * {@code pets} table with foreign keys to {@code owners} and {@code types}
 * tables. See {@code db/hsqldb/schema.sql} and {@code db/mysql/schema.sql}.
 * </p>
 *
 * <p>
 * <strong>Usage Example:</strong>
 * <pre>{@code
 * Pet pet = new Pet();
 * pet.setName("Fluffy");
 * pet.setBirthDate(LocalDate.of(2020, 1, 15));
 * pet.setType(petType);
 * pet.setOwner(owner);
 *
 * Visit visit = new Visit();
 * visit.setDate(LocalDate.now());
 * visit.setDescription("Checkup");
 * pet.addVisit(visit);
 *
 * petRepository.save(pet);
 * }</pre>
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @version 1.0
 * @since 1.0
 * @see NamedEntity
 * @see Owner
 * @see PetType
 * @see Visit
 * @see PetRepository
 */
package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.visit.Visit;

/**
 * Simple business object representing a pet.
 *
 * <p>
 * This class extends {@link NamedEntity} and represents a pet in the PetClinic
 * application with type, birth date, owner, and visit information.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Entity (Domain Model Layer)<br>
 * JPA entity representing a pet with relationships to owner and visits.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "pets")
public class Pet extends NamedEntity {

	/**
	 * Birth date of this pet.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Date when the pet was born.<br>
	 * <strong>Format:</strong> {@code yyyy-MM-dd} (ISO 8601).<br>
	 * <strong>Valid Range:</strong> Past dates; typically not in the future.
	 * </p>
	 */
	@Column(name = "birth_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	/**
	 * Type of this pet (e.g., Dog, Cat, Hamster).
	 *
	 * <p>
	 * <strong>Purpose:</strong> Many-to-one relationship with {@link PetType}.<br>
	 * <strong>Constraints:</strong> Foreign key reference to the types table.<br>
	 * <strong>Valid Range:</strong> Non-null PetType reference.
	 * </p>
	 */
	@ManyToOne
	@JoinColumn(name = "type_id")
	private PetType type;

	/**
	 * Owner of this pet.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Many-to-one relationship with {@link Owner}.<br>
	 * <strong>Constraints:</strong> Foreign key reference to the owners table.<br>
	 * <strong>Valid Range:</strong> Non-null Owner reference.
	 * </p>
	 */
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	/**
	 * Set of visits for this pet.
	 *
	 * <p>
	 * <strong>Purpose:</strong> One-to-many relationship with {@link Visit}
	 * entities.<br>
	 * <strong>Cascade:</strong> All operations are cascaded to visits.<br>
	 * <strong>Fetch Strategy:</strong> Eager loading to avoid lazy loading
	 * issues.<br>
	 * <strong>Constraints:</strong> Managed by JPA provider.
	 * </p>
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "petId", fetch = FetchType.EAGER)
	private Set<Visit> visits = new LinkedHashSet<>();

	/**
	 * Sets the birth date of this pet.
	 *
	 * <p>
	 * Assigns a birth date to this pet.
	 * </p>
	 *
	 * @param birthDate the birth date to assign. May be {@code null}.
	 */
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Retrieves the birth date of this pet.
	 *
	 * <p>
	 * Returns the date when this pet was born.
	 * </p>
	 *
	 * @return the pet's birth date, or {@code null} if not set.
	 */
	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	/**
	 * Retrieves the type of this pet.
	 *
	 * <p>
	 * Returns the pet type (e.g., Dog, Cat, Hamster).
	 * </p>
	 *
	 * @return the pet's type, or {@code null} if not set.
	 */
	public PetType getType() {
		return this.type;
	}

	/**
	 * Sets the type of this pet.
	 *
	 * <p>
	 * Assigns a pet type to this pet.
	 * </p>
	 *
	 * @param type the pet type to assign. May be {@code null}.
	 */
	public void setType(PetType type) {
		this.type = type;
	}

	/**
	 * Retrieves the owner of this pet.
	 *
	 * <p>
	 * Returns the owner who owns this pet.
	 * </p>
	 *
	 * @return the pet's owner, or {@code null} if not set.
	 */
	public Owner getOwner() {
		return this.owner;
	}

	/**
	 * Sets the owner of this pet.
	 *
	 * <p>
	 * Assigns an owner to this pet. This method is protected to control the
	 * relationship management through the {@link Owner#addPet(Pet)} method.
	 * </p>
	 *
	 * @param owner the owner to assign. May be {@code null}.
	 */
	protected void setOwner(Owner owner) {
		this.owner = owner;
	}

	/**
	 * Retrieves the internal set of visits for this pet.
	 *
	 * <p>
	 * Returns the mutable set of visits. This method is protected to allow
	 * subclasses and internal operations to access the raw visit collection.
	 * Initializes an empty set if none exists.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(1) constant-time operation.
	 * </p>
	 *
	 * @return the internal set of visits, never {@code null}.
	 *
	 * @see #getVisits()
	 */
	protected Set<Visit> getVisitsInternal() {
		if (this.visits == null) {
			this.visits = new HashSet<>();
		}
		return this.visits;
	}

	/**
	 * Sets the internal set of visits for this pet.
	 *
	 * <p>
	 * Replaces the entire visit collection. This method is protected for internal
	 * use by JPA and subclasses.
	 * </p>
	 *
	 * @param visits the set of visits to assign. May be {@code null}.
	 *
	 * @see #getVisits()
	 */
	protected void setVisitsInternal(Set<Visit> visits) {
		this.visits = visits;
	}

	/**
	 * Retrieves an unmodifiable list of visits for this pet, sorted by date.
	 *
	 * <p>
	 * Returns a sorted, unmodifiable list of the pet's visits. Visits are sorted
	 * by date in descending order (most recent first). This method provides a
	 * read-only view of the visit collection.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(n log n) due to sorting operation, where n
	 * is the number of visits.
	 * </p>
	 *
	 * @return an unmodifiable list of visits sorted by date, never {@code null}.
	 *
	 * @see #addVisit(Visit)
	 */
	public List<Visit> getVisits() {
		List<Visit> sortedVisits = new ArrayList<>(getVisitsInternal());
		PropertyComparator.sort(sortedVisits,
				new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedVisits);
	}

	/**
	 * Adds a visit to this pet's collection.
	 *
	 * <p>
	 * Associates a visit with this pet. The visit's pet identifier is set to
	 * this pet's identifier.
	 * </p>
	 *
	 * <p>
	 * <strong>Side Effects:</strong> Modifies the visit's pet identifier and the
	 * pet's visit collection.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(1) constant-time operation.
	 * </p>
	 *
	 * @param visit the visit to add. Must not be {@code null}.
	 *
	 * @throws NullPointerException if {@code visit} is {@code null}.
	 *
	 * @see #getVisits()
	 */
	public void addVisit(Visit visit) {
		getVisitsInternal().add(visit);
		visit.setPetId(this.getId());
	}

}
