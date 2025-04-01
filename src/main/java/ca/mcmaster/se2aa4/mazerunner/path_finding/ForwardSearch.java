package ca.mcmaster.se2aa4.mazerunner.path_finding;

import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

import java.util.Arrays;

import ca.mcmaster.se2aa4.mazerunner.DirectionManager;
import ca.mcmaster.se2aa4.mazerunner.Maze;
import ca.mcmaster.se2aa4.mazerunner.actions.ActionManager;
import ca.mcmaster.se2aa4.mazerunner.actions.ForwardAction;
import ca.mcmaster.se2aa4.mazerunner.utilities.StartDirectionFinder;

public class ForwardSearch extends PathFindingAlgorithm implements StartDirectionFinder {

    /**************************************************************************
     * Finds path through maze by simply attempting to move forward. Will
     * return a string of the path taken, or an insufficient path message if
     * unable to find path moving forward.
     * 
     * @param maze the maze object to be used
     * @param startPos the west entrance in the maze
     * @param endPos the east entrance in the maze
    **************************************************************************/
    public String findPath(Maze maze, int[] startPos, int[] endPos) {
        logger.trace("**** Finding path using forward search");
        DirectionManager currDirection = new DirectionManager(determineDirection(startPos, endPos)); // Manages direction of movement
        ActionManager actionManager = new ActionManager(); // Tracks actions taken and the path they construct
        int[] currPos = startPos.clone(); // Set current position to start position

        while (!Arrays.equals(currPos, endPos)) { // While not at exit of maze
            int[] forwardPos = currDirection.getNewPosition(currPos); // Gets location of forward (Eastward) tile

            if (maze.checkCoord(forwardPos)) { // Checks forward square
                actionManager.executeAction(new ForwardAction(currPos, currDirection)); // Executes action to move forward
            } else {
                return "Forward search algorithm insufficient, unable to find path";
            }
        }

        return actionManager.getPath(); // Returns the path taken
    }
}
