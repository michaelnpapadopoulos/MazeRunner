package ca.mcmaster.se2aa4.mazerunner;

public abstract class PathTester { // Abstract super class for all subsequent path testing algorithms/strategies

    public abstract boolean testPath(String path, Maze maze, int[] startPos, int[] endPos);
     
}
