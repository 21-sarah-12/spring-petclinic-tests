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
 * Repository interface for Owner domain objects.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Owner Management<br>
 * <strong>Purpose:</strong> Provides data access operations for {@link Owner}
 * entities, including search, retrieval, and persistence operations.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Repository (Data Access Layer)<br>
 * This interface extends Spring Data's {@link Repository} and defines custom
 * query methods for accessing owner data from the database. Method names follow
 * Spring Data naming conventions for automatic query generation.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>Extends {@link org.springframework.data.repository.Repository} for Spring
 * Data support.</li>
 * <li>Provides custom query methods using {@code @Query} annotations.</li>
 * <li>Implements eager loading of related pets using JOIN FETCH to avoid N+1
 * query problems.</li>
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
 * // Injected into a service or controller
 * @Autowired
 * private OwnerRepository ownerRepository;
 *
 * // Find owners by last name
 * Collection<Owner> owners = ownerRepository.findByLastName("Smith");
 *
 * // Find a specific owner by ID
 * Owner owner = ownerRepository.findById(1);
 *
 * // Save a new or updated owner
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
 * @see Owner
 * @see org.springframework.data.repository.Repository
 */
package org.springframework.samples.petclinic.owner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for {@link Owner} domain objects.
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
public interface OwnerRepository extends Repository<Owner, Integer> {

	/**
	 * Retrieves owners from the data store by last name.
	 *
	 * <p>
	 * Returns all owners whose last name starts with the given name (case-sensitive
	 * prefix match). Uses eager loading of pets to avoid N+1 query problems.
	 * </p>
	 *
	 * <p>
	 * <strong>Query Strategy:</strong> Uses {@code LEFT JOIN FETCH} to eagerly
	 * load the owner's pets in a single query, preventing lazy loading issues.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(n) where n is the number of owners in the
	 * database. Single database query with eager loading.
	 * </p>
	 *
	 * @param lastName the last name prefix to search for. Must not be
	 *                 {@code null}.
	 *
	 * @return a collection of matching owners (or an empty collection if none
	 *         found). Never {@code null}.
	 *
	 * @throws org.springframework.dao.DataAccessException if a database error
	 *                                                     occurs.
	 *
	 * @see #findById(Integer)
	 */
	@Query("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName LIKE :lastName%")
	@Transactional(readOnly = true)
	Collection<Owner> findByLastName(@Param("lastName") String lastName);

	/**
	 * Retrieves an owner from the data store by identifier.
	 *
	 * <p>
	 * Returns the owner with the given identifier, or {@code null} if not found.
	 * Uses eager loading of pets to avoid N+1 query problems.
	 * </p>
	 *
	 * <p>
	 * <strong>Query Strategy:</strong> Uses {@code LEFT JOIN FETCH} to eagerly
	 * load the owner's pets in a single query.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(1) constant-time lookup by primary key.
	 * Single database query with eager loading.
	 * </p>
	 *
	 * @param id the owner's identifier. Must not be {@code null}.
	 *
	 * @return the owner with the given identifier, or {@code null} if not found.
	 *
	 * @throws org.springframework.dao.DataAccessException if a database error
	 *                                                     occurs.
	 *
	 * @see #findByLastName(String)
	 */
	@Query("SELECT owner FROM Owner owner left join fetch owner.pets WHERE owner.id =:id")
	@Transactional(readOnly = true)
	Owner findById(@Param("id") Integer id);

	/**
	 * Saves an owner to the data store.
	 *
	 * <p>
	 * Persists a new owner or updates an existing owner. If the owner's identifier
	 * is {@code null}, a new owner is inserted; otherwise, the existing owner is
	 * updated.
	 * </p>
	 *
	 * <p>
	 * <strong>Side Effects:</strong> Modifies the database and may update the
	 * owner's identifier if it was {@code null}.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(1) constant-time operation (excluding
	 * database I/O).
	 * </p>
	 *
	 * @param owner the owner to save. Must not be {@code null}.
	 *
	 * @throws org.springframework.dao.DataAccessException if a database error
	 *                                                     occurs.
	 * @throws IllegalArgumentException if {@code owner} is {@code null}.
	 *
	 * @see Owner#isNew()
	 */
	void save(Owner owner);


}
