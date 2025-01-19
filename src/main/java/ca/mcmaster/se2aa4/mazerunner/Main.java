package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    
    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        Options options = new Options();
        options.addOption("i", true, "Path to the input maze txt file");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            String mazePath = cmd.getOptionValue("i");
            
            logger.trace("**** Reading the maze from file {}", mazePath);
            BufferedReader reader = new BufferedReader(new FileReader(mazePath));
            String line;
            while ((line = reader.readLine()) != null) {
                for (int idx = 0; idx < line.length(); idx++) {
                    if (line.charAt(idx) == '#') {
                        System.out.print("WALL ");
                    } else if (line.charAt(idx) == ' ') {
                        System.out.print("PASS ");
                    }
                }
                System.out.print(System.lineSeparator());
            }
            reader.close();
        } catch(Exception e) {
            logger.error("/!\\\\ An error has occured /!\\\\");
        }

        logger.trace("**** Computing path");
        System.out.println("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }
}
