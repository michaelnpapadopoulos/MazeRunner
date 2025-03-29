package ca.mcmaster.se2aa4.mazerunner.path_finding;

import ca.mcmaster.se2aa4.mazerunner.Maze;

public abstract class PathFindingAlgorithm { // Abstract super class for all subsequent path finding algorithms

    /**************************************************************************
     * Method to verify a given path through the maze using a path tester
     * 
     * @param maze the maze object to be used during navigation
     * @param startPos the starting position in the maze
     * @param endPos the ending position in the maze
    **************************************************************************/
    public abstract String findPath(Maze maze, int[] startPos, int[] endPos);
}
