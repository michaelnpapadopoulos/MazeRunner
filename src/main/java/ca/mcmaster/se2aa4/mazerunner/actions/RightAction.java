package ca.mcmaster.se2aa4.mazerunner.actions;

import ca.mcmaster.se2aa4.mazerunner.DirectionManager;

public class RightAction implements Action {
    private final DirectionManager currDirection;

    /**************************************************************************
     * Constructor for RightAction. Initializes the current direction of the maze runner.
     * 
     * @param currDirection The current direction of the maze runner.
    **************************************************************************/
    public RightAction(DirectionManager currDirection) {
        this.currDirection = currDirection; 
    }

    @Override
    public void makeAction() {
        currDirection.turnRight();
    }

    @Override
    public void undoAction() {
        currDirection.turnLeft();
    }

    @Override
    public char getActionChar() {
        return 'R';
    }
}
