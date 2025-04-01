package ca.mcmaster.se2aa4.mazerunner.actions;

import java.util.Stack;
/**************************************************************************
 * Using an action manager to provide a cleaner interface for actions
 * throughout the maze. Manages the action (Command) history and the path
 * taken by whatever algorithm is being used.
 * 
 * @param action The action to be added.
**************************************************************************/
public class ActionManager {
    private final Stack<Action> actionHistory;
    private final StringBuilder path;

    /**************************************************************************
     * Constructor for ActionManager. Initializes the path Stringbuilder
     * and action history stack.
    **************************************************************************/
    public ActionManager() {
        this.path = new StringBuilder();
        this.actionHistory = new Stack<>();
    }

    /**************************************************************************
     * Executes an action
     * 
     * @param action The action to be executed.
    **************************************************************************/
    public void executeAction(Action action) {
        action.makeAction();
        addAction(action);
    }

    /**************************************************************************
     * Undoes the last action in the action history stack. Implementation of the 
     * undo method is defined within each concrete action class.
    **************************************************************************/
    public void undoAction() {
        if (!actionHistory.isEmpty()) {
            Action action = actionHistory.pop();
            action.undoAction();
            this.path.setLength(this.path.length() - 1); // Remove the last character from the path string
        }
    }

    /**************************************************************************
     * Adds an action to the action history stack and appends the action to the path.
     * 
     * @param action The action to be added.
    **************************************************************************/
    private void addAction(Action action) {
        this.actionHistory.push(action);
        this.path.append(action.getActionChar());
    }


    /**************************************************************************
     * Clears the path StringBuilder.
    **************************************************************************/
    public void clearPath() {
        this.path.setLength(0);
    }

    /**************************************************************************
     * Returns the path as a string.
    **************************************************************************/
    public String getPath() {
        return path.toString();
    }
}
