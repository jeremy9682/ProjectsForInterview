package projects.phonebook.hashes;

import projects.phonebook.utils.KVPair;
import projects.phonebook.utils.PrimeGenerator;

/**
 * <p>@link QuadraticProbingHashTable} is an Openly Addressed {@link HashTable} which uses <b>Quadratic
 * Probing</b> as its collision resolution strategy. Quadratic Probing differs from <b>Linear</b> Probing
 * in that collisions are resolved by taking &quot; jumps &quot; on the hash table, the length of which
 * determined by an increasing polynomial factor. For example, during a key insertion which generates
 * several collisions, the first collision will be resolved by moving 1^2 = 1 positions over from
 * the originally hashed address (like Linear Probing), the second one will be resolved by moving
 * 2^2 = 4 positions over from our hashed address, the third one by moving 3^2 = 9 positions over, etc.
 * </p>
 *
 * <p>By using this collision resolution technique, {@link QuadraticProbingHashTable} aims to get rid of the
 * &quot;key clustering &quot; problem that {@link LinearProbingHashTable} suffers from. Leaving more
 * space in between memory probes allows other keys to be inserted without many collisions. The tradeoff
 * is that, in doing so, {@link QuadraticProbingHashTable} sacrifices <em>cache locality</em>.</p>
 *
 * @author YOUR NAME HERE!
 *
 * @see HashTable
 * @see SeparateChainingHashTable
 * @see LinearProbingHashTable
 * @see CollisionResolver
 */
public class QuadraticProbingHashTable implements HashTable{

    /* *******************************************************************/
    /* ***** PRIVATE FIELDS / METHODS PROVIDED TO YOU: DO NOT EDIT! ******/
    /* ****************************************************** ***********/

    private static final RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");
    private KVPair[] table;
    private PrimeGenerator primeGenerator;
    private int count = 0;
    private int hash(String key){
        return (key.hashCode() & 0x7fffffff) % table.length;
    }

    /*  YOU SHOULD ALSO IMPLEMENT THE FOLLOWING 2 METHODS ACCORDING TO THE SPECS
     * PROVIDED IN THE PROJECT WRITEUP, BUT KEEP THEM PRIVATE!  */

    private void enlarge(){

        // Set new table
        KVPair[] newTable = new KVPair[primeGenerator.getNextPrime()];

        // Insertion
        for(int i= 0;i < table.length;i++) {
            // Iterate each element and add into new table
            if(table[i] != null){
                KVPair currPair = table[i];
                insertHelper(currPair,newTable);
            }
        }
        table = newTable;
    }
    private void insertHelper(KVPair currPair, KVPair[] newTable){
        int newHash = (currPair.getKey().hashCode() & 0x7fffffff) % newTable.length;
        int curr = newHash;
        int num = 1;
        while(newTable[curr] != null){// find the empty space to insert
            curr = (curr + (num*num))%(newTable.length);
            num++;
        }
        newTable[curr] = currPair;
    }

    private void shrink(){
        // Set new table
        KVPair[] newTable = new KVPair[primeGenerator.getPreviousPrime()];

        // Insertion
        for(int i= 0;i < table.length;i++) {
            // Iterate each element and add into new table
            if(table[i] != null){
                KVPair currPair = table[i];
                insertHelper(currPair,newTable);
            }
        }
        table = newTable;
    }

    /* ******************/
    /*  PUBLIC METHODS: */
    /* ******************/

    /**
     *  Default constructor. Initializes the internal storage with a size equal to the default of {@link PrimeGenerator}.
     *  This constructor is <b>GIVEN TO YOU: DO NOT EDIT!</b>
     */
    public QuadraticProbingHashTable(){
        primeGenerator = new PrimeGenerator();
        table = new KVPair[primeGenerator.getCurrPrime()];
        count = 0;
    }

