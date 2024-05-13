#Load Balancer Demo API

- This repository contains a demo implementation of a load balancer API in Java Spring Boot. The load balancer distributes incoming requests across multiple instances of a server to improve performance and reliability.
- This loadbalancer also have the capablity for creating new instances of the demoServer and kill the server if not needed
- It also have the monitoring feature for instances of the demoServer 

#Controllers

Controller: This controller handles incoming requests to the load balancer API.
- GET /service/v1/demos: Retrieves data from a server instance.
- POST /service/v1/demos/active/{activeNumber}: Activates a specified number of server instances.
- DELETE /service/v1/demos/port/{portId}: Deactivates a server instance based on its port number.
- PUT /service/v1/demos/algo/{algoName}: Sets the load balancing algorithm to be used.
- GET /service/v1/demos/server?status={status}: Retrieves a list of active or available server instances.

#Usage

- Start the load balancer application.
- Send requests to the load balancer API (GET, POST, DELETE, PUT) to interact with the server instances.
- The load balancer will distribute incoming requests to the appropriate server instances based on the configured algorithm.
- Monitor the status of active and available server instances using the provided endpoints.

#Dependencies

- Spring Boot
- WebClient
- WebClientResponseException

#Steps

- Clone this repository to your local machine.
- Ensure you have Java 17 and Maven 1.6> installed.
- Build the project using Maven: mvn clean install.
- Run the application: java -jar <jar-file-name>.jar.
- Access the load balancer API using the provided endpoints.

#Contributing

- Contributions to this project are welcome. Feel free to submit issues, suggest improvements, or open pull requests.

#License

- This project is licensed under the MIT License. See the LICENSE file for details.
