package projects.tavl;


import java.util.Iterator;     // You need this import because of the interface method inorderTraversal()'s return type.

/**<p> {@link ThreadedAVLTree} implements threaded <a href="https://en.wikipedia.org/wiki/AVL_tree">Adelson-Velsky-Landis (AVL) trees</a>
 * (shorthand: TAVL trees). These trees:</p>
 * <ol>
 *      <li> Allow for efficient lookup, insertion and deletion in <em>O(logn)</em> time, by virtue
 *       of being AVL trees.</li>
 *       <li>Perform a full inorder traversal in <em>O(n)</em> time, by virtue of being threaded trees.</li>
 * </ol>
 * <p>Hence, two powerful ideas that we have talked about in lecture will now be combined in one data structure. </p>
 *
 *  <p>To get more than 50&#37; credit for this project, you <b>must</b> attempt to implement your tree as a <b>threaded</b>
 * tree, as discussed in lecture. We have access to your source code after submission and we <b>will</b> be checking to
 * make sure that you have been appropriately updating the tree's threads every time you must! You are also required to
 * implement a method for generating the inorder traversal over such a tree, and this method needs to be functioning
 * <b>entirely stacklessly!</b>Read the project description for more in-depth information.</p>
 *
 * <p>Finally, for this project, <b>we assume that there are no duplicate keys in your data structure. </b> This means that, in our unit tests,
 * whenever we delete a key from your tree, <b>we expect it to no longer be found in the tree.</b> You may deal with this
 * invariant in any way you please, e.g. throw an exception if a duplicate is inserted, or delete all instances of a key when we ask for a deletion.</p>
 *
 * @author ------- Zihan Liu ------
 * @see #inorderTraversal()
 * @see StudentTests
 * @param <T> The {@link java.lang.Comparable} type held by the data structure.
 */
public class ThreadedAVLTree<T extends Comparable<T>> {

        int height;
        TAVLNode root;

    private static final String UNIMPL_MSG = "Implement this method!";

    /* ***************************************************************************
     * PLACE YOUR PRIVATE, PACKAGE-PRIVATE AND PROTECTED MEMBERS  AND METHODS HERE:
     * *************************************************************************** */
    protected class TAVLNode{
        T value;
        TAVLNode leftChild;
        TAVLNode rightChild;
        TAVLNode IOsuccessor;
        boolean leftThread,rightThread; // false == link, true == thread

        public TAVLNode(T value){
            this.value = value;
        }
    }


    /* ******************************************
     * IMPLEMENT THE CLASS' PUBLIC METHODS BELOW:
     * ****************************************** */


    /**
     * Default constructor. Your code should allow for one, since the unit tests
     * depend on the presence of a default constructor.
     */
    public ThreadedAVLTree(){
        /*
         *  Fill-in the code here!
         */
       // <---- When done implementing your method, erase this line!

        /* Note: Depending on how you handle things. it might be completely ok
         * to have nothing in your constructor. That is, the only thing you'd need to do
         * here is erase the application of throw() above. Your code, your choice ! */
    }

    /**
     * Insert <tt>key</tt> in the tree.
     * @param key The key to insert in the tree.
     */
    public void insert(T key){

        if(this.root == null){
            TAVLNode newNode = new TAVLNode(key);
            root = newNode;
            newNode.leftThread = true;
            newNode.rightThread = true;

            this.height = 0;
        }else{
          root =  insertHelper(key,root);
            this.height = getHeight(root);
        }
        setupThread();
    }


