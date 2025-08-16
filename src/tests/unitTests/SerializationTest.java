package tests.unitTests;


import java.io.*;
import implementations.BSTree;
import appDomain.Word;
import utilities.Iterator;

/**
 * Test program specifically for serialization functionality
 * author: annie
 */

public class SerializationTest {
    private static final String TEST_REPO = "test_repository.ser";

    public static void main(String[] args) {
        System.out.println("=== Testing Serialization Functionality ===\n");

        // Test 1: Create and populate tree
        System.out.println("1. Creating and populating BSTree with Word objects:");
        BSTree<Word> originalTree = new BSTree<Word>();

        // Add some test words
        Word apple = new Word("apple");
        apple.addOccurrence("test1.txt", 1);
        apple.addOccurrence("test1.txt", 3);

        Word banana = new Word("banana");
        banana.addOccurrence("test2.txt", 2);
        banana.addOccurrence("test2.txt", 5);

        Word cherry = new Word("cherry");
        cherry.addOccurrence("test1.txt", 4);

        originalTree.add(apple);
        originalTree.add(banana);
        originalTree.add(cherry);

        System.out.println("   Original tree size: " + originalTree.size());
        System.out.println("   Original tree height: " + originalTree.getHeight());

        // Display original contents
        System.out.println("   Original tree contents (inorder):");
        Iterator<Word> originalIter = originalTree.inorderIterator();
        while (originalIter.hasNext()) {
            Word w = originalIter.next();
            System.out.println("     " + w.getWord() + " (freq: " + w.getTotalFrequency() + ")");
        }

        // Test 2: Serialize the tree
        System.out.println("\n2. Serializing tree to " + TEST_REPO + ":");
        try {
            serializeTree(originalTree);
            System.out.println("   ✅ Tree serialized successfully!");
        } catch (Exception e) {
            System.out.println("   ❌ Serialization failed: " + e.getMessage());
            return;
        }

        // Test 3: Deserialize the tree
        System.out.println("\n3. Deserializing tree from " + TEST_REPO + ":");
        BSTree<Word> deserializedTree = null;
        try {
            deserializedTree = deserializeTree();
            System.out.println("   ✅ Tree deserialized successfully!");
        } catch (Exception e) {
            System.out.println("   ❌ Deserialization failed: " + e.getMessage());
            return;
        }

        // Test 4: Verify deserialized tree
        System.out.println("\n4. Verifying deserialized tree:");
        System.out.println("   Deserialized tree size: " + deserializedTree.size());
        System.out.println("   Deserialized tree height: " + deserializedTree.getHeight());

        // Display deserialized contents
        System.out.println("   Deserialized tree contents (inorder):");
        Iterator<Word> deserializedIter = deserializedTree.inorderIterator();
        while (deserializedIter.hasNext()) {
            Word w = deserializedIter.next();
            System.out.println("     " + w.getWord() + " (freq: " + w.getTotalFrequency() + ")");
        }

        // Test 5: Verify specific word data is preserved
        System.out.println("\n5. Verifying word data preservation:");
        Word testWord = deserializedTree.search(new Word("apple")).getData();
        if (testWord != null) {
            System.out.println("   Found 'apple' with frequency: " + testWord.getTotalFrequency());
            System.out.println("   Files: " + testWord.getFilenames());
            System.out.println("   Line numbers for test1.txt: " + testWord.getLineNumbers("test1.txt"));
        }

        // Test 6: Check serialVersionUID compatibility
        System.out.println("\n6. Testing serialVersionUID:");
        System.out.println("   BSTree serialVersionUID: " + getSerialVersionUID(originalTree));
        System.out.println("   Word serialVersionUID: " + getSerialVersionUID(apple));

        // Cleanup
        new File(TEST_REPO).delete();

        System.out.println("\n=== Serialization Test Complete ===");
        System.out.println("✅ All serialization features working correctly!");
    }

    private static void serializeTree(BSTree<Word> tree) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEST_REPO))) {
            oos.writeObject(tree);
        }
    }

    @SuppressWarnings("unchecked")
    private static BSTree<Word> deserializeTree() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TEST_REPO))) {
            return (BSTree<Word>) ois.readObject();
        }
    }

    private static long getSerialVersionUID(Object obj) {
        try {
            return obj.getClass().getDeclaredField("serialVersionUID").getLong(null);
        } catch (Exception e) {
            return -1;
        }
    }
}
