package projects.tavl;

/* These imports bind with jUnit4 (four). Since this is the approach that you have used in 131 and 132, and we are absolutely
 * certain that submit.cs works well with it, we kindly ask that you stick with it for our projects. It's easy: just copy
 * and paste the following imports, and then you can write your tests the way that you have been used to.
 */
import org.junit.Test;

import java.util.Iterator;
import java.util.Random;
import static org.junit.Assert.*;
import java.util.Queue;
import java.util.TreeMap;
import java.util.LinkedList;

/**
 * <p><tt>StudentTests</tt> is an example class that contains some basic jUnit tests that you can write. It is <b>very important</b>
 * that you write your own tests! </p>
 * @author --- YOUR NAME HERE! -----
 * @see ThreadedAVLTree
 */
public class StudentTests {

    private ThreadedAVLTree<Integer> tree = new ThreadedAVLTree<>(); // Any other Comparable type will do.
    private static final long SEED=47; // To allow for reproducibility of your test cases.
    private static Random r = new Random(SEED); // Or don't provide a seed for full pseudo-randomness (but you lose the reproducibility)
    private static final int UPPER_BOUND = 1000; // Upper bound of integers to generate.
    private static final int NUM_INTS = 200; // Number of integers to generate in stress tests.

    // Note that if NUM_INTS > UPPER_BOUND, by pigeonhole principle, you *WILL* generate duplicate integers through calls to Random::nextInt(). For
    // example, if you want to generate ints between 1 and 10 inclusive uniformly at random and you perform this experiment 11 times, you are guaranteed
    // to repeat the insertion of an integer between 1 and 10 at least twice.


    /**
     * Tests if an empty tree behaves as it should based on the docs.
     */
    @Test
    public void testEmptyTree(){

        // assertTrue is overloaded, which means that we can call it with just one argument (a boolean condition
        // that must be met), or two: a String which should be human-readable and describes what went wrong,
        // followed by our familiar condition. This version is more verbal and gives you more information
        // about what went wrong. This is how we structure our tests on submit.cs so that you have a lot more information
        // about where your tests are failing: replicate this behavior yourselves and save some time!
        assertTrue("After creation, the tree should be empty.", tree.isEmpty());


        // assertNull(String message, Object obj) is equivalent to assertEquals(message, null, obj) or
        // assertTrue(message, obj==null). It's just more indicative of why it's used.
        assertNull("After creation, the tree's root should be null.", tree.getRoot());

        // Notice how the three-argument version of assertEquals() works: The first argument is a String,
        // which should be human-readable and describes what went wrong, the second is the exact value we expected,
        // while the third one is the value that we got! Remember: (message, expected, actual). *NOT*
        // (message, actual, expected).
        assertEquals("After creation, the tree's height should be -1.", -1, tree.height());
    }

    @Test
    public void testsimpleAdditions() {

        // Insert the first integer
        attemptInsertion(10); // Definition of this utility is below.
        assertFalse("After a single insertion, the tree should no longer be considered empty!", tree.isEmpty());
        assertEquals("After a single insertion, the tree's root should be equal to the element inserted.", new Integer(10),
                tree.getRoot());
        assertEquals("After a single insertion, the tree's height should be 0.", 0, tree.height());

        // The second integer will be the root's left child, and should *not* trigger rotations.
        attemptInsertion(5);
        assertFalse("After two insertions, the tree should still not be considered empty!", tree.isEmpty());
        assertEquals("After two insertions, the tree's root should still be equal to the element previously inserted.", new Integer(10),
                tree.getRoot());
        assertEquals("After two insertions, the tree's height should be 1.", 1, tree.height());

        // Let's insert a right child for the root. This should also *not* trigger rotations.
        attemptInsertion(15);
        assertFalse("After a third insertion that should not trigger a re-balancing, the tree should still not be considered empty!", tree.isEmpty());
        assertEquals("After a third insertion that should not trigger a re-balancing, the tree's root should still be equal to the element " +
                        "first inserted.", new Integer(10), tree.getRoot());
        assertEquals("After a third insertion that should not trigger a re-balancing, the tree's height should still be 1.", 1,
                tree.height());
    }

    /* This utility tries to insert an integer into the tree and reports any Throwables caught. */
    private void attemptInsertion(int intToInsert){
        try {
         //  System.out.println(intToInsert);
            tree.insert(intToInsert);
        }  catch (Throwable t) {
            fail("Insertion of " + intToInsert + ":" + format(t));
        }
    }

    /* A utility that receives a Throwable and returns a String with useful information mined from the thrown Throwable instance. */
    private static String format(Throwable t){
        return "Caught a " + t.getClass()+ " with message: " + t.getMessage() + ".";
    }

    /**
     * This test inserts many random integers and checks for any Throwable instances thrown.
     */

