package ca.mcmaster.se2aa4.mazerunner;

import static ca.mcmaster.se2aa4.mazerunner.MazeRunner.logger;

public interface StringConverter {

    default String convertToFactored(String unfactoredPath) {
        logger.trace("**** Converting unfactored path to factored path");
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

    default String removeSpaces(String stringToClean) {
        String cleanedString = stringToClean.replaceAll("\\s", ""); // Regex to replace all whitespace chars
        return cleanedString;
    }
}