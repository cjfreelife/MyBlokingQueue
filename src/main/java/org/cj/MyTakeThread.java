package org.cj;

public class MyTakeThread extends Thread {
    private MyBlokingQueue queue = null;

    public MyTakeThread(MyBlokingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            queue.take();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
