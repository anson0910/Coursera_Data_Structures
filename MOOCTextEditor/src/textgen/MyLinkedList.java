package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;		// sentinel head
	LLNode<E> tail;			// sentinel tail
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		this.size = 0;
		this.head = new LLNode<E> (null, null, this.tail);
		this.tail = new LLNode<E> (null, this.head, null);
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		add(this.size, element);
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		if ( index < 0 || index > (this.size - 1) )
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		
		// starting from sentinel head, trace to index
		LLNode<E> nodeToReturn = this.head.next;
		for (int i = 0; i < index; ++i)	{
			nodeToReturn = nodeToReturn.next; 
		}
		return nodeToReturn.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if ( index < 0 || index > this.size )
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		
		LLNode<E>  newNode = new LLNode<E> (element);
		LLNode<E>  nodeToAddAfter;
		LLNode<E>  nodeToAddBefore;
		
		// if appending to end of list
		if ( index == this.size )	{
			if ( this.size == 0 )	{		// if list was originally empty
				this.head.next = newNode;
				this.tail.prev = newNode;
				newNode.prev = this.head;
				newNode.next = this.tail;
			}
			else	{
				nodeToAddAfter = this.tail.prev;
				this.tail.prev = newNode;
				nodeToAddAfter.next = newNode;
				newNode.prev = nodeToAddAfter;
				newNode.next = this.tail;
			}				
		}
		// if appending to head of list
		else	if ( index == 0 ){
			nodeToAddBefore = this.head.next;
			this.head.next = newNode;
			newNode.prev = this.head;
			newNode.next = nodeToAddBefore;
			nodeToAddBefore.prev = newNode;
		}
		// if appending to middle of list
		else	{
			nodeToAddAfter = this.head;
			for(int i = 0; i < index; ++i)	{
				nodeToAddAfter = nodeToAddAfter.next;
			}
			nodeToAddBefore = nodeToAddAfter.next;
			
			nodeToAddAfter.next = newNode;
			newNode.prev = nodeToAddAfter;
			newNode.next = nodeToAddBefore;
			nodeToAddBefore.prev = newNode;			
		}
		this.size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if ( index < 0 || index > (this.size - 1) )
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		
		LLNode<E>  nodeToRemove;
		LLNode<E>  nodeAfterToRemove;
		LLNode<E>  nodeBeforeToRemove;
		
		// if removing last element
		if (index == (this.size - 1))	{
			nodeAfterToRemove = this.tail;
			nodeBeforeToRemove = nodeAfterToRemove.prev.prev;
		}
		// if removing first element
		else if (index == 0)	{
			nodeAfterToRemove = this.head.next.next;
			nodeBeforeToRemove = this.head;
		}
		// if removing middle element
		else	{
			nodeBeforeToRemove = this.head;
			for (int i = 0; i < index; ++i)	{
				nodeBeforeToRemove = nodeBeforeToRemove.next;
			}
			nodeAfterToRemove = nodeBeforeToRemove.next.next;
		}
		
		nodeToRemove = nodeBeforeToRemove.next;
		nodeAfterToRemove.prev = nodeBeforeToRemove;
		nodeBeforeToRemove.next = nodeAfterToRemove;
			
		this.size--;
		return nodeToRemove.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if (index < 0 || index > (this.size - 1))
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		if (element == null)
			throw new NullPointerException("Trying to set node with null element.");
		
		LLNode<E> elementToSet = this.head.next;
		// find the element to set
		for (int i = 0; i < index; ++i)
			elementToSet = elementToSet.next;
		// backup original data
		E backup = elementToSet.data;
		elementToSet.data = element;
		
		return backup;
	}
	
	// add toString method
	public String toString() {
		String  result = "";
		LLNode<E> currentElement = this.head;		
		for (int i = 0; i < this.size; ++i)	{
			currentElement = currentElement.next;
			result += (currentElement.data + " ");
		}
		return result;
	}
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode(E e, LLNode<E> p, LLNode<E> n)	{
		this.data = e;
		this.prev = p;
		this.next = n;
	}

}
