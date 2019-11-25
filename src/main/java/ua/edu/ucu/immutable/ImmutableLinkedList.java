package ua.edu.ucu.immutable;

import java.util.Arrays;

public class ImmutableLinkedList implements ImmutableList {
    private Node head;
    private int size;

    private static class Node {
        private Object element;
        private Node next;

        private Node(Object elem) {
            this.element = elem;
            this.next = null;
        }
    }


    public ImmutableLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public ImmutableLinkedList(Object[] list) {
        checkEmptyArray(list);
        this.head = new Node(list[0]);
        Node curr = head;
        int i = 1;
        while (i < list.length) {
            curr.next = new Node(list[i]);
            curr = curr.next;
            i++;
        }
        this.size = list.length;
    }

    private void checkForIndex(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkEmptyArray(Object[] arr) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("The list is empty!");
        }
    }

    private ImmutableLinkedList linkedCopyOf() {
        if (this.size == 0) {
            return new ImmutableLinkedList();
        }
        return new ImmutableLinkedList(toArray());
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return addAll(this.size, new Object[]{e});
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {

        return addAll(index, new Object[]{e});
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(this.size, c);

    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException();
        }

        ImmutableLinkedList temp = linkedCopyOf();
        Object[] tempArr = temp.toArray();
        Object[] larger = new Object[this.size + c.length];
        /* Copying all the elements from tempArr from 0 to index exclusive */
        System.arraycopy(tempArr, 0, larger, 0, index);
        /* Copying all the elements from c and putting on the index position*/
        System.arraycopy(c, 0, larger, index, c.length);
        /* Copying all the rest elements from tempArr to temp*/
        System.arraycopy(tempArr, index, larger,
                index + c.length, this.size - index);

        return new ImmutableLinkedList(larger);

    }


    @Override
    public Object get(int index) {
        checkForIndex(index);
        Object[] temp = toArray();
        return temp[index];
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        if (isEmpty()) {
            return new ImmutableLinkedList();
        }
        checkForIndex(index);
        Object[] lst = toArray();
        Object[] temp = new Object[this.size - 1];
        System.arraycopy(lst, 0, temp, 0, index);

        System.arraycopy(lst, index + 1, temp,
                index, this.size - 1 - index);

        if (temp.length == 0) {
            return new ImmutableLinkedList();
        }
        return new ImmutableLinkedList(temp);
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        checkForIndex(index);
        Object[] temp = toArray();
        temp[index] = e;
        return new ImmutableLinkedList(temp);
    }

    @Override
    public int indexOf(Object e) {
        if (isEmpty()) {
            return -1;
        }
        int i = 0;
        Node curr = this.head;
        while (i < this.size) {
            if (curr.element.equals(e)) {
                return i;
            }
            curr = curr.next;
            i++;
        }
        return -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Object[] toArray() {
        if (this.size == 0) {
            return new Object[0];
        }
        Object[] array = new Object[this.size];
        int i = 1;
        array[0] = this.head.element;
        Node curr = this.head.next;
        while (i < this.size) {
            array[i] = curr.element;
            curr = curr.next;
            i++;
        }
        return array;
    }

    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }

    public ImmutableLinkedList addFirst(Object e) {
        if (isEmpty()) {
            return (ImmutableLinkedList) add(e);
        }
        return (ImmutableLinkedList) add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {

        return (ImmutableLinkedList) add(e);
    }

    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        return get(this.size - 1);
    }


}

