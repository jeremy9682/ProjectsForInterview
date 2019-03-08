package projects.pqueue.heaps; // ******* <---  DO NOT ERASE THIS LINE!!!! *******

/* *****************************************************************************************
 * THE FOLLOWING IMPORT IS NECESSARY FOR THE ITERATOR() METHOD'S SIGNATURE. FOR THIS
 * REASON, YOU SHOULD NOT ERASE IT! YOUR CODE WILL BE UNCOMPILABLE IF YOU DO!
 * ********************************************************************************** */

import java.lang.reflect.Array;
import java.util.*;

/**
 * <p><tt>ArrayMinHeap</tt> is a {@link MinHeap} implemented using an internal array. Since projects.pqueue.heaps are <b>complete</b>
 * binary projects.pqueue.trees, using contiguous storage to store them is an excellent idea, since with such storage we avoid
 * wasting bytes per <tt>null</tt> pointer in a linked implementation.</p>
 *
 * <p>You <b>must</b> edit this class! To receive <b>any</b> credit for the unit tests related to this class,
 * your implementation <b>must</b> be a <b>contiguous storage</b> implementation based on a linear {@link java.util.Collection}
 * or a raw Java array.</p>
 *
 * @author -- YOUR NAME HERE ---
 *
 * @see MinHeap
 * @see ArrayMinHeap
  */
public class ArrayMinHeap<T extends Comparable<T>> implements MinHeap<T> { // *** <-- DO NOT CHANGE THIS LINE!!! ***

	private static final RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");

	/* *********************************************
	 * PLACE YOUR PRIVATE AND PROTECTED FIELDS HERE!
	 * YOU MIGHT ALSO WANT TO PUT PRIVATE METHODS AND/OR CLASSES HERE!
	 * THE DESIGN CHOICE IS YOURS ENTIRELY.
	 * ******************************************** */
	private Object[] minHeap;
	private int size;
	protected boolean flag;
	private final int DEFAULT_CAPACITY = 50;

	private void expandArray(){
		Object[] newArray = new Object[this.minHeap.length*2];
		for(int i = 0; i < this.minHeap.length; i++){
			newArray[i] = minHeap[i];
			}
			this.minHeap = newArray;
	}

	public String toString(){
        ArrayList<T> newOne = new ArrayList<T>();
	    for(int i = 0; i < minHeap.length; i++ ){
	        if(minHeap[i] != null) {
                newOne.add((T) minHeap[i]);
            }
        }
	    return newOne.toString();
    }
	/* ***********************************************************************************
	 * YOU SHOULD IMPLEMENT THE FOLLOWING METHODS. BESIDES THE INTERFACE METHODS,
	 * THOSE INCLUDE CONSTRUCTORS (DEFAULT, NON-DEFAULT, COPY) AS WELL AS EQUALS().
	 * PLEASE MAKE SURE YOU RECALL HOW ONE SHOULD MAKE A CLASS-SAFE EQUALS() FROM EARLIER
	 * JAVA COURSES!
	 *
	 * YOU SHOULD NOT CHANGE *ANY* METHOD SIGNATURES! IF YOU DO, YOUR CODE WILL NOT RUN
	 * AGAINST OUR TESTS!
	 * ********************************************************************************** */


	/**
	 *  Default constructor.
	 */
	public ArrayMinHeap(){
		/* FILL THIS IN WITH YOUR IMPLEMENTATION OF A DEFAULT CONSTRUCTOR, IF ANY. */
		minHeap =  new Object[DEFAULT_CAPACITY];
		size = 0;
		flag = false;
	}

	/**
	 *  Second, non-default constructor.
	 *  @param rootElement the element to create the root with.
	 */
	public ArrayMinHeap(T rootElement){
        minHeap =  new Object[DEFAULT_CAPACITY];
	    minHeap[0] = rootElement;
	    size++;
	    flag = false;
	}

	/**
	 * Copy constructor initializes the current MinHeap as a carbon
	 * copy of the parameter.
	 *
	 * @param other The MinHeap to copy the elements from.
	 */
	public ArrayMinHeap(MinHeap<T> other){

		if(other == null){
			return;
		}else if(other.size() == 0){
			this.minHeap =  new Object[DEFAULT_CAPACITY];
			this.size = 0;
		}else{
            this.minHeap =  new Object[DEFAULT_CAPACITY];
			for(T newElements: other){
				if(newElements != null) {
					insert(newElements);
					size++;
				}else{
					break;
				}

			}
			flag = false;
		}
	}

