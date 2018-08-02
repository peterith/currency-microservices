# Curency Microservices

This repository contains a set of microservices for converting currencies. The microservices run on
Spring Boot applications and contain serveral Spring Cloud components such as Eureka naming
server, Ribbon client-side load balancer, Config server, Feign REST Client,

I am planning to expand on this project to obtain live currency exchange rates as well as setting up
additional Spring Cloud elements to improve the interactions between the microservices. I will also
update the README soon so that the the instructions for deploying the microservices are clearer (possibly
deploying from a virtual machine/container). Expect a lot more content to be added soon!

## Getting Started

Clone the repository into your local directory to get the source code.

### Prerequisites

You will need the following software installed on your computer:
* [Java 8](https://www.oracle.com/technetwork/java/javase)
* [Gradle](https://gradle.org) for building the project
* [IntelliJ](https://www.jetbrains.com/idea)/[Eclipse/STS](https://spring.io/tools) for running applications
on IDE
* [Postman](https://www.getpostman.com) for making easy API calls

## Deployment

Please deploy the microservices in the following order:
* spring-cloud-config-server
* netflix-eureka-naming-server
* currency-exchange-service
* currency-conversion-service
* netflix-turbine-dashboard-server

You can make a HTTP GET request for a JSON data of a specific pair of currencies for conversion rate
and the amount converted using the following URI:
```
http://localhost:8080/currency-converter-fallback/from/{from}/to/{to}/quantity/{quantity}
```
where *{from}* and *{to}* are placeholders for the following currency pairs:
* USD/INR
* EUR/INR
* AUD/INR

## Built With

* [Spring](https://spring.io) - The application framework used
* [Gradle](https://gradle.org) - The build automation system used

## Contributing

Please submit a pull request or directly message me for any ideas or comments you would like to share.
I am an active member on GitHub and will constantly monitor my page.

## Acknowledgments

* To all of you for visiting my GitHub page
* Barclays and especially my team for providing me with a lot of challenges and guidance over the 9
weeks during the internship
* [in28Minutes](http://www.in28minutes.com) for their tutorials on Spring framework and microservices
