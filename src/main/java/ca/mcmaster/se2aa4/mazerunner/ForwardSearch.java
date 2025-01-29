package ca.mcmaster.se2aa4.mazerunner;

import java.util.Arrays;
import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

public class ForwardSearch extends PathFindingAlgorithm {

    public ForwardSearch(Maze maze, int[] startPos, int[] endPos) {
        super(maze, startPos, endPos);
    }

    public String findPath() {
        logger.trace("**** beginning forward search");
        StringBuilder foundPath = new StringBuilder();
        DirectionManager currDirection = new DirectionManager('E'); // Assumes always start facing east

        while (!Arrays.equals(this.startPosition, this.endPosition)) { // While not at exit of maze
            int[] forwardPos = currDirection.getNewPosition(startPosition); // Gets location of forward (Eastward) tile

            if (maze.checkCoord(forwardPos)) { // Checks forward square
                foundPath.append('F');
                startPosition = forwardPos;
            } else {
                foundPath.setLength(0);
                foundPath.append("Forward search algorithm insufficient, unable to find path");
                break;
            }
        }

        return foundPath.toString();
    }
}
