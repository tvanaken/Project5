package edu.unca.csci202;
/**
 * Interface that defines a HashMinHeap.
 * Similar to a MinHeap but 
 * @author ksanft
 *
 * @param <T>
 */
public interface HashMinHeapADT<T> {

	/**
	 * used to insert an element into the heap.
	 * 
	 * @param element the element to be inserted
	 */ 
	public void insert(T element);
	
	/**
	 * return a reference to the smallest element. In typical implementation
	 * this is the root of a binary minheap (tree).
	 * 
	 * @return a reference to the smallest element in the heap
	 */
	public T getMin();
	
	/**
	 * Update an element in the tree because its "value" (as 
	 * determined by compareTo) may have changed.
	 * @param element The element in the heap that needs to be updated.
	 */
	public void update(T element);
	
	/**
	 * Returns true if the heap has been properly constructed. getMin
	 * and update should not be called until isInitialized is true.
	 * 
	 * @return the initialized indicator variable
	 */
	public boolean isInitialized();
	
	/**
	 * The number of elements in the heap.
	 * 
	 * @return the elementCount
	 */
	public int getElementCount();
}
