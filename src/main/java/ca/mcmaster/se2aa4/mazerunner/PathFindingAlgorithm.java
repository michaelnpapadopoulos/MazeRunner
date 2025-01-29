package ca.mcmaster.se2aa4.mazerunner;

import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

public abstract class PathFindingAlgorithm {

    protected final Maze maze;
    protected int[] startPosition;
    protected int[] endPosition;

    public PathFindingAlgorithm(Maze maze, int[] startPos, int[] endPos) {
        logger.trace("**** Constructing pathFinder object");
        this.maze = maze;
        this.startPosition = startPos;
        this.endPosition = endPos;
    }

    public abstract String findPath();
}
