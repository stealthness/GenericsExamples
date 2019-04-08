package ga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Stephen West on 08/04/2019.
 */
class MazeTest {

    private final int MAX_X = 8;
    private final int MAX_Y = 11;
    private Maze maze;

    @BeforeEach
    void setUp() {

        maze = new Maze(new int[MAX_X][MAX_Y] );
    }

    @Test
    void getStartPosition() {
        assertEquals(-1, maze.getStartPosition()[0]);
        assertEquals(-1, maze.getStartPosition()[1]);
    }

    @Test
    void getPositionValue() {
        IntStream.range(0,MAX_X).forEach(x -> IntStream.range(0,MAX_Y).forEach(y ->{
            assertEquals(0, maze.getPositionValue(x,y), "x : "+ x +" y :"+y);
        }));
    }

    @Test
    void isWall() {
        IntStream.range(0,MAX_X).forEach(x -> IntStream.range(0,MAX_Y).forEach(y ->{
            assertFalse(maze.isWall(x,y));
        }));
    }

    @Test
    void getMaxX() {
        assertEquals(MAX_X,maze.getMaxX());
    }

    @Test
    void getMaxY() {
        assertEquals(MAX_Y,maze.getMaxY());
    }

    @Test
    void scoreRoute() {
        fail();
        // to do
    }
}