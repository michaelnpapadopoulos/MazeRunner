package ca.mcmaster.se2aa4.mazerunner.path_validation;

import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

import java.util.Arrays;

import ca.mcmaster.se2aa4.mazerunner.DirectionManager;
import ca.mcmaster.se2aa4.mazerunner.Maze;
import ca.mcmaster.se2aa4.mazerunner.actions.ActionManager;
import ca.mcmaster.se2aa4.mazerunner.actions.ForwardAction;
import ca.mcmaster.se2aa4.mazerunner.actions.LeftAction;
import ca.mcmaster.se2aa4.mazerunner.actions.RightAction;
import ca.mcmaster.se2aa4.mazerunner.utilities.StartDirectionFinder;

public class PathWalker extends PathTester implements StartDirectionFinder {

    /**************************************************************************
     * Tests the validity of a path through a maze by "walking" the path from both
     * entry points (west and east) to the exit point.
     * 
     * @param rawPath path to be tested
     * @param maze maze object to be used
     * @param startPos east entrance in the maze
     * @param endPos west entrance in the maze
     **************************************************************************/
    public boolean testPath(String rawPath, Maze maze, int[] startPos, int[] endPos) {
        logger.trace("**** Verifying with Path Walker");

        // Test path from both entry points
        boolean westToEast = walkSpecificPath(rawPath, maze, startPos, endPos, determineDirection(startPos, endPos));
        boolean eastToWest = walkSpecificPath(rawPath, maze, endPos, startPos, determineDirection(endPos, startPos));

        if (westToEast || eastToWest) { return true; } // Returns true if either path is valid
        return false;
    }

    /**************************************************************************
     * Simulates taking a given path through a maze by "walking" the path from a
     * starting point and direction to an ending point.
     * 
     * @param rawPath path to be tested
     * @param maze maze object to be navigated
     * @param startPos starting position in the maze
     * @param endPos ending position in the maze
     * @param startDirection initial facing direction (E or W, for East or West)
    **************************************************************************/
    private boolean walkSpecificPath(String rawPath, Maze maze, int[] startPos, int[] endPos, char startDirection) {
        logger.trace("**** Walking path starting: {}, ending: {}", startPos, endPos);
        DirectionManager currDirection = new DirectionManager(startDirection);
        ActionManager actionManager = new ActionManager();
        int[] currPos = startPos.clone(); // Preserve original start position
       

        for (int charIndex = 0; charIndex < rawPath.length(); charIndex++) { // Iterates through entire path string
            char currChar = rawPath.charAt(charIndex);

            if (currChar == 'F') {
                actionManager.executeAction(new ForwardAction(currPos, currDirection)); // Executes action to move forward
            } else if (currChar == 'R') {
                actionManager.executeAction(new RightAction(currDirection)); // Executes action to turn right
            } else {
                actionManager.executeAction(new LeftAction(currDirection)); // Executes action to turn left
            }

            if (!maze.checkCoord(currPos)) { // False if position is "out of bounds"
                return false;
            }
        }

        return Arrays.equals(currPos, endPos);
    }
}
