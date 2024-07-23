package ru.doczilla.utils;

/**
 * Represents a pair of key and value.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}