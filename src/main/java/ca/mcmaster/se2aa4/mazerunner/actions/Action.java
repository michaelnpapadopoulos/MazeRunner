package ca.mcmaster.se2aa4.mazerunner.actions;

/**************************************************************************
 * Application of the Command design pattern. This interface is used to define the
 * actions that can be performed throughout the path finding and path testing
 * algorithms.
**************************************************************************/
public interface Action {
    public void makeAction(); // Execute some action
    public void undoAction(); // Undo some action
    public char getActionChar(); // Get the character representation of the action
}