    /**
     * Inserts the pair &lt;key, value&gt; into <tt>this</tt>. The container should <b>not</b> allow for <tt>null</tt>
     * keys and values, and we <b>will</b> test if you are throwing a {@link IllegalArgumentException} from your code
     * if this method is given <tt>null</tt> arguments! It is important that we establish that no <tt>null</tt> entries
     * can exist in our database because the semantics of {@link #get(String)} and {@link #remove(String)} are that they
     * return <tt>null</tt> if, and only if, their <tt>key</tt> parameter is null. This method is expected to run in <em>amortized
     * constant time</em>.
     *
     * Instances of {@link QuadraticProbingHashTable} will follow the writeup's guidelines about how to internally resize
     * the hash table when the capacity drops below 50&#37;
     * @param key The record's key.
     * @throws IllegalArgumentException if either argument is null.
     */
    @Override
    public void put(String key, String value) {
        // Check parameter
        if(key == null || value == null){throw new IllegalArgumentException();}
        // Check if need to enlarge
        if( ((double)size())/((double) capacity()) > 0.5){
            enlarge();
        }
        KVPair temp = new KVPair(key,value);
        insertHelper(temp,table);
        count++;
    }


    @Override
    public String get(String key) {
        if(key == null){return null;}
        int newHash = hash(key);
        int curr = newHash;
        int num = 1;
        while(table[curr] != null){// find the empty space to insert
            if(table[curr] != null && (table[curr].getKey()).equals(key)){return table[curr].getValue();}
            curr = (curr + (num*num))%(table.length);
            num++;
        }
        return null;
    }


    /**
     * <b>Return</b> and <b>remove</b> the value associated with <tt>key</tt> in the {@link HashTable}. If <tt>key</tt> does not exist in the database
     * or if <tt>key = null</tt>, this method returns <tt>null</tt>. This method is expected to run in <em>amortized constant time</em>.
     *
     * Instances of {@link QuadraticProbingHashTable} will follow the writeup's guidelines about how to internally resize
     * the hash table when the capacity drops below 50&#37;
     * @param key The key to search for.
     * @return The associated value if <tt>key</tt> is non-<tt>null</tt> <b>and</b> exists in our database, <tt>null</tt>
     * otherwise.
     */
    @Override
    public String remove(String key) {
        if(key == null || !containsKey(key) ) return null;
        // Check if need to shrink
        if(((double)size())/((double) capacity()) <= 0.5){
          //  shrink();
            String returnVal = null;
            KVPair[] newTable = new KVPair[primeGenerator.getPreviousPrime()];
            for(KVPair curr: table){
                if(curr!= null && (curr.getKey()).equals(key)){returnVal = curr.getKey();}
                if(curr!=null && !(curr.getKey()).equals(key)){
                    insertHelper(curr,newTable);
                }
            }
            table = newTable;
            count--;
            return returnVal;

        }
        int curr = hash(key);
        int num = 1;
        while(table[curr] != null) {
            if(table[curr] != null && (table[curr].getKey()).equals(key)) {
                //reinsert the cluster
                String returnVal = table[curr].getValue();
                table[curr] = null;
                // reinsert the cluster
                curr =+ 1;
                num = 2;
                while(table[curr] != null){
                    KVPair temp = table[curr];
                    table[curr] = null;
                    insertHelper(temp,table);
                    curr = (curr + (num*num))%(table.length);
                    num++;
                }
                count--;
                return  returnVal;
            }
                curr = (curr + (num*num))%(table.length);
                num++;

        }

return null;
    }

    @Override
    public boolean containsKey(String key) {
        if(key == null) return false;
        int curr = hash(key);
        int num = 1;
        while(table[curr] != null){
            if(table[curr] != null && (table[curr].getKey()).equals(key)) return true;
            curr = (curr + (num*num))%(table.length);
            num++;
        }
        return false;
    }

    @Override
    public boolean containsValue(String value) {
        if(value == null)return false;
        for(int i = 0;i < table.length;i++){
            if(table[i] != null && (table[i].getValue()).equals(value)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public int capacity() {
        return table.length;
    }

}
