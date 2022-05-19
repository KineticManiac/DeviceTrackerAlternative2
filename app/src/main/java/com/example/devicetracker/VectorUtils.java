package com.example.devicetracker;

public interface VectorUtils<T> {
    boolean equals(T t1, T t2);
    T add(T t1, T t2);
    T scale(T t, double d);
}