  /*
    @Test
    public void stressTestForInsertions(){
        int randInt = -1; // Just an initializer value so that the catch block can see the variable from its scope.
    */
        /*
        for(int ignored = 0; ignored < NUM_INTS; ignored++){
            System.out.println(ignored);
            attemptInsertion(r.nextInt(UPPER_BOUND));

        }
        */
        /*
        for(int ignored = 0; ignored < 20; ignored++){
           // System.out.println(ignored);
            int a = r.nextInt(UPPER_BOUND);
            attemptInsertion(a);
            if(ignored == 19) {
               // System.out.println("Done");
            }

        }

    }
*/

/*
@Test
public void testSearch(){

        for(int ignored = 0; ignored < 19; ignored++){
            System.out.println(ignored);
            int a = r.nextInt(UPPER_BOUND);
            attemptInsertion(a);
            if(ignored == 18) {
                System.out.println("Done");
            }
        }
        assertEquals(new Integer(344), tree.search(344));
    }
    */
    /*
    @Test
    public void testInorder(){
        for(int ignored = 0; ignored < 19; ignored++){
           //
            // System.out.println(ignored);
          //  int a = r.nextInt(UPPER_BOUND);
            attemptInsertion(ignored);
            if(ignored == 18) {
                System.out.println("Done");
            }
        }
        ThreadedAVLTree.TAVLNode newNode1 =  tree.getter(0);
        assertEquals(new Integer(1), tree.inorderSuc(newNode1).value);

        ThreadedAVLTree.TAVLNode newNode2 =  tree.getter(1);
        assertEquals(new Integer(2), tree.inorderSuc(newNode2).value);

        ThreadedAVLTree.TAVLNode newNode3 =  tree.getter(2);
        assertEquals(new Integer(3), tree.inorderSuc(newNode3).value);

        ThreadedAVLTree.TAVLNode newNode4 =  tree.getter(6);
        assertEquals(new Integer(7), tree.inorderSuc(newNode4).value);

        ThreadedAVLTree.TAVLNode newNode5 =  tree.getter(11);
        assertEquals(new Integer(12), tree.inorderSuc(newNode5).value);

        ThreadedAVLTree.TAVLNode newNode6 =  tree.getter(18);
        assertNull(tree.inorderSuc(newNode6));


    }
*/
    /*
    @Test
    public void testDelete() {
        for (int ignored = 0; ignored < 19; ignored++) {
            //
            // System.out.println(ignored);
            //  int a = r.nextInt(UPPER_BOUND);
            attemptInsertion(ignored);
            if (ignored == 18) {
                System.out.println("Done");
            }
        }

        for (int ignored = 0; ignored < 19; ignored++) {
            //
            // System.out.println(ignored);
            //  int a = r.nextInt(UPPER_BOUND);
           System.out.println( tree.delete(ignored));
            if (ignored == 5) {
                System.out.println("Done");
            }
        }

    }
*/
/*
  @Test
    public void testDelete2() {


        ThreadedAVLTree newTree = new ThreadedAVLTree();

        int[] num = {};

        for (int ignored = 0; ignored < 50; ignored++) {
            int a = r.nextInt(50);

            if(newTree.search(a) != null){
                System.out.println("duplicated element");
            }
            newTree.insert(a);
        }

        for(int ignored = 0; ignored < 50; ignored++){


            if(ignored == 5){
                System.out.println("DONE");
            }
            if(newTree.search(ignored) == null){
                System.out.println("Not present");
            }

            System.out.println(newTree.delete(ignored));

        }


    }
*/

/*
    @Test
    public void testDelete5(){

        ThreadedAVLTree newTree = new ThreadedAVLTree();



        for (int ignored = 0; ignored <50; ignored++) {

            int a = r.nextInt(50);
            if(newTree.search(a) != null){
                System.out.println("duplicated element");
            }
            newTree.insert(a);
        }

        for(int ignored = 50; ignored >= 0; ignored--){


            int a = r.nextInt(500);
            if(newTree.search(a) == null){
                System.out.println("Not present");
            }

                try {
                    System.out.println(newTree.delete(a));
                }catch (NullPointerException e ){
                    System.out.println("error detected with " + ignored);
                }
        }
    }
*/




    @Test
    public void testDelete4(){

        ThreadedAVLTree newTree = new ThreadedAVLTree();

        int[] num = {1,2,55,33,22,32,97,35,5,3,9,75,4,7,8,66,88,34,67,232,3234,54,98,765,3476,314,566,1213,1423424
                ,663534,123131,345,456544,27567,755,4542,9876,5335,
        3453767,45256,66,77,88,999,22,1111,2222,3333,44444,5666634,7784,65658,12231231};
            System.out.println("size" + num.length);
        for (int ignored = 0; ignored <num.length; ignored++) {
            if(newTree.search(num[ignored]) != null){
                System.out.println(num[ignored]);
                System.out.println("duplicated element");
            }
            newTree.insert(num[ignored]);
        }

        for(int ignored = 0; ignored < num.length; ignored++){

            if(newTree.search(num[ignored]) == null){
                System.out.println(num[ignored] + "Not present");

            }
            if(ignored == 35){
                System.out.println("DONE");
            }

            try {
                System.out.println(newTree.delete(num[ignored]));
            }catch (NullPointerException e ){
                System.out.println("error detected with " + ignored + " element" + num[ignored]);
            }

        }
    }

   @Test
    public void testInorder2() {
        ThreadedAVLTree newTree1 = new ThreadedAVLTree();

       // int [] num = {1,2,3,4,5,6,7,8,9,10};
     //  int[] num = {1,2,55,33,22,32,97,35,5,3,9,75,4,7,8,66,88,34,67};

       int[] num = {1,2,55,33,22,32,97,35,5,3,9,75,4,7,8,66,88,34,67,232,3234,54,98,765,3476,314,566,1213,1423424
               ,663534,123131,345,456544,27567,755,4542,9876,5335,
               3453767,45256,66,77,88,999,22,1111,2222,3333,44444,5666634,7784,65658,12231231};

        for (int ignored = 0; ignored <num.length; ignored++) {
            newTree1.insert(num[ignored]);
        }
        int i = 0;
      Iterator<ThreadedAVLTree.TAVLNode> iter = newTree1.inorderTraversal();
        while(iter.hasNext()){
            i++;

            try {
                System.out.println(iter.next());
            }catch (NullPointerException e ){
                System.out.println("error detected with " + i);
                break;
            }
        }


    }







}
