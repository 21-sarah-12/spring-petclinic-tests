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
 * Controller for managing owner-related HTTP requests.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Owner Management<br>
 * <strong>Purpose:</strong> Handles HTTP requests for owner operations including
 * creation, updates, search, and display of owner details.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Controller (Web Layer)<br>
 * This class handles HTTP requests and coordinates with the repository layer
 * to manage owner data. It provides endpoints for owner CRUD operations and
 * search functionality.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>Annotated with {@code @Controller} for Spring MVC integration.</li>
 * <li>Provides endpoints for creating, updating, searching, and viewing owners.</li>
 * <li>Uses {@code @InitBinder} to configure data binding and validation.</li>
 * <li>Supports form-based and REST-style request handling.</li>
 * <li>Thread-safe: stateless controller.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Dependencies:</strong>
 * <ul>
 * <li>Spring MVC: Controller, request mapping, and view resolution.</li>
 * <li>OwnerRepository: For accessing owner data.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Endpoints:</strong>
 * <ul>
 * <li>{@code GET /owners/new}: Display owner creation form.</li>
 * <li>{@code POST /owners/new}: Process owner creation.</li>
 * <li>{@code GET /owners/find}: Display owner search form.</li>
 * <li>{@code GET /owners}: Search owners by last name.</li>
 * <li>{@code GET /owners/{ownerId}}: Display owner details.</li>
 * <li>{@code GET /owners/{ownerId}/edit}: Display owner edit form.</li>
 * <li>{@code POST /owners/{ownerId}/edit}: Process owner update.</li>
 * </ul>
 * </p>
 *
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @version 1.0
 * @since 1.0
 * @see Owner
 * @see OwnerRepository
 */
package org.springframework.samples.petclinic.owner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

