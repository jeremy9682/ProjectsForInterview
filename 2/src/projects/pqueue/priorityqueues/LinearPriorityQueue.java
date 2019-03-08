package projects.pqueue.priorityqueues; // ******* <---  DO NOT ERASE THIS LINE!!!! *******

/* *****************************************************************************************
 * THE FOLLOWING IMPORTS ARE HERE ONLY TO MAKE THE JAVADOC AND iterator() method signature
 * "SEE" THE RELEVANT CLASSES. SOME OF THOSE IMPORTS MIGHT *NOT* BE NEEDED BY YOUR OWN
 * IMPLEMENTATION, AND IT IS COMPLETELY FINE TO ERASE THEM. THE CHOICE IS YOURS.
 * ********************************************************************************** */
import projects.pqueue.InvalidPriorityException;
import projects.pqueue.InvalidCapacityException;
import projects.pqueue.fifoqueues.FIFOQueue;


import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * <p><tt>LinearPriorityQueue</tt> is a priority queue implemented as a linear {@link java.util.Collection}
 * of common {@link FIFOQueue}s, where the {@link FIFOQueue}s themselves hold objects
 * with the same priority (in the order they were inserted).</p>
 *
 * <p>You  <b>must</b> implement the methods in this file! To receive <b>any credit</b> for the unit tests related to this class, your implementation <b>must</b>
 * use <b>whichever</b> linear {@link java.util.Collection} you want (e.g {@link ArrayList}, {@link java.util.LinkedList},
 * {@link java.util.Queue}), or even the various {@link projects.pqueue.lists.List} and {@link FIFOQueue} implementations that we
 * provide for you. It is also possible to use <b>raw</b> arrays.</p>
 *
 * @param <T> The type held by the container.
 * 
 * @author  ---- YOUR NAME HERE ----
 *
 * @see MinHeapPriorityQueue
 *
 */
public class LinearPriorityQueue<T> implements PriorityQueue<T> { // *** <-- DO NOT CHANGE THIS LINE!!! ***

	private static final RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");

	/* *********************************************
	 * PLACE YOUR PRIVATE AND PROTECTED FIELDS HERE!
	 * YOU MIGHT ALSO WANT TO PUT PRIVATE METHODS AND/OR CLASSES HERE!
	 * THE DESIGN CHOICE IS YOURS ENTIRELY.
	 * ******************************************** */
	ArrayList<PQNode<T>> PQlist;
	boolean flag;


	class PQNode<T> implements Comparable<PQNode<T>>{
		int num;
		T content;

		public PQNode(int newNum, T newContent){
			if(newNum > 0  && newContent != null){
				num = newNum;
				content = newContent;
			}else{
				System.out.println("Input parameters invalid");
			}
		}

		public int compareTo(PQNode<T> other){
			return num - other.num;
		}

		public int getNum(){
			return  num;
		}
		public T getContent(){
			return content;
		}
		public void setNum(int newNum){
			num = newNum;
		}

	}


	/* ***********************************************************************************
	 * YOU SHOULD IMPLEMENT THE FOLLOWING METHODS. BESIDES THE INTERFACE METHODS,
	 * THOSE INCLUDE TWO CONSTRUCTORS. FOR THE PRIORITY QUEUE CLASSES, YOU
	 * WILL *NOT* NEED TO IMPLEMENT COPY CONSTRUCTORS AND EQUALS(), BUT IF YOU WOULD LIKE TO
	 * IMPLEMENT THOSE TO FACILITATE YOUR OWN TESTS AND CLIENT CODE, PLEASE FEEL FREE TO DO SO!
	 * THE CHOICE IS YOURS.
	 *
	 * YOU SHOULD NOT CHANGE *ANY* METHOD SIGNATURES! IF YOU DO, YOUR CODE WILL NOT RUN
	 * AGAINST OUR TESTS!
	 * ********************************************************************************** */

	/**
	 * Default constructor initializes the data structure with
	 * a default capacity. This default capacity will be the default capacity of the
	 * underlying data structure that you will choose to use to implement this class.
	 */
	public LinearPriorityQueue(){
		PQlist = new ArrayList<PQNode<T>>();
		flag = false;
	}

	/**
	 * Non-default constructor initializes the data structure with 
	 * the provided capacity. This provided capacity will need to be passed to the default capacity
	 * of the underlying data structure that you will choose to use to implement this class.
	 * @see #LinearPriorityQueue()
	 * @param capacity The initial capacity to endow your inner implementation with.
	 * @throws InvalidCapacityException if the capacity provided is negative.
	 */
	public LinearPriorityQueue(int capacity) throws InvalidCapacityException{


		if(capacity >= 0 ){
			PQlist = new ArrayList<PQNode<T>>(capacity);
			flag = false;
		}else{
			throw new InvalidCapacityException("Capacity should not < 0");
		}
	}

	@Override
	public void enqueue(T element, int priority) throws InvalidPriorityException{

		if(priority < 0){throw new InvalidPriorityException("Priority < 0");}

		PQNode newElement = new PQNode(priority, element);
		PQlist.add(newElement);
		PQlist.sort(null);
		flag = true;
	}

	@Override
	public T dequeue() throws EmptyPriorityQueueException {
		if(PQlist.size() == 0){throw new EmptyPriorityQueueException("size == 0");}

		flag = true;
		T returnVal = PQlist.remove(0).content;
		return returnVal;
		}

	@Override
	public T getFirst() throws EmptyPriorityQueueException {
		if(PQlist.size() == 0){throw new EmptyPriorityQueueException("SIZE == 0");}
		T returnVal = PQlist.get(0).content;
		return returnVal;

	}


	@Override
	public int size() {
		return PQlist.size();
	}

	@Override
	public boolean isEmpty() {
		return  PQlist.size() == 0;
	}

	@Override
	public void clear() {
		PQlist.clear();
		flag = true;
	}

	@Override
	public Iterator<T> iterator() {
		return  new LinearPriorityQueueIterator();
	}
	class LinearPriorityQueueIterator<T extends Comparable<T>> implements Iterator<T>{
		ArrayList<T> tempList;
		Iterator<T> tempIterator;

		public LinearPriorityQueueIterator(){
			tempIterator = tempList.iterator();
			for(PQNode each: PQlist){
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


}