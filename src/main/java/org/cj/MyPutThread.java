package org.cj;

public class MyPutThread extends Thread {
    private MyBlokingQueue queue = null;

    public MyPutThread(MyBlokingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            String name = Thread.currentThread().getName();
            queue.put(name);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
