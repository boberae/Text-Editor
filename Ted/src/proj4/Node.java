package proj4;

import java.io.Serializable;

public class Node implements Serializable {
    
	private static final long serialVersionUID = 1L;
	public String data;
    public Node next;
    public Node prev;

    public Node() {
        super();
    }

    public Node(String data, Node next, Node prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }


    public String getData() {
        return data;
    }

    public void setData(String data2) {
        this.data = data2;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
}