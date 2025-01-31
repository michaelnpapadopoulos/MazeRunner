package ca.mcmaster.se2aa4.mazerunner;

public interface StartDirectionFinder {

    default char determineDirection(int[] startPos, int[]endPos) { // Compares column values to determine direction
        if (startPos[1] > endPos[1]) {
            return 'W';
        } else {
            return 'E';
        }
    }
    
}
