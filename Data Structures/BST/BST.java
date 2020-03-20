import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Collection;
/**
 * My implementation of a Binary Search Tree
 *
 * @author Ian Andrew McLeod
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {

    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     */
    public BST() {

    }

    /**
     * Constructs a new BST.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data collection is null."
                    + " Please enter existing data.");
        }
        for (T element: data) {
            if (element == null) {
                throw new IllegalArgumentException("At least one of the data in"
                        + " the collection is null. Please enter a complete data set.");
            }
            add(element);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null. Please enter existing data.");
        }
        root = addHelper(data, root);
        size++;

    }

    /**
     * Recursive helper method for add
     *
     * @param data the data to be added
     * @param node the current node checked in the recursion
     * @return the root node
     *
     */
    private BSTNode<T> addHelper(T data, BSTNode<T> node) {
        if (node == null) {
            return new BSTNode<>(data);
        } else {
            if (data.compareTo(node.getData()) == 0) {
                size--;
                return node;
            } else if (data.compareTo(node.getData()) < 0) {
                node.setLeft(addHelper(data, node.getLeft()));
            } else {
                node.setRight(addHelper(data, node.getRight()));
            }
            return node;
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        BSTNode<T> dummy = new BSTNode<>(null);
        root = removeHelper(data, root, dummy);
        return dummy.getData();
    }

    /**
     * Recursive helper method for remove
     *
     * @param data the data to be removed
     * @param node the current node checked in the recursion
     * @param dummy the node that contains the removed data
     * @return the root node
     * @throws IllegalArgumentException if the data is null
     * @throws NoSuchElementException if the data is not in the tree
     *
     */

    private BSTNode<T> removeHelper(T data, BSTNode<T> node, BSTNode<T> dummy) {
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null. Please enter existing data.");
        }
        if (node == null) {
            throw new NoSuchElementException("The data you want to remove is not in the tree.");
        } else {
            if (data.compareTo(node.getData()) == 0) {
                if (node.getLeft() == null && node.getRight() == null) {
                    dummy.setData(node.getData());
                    node = null;
                    size--;
                    return node;
                } else if (node.getRight() != null && node.getLeft() == null) {
                    if (node.getRight().getRight() != null || node.getRight().getLeft() != null) {
                        dummy.setData(node.getData());
                        size--;
                        return node.getRight();
                    }
                    dummy.setData(node.getData());
                    node.setData(node.getRight().getData());
                    node.setRight(null);
                    size--;
                    return node;
                } else if (node.getLeft() != null && node.getRight() == null) {
                    if (node.getLeft().getRight() != null || node.getLeft().getLeft() != null) {
                        dummy.setData(node.getData());
                        size--;
                        return node.getLeft();
                    }
                    dummy.setData(node.getData());
                    node.setData(node.getLeft().getData());
                    node.setLeft(null);
                    size--;
                    return node;
                } else {
                    BSTNode<T> successor = node.getRight();
                    BSTNode<T> parent = node;
                    while (successor.getLeft() != null) {
                        parent = successor;
                        successor = successor.getLeft();
                    }
                    dummy.setData(node.getData());
                    node.setData(successor.getData());
                    if (successor.getLeft() == null && successor.getRight() == null) {
                        if (parent.getData().equals(successor.getData())) {
                            parent.setRight(successor.getRight());
                            size--;
                            return node;
                        } else {
                            parent.setLeft(null);
                            successor.setData(null);
                            size--;
                            return node;
                        }

                    } else if (successor.getRight() != null && successor.getLeft() == null) {
                        if (parent.getData().equals(successor.getData())) {
                            parent.setRight(successor.getRight());
                            size--;
                            return node;
                        } else {
                            parent.setLeft(successor.getRight());
                            size--;
                            return node;
                        }

                    } else if (successor.getLeft() != null && successor.getRight() == null) {
                        if (parent.getData().equals(successor.getData())) {
                            parent.setRight(successor.getLeft());
                            size--;
                            return node;
                        } else {
                            parent.setLeft(successor.getLeft());
                            size--;
                            return node;
                        }
                    }
                }
            } else if (data.compareTo(node.getData()) < 0) {
                node.setLeft(removeHelper(data, node.getLeft(), dummy));
            } else {
                node.setRight(removeHelper(data, node.getRight(), dummy));
            }
            return node;
        }

    }


    /**
     * Returns the data from the tree matching the given parameter.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null. Please enter existing data");
        }
        BSTNode<T> node = search(data, root);
        if (node == null) {
            throw new NoSuchElementException("The data you entered is not in the tree. Please try a different value.");
        }
        return node.getData();

    }

    /**
     * Recursive helper method for the get and contains methods
     *
     * @param data the data being searched for
     * @param curr the current node being checked
     * @return the node that contains the data being searched for
     *
     */

    private BSTNode<T> search(T data, BSTNode<T> curr) {
        if (curr == null) {
            return null;
        } else if (data.compareTo(curr.getData()) == 0) {
            return curr;
        } else if (data.compareTo(curr.getData()) < 0) {
            return search(data, curr.getLeft());
        } else {
            return search(data, curr.getRight());
        }

    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null. Please enter existing data");
        }
        BSTNode<T> node = search(data, root);
        return node != null;

    }

    /**
     * Generates a pre-order traversal of the tree.
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> myList = new ArrayList<T>(size);
        if (size == 0) {
            return myList;
        }
        return preOrderHelper(root, myList);
    }

    /**
     * Helper method for the preorder traversal
     *
     * @param curr the current node being looked at
     * @param myList the list of data
     * @return an ArrayList in preorder
     */

    private List<T> preOrderHelper(BSTNode<T> curr, List<T> myList) {

        if (curr == null) {
            return null;
        } else {
            myList.add(curr.getData());
            preOrderHelper(curr.getLeft(), myList);
            preOrderHelper(curr.getRight(), myList);
        }
        return myList;
    }

    /**
     * Generates an in-order traversal of the tree.
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> myList = new ArrayList<T>(size);
        return inOrderHelper(root, myList);

    }

    /**
     * Helper method for the inorder traversal
     *
     * @param curr the current node being looked at
     * @param myList the list of data
     * @return an ArrayList inorder
     *
     */

    private List<T> inOrderHelper(BSTNode<T> curr, List<T> myList) {

        if (curr == null) {
            return myList;
        } else {
            inOrderHelper(curr.getLeft(), myList);
            myList.add(curr.getData());
            inOrderHelper(curr.getRight(), myList);
        }
        return myList;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> myList = new ArrayList<T>(size);
        return postOrderHelper(root, myList);
    }

    /**
     * Helper method for the postorder traversal
     * @param myList the list of data
     * @param curr the current node being looked at
     * @return an ArrayList in postOrder
     */
    private List<T> postOrderHelper(BSTNode<T> curr, List<T> myList) {

        if (curr == null) {
            return myList;
        } else {
            postOrderHelper(curr.getLeft(), myList);
            postOrderHelper(curr.getRight(), myList);
            myList.add(curr.getData());
        }
        return myList;
    }

    /**
     * Generates a level-order traversal of the tree.
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> myList = new ArrayList<T>(size);
        if (root != null) {
            Queue<BSTNode<T>> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                BSTNode<T> temp = queue.poll();
                myList.add(temp.getData());

                if (temp.getLeft() != null) {
                    queue.add(temp.getLeft());
                }

                if (temp.getRight() != null) {
                    queue.add(temp.getRight());
                }

            }
        }
        return myList;

    }


    /**
     * Returns the height of the root of the tree.
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);

    }

    /**
     * Helper method for obtaining the height of a tree
     *
     * @param curr the current node being looked at
     * @return the height of the tree
     *
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return Math.max(heightHelper(curr.getLeft()), heightHelper(curr.getRight())) + 1;
        }
    }


    /**
     * Clears the tree.
     */
    public void clear() {
        if (size == 0) {
            return;
        } else {
            root.setRight(null);
            root.setLeft(null);
            root = null;
            size = 0;
        }

    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        int num = -1;
        List<T> myList = new ArrayList<T>(size);
        maxDataHelper(num, root, myList);
        return myList;
    }

    /**
     * Helper method for getMaxDataPerLevel()
     *
     * @param curr the current node being checked
     * @param num the level of the tree that has been checked
     * @param myList the list of data
     *
     */

    private void maxDataHelper(int num, BSTNode<T> curr, List<T> myList) {
        if (curr == null) {
            return;
        }
        if (num == myList.size() - 1) {
            myList.add(curr.getData());
        }
        maxDataHelper(num + 1, curr.getRight(), myList);
        maxDataHelper(num + 1, curr.getLeft(), myList);
    }

    /**
     * Returns the root of the tree.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * @return the size of the tree
     */
    public int size() {
        return size;
    }
}
