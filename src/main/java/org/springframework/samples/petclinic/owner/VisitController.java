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
package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * Controller for managing visit-related HTTP requests.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Visit Management<br>
 * <strong>Purpose:</strong> Handles HTTP requests for visit operations including
 * creation and display of visit details within the context of a pet.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Controller (Web Layer)<br>
 * This class handles HTTP requests and coordinates with the repository layer
 * to manage visit data. It provides endpoints for visit CRUD operations within
 * the pet context.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>Annotated with {@code @Controller} for Spring MVC integration.</li>
 * <li>Provides endpoints for creating and viewing visits.</li>
 * <li>Uses {@code @ModelAttribute} to populate form data (visit, pet).</li>
 * <li>Uses {@code @InitBinder} to configure data binding.</li>
 * <li>Thread-safe: stateless controller.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Dependencies:</strong>
 * <ul>
 * <li>Spring MVC: Controller, request mapping, and view resolution.</li>
 * <li>VisitRepository: For accessing visit data.</li>
 * <li>PetRepository: For accessing pet data.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Endpoints:</strong>
 * <ul>
 * <li>GET /owners/*/pets/{petId}/visits/new: Display visit creation form.</li>
 * <li>POST /owners/{ownerId}/pets/{petId}/visits/new: Process visit creation.</li>
 * </ul>
 * </p>
 *
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 * @version 1.0
 * @since 1.0
 * @see Visit
 * @see Pet
 * @see VisitRepository
 * @see PetRepository
 */
@Controller
class VisitController {

	/**
	 * Repository for accessing visit data.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Used to perform CRUD operations on visit entities.
	 * </p>
	 */
	private final VisitRepository visits;

	/**
	 * Repository for accessing pet data.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Used to retrieve pet information for visit
	 * operations.
	 * </p>
	 */
	private final PetRepository pets;


	/**
	 * Constructs a VisitController with the given repositories.
	 *
	 * <p>
	 * Initializes the controller with repositories for accessing visit and pet
	 * data.
	 * </p>
	 *
	 * @param visits the VisitRepository to use for visit operations. Must not be
	 *               {@code null}.
	 * @param pets the PetRepository to use for pet operations. Must not be
	 *             {@code null}.
	 *
	 * @throws NullPointerException if either parameter is {@code null}.
	 */
	public VisitController(VisitRepository visits, PetRepository pets) {
		this.visits = visits;
		this.pets = pets;
	}

	/**
	 * Configures data binding for visit form submissions.
	 *
	 * <p>
	 * Disallows the "id" field from being set during form binding to prevent
	 * unauthorized modification of visit identifiers.
	 * </p>
	 *
	 * @param dataBinder the data binder to configure. Must not be {@code null}.
	 */
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/**
	 * Loads the pet and creates a new visit for the current request.
	 *
	 * <p>
	 * This method is invoked before each request handler method. It retrieves the
	 * pet from the path variable, creates a new visit, and adds it to the pet's
	 * visit collection. This ensures that the pet object always has an id (even
	 * though id is not part of the form fields) and that we always have fresh
	 * data.
	 * </p>
	 *
	 * <p>
	 * <strong>Side Effects:</strong> Creates a new Visit object and adds it to
	 * the pet's visit collection.
	 * </p>
	 *
	 * @param petId the pet identifier from the path variable. Must be a positive
	 *              integer.
	 * @param model the model to add attributes to. Must not be {@code null}.
	 *
	 * @return a new Visit object associated with the pet.
	 */
	@ModelAttribute("visit")
	public Visit loadPetWithVisit(@PathVariable("petId") int petId, Map<String, Object> model) {
		Pet pet = this.pets.findById(petId);
		model.put("pet", pet);
		Visit visit = new Visit();
		pet.addVisit(visit);
		return visit;
	}

	/**
	 * Displays the visit creation form.
	 *
	 * <p>
	 * HTTP Method: GET<br>
	 * Path: /owners/*/pets/{petId}/visits/new<br>
	 * Path Variables: petId (pet identifier).<br>
	 * Response: HTML form for creating a new visit.
	 * </p>
	 *
	 * <p>
	 * <strong>Note:</strong> The wildcard in the path allows this endpoint to be
	 * accessed from any owner context.
	 * </p>
	 *
	 * @param petId the pet identifier from the path variable. Must be a positive
	 *              integer.
	 * @param model the model to add attributes to. Must not be {@code null}.
	 *
	 * @return the view name for the visit creation form.
	 */
	@GetMapping("/owners/*/pets/{petId}/visits/new")
	public String initNewVisitForm(@PathVariable("petId") int petId, Map<String, Object> model) {
		return "pets/createOrUpdateVisitForm";
	}

	/**
	 * Processes visit creation form submission.
	 *
	 * <p>
	 * HTTP Method: POST<br>
	 * Path: /owners/{ownerId}/pets/{petId}/visits/new<br>
	 * Path Variables: ownerId (owner identifier), petId (pet identifier).<br>
	 * Request Body: Visit form data (validated with {@code @Valid}).<br>
	 * Response: Redirect to owner details page on success, or form with errors on
	 * validation failure.
	 * </p>
	 *
	 * @param visit the visit to create (validated). Must not be {@code null}.
	 * @param result the binding result containing validation errors. Must not be
	 *               {@code null}.
	 *
	 * @return redirect to owner details page on success, or form view on
	 *         validation failure.
	 */
	@PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisitForm(@Valid Visit visit, BindingResult result) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateVisitForm";
		} else {
			this.visits.save(visit);
			return "redirect:/owners/{ownerId}";
		}
	}

}
