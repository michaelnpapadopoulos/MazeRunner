package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ca.mcmaster.se2aa4.mazerunner.path_finding.ForwardSearch;
import ca.mcmaster.se2aa4.mazerunner.path_finding.RightHandSearch;
import ca.mcmaster.se2aa4.mazerunner.path_validation.PathWalker;

/**************************************************************************
 * Decided to test MazeRunner directly as I plan on modifying how it is
 * structured/instantiated (Builder pattern) in the refactored version 
 * of the code. For this reason I am not directly testing the path 
 * finding algorithms or the path validation algorithms as these tests
 * on MazeRunner are essentially doing that already as the findPath() and
 * verifyPath() methods are calling the algorithms directly (wrappers).
**************************************************************************/
public class MazeRunnerTest {
    private MazeRunner mazeRunner;

    // Direct maze test cases
    @Nested
    class MazeRunnerDirectMaze {
        @BeforeEach
        void setUp() {
            // Initialize the MazeRunner object before each test
            mazeRunner = new MazeRunner("examples/direct.maz.txt");
        }

        @Test 
        void testMazeRunnerInitialization() {
            // Test if the MazeRunner is initialized correctly
            assertNotNull(mazeRunner, "MazeRunner should be initialized");
        }

        @Test
        void testFindPathRightHandSearch() {
            // Test if the findPath method works correctly

            // Testing the RightHandSearch algorithm
            String path = mazeRunner.findPath(new RightHandSearch());
            String expectedPath = "2F R 2F L 4F R 2F L F"; // Expected path for the direct maze
            assertEquals(expectedPath, path, "Path should match expected values using RightHandSearch algorithm");
        }

        @Test
        void testFindPathForwardSearch() {
            // Test if the findPath method works correctly

            // Testing the ForwardSearch algorithm
            String path = mazeRunner.findPath(new ForwardSearch());
            String expectedPath = "Forward search algorithm insufficient, unable to find path"; // Expected path for the direct maze (using forward search)
            assertEquals(expectedPath, path, "Path should match expected values using ForwardSearch algorithm");
        }

        @Test
        void testVerifyPathValid() {
            // Test if the verifyPath method works correctly on a valid path
            boolean isValid = mazeRunner.verifyPath("2F 5R 2F L 4F R 2F L 2L 2R F", new PathWalker());
            assertTrue(isValid, "Path should be valid using PathWalker algorithm");
        }

        @Test
        void testVerifyPathInvalid() {
            // Test if the verifyPath method works correctly for an invalid path
            boolean isNotValid = mazeRunner.verifyPath("2F 5R", new PathWalker());
            assertFalse(isNotValid, "Path should be invalid using PathWalker algorithm");
        }

        @Test
        void testVerifyPathInvalidNoPath() {
            // Test if the verifyPath method works correctly for an empty string (no path)
            boolean isNotValid = mazeRunner.verifyPath("", new PathWalker());
            assertFalse(isNotValid, "Path should be invalid using PathWalker algorithm");
        }
    }

    // Straight maze test cases
    @Nested
    class MazeRunnerStraightMaze {
        @BeforeEach
        void setUp() {
            // Initialize the MazeRunner object before each test
            mazeRunner = new MazeRunner("examples/straight.maz.txt");
        }

        @Test 
        void testMazeRunnerInitialization() {
            // Test if the MazeRunner is initialized correctly
            assertNotNull(mazeRunner, "MazeRunner should be initialized");
        }

        @Test
        void testFindPathRightHandSearch() {
            // Test if the findPath method works correctly

            // Testing the RightHandSearch algorithm
            String path = mazeRunner.findPath(new RightHandSearch());
            String expectedPath = "4F"; // Expected path for the straight maze
            assertEquals(expectedPath, path, "Path should match expected values using RightHandSearch algorithm");
        }

        @Test
        void testFindPathForwardSearch() {
            // Test if the findPath method works correctly

            // Testing the ForwardSearch algorithm
            String path = mazeRunner.findPath(new ForwardSearch());
            String expectedPath = "4F"; // Expected path for the straight maze (using forward search)
            assertEquals(expectedPath, path, "Path should match expected values using ForwardSearch algorithm");
        }

        @Test
        void testVerifyPathValid() {
            // Test if the verifyPath method works correctly on a valid path
            boolean isValid = mazeRunner.verifyPath("2L 2R 4F 2L 2R", new PathWalker());
            assertTrue(isValid, "Path should be valid using PathWalker algorithm");
        }

        @Test
        void testVerifyPathInvalid() {
            // Test if the verifyPath method works correctly for an invalid path
            boolean isNotValid = mazeRunner.verifyPath("2F 5R", new PathWalker());
            assertFalse(isNotValid, "Path should be invalid using PathWalker algorithm");
        }

        @Test
        void testVerifyPathInvalidNoPath() {
            // Test if the verifyPath method works correctly for an empty string (no path)
            boolean isNotValid = mazeRunner.verifyPath("", new PathWalker());
            assertFalse(isNotValid, "Path should be invalid using PathWalker algorithm");
        }
    }
}
