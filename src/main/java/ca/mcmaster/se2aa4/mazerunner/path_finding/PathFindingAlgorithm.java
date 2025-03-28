package ca.mcmaster.se2aa4.mazerunner.path_finding;

import ca.mcmaster.se2aa4.mazerunner.Maze;

public abstract class PathFindingAlgorithm { // Abstract super class for all subsequent path finding algorithms

    public abstract String findPath(Maze maze, int[] startPos, int[] endPos);
}
