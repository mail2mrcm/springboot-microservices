- # springboot-microservices-basic
  _This is a student a mangemnt microservices based project. It uses various spring components to create microservices style architecture._
  It contains :
  - `Spring Config server` - This is used to externalize application configurations.
  - `Netflix Eureka as Service discvery` - This is for service registry. 
  - `Spring CLoud API Gateway` - Gateway of all microservices.  All srevice to be accessed through tis gateway. 
  - `Stident Miroservice `- add/get/update student and also perform internal coounication with Payment microservice to fetch payment status of the student. 
  - `Payment Microservice` - add/make payment.

```diff
- Please refer architecture digram for clear understanding of whole project.
![Student_Management](https://github.com/mail2mrcm/tech4all/assets/118661926/d4cdafb3-2174-45e4-9803-4a58fdff0780)


```
