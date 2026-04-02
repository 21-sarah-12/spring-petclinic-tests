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
 * PetClinic Spring Boot Application entry point.
 *
 * <p>
 * <strong>Module:</strong> PetClinic Application<br>
 * <strong>Purpose:</strong> Main entry point for the Spring Boot PetClinic application,
 * demonstrating a complete Spring Framework-based web application with layered
 * architecture (Controllers, Services, Repositories, and Domain Models).
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Application Bootstrap<br>
 * This class serves as the Spring Boot application entry point, enabling auto-configuration
 * and component scanning for the entire PetClinic application. It initializes the embedded
 * web server and manages the application lifecycle.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>Annotated with {@code @SpringBootApplication} to enable auto-configuration,
 * component scanning, and configuration properties support.</li>
 * <li>Contains the {@code main(String[])} method that bootstraps the Spring Boot
 * application using {@link org.springframework.boot.SpringApplication#run(Class, String[])}.</li>
 * <li>Scans the {@code org.springframework.samples.petclinic} package and all subpackages
 * for Spring components (Controllers, Services, Repositories, Configurations).</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Dependencies:</strong>
 * <ul>
 * <li>Spring Boot: Auto-configuration and embedded web server management.</li>
 * <li>Spring Framework: Dependency injection, MVC, Data JPA, and transaction management.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Related Configuration:</strong> Application properties are loaded from
 * {@code application.properties} and environment-specific profiles
 * (e.g., {@code application-mysql.properties}). See {@code src/main/resources/}
 * for configuration details.
 * </p>
 *
 * <p>
 * <strong>Usage Example:</strong>
 * <pre>{@code
 * // Run the application from command line:
 * java -jar spring-petclinic-tests.jar
 *
 * // Or run from IDE:
 * PetClinicApplication.main(new String[]{});
 * }</pre>
 * </p>
 *
 * @author Dave Syer
 * @version 1.0
 * @since 1.0
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 */
package org.springframework.samples.petclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application class for PetClinic.
 *
 * <p>
 * This class bootstraps the PetClinic application using Spring Boot's auto-configuration
 * mechanism. It enables component scanning, auto-configuration, and property support
 * for the entire application.
 * </p>
 *
 * @author Dave Syer
 */
@SpringBootApplication
public class PetClinicApplication {

	/**
	 * Main entry point for the PetClinic Spring Boot application.
	 *
	 * <p>
	 * Starts the embedded web server and initializes the Spring application context.
	 * The application will listen on the configured port (default: 8080) and serve
	 * the PetClinic web application.
	 * </p>
	 *
	 * @param args command-line arguments passed to the Spring Boot application
	 *             (e.g., {@code --server.port=9000} to override the default port).
	 *
	 * @see org.springframework.boot.SpringApplication#run(Class, String[])
	 */
	public static void main(String[] args) {
		SpringApplication.run(PetClinicApplication.class, args);
	}

}