    private TAVLNode insertHelper(T key, TAVLNode curr){
        // when add node into the left child
        if(curr.leftChild == null && (key.compareTo(curr.value) < 0) ){
            TAVLNode newNode = new TAVLNode(key);
            curr.leftChild = newNode;
            curr.leftThread = false;
            newNode.leftThread = true;
            newNode.rightThread = true;
            return curr;
        }
            // Add node into right child
        if(curr.rightChild == null && key.compareTo(curr.value) > 0){
            TAVLNode newNode = new TAVLNode(key);
            curr.rightChild = newNode;
            curr.rightThread = false;
            newNode.leftThread = true;
            newNode.rightThread = true;
            return curr;
        }

        if(key.compareTo(curr.value) < 0){
            curr.leftChild = insertHelper(key,curr.leftChild);
            if(getHeight(curr.leftChild) - getHeight(curr.rightChild) == 2 ){
                if(key.compareTo(curr.leftChild.value) < 0){
                    curr = rotateRight(curr);
                }else{
                    curr = rotateLR(curr);
                }
            }
        }else if(key.compareTo(curr.value) > 0) {
            curr.rightChild = insertHelper(key,curr.rightChild);
            if(getHeight(curr.rightChild) - getHeight(curr.leftChild) == 2 ){
                //rotation
                if(key.compareTo(curr.rightChild.value) > 0){
                    curr = rotateLeft(curr);
                }else{
                    curr = rotateRL(curr);
                }
            }
        }else{// when there is a duplicate element
            return curr;
        }
        return curr;
    }
    public void setupThread(){
        // no element
        if(root == null){ return;}
        // Only one element
        if(root.leftChild == null && root.rightChild == null){
            return;
        }
        // Start from leftMost element
        TAVLNode curr = getLeftMost();
        while(!rightMostNode(root,curr)){
            curr.IOsuccessor = inorderSuc(curr);
            curr = curr.IOsuccessor;
        }

    }



    public int getHeight(TAVLNode curr){
        if(curr == null ){return -1;}
        return Math.max(getHeight(curr.leftChild),getHeight(curr.rightChild)) + 1;
    }
    public int getSize(TAVLNode curr){
        if(curr == null){return 0;}
        return  1+getSize(curr.leftChild) + getSize(curr.rightChild);
    }


    public TAVLNode rotateRight(TAVLNode t){
        TAVLNode temp = t.leftChild;
        t.leftChild = temp.rightChild;
        temp.rightChild = t;
        // Flip thread
        t.leftThread = temp.rightThread;
        temp.rightThread = false;
        return temp;
    }

    public TAVLNode rotateLeft(TAVLNode t){
        TAVLNode temp = t.rightChild;
        t.rightChild = temp.leftChild;
        temp.leftChild = t;
        // Flip thread
        t.rightThread =  temp.leftThread;
        temp.leftThread = false;
        return temp;

    }
    public TAVLNode rotateLR(TAVLNode t){
        t.leftChild = rotateLeft(t.leftChild);
        t = rotateRight(t);
        return t;
    }
    public TAVLNode rotateRL(TAVLNode t){
        t.rightChild = rotateRight(t.rightChild);
        t = rotateLeft(t);
        return t;
    }

    /**
     * Delete the key from the data structure and return it to the caller. Note that it is assumed that there are no
     * duplicate keys in the tree. That is, if a key is deleted from the tree, it should no longer be found in it.
     * @param key The key to deleteRec from the structure.
     * @return The key that was removed, or <tt>null</tt> if the key was not found.
     */
    public T delete(T key){
       // The element doesn't exist
        if(search(key) == null || root == null){ return null;}

        T retvl = search(key);
       // when there is only one element
        if((root.value.compareTo(key) == 0) && root.leftChild == null && root.rightChild == null){
           root = null;
            return retvl;
        }
            // In case of duplicated element
        while(search(key) != null) {
            root = deleteHelper(key, root);
            this.height = getHeight(root);
        }
        setupThread();
        return retvl;
    }



