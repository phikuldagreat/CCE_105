package queue;

import javax.swing.*;
import java.util.*;
import java.util.function.Predicate;

public class QueueImp<E> implements ADT<E> {
    private List<E> d = new ArrayList<>();

    @Override
    public void enqueue(E x) {
        d.add(x); 
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        return d.remove(0); 
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        return d.get(0); 
    }

    @Override
    public boolean isEmpty() {
        return d.isEmpty();
    }

    @Override
    public int size() {
        return d.size();
    }

     //Show contents in a dialog
    @Override
    public void display() {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "Queue is empty.");
            return;
        }
        StringBuilder sb = new StringBuilder("Queue (front → back):\n");
        for (int i = 0; i < d.size(); i++) {
            sb.append(d.get(i));
            if (i < d.size() - 1) sb.append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public List<E> toList() {
        return new ArrayList<>(d);
    }

    public boolean removeIf(Predicate<? super E> pred) {
        return d.removeIf(pred);
    }
}
