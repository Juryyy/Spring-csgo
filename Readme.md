# CS:GO Matches API

A Spring Boot REST API for analyzing CS:GO matches from ESEA dataset. This application provides endpoints to access match statistics, rounds information, and weapon usage analytics.

## Getting Started

### Prerequisites

- Java JDK 17
- PostgreSQL
- IntelliJ IDEA (recommended)
- Docker (optional, for running PostgreSQL in container)

### Setup and Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   ```

2. **Database Setup**
   
   Option 1: Using Docker (recommended)
   ```bash
   docker-compose up -d
   ```
   
   Option 2: Connect to an existing PostgreSQL instance by updating `application.properties`

3. **Open in IntelliJ IDEA**
   - Open IntelliJ IDEA
   - Select `File > Open` and navigate to the project directory
   - Wait for the IDE to import the Gradle project

4. **Run the application**
   - Run the main class `com.example.csgo.CsgoApplication`
   - Or use the Gradle task: `./gradlew bootRun`
   - The API will be available at `http://localhost:8090`

5. **Import test data** (optional)
   ```bash
   # Assuming PostgreSQL is running at localhost:5434
   cd import
   python Data-import.py
   ```

### Running Tests

1. **Unit tests**
   ```bash
   ./gradlew test
   ```

2. **Integration tests**
   ```bash
   ./gradlew test --tests "*IntegrationTest"
   ```

3. **Performance tests** (using Locust)
   ```bash
   cd src/test/Performance
   locust -f Locust.py --host=http://localhost:8090
   ```
   Then open `http://localhost:8089` in your browser to access the Locust web UI.

### API Documentation

When the application is running, access the Swagger UI at:
```
http://localhost:8090/swagger-ui.html
```

## Project Structure

The project follows a standard Spring Boot application structure:
- `src/main/java` - Java source code
  - `domain` - Domain-specific code organized by entity
  - `utils` - Utilities, exceptions, and helpers
- `src/main/resources` - Configuration files and migrations
- `src/test` - Test code
- `import` - Data import scripts

---

## Project Requirements

This project was developed to meet the following requirements:

- [x] Uses Spring, IDEA, PostgreSQL
- [x] Includes at least 10 endpoints
- [x] Includes at least 5 calculations (mathematical operations on arrays of data)
- [x] Code follows DRY principle
- [x] One method is always doing one thing
- [x] Method should not exceed 20 lines and 2 indentations
- [x] Handles error states (no uncaught exceptions)
- [x] Uses migrations to prepare database tables
- [x] Provides web-based documentation of endpoints and models
- [x] Includes unit tests for all calculations
- [x] Includes integration tests for all endpoints
- [x] Includes performance tests for all endpoints - done in Python

## API Endpoints

### Matches

- `GET /matches` - get all matches
- `GET /matches/{id}` - get match by id, with all other data about match  
- `GET /matches/maps` - get matches count for each map    
- `GET /matches/map/{map_name}` - get matches by map name
- `GET /matches/top-map-matches` - get list of maps with matches with the highest rounds
- `POST /matches` - create a new match
- `DELETE /matches/{id}` - delete a match by id
- `PUT /matches/{id}` - update a match by id

### Rounds

- `GET /rounds` - get all rounds
- `GET /rounds/counts` - get rounds count + map for each match
- `GET /rounds/maps` - get rounds count for each map
- `GET /rounds/avg` - get average rounds count for all matches
- `GET /rounds/avg/{map_name}` - get average rounds count for matches on specific map
- `GET /rounds/winrate/{map_name}` - get winrate for each team on specific map
- `POST /rounds` - create a new round
- `PUT /rounds/{id}` - update a round by id
- `DELETE /rounds/{id}` - delete a round by id

### Kills
- `GET /kills/avg` - get average kills count for each map
- `GET /kills/avg/{map_name}` - get average kills count for matches on specific map
- `GET /kills/avg/sides` - get average kills count for matches on specific side
- `GET /kills/weapons` - get average kills count for matches with each weapon

### Damage (Planned)
- `GET /damage` - get total damage for each match
- `GET /damage/{id}` - get total damage for specific match
- `GET /damage/highest/{id}` - get highest damage for specific match
- `GET /damage/lowest/{id}` - get lowest damage for specific match

## License

This project is licensed under the MIT License - see the LICENSE file for details.
