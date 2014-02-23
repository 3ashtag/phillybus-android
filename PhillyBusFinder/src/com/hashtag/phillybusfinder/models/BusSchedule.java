package com.hashtag.phillybusfinder.models;

import org.joda.time.DateTime;

public class BusSchedule {
    private String route;
    private DateTime string;
    private Integer offset;
    private String warnings;

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public DateTime getString() {
        return string;
    }

    public void setString(DateTime string) {
        this.string = string;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getWarnings() {
        return warnings;
    }

    public void setWarnings(String warnings) {
        this.warnings = warnings;
    }
}
