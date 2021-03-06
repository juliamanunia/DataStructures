package com.miskevich.datastructures.list;

import java.util.Iterator;
import java.util.StringJoiner;

public class LinkedList<E> extends AbstractList<E> {

    private Node<E> head;
    private Node<E> tail;

    public void add(int index, E value) {
        validateElementIndex(index);
        Node<E> node = new Node<>(value);
        if (size == 0) {
            head = tail = node;
        } else if (index == size) {
            tail.next = node;
            node.prev = tail;
            tail = node;
        } else if (index == 0) {
            Node<E> currentNode = node(index);
            head = node;
            node.next = currentNode;
        } else {
            Node<E> currentNode = node(index);
            Node<E> prev = currentNode.prev;
            node.prev = prev;
            node.next = currentNode;
            prev.next = node;
        }

        size++;
    }

    public int indexOf(E value) {
        Node<E> currentNode = head;
        if (value == null) {
            for (int i = 0; i < size; i++) {
                if (currentNode.value == null) {
                    return i;
                }
                currentNode = currentNode.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (value.equals(currentNode.value)) {
                    return i;
                }
                currentNode = currentNode.next;
            }
        }
        return -1;
    }

    public int lastIndexOf(E value) {
        Node<E> currentNode = tail;
        if (value == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (currentNode.value == null) {
                    return i;
                }
                currentNode = currentNode.prev;
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (value.equals(currentNode.value)) {
                    return i;
                }
                currentNode = currentNode.prev;
            }
        }
        return -1;
    }

    public boolean contains(E value) {
        return indexOf(value) >= 0;
    }

    public E set(int index, E value) {
        validatePositionIndex(index);
        Node<E> currentNode;
        Node<E> node = new Node<>(value);
        if (index == size - 1) {
            currentNode = tail;
            tail = node;
            Node<E> prev = currentNode.prev;
            node.prev = prev;
            prev.next = node;
        } else if (index == 0) {
            currentNode = node(index);
            Node<E> next = currentNode.next;
            head = node;
            node.next = next;
            next.prev = node;
        } else {
            currentNode = node(index);
            Node<E> next = currentNode.next;
            Node<E> prev = currentNode.prev;
            node.next = next;
            node.prev = prev;
            next.prev = node;
            prev.next = node;
        }
        return currentNode.value;
    }

    public void clear() {
        Node<E> currentNode = head;
        for (int i = 0; i < size; i++) {
            Node<E> next = currentNode.next;
            currentNode.value = null;
            currentNode.prev = null;
            currentNode.next = null;
            currentNode = next;
        }
        head = tail = null;
        size = 0;
    }

    public E get(int index) {
        validatePositionIndex(index);
        return node(index).value;
    }

    private E remove(Node<E> node) {
        if (node == tail) {
            Node<E> prev = node.prev;
            tail = prev;
            prev.next = null;
        } else if (node == head) {
            Node<E> next = node.next;
            head = next;
            next.prev = null;
        } else {
            Node<E> prev = node.prev;
            Node<E> next = node.next;
            prev.next = next;
            next.prev = prev;
        }
        size--;
        return node.value;
    }

    public E remove(int index) {
        validatePositionIndex(index);
        Node<E> currentNode = node(index);
        return remove(currentNode);
    }

    public Iterator<E> iterator() {
        return new MyIterator();
    }

    private static class Node<E> {
        E value;
        Node<E> next;
        Node<E> prev;

        private Node(E value) {
            this.value = value;
        }

        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        Node<E> temp = head;
        for (int i = 0; i < size; i++) {
            joiner.add(String.valueOf(temp.value));
            temp = temp.next;
        }

        return joiner.toString();
    }

    private Node<E> node(int index) {
        if (index < size / 2) {
            Node<E> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node<E> currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    private class MyIterator implements Iterator<E> {
        private Node<E> currentNode;

        public boolean hasNext() {
            return currentNode != tail;
        }

        public E next() {
            if (currentNode == null) {
                currentNode = head;
            } else {
                currentNode = currentNode.next;
            }
            return currentNode.value;
        }

        public void remove() {
            Node<E> newCurrentNode = currentNode == tail ? tail.prev : currentNode;
            LinkedList.this.remove(currentNode);
            currentNode = newCurrentNode;
        }
    }
}