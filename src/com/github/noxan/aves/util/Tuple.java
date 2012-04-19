package com.github.noxan.aves.util;

public class Tuple<A, B> {
    private A first;
    private B second;

    public Tuple(A first, B second) {
        setFirst(first);
        setSecond(second);
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public A getFirst() {
        return first;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    public B getSecond() {
        return second;
    }
}
