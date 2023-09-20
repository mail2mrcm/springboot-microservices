# springboot-microservices-with-cicuitbreaker
This projects contains additionly circuitbreaker process between student service and payment service. All other components of this project are same as **springboot-microservice-basic.** and details are mentioned in `README.md`.
# Circuit Breaker 
This is a microservice pattern and principles for creating fault isolation beteen services in case any of services perform abnormally. Consider a senario where Service A calls Service B and Service B is not responding or not available. Consequently Service A will wait for a period and finally it will throw exception to end users. So resources of the system are not properly being used, even if Service B is not avilable but Service A calls everytime and unnecessary utilizing computer resources.In such cases, circuit breaker can help by stopping request sending for a specific time, waiting for the timeout ends, enable a limited number of requests to check whether Serice B is working. If those requests succeed, it allows microservices to continue normal operations. If not, it will again start the timeout. This concept is being used in this project, in case payment service is down/unavailable then this will not be called by student service as per circutbreaker configuration. [Circuit Breaker](https://salithachathuranga94.medium.com/micro-service-patterns-circuit-breaker-with-spring-boot-253e4a829f94)

# Circuit Breaker Configuration used in this project
- `sliding-window-type`: The number of requests is recorded and aggregated in the last “sliding-window-size” seconds.
- `failure-rate-threshold`: Trigger Circuit Breaker if at least “failure-rate-threshold” of requests have failed.
- `minimum-number-of-calls`: Records at least “minimum-number-of-calls” requests in the last “sliding-window-size” seconds before calculating the failure rate.
- `automatic-transition-from-open-to-half-open-enabled`: After ”wait-duration-in-open-state” seconds, Circuit Breaker will automatically transition from open to half-open state.
- `wait-duration-in-open-state`: If triggered, wait at least “wait-duration-in-open-state” seconds before allowing more calls.
- `permitted-number-of-calls-in-half-open-state`: After “wait-duration-in-open-state” time has passed, allow another “permitted-number-of-calls-in-half-open-state” requests and wait for them to calculate the failure rate again.
- `sliding-window-size`: Record the result of the last “sliding-window-size” seconds.
![image](https://github.com/mail2mrcm/springboot-microservices/assets/118661926/f8380fc7-d912-4c79-9cfb-3db14004034e)

# Architecture
![Student-Management-Day2](https://github.com/mail2mrcm/springboot-microservices/assets/118661926/5008ee49-8046-4594-be9e-7d0861327e84)

# Build & Deploy
Please follow the same steps mentioned in **README.md**
> **Note**  
> This time Student service and Payment Service are deployed in dynamic port as there can be multiple instances of both services to manage load. `server.port = 0`

# Test
- Add Student using curl command:  [Step 1]  
**curl -X POST -H "Content-Type: application/json" -d '{"orgId": 1, "firstName": "chandan", "lastName": "maity", "gender": "Male"}'  http://localhost:9093/api/1.0/student/**
- Create Payment for the student using curl command:  [Step 2]  
**curl -X POST -H "Content-Type: application/json" -d '{"studentId": 1, "description": "Semister Fees", "amount": 5000.0, "status": "Pending"}' http://localhost:9093/api/1.0/payment/**
- Get Student details along with Payment:  [Step 3]  
![image](https://github.com/mail2mrcm/springboot-microservices/assets/118661926/9a306026-f281-4239-ac5e-645c28575422)
- Get Status of circuit Breaker information:  [Step 4]
> **Note**  
> Circuite Breaker is in CLOSE State  
![image](https://github.com/mail2mrcm/springboot-microservices/assets/118661926/6a3e136d-d0f0-4f59-b51d-a278a2efce35)
- Stop Payment service:  [Step 5]  
- Get Student details along with Payment: [step 6]
![image](https://github.com/mail2mrcm/springboot-microservices/assets/118661926/48a5b2cf-9320-4e4d-adf3-c286f5d67bfb)
> **Note**  
> Circuite Breaker is in HALF_OPEN State. Once it reaches to failed threshold mentioned in te configuration above.
![image](https://github.com/mail2mrcm/springboot-microservices/assets/118661926/696cf6ba-7a57-4c39-aff8-d430f068ff75)

- Get Student details along with Payment (6 times to make to make 100% failure): [step 7]
> **Note**  
> Circuite Breaker is in OPEN State.  
![image](https://github.com/mail2mrcm/springboot-microservices/assets/118661926/5489de4c-0cb2-44ca-8093-7970c4579045)
