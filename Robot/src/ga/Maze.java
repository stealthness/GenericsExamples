package ga;

/**
 * Created by Stephen West on 08/04/2019.
 */
import java.util.ArrayList;
import java.util.stream.IntStream;

public class Maze {
    private final int maze[][];
    private int startPosition[] = { -1, -1 };

    public Maze(int maze[][]) {
        this.maze = maze;
    }

    public int[] getStartPosition() {
        // Check if we’ve already found start position
        if (this.startPosition[0] != -1 && this.startPosition[1] != -1) {
            return this.startPosition;
        }

        // Default return value
        int startPosition[] = { 0, 0 };

        // Loop over rows
        for (int rowIndex = 0; rowIndex < this.maze.length; rowIndex++) {
            // Loop over columns
            for (int colIndex = 0; colIndex < this.maze[rowIndex].length; colIndex++) {
                // 2 is the growthMethod for start position
                if (this.maze[rowIndex][colIndex] == 2) {
                    this.startPosition = new int[] { colIndex, rowIndex };
                    return new int[] { colIndex, rowIndex };
                }
            }
        }

        return startPosition;
    }

    public int getPositionValue(int x, int y) {
        if (x < 0 || y < 0 || x >= this.sizeX() || y >= this.sizeY()) {
            return 1;
        }
        return this.maze[y][x];
    }

    public boolean isWall(int x, int y) {
        return (this.getPositionValue(x, y) == 1);
    }

    public int sizeX() {
        return this.maze[0].length;
    }

    public int sizeY() {
        return this.maze.length;
    }

    public int scoreRoute(ArrayList<int[]> route) {
        int score = 0;
        boolean visited[][] = new boolean[this.sizeY()][this.sizeX()];

        // Loop over route and score each move
        for (Object routeStep : route) {
            int step[] = (int[]) routeStep;
            if (this.maze[step[1]][step[0]] == 3 && visited[step[1]][step[0]] == false) {
                // Increase score for correct move
                score++;
                // Remove reward
                visited[step[1]][step[0]] = true;
            }
        }

        return score;
    }

    @Override
    public String toString(){
        StringBuilder sb =new StringBuilder();
        IntStream.range(0,sizeY()).forEach(y -> {
            IntStream.range(0,sizeX()).forEach( x-> {
                switch(this.getPositionValue(x,y)){
                    case 0:
                        sb.append("_");
                        break;
                    case 2:
                        sb.append("O");
                        break;
                    case 3:
                        sb.append(".");
                        break;
                    case 4:
                        sb.append("+");
                        break;
                    case 1:
                    default:
                        sb.append("X") ;
                }

            });
            sb.append(System.lineSeparator());
        });
        return sb.toString();
    }
}
