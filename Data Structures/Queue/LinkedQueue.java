import java.util.NoSuchElementException;

/**
 * My implementation of a LinkedQueue.
 *
 * @author Ian Andrew McLeod
 * @version 1.0
 */
public class LinkedQueue<T> {

    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;


    /**
     * Adds the data to the back of the queue.
     *
     * @param data the data to add to the front of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null."
                    + " Please enter existing data.");
        }
        if (size == 0) {
            head = new LinkedNode<T>(data, null);
            tail = head;
        } else {
            LinkedNode<T> newNode = new LinkedNode<T>(data, null);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }


    /**
     * Removes and returns the data from the front of the queue.
     *
     * @return the data formerly located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("The queue is empty"
                    + ". Please add data before attempting to remove.");
        } else if (size == 1) {
            LinkedNode<T> temp = head;
            head = null;
            tail = null;
            size--;
            return temp.getData();
        } else {
            LinkedNode<T> temp = head;
            head = head.getNext();
            temp.setNext(null);
            size--;
            return temp.getData();
        }

    }

    /**
     * Returns the data from the front of the queue without removing it.
     *
     * @return the data located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("The queue is empty"
                    + ". Please add data before attempting to peek.");
        } else {
            return head.getData();
        }
    }

    /**
     * Returns the head node of the queue.
     * @return the node at the head of the queue
     */
    public LinkedNode<T> getHead() {
        return head;
    }

    /**
     * Returns the tail node of the queue.
     *
     * @return the node at the tail of the queue
     */
    public LinkedNode<T> getTail() {
        return tail;
    }

    /**
     * Returns the size of the queue.
     *
     * @return the size of the queue
     */
    public int size() {
        return size;
    }
}
