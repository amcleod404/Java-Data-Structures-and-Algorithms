import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;


/**
 * My implementation of a ExternalChainingHashMap.
 *
 * @author Ian Andrew McLeod
 * @version 1.0
 */
public class ExternalChainingHashMap<K, V> {

    /*
     * The initial capacity of the ExternalChainingHashMap when created with the
     * default constructor.
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * The max load factor of the ExternalChainingHashMap.
     */
    public static final double MAX_LOAD_FACTOR = 0.67;
    private ExternalChainingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new ExternalChainingHashMap.
     */
    public ExternalChainingHashMap() {
        this(INITIAL_CAPACITY);

    }

    /**
     * Constructs a new ExternalChainingHashMap.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public ExternalChainingHashMap(int initialCapacity) {
        ExternalChainingMapEntry[] arr = new ExternalChainingMapEntry[initialCapacity];
        table = (ExternalChainingMapEntry<K, V>[]) arr;
        size = 0;

    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, it replaces the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, external chaining is the resolution
     * strategy.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("The key or value you entered is null. Please add existing data");
        }
        if ((((double) (size + 1)) / ((double) table.length)) > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }
        int hash = key.hashCode();
        int compressed = Math.abs((hash % table.length));
        if (table[compressed] != null) {
            ExternalChainingMapEntry<K, V> curr = table[compressed];
            while (curr != null) {
                if (curr.getKey().equals(key)) {
                    V oldValue = curr.getValue();
                    curr.setValue(value);
                    return oldValue;
                }
                curr = curr.getNext();
            }
            ExternalChainingMapEntry<K, V> temp = new ExternalChainingMapEntry<K, V>(key, value, table[compressed]);
            table[compressed] = temp;
            size++;
            return null;
        } else {
            ExternalChainingMapEntry<K, V> newEntry = new ExternalChainingMapEntry<K, V>(key, value, null);
            table[compressed] = newEntry;
            size++;
            return null;
        }

    }

    /**
     * Removes the entry with a matching key from the map.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key you entered is null. Please attempt to remove existing data");
        }
        int hash = key.hashCode();
        int compressed = Math.abs((hash % table.length));
        if (table[compressed] != null) {
            ExternalChainingMapEntry<K, V> curr = table[compressed];
            if (curr.getKey().equals(key)) {
                table[compressed] = curr.getNext();
                size--;
                return curr.getValue();
            } else {
                while (curr != null) {
                    if (curr.getNext().getKey().equals(key)) {
                        V theValue = curr.getNext().getValue();
                        ExternalChainingMapEntry<K, V> theKey = curr.getNext();
                        curr.setNext(theKey.getNext());
                        theKey.setValue(null);
                        theKey.setKey(null);
                        size--;
                        return theValue;
                    }
                    curr = curr.getNext();
                }
                throw new NoSuchElementException("The key you entered is not in the map");
            }
        } else {
            throw new NoSuchElementException("The key you entered is not in the map");
        }

    }


    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key you entered is null. Please attempt to remove existing data");
        }
        int hash = key.hashCode();
        int compressed = Math.abs((hash % table.length));
        if (table[compressed] != null) {
            ExternalChainingMapEntry<K, V> curr = table[compressed];
            if (curr.getKey().equals(key)) {
                return curr.getValue();
            } else {
                while (curr != null) {
                    if (curr.getNext().getKey().equals(key)) {
                        return curr.getNext().getValue();
                    }
                    curr = curr.getNext();
                }
                throw new NoSuchElementException("The key you entered is not in the map");
            }

        } else {
            throw new NoSuchElementException("The key you entered is not in the map");
        }

    }


    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key you entered is null. Please attempt to remove existing data");
        }
        int hash = key.hashCode();
        int compressed = Math.abs((hash % table.length));
        if (table[compressed] != null) {
            ExternalChainingMapEntry<K, V> curr = table[compressed];
            if (curr.getKey().equals(key)) {
                return true;
            } else {
                while (curr.getNext() != null) {
                    if (curr.getNext().getKey().equals(key)) {
                        return true;
                    }
                    curr = curr.getNext();
                }
                return false;
            }

        } else {
            return false;
        }

    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        HashSet<K> theSet = new HashSet<K>();
        int count = 0;
        for (int i = 0; i < table.length; i++) {
            if (count >= size) {
                break;
            }
            if (table[i] != null) {
                ExternalChainingMapEntry<K, V> curr = table[i];
                while (curr != null) {
                    theSet.add(curr.getKey());
                    curr = curr.getNext();
                    count++;
                }
            }
        }

        return theSet;


    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> theList = new ArrayList<V>();
        int count = 0;
        for (int i = 0; i < table.length; i++) {
            if (count >= size) {
                break;
            }
            if (table[i] != null) {
                ExternalChainingMapEntry<K, V> curr = table[i];
                while (curr != null) {
                    theList.add(curr.getValue());
                    curr = curr.getNext();
                    count++;
                }
            }
        }

        return theList;

    }

    /**
     * Resizes the backing table to length.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("The entered length is less than the number of items in the hashmap."
                    + " Please enter a length larger than the size.");
        }
        ExternalChainingMapEntry[] arr = new ExternalChainingMapEntry[length];
        arr = (ExternalChainingMapEntry<K, V>[]) arr;
        int count = 0;
        for (int i = 0; i < table.length; i++) {
            if (count >= size) {
                break;
            }
            if (table[i] != null) {
                ExternalChainingMapEntry<K, V> curr = table[i];
                while (curr != null) {
                    int hash = curr.getKey().hashCode();
                    int compressed = Math.abs((hash % length));
                    if (arr[compressed] != null) {
                        ExternalChainingMapEntry<K, V> next = curr.getNext();
                        curr.setNext(arr[compressed]);
                        arr[compressed] = curr;
                        curr = next;
                        count++;
                        continue;
                    } else {
                        arr[compressed] = curr;
                        ExternalChainingMapEntry<K, V> node = curr;
                        curr = curr.getNext();
                        node.setNext(null);
                        count++;
                        continue;
                    }
                }
            }
        }
        table = arr;

    }

    /**
     * Clears the map.
     */
    public void clear() {
        ExternalChainingMapEntry[] arr = new ExternalChainingMapEntry[INITIAL_CAPACITY];
        table = (ExternalChainingMapEntry<K, V>[]) arr;
        size = 0;
    }

    /**
     * Returns the table of the map.
     *
     * @return the table of the map
     */
    public ExternalChainingMapEntry<K, V>[] getTable() {
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * @return the size of the map
     */
    public int size() {
        return size;
    }
}
