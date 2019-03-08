package projects.pqueue.priorityqueues; // ******* <---  DO NOT ERASE THIS LINE!!!! *******


/* *****************************************************************************************
 * THE FOLLOWING IMPORTS WILL BE NEEDED BY YOUR CODE, BECAUSE WE REQUIRE THAT YOU USE
 * ANY ONE OF YOUR EXISTING MINHEAP IMPLEMENTATIONS TO IMPLEMENT THIS CLASS. TO ACCESS
 * YOUR MINHEAP'S METHODS YOU NEED THEIR SIGNATURES, WHICH ARE DECLARED IN THE MINHEAP
 * INTERFACE. ALSO, SINCE THE PRIORITYQUEUE INTERFACE THAT YOU EXTEND IS ITERABLE, THE IMPORT OF ITERATOR
 * IS NEEDED IN ORDER TO MAKE YOUR CODE COMPILABLE.
 ** ********************************************************************************** */

import projects.pqueue.InvalidPriorityException;
import projects.pqueue.heaps.ArrayMinHeap;
import projects.pqueue.heaps.MinHeap;
import projects.pqueue.heaps.*;


import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p><tt>MinHeapPriorityQueue</tt> is a {@link PriorityQueue} implemented using a {@link MinHeap}.</p>
 *
 * <p>You  <b>must</b> implement the methods in this file! To receive <b>any credit</b> for the unit tests related to this class, your implementation <b>must</b>
 * use <b>whichever</b> {@link MinHeap} implementation among the two that you should have implemented you choose!</p>
 *
 * @author  ---- YOUR NAME HERE ----
 *
 * @param <T> The Type held by the container.
 *
 * @see LinearPriorityQueue
 * @see MinHeap
 */
public class MinHeapPriorityQueue<T> implements PriorityQueue<T>{ // *** <-- DO NOT CHANGE THIS LINE!!! ***

	private static final RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");

	/* *********************************************
	 * PLACE YOUR PRIVATE AND PROTECTED FIELDS HERE!
	 * YOU MIGHT ALSO WANT TO PUT PRIVATE METHODS AND/OR CLASSES HERE!
	 * THE DESIGN CHOICE IS YOURS ENTIRELY.
	 * ******************************************** */

	MinHeap<PQNode<T>> PQheap;
	boolean flag;


	class PQNode<T> implements Comparable<PQNode<T>> {
		int num;
		T content;

		public PQNode(int newNum, T newContent) {
			if (newNum > 0 && newContent != null) {
				num = newNum;
				content = newContent;
			} else {
				System.out.println("Input parameters invalid");
			}
		}

		public int compareTo(PQNode<T> other) {
			return num - other.num;
		}

	}
	/* ***********************************************************************************
	 * YOU SHOULD IMPLEMENT THE FOLLOWING METHODS. BESIDES THE INTERFACE METHODS,
	 * THOSE INCLUDE A SIMPLE DEFAULT CONSTRUCTOR. FOR THE PRIORITY QUEUE CLASSES, YOU
	 * WILL *NOT* NEED TO IMPLEMENT COPY CONSTRUCTORS AND EQUALS(), BUT IF YOU WOULD LIKE TO
	 * IMPLEMENT THOSE TO FACILITATE YOUR OWN TESTS AND CLIENT CODE, PLEASE FEEL FREE TO DO SO!
	 * THE CHOICE IS YOURS.
	 *
	 * YOU SHOULD NOT CHANGE *ANY* METHOD SIGNATURES! IF YOU DO, YOUR CODE WILL NOT RUN
	 * AGAINST OUR TESTS!
	 * ********************************************************************************** */

	/**
	 * Simple default constructor.
	 */
	public MinHeapPriorityQueue(){
		this.PQheap = new ArrayMinHeap<PQNode<T>>();
		flag = false;
	}

	@Override
	public void enqueue(T element, int priority) throws InvalidPriorityException{
			PQNode<T> newElement = new PQNode<T>(priority,element);
			PQheap.insert(newElement);
			flag = true;
	}


	@Override
	public T dequeue() throws EmptyPriorityQueueException {

		T returnVal = null;
		try{
			returnVal = PQheap.deleteMin().content;
		} catch(EmptyHeapException e){
			throw new EmptyPriorityQueueException("Empty Heap");
		}
		flag = true;
		return returnVal;
	}

	@Override
	public T getFirst() throws EmptyPriorityQueueException {
		T returnVal = null;
		try{
			returnVal = PQheap.getMin().content;
		} catch (EmptyHeapException e){
			throw new EmptyPriorityQueueException("Empty heap");
		}
		return returnVal;
	}

	@Override
	public Iterator<T> iterator(){
	return new MinHeapPriorityQueueIterator();
	}
	class MinHeapPriorityQueueIterator<T extends Comparable<T>> implements Iterator<T>{
		ArrayList<T> tempList;
		Iterator<T> tempIterator;

		public MinHeapPriorityQueueIterator(){
			tempIterator = tempList.iterator();
			for(PQNode each: PQheap){
				tempList.add((T) each.content);
			}
		}
		public boolean hasNext(){
			return tempList.size() != 0;
		}
		public T next() throws ConcurrentModificationException, NoSuchElementException {
			if(flag){
				throw new ConcurrentModificationException(" ArrayMinHeap was modified ");
			}
			return tempIterator.next();
		}
	}

	@Override
	public int size() {
		return PQheap.size();
	}

	@Override
	public boolean isEmpty() {
		return PQheap.size() != 0;
	}

	@Override
	public void clear() {
	PQheap.clear();
	flag = true;

	}
	

}
