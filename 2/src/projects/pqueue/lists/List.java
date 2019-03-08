package projects.pqueue.lists;
import java.util.Iterator;

/**
 * <p>A <tt>List</tt> is a linear ADT which offers insertion, search and deletion in at most linear time.
 * Elements can only be added to the front or the back, never in between.</p>
 * 
 * <p>It is a requirement that the type <i>T</i> is {@link Comparable}, or else we will not be able to
 * implement methods such as {@link #contains(Object)} and {@link #delete(int)}. The interface is {@link Iterable}
 * interface, which means that we can use for-each loops to easily access its elements.</p>
 *
 * <p>You should <b>not</b> edit this class! It is given to you as a resource for your project.</p>
 *
 * @author  <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 *
 * @param <T> The type of object that the <tt>List</tt> will hold.
 */

public interface List<T> extends Iterable<T>{

	/**
	 * Insert an element at the start of the list.
	 * @param element The element to be inserted.
	 */
	public void pushFront(T element);
	
	/**
	 * Insert an element at the end of the list.
	 * @param element The element to be inserted.
	 */
	public void pushBack(T element);
	
	/**
	 * Get the getFirst element of the list. This method does *not* delete this element
	 * (call "delete" to delete elements).
	 * @return A copy of the element at the top of the list.
	 * @throws EmptyListException If the list is empty.
	 */
	public T getFirst() throws EmptyListException;
	
	/**
	 * Get the last element of the list. This method does <b>not</b> delete this element
	 * (call "delete" to delete elements).
	 * @return A copy of the element at the top of the list.
	 * @throws EmptyListException if the list is empty.
	 */
	public T getLast() throws EmptyListException;

	/**
	 * Get the element at the specified index of the list.
	 * @param index The index of the element to return.
	 * @return The element at the index-th position.
	 * @throws EmptyListException If the list is empty upon calling.
	 * @throws IllegalListAccessException If the index is invalid in the current list's context.
	 */
	public T get(int index) throws EmptyListException, IllegalListAccessException;
	
	/**
	 * Determines whether the list contains at least one occurrence of element.
	 * @param element the element to be searched.
	 * @return true if element is in list.
	 */
	public boolean contains(T element);
	
	/**
	 * Removes <b>one</b> instance of element from the list.
	 * @param element the element to be removed.
	 * @return <tt>true</tt> if the element was removed, <tt>false</tt> otherwise.
	 */
	public boolean delete(T element);
	
	/** Deletes the element at position <tt>index</tt> of the list.
	 * @param index The index of the element to be removed.
	 * @throws IllegalListAccessException if the index is below 0 or above the list's length.
	 */
	public void delete(int index) throws IllegalListAccessException;
	
	/**
	 * Removes <b>all</b> instances of element from the list.
	 * @param element The element to be removed.
	 * @return <tt>true</tt> if the element was removed at least once, <tt>false</tt> otherwise.
	 */
	public boolean deleteAll(T element);
	
	/**
	 * Returns the number of elements in the list.
	 * @return the number of elements in the list.
	 */
	public int size();
	
	
	/**
	 * Empties the list.
	 */
	public void clear();
	
	/**
	 * Queries the list for emptiness.
	 * @return <tt>true</tt> if the list is empty, <tt>false</tt> otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * <p>Returns a "fail-fast" {@link Iterator} over the elements contained by <tt>this</tt>.
	 * Fail-fast iterators will throw a {@link java.util.ConcurrentModificationException}
	 * if the collection is modified by means other than {@link Iterator#remove()}
	 * while iterating through the container through the {@link Iterator}.</p>
	 * 
	 * @return an {@link Iterator} over the elements of the collection, in proper (linear) order.
	 */
	public Iterator<T> iterator();
}
