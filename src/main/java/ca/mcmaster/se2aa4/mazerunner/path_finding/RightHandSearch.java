package ca.mcmaster.se2aa4.mazerunner.path_finding;

import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

import java.util.Arrays;

import ca.mcmaster.se2aa4.mazerunner.DirectionManager;
import ca.mcmaster.se2aa4.mazerunner.Maze;
import ca.mcmaster.se2aa4.mazerunner.actions.ActionManager;
import ca.mcmaster.se2aa4.mazerunner.actions.ForwardAction;
import ca.mcmaster.se2aa4.mazerunner.actions.LeftAction;
import ca.mcmaster.se2aa4.mazerunner.actions.RightAction;
import ca.mcmaster.se2aa4.mazerunner.utilities.StartDirectionFinder;

public class RightHandSearch extends PathFindingAlgorithm implements StartDirectionFinder {

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
        return shortestPath;
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
        ActionManager actionManager = new ActionManager();
        DirectionManager currDirection = new DirectionManager(startingDirection);
        DirectionManager relativeRightDirection;
        int[] currPos = startPos.clone(); // Preserve original start position

        while (!Arrays.equals(currPos, endPos)) {
            logger.trace("**** Pos: [{}, {}]", currPos[0], currPos[1]);

            // Uses temp direction to keep track of relative "right" to current direction
            relativeRightDirection = new DirectionManager(currDirection.getTrueDirection());
            relativeRightDirection.turnRight();

            int[] rightSquare = relativeRightDirection.getNewPosition(currPos);
            int[] forwardSquare = currDirection.getNewPosition(currPos);

            if (maze.checkCoord(rightSquare)) { // Check if right square is valid first
                // Turn right and move forward
                actionManager.executeAction(new RightAction(currDirection));
                actionManager.executeAction(new ForwardAction(currPos, currDirection));
            } else if (maze.checkCoord(forwardSquare)) { // Cant go right attempt to go forward
                actionManager.executeAction(new ForwardAction(currPos, currDirection));
            } else { // Turn left if all else fails
                actionManager.executeAction(new LeftAction(currDirection));
            }
        }
        
        logger.trace("**** Found path: {}", actionManager.getPath());
        return actionManager.getPath();
    }
}
