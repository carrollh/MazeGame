import java.util.NoSuchElementException;


public class DLList<T> {
	private DLLNode<T> head = null;	// pointer to the head Node, once it exists
	private int size = 0;			// tracker for the number of nodes in the list

	// No-arg constructor (not technically needed...)
	public DLList() {
		
	}
	
	/**
	 * Returns the number of elements in this list.
	 * @return
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Method used to add a new Node to the end of the list. It 
	 * makes that new Node the first node if head is null, otherwise
	 * it adds it to the end.
	 * The project description lied and said this was supposed to 
	 * return void. It should return boolean as the LinkedList.add(E e) 
	 * method does just that.
	 * @param o
	 */
	public boolean add(T t) {
		DLLNode<T> temp = new DLLNode<T>(t);
		if(head != null) {
			temp.previous = this.lastNode();
			this.lastNode().next = temp;
		}
		else {
			head = temp;
		}
		
		size++;
		
		return true;
	}
	
	/**
	 * Inserts the specified element at the specified position in this list.
	 * @param index
	 * @param o
	 * @throws IndexOutOfBoundsException
	 */
	public void add(int index, T o) throws IndexOutOfBoundsException {
		if(index >= size && size > 0) throw new IndexOutOfBoundsException();
		else if(head != null){
			DLLNode<T> temp = new DLLNode<T>(o);
		
			temp.next = getNode(index);
			temp.previous = getNode(index).previous;
			
			
			getNode(index).previous = temp;
			getNode(index-1).next = temp;
		}
		else add(o);
		
		size++;
	}
	
	/**
	 * Returns true if this list contains the specified element.
	 * 
	 * @param o
	 * @return
	 */
	public boolean contains(T o) {
		boolean wasFound = false;
		
		if(size > 0 && head != null) {
			DLLNode<T> temp = head;
			while(temp.next != null && !wasFound) {
				if(temp.data.equals(o)) wasFound = true;
				temp = temp.next;
			}
			if(temp.data.equals(o)) wasFound = true;
		}
		
		return wasFound;
	}
	
	/**
	 * Returns the element at the specified position in this list.
	 * @param index
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public T get(int index) throws IndexOutOfBoundsException {
		if(index >= size) throw new IndexOutOfBoundsException();
		else if(head != null) {
			DLLNode<T> temp = head;
			int counter = 0;
			while(temp.next != null && counter < index) {
				temp = temp.next;
				counter++;
			}
			return temp.data;
		}
		
		return null;
		
		// if you have implemented the private .getNode method below,
		// you can just do the following 
		//return getNode(index).data;
	}
	
	/**
	 * Helper method that returns the DLLNode<T> at the specified position in this list.
	 * @param index
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	private DLLNode<T> getNode(int index) throws IndexOutOfBoundsException {
		if(index >= size) throw new IndexOutOfBoundsException();
		else if(head != null) {
			DLLNode<T> temp = head;
			int counter = 0;
			while(temp.next != null && counter < index) {
				temp = temp.next;
				counter++;
			}
			return temp;
		}
		
		return null;
	}
	
	/**
	 * Returns the index of the first occurrence of the specified element 
	 * in this list, or -1 if this list does not contain the element.
	 * 
	 * @param o
	 * @return
	 */
	public int indexOf(T o) {
		int index = -1;
		if(size > 0 && head != null) {
			DLLNode<T> temp = head;
			int counter = 0;
			while(temp.next != null && index == -1) {
				if(temp.data.equals(o)) index = counter;
				else counter++;
			}
		}
		
		return index;
	}
	
	/**
	 * Returns true if this list contains no elements.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0; 
	}
	
	/**
	 * Retrieves and removes the head (first element) of this list.
	 * This wasn't specified as required in the project description.
	 * @return
	 */
	public T remove() throws NoSuchElementException {
		if(head != null) {
			DLLNode<T> temp = head;
			head = head.next;
			
			size--;
			
			return temp.data;
		}
		
		throw new NoSuchElementException();
	}
	
	/**
	 * Removes the element at the specified position in this list.
	 * @param index
	 * @return
	 */
	public T remove(int index)  throws NoSuchElementException {
		if(head != null) {
			if(index > 0) {
				DLLNode<T> temp = head;
				
				int counter = 0;
				while(temp.next != null && counter < index) {
					temp = temp.next;
					counter++;
				}
				
				if(index + 1 < size) getNode(index - 1).next = getNode(index + 1);
				else getNode(index - 1).next = null;
				
				getNode(index).previous = getNode(index - 1);
				
				size--;
			
				return temp.data;
			}
			else remove();
		}
		
		return null;
	}
	
	/**
	 * Helper method that walks the entire data structure from the
	 * head Node to the end Node and returns that Node. 
	 * Note: this method returns null if the DLList<T> has not had any
	 * data added to it.
	 * Called by the .add method so that the added Node can become the
	 * last Node's "next" node.
	 * @return
	 */
	private DLLNode<T> lastNode() {
		if(head != null) {
			DLLNode<T> temp = head;
			while(temp.next != null) {
				temp = temp.next;
			}
			
			return temp;
		}
		
		return null;
	}
	
	/**
	 * Returns a String consisting of "DLList<T>: [ item0, item1, ... ]"
	 */
	@Override
	public String toString() {
		String output = "DLList<T>: [ ";
			
		if(head != null) {
			DLLNode<T> temp = head;
			output += temp.data.toString();
			
			while(temp.next != null) {
				temp = temp.next;
				output += ", " + temp.data.toString();
			}
		}
		output += " ]";
		return output;
	}
	

	/**
	 * Compares the specified T with this list for equality. Returns 
	 * true if and only if the specified T is also a list, both lists 
	 * have the same size, and all corresponding pairs of elements in the 
	 * two lists are equal. 
	 * (Two elements e1 and e2 are equal if (e1==null ? e2==null : e1.equals(e2)).) 
	 * In other words, two lists are defined to be equal if they contain 
	 * the same elements in the same order. This definition ensures that 
	 * the equals method works properly across different implementations 
	 * of the List interface.
	 */
	@Override
	public boolean equals(Object obj) {
		boolean isEqual = true;
		if (obj != null) {
			if (getClass() != obj.getClass()) isEqual = false;
			else {
				DLList<T> other = (DLList<T>) obj;
				if (head == null) {
					if (other.head != null) isEqual = false;
				} 
				else if (!head.data.equals(other.head.data)) isEqual = false;
				
				if (size != other.size) isEqual = false;
				else {
					for(int i = 0; i < size && isEqual; i++) {
						if(!get(i).equals(other.get(i))) isEqual = false;
					}
				}
			}
		}
		return isEqual;
	}


	/**
	 * Node class for the DLList<T> class above. It stores whatever 
	 * data is needed stored, and has a DLLNode<T> pointer to the 
	 * next node and another to the previous node in the data structure.
	 * @author carro_000
	 *
	 */
	private class DLLNode<S> {
		
		S data = null;
		DLLNode<S> next = null;
		DLLNode<S> previous = null;
		
		private DLLNode(S data) {
			this.data = data;
		}
	}
}