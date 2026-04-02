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
 * Owner entity model representing a pet owner in the PetClinic application.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Owner Management<br>
 * <strong>Purpose:</strong> Represents a pet owner with contact information and
 * associated pets. Serves as the central entity for managing owner data and their
 * relationships with pets.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Entity (Domain Model Layer)<br>
 * This class extends {@link Person} and represents a pet owner in the PetClinic
 * application. It manages owner contact information and maintains a one-to-many
 * relationship with {@link Pet} entities.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>JPA {@code @Entity} mapped to the {@code owners} table.</li>
 * <li>Extends {@link Person} to inherit identifier and name management.</li>
 * <li>Maintains a one-to-many relationship with {@link Pet} entities using
 * cascade operations.</li>
 * <li>Provides methods for managing the owner's pet collection.</li>
 * <li>Thread-safe: immutable after construction.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Dependencies:</strong>
 * <ul>
 * <li>JPA (javax.persistence): ORM framework for entity mapping.</li>
 * <li>Bean Validation (javax.validation): Constraint validation for required
 * fields.</li>
 * <li>Spring Framework: Utility classes for sorting and string representation.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Related Configuration:</strong> Database schema defines the
 * {@code owners} table with columns for address, city, and telephone. See
 * {@code db/hsqldb/schema.sql} and {@code db/mysql/schema.sql}.
 * </p>
 *
 * <p>
 * <strong>Usage Example:</strong>
 * <pre>{@code
 * Owner owner = new Owner();
 * owner.setFirstName("John");
 * owner.setLastName("Doe");
 * owner.setAddress("123 Main St");
 * owner.setCity("Springfield");
 * owner.setTelephone("5551234567");
 *
 * Pet pet = new Pet();
 * pet.setName("Fluffy");
 * owner.addPet(pet);
 *
 * ownerRepository.save(owner);
 * }</pre>
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @version 1.0
 * @since 1.0
 * @see Person
 * @see Pet
 * @see OwnerRepository
 */
package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.model.Person;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * <p>
 * This class extends {@link Person} and adds contact information and pet
 * management capabilities for representing pet owners in the PetClinic
 * application.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Entity (Domain Model Layer)<br>
 * JPA entity representing a pet owner with contact details and pet relationships.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "owners")
public class Owner extends Person {

	/**
	 * Street address of this owner.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Owner's residential or business address.<br>
	 * <strong>Constraints:</strong> Must not be empty (validated with
	 * {@code @NotEmpty}).<br>
	 * <strong>Valid Range:</strong> Non-empty string values.
	 * </p>
	 */
	@Column(name = "address")
	@NotEmpty
	private String address;

	/**
	 * City where this owner resides.
	 *
	 * <p>
	 * <strong>Purpose:</strong> City name for the owner's address.<br>
	 * <strong>Constraints:</strong> Must not be empty (validated with
	 * {@code @NotEmpty}).<br>
	 * <strong>Valid Range:</strong> Non-empty string values.
	 * </p>
	 */
	@Column(name = "city")
	@NotEmpty
	private String city;

	/**
	 * Telephone number of this owner.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Contact phone number for the owner.<br>
	 * <strong>Constraints:</strong> Must not be empty and contain at most 10
	 * digits (validated with {@code @NotEmpty} and {@code @Digits}).<br>
	 * <strong>Valid Range:</strong> Numeric strings with up to 10 digits.
	 * </p>
	 */
	@Column(name = "telephone")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String telephone;

	/**
	 * Set of pets owned by this owner.
	 *
	 * <p>
	 * <strong>Purpose:</strong> One-to-many relationship with {@link Pet}
	 * entities.<br>
	 * <strong>Cascade:</strong> All operations (persist, merge, remove) are
	 * cascaded to pets.<br>
	 * <strong>Constraints:</strong> Managed by JPA provider.
	 * </p>
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private Set<Pet> pets;

	/**
	 * Retrieves the street address of this owner.
	 *
	 * <p>
	 * Returns the owner's residential or business address.
	 * </p>
	 *
	 * @return the owner's street address, or {@code null} if not set.
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Sets the street address of this owner.
	 *
	 * <p>
	 * Assigns a residential or business address to this owner. Must not be empty.
	 * </p>
	 *
	 * @param address the street address to assign. Must not be empty.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Retrieves the city where this owner resides.
	 *
	 * <p>
	 * Returns the city name for the owner's address.
	 * </p>
	 *
	 * @return the owner's city, or {@code null} if not set.
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * Sets the city where this owner resides.
	 *
	 * <p>
	 * Assigns a city name to this owner's address. Must not be empty.
	 * </p>
	 *
	 * @param city the city name to assign. Must not be empty.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Retrieves the telephone number of this owner.
	 *
	 * <p>
	 * Returns the contact phone number for this owner.
	 * </p>
	 *
	 * @return the owner's telephone number, or {@code null} if not set.
	 */
	public String getTelephone() {
		return this.telephone;
	}

