package ca.mcmaster.se2aa4.mazerunner.utilities;

import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

public interface StringConverter {

    /**************************************************************************
     * Converts a string of unfactored characters into a "factored" string.
     * Consecutive characters are replaced with a number indicating how many times they
     * appear in a row. For example, "aaabbbcc" becomes "3a3b2c".
     * 
     * @param unfactoredPath The unfactored path to be factored.
    **************************************************************************/
    default String convertToFactored(String unfactoredPath) {
        logger.trace("**** Converting unfactored path to factored path");

        if (unfactoredPath.isEmpty()) return ""; // Added because of JUnit test

        StringBuilder factoredPath = new StringBuilder();
        int charCount = 1;

        for (int charIndex = 1; charIndex < unfactoredPath.length(); charIndex++) {
            char currChar = unfactoredPath.charAt(charIndex);
            char prevChar = unfactoredPath.charAt(charIndex - 1);

            if (currChar == prevChar) {
                charCount++;
            } else {
                if (charCount > 1) {
                    factoredPath.append(charCount);
                }

                factoredPath.append(prevChar + " ");
                charCount = 1;
            }
        }

        // Append the last character/set of characters to the factored path
        char lastChar = unfactoredPath.charAt(unfactoredPath.length() - 1);
        if (charCount > 1) { factoredPath.append(charCount); }
        factoredPath.append(lastChar);

        return factoredPath.toString().strip();
    }

    /**************************************************************************
     * Converts a string of factored characters into an unfactored string.
     * 
     * @param factoredPath The factored path to be unfactored.
    **************************************************************************/
    default String convertToUnfactored(String factoredPath) {
        logger.trace("**** Converting factored path to unfactored path");
        StringBuilder unfactoredPath = new StringBuilder();
        
        for (int charIndex = 0; charIndex < factoredPath.length(); charIndex++) {
            char currChar = factoredPath.charAt(charIndex);

            if (Character.isDigit(currChar)) {
                StringBuilder numBuilder = new StringBuilder();

                while (Character.isDigit(factoredPath.charAt(charIndex))) { // Accounts for multi-digit numbers
                    numBuilder.append(factoredPath.charAt(charIndex));
                    charIndex++;
                }

                int numRepeats = Integer.parseInt(numBuilder.toString());
                for (int i = 0; i < numRepeats; i++) {
                    unfactoredPath.append(factoredPath.charAt(charIndex));
                }

            } else {
                if (currChar != ' ') { // Account for spaces in the string
                    unfactoredPath.append(currChar);
                }
            }
        }

        return unfactoredPath.toString();
    }


    /**************************************************************************
     * Removes all whitespace characters from a string.
     * 
     * @param stringToClean The string to be cleaned of whitespace characters.
    **************************************************************************/
    default String removeSpaces(String stringToClean) {
        String cleanedString = stringToClean.replaceAll("\\s", ""); // Regex to remove all whitespace chars and replace them with empty string
        return cleanedString;
    }
}