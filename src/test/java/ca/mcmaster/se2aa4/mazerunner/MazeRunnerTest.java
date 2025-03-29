package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.path_finding.RightHandSearch;
import ca.mcmaster.se2aa4.mazerunner.path_validation.PathWalker;
/**************************************************************************
 * Decided to test MazeRunner directly as I plan on modifying how it is
 * structured/instantiated (Builder pattern) in the refactored version 
 * of the codebase. Not testing findPath or verifyPath as they are
 * wrapper methods for the path finding algorithm and path tester respectively.
**************************************************************************/
public class MazeRunnerTest {
    private MazeRunner mazeRunner;

    @BeforeEach
    void setUp() {
        // Initialize the MazeRunner before each test
        mazeRunner = new MazeRunner.Builder()
            .withPathFindingAlgorithm(new RightHandSearch())
            .withPathTester(new PathWalker())
            .withMaze(new Maze("examples/small.maz.txt"))
            .build();
    }

    @Test
    void testInitialization() {
        // Test if the MazeRunner is initialized correctly (Will be important for builder pattern)
        assertNotNull(mazeRunner, "MazeRunner should be initialized");
    }
}
