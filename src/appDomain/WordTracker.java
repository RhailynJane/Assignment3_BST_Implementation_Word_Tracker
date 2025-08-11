package appDomain;

import java.io.*;
import java.util.Scanner;
import implementations.BSTree;
import utilities.Iterator;

/**
 * WordTracker application that reads text files, tracks word occurrences,
 * and generates reports using a Binary Search Tree data structure.
 *
 * Command line usage: java -jar WordTracker.jar <input.txt> -pf/-pl/-po [-f<output.txt>]
 *
 * @author Assignment3 Team
 * @version 1.0
 */
public class WordTracker {

    private static final String REPOSITORY_FILE = "repository.ser";
    private static BSTree<Word> wordTree;

    public static void main(String[] args) {
        if (args.length < 2) {
            printUsage();
            return;
        }

        // Parse command line arguments
        String inputFile = args[0];
        String outputMode = args[1];
        String outputFile = null;

        // Check for optional output file
        if (args.length >= 3 && args[2].startsWith("-f")) {
            outputFile = args[2].substring(2); // Remove "-f" prefix
        }

        // Validate output mode
        if (!outputMode.equals("-pf") && !outputMode.equals("-pl") && !outputMode.equals("-po")) {
            System.err.println("Error: Invalid output mode. Use -pf, -pl, or -po");
            printUsage();
            return;
        }

        try {
            // Step 1: Load existing repository or create new tree
            loadRepository();

            // Step 2: Process the input file
            processInputFile(inputFile);

            // Step 3: Save repository
            saveRepository();

            // Step 4: Generate and display/save output
            generateOutput(outputMode, outputFile);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Load existing word tree from repository.ser, or create new tree
     */
    private static void loadRepository() {
        File repoFile = new File(REPOSITORY_FILE);

        if (repoFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(repoFile))) {
                wordTree = (BSTree<Word>) ois.readObject();
                System.out.println("Loaded existing repository with " + wordTree.size() + " words.");
            } catch (Exception e) {
                System.err.println("Warning: Could not load repository file. Creating new tree.");
                wordTree = new BSTree<Word>();
            }
        } else {
            wordTree = new BSTree<Word>();
            System.out.println("No existing repository found. Creating new tree.");
        }
    }

    /**
     * Save word tree to repository.ser
     */
    private static void saveRepository() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REPOSITORY_FILE))) {
            oos.writeObject(wordTree);
            System.out.println("Repository saved with " + wordTree.size() + " words.");
        } catch (IOException e) {
            System.err.println("Warning: Could not save repository file: " + e.getMessage());
        }
    }

    /**
     * Process input file and add words to the tree
     * @param filename the file to process
     */
    private static void processInputFile(String filename) throws IOException {
        File inputFile = new File(filename);
        if (!inputFile.exists()) {
            throw new IOException("Input file not found: " + filename);
        }

        int wordsAdded = 0;
        int lineNumber = 0;

        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine();

                // Clean and split the line into words
                String[] words = cleanAndSplitLine(line);

                for (String wordStr : words) {
                    if (wordStr.length() > 0) {
                        Word word = new Word(wordStr);

                        // Check if word already exists in tree
                        Word existingWord = findWordInTree(word);

                        if (existingWord != null) {
                            // Word exists, add this occurrence
                            existingWord.addOccurrence(filename, lineNumber);
                        } else {
                            // New word, add occurrence and insert into tree
                            word.addOccurrence(filename, lineNumber);
                            wordTree.add(word);
                            wordsAdded++;
                        }
                    }
                }
            }
        }

        System.out.println("Processed file: " + filename);
        System.out.println("Lines read: " + lineNumber);
        System.out.println("New unique words added: " + wordsAdded);
    }

    /**
     * Clean a line and split it into individual words
     * @param line the line to clean and split
     * @return array of cleaned words
     */
    private static String[] cleanAndSplitLine(String line) {
        // Remove punctuation and split on whitespace
        String cleaned = line.replaceAll("[^a-zA-Z0-9\\s]", "")
                .toLowerCase()
                .trim();

        // Split on whitespace and filter empty strings
        String[] words = cleaned.split("\\s+");

        // Filter out empty strings
        java.util.List<String> wordList = new java.util.ArrayList<String>();
        for (String word : words) {
            if (word.length() > 0) {
                wordList.add(word);
            }
        }

        return wordList.toArray(new String[0]);
    }

    /**
     * Find a word in the tree (helper method)
     * @param targetWord the word to find
     * @return the Word object if found, null otherwise
     */
    private static Word findWordInTree(Word targetWord) {
        if (wordTree.contains(targetWord)) {
            return wordTree.search(targetWord).getData();
        }
        return null;
    }

    /**
     * Generate output based on the specified mode
     * @param mode the output mode (-pf, -pl, -po)
     * @param outputFile optional output file (null for console)
     */
    private static void generateOutput(String mode, String outputFile) throws IOException {
        PrintWriter writer;

        if (outputFile != null) {
            writer = new PrintWriter(new FileWriter(outputFile));
            System.out.println("Output will be written to: " + outputFile);
        } else {
            writer = new PrintWriter(System.out);
            System.out.println("Displaying " + getDisplayFormat(mode) + " format:");
        }

        // Use inorder iterator to get alphabetically sorted words
        Iterator<Word> iterator = wordTree.inorderIterator();

        while (iterator.hasNext()) {
            Word word = iterator.next();

            switch (mode) {
                case "-pf":
                    writer.println(word.toStringFiles());
                    break;
                case "-pl":
                    writer.println(word.toStringLinesAndFiles());
                    break;
                case "-po":
                    writer.println(word.toStringComplete());
                    break;
            }
        }

        if (outputFile != null) {
            writer.close();
            System.out.println("Output written successfully.");
        } else {
            writer.flush();
            System.out.println("\nNot exporting file.");
        }
    }

    /**
     * Get display format description for a mode
     */
    private static String getDisplayFormat(String mode) {
        switch (mode) {
            case "-pf": return "-pf";
            case "-pl": return "-pl";
            case "-po": return "-po";
            default: return "unknown";
        }
    }

    /**
     * Print usage instructions
     */
    private static void printUsage() {
        System.out.println("Usage: java -jar WordTracker.jar <input.txt> -pf/-pl/-po [-f<output.txt>]");
        System.out.println();
        System.out.println("Arguments:");
        System.out.println("  <input.txt>     - Path to input text file");
        System.out.println("  -pf             - Print words and files");
        System.out.println("  -pl             - Print words, files, and line numbers");
        System.out.println("  -po             - Print words, files, lines, and frequency");
        System.out.println("  -f<output.txt>  - Optional: redirect output to file");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java -jar WordTracker.jar input.txt -pf");
        System.out.println("  java -jar WordTracker.jar input.txt -pl -fresults.txt");
    }
}