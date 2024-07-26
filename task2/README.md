# Instructions for Running the Spring Boot Web Application

## Prerequisites

1. **Java Development Kit (JDK)**: Ensure you have Java version 15 or higher installed on your machine.
2. **PostgreSQL**: The application requires a PostgreSQL database. Ensure PostgreSQL is installed and running on your machine.
3. **Maven**: This project uses Maven for dependency management and building.

## Database Configuration

Before running the application, configure your database settings in the `application.properties` file within the project. You need to set the database URL, username, and password as per your PostgreSQL setup.

## Setup and Execution

1. **Clone the project**:  
   Correctly clone the repository to your local machine using the following command:  
   `git clone https://your-repository-url`  
   `cd your-project-folder`

2. **Build the project with Maven**:  
   Navigate to the root directory of the project where the `pom.xml` file is located and run:  
   `mvn clean install`  
   This command compiles the project and prepares it for running, ensuring everything is set up correctly.

3. **Run the application**:  
   To start the application, use the following Maven command:  
   `mvn spring-boot:run`  
   This will start the Spring Boot application and host it locally.

## Accessing the Application

After starting the application, you can access it by navigating to `http://localhost:8080` in your web browser. The application will serve the content from the root URL.