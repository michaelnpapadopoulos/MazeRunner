package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

public class Maze {
    private final char[][] maze;
    private int numOfRows;
    private int numOfCols;
    private final int[][] mazeEntrances = new int[2][2];

    public Maze(String pathToMazeFile) { // Constructor
        logger.trace("**** Constructing Maze object");

        ArrayList<String> rawMaze = convertToRawMaze(pathToMazeFile);
        getMazeDimensions(rawMaze);
        verifyMaze(rawMaze);
        this.maze = rawToArray(rawMaze);

        findEntryPoints();
    }


    //=========== MAZE CREATION METHODS ===========//
    private ArrayList<String> convertToRawMaze(String pathToMazeFile) {
        logger.trace("**** Reading maze from input file path");
        ArrayList<String> rawMaze = new ArrayList<>(); // Stores the raw data from txt file into dynamic arraylist
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(pathToMazeFile));
            while (reader.ready()) {
                // Store maze in ArrayList of Strings
                rawMaze.add(reader.readLine());
            }
        } catch (IOException e) {
            logger.error("Unable to read file from path: {}", pathToMazeFile, e);
        }

        return rawMaze;
    }

    private void getMazeDimensions(ArrayList<String> rawMaze) {
        logger.trace("**** Getting maze dimensions");
        this.numOfRows = rawMaze.size();
        this.numOfCols = rawMaze.getFirst().length();
    }

    private void verifyMaze(ArrayList<String> rawMaze) { // Ensures no empty rows in maze (Possibly unnecessary, but included for robustness)
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
    

    //=========== MAZE INTERACTION METHODS ===========//
    public void displayMaze() { // Displays the maze to std out (For later development)
        for (int row = 0; row < this.numOfRows; row++) {
            for (int col = 0; col < this.numOfCols; col++) {
                System.out.printf("%c", this.maze[row][col]);
            }
            System.out.println();
        }
    }

    public boolean checkCoord(int[] coordinate) { // Returns true if coordinate exists and is not a wall
        logger.trace("**** Checking coordinate {}, {}", coordinate[0], coordinate[1]);

        if (coordinate[0] < 0 || coordinate[0] >= numOfRows || coordinate[1] < 0 || coordinate[1] >= numOfCols) {
            return false;
        }

        return this.maze[coordinate[0]][coordinate[1]] == ' ';
    }

    public int[][] getEntryPoints() { return this.mazeEntrances; } // Getter for entry points
}
