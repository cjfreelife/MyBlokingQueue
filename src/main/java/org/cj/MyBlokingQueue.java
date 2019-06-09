package org.cj;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 *
 * 请结合ReentrantLock、Condition实现一个简单的阻塞队列，阻塞队列提供两个方法，一个是put、一个是take
 * 当队列为空时，请求take会被阻塞，直到队列不为空
 * 当队列满了以后，存储元素的线程需要被阻塞直到队列可以添加数据
 * */
public class MyBlokingQueue {
    private LinkedList linkedList = new LinkedList();
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();
    private volatile AtomicInteger i = new AtomicInteger(0);
    private int maxSize;

    public MyBlokingQueue(int initSize) {
        this.maxSize = initSize;
    }

    public void put(String str) {
        reentrantLock.lock();
        try {
            if (linkedList.size() == maxSize) {
                System.out.println("达到最大容量，当前线程阻塞");
                //添加达到最大容量时，通过condition将当前线程阻塞。
                condition.await();
            }
            System.out.println("添加元素" + str);
            linkedList.add(str);
            i.addAndGet(1);
            //添加一个元素后，去唤醒其他被阻塞的线程，唤醒获取线程。
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public String take() {
        String result = "";
        try {
            reentrantLock.lock();
            if (i.get() == 0) {
                //当前链表中不存在元素，通过condition将当前线程阻塞。
                System.out.println("当前链表中不存在元素，当前线程阻塞");
                condition.await();
            }
            //获取线程被唤醒后，从链表中取出添加的元素，并唤醒其他被阻塞的线程，唤醒添加线程再次进行添加。
            String pop = (String) linkedList.pop();
            System.out.println("释放元素" + pop);
            i.decrementAndGet();
            condition.signal();
            result = pop + Thread.currentThread().getName();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
        return result;
    }
}
