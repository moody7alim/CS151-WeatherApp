<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [Weather App](#weather-app)
  - [Team 10](#team-10)
  - [Team Members working on the proposal](#team-members-working-on-the-proposal)
  - [Team Members working on the project presentation](#team-members-working-on-the-project-presentation)
  - [Team Members working on the project (code) and report](#team-members-working-on-the-project-(code)-and-report)
  - [Problem/issue](#problemissue)
  - [Briefly survey previous works](#briefly-survey-previous-works)
  - [Approach](#approach) 
  - [Assumptions](#assumptions)
  - [Operating Environments](#operating-environments)
  - [Intended Usage](#intended-usage)
  - [Diagrams](#diagrams)
  - [Functionality](#functionality)
  - [Operations](#operations)
  - [Solution](#solution)
  - [Steps to run your code](#steps-to-run-your-code)
  - [Snapshot of the running program](#snapshot-of-the-running-program)
  - [References](#references)


# Weather App

## Team 10

- Mohamed Halim (Group Leader)
- Huu Hung Nguyen

## Team Members working on the proposal

- Mohamed Halim (Group Leader)
- Huu Hung Nguyen

## Team members working on the project presentation

- Mohamed Halim (Group Leader)
- Huu Hung Nguyen

## Team members working on the project (code) and report
- Mohamed Halim (Group Leader) (Backend Development - Spring Boot)
   - Develop and Maintain Backend Logic:
       - Implement the core logic for data processing and business rules in `SpringApiApplication.java`.
       - Develop RESTful APIs to serve data to the front end.

   - Integrate External APIs:
       - Handle integration with external services like weather data APIs.
       - Manage API keys and sensitive data securely.

   - Database Management:
       - Design and maintain the database schema.
       - Implement data access layers and manage database connections.

   - Authentication and Security:
       - Implement user authentication and authorization.
       - Ensure the security of the backend, including data protection and secure API endpoints.

   - Testing:
       - Write and maintain unit and integration tests for the backend.
       - Ensure the robustness and reliability of backend services.

   - Deployment:
       - Handle the deployment of the backend application.
       - Manage cloud services or server environments as required.

   - Performance Optimization:
       - Monitor performance and optimize backend operations.
       - Implement caching, query optimization, and other performance enhancement techniques.
      
- Huu Hung Nguyen (Frontend Development - JavaFX)
   - Develop and Maintain Frontend Interface:
       - Implement the user interface in `HelloApplication.java`.
       - Design and develop user-friendly screens, dialogs, and visual elements.

   - Frontend Logic:
       - Write frontend logic to handle user interactions.
       - Implement data validation and form submissions.

   - Connect with Backend:
       - Integrate RESTful API calls to communicate with the backend.
       - Handle data fetching, posting, and displaying real-time data from the backend.

   - User Experience (UX) and Design:
       - Focus on the application’s usability and visual design.
       - Ensure the interface is intuitive and accessible to users.

   - Testing:
       - Conduct front-end testing, including GUI and usability tests.
       - Ensure compatibility across different platforms and resolutions.
  
   - Optimization and Performance:
       - Optimize frontend performance, ensuring quick load times and smooth interactions.
       - Implement responsive design for different screen sizes.

   - Deployment:
       - Package and deploy the JavaFX application.
       - Manage versioning and updates of the frontend application.

## Problem/issue

- The problem we are trying to solve is to build a weather application that can provide real-time weather updates for a specific city. The application will display important information such as the city's name, date, and time, an icon indicating the current weather conditions, and the temperature in Fahrenheit or Celsius. It will also feature details such as sunrise time, humidity levels, and wind speed. The application will include a language-switching feature to cater to a diverse user base, making it more accessible to users from different regions. Moreover, the app will have user authentication functionality, allowing individuals to sign up and log in to access personalized profiles. We build the application's backend using Spring Boot to manage data processing, security, and API interactions. At the same time, the front end will be crafted using JavaFX, ensuring a seamless and engaging user experience. Integrating these technologies allows the application to operate smoothly, balancing robust backend functionalities with a user-friendly frontend design.

## Briefly survey previous works

- Multiple weather applications, such as AccuWeather, Weather Underground, and The Weather Channel app, provide extensive weather data but may lack personalized user experiences or intuitive alert systems [AccuWeather].


## Approach

- The team will build a spring boot API that manages the back side of the application while accessing that through a JavaFX application.


## Assumptions

- Reliable Internet Connectivity: The application assumes reliable Internet access for real-time weather data and user authentication.
- API Availability: It relies on external APIs (like OpenWeatherMap) for weather data, assuming these services are regularly available and provide accurate, up-to-date information.
- User Device Capability: Assumes users have devices capable of running a JavaFX application, which typically includes modern computers with sufficient processing power and memory.
- Language Preferences: The app anticipates a diverse user base with different language preferences, necessitating the language switching feature.
- User Familiarity: Assumes a basic level of user familiarity with digital applications, particularly in navigating interfaces for login, signup, and data interpretation.

## Operating Environments

- Cross-Platform Compatibility: Designed to operate on multiple platforms (Windows, macOS, Linux), given JavaFX's cross-platform nature.
- Backend Server Requirements: The Spring Boot backend requires a server environment, potentially cloud-based, with adequate resources to handle API requests, user authentication, and data processing.
- Database Integration: Assumes integration with a database (like MongoDB) for user data storage, necessitating a stable database environment.


## Intended Usage

- Personal Use: Ideal for individuals seeking real-time weather updates and personalized weather information based on location preferences.
- Language Accessibility: Tailored for a global audience with multiple language options, making it accessible for non-English speaking users.
- Educational/Informational Purposes: Can be used in educational settings or for informational purposes, where understanding weather patterns is crucial.
- Travel Planning: Useful for travelers planning their trips based on weather conditions in different cities.

## Diagrams

- [UML diagrams](https://github.com/moody7alim/CS151-WeatherApp/blob/main/diagrams/README.md)

## Functionality

- Backend (Spring Boot):
    - Weather API Communication: The backend communicates with an external weather API like OpenWeatherMap. It sends requests to this API to retrieve real-time weather data for specified locations.
    - Data Parsing and Storage: Once the weather data is fetched, the backend parses this information, extracting critical details like temperature, humidity, wind speed, and sunrise time. Depending on the application's design, this data can be temporarily stored or directly relayed.
    - REST Endpoint Exposure: The backend exposes RESTful endpoints. These endpoints allow the front end to request and receive weather information. The data is typically formatted in JSON, providing a standardized way for the front end to interpret and display it.

- Frontend (JavaFX):
    - User Interface for Data Request: The JavaFX frontend provides a user-friendly interface where users can input their desired locations for weather updates. This may include text fields for city names, options to choose units (Celsius or Fahrenheit), and language selection.
    - Backend Communication for Data Retrieval: The front end communicates with the backend through HTTP requests to the exposed REST endpoints. Once a user requests weather data, the frontend sends this request to the backend, fetching and returning the relevant data.
    - Displaying Weather Information: After receiving the weather data from the backend, the frontend displays this information to the user. This display includes graphical elements like weather condition icons and neatly formatted text showing temperatures, humidity, wind speed, etc.

## Operations

- General Users (Accessing Weather Information)
    - Open Application: Launch the JavaFX application on their device.
    - View Default Weather Data: See weather information for a default or previously selected city upon application launch.
    - Select City: Enter or select a city name to view its current weather.
    - Choose Units: Select preferred temperature units (Celsius or Fahrenheit).
    - Change Language: Use the language switcher to view the information in the preferred language.
    - View Weather Data: Observe the displayed weather data, including temperature, humidity, wind speed, sunrise time, and weather condition icons.
    - Refresh Data: Option to refresh weather data to get real-time updates.

- Users with Accounts (Personalized Experience)
    - Register/Sign Up: Create a new account by providing details like email, username, and password.
    - Login: Access their account using their credentials.
    - View Personalized Dashboard: See weather information for their saved or frequently viewed cities.
    - Manage Profile: Update personal information, change passwords, or manage saved cities.
    - Logout: Securely exit their account.

- Administrators (Application Maintenance and User Management)
    - Login to Admin Panel: Access the backend or admin interface with admin credentials.
    - Monitor Application Health: Check server status, API response times, and error logs.
    - Manage User Accounts: View, edit, or delete user accounts as necessary.
    - Update Weather API Configuration: Change API keys or settings for the external weather API.
    - Review User Feedback: Access and respond to user feedback or support requests.
    - Deploy Updates: Push new updates or fixes to the application.
    - Logout: Securely exit the admin panel.
      
## Solution


- Backend Development (Spring Boot)

    - API Integration**: Integrated the backend with an external weather API (like OpenWeatherMap) to fetch real-time weather data. This involved setting up HTTP client services within Spring Boot to make API calls and retrieve data based on user requests.

    - Data Processing**: Implemented methods to parse the JSON response from the weather API, extracting key details such as temperature, humidity, wind speed, and sunrise time. This data processing ensured that only relevant information was sent to the front end.

    - User Authentication**: Developed a secure user authentication system using Spring Security and JWT (JSON Web Tokens). This system allows users to register, log in, and maintain secure sessions.

    - REST API Creation**: Developed RESTful endpoints using Spring Boot, enabling the front to send requests (like city name for weather information) and receive formatted JSON responses.

    - Database Integration**: Integrated a MongoDB database for storing user information and preferences. This facilitated personalized experiences for registered users, like saving favorite cities.

- Frontend Development (JavaFX)

    - Interface Design**: Designed an intuitive and engaging user interface using JavaFX. This included input fields for city selection, buttons for unit conversion, and a language switcher.

    - Dynamic Content Rendering**: Implemented functionality to render weather data dynamically from the backend. This includes displaying temperature, weather icons, and other relevant information.

    - Interaction with Backend**: Established HTTP communication between the front and backend. The front end sends requests (like city names for weather data) to the backend and displays the received data.

    - User Interaction Features**: Added features for user interaction, such as the ability to switch between Celsius and Fahrenheit, change the application language, and view weather details of different cities.

    - User Authentication Integration**: Integrated login and registration functionalities, allowing users to create accounts, log in, and view personalized weather data.

## Steps to run your code

First, run the Spring Boot Backend - SpringApiApplication.java
Second, run the JavaFX Frontend - HelloApplication.java

## Snapshot of the running program

- Spring Boot Backend
<img width="1470" alt="image" src="https://github.com/moody7alim/CS151-WeatherApp/assets/79944868/70b0b944-9f64-44c0-b395-1d214d8afc36">

- JavaFX Frontend
<img width="1470" alt="image" src="https://github.com/moody7alim/CS151-WeatherApp/assets/79944868/d05549fc-323f-4342-8db2-cf164bbc34d4">
<img width="303" alt="image" src="https://github.com/moody7alim/CS151-WeatherApp/assets/79944868/b98f148b-9945-45b1-98db-7d73170c073f">
<img width="602" alt="image" src="https://github.com/moody7alim/CS151-WeatherApp/assets/79944868/47775077-55aa-49b6-86eb-1c9a933c9ff8">


### References
- [AccuWeather](https://www.accuweather.com/)
- [Weather Underground](https://www.wunderground.com/)
- [The Weather Channel](https://weather.com/)
- [Baeldung."Building a RESTful Web Service with Spring Boot"](https://www.baeldung.com/spring-boot-start)
