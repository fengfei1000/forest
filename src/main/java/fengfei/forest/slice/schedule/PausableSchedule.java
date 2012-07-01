package fengfei.forest.slice.schedule;

public interface PausableSchedule extends Runnable {

    /**
     * if queue has new element,this method will resume the paused threads.
     */
    void resume();

    /**
     * when queue is empty,executing threads will be paused.
     */
    void pause();
}