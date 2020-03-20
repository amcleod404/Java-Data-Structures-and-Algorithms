import java.util.NoSuchElementException;

/**
 * My implementation of an ArrayList.
 *
 * @author Ian Andrew McLeod
 * @version 1.0
 */
public class ArrayList<T> {

    /*
     * The initial capacity of the ArrayList.
     */
    public static final int INITIAL_CAPACITY = 9;

    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     */
    public ArrayList() {
        Object[] arr = new Object[INITIAL_CAPACITY];
        backingArray = (T[]) arr;
        size = 0;
    }

    /**
     * Adds the data to the specified index.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */

    public void addAtIndex(int index, T data) {
        // Exceptions
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index you input is less than 0."
                    + " Please enter an index greater than or equal to 0.");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("The index you input is greater than the ArrayList size. "
                    + "Please enter an index less than or equal to the size of the ArrayList.");
        }
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null. Please input existing data.");
        }

        // resize case
        if (size == backingArray.length) {
            T[] oldArray = backingArray;
            Object[] arr = new Object[size * 2];
            backingArray = (T[]) arr;
            for (int i = 0; i < index; i++) {
                backingArray[i] = oldArray[i];
            }
            backingArray[index] = data;
            size++;
            for (int i = index; i < oldArray.length; i++) {
                backingArray[i + 1] = oldArray[i];
            }
            return;
        }
        // default cases
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            for (int i = size - 1; i >= index; i--) {
                backingArray[i + 1] = backingArray[i];
            }
            backingArray[index] = data;
            size++;
        }
    }

    /**
     * Adds the data to the front of the list.
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        // Exceptions
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null. Please input existing data.");
        }
        // resize case
        if (size == backingArray.length) {
            T[] oldArray = backingArray;
            Object[] arr = new Object[size * 2];
            backingArray = (T[]) arr;
            backingArray[0] = data;
            size++;
            for (int i = 0; i < oldArray.length; i++) {
                backingArray[i + 1] = oldArray[i];
            }
        // default case
        } else if (size < backingArray.length) {
            for (int i = size - 1; i >= 0; i--) {
                backingArray[i + 1] = backingArray[i];
            }
            backingArray[0] = data;
            size++;
        }
    }

    /**
     * Adds the data to the back of the list.
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        // Exceptions
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null. Please input existing data.");
        }
        //resize case
        if (size == backingArray.length) {
            T[] oldArray = backingArray;
            Object[] arr = new Object[size * 2];
            backingArray = (T[]) arr;
            for (int i = 0; i < oldArray.length; i++) {
                backingArray[i] = oldArray[i];
            }
            backingArray[oldArray.length] = data;
            size++;
        } else if (size < backingArray.length) {
            backingArray[size] = data;
            size++;
        }

    }

    /**
     * Removes and returns the data at the specified index.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        // Exceptions
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index you input is less than 0."
                    + " Please enter an index greater than or equal to 0.");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("The index you input is greater than the ArrayList size."
                    + " Please enter an index less than the size of the ArrayList.");
        }
        T oldData = backingArray[index];
        // O(1) case
        if (index == size - 1) {
            backingArray[size - 1] = null;
            size--;
        // O(n) cases
        } else {
            backingArray[index] = null;
            size--;
            for (int i = index; i < size; i++) {
                backingArray[i] = backingArray[i + 1];
                backingArray[i + 1] = null;
            }
        }
        return oldData;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty."
                    + " Please add elements to the list before attempting to remove.");
        }
        return removeAtIndex(0);

    }

    /**
     * Removes and returns the last data of the list.
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty."
                    + " Please add elements to the list before attempting to remove.");
        }
        T oldData = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return oldData;
    }

    /**
     * Returns the data at the specified index.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index you input is less than 0."
                    + " Please enter an index greater than or equal to 0.");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("The index you input is greater than the ArrayList size."
                    + " Please enter an index less than the size of the ArrayList.");
        }

        return backingArray[index];

    }

    /**
     * Returns whether or not the list is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     */
    public void clear() {
        Object[] arr = new Object[INITIAL_CAPACITY];
        backingArray = (T[]) arr;
        size = 0;
    }

    /**
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        
        return backingArray;
    }

    /**
     * @return the size of the list
     */
    public int size() {
        
        return size;
    }
}