- # Microservice Design Patterns and Principles with example
Microservice architecture has become very popular due to it's aglity, scalability, resilience and maintainability. In this article, We will discuss and demonstrated important design pattern and principles that generally being followed to acheive aglity, scalability, resilience in microservices based application. 

- # Important Design Patterns
  - Database per Service **Note** <span style='color: red;'>Database Pattern</span>.
  - Externalized Configuration
  - API Gateway
  - Circuit Breaker
  - Service Discovery
  - Distributed Tracing
  - Health Check
- This is a spring boot based microsevice style application for student managment system. It used various sping components like spring config server, netflix eurek server as discovery    service, spring api gateway, openfeign for inter communication etc._

- ## Technology stack
  - java 17 (JDK 17)
  - Spring Boot 3.1.3
  - Spring Cloud 2022.0.4
  - Maven 3.9.2

- ## Architecture
  ![Student-Management](https://github.com/mail2mrcm/tech4all/assets/118661926/4104fa46-8183-4b7c-83a5-f8afc0f764ca)

- ## Project Structure & Components  :
  ![image](https://github.com/mail2mrcm/tech4all/assets/118661926/a567b87c-0729-44aa-bf43-283a66fecb62)
  - `Spring Config server` - This is used to externalize application configurations. [Spring Cloud Config](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/)
     I have loaded configuration from resources so used profile = native and all configurations are available under /config of resources location.
      
  - `Netflix Eureka as Service discvery` - This is for service registry. configuration of the service registry is available in config server i.e, ***service-discovery.yml***. Please 
     refer [service registry](https://spring.io/guides/gs/service-registration-and-discovery/) for more technical details.
    
  - `Spring CLoud API Gateway` - Gateway of all microservices.  All srevice to be accessed through this gateway. This application is also regstered in service discovery as per the 
     configuration available in ***gateway-service.yml***. [Spring api gateway](https://spring.io/guides/gs/gateway/) for more details
    
  - `Student Miroservice `- add/get/update student and also perform internal communication with Payment microservice to fetch payment details of student. This application is also 
     regstered in service discovery as per the configuration available in ***student-service.yml***.
    
  - `Payment Microservice` - add/make payment function. This application is regstered in service discovery as per the configuration available in ***payment-service.yml***.
    
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
