package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.path_finding.ForwardSearch;
import ca.mcmaster.se2aa4.mazerunner.path_finding.PathFindingAlgorithm;
import ca.mcmaster.se2aa4.mazerunner.path_finding.RightHandSearch;

public class PathFindingAlgorithmTest {
    private PathFindingAlgorithm pathFindingAlgorithm;
    private Maze maze;

    @BeforeEach
    void setUp() {
        // Initialize the maze object before each test
        maze = new Maze("examples/small.maz.txt");
    }
    
    @Nested
    class ForwardSearchAlgorithm {
        @BeforeEach
        void setUp() {
            // Initialize the path finding algorithm before each test
            pathFindingAlgorithm = new ForwardSearch();
        }

        @Test
        void testFindPath() {
            int[] startPos = {8, 0};
            int[] endPos = {5, 10};
            String resultPath = pathFindingAlgorithm.findPath(maze, startPos, endPos);
            String expectedPath = "Forward search algorithm insufficient, unable to find path";
            assertEquals(expectedPath, resultPath, "Path should not be found using forward search algorithm");
        }
    }

    @Nested
    class RightHandSearchAlgorithm {
        @BeforeEach
        void setUp() {
            // Initialize the path finding algorithm before each test
            pathFindingAlgorithm = new RightHandSearch();
        }

        @Test
        void testFindPath() {
            int[] startPos = {8, 0};
            int[] endPos = {5, 10};
            String resultPath = pathFindingAlgorithm.findPath(maze, startPos, endPos);
            String expectedPath = "FRFFLFFRFFRFFLLFFFFFFFFLLFFRFFRFFLFFLLFFRFFRFFFFRFFLFRF";
            assertEquals(expectedPath, resultPath, "Path should be found using right hand search algorithm");
        }
    }
}
