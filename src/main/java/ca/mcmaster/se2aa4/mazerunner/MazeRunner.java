package ca.mcmaster.se2aa4.mazerunner;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class MazeRunner implements StringConverter { // Implements StringConverter interface
    public static final Logger logger = LogManager.getLogger(); // Logger object

    // Instance variables to store a maze and direction object
    private final Maze maze;
    private final DirectionManager startingDirection;

    public MazeRunner(String pathToMazeFile) { // Constructor
        logger.trace("**** Constructing MazeRunner object");

        this.maze = new Maze(pathToMazeFile);
        startingDirection = new DirectionManager('E'); // Assumes always start facing east
    }


    public void findPath() {
        logger.trace("**** Computing path");
        int[] startingPos = this.maze.getEntryPoints()[0];
        int[] endingPos = this.maze.getEntryPoints()[1];

        PathFindingAlgorithm pfa = new RightHandSearch(this.maze, startingPos, endingPos);
        String pathToExit = pfa.findPath();

        pathToExit = convertToFactored(pathToExit);
        
        System.out.println("Found path: "+ pathToExit);
    }



    public void verifyPath(String path) { // Checks if path is valid from west to east and east to west
        logger.trace("**** Verifying path: {}", path);
        int[] currPos = this.maze.getEntryPoints()[0]; // Assumes always start at west wall

        for (int charIndex = 0; charIndex < path.length(); charIndex++) { // Iterates through entire path string
            char currChar = path.charAt(charIndex);
            if (currChar == 'F') {
                currPos = this.startingDirection.getNewPosition(currPos);
            } else if (currChar == 'R') {
                startingDirection.turnRight();
            } else {
                startingDirection.turnLeft();
            }

            if (!this.maze.checkCoord(currPos)) {
                System.out.println("Input path valid: " + false);
                return;
            }
        }

        boolean validPath = Arrays.equals(currPos, this.maze.getEntryPoints()[1]);
        System.out.println("Input path valid: " + validPath);
    }

    
    public static void main(String[] args) {
         logger.info("** Starting Maze Runner");
        
         // Create new options object and add command line flags
         Options options = new Options();
         options.addOption("i", true, "Path to the input maze txt file");
         options.addOption("p", true, "User provided path through maze");

         // create command line and command line parser objects
         CommandLineParser parser = new DefaultParser();
         CommandLine cmd;

         try {
             // Parse command line arguments and retrieve -i flag argument
             cmd = parser.parse(options, args);

             if (cmd.hasOption("i")) {
                 String mazeFilePath = cmd.getOptionValue("i");
                 MazeRunner mr = new MazeRunner(mazeFilePath);
                 mr.findPath();

                 if (cmd.hasOption("p")) {
                     String userPath = cmd.getOptionValue("p");
                     mr.verifyPath(userPath);
                 }

                 logger.trace("Converted to factored: {}", mr.convertToFactored("FLFFFFFFFFFFFFFFFFFFFRFFRRRRRRRRRRRRRRFFLFFFFFFRFFFFLL"));
                 logger.trace("Converted to unfactored: {}", mr.convertToUnfactored("F L 5F R 2F R 2F L 6F R 4F L F"));
             } else {
                 System.out.println("**Please provide the path to a maze.txt file using the -i flag**");
             }
         } catch(ParseException e) {
             logger.error("/!\\\\ An error has occurred whilst trying to parse command line arguments /!\\\\", e);
         }

         logger.info("** End of MazeRunner");
    }
}
