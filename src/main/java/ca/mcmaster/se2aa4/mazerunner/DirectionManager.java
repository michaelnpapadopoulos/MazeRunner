package ca.mcmaster.se2aa4.mazerunner;

public class DirectionManager {
    private final int[][] directionVectors = new int[][] {{-1,0}, {0,1}, {1,0}, {0,-1}}; // Movement vectors for North, East, South, West directions respectively
    private int trueDirection; // Stores a value between 0 and 3, 0 being North, 1 being east, and so on.
   

    /**************************************************************************
     * Constructor for the DirectionManager class
     * 
     * @param startingDirection The starting direction of the maze runner
    **************************************************************************/
    public DirectionManager(char startingDirection) {
        switch (startingDirection) {
            case 'N':
                this.trueDirection = 0;
                break;
            case 'E':
                this.trueDirection = 1;
                break;
            case 'S':
                this.trueDirection = 2;
                break;
            case 'W':
                this.trueDirection = 3;
                break;
        }
    }

    /**************************************************************************
     * Overloaded constructor for the DirectionManager class
     * 
     * @param startingDirection The int starting direction of the maze runner
    **************************************************************************/
    public DirectionManager(int startingDirection) { this.trueDirection = startingDirection; }

    
    /**************************************************************************
     * Turns the maze runner left by using the modulo operator to ensure that the
     * value stays between 0 and 3.
    **************************************************************************/
    public void turnLeft() { this.trueDirection = (this.trueDirection+3)%4; } // Modulo 4 to ensure that the value stays between 0 and 3

    /**************************************************************************
     * Turns the maze runner right by using the modulo operator to ensure that the
     * value stays between 0 and 3.
    **************************************************************************/
    public void turnRight() { this.trueDirection = (this.trueDirection+1)%4; }

    /**************************************************************************
     * Gets the new position of the maze runner based on the current position and
     * the current direction of the maze runner.
     * 
     * @param position The current position of the maze runner
    **************************************************************************/
    public int[] getNewPosition(int[] position) {
        int newX = position[0] + this.directionVectors[this.trueDirection][0]; // Calculate new X position
        int newY = position[1] + this.directionVectors[this.trueDirection][1]; // Calculate new Y position
        return new int[] {newX, newY}; // Return the new position as an array
    }

    /**************************************************************************
     * Returns the current direction of the maze runner as an int value
    **************************************************************************/
    public int getTrueDirection() { return this.trueDirection; }
}
