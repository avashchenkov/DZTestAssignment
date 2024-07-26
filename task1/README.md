# Instructions for Running the Java Project Tests

## Prerequisites

Ensure you have Java version 15 or higher installed on your machine.

## Setup and Execution

1. **Clone the project**:  
   Correctly clone the repository to your local machine using the following command:  
   `git clone https://your-repository-url`  
   `cd your-project-folder`

2. **Build the project with Maven**:  
   Navigate to the root directory of the project where the `pom.xml` file is located and run:  
   `mvn clean install`  
   This command compiles the project and prepares it for testing, ensuring everything is set up correctly.

3. **Run the tests**:  
   To execute the tests, use the following Maven command:  
   `mvn test`  
   This will run all the unit tests included in the project. You can observe the effects of the tests and verify the behavior of your project through the test outputs provided in the console after execution.