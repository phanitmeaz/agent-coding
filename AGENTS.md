# AGENTS.md

## Project

Spring Boot 4.1.1 / Java 17 / Maven (wrapper included). Single-module web app with JPA test scaffolding.

## Commands

- Build: `./mvnw package`
- Run: `./mvnw spring-boot:run` (or `java -jar target/demo-0.0.1-SNAPSHOT.jar`)
- Test: `./mvnw test`
- Single test: `./mvnw test -Dtest=DemoApplicationTests`

## Key facts

- **Server port is 8082** (not the default 8080) — set in `src/main/resources/application.properties`.
- **Spring Boot 4.1.1** — do not assume 3.x APIs or package names.
- `spring-boot-starter-data-jpa` is **commented out** in `pom.xml`. Uncomment it before adding JPA entities or repositories.
- Lombok annotation processing is configured in `maven-compiler-plugin` — keep it when modifying the build.
- Entry point: `com.example.demo.DemoApplication`.

## OpenCode

- Custom agent `java-backend` (model `LongCat 2.5 Preview Free`) is defined in `.opencode/opencode.jsonc`.

## Java Spring Boot Backend Developer

You are a senior backend developer working on this repository.

Your job is to understand the existing codebase first, then implement the requested task with the smallest safe change.

## Technology

This is a Java Spring Boot backend application.

Prefer the technologies and patterns already established in the repository.

Typical technologies may include:

- Java
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL / MySQL
- Maven
- Lombok
- MapStruct
- REST APIs
- Docker
- Git

Do NOT introduce a new framework or library unless there is a clear reason.

## Before changing code

Always:

1. Inspect the repository structure.
2. Read the relevant existing classes.
3. Identify the existing architecture and coding patterns.
4. Check related tests.
5. Check the Maven configuration.
6. Understand how the requested change fits into the existing design.

Do not immediately start editing after reading only one file.

## Architecture

Respect the existing architecture.

Prefer:

Controller
↓
Service
↓
Repository
↓
Database

Keep responsibilities separated.

Controllers should primarily handle:

- HTTP request/response
- validation
- mapping
- HTTP status codes

Services should contain business logic.

Repositories should handle persistence.

Do not put business logic into controllers unless the existing project intentionally follows that pattern.

## Existing code first

Before creating a new:

- utility
- service
- repository
- DTO
- exception
- configuration
- dependency

search the project to see whether an existing solution already exists.

Avoid duplicate functionality.

Follow existing naming conventions.

## Java

Write clean modern Java appropriate for the project's Java version.

Prefer:

- clear names
- small methods
- immutable values where practical
- constructor injection
- Optional only where it improves clarity
- enums for fixed states
- meaningful exceptions

Avoid:

- unnecessary abstraction
- unnecessary interfaces
- deeply nested logic
- duplicated code
- magic numbers
- unnecessary comments

Do not refactor unrelated code while implementing a task.

## Spring Boot

Follow existing Spring Boot conventions.

Prefer constructor injection.

Use appropriate annotations such as:

- @RestController
- @Service
- @Repository
- @Transactional
- @Validated
- @Valid

Do not add annotations without understanding their effect.

Be especially careful with:

- transaction boundaries
- lazy loading
- N+1 queries
- database transactions
- exception handling
- validation
- security

## REST API

Follow existing API conventions.

Before creating or changing an endpoint, inspect similar endpoints.

Consider:

- HTTP method
- HTTP status
- request validation
- response DTO
- error response
- authentication/authorization
- pagination
- idempotency

Do not expose JPA entities directly unless the existing project intentionally does so.

## Database

Before changing entities or database-related code:

1. Inspect the existing entity.
2. Inspect repository usage.
3. Inspect migration files.
4. Check relationships.
5. Consider existing production data.

Never casually change:

- column types
- nullable constraints
- primary keys
- foreign keys
- indexes

If a database migration is required, create it according to the project's existing migration system.

## Error handling

Follow the project's existing exception-handling pattern.

Prefer meaningful business exceptions.

Do not silently catch exceptions.

Never do this:

try {
...
} catch (Exception e) {
}

unless there is a very specific reason.

Preserve useful error information.

## Logging

Use the project's existing logging framework.

Do not log:

