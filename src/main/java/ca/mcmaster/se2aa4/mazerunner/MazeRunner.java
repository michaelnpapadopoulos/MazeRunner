package ca.mcmaster.se2aa4.mazerunner;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class MazeRunner {
    private static final Logger logger = LogManager.getLogger();

    private char[][] maze;
    private int numOfRows;
    private int numOfCols;

    private final int[][] mazeEntrances = new int[2][2]; // Stores west entry at index 0, and east entry at index 1
    private final int[][] directionVectors = new int[][] {{-1,0}, {0,1}, {1,0}, {0,-1}}; // North, East, South, West

    private StringBuilder pathToExit = new StringBuilder();
    private StringBuilder pathToExitFactored = new StringBuilder();

    public MazeRunner(String pathToMazeFile) {
        logger.trace("Constructing MazeRunner object");
        try {
            logger.trace("Reading maze from input file path");
            ArrayList<String> rawMaze = new ArrayList<>(); // Stores the raw data from txt file into dynamic arraylist
            BufferedReader reader = new BufferedReader(new FileReader(pathToMazeFile));

            while (reader.ready()) {
                // Store maze in ArrayList of Strings
                rawMaze.add(reader.readLine());
            }

            this.numOfCols = rawMaze.getFirst().length();
            this.numOfRows = rawMaze.size();

            // Convert raw maze data into a 2d character array for ease of processing
            logger.trace("Storing maze in 2D array");
            this.maze = new char[numOfRows][numOfCols];
            for (int row = 0; row < numOfRows; row++) { // Populate maze array
                for (int col = 0; col < numOfCols; col++) {
                    this.maze[row][col] = rawMaze.get(row).charAt(col);
                }
            }
        } catch (IOException e) {
            logger.error("Unable to read file from path: {}", pathToMazeFile, e);
        }
    }

    public void displayMaze() {
        for (int row = 0; row < numOfRows; row++) { // Populate maze array
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

            if (this.maze[row][numOfCols-1] == ' ') {
                this.mazeEntrances[1] = new int[] {row, numOfCols-1};
            }
        }

        logger.trace("**** Found west and east entry respectively: ({}, {}), ({}, {}) ", this.mazeEntrances[0][0], this.mazeEntrances[0][1], this.mazeEntrances[1][0], this.mazeEntrances[1][1]);
    }

    public void findPath() { // Only need to find path from west side to east (Can derive path for east to west from it)
        findEntryPoints();
        int[] currPos = this.mazeEntrances[0];
        TrueDirection currDir = new TrueDirection('E');

        while (!Arrays.equals(currPos, this.mazeEntrances[1])) {
            logger.info("Pos: [{}, {}], Dir: {}", currPos[0], currPos[1], currDir.getTrueDirection());
            int[] rightSquare = new int[] {currPos[0] + directionVectors[(currDir.getTrueDirection()+1)%4][0], currPos[1] + directionVectors[(currDir.getTrueDirection()+1)%4][1]};
            logger.trace("Checked right square");
            int[] forwardSquare = new int[] {currPos[0] + directionVectors[currDir.getTrueDirection()][0], currPos[1] + directionVectors[currDir.getTrueDirection()][1]};

            if (checkCoord(rightSquare)) {
                currDir.turnRight();
                this.pathToExit.append("RF");
                currPos = rightSquare;
            } else if (checkCoord(forwardSquare)) {
                this.pathToExit.append('F');
                currPos = forwardSquare;
            } else {
                currDir.turnLeft();
                this.pathToExit.append('L');
            }
        }

        logger.info("**** Calculated path to exit using right hand algorithm");
        System.out.println(this.pathToExit.toString());
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

         try {
             // Parse command line arguments and retrieve -i flag argument
             cmd = parser.parse(options, args);
             String mazePath = cmd.getOptionValue("i");

             MazeRunner mr = new MazeRunner(mazePath);
             mr.displayMaze();
             mr.findPath();
         } catch(Exception e) {
             logger.error("/!\\\\ An error has occurred /!\\\\");
         }

         logger.trace("**** Computing path");
         System.out.println("PATH NOT COMPUTED");
         logger.info("** End of MazeRunner");
    }
}
