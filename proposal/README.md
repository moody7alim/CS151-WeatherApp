<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [Weather App](#weather-app)
  - [Team 10](#team-10)
  - [Team Members working on the proposal](#team-members-working-on-the-proposal)
  - [State the problem/issue to resolve](#state-the-problemissue-to-resolve)
  - [Briefly survey previous works if any (include references)](#briefly-survey-previous-works-if-any-include-references)
  - [Approach](#approach) 
  - [Assumptions](#assumptions)
  - [Operating Environments](#operating-environments)
  - [Intended Usage](#intended-usage)
  - [High-level Description of Solution](#high-level-description-of-solution)
  - [Functionality](#functionality)
  - [Operations](#operations)
  - [References](#references)

<!-- /code_chunk_output -->

<!-- In less than 2 pages, propose a topic in object-oriented design as your project.  Your proposal must include (but not be limited to) the following sections: 
• Project title 
• Team #, team members 
• Team members working on the proposal 
• State the problem/issue to resolve 
• If applicable, briefly survey previous works if any (include references) 
• If applicable, describe assumptions / operating environments / intended usage 
• High-level description of your solution which may include (but is not limited to), your plan and approach.  Be as specific as possible. 
• Functionality: describe how your solution tackles the issues 
• Operations: List operations for each intended user (in list format).  Be precise and specific. 
• (Optional) References: must include citations in content using the format [1], [2], etc. 
Be mindful that we are using java. So we're probably gonna be doing spring boot and JavaFX app-->


# Weather App

## Team 10


<!-- create a list of names -->
## Team Members working on the proposal

- Mohamed Halim (Group Leader)
- Huu Hung Nguyen
- Diego Quezada
- Ngoc Ly Tran

## State the problem/issue to resolve

- The problem we are trying to solve is to create a weather app that can display the weather of a city. The app will be able to display the current weather, the weather for the next 5 days, and the weather for the next 24 hours. This included the temperature, humidity, wind speed, and the weather condition. The app will also be updated every 10 minutes to display the most recent weather information. 


## Briefly survey previous works if any (include references)

- Multiple weather applications, such as AccuWeather, Weather Underground, and The Weather Channel app, provide extensive weather data but may lack personalized user experiences or intuitive alert systems [AccuWeather].


## Approach

- The team is going to build a spring boot api that manages the back-side of the application, while accessing that through a JavaFX application.


## Assumptions

- Weather data is available from an external API; users have internet connectivity.


## Operating Environments

- The system should run on environments that support Java and have network access.


## Intended Usage

- Users will access the JavaFX application to view weather details fetched through the Spring Boot server from an external API.


## High-level Description of Solution

- Spring Boot Server:
    - Implement a REST API that communicates with an external weather API to retrieve weather data.
    - Expose endpoints to be consumed by the JavaFX application.

- JavaFX Application:
-- Develop a user interface to display the weather information.
-- Implement functionalities to request data from the Spring Boot server.


## Functionality

- Backend (Spring Boot):
    - Communicate with an external weather API and fetch real-time data.
    - Parse and possibly store the fetched data, exposing relevant weather information through a REST endpoint.

- Frontend (JavaFX):
    - Present a user-friendly interface to allow users to request weather information.
    - Communicate with the backend to fetch the requested data and display it to the user.


## Operations

- End-User:
    - Open the JavaFX application.
    - Input data if necessary (like location) to fetch the weather info.
    - View the displayed weather information.

- Admin User (if an admin panel or similar is implemented):
    - Monitor the server health and status.
    - Manage API keys or credentials for external weather API access.
    - Update/Modify available locations or other configurable settings in the app.

- Developer/Maintenance User:
    - Deploy the Spring Boot application on a server.
    - Ensure the continuous running of both the server and application, with possible logging and error tracking.
    - Periodically update the application for security and functionality enhancements.


### References
- [AccuWeather](https://www.accuweather.com/)
- [Weather Underground](https://www.wunderground.com/)
- [The Weather Channel](https://weather.com/)
- [Baeldung."Building a RESTful Web Service with Spring Boot"](https://www.baeldung.com/spring-boot-start)