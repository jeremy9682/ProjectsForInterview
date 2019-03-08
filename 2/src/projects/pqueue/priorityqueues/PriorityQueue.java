package projects.pqueue.priorityqueues;

import projects.pqueue.InvalidPriorityException;

/**<p> A <tt>PriorityQueue</tt> is an extension of a classic FIFO queue. Instead of traditional
 * FIFO processing, a <tt>PriorityQueue</tt> inserts elements with a higher priority
 * getFirst, where "higher" is typically interpreted as "lower" in the arithmetic
 * sense. For example, a priority of 1 (one) is considered "higher" than priority 2 (two),
 * and the element with that particular priority would be processed faster by the application
 * using the <tt>PriorityQueue</tt>. Elements with the <b>same</b> priority are inserted in a FIFO fashion.</p>
 *
 * <p>You should <b>not</b> edit this interface! It is given to you as a resource for your project.</p>
 *
 * @author  <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 *
 * @see LinearPriorityQueue
 * @see projects.pqueue.heaps.MinHeap
 * @see MinHeapPriorityQueue
 *
 */
public interface PriorityQueue<T> extends Iterable<T> {

	/**
	 * Enqueues the provided element taking into account its prority.
	 * @param element The element to enqueue.
	 * @param priority The priority of the element that will be enqueued.
	 * @throws InvalidPriorityException if the priority provided is less than 1.
	 */
	public void enqueue(T element, int priority) throws InvalidPriorityException;
	
	/**
	 * Returns <b>and deletes</b> the top element of the <tt>PriorityQueue</tt>.
	 * @return The element at the top of the <tt>PriorityQueue</tt>.
	 * @throws EmptyPriorityQueueException if the <tt>PriorityQueue</tt> is empty.
	 */
	public T dequeue() throws EmptyPriorityQueueException;
	
	/**
	 * Returns, <b>but does not delete</b> the top element of the <tt>PriorityQueue</tt>.
	 * @return The element at the top of the <tt>PriorityQueue</tt>.
	 * @throws EmptyPriorityQueueException If the <tt>PriorityQueue</tt> is empty.
	 */
	public T getFirst() throws EmptyPriorityQueueException;
	
	/**
	 * Returns the number of elements currently in the <tt>PriorityQueue</tt>.
	 * @return The number of elements currently in the <tt>PriorityQueue</tt>.
	 */
	public int size();
	
	/**
	 * Queries the <tt>PriorityQueue</tt> for emptiness.
	 * @return <tt>true</tt> if the <tt>PriorityQueue</tt> is empty, <tt>false</tt> otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Clears the <tt>PriorityQueue</tt> of all its elements.
	 */
	public void clear();
}
