package projects.pqueue.heaps.Test;

import org.junit.Test;
import projects.pqueue.heaps.ArrayMinHeap;
import projects.pqueue.heaps.EmptyHeapException;
import projects.pqueue.heaps.LinkedMinHeap;
import projects.pqueue.heaps.MinHeap;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
/**
Author: rayguan
**/

public class ArrayMinHeapTest {


    @Test
    public void testConstructor(){

        assertEquals(new ArrayMinHeap<Integer>(11).size(), 1);
        try {
            new ArrayMinHeap<String>().getMin();
            fail("not exception caught");
        } catch (EmptyHeapException e) {};
    }

    @Test
    public void testConstructorOrder() {

        MinHeap<Integer> test = new ArrayMinHeap<Integer>(5);
        test.insert(8);
        test.insert(10);
        assertEquals(test.toString(), "[5, 8, 10]");
        try {
            assertEquals((int)test.getMin(), 5);
        } catch (Exception e) {
            fail("nothing wrong");
        }
        test.insert(6);
        test.insert(3);
        test.insert(9);
        test.insert(4);
        assertEquals(test.toString(), "[3, 5, 4, 8, 6, 10, 9]");
        try {
            assertEquals((int)test.getMin(), 3);
        } catch (Exception e) {
            fail("nothing wrong");
        }
        MinHeap<Integer> test2 = new ArrayMinHeap<Integer>(test);

        System.out.println(test2.toString());

        assertEquals(test2.toString(), "[3, 4, 5, 6, 8, 9, 10]");



        assertTrue(test2.equals(test));
        test2.insert(1);
        try {
            assertEquals((int)test2.getMin(), 1);
        } catch (Exception e) {
            fail("nothing wrong");
        }
        assertEquals(test2.toString(), "[1, 3, 5, 4, 8, 9, 10, 6]");
        assertTrue(!test2.equals(test));
        test2.clear();
        assertEquals(test2.size(), 0);
        assertEquals(test.size(), 7);
    }



    @Test
    public void testDelete() {

        MinHeap<Integer> test = new ArrayMinHeap<Integer>(5);
        test.insert(8);
        test.insert(10);
        assertEquals(test.toString(), "[5, 8, 10]");
        test.insert(6);
        test.insert(3);
        test.insert(9);
        test.insert(4);
        assertEquals(test.toString(), "[3, 5, 4, 8, 6, 10, 9]");
        try {
            assertEquals((int) test.deleteMin(), 3);
        } catch (Exception e) { fail();}
        assertEquals(test.toString(), "[4, 5, 9, 8, 6, 10]");


        try {
            assertEquals((int) test.deleteMin(), 4);
        } catch (Exception e) { fail();}
        assertEquals(test.toString(), "[5, 6, 9, 8, 10]");

        try {
            assertEquals((int) test.deleteMin(), 5);
        } catch (Exception e) { fail();}
        assertEquals(test.toString(), "[6, 8, 9, 10]");
        try {
            assertEquals((int) test.deleteMin(), 6);
        } catch (Exception e) { fail();}
        assertEquals(test.toString(), "[8, 10, 9]");
        try {
            assertEquals((int) test.deleteMin(), 8);
        } catch (Exception e) { fail();}
        assertEquals(test.toString(), "[9, 10]");
        try {
            assertEquals((int) test.deleteMin(), 9);
        } catch (Exception e) { fail();}
        assertEquals(test.toString(), "[10]");
        try {
            assertEquals((int) test.deleteMin(), 10);
        } catch (Exception e) { fail();}
        assertEquals(test.toString(), "[]");
        try {
            assertEquals((int) test.deleteMin(), 10);
            fail();
        } catch (EmptyHeapException e) {};


    }

    @Test
    public void testEquals(){
        MinHeap<Integer> test1 = new ArrayMinHeap<Integer>(5);
        test1.insert(8);
        test1.insert(10);
        test1.insert(6);
        test1.insert(3);
        test1.insert(9);
        test1.insert(4);
        MinHeap<Integer> test2 = new LinkedMinHeap<Integer>(5);
        test2.insert(8);
        test2.insert(10);
        test2.insert(6);
        test2.insert(3);
        test2.insert(9);
        test2.insert(4);
        assertTrue(!test1.equals(test2));
        assertTrue(test1.equals(test1));
        assertTrue(!test2.equals(test1));
        MinHeap<Integer> test3 = new LinkedMinHeap<Integer>(test1);
        assertTrue(test3.equals(test2));

    }

    @Test
    public void testIterator() {
        MinHeap<Integer> test = new ArrayMinHeap<Integer>(5);
        test.insert(8);
        test.insert(10);
        test.insert(6);
        test.insert(3);
        test.insert(9);
        test.insert(4);
        Iterator<Integer> iter = test.iterator();
        try {
            assertEquals((int) iter.next(), 3);
            assertEquals((int) iter.next(), 4);
            assertEquals((int) iter.next(), 5);
        } catch (Exception e) { fail();}
        Iterator<Integer> iter2 = test.iterator();
        try {
            assertEquals((int) iter.next(), 6);
            assertEquals((int) iter2.next(), 3);
            assertEquals((int) iter2.next(), 4);
            assertEquals((int) iter2.next(), 5);
            assertEquals((int) iter.next(), 8);
            assertEquals((int) iter.next(), 9);
            assertEquals((int) iter.next(), 10);
        } catch (Exception e) { fail();}

        try {
            iter.next();
            fail();
        } catch (NoSuchElementException e) {}

        try {
            test.insert(99);
            iter2.next();
            fail();
        } catch (ConcurrentModificationException e) {}


        try {
            Iterator<Integer> iter3 = test.iterator();
            test.insert(11);
            iter3.next();
            fail();
        } catch (ConcurrentModificationException e) {}

        try {
            Iterator<Integer> iter4 = test.iterator();
            test.deleteMin();
            iter4.next();
            fail();
        } catch (EmptyHeapException e) {
            fail();
        } catch (ConcurrentModificationException e) {}

        try {
            Iterator<Integer> iter5 = test.iterator();
            test.clear();
            iter5.next();
            fail();
        } catch (ConcurrentModificationException e) {}


    }



}

