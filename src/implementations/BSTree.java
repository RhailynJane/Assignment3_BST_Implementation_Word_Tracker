package implementations;

import utilities.BSTreeADT;
import utilities.Iterator;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E> {

    // Attributes
    private BSTreeNode<E> root;
    private int size;

    // Constructor
    public BSTree() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public BSTreeNode<E> getRoot() throws NullPointerException {
        if (root == null) {
            throw new NullPointerException("Tree is empty - no root node");
        }
        return root;
    }

    @Override
    public int getHeight() {
        return getHeight(root);
    }

    /**
     * Private recursive helper method to calculate height
     * @param node the node to calculate height from
     * @return height of the subtree rooted at node
     */
    private int getHeight(BSTreeNode<E> node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = getHeight(node.getLeft());
        int rightHeight = getHeight(node.getRight());

        return 1 + Math.max(leftHeight, rightHeight);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean contains(E entry) throws NullPointerException {
        if (entry == null) {
            throw new NullPointerException("Entry cannot be null");
        }
        return search(entry) != null;
    }

    @Override
    public BSTreeNode<E> search(E entry) throws NullPointerException {
        if (entry == null) {
            throw new NullPointerException("Entry cannot be null");
        }
        return search(root, entry);
    }

    /**
     * Private recursive helper method to search for an entry
     * @param node the current node being examined
     * @param entry the entry to search for
     * @return the node containing the entry, or null if not found
     */
    private BSTreeNode<E> search(BSTreeNode<E> node, E entry) {
        if (node == null) {
            return null; // Entry not found
        }

        int comparison = entry.compareTo(node.getData());

        if (comparison == 0) {
            return node; // Found the entry
        } else if (comparison < 0) {
            return search(node.getLeft(), entry); // Search left subtree
        } else {
            return search(node.getRight(), entry); // Search right subtree
        }
    }

    @Override
    public boolean add(E newEntry) throws NullPointerException {
        if (newEntry == null) {
            throw new NullPointerException("Entry cannot be null");
        }

        if (root == null) {
            root = new BSTreeNode<E>(newEntry);
            size++;
            return true;
        }

        return add(root, newEntry);
    }

    /**
     * Private recursive helper method to add an entry to the tree
     * @param node the current node being examined
     * @param newEntry the entry to add
     * @return true if the entry was added, false if it already exists
     */
    private boolean add(BSTreeNode<E> node, E newEntry) {
        int comparison = newEntry.compareTo(node.getData());

        if (comparison == 0) {
            return false; // Duplicate - not added
        } else if (comparison < 0) {
            // Add to left subtree
            if (node.getLeft() == null) {
                node.setLeft(new BSTreeNode<E>(newEntry));
                size++;
                return true;
            } else {
                return add(node.getLeft(), newEntry);
            }
        } else {
            // Add to right subtree
            if (node.getRight() == null) {
                node.setRight(new BSTreeNode<E>(newEntry));
                size++;
                return true;
            } else {
                return add(node.getRight(), newEntry);
            }
        }
    }

    @Override
    public BSTreeNode<E> removeMin() {
        if (root == null) {
            return null; // Tree is empty
        }

        BSTreeNode<E> minNode = findMin(root);
        root = removeMin(root);
        if (minNode != null) {
            size--;
        }
        return minNode;
    }

    /**
     * Private helper method to find the minimum node in a subtree
     * @param node the root of the subtree
     * @return the node with the minimum value
     */
    private BSTreeNode<E> findMin(BSTreeNode<E> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * Private recursive helper method to remove the minimum node
     * @param node the current node
     * @return the new root of the subtree after removal
     */
    private BSTreeNode<E> removeMin(BSTreeNode<E> node) {
        if (node.getLeft() == null) {
            return node.getRight(); // Return right subtree
        }
        node.setLeft(removeMin(node.getLeft()));
        return node;
    }

    @Override
    public BSTreeNode<E> removeMax() {
        if (root == null) {
            return null; // Tree is empty
        }

        BSTreeNode<E> maxNode = findMax(root);
        root = removeMax(root);
        if (maxNode != null) {
            size--;
        }
        return maxNode;
    }

    /**
     * Private helper method to find the maximum node in a subtree
     * @param node the root of the subtree
     * @return the node with the maximum value
     */
    private BSTreeNode<E> findMax(BSTreeNode<E> node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    /**
     * Private recursive helper method to remove the maximum node
     * @param node the current node
     * @return the new root of the subtree after removal
     */
    private BSTreeNode<E> removeMax(BSTreeNode<E> node) {
        if (node.getRight() == null) {
            return node.getLeft(); // Return left subtree
        }
        node.setRight(removeMax(node.getRight()));
        return node;
    }

    @Override
    public Iterator<E> inorderIterator() {
        ArrayList<E> elements = new ArrayList<E>();
        inOrderTraversal(root, elements);
        return new BSTreeIterator(elements);
    }

    @Override
    public Iterator<E> preorderIterator() {
        ArrayList<E> elements = new ArrayList<E>();
        preOrderTraversal(root, elements);
        return new BSTreeIterator(elements);
    }

    @Override
    public Iterator<E> postorderIterator() {
        ArrayList<E> elements = new ArrayList<E>();
        postOrderTraversal(root, elements);
        return new BSTreeIterator(elements);
    }

    // Helper methods for traversals
    private void inOrderTraversal(BSTreeNode<E> node, ArrayList<E> elements) {
        if (node != null) {
            inOrderTraversal(node.getLeft(), elements);   // Left
            elements.add(node.getData());                 // Root
            inOrderTraversal(node.getRight(), elements);  // Right
        }
    }

    private void preOrderTraversal(BSTreeNode<E> node, ArrayList<E> elements) {
        if (node != null) {
            elements.add(node.getData());                 // Root
            preOrderTraversal(node.getLeft(), elements);  // Left
            preOrderTraversal(node.getRight(), elements); // Right
        }
    }

    private void postOrderTraversal(BSTreeNode<E> node, ArrayList<E> elements) {
        if (node != null) {
            postOrderTraversal(node.getLeft(), elements);  // Left
            postOrderTraversal(node.getRight(), elements); // Right
            elements.add(node.getData());                  // Root
        }
    }

    // Inner Iterator Implementation
    private class BSTreeIterator implements Iterator<E> {
        private ArrayList<E> elements;
        private int currentIndex;

        public BSTreeIterator(ArrayList<E> elements) {
            this.elements = elements;
            this.currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < elements.size();
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in iterator");
            }
            return elements.get(currentIndex++);
        }
    }
}