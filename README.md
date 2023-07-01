
# Selenium Assignment

This project contains automated tests using Selenium for a web application. The tests are written in Java and use Cucumber for test execution and reporting. The purpose of this project is to demonstrate the usage of Selenium for web application testing.

## Setup

To run the tests locally, follow these steps:

1. Clone the repository: https://github.com/GEM-SHIVAM-ROY/SeleniumAssignment.git

2. Install dependencies:
- Ensure you have Java JDK installed.
- Install Apache Maven: [Maven Installation Guide](https://maven.apache.org/install.html).

3. Build the project:
- Open a terminal or command prompt.
- Navigate to the project root directory.
- Run the following command to build the project and download dependencies:
- mvn clean install

4. Configure WebDriver:
- Download the appropriate WebDriver executable for your target browser (e.g., ChromeDriver for Google Chrome) and place it in the project's `src/test/resources` directory.

5. Run the tests:
- Execute the following command to run the tests:
  mvn test
  
6. View test reports:
- After the test execution completes, the test reports will be available in the `target` directory.
- Open the HTML report located at `target/cucumber-reports/index.html` in a web browser to view the detailed test results.

## Project Structure

The project structure is organized as follows:

- `src/test/java`: Contains the test automation code written in Java.
- `StepDefinition`: Contains Cucumber step definitions for test scenarios.
- `DriverClass`: Contains WebDriver setup and configuration.
- `src/test/resources`: Contains test data and WebDriver executables.
- `features`: Contains Gherkin feature files that define the test scenarios.

## Dependencies

The project utilizes the following dependencies:

- Selenium WebDriver: Framework for automating web browsers.
- Cucumber: Behavior-Driven Development (BDD) tool for test execution and reporting.
- Apache Maven: Build automation and dependency management tool.

## Contributing

Contributions to this project are welcome! If you find any issues or want to add new features, please submit a pull request. For major changes, please open an issue to discuss the proposed changes beforehand.

   
