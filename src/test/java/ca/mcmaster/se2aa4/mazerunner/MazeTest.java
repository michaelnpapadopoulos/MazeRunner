package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MazeTest {
    private Maze maze;

    // Nested class for testing the direct maze
    @Nested
    class DirectMazeTests {
        @BeforeEach
        void setUp() {
            // Initialize the maze object before each test
            maze = new Maze("examples/small.maz.txt");
        }

        @Test
        void testMazeInitialization() {
            // Test if the maze is initialized correctly
            assertNotNull(maze, "Maze should be initialized");
        }

        @Test
        void testMazeEntryPoints() {
            // Test if the maze entry points are set correctly
            int[][] expectedEntrances = {{8, 0}, {5, 10}}; // Example expected entrances
            int[][] actualEntrances = maze.getEntryPoints();
            assertArrayEquals(expectedEntrances, actualEntrances, "Entry points should match expected values");
        }

        @Test
        void testCheckCoordValid() {
            // Test if the checkCoord method works correctly on valid coordinates

            int[] validCoord = {5, 10}; // valid coordinates
            boolean isValid = maze.checkCoord(validCoord);
            assertTrue(isValid, "Coordinates should be valid for the maze");
        }

        @Test
        void testCheckCoordInvalidOutOfRange() {
            // Test if the checkCoord method works correctly on invalid coordinates (out of range)

            int[] invalidCoord = {15, 15}; // invalid coordinates
            boolean isInvalid = maze.checkCoord(invalidCoord);
            assertFalse(isInvalid, "Coordinates should be invalid for the maze");
        }

        @Test
        void testCheckCoordInvalidInWall() {
            // Test if the checkCoord method works correctly on invalid coordinates (in wall)

            int[] invalidCoord = {8, 2}; // invalid coordinates
            boolean isInvalid = maze.checkCoord(invalidCoord);
            assertFalse(isInvalid, "Coordinates should be invalid for the maze");
        }

        @Test
        void testCheckMazeRows() {
            // Test if the maze row count is correct
            int expectedRows = 11; // Example expected number of rows
            assertEquals(expectedRows, maze.getNumOfRows(), "Number of rows should match expected value");
        }

        @Test
        void testCheckMazeCols() {
            // Test if the maze column count is correct
            int expectedCols = 11; // Example expected number of columns
            assertEquals(expectedCols, maze.getNumOfCols(), "Number of columns should match expected value");
        }
    }

    // Nested class for testing the straight maze
    @Nested
    class straightMazeTests {
        @BeforeEach
        void setUp() {
            // Initialize the maze object before each test
            maze = new Maze("examples/straight.maz.txt");
        }

        @Test
        void testMazeInitialization() {
            // Test if the maze is initialized correctly
            assertNotNull(maze, "Maze should be initialized");
        }

        @Test
        void testMazeEntryPoints() {
            // Test if the maze entry points are set correctly
            int[][] expectedEntrances = {{2, 0}, {2, 4}}; // Example expected entrances
            int[][] actualEntrances = maze.getEntryPoints();
            assertArrayEquals(expectedEntrances, actualEntrances, "Entry points should match expected values");
        }

        @Test
        void testCheckCoordValid() {
            // Test if the checkCoord method works correctly on valid coordinates

            int[] validCoord = {2, 3}; // valid coordinates
            boolean isValid = maze.checkCoord(validCoord);
            assertTrue(isValid, "Coordinates should be valid for the maze");
        }

        @Test
        void testCheckCoordInvalidOutOfRange() {
            // Test if the checkCoord method works correctly on invalid coordinates (out of range)

            int[] invalidCoord = {-1, 15}; // invalid coordinates
            boolean isInvalid = maze.checkCoord(invalidCoord);
            assertFalse(isInvalid, "Coordinates should be invalid for the maze");
        }

        @Test
        void testCheckCoordInvalidInWall() {
            // Test if the checkCoord method works correctly on invalid coordinates (in wall)

            int[] invalidCoord = {0, 2}; // invalid coordinates
            boolean isInvalid = maze.checkCoord(invalidCoord);
            assertFalse(isInvalid, "Coordinates should be invalid for the maze");
        }

        @Test
        void testCheckMazeRows() {
            // Test if the maze row count is correct
            int expectedRows = 5; // Example expected number of rows
            assertEquals(expectedRows, maze.getNumOfRows(), "Number of rows should match expected value");
        }

        @Test
        void testCheckMazeCols() {
            // Test if the maze column count is correct
            int expectedCols = 5; // Example expected number of columns
            assertEquals(expectedCols, maze.getNumOfCols(), "Number of columns should match expected value");
        }
    }
    
}
