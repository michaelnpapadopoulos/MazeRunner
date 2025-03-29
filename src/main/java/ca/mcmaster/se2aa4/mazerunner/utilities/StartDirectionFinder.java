package ca.mcmaster.se2aa4.mazerunner.utilities;

public interface StartDirectionFinder {

    /**************************************************************************
     * Detetmine the starting direction of the maze runner
     * 
     * @param startPos The starting position
     * @param endPos   The ending position
    **************************************************************************/
    default char determineDirection(int[] startPos, int[]endPos) { 
        if (startPos[1] > endPos[1]) { // Compares column values to determine direction
            return 'W';
        } else {
            return 'E';
        }
    }
    
}
