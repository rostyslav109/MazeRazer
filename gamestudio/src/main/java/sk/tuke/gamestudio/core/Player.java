package sk.tuke.gamestudio.core;

public class Player {
    private Position position;

    public Player(Position startPosition) {
        this.position = startPosition;
    }

    public Position getPosition() {
        return position;
    }

    public void move(Direction direction, Maze maze) {
        int newX = position.getX();
        int newY = position.getY();

        switch (direction) {
            case UP:
                newY--;
                break;
            case DOWN:
                newY++;
                break;
            case LEFT:
                newX--;
                break;
            case RIGHT:
                newX++;
                break;
        }

        if (maze.isValidMove(newX, newY)) {
            position.setX(newX);
            position.setY(newY);
        }
    }
}
