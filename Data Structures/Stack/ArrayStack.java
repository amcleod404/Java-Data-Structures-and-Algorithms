import java.util.NoSuchElementException;

/**
 * My implementation of an ArrayStack.
 *
 * @author Ian Andrew McLeod
 * @version 1.0
 */
public class ArrayStack<T> {

    /*
     * The initial capacity of the ArrayQueue.
     */
    public static final int INITIAL_CAPACITY = 9;

    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayStack.
     */
    public ArrayStack() {
        Object[] arr = new Object[INITIAL_CAPACITY];
        backingArray = (T[]) arr;
        size = 0;
    }

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
        if (size == backingArray.length) {
            T[] oldarray = backingArray;
            Object[] arr = new Object[2 * size];
            backingArray = (T[]) arr;
            for (int i = 0; i < oldarray.length; i++) {
                backingArray[i] = oldarray[i];
            }
            backingArray[size] = data;
            size++;
        } else {
            backingArray[size] = data;
            size++;
        }

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
            T data = backingArray[size - 1];
            backingArray[size - 1] = null;
            size--;
            return data;
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
            return backingArray[size - 1];
        }

    }

    /**
     * Returns the backing array of the stack.
     *
     * @return the backing array of the stack
     */
    public T[] getBackingArray() {
        return backingArray;
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
