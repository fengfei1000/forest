package fengfei.forest.slice.schedule;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PausableThread implements Runnable {

    private static Logger log = LoggerFactory.getLogger(PausableThread.class);
    private boolean isPaused;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unpaused = pauseLock.newCondition();
    private AtomicLong ct = new AtomicLong();
    private long sleeptime = 500;

    public PausableThread() {
    }

    public PausableThread(ReentrantLock pauseLock) {
        this.pauseLock = pauseLock;
    }

    @Override
    public void run() {
        isPaused = true;
        String id = Thread.currentThread().getId() + "";
        // pause();
        while (true) {
            long i = ct.getAndIncrement();
            if (i % 11 == 0) {
                // when queue is empty,executing threads will be paused.

                System.out.println(id+" , "+new Date().toLocaleString()
                        + " : Real Time Schedule  is paused." + i);
                isPaused = true;
                pause();

            } else {
                isPaused = false;

                System.out.println(id+" , "+new Date().toLocaleString()
                        + " : RealTimeSchedule is execute: " + i);
            }

            try {
                Thread.sleep(sleeptime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    public void pause() {
        pauseLock.lock();
        try {
            // while (isPaused)
            unpaused.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException ie) {
        } finally {
            pauseLock.unlock();
        }
    }

}