	/**
	 * Sets the telephone number of this owner.
	 *
	 * <p>
	 * Assigns a contact phone number to this owner. Must not be empty and contain
	 * at most 10 digits.
	 * </p>
	 *
	 * @param telephone the telephone number to assign. Must not be empty and
	 *                  contain at most 10 digits.
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * Retrieves the internal set of pets owned by this owner.
	 *
	 * <p>
	 * Returns the mutable set of pets. This method is protected to allow
	 * subclasses and internal operations to access the raw pet collection.
	 * Initializes an empty set if none exists.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(1) constant-time operation.
	 * </p>
	 *
	 * @return the internal set of pets, never {@code null}.
	 *
	 * @see #getPets()
	 */
	protected Set<Pet> getPetsInternal() {
		if (this.pets == null) {
			this.pets = new HashSet<>();
		}
		return this.pets;
	}

	/**
	 * Sets the internal set of pets owned by this owner.
	 *
	 * <p>
	 * Replaces the entire pet collection. This method is protected for internal
	 * use by JPA and subclasses.
	 * </p>
	 *
	 * @param pets the set of pets to assign. May be {@code null}.
	 *
	 * @see #getPets()
	 */
	protected void setPetsInternal(Set<Pet> pets) {
		this.pets = pets;
	}

	/**
	 * Retrieves an unmodifiable list of pets owned by this owner, sorted by name.
	 *
	 * <p>
	 * Returns a sorted, unmodifiable list of the owner's pets. Pets are sorted
	 * alphabetically by name in ascending order. This method provides a read-only
	 * view of the pet collection.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(n log n) due to sorting operation, where n
	 * is the number of pets.
	 * </p>
	 *
	 * @return an unmodifiable list of pets sorted by name, never {@code null}.
	 *
	 * @see #addPet(Pet)
	 * @see #getPet(String)
	 */
	public List<Pet> getPets() {
		List<Pet> sortedPets = new ArrayList<>(getPetsInternal());
		PropertyComparator.sort(sortedPets,
				new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedPets);
	}

	/**
	 * Adds a pet to this owner's collection.
	 *
	 * <p>
	 * Associates a pet with this owner. If the pet is new (not yet persisted),
	 * it is added to the internal collection. The pet's owner reference is set
	 * to this owner.
	 * </p>
	 *
	 * <p>
	 * <strong>Side Effects:</strong> Modifies the pet's owner reference and the
	 * owner's pet collection.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(1) constant-time operation.
	 * </p>
	 *
	 * @param pet the pet to add. Must not be {@code null}.
	 *
	 * @throws NullPointerException if {@code pet} is {@code null}.
	 *
	 * @see #getPets()
	 * @see #getPet(String)
	 */
	public void addPet(Pet pet) {
		if (pet.isNew()) {
			getPetsInternal().add(pet);
		}
		pet.setOwner(this);
	}

	/**
	 * Retrieves a pet by name, or {@code null} if not found.
	 *
	 * <p>
	 * Searches for a pet with the given name (case-insensitive) in this owner's
	 * pet collection. Returns {@code null} if no matching pet is found.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(n) linear search, where n is the number of
	 * pets.
	 * </p>
	 *
	 * @param name the name of the pet to search for. Must not be {@code null}.
	 *
	 * @return the pet with the given name, or {@code null} if not found.
	 *
	 * @throws NullPointerException if {@code name} is {@code null}.
	 *
	 * @see #getPet(String, boolean)
	 * @see #getPets()
	 */
	public Pet getPet(String name) {
		return getPet(name, false);
	}

	/**
	 * Retrieves a pet by name with optional filtering of new pets.
	 *
	 * <p>
	 * Searches for a pet with the given name (case-insensitive) in this owner's
	 * pet collection. If {@code ignoreNew} is {@code true}, only persisted pets
	 * are considered. Returns {@code null} if no matching pet is found.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(n) linear search, where n is the number of
	 * pets.
	 * </p>
	 *
	 * @param name the name of the pet to search for. Must not be {@code null}.
	 * @param ignoreNew if {@code true}, only persisted pets are considered;
	 *                  if {@code false}, all pets are considered.
	 *
	 * @return the pet with the given name, or {@code null} if not found.
	 *
	 * @throws NullPointerException if {@code name} is {@code null}.
	 *
	 * @see #getPet(String)
	 * @see #getPets()
	 */
	public Pet getPet(String name, boolean ignoreNew) {
		name = name.toLowerCase();
		for (Pet pet : getPetsInternal()) {
			if (!ignoreNew || !pet.isNew()) {
				String compName = pet.getName();
				compName = compName.toLowerCase();
				if (compName.equals(name)) {
					return pet;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the string representation of this owner.
	 *
	 * <p>
	 * Returns a detailed string representation including the owner's identifier,
	 * new status, first name, last name, address, city, and telephone number.
	 * </p>
	 *
	 * @return a string representation of this owner.
	 */
	@Override
	public String toString() {
		return new ToStringCreator(this)

				.append("id", this.getId()).append("new", this.isNew())
				.append("lastName", this.getLastName())
				.append("firstName", this.getFirstName()).append("address", this.address)
				.append("city", this.city).append("telephone", this.telephone).toString();
	}
}
