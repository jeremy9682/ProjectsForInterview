package projects.pqueue.heaps; // ******* <---  DO NOT ERASE THIS LINE!!!! *******

/* *****************************************************************************************
 * THE FOLLOWING IMPORT IS NECESSARY FOR THE ITERATOR() METHOD'S SIGNATURE. FOR THIS
 * REASON, YOU SHOULD NOT ERASE IT! YOUR CODE WILL BE UNCOMPILABLE IF YOU DO!
 * ********************************************************************************** */


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>A <tt>LinkedMinHeap</tt> is a tree (specifically, a <b>complete</b> binary tree) where every nodes is
 * smaller than or equal to its descendants (as defined by the <tt>compareTo() </tt>overridings of the type T).
 * Percolation is employed when the root is deleted, and insertions guarantee are performed in a way that guarantees
 * that the heap property is maintained. </p>
 *
 * <p>You <b>must</b> edit this class! To receive <b>any</b> credit for the unit tests related to this class,
 * your implementation <b>must</b> be a <i>"linked"</i>, <b>non-contiguous storage</b> (or, at least, not <i>necessarily</i>
 * contiguous storage) implementation based on a binary tree of nodes and references! </p>
 *
 * <p>Your background from CMSC132 as well as the implementation and testing framework of {@link projects.pqueue.trees.LinkedBinarySearchTree}
 * could be a help here. </p>
 * 
 * @author --- Zihan Liu ---
 *
 * @param <T> The {@link Comparable} type of object held by the <tt>LinkedMinHeap</tt>.
 *
 * @see projects.pqueue.trees.LinkedBinarySearchTree
 * @see MinHeap
 * @see ArrayMinHeap
 */
public class LinkedMinHeap<T extends Comparable<T>> implements MinHeap<T> { // *** <-- DO NOT CHANGE THIS LINE!!! ***

	private static final RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");


	/* *********************************************
	 * PLACE YOUR PRIVATE AND PROTECTED FIELDS HERE!
	 * YOU MIGHT ALSO WANT TO PUT PRIVATE METHODS AND/OR CLASSES HERE!
	 * THE DESIGN CHOICE IS YOURS ENTIRELY.
	 * ******************************************** */
	int size;
	boolean flag;
	heapNode first;
	heapNode last;


	class heapNode {
		T content;
		heapNode leftChild;
		heapNode rightChild;
		heapNode parent;

		public heapNode(T newContent){
			content = newContent;
			leftChild = null;
			rightChild = null;
			parent = null;
		}

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
	public LinkedMinHeap(){
		this.size = 0;
		this.flag = false;
		last = null;
	}

	/**
	 *  Second, non-default constructor.
	 *  @param rootElement the element to create the root with.
	 */
	public LinkedMinHeap(T rootElement){
		first  = new heapNode(rootElement);
		first.leftChild = null;
		first.rightChild = null;
		first.parent = null;
		size = 1;
		flag = false;
	}

	/**
	 * Copy constructor initializes the current MinHeap as a carbon
	 * copy of the parameter.
	 *
	 * @param other The MinHeap to copy the elements from.
	 */
	public LinkedMinHeap(MinHeap<T> other){
		if(other == null){ return;}
		for(T element: other){
			insert(element);
		}
		flag = false;
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
		if(!(other instanceof  MinHeap<?>)){
			return false;
		}

		MinHeap<T> temp = (MinHeap<T>) other;
		if(temp.size() !=  this.size){return false;}
		Iterator tempIterator = temp.iterator();
		Iterator currIterator = this.iterator();


		while(tempIterator.hasNext()){
			T a =  (T) tempIterator.next();
			T b = (T) currIterator.next();
			if(!(a.equals(b))){return false;}
		}

			return true;
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
		flag =  true;
		first = null;
		last = null;
		size = 0;
		flag = true;
	}

	@Override
	public void insert(T element) {

			if(element == null){
				return;
			}


			if(first == null) {

				heapNode root = new heapNode(element);
				first = root;
				last = root;


			}else if(size == 1) {

				heapNode newElement = new heapNode(element);
				first.leftChild = newElement;
				last = newElement;
				newElement.parent = first;



			}else if(size == 2){

				heapNode newElement = new heapNode(element);
				first.rightChild = newElement;
				last = newElement;
				newElement.parent = first;

			}else {

				heapNode newElement = new heapNode(element);
				// Get the binary number of size to locate the input place

				String binaryStr = Integer.toBinaryString(size+1);

				heapNode curr = this.first;

				for (int i = 1; i <binaryStr.length(); i++ ){

					char currNum = binaryStr.charAt(i);

					// when reach the target location
					if(i == binaryStr.length() -1){
						if(currNum == '0'){
							curr.leftChild = newElement;
							newElement.parent = curr;
							last = newElement;
						}else{
							curr.rightChild = newElement;
							newElement.parent = curr;
							last = newElement;
						}
						break;
					}

					if(currNum == '0'){
						curr = curr.leftChild;
					}else{
						curr = curr.rightChild;
					}
				}
				// After inserting the element. swap for maintaining the minheap property

			}

		if(first.leftChild != null) {
			heapNode current = last;
			while ( (current.parent != null) && ((current.content).compareTo(current.parent.content) < 0) ){
				heapNode temp = new heapNode(current.content);
				current.content = current.parent.content;
				current.parent.content = temp.content;
				current = current.parent;
			}
		}


			size++;
			flag = true;
	} 

