package fengfei.forest.slice.schedule;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PausableThread2 implements Runnable {

    private static Logger log = LoggerFactory.getLogger(PausableThread2.class);

    private AtomicLong ct = new AtomicLong();
    private long sleeptime = 500;
    private PausableLock lock = new PausableLock();

    public PausableThread2() {
    }

    public PausableThread2(PausableLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.setPaused(true);
        String id = Thread.currentThread().getId() + "";
        while (true) {
            long i = ct.getAndIncrement();

            if (i % 11 == 0) {
                // when queue is empty,executing threads will be paused.

                System.out.println(id + " , " + new Date().toLocaleString()
                        + " : Real Time Schedule  is paused." + i);

                lock.setPaused(true);
                try {
                    lock.pause(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                lock.setPaused(false);
                System.out.println(id + " , " + new Date().toLocaleString()
                        + " : RealTimeSchedule is execute: " + i);
            }

            try {
                Thread.sleep(sleeptime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
