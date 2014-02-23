package com.hashtag.phillybusfinder.models;

import org.joda.time.DateTime;

public class BusSchedule {
    private String route;
    private String direction;
    private DateTime time;
    private Integer offset;
    private String warnings;

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
    
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
    
    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public void setTime(String time) {
        this.time = new DateTime(time);
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Route " + route);
        builder.append(", ");
        builder.append(time.toString("h:mm aa"));
        builder.append(", ");
        if (offset > 0) {
            builder.append(offset.toString() + " Min Late");
        } else {
            builder.append("On Time");
        }
        return builder.toString();
    }
}
