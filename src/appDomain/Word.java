package appDomain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Word class to store word information including files and line numbers
 * where the word appears, and frequency of occurrence
 *
 * @author Assignment3 Team
 * @version 1.0
 */
public class Word implements Comparable<Word>, Serializable {

    private static final long serialVersionUID = 1L;

    private String word;
    private HashMap<String, ArrayList<Integer>> fileLocations; // filename -> list of line numbers
    private int totalFrequency;

    /**
     * Constructor to create a new Word object
     * @param word the actual word string
     */
    public Word(String word) {
        this.word = word.toLowerCase(); // Store in lowercase for consistency
        this.fileLocations = new HashMap<String, ArrayList<Integer>>();
        this.totalFrequency = 0;
    }

    /**
     * Add an occurrence of this word from a specific file and line
     * @param filename the name of the file where word was found
     * @param lineNumber the line number where word was found
     */
    public void addOccurrence(String filename, int lineNumber) {
        // Get or create the list of line numbers for this file
        ArrayList<Integer> lineNumbers = fileLocations.get(filename);
        if (lineNumbers == null) {
            lineNumbers = new ArrayList<Integer>();
            fileLocations.put(filename, lineNumbers);
        }

        // Add the line number if it's not already there
        if (!lineNumbers.contains(lineNumber)) {
            lineNumbers.add(lineNumber);
        }

        totalFrequency++;
    }

    /**
     * Get the word string
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * Get all files where this word appears
     * @return HashMap of filename -> line numbers
     */
    public HashMap<String, ArrayList<Integer>> getFileLocations() {
        return fileLocations;
    }

    /**
     * Get total frequency of this word across all files
     * @return total frequency count
     */
    public int getTotalFrequency() {
        return totalFrequency;
    }

    /**
     * Get frequency of this word in a specific file
     * @param filename the file to check
     * @return frequency in that file, or 0 if not found
     */
    public int getFrequencyInFile(String filename) {
        ArrayList<Integer> lineNumbers = fileLocations.get(filename);
        return lineNumbers != null ? lineNumbers.size() : 0;
    }

    /**
     * Get all filenames where this word appears
     * @return ArrayList of filenames
     */
    public ArrayList<String> getFilenames() {
        return new ArrayList<String>(fileLocations.keySet());
    }

    /**
     * Get line numbers for a specific file
     * @param filename the file to get line numbers for
     * @return ArrayList of line numbers, or null if file not found
     */
    public ArrayList<Integer> getLineNumbers(String filename) {
        return fileLocations.get(filename);
    }

    /**
     * Compare words alphabetically (for BST ordering)
     */
    @Override
    public int compareTo(Word other) {
        return this.word.compareTo(other.word);
    }

    /**
     * Check if two Word objects represent the same word
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Word word1 = (Word) obj;
        return word.equals(word1.word);
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }

    /**
     * String representation for -pf format (word and files)
     * Format: "Key = ===word=== found in file: filename"
     */
    public String toStringFiles() {
        StringBuilder sb = new StringBuilder();
        sb.append("Key = ===").append(word).append("===");

        boolean first = true;
        for (String filename : fileLocations.keySet()) {
            if (first) {
                sb.append(" found in file: ");
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(filename);
        }

        return sb.toString();
    }

    /**
     * String representation for -pl format (word, files, and line numbers)
     * Format: "Key = ===word=== found in file: filename on lines: 1,2, found in file: filename2 on lines: 3,4,"
     */
    public String toStringLinesAndFiles() {
        StringBuilder sb = new StringBuilder();
        sb.append("Key = ===").append(word).append("===");

        for (String filename : fileLocations.keySet()) {
            sb.append(" found in file: ").append(filename).append(" on lines: ");
            ArrayList<Integer> lines = fileLocations.get(filename);

            for (int i = 0; i < lines.size(); i++) {
                if (i > 0) sb.append(",");
                sb.append(lines.get(i));
            }
            sb.append(",");
        }

        return sb.toString();
    }

    /**
     * String representation for -po format (word, files, lines, and frequency)
     * Format: "===word=== number of entries: X found in file: filename on lines: 1,2, found in file: filename2 on lines: 3,4,"
     */
    public String toStringComplete() {
        StringBuilder sb = new StringBuilder();
        sb.append("===").append(word).append("=== number of entries: ").append(totalFrequency);

        for (String filename : fileLocations.keySet()) {
            ArrayList<Integer> lines = fileLocations.get(filename);
            sb.append(" found in file: ").append(filename).append(" on lines: ");

            for (int i = 0; i < lines.size(); i++) {
                if (i > 0) sb.append(",");
                sb.append(lines.get(i));
            }
            sb.append(",");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return word + " (frequency: " + totalFrequency + ")";
    }
}