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


## FAQ

> Q: The director of the largest hospital in the country (around 500.000 patients per year) wants to have an overview of the average age of the patients who visited the hospital, divided by sex and per month, for the past 10 years. He wants to see this information immediately (<200ms). Describe in detail how you would fulfill this request.

**Idea#1**: The idea is to have this data pre-calculated somehow. Initial thought would be to have a cron job that would run every month and store the information needed
in a new column called statistics in the `hospital` table. This column could be a json column that would hold all statistics information - in case in the future some other statistics are needed.

**Consideration idea#1**:
- We still need to open a connection with the database each time when need such information. _One quick fix is to have
a caching mechanism such as redis and update this information there, and when fetching the statistics fetch it from the cache_. If we are only storing statistics for this particular director it should be fine,
however, what if we need to calculate the statistics for all hospitals and cache them as well. At some point we cannot cache everyone, so who to evict or who to store in the cache?
- Running a cron job might be okay for such statistic but for other statistics where information needs to be more fresh might not be the ideal case.
- What if we want to do advanced analysis on our data, would hitting our server with more requests be ideal - it will overload our server.

**Idea#2**: With each new patient we fire an event containing our interesting data. This event is sent to an external tool that performs analysis on the data, for instance `amplitude`.
In this tool we can setup dashboards that display all kinds of statistics such as the average age of patients visiting a hospital divided by sex and per month. 

**Consideration Idea#2**:

- Sending a request for every patient visiting might not be ideal. It's better to do bulk requests instead.
  - How many patients before sending the bulk request?
    - What happens when that number is not reached? -> _Just send the request after x-time_.
  - Where to store the accumlated patient events to be sent?
    - cache? What happens if the cache fails or the system fails? Now we have lost those events.
- Consider the situation where sending the events to `amplitude` is failing for some reason, now our data or statistics are lost forever.

For the issues in **idea#2** we could try to utilize a messaging queue system such as `kafka`. Where every event is published and a subscriber would send the data to `amplitude`
and the messages are only removed from the queue/channels only when requests are successful. We can also rewind the history and reapply events if needed as well.   
