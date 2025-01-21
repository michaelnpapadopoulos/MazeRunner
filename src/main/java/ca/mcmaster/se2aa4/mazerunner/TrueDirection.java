package ca.mcmaster.se2aa4.mazerunner;

public class TrueDirection { // ADT to keep track of absolute direction during path finding
    private int trueDirection; // Stores a value between 0 and 3, 0 being North, 1 being east, and so on.
    private int[][] directionVectors = new int[][] {{-1,0}, {0,1}, {1,0}, {0,-1}};

    public TrueDirection(char startingDirection) { // Smelly but necessary :(
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

    public char turnLeft() {
        this.trueDirection = (this.trueDirection-1)%4;
        return 'L';
    }

    public char turnRight() {
        this.trueDirection = (this.trueDirection+1)%4;
        return 'R';
    }

    public int getTrueDirection() {
        return this.trueDirection;
    }
}
