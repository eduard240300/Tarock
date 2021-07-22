package com.Tarock.Common.Domain;

public class Triple<K, V, P> {
    private final K key;
    private V value1;
    private P value2;

    public Triple(K key)
    {
        this.key = key;
    }

    public K getKey() {
        return key;
    }

    public V getValue1() {
        return value1;
    }

    public void setValue1(V value1) {
        this.value1 = value1;
    }

    public P getValue2() {
        return value2;
    }

    public void setValue2(P value2) {
        this.value2 = value2;
    }
}
