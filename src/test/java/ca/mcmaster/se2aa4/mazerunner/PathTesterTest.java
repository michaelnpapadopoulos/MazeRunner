package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.path_validation.PathTester;
import ca.mcmaster.se2aa4.mazerunner.path_validation.PathWalker;

public class PathTesterTest {
    private PathTester pathTester;
    private Maze maze;
    private int[] startPos;
    private int[] endPos;   


    @BeforeEach
    void setUp() {
        // Initialize the path tester object before each test
        pathTester = new PathWalker();
        maze = new Maze("examples/small.maz.txt");
        startPos = new int[]{8, 0}; 
        endPos = new int[]{5, 10}; 
    }

    @Test
    void testMazeInitialization() {
        // Check if the maze is initialized correctly
        assertNotNull(maze, "Maze should be initialized");
    }
    
    @Test
    void testPathTesterInitialization() {
        // Check if the path tester is initialized correctly
        assertNotNull(pathTester, "Path tester should be initialized");
    }

    // Modified to use the raw path string as I have removed the StringConverter interface
    // from the PathTester class's and do it within the MazeRunner class itself
    @Test
    void testValidPath() {
        String path = "FRFFLFFRFFRFFLLFFFFFFFFLLRRLLLLLLFFRFFRFFLFFLLFFRFFRFFFFRFFLFRF"; // Example path to test
        boolean testerResult = pathTester.testPath(path, maze, startPos, endPos);
        assertTrue(testerResult, "Path should be valid"); // Check if the path is valid
    }

    @Test
    void testInvalidPath() {
        String path = "FRFFLFFRFFRFFLLFFFFFFFF"; // Example path to test
        boolean testerResult = pathTester.testPath(path, maze, startPos, endPos);
        assertFalse(testerResult, "Path should be invalid"); // Check if the path is invalid
    }

    @Test
    void testEmptyPath() {
        String path = ""; // Empty path
        boolean testerResult = pathTester.testPath(path, maze, startPos, endPos);
        assertFalse(testerResult, "Empty path should be invalid"); // Check if the empty path is invalid
    }
}
