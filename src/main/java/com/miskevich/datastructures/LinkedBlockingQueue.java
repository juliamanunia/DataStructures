package com.miskevich.datastructures;

import java.util.StringJoiner;

public class LinkedBlockingQueue<E> extends AbstractBlockingQueue<E> implements Queue<E> {

    private int countOfElementsInQueue;
    private int capacity;
    private Node<E> head;
    private Node<E> tail;

    public LinkedBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity of the queue can't be <= 0");
        }
        this.capacity = capacity;
        head = tail = new Node<>(null);
    }

    public E peek() {
        synchronized (this){
            if(countOfElementsInQueue > 0){
                Node<E> temp = head;
                Node<E> firstElementInQueue = temp.next;
                return firstElementInQueue.value;
            }else {
                return null;
            }
        }
    }

    public E poll() {
        synchronized (this){
            if(countOfElementsInQueue > 0){
                Node<E> temp = head;
                Node<E> firstElementInQueue = temp.next;
                temp.next = null;
                head = firstElementInQueue;
                E elementForRemoval = firstElementInQueue.value;
                firstElementInQueue.value = null;
                countOfElementsInQueue--;
                return elementForRemoval;
            }else {
                return null;
            }
        }
    }

    public void push(E value) {
        checkNotNull(value);
        synchronized (this){
            while (countOfElementsInQueue == capacity){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Node<E> node = new Node<>(value);
            tail.next = node;
            tail = node;
            countOfElementsInQueue++;
            this.notifyAll();
        }
    }

    public int size() {
        return countOfElementsInQueue;
    }

    public String toString(){
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        Node<E> temp = head.next;
        for (int i = 0; i < countOfElementsInQueue; i++) {
            joiner.add(String.valueOf(temp.value));
            temp = temp.next;
        }

        return joiner.toString();
    }

    private static class Node<E>{
        E value;
        Node<E> next;

        private Node(E value){
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }
}
