package ca.mcmaster.se2aa4.mazerunner.actions;

import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

import ca.mcmaster.se2aa4.mazerunner.DirectionManager;

public class ForwardAction implements Action {
    private final DirectionManager currDirection;
    private final int[] prevPos;
    private int[] currPos; 

    /**************************************************************************
     * Constructor for ForwardAction. Initializes the current position and 
     * direction of the maze runner.
     * 
     * @param currPos The current position of the maze runner.
     * @param currDirection The current direction of the maze runner.
    **************************************************************************/
    public ForwardAction(int[] currPos, DirectionManager currDirection) {
        this.currDirection = currDirection;
        this.prevPos = new int[2];
        this.currPos = currPos;        
    }

    @Override
    public void makeAction() {
        int[] newPos = currDirection.getNewPosition(currPos);
        // Store the previous position before moving forward
        prevPos[0] = currPos[0]; 
        prevPos[1] = currPos[1];

        // Move forward to the new position
        currPos[0] = newPos[0];
        currPos[1] = newPos[1];
        logger.trace("**** Moving forward to: {}", currPos);
    }

    @Override
    public void undoAction() {
        this.currPos[0] = prevPos[0]; 
        this.currPos[1] = prevPos[1];
    }

    @Override
    public char getActionChar() {
        return 'F';
    }
}
