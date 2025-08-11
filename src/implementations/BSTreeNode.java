package implementations;

/**
 * BSTreeNode represents a single node in a Binary Search Tree
 *
 * @param <E> The type of data stored in this node
 */
public class BSTreeNode<E> {

    // Attributes
    private E data;                    // The actual data stored in this node
    private BSTreeNode<E> left;        // Reference to left child (smaller values)
    private BSTreeNode<E> right;       // Reference to right child (larger values)

    /**
     * Constructor to create a new node with data
     * @param data the data to store in this node
     */
    public BSTreeNode(E data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    /**
     * Constructor to create a new node with data and children
     * @param data the data to store in this node
     * @param left the left child node
     * @param right the right child node
     */
    public BSTreeNode(E data, BSTreeNode<E> left, BSTreeNode<E> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    // Getter and Setter Methods

    /**
     * Gets the data stored in this node
     * @return the data stored in this node
     */
    public E getData() {
        return data;
    }

    /**
     * Sets the data stored in this node
     * @param data the new data to store
     */
    public void setData(E data) {
        this.data = data;
    }

    /**
     * Gets the left child of this node
     * @return the left child node, or null if no left child
     */
    public BSTreeNode<E> getLeft() {
        return left;
    }

    /**
     * Sets the left child of this node
     * @param left the new left child node
     */
    public void setLeft(BSTreeNode<E> left) {
        this.left = left;
    }

    /**
     * Gets the right child of this node
     * @return the right child node, or null if no right child
     */
    public BSTreeNode<E> getRight() {
        return right;
    }

    /**
     * Sets the right child of this node
     * @param right the new right child node
     */
    public void setRight(BSTreeNode<E> right) {
        this.right = right;
    }

    // Utility Methods

    /**
     * Checks if this node is a leaf (has no children)
     * @return true if this node has no children, false otherwise
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }

    /**
     * Checks if this node has a left child
     * @return true if this node has a left child, false otherwise
     */
    public boolean hasLeft() {
        return left != null;
    }

    /**
     * Checks if this node has a right child
     * @return true if this node has a right child, false otherwise
     */
    public boolean hasRight() {
        return right != null;
    }

    /**
     * Returns a string representation of this node's data
     * @return string representation of the data
     */
    @Override
    public String toString() {
        return data.toString();
    }
}