# Routing Service

## Overview

Routing Service is a Spring Boot application that provides a REST endpoint to find a list of border crossings to get from one country to another. The data is loaded from a JSON file containing information about countries and their borders.

## Prerequisites

- JDK 21 or later
- Maven 3.6.0 or later

## Building the Project

To build the project, follow these steps:

1. **Clone the repository**

- git clone https://github.com/adlet-nyussupov/RoutingService
- cd RoutingService

2. **Build the project using Maven**
- mvn clean install
  
This command will compile the project, run the tests, and package the application into a JAR file.

## Running the Application

After building the project, you can run the application using the generated JAR file.

1. **Navigate to the target directory**
   
- cd target
  
2. **Run the JAR file**

- java -jar RoutingService-0.0.1-SNAPSHOT.jar
  
The application will start, and you should see log messages indicating that the application is running and listening on port 8080.

## Using the Routing Service

The Routing Service provides a REST endpoint to find routes between countries.

### Endpoint
- GET /routing/{origin}/{destination}
  
### Example Request
To find a route from Afghanistan (AFG) to South Africa (ZAF), you can use the following URL:

- http://localhost:8080/routing/AFG/ZAF
  
### Example Response
{
  "route": ["AFG", "PAK", "IND", "ZAF"]
}
If no land route is found, the service will return a 400 Bad Request response with an appropriate error message.

### Data Initialization
The application loads country data from the following URL during startup: 

- https://raw.githubusercontent.com/mledoze/countries/master/countries.json.

The data is loaded into an in-memory H2 database.

## Troubleshooting
### Common Issues
#### Port 8080 is already in use
If port 8080 is already in use, you can change the port by adding the following argument:

- java -jar RoutingService-0.0.1-SNAPSHOT.jar --server.port=8081
  
#### Application fails to start

Ensure that you have the correct JDK and Maven versions installed. Check the logs for any error messages and address them accordingly.
