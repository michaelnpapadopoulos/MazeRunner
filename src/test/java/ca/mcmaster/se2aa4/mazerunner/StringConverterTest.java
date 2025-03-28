package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ca.mcmaster.se2aa4.mazerunner.utilities.StringConverter;

public class StringConverterTest {
    private StringConverter stringConverter; // Anonymous class to instantiate the interface

    @BeforeEach
    void setUp() {
        // Initialize the StringConverter object before each test
        stringConverter = new StringConverter() {}; // Anonymous class to instantiate the interface
    }

    @Test
    void testConvertToFactored() {
        // Test the conversion from unfactored to factored path
        String unfactoredPath = "LLLLLLFRRRRRRLLR";
        String expectedFactoredPath = "6L F 6R 2L R";
        String actualFactoredPath = stringConverter.convertToFactored(unfactoredPath);
        assertEquals(expectedFactoredPath, actualFactoredPath, "Factored path should match expected value");
    }

    @Test
    void testConvertToFactoredEmpty() {
        // Test the conversion of an empty string to factored path
        String unfactoredPath = "";
        String expectedFactoredPath = ""; // Expecting an empty string as output
        String actualFactoredPath = stringConverter.convertToFactored(unfactoredPath);
        assertEquals(expectedFactoredPath, actualFactoredPath, "Factored path should match expected value");
    }

    @Test
    void testConvertToUnfactored() {
        // Test the conversion from factored to unfactored path
        String factoredPath = "R F L 2R 6L 6R";
        String expectedUnfactoredPath = "RFLRRLLLLLLRRRRRR";
        String actualUnfactoredPath = stringConverter.convertToUnfactored(factoredPath);
        assertEquals(expectedUnfactoredPath, actualUnfactoredPath, "Unfactored path should match expected value");
    }

    @Test
    void testRemoveSpaces() {
        // Test the removal of spaces in a string
        String stringWithSpaces = " hi this is a test string  ";
        String expectedStringWithoutSpaces = "hithisisateststring";
        String actualStringWithoutSpaces = stringConverter.removeSpaces(stringWithSpaces);
        assertEquals(expectedStringWithoutSpaces, actualStringWithoutSpaces, "String should match expected value");
    }

    @Test
    void testRemoveSpacesEmpty() {
        // Test the removal of spaces in an empty string
        String emptyString = "";
        String expectedEmptyString = ""; // Expecting an empty string as output
        String actualEmptyString = stringConverter.removeSpaces(emptyString);
        assertEquals(expectedEmptyString, actualEmptyString, "String should match expected value");
    }
}
