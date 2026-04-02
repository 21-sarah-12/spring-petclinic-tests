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
 * Visit entity model representing a veterinary visit for a pet.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Visit Management<br>
 * <strong>Purpose:</strong> Represents a veterinary visit record for a pet,
 * including the visit date and description of the visit.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Entity (Domain Model Layer)<br>
 * This class extends {@link BaseEntity} and represents a visit in the PetClinic
 * application. It maintains a reference to the pet being visited through the
 * {@code petId} field.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>JPA {@code @Entity} mapped to the {@code visits} table.</li>
 * <li>Extends {@link BaseEntity} to inherit identifier management.</li>
 * <li>Stores visit date and description.</li>
 * <li>Maintains a reference to the pet through {@code petId}.</li>
 * <li>Initializes with the current date by default.</li>
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
 * <li>Spring Framework: Date formatting support.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Related Configuration:</strong> Database schema defines the
 * {@code visits} table with columns for visit_date, description, and pet_id.
 * See {@code db/hsqldb/schema.sql} and {@code db/mysql/schema.sql}.
 * </p>
 *
 * <p>
 * <strong>Usage Example:</strong>
 * <pre>{@code
 * Visit visit = new Visit();
 * // Date is automatically set to today
 * visit.setDescription("Annual checkup");
 * visit.setPetId(petId);
 * visitRepository.save(visit);
 * }</pre>
 * </p>
 *
 * @author Ken Krebs
 * @author Dave Syer
 * @version 1.0
 * @since 1.0
 * @see BaseEntity
 * @see Pet
 * @see VisitRepository
 */
package org.springframework.samples.petclinic.visit;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;

/**
 * Simple JavaBean domain object representing a visit.
 *
 * <p>
 * This class extends {@link BaseEntity} and represents a veterinary visit
 * record for a pet in the PetClinic application.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Entity (Domain Model Layer)<br>
 * JPA entity representing a veterinary visit with date and description.
 * </p>
 *
 * @author Ken Krebs
 * @author Dave Syer
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {

	/**
	 * Date of this visit.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Date when the veterinary visit occurred.<br>
	 * <strong>Format:</strong> {@code yyyy-MM-dd} (ISO 8601).<br>
	 * <strong>Valid Range:</strong> Past dates; typically not in the future.
	 * </p>
	 */
	@Column(name = "visit_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	/**
	 * Description of this visit.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Details about the veterinary visit (e.g.,
	 * "Annual checkup", "Vaccination").<br>
	 * <strong>Constraints:</strong> Must not be empty (validated with
	 * {@code @NotEmpty}).<br>
	 * <strong>Valid Range:</strong> Non-empty string values.
	 * </p>
	 */
	@NotEmpty
	@Column(name = "description")
	private String description;

	/**
	 * Identifier of the pet being visited.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Foreign key reference to the pet entity.<br>
	 * <strong>Constraints:</strong> Must reference a valid pet identifier.<br>
	 * <strong>Valid Range:</strong> Positive integers.
	 * </p>
	 */
	@Column(name = "pet_id")
	private Integer petId;

	/**
	 * Constructs a new Visit with the current date.
	 *
	 * <p>
	 * Initializes a new visit with today's date as the visit date.
	 * </p>
	 */
	public Visit() {
		this.date = LocalDate.now();
	}

	/**
	 * Retrieves the date of this visit.
	 *
	 * <p>
	 * Returns the date when the veterinary visit occurred.
	 * </p>
	 *
	 * @return the visit date, or {@code null} if not set.
	 */
	public LocalDate getDate() {
		return this.date;
	}

	/**
	 * Sets the date of this visit.
	 *
	 * <p>
	 * Assigns a date to this visit.
	 * </p>
	 *
	 * @param date the visit date to assign. May be {@code null}.
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Retrieves the description of this visit.
	 *
	 * <p>
	 * Returns details about the veterinary visit.
	 * </p>
	 *
	 * @return the visit description, or {@code null} if not set.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the description of this visit.
	 *
	 * <p>
	 * Assigns a description to this visit. Must not be empty.
	 * </p>
	 *
	 * @param description the visit description to assign. Must not be empty.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Retrieves the identifier of the pet being visited.
	 *
	 * <p>
	 * Returns the pet identifier associated with this visit.
	 * </p>
	 *
	 * @return the pet identifier, or {@code null} if not set.
	 */
	public Integer getPetId() {
		return this.petId;
	}

	/**
	 * Sets the identifier of the pet being visited.
	 *
	 * <p>
	 * Assigns a pet identifier to this visit.
	 * </p>
	 *
	 * @param petId the pet identifier to assign. May be {@code null}.
	 */
	public void setPetId(Integer petId) {
		this.petId = petId;
	}

}
