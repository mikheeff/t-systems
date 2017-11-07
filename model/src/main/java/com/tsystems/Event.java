package com.tsystems;

import java.io.Serializable;

public class Event implements Serializable,Comparable {
    private Object property;

    public Event(){

    }

    public Event(Object property) {
        this.property = property;
    }

    public Object getProperty() {
        return property;
    }

    public void setProperty(Object property) {
        this.property = property;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
