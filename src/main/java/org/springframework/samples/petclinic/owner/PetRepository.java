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
 * Repository interface for Pet domain objects.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Pet Management<br>
 * <strong>Purpose:</strong> Provides data access operations for {@link Pet}
 * entities and {@link PetType} lookups, including retrieval and persistence
 * operations.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Repository (Data Access Layer)<br>
 * This interface extends Spring Data's {@link Repository} and defines custom
 * query methods for accessing pet data from the database. Method names follow
 * Spring Data naming conventions for automatic query generation.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>Extends {@link org.springframework.data.repository.Repository} for Spring
 * Data support.</li>
 * <li>Provides custom query methods using {@code @Query} annotations.</li>
 * <li>Supports read-only transactions for query methods.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Dependencies:</strong>
 * <ul>
 * <li>Spring Data JPA: Repository abstraction and query generation.</li>
 * <li>JPA (javax.persistence): ORM framework.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Usage Example:</strong>
 * <pre>{@code
 * // Injected into a controller
 * @Autowired
 * private PetRepository petRepository;
 *
 * // Find all pet types
 * List<PetType> types = petRepository.findPetTypes();
 *
 * // Find a specific pet by ID
 * Pet pet = petRepository.findById(1);
 *
 * // Save a new or updated pet
 * petRepository.save(pet);
 * }</pre>
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @version 1.0
 * @since 1.0
 * @see Pet
 * @see PetType
 * @see org.springframework.data.repository.Repository
 */
package org.springframework.samples.petclinic.owner;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for {@link Pet} domain objects.
 *
 * <p>
 * All method names are compliant with Spring Data naming conventions so this
 * interface can easily be extended for Spring Data. See
 * <a href="http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation">
 * Spring Data JPA Query Methods</a> for more details.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @version 1.0
 * @since 1.0
 */
public interface PetRepository extends Repository<Pet, Integer> {

	/**
	 * Retrieves all pet types from the data store.
	 *
	 * <p>
	 * Returns a list of all available pet types, sorted by name in ascending
	 * order.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(n) where n is the number of pet types in
	 * the database. Single database query.
	 * </p>
	 *
	 * @return a list of all pet types, sorted by name. Never {@code null}.
	 *
	 * @throws org.springframework.dao.DataAccessException if a database error
	 *                                                     occurs.
	 */
	@Query("SELECT ptype FROM PetType ptype ORDER BY ptype.name")
	@Transactional(readOnly = true)
	List<PetType> findPetTypes();

	/**
	 * Retrieves a pet from the data store by identifier.
	 *
	 * <p>
	 * Returns the pet with the given identifier, or {@code null} if not found.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(1) constant-time lookup by primary key.
	 * Single database query.
	 * </p>
	 *
	 * @param id the pet's identifier. Must not be {@code null}.
	 *
	 * @return the pet with the given identifier, or {@code null} if not found.
	 *
	 * @throws org.springframework.dao.DataAccessException if a database error
	 *                                                     occurs.
	 */
	@Transactional(readOnly = true)
	Pet findById(Integer id);

	/**
	 * Saves a pet to the data store.
	 *
	 * <p>
	 * Persists a new pet or updates an existing pet. If the pet's identifier is
	 * {@code null}, a new pet is inserted; otherwise, the existing pet is
	 * updated.
	 * </p>
	 *
	 * <p>
	 * <strong>Side Effects:</strong> Modifies the database and may update the
	 * pet's identifier if it was {@code null}.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(1) constant-time operation (excluding
	 * database I/O).
	 * </p>
	 *
	 * @param pet the pet to save. Must not be {@code null}.
	 *
	 * @throws org.springframework.dao.DataAccessException if a database error
	 *                                                     occurs.
	 * @throws IllegalArgumentException if {@code pet} is {@code null}.
	 *
	 * @see Pet#isNew()
	 */
	void save(Pet pet);

}
