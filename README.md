# Withdrawal Processing Service
This project is a rewritten solution for a legacy withdrawal processing system. It modernizes the API, database interaction, and overall architecture while adhering to SOLID principles and meeting the business requirements outlined in the challenge.

## Features
### User Management:

Users can have multiple payment methods.
Users can initiate withdrawal requests using any of their payment methods.

### Withdrawal Requests:

Supports two types of withdrawals:
Immediate: To be processed as soon as possible.
Scheduled: To be processed at a specific later time.
Withdrawal requests are stored in the database with a PENDING status.
Events are emulated and sent to a queue for further processing.
Sends asynchronous transaction requests to a payment provider.

### Modern Technology:

Built with Kotlin and follows modern software design practices.
Uses an embedded SQL database for ease of setup and testing.
Comprehensive testing for critical functionality.

### Architecture
The system follows a hexagonal architecture (ports and adapters), ensuring high testability and adherence to SOLID principles.

### Layers:
#### Controller Layer:

Handles API requests.
Maps DTOs to domain objects and vice versa.

#### Service Layer:

Contains core business logic, including withdrawal processing.
Ensures proper state transitions (PENDING, PROCESSING, etc.).

#### Repository Layer:

Manages data persistence using an embedded SQL database.
Interfaces with entities mapped via JPA.

#### Event Layer:

Simulates sending events to a queue.
Abstracted to make the implementation extensible to real-world messaging systems like Kafka or RabbitMQ.

#### Database
The system uses an embedded H2 database for storage. Key entities include:

####  Business Logic
Processing a Withdrawal:

Validate user and payment method.
Create and persist a withdrawal in the PENDING state.
Simulate sending an event to an external queue.
Simulate sending a transaction request to a payment provider asynchronously.
Return the persisted withdrawal.

### Scheduled Withdrawals:

A periodic job processes withdrawals scheduled for the current time.
Updates the status to PROCESSING.
Sends the event and transaction request.
