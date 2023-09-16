- # springboot-microservices-basic
  
  _This is a spring boot based microsevice style application for student managment system. It used various sping components like spring config server, netflix eurek server as discovery service, spring api gateway, openfeign for inter communication etc._
    
- ## Technology stack
- java 17 (JDK 17)
- Spring Boot 3.1.3
- Spring Cloud 2022.0.4
- Maven 3.9.2

- ## Arhitecture
![Student_Management](https://github.com/mail2mrcm/tech4all/assets/118661926/d4cdafb3-2174-45e4-9803-4a58fdff0780)

- ## Project Structure & Components  :
![image](https://github.com/mail2mrcm/tech4all/assets/118661926/a567b87c-0729-44aa-bf43-283a66fecb62)
  - `Spring Config server` - This is used to externalize application configurations. [Spring Cloud Config](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/)
  I have loaded configuration from resources so used profile = native and all configurations are available under /config of resources location.
    
  - `Netflix Eureka as Service discvery` - This is for service registry. configuration of the service registry is available in config server i.e, ***service-discovery.yml***. Please 
  refer [service registry](https://spring.io/guides/gs/service-registration-and-discovery/) for more technical details.
  - `Spring CLoud API Gateway` - Gateway of all microservices.  All srevice to be accessed through this gateway. This application is also regstered in service discovery as per the 
  configuration available in ***gateway-service.yml***. [Spring api gateway](https://spring.io/guides/gs/gateway/) for more details
  - `Stident Miroservice `- add/get/update student and also perform internal communication with Payment microservice to fetch payment details of student.
  - `Payment Microservice` - add/make payment function.


