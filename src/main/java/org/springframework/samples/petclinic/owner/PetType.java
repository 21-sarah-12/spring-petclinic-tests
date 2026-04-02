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
 * PetType entity model representing a type of pet (e.g., Dog, Cat, Hamster).
 *
 * <p>
 * <strong>Module:</strong> PetClinic Pet Management<br>
 * <strong>Purpose:</strong> Represents a classification of pets, such as Dog,
 * Cat, or Hamster. Serves as a reference entity for categorizing pets in the
 * PetClinic application.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Entity (Domain Model Layer)<br>
 * This class extends {@link NamedEntity} and represents a pet type in the
 * PetClinic application. It is referenced by {@link Pet} entities through a
 * many-to-one relationship.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>JPA {@code @Entity} mapped to the {@code types} table.</li>
 * <li>Extends {@link NamedEntity} to inherit identifier and name management.</li>
 * <li>Immutable after construction; thread-safe.</li>
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
 * <strong>Related Configuration:</strong> Database schema defines the
 * {@code types} table with a name column. See {@code db/hsqldb/schema.sql}
 * and {@code db/mysql/schema.sql}.
 * </p>
 *
 * <p>
 * <strong>Usage Example:</strong>
 * <pre>{@code
 * PetType dogType = new PetType();
 * dogType.setName("Dog");
 * petTypeRepository.save(dogType);
 *
 * Pet pet = new Pet();
 * pet.setName("Buddy");
 * pet.setType(dogType);
 * petRepository.save(pet);
 * }</pre>
 * </p>
 *
 * @author Juergen Hoeller
 * @version 1.0
 * @since 1.0
 * @see NamedEntity
 * @see Pet
 */
package org.springframework.samples.petclinic.owner;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.petclinic.model.NamedEntity;

/**
 * Simple JavaBean domain object representing a pet type.
 *
 * <p>
 * This class extends {@link NamedEntity} and represents a type of pet
 * (e.g., Dog, Cat, Hamster) in the PetClinic application.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Entity (Domain Model Layer)<br>
 * JPA entity representing a pet type classification.
 * </p>
 *
 * @author Juergen Hoeller
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "types")
public class PetType extends NamedEntity {

}
