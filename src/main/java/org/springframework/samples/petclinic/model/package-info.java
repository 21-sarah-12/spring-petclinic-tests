/**
 * Domain model layer for PetClinic application.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Domain Model<br>
 * <strong>Purpose:</strong> Contains the core domain entities and base classes
 * for the PetClinic application, providing a foundation for all JPA-managed
 * entities across the application.
 * </p>
 *
 * <p>
 * <strong>Key Classes:</strong>
 * <ul>
 * <li>{@link org.springframework.samples.petclinic.model.BaseEntity}: Root
 * superclass for all entities, providing identifier management and lifecycle
 * detection.</li>
 * <li>{@link org.springframework.samples.petclinic.model.NamedEntity}: Extends
 * BaseEntity with a name property for entities like PetType and Specialty.</li>
 * <li>{@link org.springframework.samples.petclinic.model.Person}: Extends
 * BaseEntity with first and last name properties for entities like Owner and
 * Vet.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Domain Model Layer<br>
 * This package contains the core domain entities that represent the business
 * concepts in the PetClinic application. These entities are mapped to database
 * tables using JPA annotations and serve as the foundation for the repository
 * and service layers.
 * </p>
 *
 * <p>
 * <strong>Design Patterns:</strong>
 * <ul>
 * <li>Inheritance Hierarchy: Uses JPA mapped superclasses to share common
 * properties across multiple entity types.</li>
 * <li>Entity Pattern: Implements the Entity pattern for domain-driven design.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Dependencies:</strong>
 * <ul>
 * <li>JPA (javax.persistence): ORM framework for entity mapping and lifecycle
 * management.</li>
 * <li>Bean Validation (javax.validation): Constraint validation for entity
 * properties.</li>
 * </ul>
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @version 1.0
 * @since 1.0
 */
package org.springframework.samples.petclinic.model;
