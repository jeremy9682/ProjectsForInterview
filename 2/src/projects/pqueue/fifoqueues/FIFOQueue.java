package projects.pqueue.fifoqueues;

/**
 * <p><tt>FIFOQueue</tt> is an {@link Iterable} interface for FIFO queues. Such queues allow for enqueueing
 * an element in the back, dequeueing it from the front, as well as querying for the size of the queue,
 * the getFirst element, whether the queue is empty, and clearing the queue. <i>"Front"</i> and <i>"back"</i>
 * imply order of processing; not memory addressing! The exact position of an element in memory is dependent
 * on the various implementations of <tt>FIFOQueue</tt>, such as {@link LinearArrayFIFOQueue} and
 * {@link CircularArrayFIFOQueue}. </p>
 *
 *
 * <p>You should <b>not</b> edit this interface! It is given to you as a resource for your project.</p>
 *
 * @author  <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 *
 * @param <T> The type contained by this <tt>FIFOQueue</tt>.
 *
 * @see LinearArrayFIFOQueue
 * @see CircularArrayFIFOQueue
 * @see LinkedFIFOQueue
 */
public interface FIFOQueue<T> extends Iterable<T> {

	/** Inserts an element in the back of the queue.
	 * 
	 * @param element The element to be inserted in the queue.
	 */
	public void enqueue(T element);
	
	/**
	 * Removes and returns the element at the front of the queue.
	 * @return The element at the front of the queue.
	 * @throws EmptyFIFOQueueException If the queue is empty.
	 */
	public T dequeue() throws EmptyFIFOQueueException;
	
	/**
	 * Returns but doesn not delete the getFirst element from the front of the queue.
	 * @return The element at the front of the queue.
	 * @throws EmptyFIFOQueueException If the queue is empty.
	 */
	public T first() throws EmptyFIFOQueueException;
	
	/**
	 * Returns the number of elements currently in the queue.
	 * @return The number of elements currently in the queue.
	 */
	public int size();
	
	/**
	 * Queries the queue for emptiness.
	 * @return true if the queue is empty.
	 */
	public boolean isEmpty();
	
	/**
	 * Clears the queue.
	 */
	public void clear();
}
