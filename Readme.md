# Csgo-matches API:
This is a simple API, working with dataset of esea matches. It provides some basic statistics, aswell option for adding more data.
Main goal is just to practice with Spring and PostgreSQL, but can help with collecting information about game itself.

## Project tasks:

- [x] Uses Spring, IDEA, PostgreSQL
- [ ] Includes at least 10 endpoints:
- [ ] Includes at least 5 calculations (mathematical operations on arrays of data)
- [x] Code follows DRY principle
- [x] One method is always doing one thing
- [x] Method should not exceed 20 lines and 2 indentations
- [x] Handles error states (no uncaught exceptions)
- [x] Uses migrations to prepare database tables
- [x] Provides web-based documentation of endpoints and models
- [ ] Includes unit tests for all calculations
- [ ] Includes integration tests for all endpoints
- [ ] Includes performance tests for all endpoints


## Endpoints:
### Matches
    - GET /matches - get all matches
    - GET /matches/{map_name} - get matches by map name
    - GET /matches/maps - get matches count for each map
    - GET /matches/top-map-matches - get list of maps with matches with the highest rounds
    - GET /matches/{id} - get match by id, with all other data about match    

    - POST /matches - create a new match
    - DELETE /matches/{id} - delete a match by id

### Rounds
    - GET /counts - get rounds count + map for each match
    - GET /maps - get rounds count for each map
