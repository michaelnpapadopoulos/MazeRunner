package ca.mcmaster.se2aa4.mazerunner;

public class DirectionManager { // Keeps track of direction during path finding

    //=========== DIRECTION ATTRIBUTES ===========//
    private int trueDirection; // Stores a value between 0 and 3, 0 being North, 1 being east, and so on.
    private final int[][] directionVectors = new int[][] {{-1,0}, {0,1}, {1,0}, {0,-1}}; // Movement vectors for North, East, South, West directions respectively


    //=========== DIRECTION CONSTRUCTORS ===========//
    public DirectionManager(char startingDirection) { // Smelly but necessary :(
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

    public DirectionManager(int startingDirection) { this.trueDirection = startingDirection; } // Overloaded constructor for integer direction value

    
    //=========== DIRECTION METHODS ===========//
    public void turnLeft() { this.trueDirection = (this.trueDirection+3)%4; } // Modulo 4 to ensure that the value stays between 0 and 3

    public void turnRight() { this.trueDirection = (this.trueDirection+1)%4; }

    public int[] getNewPosition(int[] position) { // Gets forward square relative to direction
        return new int[] {position[0] + this.directionVectors[this.trueDirection][0], position[1] + this.directionVectors[this.trueDirection][1]};
    }

    public int getTrueDirection() { return this.trueDirection; } // Getter for trueDirection
}
