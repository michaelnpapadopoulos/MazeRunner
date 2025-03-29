package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**************************************************************************
 * Decided to test MazeRunner directly as I plan on modifying how it is
 * structured/instantiated (Builder pattern) in the refactored version 
 * of the code.
**************************************************************************/
public class MazeRunnerTest {
    private MazeRunner mazeRunner;

    @BeforeEach
    void setUp() {
        // Initialize the MazeRunner before each test
        mazeRunner = new MazeRunner("examples/small.maz.txt");
    }

    @Test
    void testInitialization() {
        // Test if the MazeRunner is initialized correctly (Will be important for builder pattern)
        mazeRunner = new MazeRunner("examples/small.maz.txt");
        assertNotNull(mazeRunner, "MazeRunner should be initialized");
    }
}
