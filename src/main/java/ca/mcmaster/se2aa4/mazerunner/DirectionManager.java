package ca.mcmaster.se2aa4.mazerunner;

public class DirectionManager { // Keeps track of absolute direction during path finding
    private int trueDirection; // Stores a value between 0 and 3, 0 being North, 1 being east, and so on.
    private final int[][] directionVectors = new int[][] {{-1,0}, {0,1}, {1,0}, {0,-1}}; // Movement vectors for North, East, South, West directions respectively


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

    public void turnLeft() { this.trueDirection = (this.trueDirection+3)%4; }

    public void turnRight() { this.trueDirection = (this.trueDirection+1)%4; }

    public int[] getNewPosition(int[] position) { // Gets forward square relative to direction
        return new int[] {position[0] + this.directionVectors[this.trueDirection][0], position[1] + this.directionVectors[this.trueDirection][1]};
    }

    public int getTrueDirection() { return this.trueDirection; }
}