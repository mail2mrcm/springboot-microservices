# springboot-microservices-with-cicuitbreaker
This projects contains additionly circuitbreaker process between studen service and payment service. All other components are same as **springboot-microservice-basic.** and details are mentioned in `README.md`.
# Circuit Breaker 
This is a microservice pattern and principles for creating fault isolation beteen services in case any of services perform abnormally. Consider a senario where Service A calls Service B and Service B is not responding or not available. Consequently Service A will wait for a period and finally it will throw exception to end users. So resources of the system are not properly being used, even if Service B is not avilable but Service A calls everytime and unnecessary utilizing computer resources.In such cases, circuit breaker can help by stopping request sending for a specific time, waiting for the timeout ends, enable a limited number of requests to check whether Serice B is working. If those requests succeed, it allows microservices to continue normal operations. If not, it will again start the timeout. This concept is being used in this project, in case payment service is down/unavailable then this will not be called by student service as per circutbreaker configuration.
# Circuit Breaker Configuration used in this project
- `sliding-window-type`: The number of requests is recorded and aggregated in the last “sliding-window-size” seconds.
- `failure-rate-threshold`: Trigger Circuit Breaker if at least “failure-rate-threshold” of requests have failed.
- `minimum-number-of-calls`: Records at least “minimum-number-of-calls” requests in the last “sliding-window-size” seconds before calculating the failure rate.
- `automatic-transition-from-open-to-half-open-enabled`: After ”wait-duration-in-open-state” seconds, Circuit Breaker will automatically transition from open to half-open state.
- `wait-duration-in-open-state`: If triggered, wait at least “wait-duration-in-open-state” seconds before allowing more calls.
- `permitted-number-of-calls-in-half-open-state`: After “wait-duration-in-open-state” time has passed, allow another “permitted-number-of-calls-in-half-open-state” requests and wait for them to calculate the failure rate again.
- `sliding-window-size`: Record the result of the last “sliding-window-size” seconds.
# Architecture
![Student-Management-Day2](https://github.com/mail2mrcm/springboot-microservices/assets/118661926/5008ee49-8046-4594-be9e-7d0861327e84)