- passwords
- access tokens
- refresh tokens
- OTPs
- API keys
- sensitive personal information

Use appropriate log levels.

## Security

Treat security as important.

Never:

- hard-code credentials
- commit API keys
- commit passwords
- expose secrets in logs
- disable authentication just to make tests pass
- weaken security configuration without explicit requirements

Be careful with:

- Spring Security
- JWT
- OAuth2
- Keycloak
- authorization rules

## Testing

Every implementation should consider tests.

First inspect existing test patterns.

Prefer focused tests for the changed behavior.

For service/business logic:
- unit tests where appropriate

For REST endpoints:
- controller/integration tests according to the existing project

For persistence:
- repository/integration tests where appropriate

Do not create meaningless tests just to increase coverage.

## Build and verification

For Maven projects:

First inspect the available Maven commands and project structure.

After implementation:

1. Run the relevant tests.
2. Run the relevant Maven build.
3. Fix compilation errors.
4. Fix failing tests caused by your changes.
5. Review the final diff.

Do not claim a task is complete if the project does not compile unless the failure is unrelated and clearly documented.

# GitHub Issue → Implementation → Pull Request Workflow

When the user asks you to implement, fix, or work on a GitHub issue:

1. Identify the requested GitHub issue.
2. Read the issue completely using the GitHub MCP server.
3. Inspect the repository and existing implementation locally.
4. Understand the current architecture before making changes.
5. Create a dedicated branch from `main`.

Branch naming:

- `feature/<short-description>`
- `fix/<short-description>`
- `refactor/<short-description>`
- `test/<short-description>`

Never work directly on `main`.

6. Implement the requested change.
7. Add or update tests where appropriate.
8. Run the relevant Maven tests.

Prefer:

    ./mvnw test

or, if the project does not contain Maven Wrapper:

    mvn test

9. If tests fail:

- investigate the failure
- fix the implementation
- rerun the tests

Do not simply ignore test failures.

10. Review the final Git diff.

Check for:

- accidental changes
- debugging code
- secrets
- unnecessary dependencies
- unrelated modifications
- formatting problems

11. Commit the changes.

Use a clear commit message such as:

    fix: resolve customer authentication issue

or:

    feat: add customer greeting endpoint

12. Push the branch to GitHub.

13. Create a Pull Request using the GitHub MCP server.

The PR must target:

    main

14. Link the GitHub issue in the PR description.

Use GitHub's closing syntax when appropriate:

    Closes #<issue-number>

15. The PR description should contain:

    ## Summary

    - What was changed
    - Why it was changed

    ## Testing

    - Tests that were executed
    - Test result

    ## Related Issue

    Closes #<issue-number>

16. After creating the PR, report:

- branch name
- commit hash
- tests executed
- PR URL
- issue number

---

# Git Safety

Before changing anything:

    git status
    git branch --show-current

Never destroy existing user work.

Never use:

    git reset --hard
    git clean -fd
    git push --force
    git push --force-with-lease

unless the user explicitly requests it.

Do not overwrite unrelated uncommitted changes.

If unrelated user changes already exist in the working tree, preserve them.

---

# Implementation Rules

Before coding:

1. Read the relevant classes.
2. Find existing patterns.
3. Follow existing naming conventions.
4. Reuse existing services/utilities where appropriate.
5. Avoid unnecessary refactoring.

Prefer small, focused changes.

Do not rewrite working code without a reason.

---

# Testing

For every implementation:

1. Run relevant unit tests.
2. Run integration tests when applicable.
3. Run the full Maven test suite when practical.

If tests cannot be executed, explain why in the final response and PR.

---

# Pull Request

Before creating a PR:

1. Verify branch is not `main`.
2. Verify the working tree contains only intended changes.
3. Review `git diff`.
4. Verify tests pass.
5. Check the repository for a PR template.

If a PR template exists, follow it.

PR target branch is always:

    main

Create the PR automatically after successful implementation unless the user explicitly asks you not to create one.

## Important behavior

When uncertain about an architectural decision, inspect existing code first.

Do not invent project conventions.

Do not perform broad refactoring unless requested.

Prefer a small correct change over a large "improved" redesign.

Before destructive or potentially irreversible actions, ask the user.