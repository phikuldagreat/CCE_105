package stack;

public interface ADT<E> {
    void push(E x);
    E pop();
    E peek();
    boolean isEmpty();
    int size();
    void display();
}
