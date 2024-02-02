package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 8;
   
    SimpleEntry<K, V>[] entries;

    private int size;

    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public ArrayMap(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException();
        }
        this.entries = this.createArrayOfEntries(initialCapacity);
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @Override
    public V get(Object key) {
        for (int i = 0; i < size; i++) {
                if (Objects.equals(entries[i].getKey(), key)) {
                    return entries[i].getValue();
                }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (size == entries.length) {
            SimpleEntry<K, V>[] newEntries = this.createArrayOfEntries(entries.length * 2);
            System.arraycopy(entries, 0, newEntries, 0, entries.length);
            entries = newEntries;
        }

        for (int i = 0; i < size; i++) {
            if (Objects.equals(entries[i].getKey(), key)) {
                V ret = entries[i].getValue();
                entries[i] = new SimpleEntry<>(key, value);
                return ret;
            }
        }
        entries[size] = new SimpleEntry<>(key, value);
        size++;
        return null;
    }

    @Override
    public V remove(Object key) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(entries[i].getKey(), key)) {
                V ret = entries[i].getValue();
                entries[i] = entries[size - 1];
                size--;
                return ret;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        size = 0;
        this.entries = this.createArrayOfEntries(entries.length);
    }

    @Override
    public boolean containsKey(Object key) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(entries[i].getKey(), key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new ArrayMapIterator<>(this.entries, size);
    }


    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        private final int size;
        private int curr;
        // You may add more fields and constructor parameters

        public ArrayMapIterator(SimpleEntry<K, V>[] entries, int size) {
            this.entries = entries;
            this.size = size;
            curr = 0;
        }

        @Override
        public boolean hasNext() {
            return curr < size;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (curr >= size) {
                throw new NoSuchElementException();
            }
            curr++;
            return entries[curr - 1];
        }
    }
}
