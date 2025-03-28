package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.mazerunner.path_finding.PathFindingAlgorithm;
import ca.mcmaster.se2aa4.mazerunner.path_finding.RightHandSearch;
import ca.mcmaster.se2aa4.mazerunner.path_validation.PathTester;
import ca.mcmaster.se2aa4.mazerunner.path_validation.PathWalker;

import org.apache.commons.cli.*;

public class MazeRunner {
    public static final Logger logger = LogManager.getLogger(); // Logger object 
    protected final PathFindingAlgorithm pathFindingAlgorithm; 
    protected final PathTester pathTester;
    protected final Maze maze;
    
    /**************************************************************************
     * Private Constructor for the MazeRunner class using the builder pattern.
     * 
     * @param builder the builder object used to create the MazeRunner object
    **************************************************************************/
    private MazeRunner(Builder builder) { // Builder pattern for the MazeRunner class
        logger.trace("**** Constructing MazeRunner object using builder pattern");
        this.pathFindingAlgorithm = builder.pathFindingAlgorithm;
        this.pathTester = builder.pathTester;
        this.maze = builder.maze;
    }

    /**************************************************************************
     * Builder class for MazeRunner
     *
     * @param pathFindingAlgorithm stores the path finding algorithm object
     * @param pathTester stores the path tester object
     * @param maze stores the maze object for processing throughout methods
    **************************************************************************/
    public static class Builder { // Builder class for the MazeRunner
        private PathFindingAlgorithm pathFindingAlgorithm;
        private PathTester pathTester;
        private Maze maze;

        public Builder withPathFindingAlgorithm(PathFindingAlgorithm pathFindingAlgorithm) {
            this.pathFindingAlgorithm = pathFindingAlgorithm;
            return this;
        }

        public Builder withPathTester(PathTester pathTester) {
            this.pathTester = pathTester;
            return this;
        }

        public Builder withMaze(Maze maze) {
            this.maze = maze;
            return this;
        }

        public MazeRunner build() {
            return new MazeRunner(this);
        }
    }
    
    /**************************************************************************
     * Method to find the path through the maze using a given path finding algorithm
    **************************************************************************/
    public String findPath() { // Finds path using a given path finding algorithm
        logger.info("** Computing path");

        // Assumes entry is west and exit is east, lets path finding algorithm decide if it needs to reverse to find shortest path
        String pathToExit = this.pathFindingAlgorithm.findPath(this.maze, this.maze.getEntryPoints()[0], this.maze.getEntryPoints()[1]);
        System.out.println("Found path: "+ pathToExit);
        return pathToExit;
    }

    
    /**************************************************************************
     * Method to verify a given path through the maze using a path tester
     * 
     * @param path the path to be verified
    **************************************************************************/
    public boolean verifyPath(String path) { // Checks if path is valid from west to east and east to west
        logger.info("** Verifying path: {}", path);

        boolean pathResults = this.pathTester.testPath(path, maze, this.maze.getEntryPoints()[0], this.maze.getEntryPoints()[1]);
        System.out.println("Path is valid: " + pathResults);
        return pathResults;
    }

    /**************************************************************************
     * Main method for the MazeRunner class. Used to run the program.
    **************************************************************************/
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
                // Create mazeRunner object with builder pattern
                String mazeFilePath = cmd.getOptionValue("i");
                MazeRunner mazeRunner = new MazeRunner.Builder()
                    .withPathFindingAlgorithm(new RightHandSearch())
                    .withMaze(new Maze(mazeFilePath))
                    .withPathTester(new PathWalker())
                    .build();

                if (cmd.hasOption("p")) {
                    String userPath = cmd.getOptionValue("p");
                    mazeRunner.verifyPath(userPath); // Use path walker to verify user path
                } else {
                    mazeRunner.findPath(); // Use right hand search as path finding algorithm
                }

            } else {
                System.out.println("** Please provide a VALID file path to a maze.txt file using the -i flag **");
            }

        } catch(ParseException e) { 
            logger.error("/!\\\\ An error has occurred whilst trying to parse command line arguments /!\\\\"); 
            System.err.println("An error has occurred whilst trying to parse command line arguments, are you sure you entered them correctly?");
        }

        logger.info("** End of MazeRunner");
    }
}
