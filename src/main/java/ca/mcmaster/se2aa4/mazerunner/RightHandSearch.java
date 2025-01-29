package ca.mcmaster.se2aa4.mazerunner;

import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

import java.util.Arrays;

public class RightHandSearch extends PathFindingAlgorithm {
    
    public RightHandSearch(Maze maze, int[] startPos, int[] endPos) {
        super(maze, startPos, endPos);
    }

    private String findSpecificPath(Maze maze, int[] startPos, int[] endPos, char startingDirection) {
        logger.trace("**** Starting search at: {}, facing {}", startPos, startingDirection);
        StringBuilder path = new StringBuilder();
        int[] currPos = startPos;
        int[] exitPos = endPos;
        DirectionManager currDirection = new DirectionManager(startingDirection);
        DirectionManager tempDirection; // Stores the direction to the right of the current direction
        
        while (!Arrays.equals(currPos, exitPos)) {
            logger.trace("**** Pos: [{}, {}]", currPos[0], currPos[1]);
            tempDirection = new DirectionManager(currDirection.getTrueDirection());
            tempDirection.turnRight();

            int[] rightSquare = tempDirection.getNewPosition(currPos);
            int[] forwardSquare = currDirection.getNewPosition(currPos);

            if (maze.checkCoord(rightSquare)) {
                currDirection.turnRight();
                path.append("RF");
                currPos = rightSquare;
            } else if (maze.checkCoord(forwardSquare)) {
                path.append('F');
                currPos = forwardSquare;
            } else {
                currDirection.turnLeft();
                path.append('L');
            }
        }
        
        logger.trace("**** Found path: {}", path);
        return path.toString();
    }

    public String findPath() {
        logger.trace("**** Finding path using right hand search");
        String westToEastPath = findSpecificPath(this.maze, this.startPosition, this.endPosition, 'E');
        String eastToWestPath = findSpecificPath(this.maze, this.endPosition, this.startPosition, 'W');

        String shortestPath = new String();
        String startingWall = new String();

        if (westToEastPath.length() > eastToWestPath.length()) {
            shortestPath = eastToWestPath;
            startingWall = "East";
        } else {
            shortestPath = westToEastPath;
            startingWall = "West";
        }

        System.out.println("Starting wall: " + startingWall);
        return shortestPath;
    }

}
