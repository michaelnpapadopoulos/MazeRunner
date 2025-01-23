package ca.mcmaster.se2aa4.mazerunner;

import java.util.Arrays;

import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

public class ForwardSearchAlgorithm extends PathFindingAlgorithm {

    public ForwardSearchAlgorithm(Maze maze, int[] position, DirectionManager direction) {
        super(maze, position, direction);
    }

    public void findPath() {
        logger.trace("**** beginning forward search");

        while (!Arrays.equals(this.position, this.maze.getEntryPoints()[1])) { // While not at exit of maze
            int[] forwardPos = this.direction.getNewPosition(this.position); // Gets location of forward (Eastward) tile

            if (maze.checkCoord(forwardPos)) { // Checks forward square
                this.foundPath.append('F');
                this.position = forwardPos;
            } else {
                this.foundPath.setLength(0);
                this.foundPath.append("Forward search algorithm insufficient, unable to find path");
                break;
            }
        }
    }
}
