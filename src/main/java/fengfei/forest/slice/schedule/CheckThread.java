package fengfei.forest.slice.schedule;

public class CheckThread extends Thread {

    public static void main(String[] args) throws InterruptedException {
        PausableLock lock = new PausableLock();
        PausableThread2 thread1 = new PausableThread2(lock);
        PausableThread2 thread2 = new PausableThread2(lock);
        new Thread(thread1).start();
        new Thread(thread2).start();
        lock.pause();
        // lock.resume();
        // while (true) {
        // // lock.resume();
        // Thread.sleep(3000);
        //
        // }

    }
}