import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedStack.
 *
 * @author Ian Andrew McLeod
 * @version 1.0
 */
public class LinkedStack<T> {

    private LinkedNode<T> head;
    private int size;

    /**
     * Adds the data to the top of the stack.
     *
     * @param data the data to add to the top of the stack
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null."
                    + " Please enter existing data.");
        }
        if (size == 0) {
            head = new LinkedNode<T>(data, null);
        } else {
            head = new LinkedNode<T>(data, head);
        }
        size++;
    }

    /**
     * Removes and returns the data from the top of the stack.
     *
     * @return the data formerly located at the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException("The stack is empty"
                    + ". Please add data before attempting to remove.");
        } else {
            LinkedNode<T> temp = head;
            head = head.getNext();
            temp.setNext(null);
            size--;
            return temp.getData();
        }
    }

    /**
     * Returns the data from the top of the stack without removing it.
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("The stack is empty"
                    + ". Please add data before attempting to peek.");
        } else {
            return head.getData();
        }
    }

    /**
     * Returns the head node of the stack.
     *
     * @return the node at the head of the stack
     */
    public LinkedNode<T> getHead() {
        return head;
    }

    /**
     * Returns the size of the stack.
     *
     * @return the size of the stack
     */
    public int size() {
        return size;
    }
}
