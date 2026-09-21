# Selenium Cucumber Portfolio

![Selenium Cucumber Tests](https://github.com/SearchSanctuary/SeleniumCucumberPortfolio/actions/workflows/maven-tests.yml/badge.svg)

A UI test automation framework built with **Java 21, Selenium WebDriver, Cucumber, JUnit 5 and Maven**, using [Sauce Demo](https://www.saucedemo.com/) as the application under test.

I built this to show how I approach UI automation as an SDET, with a focus on maintainable structure, reliable synchronisation, parallel execution, failure diagnostics, and CI/CD integration. The framework is intentionally practical rather than over-engineered: every abstraction here exists to solve a specific maintainability or execution problem, not to show off.

## Contents

* [Why This Framework Is Structured This Way](#why-this-framework-is-structured-this-way)
* [Technology Stack](#technology-stack)
* [Project Structure](#project-structure)
* [Test Design](#test-design)
* [Scenario Outlines and Reusable Test Data](#scenario-outlines-and-reusable-test-data)
* [Test Tags](#test-tags)
* [Parallel Execution](#parallel-execution)
* [Synchronisation Strategy](#synchronisation-strategy)
* [Handling Application-Specific UI Behaviour](#handling-application-specific-ui-behaviour)
* [Failure Diagnostics and Reporting](#failure-diagnostics-and-reporting)
* [CI/CD](#cicd)
* [Configuration](#configuration)
* [Key Design Decisions](#key-design-decisions)
* [What This Project Demonstrates](#what-this-project-demonstrates)
* [Running Locally](#running-locally)
* [Future Improvements](#future-improvements)

---

## Why This Framework Is Structured This Way

The framework separates behaviour from implementation:

```text
Gherkin behaviour
       ↓
Step definitions
       ↓
Page Objects
       ↓
Selenium WebDriver
       ↓
Browser
```

Gherkin describes **what** the user is doing. Step definitions translate that into test actions. Page Objects own the actual Selenium interactions. This means a change to a page locator stays inside its Page Object and never touches a feature file.

Cross-cutting concerns, browser lifecycle, configuration, and failure handling, are also kept out of the test logic itself, so they can change independently of the scenarios that use them.

The goal isn't the largest possible framework. It's one that's easy to understand, maintain, and diagnose when something fails.

## Technology Stack

| Technology                | Purpose                           |
| ------------------------- | --------------------------------- |
| Java 21                   | Programming language              |
| Selenium WebDriver 4.35.0 | Browser automation                |
| Cucumber 7.27.2           | BDD and executable specifications |
| JUnit 5                   | Test execution platform           |
| Maven                     | Build and dependency management   |
| GitHub Actions            | CI/CD                             |
| Chrome                    | CI browser                        |
| Git                       | Version control                   |

## Project Structure

```text
src/test/
├── java/
│   ├── config/
│   │   └── Config.java
│   │
│   ├── hooks/
│   │   ├── DriverManager.java
│   │   └── Hooks.java
│   │
│   ├── pages/
│   │   ├── LoginPage.java
│   │   ├── ProductsPage.java
│   │   ├── CartPage.java
│   │   ├── CheckoutPage.java
│   │   ├── CheckoutOverviewPage.java
│   │   └── OrderConfirmationPage.java
│   │
│   ├── runners/
│   │   └── CucumberTest.java
│   │
│   ├── steps/
│   │   ├── LoginSteps.java
│   │   ├── ProductSteps.java
│   │   ├── CartSteps.java
│   │   ├── CheckoutSteps.java
│   │   ├── CheckoutOverviewSteps.java
│   │   └── ConfirmationSteps.java
│   │
│   └── testdata/
│
└── resources/
    └── features/
        ├── login.feature
        ├── cart.feature
        └── checkout.feature
```

**Feature files** describe user behaviour and expected outcomes in business-readable Gherkin.

**Step definitions** translate Gherkin into test actions and delegate browser interaction to Page Objects, while assertions validate the expected outcome. They don't contain low-level Selenium code themselves.

**Page Objects** hold the locators and interactions for a single page or screen.

**DriverManager** creates, provides, and cleans up WebDriver instances, using `ThreadLocal<WebDriver>` to keep browsers isolated during parallel execution.

**Hooks** manage scenario setup and teardown and capture a screenshot when a scenario fails.

**Config** controls execution settings through Java system properties.

**Runner** configures Cucumber's glue, feature discovery, reporting, and parallel execution.

## Test Design

The suite focuses on meaningful user journeys rather than maximising raw test count, and covers both positive and negative paths.

**Login**

* Successful login
* Invalid login
* Login error validation

**Shopping Cart**

* Add products to the cart, including multiple products
* Verify cart contents
* Remove products and verify they're no longer present

**Checkout**

* Complete checkout, including with multiple products
* Validate missing customer information and checkout errors
* Cancel checkout and return to the cart
* Validate order overview, order total, and order confirmation

## Scenario Outlines and Reusable Test Data

Where the same behaviour needs checking against different products, I use Cucumber Scenario Outlines rather than duplicating near-identical scenarios:

```gherkin
Scenario Outline: Add product to shopping cart
  Given I am on the login page
  When I login with valid credentials
  And I add the "<product>" to the cart
  And The cart should contain 1 item
  And I open the shopping cart
  Then I should see the "<product>" in the cart

Examples:
  | product                  |
  | Sauce Labs Backpack      |
  | Sauce Labs Bike Light    |
  | Sauce Labs Bolt T-Shirt  |
```

This keeps the feature file readable while still exercising the behaviour against multiple data sets. Outlines are used where parameterisation genuinely improves coverage, not as a default for every scenario.

Feature files stay written around **behaviour**, not implementation, e.g. `When I proceed to checkout` rather than `When I click the checkout button`. The Page Object layer is what decides how that behaviour actually happens, which keeps the feature files useful as executable specifications rather than a second form of automation code.

## Test Tags

| Tag           | Purpose                        |
| ------------- | ------------------------------ |
| `@smoke`      | Critical happy-path coverage   |
| `@regression` | Broader regression coverage    |
| `@negative`   | Validation and error scenarios |
| `@cart`       | Shopping-cart scenarios        |
| `@checkout`   | Checkout scenarios             |

```bash
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@regression"
mvn test -Dcucumber.filter.tags="@negative"
mvn test -Dcucumber.filter.tags="@checkout"
```

This lets CI or local development run an appropriate subset without maintaining separate test suites.

## Parallel Execution

```text
cucumber.execution.parallel.enabled=true
cucumber.execution.parallel.config.fixed.parallelism=2
```

Each executing thread gets its own WebDriver via `ThreadLocal<WebDriver>`, so scenarios running concurrently never share browser state:

```text
Scenario A ──→ Thread 1 ──→ WebDriver 1
Scenario B ──→ Thread 2 ──→ WebDriver 2
```

The driver lifecycle:

```text
Scenario starts → @Before → DriverManager.startDriver() → Scenario execution
→ @After → Screenshot if failed → DriverManager.quitDriver() → ThreadLocal removed
```

Removing the driver from `ThreadLocal` after quitting stops stale references from hanging around on the executing thread.

**Why 2, specifically?** It's a deliberately conservative number. The point here is to demonstrate parallel execution safely, not to squeeze out maximum speed, so I picked a value that avoids browser contention on the CI runner rather than one tuned for throughput. In a larger production suite I'd set parallelism based on measured suite duration, runner resources, and how much concurrent load the application under test can actually handle, not just "more is faster."

## Synchronisation Strategy

The framework uses **explicit waits** instead of fixed sleeps, waiting for conditions like element visibility, clickability, or a specific application state:

```java
wait.until(
    ExpectedConditions.elementToBeClickable(button)
).click();
```

The default wait is 10 seconds and can be overridden without touching the source:

```bash
mvn test -DexplicitWait=20
```

## Handling Application-Specific UI Behaviour

Some interactions in Sauce Demo proved unreliable with native Selenium clicks during development. Rather than hide that or switch every click to JavaScript by default, the framework uses JavaScript only for the specific interactions affected:

1. Locate the element
2. Wait for the expected state
3. Scroll it into view if needed
4. Perform the JavaScript click
5. Verify the resulting application state

For example, after adding a product to the cart, the framework checks that the corresponding **Remove** button actually becomes available, rather than assuming the click worked. JavaScript is a targeted workaround for an observed problem here, not the default interaction mechanism.

## Failure Diagnostics and Reporting

Failed scenarios automatically capture a screenshot through the `@After` hook, which gets attached to the scenario in the generated Cucumber HTML report:

```text
Scenario fails → @After hook → Screenshot captured → Attached to scenario → Cucumber HTML report
```

This matters most in CI, where the browser itself isn't available to inspect directly. The report (`target/cucumber-reports/cucumber.html`) includes scenario and step-level results, failure information, and screenshots, and CI uploads the whole report directory as a workflow artifact.

## CI/CD

GitHub Actions runs the suite on pushes and pull requests targeting `master`:

1. Checks out the repository
2. Sets up Temurin Java 21
3. Enables Maven dependency caching
4. Runs the suite in headless Chrome
5. Uploads the Cucumber report as an artifact

```yaml
- name: Run tests
  run: mvn test -Dheadless=true
```

The same `mvn test` command works locally and in CI; headless mode is the main difference.

## Configuration

| Property       | Default        | Description                      |
| -------------- | -------------- | -------------------------------- |
| `baseUrl`      | Sauce Demo URL | Application under test           |
| `browser`      | `chrome`       | Browser to execute against       |
| `headless`     | `false`        | Run without a visible browser UI |
| `explicitWait` | `10`           | Explicit wait timeout in seconds |

```bash
mvn test
mvn test -Dheadless=true
mvn test -Dbrowser=chrome -Dheadless=true
mvn test -DexplicitWait=20
```

Keeping these as system properties means environment and execution settings stay out of the test implementation entirely.

## Key Design Decisions

**Page Object Model.** Step definitions call methods like `checkoutPage.enterCustomerInformation(firstName, lastName, postCode)` instead of touching Selenium locators directly, so a page change usually stays isolated to its Page Object.

**Step definitions split by functional area** (`LoginSteps`, `ProductSteps`, `CartSteps`, `CheckoutSteps`, `CheckoutOverviewSteps`, `ConfirmationSteps`), mirroring the application's own structure instead of piling everything into one large class.

**Checkout split into separate Page Objects** (`CheckoutPage` → `CheckoutOverviewPage` → `OrderConfirmationPage`), one per screen, rather than one class trying to own the whole flow.

**Actions and assertions kept separate.** A `When` step performs an action; a `Then` step verifies the result. No hidden assertions inside action methods, so scenario intent stays explicit.

**Thread-safe driver management.** `ThreadLocal<WebDriver>` over a single shared instance, so parallel scenarios never share browser state, with the reference removed during teardown.

**Explicit waits over sleeps**, for reliability and to avoid wasting time on arbitrary delays.

**JavaScript clicks scoped narrowly**, used only where native clicks proved unreliable, and always followed by a state check rather than an assumption that the click succeeded.

**Configuration through system properties**, so execution behaviour (headless mode, browser, wait timeouts) can change without touching Java source, in both local and CI runs.

## What This Project Demonstrates

* Java-based UI automation with Selenium WebDriver
* Cucumber BDD and Gherkin scenario design, including Scenario Outlines
* Page Object Model
* Positive and negative testing
* Explicit waits and thread-safe parallel execution
* Cucumber tagging
* Failure screenshots and HTML reporting
* Maven and GitHub Actions CI/CD
* Headless browser execution
* Application-specific automation troubleshooting
* Framework design for maintainability

## Running Locally

```bash
mvn test
mvn test -Dheadless=true
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@negative"
```

The Cucumber report is generated at `target/cucumber-reports/cucumber.html`.

## Future Improvements

* Cross-browser execution in CI
* Dockerised browser execution
* Environment-specific configuration
* Additional API/UI integration coverage
* Enhanced reporting
* More extensive test-data management for larger suites
* Integration with a cloud browser platform

None of these are missing by oversight, they just haven't had a clear testing or engineering need behind them yet. The current framework is focused on being maintainable and practical rather than demonstrating every possible feature at once.

---

**Repository:** [SeleniumCucumberPortfolio](https://github.com/SearchSanctuary/SeleniumCucumberPortfolio)
