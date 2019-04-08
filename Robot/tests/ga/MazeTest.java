package ga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        maze = new Maze(new int[MAX_Y][MAX_X] );
    }

    @Test
    void getStartPosition() {
        assertEquals(0, maze.getStartPosition()[0]);
        assertEquals(0, maze.getStartPosition()[1]);
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
    void sizeX() {
        assertEquals(MAX_X,maze.sizeX());
    }

    @Test
    void sizeY() {
        assertEquals(MAX_Y,maze.sizeY());
    }

    @Test
    void scoreRoute() {
        maze = createTestMaze();
        System.out.println(maze.toString());

        fail();
        // to do
    }

    private Maze createTestMaze(){
    return new Maze(new int[][]{{0,0,0,0,1,0,1,3,2},
                                {1,0,1,1,1,0,1,3,1},
                                {1,0,0,1,3,3,3,3,1},
                                {3,3,3,1,3,1,1,0,1},
                                {3,1,3,3,3,1,1,0,0},
                                {3,3,1,1,1,1,0,1,1},
                                {1,3,0,1,3,3,3,3,3},
                                {0,3,1,1,3,1,0,1,3},
                                {1,3,3,3,3,1,1,1,4}});
 }

}