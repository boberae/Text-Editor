package proj4;

import java.io.Serializable;


/******************************************************************
Custom double linked list class for Nodes with a 'top' pointer and 
a 'cursor' pointer.

@author Tony Bober
@version 1.0
 *****************************************************************/
public class LinkedList implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Points at first node in the linked list */
	protected Node top;
	
	/** Points at active node in the linked list */
	protected Node cursor;
	
	/** Number of nodes in the linked list */
	protected int size;	

	
	/******************************************************************
    Constructor initializes the top pointer and cursor pointer to null
	*****************************************************************/
	public LinkedList() {
		top = null;
		cursor = null;
	}

	
	/******************************************************************
    Sets the cursor pointer equal to the first node in the linked list
    
    @throws NullPointerException if cursor == null
	*****************************************************************/
	public void first() {
		cursor = top;
		if (cursor == null)
			throw new NullPointerException();
	}

	
	/******************************************************************
    Sets the cursor pointer equal to the last Node in the linked list
	*****************************************************************/
	public void last() {
		cursor = top;
        if (cursor == null)
            return;

        while (cursor.getNext() != null)
            cursor = cursor.getNext();
	}

	
	/******************************************************************
    Moves the cursor down one position in the linked list, so long as
    another node exists below.  
	*****************************************************************/
	public void next() {
		if (cursor == null)
            return;
		
		if (cursor.getNext() != null)
			cursor = cursor.getNext();
	}

	
	/******************************************************************
    Moves the cursor up one position in the linked list, so long as
    another node exists above.  
	*****************************************************************/
	public void previous() {
		if (cursor == null)
            return;
		
		if (cursor.getPrev() != null)
			cursor = cursor.getPrev();			
	}

	
	/******************************************************************
    Removes the node at the current cursor position, so long as that
    node exists. 
    
    @returns Returns true if removal of line was successful
	*****************************************************************/
    public boolean remove() {
    	// No cursor
    	if (cursor == null)
    		return false;
    	
    	// Cursor on only item
    	else if(cursor.getPrev() == null && cursor.getNext() == null) {
    		cursor = null;
    		top = null;
    		return true;
    	}
    		
    	// Cursor on bottom item
    	else if (cursor.getNext() == null){
    		cursor.getPrev().setNext(null);
    		cursor = cursor.getPrev();
    		return true;
    	}
    	
    	// Cursor on top item
    	else if (cursor.getPrev() == null) {
    		cursor.getNext().setPrev(null);
    		cursor = cursor.getNext();
    		top = top.getNext();
    		return true;
    	}
    	
    	// Cursor on middle item
    	else {
    		cursor.getPrev().setNext(cursor.getNext());
            cursor.getNext().setPrev(cursor.getPrev());
            cursor = cursor.getNext();
            return true;
    	}
    }
    
    
    /******************************************************************
    Removes all nodes in the linked list.
	*****************************************************************/
    //Remove all lines
    public void removeAll() {
    	while(remove());
    }
	
    
    /******************************************************************
    Adds a new node to the linked list one line before the cursor
    position, then sets the cursor position to the newly created node.
    
    @param element String value to add as data for new node
	*****************************************************************/
	// Add before cursor
	public void addBefore(String element) {  
		// Cursor = null
		if (cursor == null) {
			top = new Node(element, null, null);
			cursor = top;
		}
		// Cursor = top
		else if (cursor == top) {
			Node temp = new Node(element, cursor, null);
			cursor.setPrev(temp);
			top = temp;
			cursor = temp;
		}
		// Cursor = middle or bottom
		else {
			Node temp = new Node(element, cursor, cursor.getPrev());
			cursor.getPrev().setNext(temp);
			cursor.setPrev(temp);
			cursor = temp;
		}
	}

	
	/******************************************************************
    Adds a new node to the linked list one line after the cursor
    position, then sets the cursor position to the newly created node.
    
    @param element String value to add as data for new node
	*****************************************************************/
	// Add after cursor
	public void addAfter(String element) {
		// Cursor = null
		if (cursor == null) {
			top = new Node(element, null, null);
			cursor = top;
		}
		// Cursor = top or middle
		else if (cursor.getNext() != null) {
			Node temp = new Node(element, cursor.getNext(), cursor);
			cursor.getNext().setPrev(temp);
			cursor.setNext(temp);
			cursor = temp;
		}
		// Cursor = bottom
		else {
			Node temp = new Node(element, null, cursor);
			cursor.setNext(temp);
			cursor = temp;
		}
	}


	/******************************************************************
    Returns the size of the linked list.
    
    @returns Size of the linked list
	*****************************************************************/
	public int size() {
		Node cursorTemp = top;
		int count = 0;

		while (cursorTemp != null) {
			count++;
			cursorTemp = cursorTemp.getNext();
		}
		return count;
	}
	

	/******************************************************************
    Returns string data value at current node position
    
    @returns String data value at current node position
	*****************************************************************/
	public String get() {
		return cursor.getData();
	}

	
	/******************************************************************
    Returns string data value at specified node position
    
    @param position Position in the linked list for which to retrieve
    data
    @returns String data value at specified node position
	*****************************************************************/
	public String get(int position) {
		Node current = top;
		for (int i = 0; i < position; i++)
			current = current.getNext();
		return current.getData();

	}


	/******************************************************************
    Returns line number at which cursor is currently located
    
    @returns Integer line number at which cursor is currently located
	*****************************************************************/
	public int getLineNumber() {
		int line = 0;
		Node current = top;

		while (current != cursor) {
			current = current.getNext();
			line++;
		}
		return line;
	}

	
	/******************************************************************
    Moves cursor to the specified position in the linked list.
    
    @param position Line number to move cursor to.
	*****************************************************************/
	public void gotoLineNumber(int line) {
		cursor = top;
		for(int i = 0; i < line; i++) {
			cursor = cursor.getNext();
		}
	}
}
