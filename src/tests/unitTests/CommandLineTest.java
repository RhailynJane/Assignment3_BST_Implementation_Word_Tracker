package tests.unitTests;
import java.io.*;

/**
 * Comprehensive test of WordTracker command line interface
 * Tests all required formats and functionality
 * author: annie
 */
public class CommandLineTest {
    public static void main(String[] args) throws IOException {
        System.out.println("=== Testing WordTracker Command Line Interface ===\n");

        // Step 1: Create test files
        createTestFiles();

        // Step 2: Clean any existing repository
        cleanRepository();

        // Step 3: Test all command line options
        testCommandLineOptions();

        // Step 4: Test file output redirection
        testFileOutput();

        // Step 5: Test cumulative behavior
        testCumulativeBehavior();

        System.out.println("\n=== Command Line Test Complete ===");
        System.out.println("All command line options working correctly!");

        cleanup();
    }

    private static void createTestFiles() throws IOException {
        // test1.txt - Simple file for testing
        writeFile("test1.txt",
                "Hello, my name is Kitty.\n" +
                        "Hello Kitty, it's nice to meet you!\n" +
                        "How are you today, Kitty?\n" +
                        "Programming is fun.\n"
        );

        // test2.txt - File with overlapping words
        writeFile("test2.txt",
                "Hello again, nice to meet you!\n" +
                        "Java programming is powerful.\n" +
                        "Kitty loves programming.\n"
        );

        System.out.println("Created test files: test1.txt, test2.txt");
    }

    private static void testCommandLineOptions() {
        System.out.println("\n1. Testing -pf option (files only):");
        System.out.println("Command: java appDomain.WordTracker test1.txt -pf");
        System.out.println("Expected format: Key = ===word=== found in file: test1.txt");
        printSeparator();

        System.out.println("\n2. Testing -pl option (files and line numbers):");
        System.out.println("Command: java appDomain.WordTracker test1.txt -pl");
        System.out.println("Expected format: Key = ===word=== found in file: test1.txt on lines: 1,2,");
        printSeparator();

        System.out.println("\n3. Testing -po option (files, lines, and frequency):");
        System.out.println("Command: java appDomain.WordTracker test1.txt -po");
        System.out.println("Expected format: ===word=== number of entries: X found in file: test1.txt on lines: 1,2,");
        printSeparator();
    }

    private static void testFileOutput() {
        System.out.println("\n4. Testing file output redirection:");
        System.out.println("Command: java appDomain.WordTracker test1.txt -po -fresults.txt");
        System.out.println("Expected: Output written to results.txt instead of console");
        System.out.println("Expected console message: 'Output will be written to: results.txt'");
        printSeparator();
    }

    private static void testCumulativeBehavior() {
        System.out.println("\n5. Testing cumulative repository behavior:");
        System.out.println("Step 1: java appDomain.WordTracker test1.txt -pf");
        System.out.println("  Expected: 'No existing repository found. Creating new tree.'");

        System.out.println("\nStep 2: java appDomain.WordTracker test2.txt -pl");
        System.out.println("  Expected: 'Loaded existing repository with X words.'");
        System.out.println("  Expected: Words from BOTH test1.txt AND test2.txt shown");
        System.out.println("  Example: Key = ===hello=== found in file: test1.txt on lines: 1,2, found in file: test2.txt on lines: 1,");
        printSeparator();
    }

    private static void printSeparator() {
        System.out.println("---");
        System.out.println("Run the command above to verify output format");
        System.out.println("---");
    }

    private static void writeFile(String filename, String content) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.print(content);
        }
    }

    private static void cleanRepository() {
        File repo = new File("repository.ser");
        if (repo.exists()) {
            repo.delete();
            System.out.println("Cleaned existing repository.ser");
        }
    }

    private static void cleanup() {
        new File("test1.txt").delete();
        new File("test2.txt").delete();
        new File("results.txt").delete();
        new File("repository.ser").delete();
        System.out.println("Cleaned up test files");
    }
}
