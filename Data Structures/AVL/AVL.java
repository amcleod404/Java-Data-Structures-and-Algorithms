import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * My implementation of an AVL.
 *
 * @author Ian Andrew McLeod
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {

    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     */
    public AVL() {
    }

    /**
     * Constructs a new AVL.

     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data collection is null. Please enter existing data");
        }
        for (T element: data) {
            if (element == null) {
                throw new IllegalArgumentException("At least one of the data in the collection is null."
                        + " Please enter existing data");
            }
            add(element);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null. Please enter existing data.");
        }
        root = addHelper(data, root);

    }

    /**
     * Helper method for the add method
     *
     * @param data the data to add
     * @param node the current node being accessed
     * @return the new root node to satisfy the call in add()
     */

    private AVLNode<T> addHelper(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        } else {
            if (data.compareTo(node.getData()) < 0) {
                node.setLeft(addHelper(data, node.getLeft()));
            }
            if (data.compareTo(node.getData()) > 0) {
                node.setRight(addHelper(data, node.getRight()));
            }
            resetStats(node);
            node = logic(node);
            return node;
        }
    }

    /**
     * Helper method for add and remove. Determines what kind of rotation
     * is necessary to balance the tree
     *
     * @param node the node being checked for imbalance
     * @return a node to satisfy the call in addHelper()
     */

    private AVLNode<T> logic(AVLNode<T> node) {
        AVLNode<T> returnedNode = node;
        if (node.getBalanceFactor() == 2) {
            if (node.getLeft().getBalanceFactor() == -1) {
                node.setLeft(leftRotation(node.getLeft()));
                returnedNode = rightRotation(node);
            } else {
                returnedNode = rightRotation(node);
            }
        } else if (node.getBalanceFactor() == -2) {
            if (node.getRight().getBalanceFactor() == 1) {
                node.setRight(rightRotation(node.getRight()));
                returnedNode = leftRotation(node);
            } else {
                returnedNode = leftRotation(node);
            }
        }
        return returnedNode;
    }

    /**
     * Resets the height and balance factor after adding/removing a node
     * @param node the node being accessed
     */

    private void resetStats(AVLNode<T> node) {
        if (node.getRight() != null && node.getLeft() != null) {
            node.setHeight((Math.max(node.getLeft().getHeight(), node.getRight().getHeight()) + 1));
            node.setBalanceFactor(node.getLeft().getHeight() - node.getRight().getHeight());
        } else if (node.getRight() == null && node.getLeft() != null) {
            node.setHeight((Math.max(node.getLeft().getHeight(), -1) + 1));
            node.setBalanceFactor(node.getLeft().getHeight() - (-1));
        } else if (node.getLeft() == null && node.getRight() != null) {
            node.setHeight((Math.max(node.getRight().getHeight(), -1) + 1));
            node.setBalanceFactor(-1 - node.getRight().getHeight());
        } else {
            node.setHeight(0);
            node.setBalanceFactor(0);
        }

    }

    /**
     * Performs a left rotation on a given node
     *
     * @param a the node on which the rotation acts on
     * @return a's original right child
     */

    private AVLNode<T> leftRotation(AVLNode<T> a) {
        AVLNode<T> b = a.getRight();
        a.setRight(b.getLeft());
        b.setLeft(a);
        resetStats(a);
        resetStats(b);
        return b;

    }
    /**
     * Performs a right rotation on a given node
     *
     * @param c the node on which the rotation acts on
     * @return c's original left child
     */

    private AVLNode<T> rightRotation(AVLNode<T> c) {
        AVLNode<T> b = c.getLeft();
        c.setLeft(b.getRight());
        b.setRight(c);
        resetStats(c);
        resetStats(b);
        return b;

    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data passed in is null. Choose real value.");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = removeHelper(data, root, dummy);
        size--;
        return dummy.getData();
    }

    /**
     * Helper method for the remove method
     *
     * @param data the data to remove
     * @param node the current node being accessed
     * @param dummy the node that stores the data for the node being removed
     * @return the new root node to satisfy the call in remove()
     */
    
    private AVLNode<T> removeHelper(T data, AVLNode<T> node, AVLNode<T> dummy) {
        if (node == null) {
            throw new NoSuchElementException("Data was not found in the tree. Please enter existing data.");
        }
        if (node.getData().equals(data)) {
            if (node.getLeft() == null && node.getRight() == null) {
                dummy.setData(node.getData());
                return null;
            } else if ((node.getLeft() != null && node.getRight() == null)
                    || (node.getRight() != null && node.getLeft() == null)) {
                if (node.getLeft() == null) {
                    dummy.setData(node.getData());
                    return node.getRight();
                }
                if (node.getRight() == null) {
                    dummy.setData(node.getData());
                    return node.getLeft();
                }
            } else {
                dummy.setData(node.getData());
                AVLNode<T> dummy2 = new AVLNode<>(null);
                node.setLeft(predecessor(node.getLeft(), dummy2));
                node.setData(dummy2.getData());
                resetStats(node);
                node = logic(node);
                return node;
            }

        }
        if (node.getData().compareTo(data) < 0) {
            node.setRight(removeHelper(data, node.getRight(), dummy));
        }
        if (node.getData().compareTo(data) > 0) {
            node.setLeft(removeHelper(data, node.getLeft(), dummy));
        }
        resetStats(node);
        node = logic(node);
        return node;
    }

    /**
     * Helper method for the remove method. Finds the predecessor.
     *
     * @param node the current node being accessed
     * @param dummy a node that stores the data being removed
     * @return a node to return to the setLeft or setRight call from remove()
     */
    
    private AVLNode<T> predecessor(AVLNode<T> node, AVLNode<T> dummy) {
        if (node.getRight() == null) {
            if (node.getLeft() != null) {
                dummy.setData(node.getData());
                return node.getLeft();
            }
            dummy.setData(node.getData());
            return null;
        } else {
            node.setRight(predecessor(node.getRight(), dummy));
            resetStats(node);
            node = logic(node);
            return node;
        }

    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null. Please enter existing data.");
        }
        AVLNode<T> theNode = search(data, root);
        if (theNode == null) {
            throw new NoSuchElementException("The data given is not in the tree. Please search for an existing node.");
        } else {
            return theNode.getData();
        }

    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null. Please enter existing data.");
        }
        return search(data, root) != null;

    }

    /**
     * Helper method that searches for data in a tree
     *
     * @param data the data being searched for
     * @param curr the current node being accessed
     * @return the node which contains the data
     */

    private AVLNode<T> search(T data, AVLNode<T> curr) {
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
     * Returns the height of the root of the tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        } else {
            return root.getHeight();
        }

    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Find all elements within a certain distance from the given data.
     * "Distance" means the number of edges between two nodes in the tree.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *  /
     * 10
     * elementsWithinDistance(37, 3) should return the set {12, 13, 15, 25,
     * 37, 40, 50, 75}
     * elementsWithinDistance(85, 2) should return the set {75, 80, 85}
     * elementsWithinDistance(13, 1) should return the set {12, 13, 15, 25}
     *
     * @param data     the data to begin calculating distance from
     * @param distance the maximum distance allowed
     * @return the set of all data within a certain distance from the given data
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   is the data is not in the tree
     * @throws java.lang.IllegalArgumentException if distance is negative
     */
    public Set<T> elementsWithinDistance(T data, int distance) {
        if (distance < 0) {
            throw new IllegalArgumentException("The given distance is negative. Please enter a positive distance.");
        }
        Set<T> theSet = new HashSet<>();
        elementsWithinDistancePath(data, distance, root, theSet);
        return theSet;

    }

    /**
     *
     * Adds data to the Set if the current node is within the maximum distance
     * from the target node.
     *
     * @param curNode         the current node
     * @param maximumDistance the maximum distance allowed
     * @param currentDistance the distance between the current node and the
     *                        target node
     * @param currentResult   the current set of data within the maximum
     *                        distance
     */
    private void elementsWithinDistanceBelow(AVLNode<T> curNode,
                                             int maximumDistance,
                                             int currentDistance,
                                             Set<T> currentResult) {
        if (curNode == null) {
            return;
        }
        if (currentDistance <= maximumDistance) {
            currentResult.add(curNode.getData());
        }
        if (currentDistance < maximumDistance) {
            elementsWithinDistanceBelow(curNode.getLeft(), maximumDistance, currentDistance + 1, currentResult);
            elementsWithinDistanceBelow(curNode.getRight(), maximumDistance, currentDistance + 1, currentResult);
        }
    }

    /**
     * Helper method for elementsWithinDistance()
     *
     * @param data the data in the tree where distance = 0
     * @param maxDist the maximum distance allowed
     * @param curr the current node being accessed
     * @param set the set being added to
     * @return an int representing the distance from data
     */

    private int elementsWithinDistancePath(T data, int maxDist, AVLNode<T> curr, Set<T> set) {
        int childDist;

        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null. Please enter existing data.");
        }
        if (curr == null) {
            throw new NoSuchElementException("The data you entered is not in the tree. Please enter existing data.");
        } else if (data.compareTo(curr.getData()) == 0) {
            elementsWithinDistanceBelow(curr, maxDist, 0, set);
            childDist = 0;
        } else if (data.compareTo(curr.getData()) < 0) {
            childDist =  1 + elementsWithinDistancePath(data, maxDist, curr.getLeft(), set);

        } else {
            childDist = 1 + elementsWithinDistancePath(data, maxDist, curr.getRight(), set);
        }

        if (childDist <= maxDist) {
            set.add(curr.getData());
        }

        if (childDist < maxDist) {

            if (data.compareTo(curr.getData()) < 0) {
                elementsWithinDistanceBelow(curr.getRight(), maxDist, childDist + 1, set);
            } else if (data.compareTo(curr.getData()) > 0) {
                elementsWithinDistanceBelow(curr.getLeft(), maxDist, childDist + 1, set);
            }
        }

        return childDist;

    }

    /**
     * Returns the root of the tree.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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
