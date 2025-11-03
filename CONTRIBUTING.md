# Contributing to NeuroControl Middleware

First off, thank you for considering contributing to NeuroControl Middleware! It's people like you that make this project such a great tool for the brain-computer interface community.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [How Can I Contribute?](#how-can-i-contribute)
  - [Reporting Bugs](#reporting-bugs)
  - [Suggesting Enhancements](#suggesting-enhancements)
  - [Pull Requests](#pull-requests)
- [Development Setup](#development-setup)
- [Style Guidelines](#style-guidelines)
  - [Git Commit Messages](#git-commit-messages)
  - [Java Style Guide](#java-style-guide)
- [Testing](#testing)

## Code of Conduct

This project and everyone participating in it is governed by our [Code of Conduct](CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code. Please report unacceptable behavior to mohamed.medhat2121@gmail.com.

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the existing issues to avoid duplicates. When you create a bug report, include as many details as possible:

- **Use a clear and descriptive title**
- **Describe the exact steps to reproduce the problem**
- **Provide specific examples** to demonstrate the steps
- **Describe the behavior you observed** and what behavior you expected
- **Include screenshots** if relevant
- **Include your environment details**: Java version, Android version, Neurosky Mindwave2 firmware version

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion, please include:

- **Use a clear and descriptive title**
- **Provide a step-by-step description** of the suggested enhancement
- **Provide specific examples** to demonstrate the steps
- **Describe the current behavior** and **explain the behavior you'd like to see**
- **Explain why this enhancement would be useful**

### Pull Requests

1. Fork the repository and create your branch from `main`
2. If you've added code that should be tested, add tests
3. If you've changed APIs, update the documentation
4. Ensure the test suite passes
5. Make sure your code follows the existing style
6. Issue the pull request!

## Development Setup

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- Gradle 7.0+ (optional)
- Android SDK
- Neurosky Mindwave2 SDK

### Setup Steps

1. **Clone your fork**:
   ```bash
   git clone https://github.com/YOUR-USERNAME/NeuroControl-Middleware.git
   cd NeuroControl-Middleware
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```
   
   Or using Gradle:
   ```bash
   ./gradlew build
   ```

3. **Run tests**:
   ```bash
   mvn test
   ```
   
   Or using Gradle:
   ```bash
   ./gradlew test
   ```

## Style Guidelines

### Git Commit Messages

- Use the present tense ("Add feature" not "Added feature")
- Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
- Limit the first line to 72 characters or less
- Reference issues and pull requests liberally after the first line
- Consider starting the commit message with an applicable emoji:
  - 🎨 `:art:` when improving the format/structure of the code
  - 🐛 `:bug:` when fixing a bug
  - ✨ `:sparkles:` when introducing new features
  - 📝 `:memo:` when writing docs
  - 🚀 `:rocket:` when improving performance
  - ✅ `:white_check_mark:` when adding tests
  - 🔒 `:lock:` when dealing with security

### Java Style Guide

- Follow standard Java conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public methods and classes
- Keep methods focused and concise
- Use proper exception handling
- Prefer composition over inheritance where appropriate

**Example:**

```java
/**
 * Processes brainwave data from the Neurosky headset.
 *
 * @param rawData the raw brainwave data
 * @return processed signal data
 * @throws InvalidDataException if the data format is invalid
 */
public SignalData processData(byte[] rawData) throws InvalidDataException {
    // Implementation
}
```

## Testing

- Write unit tests for new functionality
- Ensure all tests pass before submitting a pull request
- Aim for good test coverage, especially for critical components
- Use meaningful test names that describe what is being tested

**Example test structure:**

```java
@Test
public void testProcessData_ValidInput_ReturnsProcessedData() {
    // Arrange
    byte[] testData = createTestData();
    
    // Act
    SignalData result = processor.processData(testData);
    
    // Assert
    assertNotNull(result);
    assertEquals(expectedValue, result.getValue());
}
```

## Questions?

Feel free to open an issue with your question or reach out to the maintainers directly.

Thank you for contributing! 🎉
