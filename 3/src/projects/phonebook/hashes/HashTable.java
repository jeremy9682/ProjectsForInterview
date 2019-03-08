package projects.phonebook.hashes;

/**
 * <p>{@link HashTable} is an abstraction over hash tables which store {@link String} keys and map to 
 * {@link String} values. Implementing classes should offer <em>amortized constant</em> insertion, search and 
 * deletion. The method names that you have to implement are the namesakes of {@link java.util.Hashtable}
 * (<b>not</b> {@link java.util.HashMap}!). </p>
 *
 *  <p><b>**** DO NOT EDIT THIS INTERFACE'S DECLARATION! ****** </b></p>
 * *
 * @author <a href="mailto:jason.filippou@gmail.com">Jason Filippou</a>
 */
public interface HashTable {

    /**
     * Inserts the pair &lt;key, value&gt; into <tt>this</tt>. The container should <b>not</b> allow for <tt>null</tt>
     * keys and values, and we <b>will</b> test if you are throwing a {@link IllegalArgumentException} from your code
     * if this method is given <tt>null</tt> arguments! It is important that we establish that no <tt>null</tt> entries
     * can exist in our database because the semantics of {@link #get(String)} and {@link #remove(String)} are that they
     * return <tt>null</tt> if, and only if, their <tt>key</tt> parameter is null. This method is expected to run in <em>amortized
     * constant time</em>.
     * @param key The record's key.
     * @param value The record's value.
     * @throws IllegalArgumentException if either argument is null.
     */
    void put(String  key, String  value);

    /**
     * Get the value associated with <tt>key</tt> in the {@link HashTable}. If <tt>key</tt> does not exist in the database
     * or if <tt>key = null</tt>, this method returns <tt>null</tt>. This method is expected to run in <em>amortized constant time</em>.
     * @param key The key to search for.
     * @return The associated value if <tt>key</tt> is non-<tt>null</tt> <b>and</b> exists in our database, <tt>null</tt>
     * otherwise.
     */
    String get(String  key);

    /**
     * <b>Return</b> and <b>remove</b> the value associated with <tt>key</tt> in the {@link HashTable}. If <tt>key</tt> does not exist in the database
     * or if <tt>key = null</tt>, this method returns <tt>null</tt>. This method is expected to run in <em>amortized constant time</em>.
     * @param key The key to search for.
     * @return The associated value if <tt>key</tt> is non-<tt>null</tt> <b>and</b> exists in our database, <tt>null</tt>
     * otherwise.
     */
    String  remove(String  key);

    /**
     * Queries the {@link HashTable} about the existence of the key <tt>key</tt> in its internal storage. This method is expected to run in <em>amortized constant time</em>.
     * @param key The key to search for.
     * @return <tt>true</tt> if <tt>key</tt> is the key of some record in our hash table, <tt>false</tt> otherwise.
     */
    boolean containsKey(String  key);

    /**
     * Queries the {@link HashTable} about the existence of the value <tt>value</tt> in its internal storage. This method is expected to run in <em>linear time</em> (i.e
     * containsValue() is expected to be an <b>inneficient</b> operation. This is to be expected, since this {@link HashTable} hashes \
     * keys, not values.
     * @param value The value to search for.
     * @return <tt>true</tt> if <tt>key</tt> is the key of some record in our hash table, <tt>false</tt> otherwise.
     */
    boolean containsValue(String  value);

    /**
     * Returns the number of records in this {@link HashTable}. Please note that this is <b>not</b> the same as returning the hash table's <b>capacity</b>
     * in Open Addressing collision resolution schemes (like Linear Probing)!
     * @return The number of records stored in <tt>this</tt>.
     */
    int size();

    /**
     * Returns the <b>capacity</b> of this {@link HashTable}. In Separate Chaining, this is the total number of cells from which the lists begin. In Open Addressing
     * methods, this method returns the size of the underlying array. As a consequence, in {@link SeparateChainingHashTable}, it's possible for {@link #size()} to
     * return a value larger than this method. On the other hand, for Open Addressing methods like {@link LinearProbingHashTable} and {@link QuadraticProbingHashTable},
     * the value returned by this method is an <b>upper bound</b> on the value returned by {@link #size()}.
     * @return the number of cells in the table.
     */
    int capacity();
}
