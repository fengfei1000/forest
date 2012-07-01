package fengfei.forest.slice.schedule;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Tietang Wang
 * 
 */
public class PausableRealTimeSchedule implements PausableSchedule {

    private static Logger log = LoggerFactory.getLogger(PausableRealTimeSchedule.class);
    private ScheduleConfig config = new ScheduleConfig();
    private long sleeptime = 1;
    private long delayPauseTime = 1 * 10 * 1000;
    //
    private QueueService queueService;
    private ThreadPoolExecutor executor;
    private boolean isPaused = false;
    private Lock pauseLock = new ReentrantLock();
    private Condition unpaused = pauseLock.newCondition();
    private long lastExeTime = 0;
    //
    private AtomicLong ct = new AtomicLong();

    public PausableRealTimeSchedule(String topic) {

        executor = new ThreadPoolExecutor(
            config.corePoolSize,
            config.maximumPoolSize,
            config.keepAliveTime,
            TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());
        sleeptime = config.sleeptime;
        delayPauseTime = config.delayPauseTime;

        lastExeTime = System.currentTimeMillis();
        log.info("Real Time Schedule is started.");
    }

    @Override
    public void run() {
        isPaused = true;
        pause();
        while (true) {
            if (queueService.isQueueEmpty()) {
                // when queue is empty,executing threads will be paused.
                if ((System.currentTimeMillis() - lastExeTime) >= delayPauseTime && !isPaused) {
                    log.debug("Real Time Schedule  is paused.");
                    isPaused = true;
                    pause();
                }
            } else {
                isPaused = false;

                Runnable task = null;
                executor.execute(task);
                lastExeTime = System.currentTimeMillis();
                log.debug("RealTimeSchedule is execute: " + ct.getAndIncrement());
            }
            try {
                Thread.sleep(sleeptime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.livejournal.service.relation.graph.schedule.Schedule#resume()
     */
    @Override
    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    @Override
    public void pause() {
        pauseLock.lock();
        try {
            while (isPaused)
                unpaused.await();
        } catch (InterruptedException ie) {
        } finally {
            pauseLock.unlock();
        }
    }
}
