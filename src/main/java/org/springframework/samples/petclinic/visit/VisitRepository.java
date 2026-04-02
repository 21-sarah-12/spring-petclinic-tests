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
 * Repository interface for Visit domain objects.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Visit Management<br>
 * <strong>Purpose:</strong> Provides data access operations for {@link Visit}
 * entities, including retrieval and persistence operations.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Repository (Data Access Layer)<br>
 * This interface extends Spring Data's {@link Repository} and defines custom
 * query methods for accessing visit data from the database. Method names follow
 * Spring Data naming conventions for automatic query generation.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>Extends {@link org.springframework.data.repository.Repository} for Spring
 * Data support.</li>
 * <li>Provides custom query methods for finding visits by pet identifier.</li>
 * <li>Supports persistence operations for visit entities.</li>
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
 * private VisitRepository visitRepository;
 *
 * // Find visits for a specific pet
 * List<Visit> visits = visitRepository.findByPetId(petId);
 *
 * // Save a new visit
 * visitRepository.save(visit);
 * }</pre>
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @version 1.0
 * @since 1.0
 * @see Visit
 * @see org.springframework.data.repository.Repository
 */
package org.springframework.samples.petclinic.visit;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.BaseEntity;

/**
 * Repository class for {@link Visit} domain objects.
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
public interface VisitRepository extends Repository<Visit, Integer> {

	/**
	 * Saves a visit to the data store.
	 *
	 * <p>
	 * Persists a new visit or updates an existing visit. If the visit's identifier
	 * is {@code null}, a new visit is inserted; otherwise, the existing visit is
	 * updated.
	 * </p>
	 *
	 * <p>
	 * <strong>Side Effects:</strong> Modifies the database and may update the
	 * visit's identifier if it was {@code null}.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(1) constant-time operation (excluding
	 * database I/O).
	 * </p>
	 *
	 * @param visit the visit to save. Must not be {@code null}.
	 *
	 * @throws DataAccessException if a database error occurs.
	 * @throws IllegalArgumentException if {@code visit} is {@code null}.
	 *
	 * @see BaseEntity#isNew
	 */
	void save(Visit visit) throws DataAccessException;

	/**
	 * Retrieves visits for a specific pet.
	 *
	 * <p>
	 * Returns a list of all visits associated with the given pet identifier.
	 * </p>
	 *
	 * <p>
	 * <strong>Performance:</strong> O(n) where n is the number of visits for the
	 * pet. Single database query.
	 * </p>
	 *
	 * @param petId the pet identifier to search for. Must not be {@code null}.
	 *
	 * @return a list of visits for the given pet. Never {@code null}.
	 *
	 * @throws DataAccessException if a database error occurs.
	 */
	List<Visit> findByPetId(Integer petId);

}
