package projects.phonebook.hashes;
import projects.phonebook.utils.KVPair;
import projects.phonebook.utils.PrimeGenerator;

/**
 * <p>{@link LinearProbingHashTable} is an Openly Addressed {@link HashTable} implemented with <b>Linear Probing</b> as its
 * collision resolution strategy: every key collision is resolved by moving one address over. It is
 * the most famous collision resolution strategy, praised for its simplicity, theoretical properties
 * and cache locality. It <b>does</b>, however, suffer from the &quot; clustering &quot; problem:
 * collision resolutions tend to cluster collision chains locally, making it hard for new keys to be
 * inserted without collisions. {@link QuadraticProbingHashTable} is a {@link HashTable} that
 * tries to avoid this problem, albeit sacrificing cache locality.</p>
 *
 * @author YOUR NAME HERE!
 *
 * @see HashTable
 * @see SeparateChainingHashTable
 * @see QuadraticProbingHashTable
 * @see CollisionResolver
 */
public class LinearProbingHashTable implements HashTable{

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
        while(newTable[curr] != null){// find the empty space to insert
                curr = (curr+1)%(newTable.length);
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
     *  This constructor is <b>given to you: DO NOT EDIT IT.</b>
     */
    public LinearProbingHashTable(){
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
     * Instances of {@link LinearProbingHashTable} will follow the writeup's guidelines about how to internally resize
     * the hash table when the capacity exceeds 50&#37;
     * @param key The record's key.
     * @param value The record's value.
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
        int curr = hash(key);
        while(table[curr] != null) {// find the empty space to insert
            curr = (curr+1)%(table.length);

        }
        table[curr] = new KVPair(key,value);
        count++;

    }

    @Override
    public String get(String key) {
        if(key == null){return null;}
        int curr = hash(key);
        while(table[curr] != null) {
            if(table[curr] != null && (table[curr].getKey()).equals(key)){return table[curr].getValue();}
            curr = (curr+1)%(table.length);

        }
        return null;
    }


    /**
     * <b>Return</b> and <b>remove</b> the value associated with <tt>key</tt> in the {@link HashTable}. If <tt>key</tt> does not exist in the database
     * or if <tt>key = null</tt>, this method returns <tt>null</tt>. This method is expected to run in <em>amortized constant time</em>.
     *
     * Instances of {@link LinearProbingHashTable} will follow the writeup's guidelines about how to internally resize
     * the hash table when the capacity drops below 50&#37;
     * @param key The key to search for.
     * @return The associated value if <tt>key</tt> is non-<tt>null</tt> <b>and</b> exists in our database, <tt>null</tt>
     * otherwise.
     */
    @Override
    public String remove(String key) {
        if(key == null || !containsKey(key)) return null;
        String returnVal = null;
        // Check if need to shrink
        if(((double)size())/((double) capacity()) <= 0.5){

            KVPair[] newTable = new KVPair[primeGenerator.getPreviousPrime()];
            for(KVPair curr: table) {
                if (curr != null && (curr.getKey()).equals(key)) {
                    returnVal = curr.getKey();
                }
                if (curr != null && !(curr.getKey()).equals(key)) {
                    insertHelper(curr, newTable);
                }
            }
            table = newTable;
            count--;
            return returnVal;
        }

        int curr = hash(key);
        while(table[curr] != null) {
            if(table[curr] != null && (table[curr].getKey()).equals(key)){
                //reinsert the cluster
                 returnVal = table[curr].getValue();
                table[curr] = null;
                // reinsert the cluster
                curr = (curr+1)%(table.length);
                while(table[curr]!=null){
                    KVPair temp = table[curr];
                    table[curr] = null;
                    insertHelper(temp,table);
                    curr = (curr+1)%(table.length);
                }
                count--;
                return  returnVal;
            }
            curr = (curr+1)%(table.length);

        }

        return null;

    }


    @Override
    public boolean containsKey(String key) {
     if(key == null) return false;
        int curr = hash(key);
        while(table[curr] != null) {
            if(table[curr] != null && (table[curr].getKey()).equals(key)){
                return true;
            }
            curr = (curr+1)%(table.length);
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
