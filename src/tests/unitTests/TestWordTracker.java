package test;

import implementations.BSTree;
import appDomain.Word;
import utilities.Iterator;

/**
 * Simple test program to verify WordTracker components work
 * author: annie
 */
public class TestWordTracker {

    public static void main(String[] args) {
        System.out.println("=== Testing WordTracker Components ===\n");

        // Test 1: Create BSTree and add words
        System.out.println("1. Testing BSTree with Word objects:");
        BSTree<Word> wordTree = new BSTree<Word>();

        // Create some test words
        Word apple = new Word("apple");
        apple.addOccurrence("test1.txt", 1);
        apple.addOccurrence("test1.txt", 3);

        Word banana = new Word("banana");
        banana.addOccurrence("test1.txt", 2);

        Word cherry = new Word("cherry");
        cherry.addOccurrence("test2.txt", 1);
        cherry.addOccurrence("test2.txt", 5);

        // Add to tree
        wordTree.add(apple);
        wordTree.add(banana);
        wordTree.add(cherry);

        System.out.println("   Added 3 words to tree");
        System.out.println("   Tree size: " + wordTree.size());
        System.out.println("   Tree height: " + wordTree.getHeight());

        // Test 2: Test search functionality
        System.out.println("\n2. Testing search functionality:");
        Word searchResult = wordTree.search(new Word("apple")).getData();
        System.out.println("   Found 'apple': " + (searchResult != null));
        if (searchResult != null) {
            System.out.println("   Apple frequency: " + searchResult.getTotalFrequency());
        }

        // Test 3: Test iterator (alphabetical order)
        System.out.println("\n3. Testing inorder iterator (alphabetical):");
        Iterator<Word> iterator = wordTree.inorderIterator();
        while (iterator.hasNext()) {
            Word word = iterator.next();
            System.out.println("   " + word.getWord() + " (freq: " + word.getTotalFrequency() + ")");
        }

        // Test 4: Test output formats
        System.out.println("\n4. Testing output formats:");
        System.out.println("   -pf format:");
        System.out.println("   " + apple.toStringFiles());

        System.out.println("\n   -pl format:");
        System.out.println("   " + apple.toStringLinesAndFiles());

        System.out.println("\n   -po format:");
        System.out.println("   " + apple.toStringComplete());

        System.out.println("\n=== Test Complete ===");
    }
}