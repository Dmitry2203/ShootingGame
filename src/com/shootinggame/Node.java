package com.shootinggame;

/**
 * Element of DoubleLinkedRingBuffer. 
 * item - data element of the Node.
 * next - reference to the next Node in list.
 * prev - reference to the previous Node in list.
 */

public class Node<E> {
    private E item;
    Node<E> next;
    Node<E> prev;

    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }

	public E getItem() {
		return item;
	}
}
