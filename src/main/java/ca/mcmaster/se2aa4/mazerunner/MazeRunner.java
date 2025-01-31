package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class MazeRunner {

    //=========== MAZERUNNER ATTRIBUTES ===========//
    public static final Logger logger = LogManager.getLogger(); // Logger object    
    protected final Maze maze; // Stores maze object for processing throughout methods

    
    //=========== MAZERUNNER CONSTRUCTOR ===========//
    public MazeRunner(String pathToMazeFile) {
        logger.trace("**** Constructing MazeRunner object");
        this.maze = new Maze(pathToMazeFile);
    }

    
    //=========== PATH FINDING METHOD ===========//
    public void findPath(PathFindingAlgorithm pfa) { // Finds path using a given path finding algorithm
        logger.info("** Computing path");

        // Assumes entry is west and exit is east, lets path finding algorithm decide if it needs to reverse to find shortest path
        String pathToExit = pfa.findPath(this.maze, this.maze.getEntryPoints()[0], this.maze.getEntryPoints()[1]);
        System.out.println("Found path: "+ pathToExit);
    }

    
    //=========== PATH VERIFICATION METHOD ===========//
    public void verifyPath(String path, PathTester pTester) { // Checks if path is valid from west to east and east to west
        logger.info("** Verifying path: {}", path);

        boolean pathResults = pTester.testPath(path, maze, this.maze.getEntryPoints()[0], this.maze.getEntryPoints()[1]);
        System.out.println("Path is valid: " + pathResults);
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
                    mazeRunner.verifyPath(userPath, new PathWalker()); // Use path walker to verify user path
                } else {
                    mazeRunner.findPath(new RightHandSearch()); // Use right hand search as path finding algorithm
                }
                
            } else {
                System.out.println("** Please provide the path to a maze.txt file using the -i flag **");
            }

        } catch(ParseException e) { logger.error("/!\\\\ An error has occurred whilst trying to parse command line arguments /!\\\\", e); }

        logger.info("** End of MazeRunner");
    }
}
