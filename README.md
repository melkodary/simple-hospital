# Simple Hospital Service

A simple service that utilizes gRPC.

## Features

- Create, modify, and delete hospitals. Patients should not be deleted if a hospital is deleted.
- Create, modify, and delete patients
- Register a patient in a hospital
- List all patients of a hospital
- List all hospitals in which a patient has been registered


## Project

It is a multi-module project containing:
 - [x] The interface - defines the protocol
 - [x] Server - defines the business logic and manages the database
 - [ ] Client - Makes requests to the server to add, modify & delete entities.

### Installation

Run `mvn -pl sh-interface,sh-server clean install`