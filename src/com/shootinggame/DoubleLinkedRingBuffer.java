package com.shootinggame;

/**
 * Implementation of small double linked and circular linked list.
 * List performance is O(1) for adding and removing elements.
 * Inheritance from any containers from JDK is not so good for our purposes.
 */

public class DoubleLinkedRingBuffer<E> {
    private Node<E> head;
    private Node<E> current;
    private int size = 0;

	/**
	  * Adding new element to the list.
	  * @param element - data of the element.
	  */
    public void add(E element) {
        size++;
        if (head == null) {
            head = new Node<>(null, element, null);
            head.prev = head.next = head;
            current = head;
            return;
        }
        Node<E> last = head.prev;
        Node<E> newNode = new Node<>(last, element, head);
        last.next = newNode;
        head.prev = newNode;
    }

	/**
	 * Remove node from the list.
	 * @param node - node that must be deleted.
	 */
    public void remove(Node<E> node) {
        if (node != null) {
            Node<E> next = node.next;
            Node<E> prev = node.prev;
            prev.next = next;
            next.prev = prev;
            size--;
        }
    }
    
	/**
	 * Set new element as current. New current is after "value" steps from current.        
	 * @param value - steps from current element.
	 */
    public void setCurrentByValue(int value) {
        if(value > 0){
            while(--value > 0) current = current.next;
        }
    }

	/**
	 * Set current element by node.    
	 * @param current - node that must be current.
	 */
    public void setCurrentByNode(Node<E> current) {
        this.current = current;
    }

    /**
     * Return current element.
     * @return - current element.
     */
    public Node<E> getCurrentNode(){
        return current;
    }

    /**
     * Return previous element from current.
     * @return - previous element.
     */
    public Node<E> getPrevNode(){
        return current.prev;
    }

    /**
     * Return next element from the current.
     * @return - next element.
     */
    public Node<E> getNextNode(){
        return current.next;
    }

    /**     
     * @return - size of the list.
     */
    public int size() { return size; }
}
