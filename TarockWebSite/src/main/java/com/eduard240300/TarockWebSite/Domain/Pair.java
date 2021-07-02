package com.eduard240300.TarockWebSite.Domain;

public class Pair<K, V, V2> {
    private K key;
    private V value;
    private V2 specialValue;

    public Pair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public Pair(V value)
    {
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public V2 getSpecialValue() {
        return specialValue;
    }

    public void setSpecialValue(V2 specialValue) {
        this.specialValue = specialValue;
    }
}
