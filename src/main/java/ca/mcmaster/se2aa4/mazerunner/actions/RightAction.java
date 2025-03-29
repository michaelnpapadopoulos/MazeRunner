package ca.mcmaster.se2aa4.mazerunner.actions;

import ca.mcmaster.se2aa4.mazerunner.DirectionManager;

public class RightAction implements Action {
    private final DirectionManager currDirection;

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
