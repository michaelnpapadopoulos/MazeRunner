package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.mazerunner.path_finding.ForwardSearch;
import ca.mcmaster.se2aa4.mazerunner.path_finding.PathFindingAlgorithm;
import ca.mcmaster.se2aa4.mazerunner.path_finding.RightHandSearch;
import ca.mcmaster.se2aa4.mazerunner.path_validation.PathTester;
import ca.mcmaster.se2aa4.mazerunner.path_validation.PathWalker;
import ca.mcmaster.se2aa4.mazerunner.utilities.StringConverter;

public class MazeRunner implements StringConverter {
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
    public static class Builder {
        // Default implementations of path finding algorithm, path tester, and maze 
        // Are used if none are provided by the user
        private PathFindingAlgorithm pathFindingAlgorithm = new ForwardSearch();
        private PathTester pathTester = new PathWalker();
        private Maze maze = new Maze("examples/direct.maz.txt");

        public Builder withPathFindingAlgorithm(PathFindingAlgorithm pathFindingAlgorithm) {
            if (pathFindingAlgorithm == null) { // Validate path finding algorithm
                logger.error("PathFindingAlgorithm cannot be null");
                throw new IllegalArgumentException("PathFindingAlgorithm cannot be null");
            }
            this.pathFindingAlgorithm = pathFindingAlgorithm;
            return this;
        }

        public Builder withPathTester(PathTester pathTester) {
            if (pathTester == null) { // Validate path tester
                logger.error("PathTester cannot be null");
                throw new IllegalArgumentException("PathTester cannot be null");
            }
            this.pathTester = pathTester;
            return this;
        }

        public Builder withMaze(Maze maze) {
            if (maze == null) { // Validate maze
                logger.error("Maze cannot be null");
                throw new IllegalArgumentException("Maze cannot be null");
            }
            this.maze = maze;
            return this;
        }

        // Method to reset the builder to default values for improved usability 
        public Builder reset() {
            this.pathFindingAlgorithm = new ForwardSearch();
            this.pathTester = new PathWalker();
            this.maze = new Maze("examples/direct.maz.txt");
            return this;
        }

        public MazeRunner build() {
            return new MazeRunner(this);
        }
    }
    
    
    /**************************************************************************
     * Method to find the path through the maze using a given path finding algorithm
    **************************************************************************/
    public String findPath() {
        logger.info("** Computing path");

        String pathToExit = computePathToExit(); 
        pathToExit = convertToFactored(pathToExit); // Converts path to factored form
        System.out.println("Found path: "+ pathToExit);
        return pathToExit;
    }

    /**************************************************************************
     * Helper function for findPath() to seperate responsibilities
    **************************************************************************/
    private String computePathToExit() {
        int[] westEntry = this.maze.getEntryPoints()[0];
        int[] eastEntry = this.maze.getEntryPoints()[1];
        return this.pathFindingAlgorithm.findPath(this.maze, westEntry, eastEntry);
    }

    
    /**************************************************************************
     * Method to verify a given path through the maze using a path tester
     * 
     * @param path the path to be verified
    **************************************************************************/
    public boolean verifyPath(String path) {
        logger.info("** Verifying path: {}", path);

        String rawPath = cleanPathForVerification(path);
        int[] westEntry = this.maze.getEntryPoints()[0];
        int[] eastEntry = this.maze.getEntryPoints()[1];
        boolean pathResults = this.pathTester.testPath(rawPath, this.maze, westEntry, eastEntry);
        System.out.println("Path is valid: " + pathResults);
        return pathResults;
    }

    /**************************************************************************
     * Cleans a given path by remove spaces and unfactoring it. Helper function
     * for verifyPath() to seperate responsibilities
     * 
     * @param path the path to be verified
    **************************************************************************/    
    private String cleanPathForVerification(String path) {
        return convertToUnfactored(removeSpaces(path));
    }

    /**************************************************************************
     * Helper method to handle command line options. Used to get the value of a given
     * option from the command line arguments.
     * 
     * @param option the option to be handled
     * @param cmd the command line object
    **************************************************************************/
    private static String handleOption(String option, CommandLine cmd) {
        return cmd.getOptionValue(option);
    }

    /**************************************************************************
     * Main method for the MazeRunner class. Used to run the program.
     * 
     * @param args command line arguments
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
            cmd = parser.parse(options, args);
            String mazeFilePath = handleOption("i", cmd);
            String userPath = handleOption("p", cmd);

            if (mazeFilePath != null) {
                // Create mazeRunner object with builder pattern
                MazeRunner mazeRunner = new MazeRunner.Builder()
                    .withPathFindingAlgorithm(new RightHandSearch())
                    .withMaze(new Maze(mazeFilePath))
                    .withPathTester(new PathWalker())
                    .build();
                if (userPath != null) {
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
