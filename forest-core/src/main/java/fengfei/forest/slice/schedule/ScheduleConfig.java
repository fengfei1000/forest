package fengfei.forest.slice.schedule;

import java.io.Serializable;

public class ScheduleConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    /** Main schedule thread sleeptime: xx ms */
    public long sleeptime = 1;
    /** Main schedule thread paused delay time */
    public long delayPauseTime = 1 * 10 * 1000;
    /** Main schedule thread number,default cpus+1 */
    public int mainThreadNums = Runtime.getRuntime().availableProcessors() + 1;
    /** ThreadPoolExecutor min thread size,default 10 */
    public int corePoolSize = mainThreadNums;
    /** ThreadPoolExecutor max thread size ,default 1/2 Integer.MAX_VALUE */
    public int maximumPoolSize = mainThreadNums;
    /** ThreadPoolExecutor keep alive time ,default 60s TimeUnit.SECONDS */
    public long keepAliveTime = 60L;

    public ScheduleConfig() {
    }

    public ScheduleConfig(long sleeptime, long delayPauseTime, int mainThreadNums, int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        super();
        this.sleeptime = sleeptime;
        this.delayPauseTime = delayPauseTime;
        this.mainThreadNums = mainThreadNums;
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    public long getSleeptime() {
        return sleeptime;
    }

    public void setSleeptime(long sleeptime) {
        this.sleeptime = sleeptime;
    }

    public long getDelayPauseTime() {
        return delayPauseTime;
    }

    public void setDelayPauseTime(long delayPauseTime) {
        this.delayPauseTime = delayPauseTime;
    }

    public int getMainThreadNums() {
        return mainThreadNums;
    }

    public void setMainThreadNums(int mainThreadNums) {
        this.mainThreadNums = mainThreadNums;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    @Override
    public String toString() {
        return "ScheduleConfig [sleeptime=" + sleeptime + ", delayPauseTime=" + delayPauseTime + ", mainThreadNums=" + mainThreadNums
                + ", corePoolSize=" + corePoolSize + ", maximumPoolSize=" + maximumPoolSize + ", keepAliveTime=" + keepAliveTime + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + corePoolSize;
        result = prime * result + (int) (delayPauseTime ^ (delayPauseTime >>> 32));
        result = prime * result + (int) (keepAliveTime ^ (keepAliveTime >>> 32));
        result = prime * result + mainThreadNums;
        result = prime * result + maximumPoolSize;
        result = prime * result + (int) (sleeptime ^ (sleeptime >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ScheduleConfig other = (ScheduleConfig) obj;
        if (corePoolSize != other.corePoolSize)
            return false;
        if (delayPauseTime != other.delayPauseTime)
            return false;
        if (keepAliveTime != other.keepAliveTime)
            return false;
        if (mainThreadNums != other.mainThreadNums)
            return false;
        if (maximumPoolSize != other.maximumPoolSize)
            return false;
        if (sleeptime != other.sleeptime)
            return false;
        return true;
    }
}
