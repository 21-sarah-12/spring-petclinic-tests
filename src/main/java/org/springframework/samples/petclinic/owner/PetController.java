/*
 * Copyright 2012-2018 the original author or authors.
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
 * Controller for managing pet-related HTTP requests.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Pet Management<br>
 * <strong>Purpose:</strong> Handles HTTP requests for pet operations including
 * creation, updates, and display of pet details within the context of an owner.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Controller (Web Layer)<br>
 * This class handles HTTP requests and coordinates with the repository layer
 * to manage pet data. It provides endpoints for pet CRUD operations within the
 * owner context.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>Annotated with {@code @Controller} for Spring MVC integration.</li>
 * <li>Mapped to {@code /owners/{ownerId}} path for owner-scoped operations.</li>
 * <li>Provides endpoints for creating, updating, and viewing pets.</li>
 * <li>Uses {@code @ModelAttribute} to populate form data (pet types, owner).</li>
 * <li>Uses {@code @InitBinder} to configure data binding and validation.</li>
 * <li>Thread-safe: stateless controller.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Dependencies:</strong>
 * <ul>
 * <li>Spring MVC: Controller, request mapping, and view resolution.</li>
 * <li>PetRepository: For accessing pet data and pet types.</li>
 * <li>OwnerRepository: For accessing owner data.</li>
 * <li>PetValidator: For validating pet form submissions.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Endpoints:</strong>
 * <ul>
 * <li>{@code GET /owners/{ownerId}/pets/new}: Display pet creation form.</li>
 * <li>{@code POST /owners/{ownerId}/pets/new}: Process pet creation.</li>
 * <li>{@code GET /owners/{ownerId}/pets/{petId}/edit}: Display pet edit form.</li>
 * <li>{@code POST /owners/{ownerId}/pets/{petId}/edit}: Process pet update.</li>
 * </ul>
 * </p>
 *
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @version 1.0
 * @since 1.0
 * @see Pet
 * @see Owner
 * @see PetRepository
 * @see OwnerRepository
 * @see PetValidator
 */
package org.springframework.samples.petclinic.owner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Controller for managing pet-related HTTP requests.
 *
 * <p>
 * Handles HTTP requests for pet operations including creation, updates, and
 * display of pet details within the context of an owner.
 * </p>
 *
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/owners/{ownerId}")
class PetController {

	/**
	 * View name for the pet creation/update form.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Template name for rendering the pet form view.
	 * </p>
	 */
	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

	/**
	 * Repository for accessing pet data.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Used to perform CRUD operations on pet entities.
	 * </p>
	 */
	private final PetRepository pets;

	/**
	 * Repository for accessing owner data.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Used to retrieve owner information for pet
	 * operations.
	 * </p>
	 */
	private final OwnerRepository owners;

	/**
	 * Constructs a PetController with the given repositories.
	 *
	 * <p>
	 * Initializes the controller with repositories for accessing pet and owner
	 * data.
	 * </p>
	 *
	 * @param pets the PetRepository to use for pet operations. Must not be
	 *             {@code null}.
	 * @param owners the OwnerRepository to use for owner operations. Must not be
	 *               {@code null}.
	 *
	 * @throws NullPointerException if either parameter is {@code null}.
	 */
	public PetController(PetRepository pets, OwnerRepository owners) {
		this.pets = pets;
		this.owners = owners;
	}