	@Override
	public T getMin() throws EmptyHeapException {

		if(first == null){
			throw new EmptyHeapException("linkedMinHeap is empty");
		}
		return (T) first.content;
	}

	@Override
	public T deleteMin() throws EmptyHeapException {

		if(this.first == null){
			throw new EmptyHeapException("linkedMinHeap is empty");
		}


		T min = first.content;
		first.content = last.content;

		//When there is only one element
		if(first.leftChild == null){
			size--;
			flag = true;
			first = last = null;
			return min;
		}


		// Set the old last to be null
		if(last.parent.leftChild == last) {
			last.parent.leftChild = null;
		}else {
			last.parent.rightChild = null;
		}
			last = null;

		// Reorder the heap
		reOrder(first);

		// Set a new last
		setNewLast(first,size-1);// size is not reduced yet, so use size -1
		size--;
		flag = true;
		return min;
	}

	public T getLast(){
		return this.last.content;
	}

	public void setNewLast(heapNode root, int newSize){
		// Find the size-1th element and set it as last

		// Get the binary number of size to locate the input place
		if(newSize == 1){
			last = root;
			return;
		}
		String binaryStr = Integer.toBinaryString(newSize);

		heapNode curr = root;

		for (int i = 1; i < binaryStr.length(); i++ ){

			char currNum = binaryStr.charAt(i);
			// when reach the target location
			if(i == binaryStr.length() - 1){
				if(currNum == '0'){
					last = curr.leftChild;
				}else{
					last = curr.rightChild;
				}
				break;
			}
			if(currNum == '0'){
				curr = curr.leftChild;
			}else{
				curr = curr.rightChild;
			}
		}

	}

	public void reOrder(heapNode root){

		heapNode current = root;
		heapNode smallerChild = null;

	while(heapValidation(current)){

		if(current.rightChild != null) {

			if (((current.leftChild.content).compareTo((current.rightChild.content))) < 0) {
				smallerChild = current.leftChild;
			} else {
				smallerChild = current.rightChild;
			}
		}else{
			smallerChild = current.leftChild;
		}
		T temp =  current.content;
		current.content = smallerChild.content;
		smallerChild.content = temp;
		current = smallerChild;
	}
}

public boolean heapValidation( heapNode curr){
	if(curr == null ||  curr.leftChild== null){
		return false;
	}
	if(curr.rightChild == null && ((curr.content).compareTo(curr.leftChild.content) > 0) ){
		return true;
	}
	 T min = curr.leftChild.content;
	if(curr.leftChild!=null && curr.rightChild != null && (curr.leftChild.content.compareTo(curr.rightChild.content) > 0) ){
		min = curr.rightChild.content;
	}
	if(  (curr.content).compareTo(min) >0 ){
		return true;
	}

	return false;
}
/*
public void copyAll(LinkedMinHeap<T> temp, heapNode curr){
		if(curr == null){ return;}
		temp.insert(curr.content);
		if(curr.leftChild != null){
			copyAll(temp, curr.leftChild);
		}
		if(curr.rightChild != null){
			copyAll(temp, curr.rightChild);
		}
}
*/
public T find(heapNode root, int num){

	if(num == 1){
		return first.content;
	}

	String binaryStr = Integer.toBinaryString(num);

	heapNode curr = root;
	T returnContent = null;

	for (int i = 1; i < binaryStr.length(); i++ ){

		char currNum = binaryStr.charAt(i);
		// when reach the target location
		if(i == binaryStr.length() - 1){
			if(currNum == '0'){
				returnContent =  curr.leftChild.content;
			}else{
				returnContent =  curr.rightChild.content;
			}
			break;
		}
		if(currNum == '0'){
			curr = curr.leftChild;
		}else{
			curr = curr.rightChild;
		}
	}
	return returnContent;
}

	@Override
	public Iterator<T> iterator() {
		return new LinkedMinHeapIterator();
	}

	class LinkedMinHeapIterator<T extends Comparable<T>> implements Iterator<T>{
			MinHeap<T> tempHeap;
			int tempSize;
			//LinkedMinHeap<T> tempHeap;
	public LinkedMinHeapIterator(){
			tempSize = 0;
			tempHeap = new LinkedMinHeap<>();
		//	copyAll(tempHeap, first);
		for(int i = 1; i <= size; i++){
			T ithElment = (T) find(first,i);
			if(ithElment != null){
				tempHeap.insert(ithElment);
				tempSize++;
			}
		}
			flag = false;

	}

	public boolean hasNext(){
		return tempSize != 0;
	}

	public T next()throws ConcurrentModificationException, NoSuchElementException {
		T returnVal = null;
		if(flag){
			throw new ConcurrentModificationException(" ArrayMinHeap was modified ");
		}
		try{
			returnVal = tempHeap.deleteMin();
		}catch (EmptyHeapException e){
			throw new NoSuchElementException("tempHeap is empty");
		}

		return returnVal;
	}



	}


}



