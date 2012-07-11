package fengfei.forest.slice.schedule;

public interface QueueService {

    boolean isQueueEmpty();

    <T> void offer(T t);

    <T> T poll();

}
