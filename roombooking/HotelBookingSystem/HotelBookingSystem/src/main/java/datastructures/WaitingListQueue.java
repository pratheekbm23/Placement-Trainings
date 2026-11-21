package datastructures;

import model.Booking;

/**
 * Our own Queue (linked list, First-In-First-Out).
 * Guests wait here when no room of their type is free.
 */
public class WaitingListQueue {

    private static class Node {
        Booking data;
        Node next;

        Node(Booking data) { this.data = data; }
    }

    private Node front;   // first guest in line
    private Node rear;    // last guest in line
    private int size;

    /** Add a guest at the end of the line. */
    public void enqueue(Booking booking) {
        Node node = new Node(booking);
        if (rear == null) {
            front = rear = node;
        } else {
            rear.next = node;
            rear = node;
        }
        size++;
    }

    /** Remove and return the earliest waiting booking for the given room type (or null). */
    public Booking removeFirstOfType(String type) {
        Node prev = null;
        Node cur = front;
        while (cur != null) {
            if (cur.data.getRoomType().equalsIgnoreCase(type)) {
                if (prev == null) front = cur.next;
                else prev.next = cur.next;
                if (cur == rear) rear = prev;
                size--;
                return cur.data;
            }
            prev = cur;
            cur = cur.next;
        }
        return null;
    }

    public int size()        { return size; }
    public boolean isEmpty() { return size == 0; }

    public void display() {
        if (isEmpty()) {
            System.out.println("Waiting list is empty.");
            return;
        }
        System.out.println("\nWaiting List (first in line at top):");
        int position = 1;
        for (Node cur = front; cur != null; cur = cur.next) {
            Booking b = cur.data;
            System.out.println(position++ + ". Booking #" + b.getBookingId() + " - "
                    + b.getGuest().getName() + " wants " + b.getRoomType());
        }
    }
}
