# Maze Racer Game

Maze Racer is a web-based maze game built with Java, Spring Boot, and Thymeleaf. Players can select a difficulty, navigate a randomly generated maze, and compete for high scores. The game supports player comments and ratings.

## Features

- Random maze generation (Easy, Medium, Hard)
- Keyboard controls (WASD)
- Player registration and session tracking
- Scoreboard with top 10 players
- Comments and ratings for the game
- Responsive, modern UI with cyber-maze theme

## Technologies

- Java 17
- Spring Boot (Web, Data JPA, Thymeleaf)
- PostgreSQL (for persistence)
- HTML/CSS (Thymeleaf templates)

## Setup

1. **Clone the repository:**
   ```
   git clone <repo-url>
   cd gamestudio-9911/gamestudio
   ```

2. **Configure the database:**
   - Ensure PostgreSQL is running.
   - Create a database named `gamestudio`.
   - Update DB credentials in `application.properties` if needed.

3. **Build and run the application:**
   ```
   ./mvnw spring-boot:run
   ```
   or on Windows:
   ```
   mvnw.cmd spring-boot:run
   ```

4. **Access the game:**
   - Open [http://localhost:8080/](http://localhost:8080/) in your browser.

## Usage

- Enter your name and select a difficulty to start.
- Use `W`, `A`, `S`, `D` keys to move.
- Reach the exit to win and submit your score.
- After finishing, you can leave a comment and rate the game.
- View top players, comments, and average rating on the main page.

## Project Structure

- `src/main/java/sk/tuke/gamestudio/` - Java source code (controllers, core logic, services)
- `src/main/resources/templates/` - Thymeleaf HTML templates
- `src/main/resources/static/` - Static assets (CSS, images)
- `src/test/java/` - Unit tests

## REST API

Test endpoints are available in `test-rest.http` for scores, comments, and ratings.

## Author

Developed by Rostyslav Matsko.

---
