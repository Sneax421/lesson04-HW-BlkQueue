package ait.mediation;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlkQueueImpl<T> implements BlkQueue<T> {
    private final LinkedList<T> queue = new LinkedList<>();
    private final int maxSize;
    private final Lock mutex = new ReentrantLock();
    private final Condition producerSenderWaitCondition = mutex.newCondition();
    private final Condition consumerWaitCondition = mutex.newCondition();

    public BlkQueueImpl(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public void push(T message) {
        mutex.lock();
        try {
            while (this.queue.size() >= maxSize){
                try {
                    producerSenderWaitCondition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            this.queue.add(message);
            consumerWaitCondition.signal();
        }finally {
            mutex.unlock();
        }
    }

    @Override
    public T pop() {
        mutex.lock();
        try {
            while (queue.isEmpty()){
                try {
                    consumerWaitCondition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            T message = queue.poll();
            producerSenderWaitCondition.signal();
            return message;
        }finally {
            mutex.unlock();
        }

    }
}
