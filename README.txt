WordTracker Application - README
===================================

DESCRIPTION:
The WordTracker application processes text files and tracks word occurrences using a Binary Search Tree data structure. It maintains a persistent repository of all words encountered across multiple file processing sessions, storing file locations and line numbers for each word occurrence.

REQUIREMENTS:
- Java 8 or higher
- Minimum 256MB RAM (512MB recommended for large files)

INSTALLATION:
1. Ensure Java 8+ is installed on your system
2. Place the WordTracker.jar file in your desired directory
3. Ensure text files to be processed are accessible from this directory

USAGE:
java -jar WordTracker.jar <input.txt> -pf/-pl/-po [-f<output.txt>]

COMMAND LINE ARGUMENTS:
- <input.txt>: Path and filename of the text file to be processed
- Report Options (choose one):
  * -pf: Prints words in alphabetical order with corresponding files
  * -pl: Prints words in alphabetical order with files and line numbers
  * -po: Prints words in alphabetical order with files, line numbers, and frequencies
- Optional Output Redirection:
  * -f<output.txt>: Redirects report output to specified file

EXAMPLES:
1. Process file and display words with files:
   java -jar WordTracker.jar test1.txt -pf

2. Process file and display words with line numbers:
   java -jar WordTracker.jar test2.txt -pl

3. Process file and save detailed report to file:
   java -jar WordTracker.jar test3.txt -po -fresults.txt

FEATURES:
- Persistent storage using repository.ser file
- Automatic loading of previous word data
- Case-insensitive word processing
- Punctuation removal and text normalization
- Alphabetical sorting of output
- Support for multiple file formats (.txt files)

REPOSITORY FILE:
- The application creates/maintains a repository.ser file
- This file stores all processed words and their occurrences
- Do not delete this file if you want to maintain word history
- The file is automatically created on first run
- Subsequent runs will load and update existing data

DATA PERSISTENCE:
- Words from all processed files are accumulated
- Each execution adds new words or updates existing word occurrences
- Repository persists between application runs
- To start fresh, delete repository.ser file

OUTPUT FORMATS:
1. -pf (Print Files):
   word1                Files: file1.txt, file2.txt
   word2                Files: file1.txt

2. -pl (Print Lines):
   word1
       file1.txt        Lines: [1, 5, 10]
       file2.txt        Lines: [2, 7]

3. -po (Print Occurrences):
   word1                (Total Frequency: 5)
       file1.txt        Lines: [1, 5, 10]        Frequency: 3
       file2.txt        Lines: [2, 7]            Frequency: 2

ERROR HANDLING:
- Invalid command line arguments will display usage information
- Missing input files will generate appropriate error messages
- Corrupted repository files will be recreated automatically
- Write permission errors for output files will be reported

LIMITATIONS:
- Only processes plain text files
- Words are defined as alphanumeric sequences
- Case-insensitive processing (all stored in lowercase)
- Memory usage scales with unique word count and file size

TROUBLESHOOTING:
1. "File not found" error:
   - Check file path and spelling
   - Ensure file exists in specified location
   - Use absolute paths if relative paths fail

2. "Permission denied" error:
   - Check read permissions on input file
   - Check write permissions for output file directory
   - Ensure repository.ser can be created/modified

3. Out of memory errors:
   - Increase JVM heap size: java -Xmx512m -jar WordTracker.jar ...
   - Process smaller files
   - Delete repository.ser to reduce memory usage

4. Corrupted repository:
   - Delete repository.ser file to start fresh
   - Application will recreate the file automatically

TECHNICAL NOTES:
- Binary Search Tree provides O(log n) average case performance
- Serialization ensures data persistence across sessions
- Iterator implementations support various tree traversal methods
- Thread-safe serialization with proper version control

VERSION: 1.0
COMPATIBILITY: Java 8+
AUTHOR: SAIT CPRG 304 Assignment 3