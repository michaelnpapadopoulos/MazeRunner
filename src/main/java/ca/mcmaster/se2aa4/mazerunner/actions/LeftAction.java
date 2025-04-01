package ca.mcmaster.se2aa4.mazerunner.actions;

import ca.mcmaster.se2aa4.mazerunner.DirectionManager;

public class LeftAction implements Action {
    private final DirectionManager currDirection;

    /**************************************************************************
     * Constructor for LeftAction. Initializes the current direction of the maze runner.
     * 
     * @param currDirection The current direction of the maze runner.
    **************************************************************************/
    public LeftAction(DirectionManager currDirection) {
        this.currDirection = currDirection; 
    }

    @Override
    public void makeAction() {
        currDirection.turnLeft();
    }

    @Override
    public void undoAction() {
        currDirection.turnRight();
    }

    @Override
    public char getActionChar() {
        return 'L';
    }
}
