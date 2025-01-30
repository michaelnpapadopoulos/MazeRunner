package ca.mcmaster.se2aa4.mazerunner;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class MazeRunner implements StringConverter { // Implements StringConverter interface

    public static final Logger logger = LogManager.getLogger(); // Logger object    
    private final Maze maze; // Stores maze object for processing throughout methods

    
    public MazeRunner(String pathToMazeFile) { // Constructor
        logger.trace("**** Constructing MazeRunner object");
        this.maze = new Maze(pathToMazeFile);
    }

    
    //=========== PATH FINDING METHODS ===========//
    public void findPath(PathFindingAlgorithm pfa) { // Finds path using a given path finding algorithm
        logger.info("** Computing path");
        int[] startingPos = this.maze.getEntryPoints()[0];
        int[] endingPos = this.maze.getEntryPoints()[1];

        String pathToExit = pfa.findPath(this.maze, startingPos, endingPos);
        
        System.out.println("Found path: "+ pathToExit);
    }

    
    //=========== PATH VERIFICATION METHODS ===========//
    public void verifyPath(String path) { // Checks if path is valid from west to east and east to west
        logger.info("** Verifying path: {}", path);

        String cleanedPath = convertToUnfactored(removeSpaces(path));
    
        boolean westToEast = verifySpecificPath(cleanedPath, this.maze.getEntryPoints()[0], this.maze.getEntryPoints()[1], 'E');
        boolean eastToWest = verifySpecificPath(cleanedPath, this.maze.getEntryPoints()[1], this.maze.getEntryPoints()[0], 'W');

        if (westToEast) {
            System.out.println("The following path is valid from West to East:\n> " + path);
        } else if (eastToWest) {
            System.out.println("The following path is valid from East to West:\n> " + path);
        } else {
            System.out.println("The following path is invalid:\n> " + path);
        }
    }

    private boolean verifySpecificPath(String path, int[] startingPos, int[] endingPos, char startingDirection) {
        logger.trace("Verifying path with start: {}, end: {}", startingPos, endingPos);
        int[] currPos = startingPos;
        DirectionManager currDirection = new DirectionManager(startingDirection);

        for (int charIndex = 0; charIndex < path.length(); charIndex++) { // Iterates through entire path string
            char currChar = path.charAt(charIndex);

            if (currChar == 'F') {
                currPos = currDirection.getNewPosition(currPos);
            } else if (currChar == 'R') {
                currDirection.turnRight();
            } else {
                currDirection.turnLeft();
            }

            if (!this.maze.checkCoord(currPos)) { // False if position is "out of bounds"
                return false;
            }
        }

        return Arrays.equals(currPos, endingPos);
    }

    
    //=========== MAIN METHOD ===========//
    public static void main(String[] args) {

        logger.info("** Starting Maze Runner");

        // Create new options object and add command line flags
        Options options = new Options();
        options.addOption("i", true, "Path to the maze txt file");
        options.addOption("p", true, "User provided path through a given maze");

        // create command line and command line parser objects
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            // Parse command line arguments and retrieve -i flag argument
            cmd = parser.parse(options, args);

            if (cmd.hasOption("i")) {
                String mazeFilePath = cmd.getOptionValue("i");
                MazeRunner mazeRunner = new MazeRunner(mazeFilePath);

                if (cmd.hasOption("p")) {
                    String userPath = cmd.getOptionValue("p");
                    mazeRunner.verifyPath(userPath);
                } else {
                    mazeRunner.findPath(new RightHandSearch());
                }
                
            } else {
                System.out.println("**Please provide the path to a maze.txt file using the -i flag**");
            }

        } catch(ParseException e) { logger.error("/!\\\\ An error has occurred whilst trying to parse command line arguments /!\\\\", e); }

        logger.info("** End of MazeRunner");
    }
}
