package ca.mcmaster.se2aa4.mazerunner;

import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

public abstract class PathFindingAlgorithm {

    protected final StringBuilder foundPath = new StringBuilder();
    protected final Maze maze;
    protected final DirectionManager direction;
    protected int[] position;

    public PathFindingAlgorithm(Maze maze, int[] position, DirectionManager direction) {
        logger.trace("**** Constructing pathFinder object");
        this.maze = maze;
        this.direction = direction;
        this.position = position;
    }

    public abstract void findPath();

    public String getFoundPath() {
        logger.trace("**** Getting path to exit");
        return this.foundPath.toString();
    }
}
