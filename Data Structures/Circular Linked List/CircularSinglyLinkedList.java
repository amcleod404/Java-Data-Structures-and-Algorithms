import java.util.NoSuchElementException;

/**
 * My implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Ian Andrew McLeod
 * @version 1.0
 */
public class CircularSinglyLinkedList<T> {

    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /**
     * Adds the data to the specified index.
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        // Exceptions
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index you input is less than zero."
                    + " Please input an index greater than or equal to 0.");
        }
        if (index > size) {
            throw new IndexOutOfBoundsException("The index you input is greater than the size."
                    + " Please input an index less than or equal to the size.");
        }
        if (data == null) {
            throw new IllegalArgumentException("The data you entered was null."
                    + " Please enter existing data.");
        }
        if (size == 0) {
            head = new CircularSinglyLinkedListNode<>(data);
            head.setNext(head);
            size++;
            return;
        }
        // O(1) addToFront
        if (index == 0) {
            addToFront(data);
        // O(1) addToBack
        } else if (index == size) {
            addToBack(data);
        // O(n) Index
        } else {
            CircularSinglyLinkedListNode<T> temp = head;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(data, temp.getNext());
            temp.setNext(newNode);
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
            throw new IllegalArgumentException("The data you input is null. Please input existing data.");
        }
        if (size == 0) {
            head = new CircularSinglyLinkedListNode<>(data);
            head.setNext(head);
            size++;
            return;
        }
        // O(1)
        CircularSinglyLinkedListNode<T> copyNode = new CircularSinglyLinkedListNode<>(head.getData(), head.getNext());
        head.setNext(copyNode);
        head.setData(data);
        size++;

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
            throw new IllegalArgumentException("The data you input is null. Please enter existing data.");
        }
        if (size == 0) {
            head = new CircularSinglyLinkedListNode<>(data);
            head.setNext(head);
            size++;
            return;
        }
        // O(1)
        CircularSinglyLinkedListNode<T> copyNode = new CircularSinglyLinkedListNode<>(head.getData(), head.getNext());
        head.setNext(copyNode);
        head.setData(data);
        head = copyNode;
        size++;

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
            throw new IndexOutOfBoundsException("The index you input is less than zero."
                    + " Please input an index greater than or equal to 0.");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("The index you input is greater than the size."
                    + " Please input an index less than or equal to the size.");
        }
        // O(1) removeAtFront
        if (index == 0) {
            return removeFromFront();
            // O(n) removeAtBack
        } else if (index == size - 1) {
            return removeFromBack();
            // O(n) Index
        } else {
            CircularSinglyLinkedListNode<T> temp = head;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }
            CircularSinglyLinkedListNode<T> removedNode = temp.getNext();
            temp.setNext(removedNode.getNext());
            size--;
            return removedNode.getData();
        }

    }

    /**
     * Removes and returns the first data of the list.
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        // Exceptions
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty. Please add elements to the list before removing.");
        }
        if (size == 1) {
            CircularSinglyLinkedListNode<T> temp = head;
            head = null;
            size--;
            return temp.getData();
        } else {
            CircularSinglyLinkedListNode<T> tempNode = head.getNext();
            CircularSinglyLinkedListNode<T> returnedNode = new CircularSinglyLinkedListNode<>(head.getData());
            head.setNext(tempNode.getNext());
            head.setData(tempNode.getData());
            size--;
            return returnedNode.getData();
        }

    }

    /**
     * Removes and returns the last data of the list.
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        // Exceptions
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty. Please add elements to the list before removing.");
        }
        if (size == 1) {
            CircularSinglyLinkedListNode<T> oldHead = head;
            head = null;
            size--;
            return oldHead.getData();
        }
        // O(n)
        CircularSinglyLinkedListNode<T> temp = head;
        for (int i = 0; i < size - 2; i++) {
            temp = temp.getNext();
        }
        CircularSinglyLinkedListNode<T> oldNode = temp.getNext();
        temp.setNext(head);
        size--;
        return oldNode.getData();
    }

    /**
     * Returns the data at the specified index.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        // Exceptions
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index you input is less than zero."
                    + " Please input an index greater than or equal to 0.");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("The index you input is greater than the size."
                    + " Please input an index less than or equal to the size.");
        }
        // O(1) getFront
        if (index == 0) {
            return head.getData();
        // O(n) getIndex
        } else {
            CircularSinglyLinkedListNode<T> temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            return temp.getData();
        }
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
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        // Exceptions
        if (data == null) {
            throw new IllegalArgumentException("The data you input is null. Please input existing data.");
        }
        CircularSinglyLinkedListNode<T> curr = head;
        CircularSinglyLinkedListNode<T> temp = null;
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (curr.getData().equals(data)) {
                temp = curr;
                count = i;
            }
            curr = curr.getNext();
        }
        if (temp == null) {
            throw new NoSuchElementException("The data you are looking for was not found."
                    + " Please look for existing data within the list.");
        } else {
            return removeAtIndex(count);
        }

    }

    /**
     * Returns an array representation of the linked list.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        Object[] arr = new Object[size];
        T[] myArr = (T[]) arr;
        CircularSinglyLinkedListNode<T> temp = head;
        for (int i = 0; i < size; i++) {
            myArr[i] = temp.getData();
            temp = temp.getNext();
        }
        return myArr;
    }

    /**
     * Returns the head node of the list.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }
}
