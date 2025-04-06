# Project Documentation

## 📖 Project Description
SC2002 BTO Application

---

## 🚀 Getting Started

### Build Commands
| Command                  | Description                                                                 |
|--------------------------|-----------------------------------------------------------------------------|
| `./gradlew javadoc`      | Generates Javadoc documentation in `./docs/javadoc/`.                       |
| `./gradlew checkstyleMain`     | Generates a Checkstyle report (output in `./docs/checkstyle/main.html`).    |
| `./gradlew build`        | Builds the project, including Javadoc and Checkstyle reports.               |
| `./gradlew run`          | Executes the main class of the project.                                     |

---

## 📊 Reports
- **Javadoc**: Access generated documentation at `./docs/javadoc/index.html` after running `./gradlew javadoc` or `./gradlew build`.
- **Checkstyle**: View the report at `./docs/checkstyle/main.html` after running `./gradlew checkstyleMain` or `./gradlew build`.

---

## 🔧 Contributing Guidelines
1. **Fix Checkstyle Issues First**  
   Always run `./gradlew checkstyleMain` and resolve all Checkstyle warnings/errors before committing code.
2. **Update Documentation**  
   Regenerate Javadoc (`./gradlew javadoc`) after making significant code changes.
3. **Test Thoroughly**  
   Use `./gradlew build` to ensure the project compiles and passes all checks.

---
