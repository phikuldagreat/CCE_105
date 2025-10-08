package queue;

public interface ADT<E> {
    void enqueue(E x);
    E dequeue();
    E peek();
    boolean isEmpty();
    int size();
    void display();
}
