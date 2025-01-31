package ca.mcmaster.se2aa4.mazerunner;

import java.util.Arrays;
import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

public class PathWalker extends PathTester implements StringConverter, StartDirectionFinder {

    //=========== PATH WALKER METHODS ===========//
    public boolean testPath(String path, Maze maze, int[] startPos, int[] endPos) { // Tests path validity using a Path "Walking" algorithm
        logger.info("** Verifying with Path Walker: {}", path);

        String rawPath = convertToUnfactored(removeSpaces(path)); // Removes spaces and converts to unfactored string
    
        // Test path from both entry points
        boolean westToEast = walkSpecificPath(rawPath, maze, startPos, endPos, determineDirection(startPos, endPos));
        boolean eastToWest = walkSpecificPath(rawPath, maze, endPos, startPos, determineDirection(endPos, startPos));

        if (westToEast || eastToWest) { return true; } // Returns true if either path is valid
        return false;
    }

    private boolean walkSpecificPath(String path, Maze maze, int[] startPos, int[] endPos, char startDirection) { // Tests path validity from a starting point and direction
        logger.trace("Verifying path with start: {}, end: {}", startPos, endPos);
        int[] currPos = startPos;
        DirectionManager currDirection = new DirectionManager(startDirection);

        for (int charIndex = 0; charIndex < path.length(); charIndex++) { // Iterates through entire path string
            char currChar = path.charAt(charIndex);

            if (currChar == 'F') {
                currPos = currDirection.getNewPosition(currPos);
            } else if (currChar == 'R') {
                currDirection.turnRight();
            } else {
                currDirection.turnLeft();
            }

            if (!maze.checkCoord(currPos)) { // False if position is "out of bounds"
                return false;
            }
        }

        return Arrays.equals(currPos, endPos);
    }
}