	/**
	 * Populates the model with available pet types.
	 *
	 * <p>
	 * This method is invoked before each request handler method to populate the
	 * model with the list of available pet types for form rendering.
	 * </p>
	 *
	 * @return a collection of all available pet types.
	 */
	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.pets.findPetTypes();
	}

	/**
	 * Retrieves the owner for the current request.
	 *
	 * <p>
	 * This method is invoked before each request handler method to retrieve the
	 * owner from the path variable and add it to the model.
	 * </p>
	 *
	 * @param ownerId the owner identifier from the path variable. Must be a
	 *                positive integer.
	 *
	 * @return the owner with the given identifier.
	 */
	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId") int ownerId) {
		return this.owners.findById(ownerId);
	}

	/**
	 * Configures data binding for owner form submissions.
	 *
	 * <p>
	 * Disallows the "id" field from being set during form binding to prevent
	 * unauthorized modification of owner identifiers.
	 * </p>
	 *
	 * @param dataBinder the data binder to configure. Must not be {@code null}.
	 */
	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/**
	 * Configures data binding and validation for pet form submissions.
	 *
	 * <p>
	 * Registers the {@link PetValidator} for validating pet form data.
	 * </p>
	 *
	 * @param dataBinder the data binder to configure. Must not be {@code null}.
	 */
	@InitBinder("pet")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PetValidator());
	}

	/**
	 * Displays the pet creation form.
	 *
	 * <p>
	 * HTTP Method: GET<br>
	 * Path: {@code /owners/{ownerId}/pets/new}<br>
	 * Response: HTML form for creating a new pet.
	 * </p>
	 *
	 * @param owner the owner for which to create a pet. Must not be {@code null}.
	 * @param model the model to add attributes to. Must not be {@code null}.
	 *
	 * @return the view name for the pet creation form.
	 */
	@GetMapping("/pets/new")
	public String initCreationForm(Owner owner, ModelMap model) {
		Pet pet = new Pet();
		owner.addPet(pet);
		model.put("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	/**
	 * Processes pet creation form submission.
	 *
	 * <p>
	 * HTTP Method: POST<br>
	 * Path: {@code /owners/{ownerId}/pets/new}<br>
	 * Request Body: Pet form data (validated with {@code @Valid}).<br>
	 * Response: Redirect to owner details page on success, or form with errors on
	 * validation failure.
	 * </p>
	 *
	 * <p>
	 * <strong>Validation:</strong> Checks for duplicate pet names within the
	 * owner's pet collection.
	 * </p>
	 *
	 * @param owner the owner for which to create a pet. Must not be {@code null}.
	 * @param pet the pet to create (validated). Must not be {@code null}.
	 * @param result the binding result containing validation errors. Must not be
	 *               {@code null}.
	 * @param model the model to add attributes to. Must not be {@code null}.
	 *
	 * @return redirect to owner details page on success, or form view on
	 *         validation failure.
	 */
	@PostMapping("/pets/new")
	public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model) {
		if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null){
			result.rejectValue("name", "duplicate", "already exists");
		}
		owner.addPet(pet);
		if (result.hasErrors()) {
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			this.pets.save(pet);
			return "redirect:/owners/{ownerId}";
		}
	}

	/**
	 * Displays the pet edit form.
	 *
	 * <p>
	 * HTTP Method: GET<br>
	 * Path: {@code /owners/{ownerId}/pets/{petId}/edit}<br>
	 * Path Variables: {@code petId} (pet identifier).<br>
	 * Response: HTML form for editing the pet.
	 * </p>
	 *
	 * @param petId the identifier of the pet to edit. Must be a positive integer.
	 * @param model the model to add attributes to. Must not be {@code null}.
	 *
	 * @return the view name for the pet edit form.
	 */
	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm(@PathVariable("petId") int petId, ModelMap model) {
		Pet pet = this.pets.findById(petId);
		model.put("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	/**
	 * Processes pet update form submission.
	 *
	 * <p>
	 * HTTP Method: POST<br>
	 * Path: {@code /owners/{ownerId}/pets/{petId}/edit}<br>
	 * Path Variables: {@code petId} (pet identifier).<br>
	 * Request Body: Pet form data (validated with {@code @Valid}).<br>
	 * Response: Redirect to owner details page on success, or form with errors on
	 * validation failure.
	 * </p>
	 *
	 * @param pet the pet to update (validated). Must not be {@code null}.
	 * @param result the binding result containing validation errors. Must not be
	 *               {@code null}.
	 * @param owner the owner of the pet. Must not be {@code null}.
	 * @param model the model to add attributes to. Must not be {@code null}.
	 *
	 * @return redirect to owner details page on success, or form view on
	 *         validation failure.
	 */
	@PostMapping("/pets/{petId}/edit")
	public String processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, ModelMap model) {
		if (result.hasErrors()) {
			pet.setOwner(owner);
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			this.pets.save(pet);
			return "redirect:/owners/{ownerId}";
		}
	}

}