    public TAVLNode deleteHelper(T key, TAVLNode curr){

        // Stop at the deleted node's parent node
         if(curr.value.compareTo(key) == 0) {
             // Leaf node
             if (curr.leftChild == null && curr.rightChild == null) {
                 TAVLNode parent = getParent(root, curr);
                 // Set parent's thread
                 if (parent.leftChild != null && parent.leftChild.value.compareTo(curr.value) == 0) {
                     parent.leftThread = true;
                 } else {
                     parent.rightThread = true;
                 }
                 return null;
                 // No right Child, has leftChild
             } else if (curr.leftChild != null && curr.rightChild == null) {
                 return curr.leftChild;
                 // No left child but has right child
             } else if (curr.leftChild == null && curr.rightChild != null) {
                 return curr.rightChild;
             } else {// When using inorder successor
                 TAVLNode successor = inorderSuc(curr);
                 T temp = successor.value;
                 // remove the inorder successor
                 curr.rightChild = deleteHelper(successor.value,curr.rightChild);
                 curr.value = temp;

                 if (getHeight(curr.leftChild) - getHeight(curr.rightChild) == 2) {
                     int inBalance = getHeight(curr.leftChild.rightChild) - getHeight(curr.leftChild.leftChild);

                     if (inBalance == -1) {
                         curr = rotateRight(curr);
                     } else {
                         curr = rotateLR(curr);
                     }
                 }

                 return curr;
                }
            }

             if (key.compareTo(curr.value) < 0) {
                 curr.leftChild = deleteHelper(key, curr.leftChild);

                 if (getHeight(curr.rightChild) - getHeight(curr.leftChild) == 2) {

                     int inBalance = getHeight(curr.rightChild.rightChild) - getHeight(curr.rightChild.leftChild);
                     if (inBalance == -1) {
                         curr = rotateRL(curr);
                     } else {
                         curr = rotateLeft(curr);
                     }
                 }


             } else if (key.compareTo(curr.value) > 0) {
                 curr.rightChild = deleteHelper(key, curr.rightChild);

                 if (getHeight(curr.leftChild) - getHeight(curr.rightChild) == 2) {
                     int inBalance = getHeight(curr.leftChild.rightChild) - getHeight(curr.leftChild.leftChild);

                     if (inBalance == -1) {
                         curr = rotateRight(curr);
                     } else {
                         curr = rotateLR(curr);
                     }
                 }


             }
        return curr;
    }



/*
public void rebalance(TAVLNode curr){

        if(getHeight(curr.rightChild) - getHeight(curr.leftChild) == 2){
            if(getHeight(curr.rightChild.leftChild)-getHeight(curr.rightChild.rightChild) <= 0){
                curr = rotateLeft(curr);
            }else{
                curr = rotateRL(curr);
            }
        } else if(getHeight(curr.leftChild) - getHeight(curr.rightChild) == 2){
            if(getHeight(curr.leftChild.leftChild) - getHeight(curr.leftChild.rightChild) >= 0){
                curr = rotateRight(curr);
            }else{
                curr = rotateLR(curr);

            }
        }

}
*/




    public TAVLNode inorderSuc(TAVLNode t){

        // Handle the corner cases first
        // Empty tree, only one node
        if(root == null || height == 0 ){return null;}

        if (rightMostNode(root,t) == true) {// when it is the rightmost node of root, no inorder successor
            return null;
        }
        if(leftMostNode(root,t) == true){// when it is the leftmost node of root
            return getParent(root,t);
        }

        // Complex cases
            // Right child is not null
            if(t.rightChild != null){
                if(t.rightChild.leftChild == null){// Get the right child directly
                    return t.rightChild;
                }else{// Get right child's leftmost node
                    t = t.rightChild;
                    while(t.leftChild != null){
                        t = t.leftChild;                    }
                }
                return t;
            }else { // when right child is null, it means that inorder successor is on the top
                TAVLNode parent = getParent(root, t);
                // when parent node is inorder successor
                if (parent.leftChild != null && (parent.leftChild.value).compareTo(t.value) == 0) {
                    return parent;
                } else { // when inorder successor is grandparent node or grandgrandparent node
                    TAVLNode curr = root;
                    // Loop until t is curr's left child's rightMost child, which means t's inorder successor is curr
                    while (rightMostNode(curr.leftChild, t) == false) {
                        if ((t.value).compareTo(curr.value) < 0) {
                            curr = curr.leftChild;
                        } else {
                            curr = curr.rightChild;
                        }
                    }
                    return curr;
                }
            }

    }


    // // Check if t is r's rightmost node
    public boolean rightMostNode(TAVLNode r, TAVLNode t){
        TAVLNode curr = r;
        while(curr.rightChild != null){
            curr = curr.rightChild;
        }
        if((curr.value).compareTo(t.value) == 0){return true;}
        return false;
    }

    // Check if t is r's leftmost node
    public boolean leftMostNode(TAVLNode r, TAVLNode t){
        TAVLNode curr = r;
        while(curr.leftChild != null){
            curr = curr.leftChild;
        }
        if((curr.value).compareTo(t.value) == 0 && curr.rightChild == null){return true;}
        return false;
    }

