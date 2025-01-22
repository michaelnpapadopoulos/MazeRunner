package ca.mcmaster.se2aa4.mazerunner;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class MazeRunner {
    private static final Logger logger = LogManager.getLogger(); // Logger object

    // Store maze as 2d char array, as well as size of array
    private char[][] maze;
    private int numOfRows;
    private int numOfCols;

    private final int[][] mazeEntrances = new int[2][2]; // Stores west entry at index 0, and east entry at index 1

    private StringBuilder pathToExit = new StringBuilder(); // Stores the un-factored path to exit

    public MazeRunner(String pathToMazeFile) { // Constructor
        logger.trace("**** Constructing MazeRunner object");

        try {
            logger.trace("**** Reading maze from input file path");
            ArrayList<String> rawMaze = new ArrayList<>(); // Stores the raw data from txt file into dynamic arraylist
            BufferedReader reader = new BufferedReader(new FileReader(pathToMazeFile));

            while (reader.ready()) {
                // Store maze in ArrayList of Strings
                rawMaze.add(reader.readLine());
            }

            this.numOfCols = rawMaze.getFirst().length();
            this.numOfRows = rawMaze.size();

            StringBuilder emptyRow = new StringBuilder();
            for (int size = 0; size < numOfCols; size++) {
                emptyRow.append(' ');
            }

            while (rawMaze.contains("")) {
                logger.trace("**** Maze contains a blank row, replacing with empty string of correct size");
                rawMaze.set(rawMaze.indexOf(""), emptyRow.toString());
            }

            logger.trace("**** Dimensions of maze: {} x {}", numOfRows, numOfCols);

            // Convert raw maze data into a 2d character array for ease of processing
            logger.trace("**** Storing maze in 2D array");
            this.maze = new char[numOfRows][numOfCols];
            for (int row = 0; row < numOfRows; row++) { // Populate maze array
                for (int col = 0; col < numOfCols; col++) {
                    logger.trace("**** Assigning index {}, {} to maze", row, col);
                    this.maze[row][col] = rawMaze.get(row).charAt(col);
                }
            }
            logger.trace("**** Stored maze in 2D array");
        } catch (IOException e) {
            logger.error("Unable to read file from path: {}", pathToMazeFile, e);
        }
    }

    public void displayMaze() { // Displays the maze to std out
        for (int row = 0; row < numOfRows; row++) {
            for (int col = 0; col < numOfCols; col++) {
                System.out.printf("%c", this.maze[row][col]);
            }
            System.out.println();
        }
    }

    public void findEntryPoints() {
        logger.trace("**** Finding maze entry points");
        for (int row = 0; row < numOfRows; row++) { // Entrance in west wall
            if (this.maze[row][0] == ' ') {
                this.mazeEntrances[0] = new int[] {row, 0};
            }

            if (this.maze[row][numOfCols-1] == ' ') { // Entrance in east wall
                this.mazeEntrances[1] = new int[] {row, numOfCols-1};
            }
        }

        logger.trace("**** Found west and east entry respectively: ({}, {}), ({}, {}) ", this.mazeEntrances[0][0], this.mazeEntrances[0][1], this.mazeEntrances[1][0], this.mazeEntrances[1][1]);
    }

    public void findPath() {
        findEntryPoints();
        int[] currentPos = this.mazeEntrances[0];

        logger.trace("**** beginning forward search");
        while (!Arrays.equals(currentPos, this.mazeEntrances[1])) { // While not at exit of maze
            int[] forwardPos = new int[] {currentPos[0], currentPos[1] + 1}; // Gets location of forward tile

            if (checkCoord(forwardPos)) { // Checks forward square
                this.pathToExit.append('F');
                currentPos = forwardPos;
            } else {
                logger.info("** Cant find simple forward path through maze");
            }
        }
    }

    public void outputPath() {
        System.out.println("Path through maze: " + this.pathToExit);
    }

    private boolean checkCoord(int[] coordinate) { // Returns true if coordinate exists and is not a wall
        logger.trace("Checking coordinate {}, {}", coordinate[0], coordinate[1]);
        if (coordinate[0] < 0 || coordinate[0] >= numOfRows || coordinate[1] < 0 || coordinate[1] >= numOfCols) {
            return false;
        }

        return this.maze[coordinate[0]][coordinate[1]] == ' ';
    }



    
    public static void main(String[] args) {
         logger.info("** Starting Maze Runner");
        
         // Create new options object and add a command line flag
         Options options = new Options();
         options.addOption("i", true, "Path to the input maze txt file");

         // create command line and command line parser objects
         CommandLineParser parser = new DefaultParser();
         CommandLine cmd;
         MazeRunner mr;

         try {
             // Parse command line arguments and retrieve -i flag argument
             cmd = parser.parse(options, args);
             String mazeFilePath = cmd.getOptionValue("i");

             mr = new MazeRunner(mazeFilePath);

             logger.trace("**** Computing path");
             mr.findPath();
             mr.outputPath();
         } catch(Exception e) {
             logger.error("/!\\\\ An error has occurred /!\\\\", e);
         }





         logger.info("** End of MazeRunner");
    }
}
