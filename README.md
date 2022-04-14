# Automated Parking Lot

### Functional Requirement:

- Design and Implement a backend system for an automated parking lot.

## Getting Started

Instruction to set up application locally for running, compiling and testing

### Prerequisites

1. Java 11
2. Maven

To Download dependencies, Run below command from project root
```shell
mvn install
```

### Build

Build - Run below command from project root
```shell
mvn compile
```

## Run tests

```shell
mvn test
```

## Deployment

To run from the command line

```shell
mvn package
java -jar target/parking-automater-0.0.1-SNAPSHOT.jar
```

## Output

1 - Allocate suitable slot and return ticket

Request

    API - v1/automatic/parking/allocate/{entrance}


Request Body 

```json 
{
   "vinNumber":1234,
   "height":5.2,
   "weight":1000,
   "type":"car"
}
```

Response

```json
{
  "vehicleSlotId": 16,
  "amount": 25.0,
  "gift": "Gift: Car Wash"
}
```
