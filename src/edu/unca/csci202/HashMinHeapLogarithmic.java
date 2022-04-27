package edu.unca.csci202;
import java.util.HashMap;

/**
 * A min heap with the ability to update elements in the heap.
 * The effect of ties is ignored.
 * Implemented using an array as a binary tree.
 * Size must be known at construction.
 * All N=size elements must be "inserted" prior to use of getMin and update
 * 
 * @author ksanft
 *
 * @param <T> T must implement Comparable<T> (verified in insert) 
 */
public class HashMinHeapLogarithmic<T> implements HashMinHeapADT<T> {
	// the heap data
	protected T[] data;
	// a map that says where in the heap a particular element T is stored
	protected HashMap<T,Integer> map;
	protected int elementCount;
	protected boolean initialized;
	
	/**
	 * Constructor sets up the underlying array data structure but
	 * does not fill it with elements (use insert to fill the structure)
	 * @param size the number of elements that will be in the heap
	 */
	public HashMinHeapLogarithmic(int size) {
		data = (T[])(new Object[size]);
		map = new HashMap<T,Integer>();
		elementCount=0;
		initialized=false;
	}
	
	/**
	 * Used to insert elements into the heap. 
	 * IN THIS IMPLEMENTATION, AFTER CONSTRUCTION, THIS METHOD MUST
	 * BE CALLED data.length TIMES TO FILL THE HEAP, AFTER WHICH
	 * INITIALIZED IS SET TO TRUE.
	 * The intent is that this is used to fill the heap
	 * before calling the other methods (e.g. getMin, update) 
	 * 
	 * To reiterate: once initialized is true, this should never be called again.
	 * 
	 * @param element the element to be inserted
	 */ 
	public void insert(T element) {
		if (!(element instanceof Comparable))
			throw new NonComparableElementException("IndexedMinHeap");
		
		if (elementCount==data.length) {
			throw new IndexOutOfBoundsException("IndexedMinHeap is full");
		}
		data[elementCount]=element;
		map.put(element,elementCount);
		elementCount++;
		bubbleUp(element);
		bubbleDown(element);
		if (elementCount==data.length) {
			initialized=true;
		}
	}
	
	/**
	 * Used to get the minimum value within the array
	 * @return T the minimum value in the heap
	 */
	public T getMin() {
		if (elementCount!=data.length || !initialized) {
			throw new IllegalStateException("Method should not be called until heap is full.");
		}
		// uncomment next line when binary heap is implemented
		return data[0];
		// comment-out below when binary heap is implemented
		// find the smallest element using linear search
//		int indexOfMin=0;
//		Comparable<T> comparableElement = (Comparable<T>)data[indexOfMin];
//		
//		for (int i=1; i<data.length; i++) {
//			if (comparableElement.compareTo(data[i])>0) {
//				indexOfMin=i;
//				comparableElement = (Comparable<T>)data[indexOfMin];
//			}
//		}
//		return data[indexOfMin];
	}
	
	/**
	 * Used to swap two nodes with one another in a binary tree
	 * @param child the child node
	 * @param parent the parent node
	 */
	public void swap(Integer child, Integer parent) {
		
		T childTemp = data[child];
		T parentTemp = data[parent];
		
		data[child] = data[parent];
		data[parent] = childTemp;
		map.replace(parentTemp, parent, child);
		map.replace(childTemp, child, parent);
	}
	
	public void bubbleDown(T element) {
		
		int index = map.get(element);
		
		try {
			while (hasChildren(element) && (((Comparable<T>)data[index]).compareTo(data[(2*index) + 1]) > 0) || ((Comparable<T>)data[index]).compareTo(data[(2*index) + 2]) > 0) {
	
				if (((Comparable<T>)data[index]).compareTo(data[(2*index) + 1]) > 0) {
					swap(index, (2*index) + 1);
					index = (index*2) + 1;
				} else if (((Comparable<T>)data[index]).compareTo(data[(2*index) + 2]) > 0) {
					swap(index, (index*2) + 2);
					index = (index*2) + 2;
				}
			}
		} catch (Exception e) {
			return;
		}
	}
	
	public void bubbleUp(T element) {
		
		int index = map.get(element);
		
		while (hasParent(index) && ((Comparable<T>)data[index]).compareTo(data[(index - 1) / 2]) < 0) {
			swap(index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}
	
	/**
	 * Used to either bubble-up or bubble-down the node that was
	 * added or manipulated within the binary tree.
	 * @param element the element that was either just added or manipulated,
	 * needing to be updated.
	 */
	public void update(T element) {
		
		if (elementCount!=data.length || !initialized) {
			throw new IllegalStateException("Method should not be called until heap is full.");
		}
		
		bubbleUp(element);
		bubbleDown(element);
	}
	
	/**
	 * Checks to see if the node has a parent node
	 * @param i the index within the binary tree
	 * @return true if the node has a parent
	 */
	protected boolean hasParent(int i) {
		
		if (i > 0) {
			return true;
		}
		return false;
	}
	
	protected boolean hasChildren(T element) {
		
		int index = map.get(element);
		
		if ((data[(2*index)+1] != null) || (data[(2*index)+2] != null)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public int getElementCount() {
		return elementCount;
	}
}
