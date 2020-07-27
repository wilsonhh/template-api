# Template First Data API project
Spring API starter template for new projects, it should have examples for the most common project scenarios.

The project is using Spring Boot 2.2.0.RELEASE with Spring JPA, Swagger, MapStruct and Oracle Database. The authentication is made with keycloak (https://www.keycloak.org/documentation.html), inside fd-security layer.

Pre-requisites (Using localhost security layer): 
1. Keycloak 3.0.0.Final 
2. fd-security running.... 

Pre-requisites (Using UAT security layer): 
1. Use UAT connections in your datasources config
2. Import the SSL certificate into your cacerts

Tutorials related to this project:

## Import the project into Eclipse IDE

1. Download the project into your workspace
2. Import the project into Eclipse as existing maven project

```
File > Import... > Maven > Existing Maven Projects
```
3. Before start your development change the package, classes, log4j name from "template" to your api name

4. Up your wildfly 17 server 

5. Run clean install

# Basics
## Project Structure
The structure is following the most common used application layers convention.
- Controllers should have as few code as possible, validate the request values and send all the information needed to the Services layer
- Services should contain all the application businness logic and orchestrate database transactions and the Repository calls
- Repositories are meant only for database calls

- **config:** Application custom configurations classes
	- **FDSecurity:** Api sercurity check
	- **SwaggerConfig:** Swagger Configuration

	
- **controller:** API Controllers
- **data:** All database related classes (minus entities). Already have some repositories and converters.
- **domain:** Domain entities
- **dto:** DTOs and POCOs
- **models:** Models of response
- **exception:** Custom exceptions
- **filter:** Request and response filters
- **handler:** Application handlers
- **properties:** Classes for getting the properties values saved on the resources/application.properties file
- **mapper:** Classes containing rulers for passing values from Entities to DTOs and the other way around

- **service:** Businness logic and others services
	- **api:** Service wrappers for api calls, not exposing the library to the businness logic services. 
		- **model:** Request and response models for the API calls
- **util:** Utils and helpers classes

## Remarks
- Everything in Portuguese
- Create **ALL** entity relations as ```FetchType.LAZY```. Use Hibernate's ```join fetch``` if the relation is needed.
- Configurations should be put in the application.properties file. If needed, create files for each environment overwriting the 'base' configuration file.

First Data pattern:
1. Use the methods HTTP: GET, POST, PUT and DELETE 
2. All response need to be done according to model, like DResponse or DPage.
3. The models will contain always the DTO object, or a list of DTO´s.
4. You can use the mapstruct to convert the domain to DTO, but it´s not mandatatory.
5. All business validation need to be done in service layer. *The validation	 error message need to be registered on messages.properties
6. Create the Custom exception to each business error and register it into Global ExceptionHandler. 
7. FDSecurity will be validate in filter layer. The API´s need inform  instituicao or institution + ( merchant / estabelecimento or documento / document (CPF ou CNPJ)) or service-contract / codigo-contrato)  or header parameter if necessary these information in your method. Id you don´t need these information, you don´t inform these attributes and just the token (Authorization) will be validate.
   If your api will be exposed to APIGEE, the validation will be done on APIGEE. And it will inform an attribute to api that it don´t need validate anymore. Please check TransactionFilter for better understanding.

