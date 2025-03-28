package ca.mcmaster.se2aa4.mazerunner.path_finding;

import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

import java.util.Arrays;

import ca.mcmaster.se2aa4.mazerunner.DirectionManager;
import ca.mcmaster.se2aa4.mazerunner.Maze;
import ca.mcmaster.se2aa4.mazerunner.utilities.StartDirectionFinder;
import ca.mcmaster.se2aa4.mazerunner.utilities.StringConverter;

public class RightHandSearch extends PathFindingAlgorithm implements StringConverter, StartDirectionFinder {

    public String findPath(Maze maze, int[] startPos, int[] endPos) {
        logger.trace("**** Finding path using right hand search");
        String westToEastPath = findSpecificPath(maze, startPos, endPos, determineDirection(startPos, endPos));
        String eastToWestPath = findSpecificPath(maze, endPos, startPos, determineDirection(endPos, startPos));

        String shortestPath = new String();
        String startingWall = new String();

        if (westToEastPath.length() > eastToWestPath.length()) {
            shortestPath = eastToWestPath;
            startingWall = "East";
        } else {
            shortestPath = westToEastPath;
            startingWall = "West";
        }

        System.out.println("Starting wall: " + startingWall);
        return convertToFactored(shortestPath);
    }

    /**************************************************************************
     * Method to verify a given path through the maze using a path tester
     * 
     * @param maze the maze object to be used
     * @param startPos the starting position in the maze
     * @param endPos the ending position in the maze
     * @param startingDirection initial facing direction (E or W, for East or West)
    **************************************************************************/
    private String findSpecificPath(Maze maze, int[] startPos, int[] endPos, char startingDirection) {
        logger.trace("**** Starting right-hand search at: {}, facing {}", startPos, startingDirection);
        StringBuilder path = new StringBuilder();
        int[] currPos = startPos;
        DirectionManager currDirection = new DirectionManager(startingDirection);
        DirectionManager tempDirection; // Stores the direction to the relative right of the current direction
        
        while (!Arrays.equals(currPos, endPos)) {
            logger.trace("**** Pos: [{}, {}]", currPos[0], currPos[1]);

            // Uses temp direction to keep track of relative "right" to current direction
            tempDirection = new DirectionManager(currDirection.getTrueDirection());
            tempDirection.turnRight();

            int[] rightSquare = tempDirection.getNewPosition(currPos);
            int[] forwardSquare = currDirection.getNewPosition(currPos);

            if (maze.checkCoord(rightSquare)) { // Check if right square is valid first
                currDirection.turnRight();
                path.append("RF");
                currPos = rightSquare;
            } else if (maze.checkCoord(forwardSquare)) { // Cant go right attempt to go forward
                path.append('F');
                currPos = forwardSquare;
            } else { // Turn left if all else fails
                currDirection.turnLeft();
                path.append('L');
            }
        }
        
        logger.trace("**** Found path: {}", path);
        return path.toString();
    }
}
