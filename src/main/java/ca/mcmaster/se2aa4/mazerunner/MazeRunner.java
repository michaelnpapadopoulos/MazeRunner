package ca.mcmaster.se2aa4.mazerunner;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class MazeRunner {
    public static final Logger logger = LogManager.getLogger(); // Logger object

    private final Maze maze;
    private StringBuilder pathToExit = new StringBuilder(); // Stores the un-factored path to exit

    public MazeRunner(String pathToMazeFile) { // Constructor
        logger.trace("**** Constructing MazeRunner object");

        maze = new Maze(pathToMazeFile);
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

    public void verifyPath(String path) {
        DirectionManager direction = new DirectionManager('E');
        int[] currPos = this.mazeEntrances[0];

        for (int charIndex = 0; charIndex < path.length(); charIndex++) {
            char currChar = path.charAt(charIndex);
            if (currChar == 'F') {
                currPos = direction.getNewPosition(direction, currPos);
            } else if (currChar == 'R') {
                direction.turnRight();
            } else {
                direction.turnLeft();
            }

            if (!checkCoord(currPos)) {
                System.out.println("Not a valid path!");
                return;
            }
        }

        if (Arrays.equals(currPos, this.mazeEntrances[1])) {
            System.out.println(path + " is a valid path!");
        } else {
            System.out.println(path + " is not valid path!");
        }
    }

    
    public static void main(String[] args) {
         logger.info("** Starting Maze Runner");
        
         // Create new options object and add a command line flag
         Options options = new Options();
         options.addOption("i", true, "Path to the input maze txt file");
         options.addOption("p", true, "User provided path through maze");

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

             if (cmd.hasOption("p")) {
                 String userPath = cmd.getOptionValue("p");
                 mr.verifyPath(userPath);
             }

         } catch(Exception e) {
             logger.error("/!\\\\ An error has occurred /!\\\\", e);
         }





         logger.info("** End of MazeRunner");
    }
}
