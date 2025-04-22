package ait.mediation;

import java.util.LinkedList;

public class BlkQueueImpl<T> implements BlkQueue<T> {
    private final LinkedList<T> queue = new LinkedList<>();
    private final int maxSize;

    public BlkQueueImpl(int maxSize) {
        this.maxSize = maxSize;
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void push(T message) {
        while (this.queue.size() != maxSize){
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        this.queue.add(message);
        notify();
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public T pop() {
        while (queue.isEmpty()){
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        throw new UnsupportedOperationException("Not implemented");
    }
}
