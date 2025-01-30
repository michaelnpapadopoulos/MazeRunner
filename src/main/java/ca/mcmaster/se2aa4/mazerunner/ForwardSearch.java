package ca.mcmaster.se2aa4.mazerunner;

import java.util.Arrays;
import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

public class ForwardSearch extends PathFindingAlgorithm implements StringConverter {

    public String findPath(Maze maze, int[] startPos, int[] endPos) {
        logger.trace("**** Finding path using forward search");
        StringBuilder foundPath = new StringBuilder();
        DirectionManager currDirection = new DirectionManager('E'); // Assumes always start facing east

        while (!Arrays.equals(startPos, endPos)) { // While not at exit of maze
            int[] forwardPos = currDirection.getNewPosition(startPos); // Gets location of forward (Eastward) tile

            if (maze.checkCoord(forwardPos)) { // Checks forward square
                foundPath.append('F');
                startPos = forwardPos;
            } else {
                foundPath.setLength(0);
                foundPath.append("Forward search algorithm insufficient, unable to find path");
                return foundPath.toString();
            }
        }

        return convertToFactored(foundPath.toString());
    }
}
