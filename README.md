# Challenge

This project is a Spring Boot application developed to solve a technical challenge. The application exposes two endpoints that implement the required logic.

## Requirements
- Java 17
- Spring Boot 2.7+
- Maven 3.8+
- Docker (optional)

## Installation and Run

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd desafio_grupo_simpli
   ```

2. Build the project with Maven:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The app will be available at `http://localhost:8080`.

## Endpoints

### 1. Array Pair Validator
**POST /array**

**Description:** Receives an array of integers and checks if all elements have a matching pair. If all elements have a pair, returns "yes"; otherwise, lists the unmatched elements.

**Sample Request Body:**
```json
[1, 2, 4, 3, 2, 2, 2, 2, 1, 1, 6, 7, 3, 2, 2, 1, 4, 3, 1, 3, 3, 1, 1, 1, 7, 6, 2, 3]
```
**Sample Response:**
```
yes
```

### 2. Digit Combination
**POST /num/{number}**

**Description:** Receives an integer and checks if the sum of its digits can be split equally using `+` and `-` symbols.

**Path Parameter:**
- `number` (int) - Number to evaluate.

**Sample Request:**
```
POST /num/26712
```
**Sample Response:**
```
-+--
```
If no valid combination is found, returns:
```
not possible
```

## Contact
For any questions, feel free to contact me at **scarnezis@gmail.com**.

