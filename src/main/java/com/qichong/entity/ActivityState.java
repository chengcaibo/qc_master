package com.qichong.entity;

public class ActivityState {
    Integer id;
    Integer start;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "ActivityState{" +
                "id=" + id +
                ", start=" + start +
                '}';
    }
}
