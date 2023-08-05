import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Your implementation of a ExternalChainingHashMap.
 */
public class ExternalChainingHashMap<K, V> {

    /*
     * The initial capacity of the ExternalChainingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * The max load factor of the ExternalChainingHashMap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private ExternalChainingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new ExternalChainingHashMap with an initial capacity of INITIAL_CAPACITY.
     */
    public ExternalChainingHashMap() {
        //DO NOT MODIFY THIS METHOD!
        table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[INITIAL_CAPACITY];
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.
     *
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the table would violate the max load factor if the data was
     * added. Resize if the load factor (LF) is greater than max LF (it is
     * okay if the load factor is equal to max LF). For example, let's say
     * the table is of length 5 and the current size is 3 (LF = 0.6). For
     * this example, assume that no elements are removed in between steps.
     * If another entry is attempted to be added, before doing anything else,
     * you should check whether (3 + 1) / 5 = 0.8 is larger than the max LF.
     * It is, so you would trigger a resize before you even attempt to add
     * the data or figure out if it's a duplicate. Be careful to consider the
     * differences between integer and double division when calculating load
     * factor.
     *
     * When regrowing, resize the length of the backing table to
     * (2 * old length) + 1. You should use the resizeBackingTable method to do so.
     *
     * @param key   The key to add.
     * @param value The value to add.
     * @return null if the key was not already in the map. If it was in the
     *         map, return the old value associated with it.
     * @throws java.lang.IllegalArgumentException If key or value is null.
     */
    public V put(K key, V value) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (key == null || value == null) {
            throw new java.lang.IllegalArgumentException();
        }
        
        // calculate new loading factor, reize if exceeds max loading factor
        double lf = (float) (size + 1)/ (float) table.length;
        if (lf > MAX_LOAD_FACTOR) {
            resizeBackingTable(2*table.length + 1);
        }

        // add new data
        int idx = abs(key.hashCode() % table.length);
        if (table[idx] == null) {
            table[idx] = new ExternalChainingMapEntry<K, V>(key, value);
            size++;
            return null;
        }
        else {
            ExternalChainingMapEntry<K, V> existing = table[idx];
            while (existing != null) {
                if (existing.getKey().equals(key)) {
                    V returnVal = existing.getValue();
                    existing.setValue(value);
                    return returnVal;
                }
                else {
                    existing = existing.getNext();
                }
            }
            ExternalChainingMapEntry<K, V> toAdd = new ExternalChainingMapEntry<K, V>(key, value);
            toAdd.setNext(table[idx]);
            table[idx] = toAdd;
            size++;
            return null;
        }
        
    }

    /**
     * Removes the entry with a matching key from the map.
     *
     * @param key The key to remove.
     * @return The value associated with the key.
     * @throws java.lang.IllegalArgumentException If key is null.
     * @throws java.util.NoSuchElementException   If the key is not in the map.
     */
    public V remove(K key) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (key == null) {
            throw new java.lang.IllegalArgumentException();
        }
        
        // get the index of where the key might be at
        int idx = abs(key.hashCode() % table.length);
        if (table[idx] == null) {
            throw new java.util.NoSuchElementException();
        }
        else {
            ExternalChainingMapEntry<K, V> prev = table[idx];
            if (prev.getKey().equals(key)) {
                table[idx] = prev.getNext();
                size--;
                return prev.getValue();
            }
            else {
                ExternalChainingMapEntry<K, V> curr = prev.getNext();
                while (curr != null) {
                    if (curr.getKey().equals(key)) {
                        prev.setNext(curr.getNext());
                        size--;
                        return curr.getValue();
                    }
                    else {
                        prev = curr;
                        curr = curr.getNext();
                    }
                }
                throw new java.util.NoSuchElementException();
            }
        }

    }

    /**
     * Helper method stub for resizing the backing table to length.
     *
     * This method should be called in put() if the backing table needs to
     * be resized.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you won't need to explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new table.
     *
     * @param Length The new length of the backing table.
     */
    private void resizeBackingTable(int length) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        ExternalChainingMapEntry<K, V>[] newTable = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[length];
        for (int i = 0; i < table.length; i++) {
            ExternalChainingMapEntry<K, V> oldEntry = table[i];
            while (oldEntry != null) {
                int newIdx = abs(oldEntry.getKey().hashCode() % length);
                ExternalChainingMapEntry<K, V> newEntry = new ExternalChainingMapEntry<K, V>(oldEntry.getKey(), oldEntry.getValue());
                newEntry.setNext(newTable[newIdx]);
                newTable[newIdx] = newEntry;
                oldEntry = oldEntry.getNext();
            }
        }
        table = newTable;
    }

    private int abs(int a) {
        if (a < 0) {
            return -a;
        }
        else {
            return a;
        }
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The table of the map.
     */
    public ExternalChainingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the map.
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}