/**
 * Controller for managing owner-related HTTP requests.
 *
 * <p>
 * Handles HTTP requests for owner operations including creation, updates,
 * search, and display of owner details.
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
class OwnerController {

	/**
	 * View name for the owner creation/update form.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Template name for rendering the owner form view.
	 * </p>
	 */
	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

	/**
	 * Repository for accessing owner data.
	 *
	 * <p>
	 * <strong>Purpose:</strong> Used to perform CRUD operations on owner entities.
	 * </p>
	 */
	private final OwnerRepository owners;


	/**
	 * Constructs an OwnerController with the given OwnerRepository.
	 *
	 * <p>
	 * Initializes the controller with a repository for accessing owner data.
	 * </p>
	 *
	 * @param clinicService the OwnerRepository to use for owner operations. Must
	 *                      not be {@code null}.
	 *
	 * @throws NullPointerException if {@code clinicService} is {@code null}.
	 */
	public OwnerController(OwnerRepository clinicService) {
		this.owners = clinicService;
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
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/**
	 * Displays the owner creation form.
	 *
	 * <p>
	 * HTTP Method: GET<br>
	 * Path: {@code /owners/new}<br>
	 * Response: HTML form for creating a new owner.
	 * </p>
	 *
	 * @param model the model to add attributes to. Must not be {@code null}.
	 *
	 * @return the view name for the owner creation form.
	 */
	@GetMapping("/owners/new")
	public String initCreationForm(Map<String, Object> model) {
		Owner owner = new Owner();
		model.put("owner", owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	/**
	 * Processes owner creation form submission.
	 *
	 * <p>
	 * HTTP Method: POST<br>
	 * Path: {@code /owners/new}<br>
	 * Request Body: Owner form data (validated with {@code @Valid}).<br>
	 * Response: Redirect to owner details page on success, or form with errors on
	 * validation failure.
	 * </p>
	 *
	 * @param owner the owner to create (validated). Must not be {@code null}.
	 * @param result the binding result containing validation errors. Must not be
	 *               {@code null}.
	 *
	 * @return redirect to owner details page on success, or form view on
	 *         validation failure.
	 */
	@PostMapping("/owners/new")
	public String processCreationForm(@Valid Owner owner, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		} else {
			this.owners.save(owner);
			return "redirect:/owners/" + owner.getId();
		}
	}

	/**
	 * Displays the owner search form.
	 *
	 * <p>
	 * HTTP Method: GET<br>
	 * Path: {@code /owners/find}<br>
	 * Response: HTML form for searching owners by last name.
	 * </p>
	 *
	 * @param model the model to add attributes to. Must not be {@code null}.
	 *
	 * @return the view name for the owner search form.
	 */
	@GetMapping("/owners/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("owner", new Owner());
		return "owners/findOwners";
	}

	/**
	 * Processes owner search form submission.
	 *
	 * <p>
	 * HTTP Method: GET<br>
	 * Path: {@code /owners}<br>
	 * Query Parameters: {@code lastName} (optional).<br>
	 * Response: Owner details page if one owner found, list of owners if multiple
	 * found, or search form with error if none found.
	 * </p>
	 *
	 * <p>
	 * <strong>Behavior:</strong>
	 * <ul>
	 * <li>If no last name is provided, searches for all owners.</li>
	 * <li>If one owner is found, redirects to owner details page.</li>
	 * <li>If multiple owners are found, displays a list.</li>
	 * <li>If no owners are found, displays search form with error message.</li>
	 * </ul>
	 * </p>
	 *
	 * @param owner the owner object containing search criteria. Must not be
	 *              {@code null}.
	 * @param result the binding result for validation errors. Must not be
	 *               {@code null}.
	 * @param model the model to add attributes to. Must not be {@code null}.
	 *
	 * @return owner details page, owner list, or search form with error.
	 */
	@GetMapping("/owners")
	public String processFindForm(Owner owner, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (owner.getLastName() == null) {
			owner.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<Owner> results = this.owners.findByLastName(owner.getLastName());
		if (results.isEmpty()) {
			// no owners found
			result.rejectValue("lastName", "notFound", "not found");
			return "owners/findOwners";
		} else if (results.size() == 1) {
			// 1 owner found
			owner = results.iterator().next();
			return "redirect:/owners/" + owner.getId();
		} else {
			// multiple owners found
			model.put("selections", results);
			return "owners/ownersList";
		}
	}

	/**
	 * Displays the owner edit form.
	 *
	 * <p>
	 * HTTP Method: GET<br>
	 * Path: {@code /owners/{ownerId}/edit}<br>
	 * Path Variables: {@code ownerId} (owner identifier).<br>
	 * Response: HTML form for editing the owner.
	 * </p>
	 *
	 * @param ownerId the identifier of the owner to edit. Must be a positive
	 *                integer.
	 * @param model the model to add attributes to. Must not be {@code null}.
	 *
	 * @return the view name for the owner edit form.
	 */
	@GetMapping("/owners/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
		Owner owner = this.owners.findById(ownerId);
		model.addAttribute(owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	/**
	 * Processes owner update form submission.
	 *
	 * <p>
	 * HTTP Method: POST<br>
	 * Path: {@code /owners/{ownerId}/edit}<br>
	 * Path Variables: {@code ownerId} (owner identifier).<br>
	 * Request Body: Owner form data (validated with {@code @Valid}).<br>
	 * Response: Redirect to owner details page on success, or form with errors on
	 * validation failure.
	 * </p>
	 *
	 * @param owner the owner to update (validated). Must not be {@code null}.
	 * @param result the binding result containing validation errors. Must not be
	 *               {@code null}.
	 * @param ownerId the identifier of the owner to update. Must be a positive
	 *                integer.
	 *
	 * @return redirect to owner details page on success, or form view on
	 *         validation failure.
	 */
	@PostMapping("/owners/{ownerId}/edit")
	public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable("ownerId") int ownerId) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		} else {
			owner.setId(ownerId);
			this.owners.save(owner);
			return "redirect:/owners/{ownerId}";
		}
	}

	/**
	 * Displays owner details.
	 *
	 * <p>
	 * HTTP Method: GET<br>
	 * Path: {@code /owners/{ownerId}}<br>
	 * Path Variables: {@code ownerId} (owner identifier).<br>
	 * Response: HTML page displaying owner details and associated pets.
	 * </p>
	 *
	 * @param ownerId the identifier of the owner to display. Must be a positive
	 *                integer.
	 *
	 * @return a ModelAndView containing the owner details view and model data.
	 */
	@GetMapping("/owners/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		mav.addObject(this.owners.findById(ownerId));
		return mav;
	}

}
