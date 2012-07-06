package fengfei.forest.slice.schedule;

public class PausableLock {

    private boolean isPaused;

    public void resume() {
        synchronized (this) {
            isPaused = false;
            notifyAll();
        }
    }

    public void pause() throws InterruptedException {
        synchronized (this) {
            if (isPaused) {
                wait();
            }
        }
    }

    public void pause(long timeout) throws InterruptedException {
        synchronized (this) {
            if (isPaused) {
                wait(timeout);
            }
        }
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }
}
