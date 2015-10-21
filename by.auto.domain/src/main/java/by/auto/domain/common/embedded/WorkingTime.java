package by.auto.domain.common.embedded;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.Interval;

public class WorkingTime {
    @JsonProperty
    private Interval work;
    @JsonProperty
    private Interval pause;

    public WorkingTime(final Interval work, final Interval pause) {
        this.work = work;
        this.pause = pause;
    }

    public Interval getWork() {
        return work;
    }

    public void setWork(Interval work) {
        this.work = work;
    }

    public Interval getPause() {
        return pause;
    }

    public void setPause(Interval pause) {
        this.pause = pause;
    }
}
