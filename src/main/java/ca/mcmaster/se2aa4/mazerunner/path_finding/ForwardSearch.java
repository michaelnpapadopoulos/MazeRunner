package ca.mcmaster.se2aa4.mazerunner.path_finding;

import java.util.Arrays;

import ca.mcmaster.se2aa4.mazerunner.DirectionManager;
import ca.mcmaster.se2aa4.mazerunner.Maze;
import ca.mcmaster.se2aa4.mazerunner.utilities.StartDirectionFinder;
import ca.mcmaster.se2aa4.mazerunner.utilities.StringConverter;

import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

public class ForwardSearch extends PathFindingAlgorithm implements StringConverter, StartDirectionFinder {

    public String findPath(Maze maze, int[] startPos, int[] endPos) { // Finds path from start to end by attempting to move forward
        logger.trace("**** Finding path using forward search");

        StringBuilder foundPath = new StringBuilder(); // Stores the path found by the algorithm
        DirectionManager currDirection = new DirectionManager(determineDirection(startPos, endPos)); // Manages direction of movement

        while (!Arrays.equals(startPos, endPos)) { // While not at exit of maze
            int[] forwardPos = currDirection.getNewPosition(startPos); // Gets location of forward (Eastward) tile

            if (maze.checkCoord(forwardPos)) { // Checks forward square
                foundPath.append('F');
                startPos = forwardPos;
            } else {
                foundPath.setLength(0); // Clear path if forward square is a wall
                foundPath.append("Forward search algorithm insufficient, unable to find path");
                return foundPath.toString();
            }
        }

        return convertToFactored(foundPath.toString());
    }
}
