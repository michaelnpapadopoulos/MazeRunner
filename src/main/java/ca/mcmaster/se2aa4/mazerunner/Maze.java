package ca.mcmaster.se2aa4.mazerunner;

import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Maze {
    private final int[][] mazeEntrances;
    private final char[][] maze;
    private int numOfRows;
    private int numOfCols;

    /**************************************************************************
     * Constructor for the Maze class
     * 
     * @param pathToMazeFile the path to the maze.txt file
    **************************************************************************/
    public Maze(String pathToMazeFile) { // Constructor
        logger.trace("**** Constructing Maze object");

        this.mazeEntrances = new int[2][2]; // Initialize mazeEntrances to store two entry points
        ArrayList<String> rawMaze = convertToRawMaze(pathToMazeFile);
        findMazeDimensions(rawMaze);
        verifyMaze(rawMaze);
        this.maze = rawToArray(rawMaze);

        findEntryPoints();
    }

    /**************************************************************************
     * Reads the maze.txt file provided and stores the data in a dynamic arraylist
     * 
     * @param pathToMazeFile the path to the maze.txt file
    **************************************************************************/
    private ArrayList<String> convertToRawMaze(String pathToMazeFile) {
        logger.trace("**** Reading maze from input file path");
        ArrayList<String> rawMaze = new ArrayList<>();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(pathToMazeFile));
            while (reader.ready()) {
                // Store maze in ArrayList of Strings
                rawMaze.add(reader.readLine());
            }
        } catch (IOException e) { // If unable to read file, log error and exit
            logger.error("Unable to read file from path: {}", pathToMazeFile);
            System.err.println("Unable to read file from path: " + pathToMazeFile + "\nPlease provide a valid file path!");
            System.exit(1);
        }

        return rawMaze;
    }

    /**************************************************************************
     * Sets the maze dimensions based on the size of the raw maze data
     * 
     * @param rawMaze the raw maze data
    **************************************************************************/
    private void findMazeDimensions(ArrayList<String> rawMaze) { // Sets class instance variables for maze dimensions
        logger.trace("**** Getting maze dimensions");
        this.numOfRows = rawMaze.size();
        this.numOfCols = rawMaze.getFirst().length();
    }

    /**************************************************************************
     * Verifies that the maze does not contain any entirely blank/empty rows
     * 
     * @param rawMaze the raw maze data
    **************************************************************************/
    private void verifyMaze(ArrayList<String> rawMaze) {
        logger.trace("**** Verifying maze");

        if (rawMaze.contains("")) { // Contains the empty string
            StringBuilder emptyRow = new StringBuilder(); // Stores a string containing only whitespaces of the correct size
            for (int size = 0; size < numOfCols; size++) {
                emptyRow.append(' ');
            }

            while (rawMaze.contains("")) {
                logger.trace("**** Maze contains a blank row, replacing with empty string of correct size");
                rawMaze.set(rawMaze.indexOf(""), emptyRow.toString());
            }
        }
    }

    /**************************************************************************
     * Converts the raw maze data from an ArrayList of Strings to a 2D char array
     * 
     * @param rawMaze the raw maze data
    **************************************************************************/
    private char[][] rawToArray(ArrayList<String> rawMaze) {
        logger.trace("**** Storing maze in 2D array");
        char[][] maze = new char[this.numOfRows][this.numOfCols];
        
        for (int row = 0; row < this.numOfRows; row++) { // Populate maze array
            for (int col = 0; col < this.numOfCols; col++) {
                logger.trace("**** Assigning index {}, {} to maze", row, col);
                maze[row][col] = rawMaze.get(row).charAt(col);
            }
        }

        return maze;
    }

    /**************************************************************************
     * Finds the entry points of the maze by checking the west and east walls
    **************************************************************************/
    private void findEntryPoints() {
        logger.trace("**** Finding maze entry points");
        for (int row = 0; row < this.numOfRows; row++) { 
            if (this.maze[row][0] == ' ') { // Entrance in west wall
                this.mazeEntrances[0] = new int[] {row, 0};
            }

            if (this.maze[row][numOfCols-1] == ' ') { // Entrance in east wall
                this.mazeEntrances[1] = new int[] {row, numOfCols-1};
            }
        }

        logger.trace("**** Found west and east entry respectively: ({}, {}), ({}, {}) ", this.mazeEntrances[0][0], this.mazeEntrances[0][1], this.mazeEntrances[1][0], this.mazeEntrances[1][1]);
    }

    /**************************************************************************
     * Checks if a given coordinate is within the maze bounds and is not a wall
     * 
     * @param coordinate the coordinate to check
    **************************************************************************/
    public boolean checkCoord(int[] coordinate) { // Returns true if coordinate exists and is not a wall
        logger.trace("**** Checking coordinate {}, {}", coordinate[0], coordinate[1]);

        if (coordinate[0] < 0 || coordinate[0] >= numOfRows || coordinate[1] < 0 || coordinate[1] >= numOfCols) {
            return false;
        }

        return this.maze[coordinate[0]][coordinate[1]] == ' ';
    }

    /**************************************************************************
     * Returns a copy of the maze entrances as a 2D int array
    **************************************************************************/
    public int[][] getEntryPoints() { return this.mazeEntrances.clone(); }

    /**************************************************************************
     * Getter for maze rows
    **************************************************************************/
    public int getNumOfRows() { return this.numOfRows; } // Getter for number of rows
    
    /**************************************************************************
     * Getter for maze columns
    **************************************************************************/
    public int getNumOfCols() { return this.numOfCols; } // Getter for number of columns
}
