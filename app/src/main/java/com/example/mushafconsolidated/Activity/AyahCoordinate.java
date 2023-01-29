package com.example.mushafconsolidated.Activity;

public class AyahCoordinate {

    Integer start;
    Integer end;

    Integer passage;

    public Integer getStart() {
        return start;
    }

    public AyahCoordinate() {
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public AyahCoordinate(Integer start, Integer end, Integer passage) {
        this.start = start;
        this.end = end;
        this.passage = passage;
    }

    public Integer getPassage() {
        return passage;
    }

    public void setPassage(Integer passage) {
        this.passage = passage;
    }

    public AyahCoordinate(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }
}
