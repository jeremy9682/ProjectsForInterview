package projects.pqueue.heaps;

import java.util.Iterator;

/**
 * <p><tt>MinHeap</tt>s are <b>complete</b> binary search projects.pqueue.trees whose nodes contents are always smaller than
 * the contents of their children nodes.
 *
 * <p>You should <b>not</b> edit this interface! It is given to you as a resource for your project.</p>
 *
 * @author  <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 *
 * @param <T> The {@link Comparable} type held by the <tt>MinHeap</tt>.
 *
 * @see projects.pqueue.trees.BinarySearchTree
 * @see LinkedMinHeap
 * @see ArrayMinHeap
 */
public interface MinHeap<T extends Comparable<T>> extends Iterable<T>{

	/**
	 * Add an element in the <tt>MinHeap</tt>.
	 * @param element The element to insert to the <tt>MinHeap</tt>.
	 */
	public void insert(T element);
	
	/**
	 * Returns <b>and removes</b> the minimum element from the <tt>MinHeap</tt>.
	 * @return The minimum element of the <tt>MinHeap</tt>.
	 * @throws EmptyHeapException if the <tt>MinHeap</tt> is empty.
	 */
	public T deleteMin() throws EmptyHeapException;

	/**
	 * Returns, <b>but does not remove</b>, the minimum element of the <tt>MinHeap</tt>.
	 * @return The minimum element of the <tt>MinHeap</tt>.
	 * @throws EmptyHeapException If the <tt>MinHeap</tt> is empty.
	 */
	public T getMin() throws EmptyHeapException;

	/**
	 * Returns the number of elements in the <tt>MinHeap</tt>.
	 * @return The number of elements in the <tt>MinHeap</tt>.
	 */
	public int size();
	
	/**
	 * Queries the <tt>MinHeap</tt> for emptiness.
	 * @return <tt>true</tt> if the <tt>MinHeap</tt> is empty, <tt>false</tt> otherwise.
	 */
	public boolean isEmpty();

	/**
	 * Clears the <tt>MinHeap</tt> of all elements.
	 */
	public void clear();
	
	/**
	 * <tt>MinHeaps</tt> are endowed with fail-fast {@link Iterator}s which return the elements
	 * in <b>ascending</b> order.
	 * @return A <b>fail-fast</b> {@link Iterator} which loops through the <tt>MinHeap</tt>'s contents in ascending
	 * order.
	 */
	public Iterator<T> iterator();
}
