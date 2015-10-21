package by.auto.domain.common;

import by.auto.domain.common.embedded.WorkingTime;

public class Times {
    private WorkingTime monday;
    private WorkingTime tuesday;
    private WorkingTime wednesday;
    private WorkingTime thursday;
    private WorkingTime friday;
    private WorkingTime saturday;
    private WorkingTime sunday;

    public Times(final WorkingTime monday, final WorkingTime tuesday, final WorkingTime wednesday, final WorkingTime thursday, final WorkingTime friday, final WorkingTime saturday, final WorkingTime sunday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public WorkingTime getMonday() {
        return monday;
    }

    public WorkingTime getTuesday() {
        return tuesday;
    }

    public WorkingTime getWednesday() {
        return wednesday;
    }

    public WorkingTime getThursday() {
        return thursday;
    }

    public WorkingTime getFriday() {
        return friday;
    }

    public WorkingTime getSaturday() {
        return saturday;
    }

    public WorkingTime getSunday() {
        return sunday;
    }
}
