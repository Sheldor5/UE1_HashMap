package at.sheldor5.hashmap;

import at.sheldor5.stock.Stock;

/**
 * Created by Michael Palata <a href="https://github.com/Sheldor5">@github.com/Sheldor5</a> on 11.03.2016.
 */
public interface HashMapInt {

    /**
     * Put an Object into the HashMap.
     * @param paramObject The object to put into the HashMap.
     */
    void put(final Object paramObject);

    /**
     * Get an Object by key.
     * @param paramKey The key to identify the Object.
     * @return The Object identified by the key, or null if the key can not be found.
     */
    Object get(final String paramKey);

    /**
     * Remove an Object from the HashMap.
     * @param paramKey The key to identify the Object.
     */
    void remove(final String paramKey);

    /**
     * Check if the HashMap contains an Object.
     * @param paramObject The Object this HashMap may contain.
     * @return true if the Object can be found in this HashMap, false otherwise.
     */
    boolean contains(final Object paramObject);

    /**
     * Check if an Object in the HashMap exists which can be identified by a key.
     * @param paramKey The key to identify the Object.
     * @return true if an Object can be found in this HashMap by the key, false otherwise.
     */
    boolean contains(final String paramKey);
}
