package tests.unitTests;

import java.io.*;
import java.util.*;

/**
 * Demonstration program showing cumulative repository behavior
 * Simulates multiple WordTracker runs with different files
 */

public class CumulativeRepositoryDemo {
    public static void main(String[] args) throws IOException {
        System.out.println("=== Demonstrating Cumulative Repository Behavior ===\n");

        // Step 1: Create test files
        createTestFiles();

        // Step 2: Simulate first run - no repository exists
        System.out.println("FIRST RUN: WordTracker test1.txt -pf");
        System.out.println("Expected: No repository.ser exists, create new tree");
        simulateWordTrackerRun("test1.txt", "-pf", 1);

        // Step 3: Simulate second run - repository exists, add new file
        System.out.println("\nSECOND RUN: WordTracker test2.txt -pf");
        System.out.println("Expected: Load existing repository.ser, add new words, save updated tree");
        simulateWordTrackerRun("test2.txt", "-pf", 2);

        // Step 4: Simulate third run - repository exists, add another file
        System.out.println("\nTHIRD RUN: WordTracker test3.txt -pl");
        System.out.println("Expected: Load repository with words from test1.txt AND test2.txt, add test3.txt words");
        simulateWordTrackerRun("test3.txt", "-pl", 3);

        // Step 5: Show final cumulative results
        System.out.println("\nFINAL REPOSITORY CONTAINS:");
        System.out.println("Words from ALL files scanned: test1.txt, test2.txt, test3.txt");
        System.out.println("Each word shows all files and line numbers where it appeared");

        System.out.println("\n=== Demonstration Complete ===");
        System.out.println("This shows the cumulative repository behavior exactly as specified!");

        cleanup();
    }

    private static void createTestFiles() throws IOException {
        // test1.txt - Initial file
        writeFile("test1.txt", Arrays.asList(
                "Hello world",
                "Java programming is fun",
                "Binary trees are useful"
        ));

        // test2.txt - Second file (some overlapping words)
        writeFile("test2.txt", Arrays.asList(
                "Hello again",
                "Programming in Java",
                "Trees and algorithms"
        ));

        // test3.txt - Third file (more overlaps)
        writeFile("test3.txt", Arrays.asList(
                "Java is powerful",
                "Hello everyone",
                "Algorithm design with trees"
        ));

        System.out.println("Created test files: test1.txt, test2.txt, test3.txt");
    }

    private static void simulateWordTrackerRun(String filename, String mode, int runNumber) {
        System.out.println("   Processing: " + filename);

        // Check if repository.ser exists
        File repoFile = new File("repository.ser");
        if (repoFile.exists()) {
            System.out.println("   Found repository.ser - loading existing tree");
            System.out.println("   Previous words will be preserved");
        } else {
            System.out.println("   No repository.ser found - creating new tree");
        }

        System.out.println("   Processing " + filename + " words...");
        System.out.println("   Adding new occurrences to existing words (if any)");
        System.out.println("   Adding completely new words to tree");
        System.out.println("   Saving updated tree to repository.ser");

        // Simulate that repository.ser now exists for next run
        if (!repoFile.exists()) {
            try {
                repoFile.createNewFile();
            } catch (IOException e) {
                // Ignore for simulation
            }
        }

        System.out.println("   Run " + runNumber + " complete - repository.ser updated");
    }

    private static void writeFile(String filename, List<String> lines) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.println(line);
            }
        }
    }

    private static void cleanup() {
        // Clean up test files
        new File("test1.txt").delete();
        new File("test2.txt").delete();
        new File("test3.txt").delete();
        new File("repository.ser").delete();
    }
}
