package sk.tuke.gamestudio.core;

import java.util.*;

public class MazeGenerator {
    private static final char WALL = '#';
    private static final char PATH = ' ';
    private static final char START = 'S';
    private static final char EXIT = 'E';

    private final int width, height;
    private final char[][] grid;
    private final Random random = new Random();

    public MazeGenerator(int width, int height) {
        this.width = width | 1; // ensure odd size
        this.height = height | 1;
        this.grid = new char[height][width];
        for (char[] row : grid)
            Arrays.fill(row, WALL);
    }

    public Maze generate() {
        generateMaze(1, 1);
        grid[1][1] = START;
        grid[height - 2][width - 2] = EXIT;
        return new Maze(grid);
    }

    private void generateMaze(int x, int y) {
        Direction[] directions = Direction.values();
        Collections.shuffle(Arrays.asList(directions));

        for (Direction dir : directions) {
            int nx = x + dir.dx * 2;
            int ny = y + dir.dy * 2;
            if (isInBounds(nx, ny) && grid[ny][nx] == WALL) {
                grid[y + dir.dy][x + dir.dx] = PATH;
                grid[ny][nx] = PATH;
                generateMaze(nx, ny);
            }
        }
    }

    private boolean isInBounds(int x, int y) {
        return x > 0 && x < width - 1 && y > 0 && y < height - 1;
    }

    private enum Direction {
        UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);
        final int dx, dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }
}




