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
 * PetType formatter with poor code style (for demonstration purposes).
 *
 * <p>
 * <strong>Module:</strong> PetClinic Pet Management<br>
 * <strong>Purpose:</strong> Provides bidirectional conversion between
 * {@link PetType} objects and their string representations for Spring MVC
 * form binding and display. This class intentionally demonstrates poor code
 * style and formatting practices.
 * </p>
 *
 * <p>
 * <strong>Architectural Role:</strong> Formatter (Web Layer)<br>
 * This class implements Spring's {@link org.springframework.format.Formatter}
 * interface to enable automatic conversion of PetType objects to and from
 * strings in web forms and request parameters.
 * </p>
 *
 * <p>
 * <strong>Key Characteristics:</strong>
 * <ul>
 * <li>Implements {@link org.springframework.format.Formatter} for Spring MVC
 * integration.</li>
 * <li>Annotated with {@code @Component} for automatic Spring registration.</li>
 * <li>Provides {@code print()} method to convert PetType to string (name).</li>
 * <li>Provides {@code parse()} method to convert string to PetType by name
 * lookup.</li>
 * <li>Thread-safe: stateless formatter.</li>
 * <li><strong>WARNING:</strong> This class demonstrates poor code style and
 * formatting. Use {@link PetTypeFormatter} instead for production code.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Dependencies:</strong>
 * <ul>
 * <li>Spring Framework: Formatter interface and Component annotation.</li>
 * <li>PetRepository: For looking up PetType by name.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Usage Note:</strong> This class is provided for educational purposes
 * to demonstrate code style violations. For production use, refer to
 * {@link PetTypeFormatter}.
 * </p>
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Michael Isvy
 * @version 1.0
 * @since 1.0
 * @see PetTypeFormatter
 * @see PetType
 * @see PetRepository
 * @see org.springframework.format.Formatter
 */
package org.springframework.samples.petclinic.owner;import java.text.ParseException;import java.util.Collection;import java.util.Locale;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.format.Formatter;import org.springframework.stereotype.Component;

/**
 * Instructs Spring MVC on how to parse and print elements of type 'PetType'.
 *
 * <p>
 * <strong>WARNING:</strong> This class demonstrates poor code style and
 * formatting practices. Use {@link PetTypeFormatter} instead for production
 * code.
 * </p>
 *
 * <p>
 * Starting from Spring 3.0, Formatters have come as an improvement in
 * comparison to legacy PropertyEditors. See the following links for more
 * details:
 * <ul>
 * <li><a href="http://static.springsource.org/spring/docs/current/spring-framework-reference/html/validation.html#format-Formatter-SPI">
 * Spring Formatter SPI</a></li>
 * <li><a href="http://gordondickens.com/wordpress/2010/09/30/using-spring-3-0-custom-type-converter/">
 * Using Spring 3.0 Custom Type Converter</a></li>
 * </ul>
 * </p>
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Michael Isvy
 * @version 1.0
 * @since 1.0
 * @deprecated Use {@link PetTypeFormatter} instead. This class demonstrates
 *             poor code style.
 */
@Component public class PetTypeFormatterBadStyle 
    implements        Formatter<PetType> {
private final PetRepository pets;


    @Autowired public                PetTypeFormatterBadStyle(PetRepository pets) 
{
        this.pets=pets;
}

    @Override
public String             print(PetType petType, 
        Locale locale) {return petType.getName();
    }

@Override
    public                     PetType parse(String text,Locale locale)throws 
        ParseException{Collection<PetType>findPetTypes=this.pets.findPetTypes();
for(PetType type:findPetTypes){
            if(type.getName().equals(text)){return type;}}
        throw new ParseException("type not found: "+
            text,0);
    }




}
