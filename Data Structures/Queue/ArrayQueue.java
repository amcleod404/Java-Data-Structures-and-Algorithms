import java.util.NoSuchElementException;

/**
 * My implementation of an ArrayQueue.
 *
 * @author Ian Andrew McLeod
 * @version 1.0
 */
public class ArrayQueue<T> {

    /*
     * The initial capacity of the ArrayQueue.
     */
    public static final int INITIAL_CAPACITY = 9;

    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        Object[] arr = new Object[INITIAL_CAPACITY];
        backingArray = (T[]) arr;
        size = 0;
        front = 0;
    }

    /**
     * Adds the data to the back of the queue.
     * @param data the data to add to the front of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null."
                    + " Please enter existing data.");
        }
        if (size == backingArray.length) {
            T[] oldarray = backingArray;
            Object[] arr = new Object[2 * size];
            backingArray = (T[]) arr;
            int temp = front;
            for (int i = 0; i < oldarray.length - front; i++) {
                backingArray[i] = oldarray[temp];
                temp++;
            }
            temp = 0;
            for (int i = oldarray.length - front; i < oldarray.length; i++) {
                backingArray[i] = oldarray[temp];
                temp++;
            }
            front = 0;
            backingArray[size] = data;
        } else if (backingArray[backingArray.length - 1] != null) {
            int back = (front + size) % backingArray.length;
            backingArray[back] = data;

        } else {
            int back = front + size;
            backingArray[back] = data;
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
        } else if (front == backingArray.length - 1) {
            int temp = front;
            T data = backingArray[temp];
            backingArray[front] = null;
            front = 0;
            size--;
            return data;
        } else {
            int temp = front;
            T data = backingArray[temp];
            backingArray[front] = null;
            front = temp + 1;
            size--;
            return data;
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
            return backingArray[front];
        }

    }

    /**
     * Returns the backing array of the queue.
     *
     * @return the backing array of the queue
     */
    public T[] getBackingArray() {
        return backingArray;
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
