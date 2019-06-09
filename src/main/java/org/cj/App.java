package org.cj;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        final MyBlokingQueue myBlokingQueue = new MyBlokingQueue(2);
        for (int i = 0; i <2 ; i++) {
            MyTakeThread myTakeThread = new MyTakeThread(myBlokingQueue);
            myTakeThread.start();
        }
        for (int i = 0; i <5 ; i++) {
            MyPutThread myPutThread = new MyPutThread(myBlokingQueue);
            myPutThread.start();
        }
    }
}
