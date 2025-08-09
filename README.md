- # Microservice Design Patterns and Principles with example
Microservice architecture has become very popular due to it's aglity, scalability, resilience and maintainability. In this article, I have briefly explained and demonstrated important design patterns and principles that are mostly used in microservice style architecture. 
 
- # Brief explanation of key design principles for microservices architecture used in the project:
  - <b>Database per Service ![#f03c15](https://via.placeholder.com/15/f03c15/f03c15.png) `Database Pattern`</b>
    <p>One important criteria of microservice is must be loosely coupled so that it can be built, maintained and deployed independently. Sometime, each service may have different data storage requirement and easy replication is also a client ask in order to scale. To mange these, database per service is a good approach where restriction of the database can be controlled by only specific service, can be scaled based on the demand, size of the database will be small as compare as database per application which turns easy maintenance.</p>
  - <b>API Gateway ![#f03c15](https://via.placeholder.com/15/f03c15/f03c15.png) `Integration Pattern`</b>
    <p>Most of the cases an application can contains more than thousand of microservices and each microservice have diffrent end points so managing all servicess a common access point is necessary. API Gateway creates an common entry gate for all microservices of the application or for group of microservices of the application and routes the traffic to concerned services as a proxy. Additionally, it provides features like aggregation of services, caching, authentication and rate limiting etc</p>
  - <b>Externalized Configuration ![#f03c15](https://via.placeholder.com/15/f03c15/f03c15.png) `Cross-Cutt Aling Concern Pattern`</b>
    <p>Often services need to be run in diffrent environment like DEV, ST, UAT etc and to manage those environments different configurations are required in various properties file. A change in any of those properties a new build and re-deployment is required to be reflected. Externalize configuration helps to overcome the problem of rebuilding and redeployment when configuration of the application are changed. </p>
  - <b>Circuit Breaker ![#f03c15](https://via.placeholder.com/15/f03c15/f03c15.png) `Cross-Cutting Concern Pattern`</b>
    <p>In a microservice based architecture, most of the services are inter dependent and lot of time they communicate internally to fulfill user's need. It is obvious that any time any downstearm service can move to non responding state or down state due to system issue. At that moment, requests are coming to a service can be sent to downsteam service that is in down state and unnecessarily uses system resources.If this process continues for longer period of time for lerge of request then there may be high chance that working service cane also be moved to down state due to to the unavailabilty of the system resouces. Circuit Breaker is used to detect the failure and prevent cascading effect </p>
  - <b>Service Discovery ![#f03c15](https://via.placeholder.com/15/f03c15/f03c15.png) `Cross-Cutting Concern Pattern`</b>
    <p> Now a days services are deployed in distributed system and sometime IP and Port are dynamically assigned to the services so it is very difficult to remeber the end point of services by consumer and most importantly every time address changes will break consumer service. So, services registry needs to be created to store all metadata of the producer services and everytime time new service added in network must be registered in service registry during start or de register during shutdown. All consumer service must query in service registry to pull correct producer service location before calling</p>
  - <b>Distributed Tracing ![#f03c15](https://via.placeholder.com/15/f03c15/f03c15.png) `Observability Patterns`</b>
    <p> In microservice architecture, request often span across different services to perform various operations. It is quite difficult to trace the request and response of service call for quick troubleshooting. Distributed Tracing patterns help to associate Trace Id and Span Id in each service call and Trace Id will be same for entire round trip of the request but Span Id will be diffrent for any intermediary service call. So using Trace Id entire journey/call stack can be idetified.</p>
  - <b>Health Check ![#f03c15](https://via.placeholder.com/15/f03c15/f03c15.png) `Observability Patterns`</b>
     <p> To detect correct status of the service is very important so that request will not be routed to any unhealth service. /health API is used to validate the status of the service and based on that instance can be declared as healthy or unhealthy. </p> 
  - <b>Authentication and Authorization ![#f03c15](https://via.placeholder.com/15/f03c15/f03c15.png) `Security Patterns`</b>
     <p> security is an important aspect of microservice architecture and we should follow standard Oauth2.0 process for securing our APIs to avoid any unauthorize access of data, function and system. This can be acheived by authenticating user and authorize their access of APIs based on defined role/authority.  </p> 
- # Use Case
<p> I have considered a simple use case to demonstrate the above said design patterns and principles. A educational society has decided to launch few model schools for children to provide better way of learning. They are looking for a "School Management Software" to perform most operations though online processes. </p>

- Society can add new school in system and also can update various parameters of existing school like location, school governance details.
- Society has the the authority to delete school from the list.
- Society can add/update/delete student in a school.
- School Fees for student can be generated monthly basis.
- Studdent can view their details and correct in case necessary.
- Student can make payment of the fees.
 
- # Technology Stacks
Here, I have used spring boot framework to implement "School Managment Software" by considering above design patterns. Below are the technology stacks involved:
  - java 17 (JDK 17)
  - Spring Boot 3.1.3
  - Spring Cloud 2022.0.4
  - Maven 3.9.2

- # Architecture Diagram
I have provided an overall architecture diagram for better undertanding and clarity purpose. Definitely there is scope for improvement and I will be adding more features to make it robust further.
![image](https://github.com/user-attachments/assets/0bbed6f6-dcdb-4c17-b2d6-21d5a93777de)

- # Project structure and various component details
![image](https://github.com/mail2mrcm/springboot-microservices/assets/118661926/f68de201-2c2b-4c5c-bded-90c67821bd68")
|Spring Libraries/Components|Purpose|Reference|
|---------------------------|-------|---------|
|Spring Cloud Config |Config server is used for externalize the configurations. Group Id `org.springframework.cloud` and Artifact Id `spring-cloud-config-server` is added for enabling config server and all client applications have  Group Id `spring-cloud-config-server` and Artifact Id `spring-cloud-starter-config`|[Config Server](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/)|
|Spring Cloud Circuit Breaker| Resilience4j library used for fault tolerance and circuitbreaker.Group Id `org.springframework.cloud` and artifact ID `spring-cloud-starter-circuitbreaker-reactor-resilience4j` is added as dependency for circuitbreaker|[Circuit Breaker](https://docs.spring.io/spring-cloud-circuitbreaker/docs/current/reference/html/)|
|Spring Cloud Netflix Eureka |This is client side service discovery allows services to find and communicate with each other without hard-coding the hostname and port. Group Id `org.springframework.cloud` and Artifact Id `spring-cloud-starter-netflix-eureka-server` is added in discovery server application and Group Id `org.springframework.cloud` and Artifact Id `spring-cloud-starter-netflix-eureka-client` is added in client applications for registering in eureka|[Service Discovery](https://spring.io/guides/gs/service-registration-and-discovery/)|
|Spring Cloud Gateway |This is an API Gateway. Group Id `org.springframework.cloud` and artifact ID `spring-cloud-starter-gateway` is added in dependency for API Gateway feature|[Gateway](https://cloud.spring.io/spring-cloud-gateway/reference/html/)|
|Spring Boot Actuator| This is used for health check of the application. Group Id `org.springframework.boot` and Artifact Id `spring-boot-starter-actuator` is added for the same.|[Health Check](https://www.baeldung.com/spring-boot-actuators)
|H2 Database| Dedicated H2 database is used for every microservices involved in this project. this is being used for demo purpose but for real application it should be any RDBMS or NoSQL database. Group Id `com.h2database` and Artifact Id `h2` is added in POM file|
|Spring Authorization Server|This framework is used for authentication and authorization purpose. Group Id `org.springframework.boot` and Artifact Id `spring-boot-starter-oauth2-authorization-server` is added in dependency for authorization process.|[Auth Server](https://docs.spring.io/spring-authorization-server/reference/getting-started.html)
|Spring Resource Server|This is used for validating user's access and takes permit/deny actions while user tries to access APIs/resources. Group Id `org.springframework.boot` and Artifact Id `spring-boot-starter-oauth2-resource-server` is added in dependency. As this works on top of spring security so spring securtity dependency Group Id `org.springframework.boot` and Artifact Id `spring-boot-starter-security` is also added.|[Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html)

- `config-server` - This application is used for storing all configuration of client applications. In this case, api-gateway, school-service, student-service, payment-service are client application and these application access HTTP resource-based API for external configuration from config-server. There can be various source of configuration supported. It supports loading configuration files from classpath, git, database, vault etc. I have loaded external configuration of client applications from classpath. I have created a properties file for each of corresponding client application for better clarity.[Single application.yml file can also be used instead].
To enable spring config server below action has been taken
    - `@EnableConfigServer` annotation is added in spring boot startup application class.
    - Added profiles.active=native properties in application.yml file
    - Created a `{client-appliaction-name}.yml` [for gateway application configuartion file is api-gateway.yml] for every client application and kept under /resources/config location
    - Config server dependency is added in POM.xml file
      `<dependency>
	    <groupId>org.springframework.cloud</groupId>
	    <artifactIdspring-cloud-config-server</artifactId>
       </dependency>`
    
- `service-discovery` - This application works as service registry and service discovery. When instance of client the application is started, immediately registered itself in service registry so that other application can find it for inter communication. Here api-gateway, school-service, payment-service, student-service are considered as client application.
To enable service discovery below actions have been taken
    - `@EnableEurekaServer` annotation is added in spring boot startup application class.
    -  Config server dependency is added in POM.xml file
      `<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
	</dependency>`
    -  Application name (service-discovery) is added `application.yml` file
    -  `service-discovery.yml` properties file is added in config-server application for storing all configuration required to run service-discovery application.
    -  <img width="408" alt="image" src="https://github.com/mail2mrcm/springboot-microservices/assets/118661926/f2e9ebb5-3720-4c3c-aa86-127135a51fc0">
- `auth-server` - This is an independent spring boot application used as authorization server for authenticating the user and issue an access token for accessing resource of resouce server. I have set in memory users with read and write access. 
For auth-server below actions have been taken.
   - `application.yml` properties file is added under resources  for storing all configuration required to run service-discovery application.
   <img width="409" alt="image" src="https://github.com/mail2mrcm/springboot-microservices/assets/118661926/cb8fa5a3-af99-46a4-b8be-516aa33cef49">
- `school-service|student-service|payment-service` - These are all microservices for managing business capabilities of school management software. These microservices exposed various REST supported APIs to communicate with other services.

|Microservice|Business Capabilites|HTTP Method & API endpoint|
|------------|--------------------|--------------------------|
|School Service|To Add new school for society|POST http://{host}:{port}/school/|
|School Service|To Edit school information|PUT http://{host}:{port}/school/{id}|
|School Service|To Delete school from society|DELETE http://{host}:{port}/school/{id}|
|School Service|To fetch all of the school details|GET http://{host}:{port}/school/|
|School Service|To Fetch specific school details|GET http://{host}:{port}/school/{id}|
|Student Service|To Add new student to specific school|POST http://{host}:{port}/student/|
|Student Service|To Edit student information|PUT http://{host}:{port}/student/{id}|
|Student Service|To Delete student from school|DELETE http://{host}:{port}/student/{id}|
|Student Service|To Fetch all of the student details of a school|GET http://{host}:{port}/student/|
|Student Service|To Fetch specific student details|GET http://{host}:{port}/student/{id}|
|Student Service|To Fetch specific school details|GET http://{host}:{port}/school/{id}|
|Payment Service|To Add new payment for student|POST http://{host}:{port}/payment/|
|Payment Service|To make payment|PUT http://{host}:{port}/payment/{id}|
|Payment Service|To Delete payment|DELETE http://{host}:{port}/payment/{id}|
|Payment Service|To Fetch a specific payment of student|GET http://{host}:{port}/payment/{id}|
|Payment Service|To Fetch paid/unpaid payment of a student|GET http://{host}:{port}/payment/student/{studentid}|

student-service or school-service or payment-service have almost similar configuration and dpendency, so I have explained only school-service configuration and dependency here. 
- config client dependency is added in POM for fetching configuration from config server.
  `<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-config</artifactId>
   </dependency>`
- I have set server.port=0 (This was being set for dynamic port assignment as we may need to run multiple instances of same application.), spring.application.name = school-service and spring.config.import (url of `config-server` application) for config server connectivity in **application.yml** properties file.
  
  <img width="372" alt="image" src="https://github.com/mail2mrcm/springboot-microservices/assets/118661926/302d6a0b-44f5-4a09-83bd-9952d0522a29">
  
 - service discovery client dependency is added in POM discovery and registry purpose.
  `		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>`
-  **school-service.yml** file is added in /resources/config of `config-server` application for keeping all external configuration.
   <img width="492" alt="image" src="https://github.com/mail2mrcm/springboot-microservices/assets/118661926/5c7158ba-1dbf-433b-82fa-bafc697d263f">
  
-  Added few important properties for service discovery like, eureka.client-service-url (url of service discovery), eureka.instnace (I have created unique id for each instance of application due to existance of multiple instances of sample application for managing load of incoming request. combination of application name, instance id and random value).
-  Also added resilience4j dependeny for fault tolerance. Any of the service i.e., student-service, school-service or payment-service is down then request will not be sent to service rather end user can get customize message. I have defined fallback method to manage the same.
-  `<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
		</dependency>`
- Added corrsponding configuration in school-service.yml file for resilience4j.circuitbreaker.
  <img width="395" alt="image" src="https://github.com/mail2mrcm/springboot-microservices/assets/118661926/d00a1aaf-1d70-4be8-9386-f61ed411de7e">
- Defined a fallback method **getSystemFailure()** in controller which will executed if when downstream service is down.

  ```
  @RequestMapping(value = "/systemFailure", method = {RequestMethod.GET, RequestMethod.POST})
  public ResponseEntity<String> getSystemFailure() {
    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Please try after sometime");
  }
  ```
- Also, attached fallback method in relevent APIs using @CircuitBreaker annotation.
  <img width="463" alt="image" src="https://github.com/mail2mrcm/springboot-microservices/assets/118661926/c5f3eed1-9ed0-4f41-b291-a2a625f979c6">

- `api-gateway` - I have use this application as the gateway of all microservices.  All APIs will be accessible through url of API gateway, only context root will be diffrent for each downstream microservices. For student api context root is used **/student**, for school APIs context root is **/school** and for payment it is **/payment**. Additionally this gateway works as Authorization resource server. So it is integrated with `auth-server` for validating incoming resource request if request header has valid bearer token then only it will forward request to downstearm i.e., student, payment or school service dpending on routing defined.

- **gateway-service.yml** file is added for external configuration in `config-server` application.
- Added netflix ureka discovery client dependency as like other applications and added corresponding service-url, eureka.instance.instance-id details.
  
  <img width="500" alt="image" src="https://github.com/mail2mrcm/springboot-microservices/assets/118661926/cda013bb-dac1-4beb-938d-613d5f43d4cc">

-  Added dependency for resource server `<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
		</dependency>`
                and
		`<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>`
- To connect to `auth-server`, I have added issuer-url (url of auth-server) in  **gateway-service.yml**. Where http://localhost:9999 is the value contained in the iss claim for JWT tokens that the authorization server will issue. Resource Server will use this property to further self-configure, discover the authorization server’s public keys, and subsequently validate incoming JWTs.
  
  <img width="377" alt="image" src="https://github.com/mail2mrcm/springboot-microservices/assets/118661926/157f9279-ea82-4e75-8600-70e0bb949e44">
  
-  Additionally, `@EnableWebFluxSecurity` annotation is added to take the dvantage of the ServerHttpSecurity class to build our security configuration for restricting  authenticated request as per scope.
  
   <img width="406" alt="image" src="https://github.com/mail2mrcm/springboot-microservices/assets/118661926/e3a68b33-bb64-4071-a6c3-52d17bedc687">
   <img width="547" alt="image" src="https://github.com/mail2mrcm/springboot-microservices/assets/118661926/1aaf5fcc-67a5-41e5-8a77-4dbf9e1661f1">

   
-  As `api-gateway` is the entry point, so this is the best place to add circuit breaker configuration in **gateway-service.yml** for building resilience in system. Also, added fallback method for handling incoming requuest while downstream service is down or not responding.

   <img width="408" alt="image" src="https://github.com/mail2mrcm/springboot-microservices/assets/118661926/283dcb5c-9369-4143-8542-809ef0ecb1d6">
   <img width="589" alt="image" src="https://github.com/mail2mrcm/springboot-microservices/assets/118661926/dc8c8416-8378-42ff-8f56-97519b382b87">

    
- ## Buiild & Deployment  :
  1. Build Config server and Deploy config-server1.0.0.jar. Default port 9090 is used.
  2. Build Service discovery application and deploy the same. Default port 9091 is used.
  3. Build Student microservice and deploy it in port 9092.
  4. Build Payment microservice and deploy it in port 9094.
  5. finally build API Gateawy and deploy it in port 9093.
     
- ## Test :
Only 3 APIs are available currently and can be added further <br/> 
 `Add student` - [POST] http://localhost:9093/api/1.0/student <br/>
   ```diff
      curl -X POST -H "Content-Type: application/json" -d '{"orgId": 1, "firstName": "chandan", "lastName": "maity", "gender": "male"}' http://localhost:9093/api/1.0/student/
   ```
 `Add fees to be paid for the student` - [POST] http://localhost:9093/api/1.0/payment <br/>
   ```diff
      curl -X POST -H "Content-Type: application/json" -d '{"studentId": 1, "description": "Semister Fees", "amount": 5000.0, "status": "pending"}' http://localhost:9093/api/1.0/payment/
   ``` 
 `Get student with payments` - [GET] http://localhost:9093/api/1.0/student/{id}/with-payments <br/>
   ```diff
      curl -X GET -H "Content-Type: application/json" http://localhost:9093/api/1.0/student/{id}/with-payments
   ``` 
 `Make payment by student` - [PUT] http://localhost:9093/api/1.0/payment/{id} <br/>
   ```diff
      curl -X PUT -H "Content-Type: application/json" -d '{"studentId": 1, "description": "Semister Fees", "amount": 5000.0, "status": "paid"}' http://localhost:9093/api/1.0/payment/1/
   ```      