	/**
	 * Standard equals() method.
	 *
	 * @return true If the parameter Object and the current MinHeap
	 * are identical Objects.
	 */
	@Override
	public boolean equals(Object other){

		if(other == null){return false;}
		if(!(other instanceof ArrayMinHeap)){return false;}

		ArrayMinHeap temp = (ArrayMinHeap) other;
		if(this.minHeap != null && temp!= null){

			if(Arrays.equals(this.minHeap, temp.minHeap) && (this.size == temp.size)){
				return true;
			}else{
				return false;
			}

		}
		return false;
	}


	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		this.minHeap = null;
		this.size = 0;
		flag = true;
	}



	@Override
	public void insert(T element) {
	   // System.out.println("size" +size);
	    // System.out.println("minheap" +minHeap);
		if (this.size == minHeap.length){expandArray();}

		minHeap[size] = element;
		int curr = this.size;
		int parent = (curr-1)/2;

	if(size == 0){
		size++;
		flag = true;
		return;
	}

		while((((T) minHeap[curr]).compareTo((T) minHeap[parent])) < 0 ){
			Object newOne =  minHeap[curr];
			minHeap[curr] = minHeap[parent];
			minHeap[parent] = newOne;
			curr = parent;
			parent = (parent-1)/2;
		}
		size++;
		flag = true;

	}

	@Override
	public T getMin() throws EmptyHeapException {
		if(this.minHeap[0] == null){
			throw new EmptyHeapException("minHeap is empty");
		}
		return (T) this.minHeap[0];
	}

	@Override
	public T deleteMin() throws EmptyHeapException {
		if(this.minHeap[0] == null){
			throw new EmptyHeapException("minHeap is empty");
		}

		T min = (T) minHeap[0];
		minHeap[0] = minHeap[size-1];
		minHeap[size-1] = null;

		int curr = 0;
		int leftChild = 2*curr + 1;
		int rightChild =2*curr + 2;
		int smallerChild = 0;
		while(heapValidation((T) minHeap[curr], (T) minHeap[leftChild],(T) minHeap[rightChild])){

		    if( (T) minHeap[rightChild] != null) {

                if ((((T) minHeap[leftChild]).compareTo((T) minHeap[rightChild])) < 0) {
                    smallerChild = leftChild;
                } else {
                    smallerChild = rightChild;
                }
            }else{
		        smallerChild = leftChild;
            }
				T temp = (T) minHeap[curr];
				minHeap[curr] = minHeap[smallerChild];
				minHeap[smallerChild] = temp;

				curr = smallerChild;
				leftChild = 2*curr + 1;
				rightChild = 2*curr + 2;
		}
		size--;
		flag = true;

	return min;
	}
	public boolean heapValidation( T curr, T leftChild, T rightChild){
	    if(curr == null || leftChild == null){
            return false;
        }
        if(rightChild == null && (curr.compareTo(leftChild) > 0) ){
            return true;
        }
		T min = leftChild;
		if(curr!=null && rightChild != null && (leftChild.compareTo(rightChild) > 0) ){
			min = rightChild;
		}
		if(  (curr).compareTo(min) >0 ){
			return true;
		}



        return false;

    }



	@Override
	public Iterator<T> iterator() {
			return new ArrayMinHeapIterator();
	}

	class ArrayMinHeapIterator<T extends Comparable<T>> implements Iterator<T>{
		//	MinHeap<T> tempHeap;
		ArrayList<T> tempHeap;
		 int tempSize;
		 int i = 0;
	public ArrayMinHeapIterator(){
		tempSize = 0;
		tempHeap = new ArrayList<T>();
		for(Object element: minHeap){
			if(element != null){
				tempHeap.add((T) element);
				tempSize++;
			}else{
				break;
			}
		}
		flag = false;
	}

	@Override
		public boolean hasNext(){
			return tempSize != 0;
	}
 	@Override
		public T next() throws ConcurrentModificationException, NoSuchElementException {


			T returnVal = null;
			if(flag){
				throw new ConcurrentModificationException(" ArrayMinHeap was modified ");
			}
				if(i < tempSize) {
					returnVal = tempHeap.get(i);
					i++;
				}


				return returnVal;
	}
	}


}