    public TAVLNode getParent(TAVLNode r, TAVLNode t){
        TAVLNode curr = r;
        while( curr.leftChild != null && curr.rightChild != null){

            if(((curr.leftChild != null) && ((curr.leftChild.value).compareTo(t.value) == 0))||
                    ((curr.rightChild != null)&&((curr.rightChild.value).compareTo(t.value) == 0))){
                return curr;
            }

            if( (t.value).compareTo(curr.value)  < 0){
                curr = curr.leftChild;
            }else {
                curr = curr.rightChild;
            }
        }
        return curr;
    }


    /**
     * Search for <tt>key</tt> in the tree. Return a reference to it if it's in there,
     * or <tt>null</tt> otherwise.
     * @param key The key to look for in the tree.
     * @return <tt>key</tt> if <tt>key</tt> is in the tree, or <tt>null</tt> otherwise.
     */
    public T search(T key){

      if(root == null) return null;
        TAVLNode curr = root;

      while(curr != null){
            if(key.compareTo(curr.value) == 0){
                return key;
            }

            if(key.compareTo(curr.value) < 0){
                curr = curr.leftChild;
            }else {
                curr = curr.rightChild;
            }
        }
      return null;
    }
    public TAVLNode getter(T key){

        if(root == null) return null;
        TAVLNode curr = root;

        while(curr != null){
            if(key.compareTo(curr.value) == 0){
                return curr;
            }

            if(key.compareTo(curr.value) < 0){
                curr = curr.leftChild;
            }else {
                curr = curr.rightChild;
            }
        }
        return null;

    }

    public TAVLNode getLeftMost(){
        TAVLNode curr = root;
        while(curr.leftChild != null){
            curr = curr.leftChild;
        }
        return curr;
    }

    /**
     * Return the height of the tree. The height of the tree is defined as the length of the
     * longest path between the root and the leaf level. By definition of path length, a
     * stub tree has a height of 0, and we define an empty tree to have a height of -1.
     * @return The height of the tree.
     */
    public int height(){
     return getHeight(root);
    }


    /**
     * Query the tree for emptiness. A tree is empty iff it has zero keys stored.
     * @return <tt>true</tt> if the tree is empty, <tt>false</tt> otherwise.
     */
    public boolean isEmpty(){
       return this.root == null;
    }
    /**
     * Return the key at the tree's root node.
     * @return The key at the tree's root node, or <tt>null</tt> if the tree is empty.
     */
    public T getRoot(){
        if(root == null) return null;
       return this.root.value;
    }
    /**
     * Generate an inorder traversal over the tree's stored keys. This should be done
     * by using the tree's threads, to be able to find every inorder successor in amortized constant
     * time. TO GET MORE THAN 50&#37; CREDIT IN THIS PROJECT, YOU <b>MUST</b> IMPLEMENT YOUR TREE AS A THREADED TREE.
     * IN PARTICULAR, TO GET ANY CREDIT FOR THIS METHOD, YOUR CODE <b>MUST</b> PASS THE RELEVANT UNIT TESTS AND
     * YOU MUST BE MAKING NO CALLS TO ANY STACK, YOURS OR THE SYSTEM'S!
     *
     * @return An {@link Iterator} over <tt>T</tt>s, which exposes the elements in
     * ascending order. If the tree is empty, the {@link Iterator}'s first call to {@link Iterator#hasNext()}
     * will return <tt>false</tt>. The behavior of {@link Iterator#remove()} is <b>undefined</b>; we do <b>not</b> test
     * for removal of elements through the returned {@link Iterator}, so you can implement {@link Iterator#remove()} in
     * <b>any way you please</b>.
     */



    public Iterator<T> inorderTraversal(){

        return new TAVLIterator();
    }

     class TAVLIterator implements Iterator<T>{
        int i;
        TAVLNode current;

        public TAVLIterator(){
            i = 0;
            current = getLeftMost();
        }
        @Override
        public boolean hasNext(){
            return i != getSize(root);
        }
        @Override
        public T next(){
            if(hasNext()) {
                    T currentT = current.value;
                    current = current.IOsuccessor;
                    i++;
                    return currentT;
                }

            return null;
        }


    }
}