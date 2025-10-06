package stack;

import javax.swing.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class StackImp<E> implements ADT<E> {
    private List<E> d = new ArrayList<>();

    @Override
    public void push(E x) {
        d.add(x); 
    }

    @Override
    public E pop() {
        if (isEmpty()) throw new EmptyStackException();
        return d.remove(d.size() - 1); 
    }

    @Override
    public E peek() {
        if (isEmpty()) throw new EmptyStackException();
        return d.get(d.size() - 1); 
    }

    @Override
    public boolean isEmpty() {
        return d.isEmpty();
    }

    @Override
    public int size() {
        return d.size();
    }

    @Override
    public void display() {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "Stack is empty.");
            return;
        }
        StringBuilder sb = new StringBuilder("Stack (top to bottom):\n");
        for (int i = d.size() - 1; i >= 0; i--) {
            sb.append(d.get(i));
            if (i > 0) sb.append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
