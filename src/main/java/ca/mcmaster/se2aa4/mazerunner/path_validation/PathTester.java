package ca.mcmaster.se2aa4.mazerunner.path_validation;

import ca.mcmaster.se2aa4.mazerunner.Maze;

public abstract class PathTester { // Abstract super class for all subsequent path testing algorithms/strategies

    public abstract boolean testPath(String rawPath, Maze maze, int[] startPos, int[] endPos);
     
}
