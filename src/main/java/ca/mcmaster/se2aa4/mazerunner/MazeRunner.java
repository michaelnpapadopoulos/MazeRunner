package ca.mcmaster.se2aa4.mazerunner;

import java.io.*;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class MazeRunner {
    private static final Logger logger = LogManager.getLogger();
    private char[][] maze;
    private int numOfRows;
    private int numOfCols;
    private StringBuilder pathToExit;
    private StringBuilder pathToExitFactored;

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

    // Find entry point method (always on the west side)

    // Test if a given path is viable (Easiest to just simulate by attempting to take a given path)

    // Find sequence of directions

    // Convert sequence of directions to factored form



    
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
         } catch(Exception e) {
             logger.error("/!\\\\ An error has occurred /!\\\\");
         }

         logger.trace("**** Computing path");
         System.out.println("PATH NOT COMPUTED");
         logger.info("** End of MazeRunner");
    }
}
