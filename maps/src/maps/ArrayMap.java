package maps;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    SimpleEntry<K, V>[] entries;

    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public ArrayMap(int initialCapacity) {
        this.entries = this.createArrayOfEntries(Math.max(1, initialCapacity));
    }

    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @Override
    public V get(Object key) {
        for (SimpleEntry<K, V> entry : entries) {
            if (entry != null && Objects.equals(entry.getKey(), key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] != null && Objects.equals(entries[i].getKey(), key)) {
                V oldValue = entries[i].getValue();
                entries[i].setValue(value);
                return oldValue;
            }
        }

        ensureCapacity();
        entries[size()] = new SimpleEntry<>(key, value);
        return null;
    }

    @Override
    public V remove(Object key) {
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] != null && Objects.equals(entries[i].getKey(), key)) {
                V oldValue = entries[i].getValue();
                entries[i] = entries[size() - 1];
                entries[size() - 1] = null;
                return oldValue;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        Arrays.fill(entries, null);
    }

    @Override
    public boolean containsKey(Object key) {
        for (SimpleEntry<K, V> entry : entries) {
            if (entry != null && Objects.equals(entry.getKey(), key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        int count = 0;
        for (SimpleEntry<K, V> entry : entries) {
            if (entry != null) {
                count++;
            }
        }
        return count;
    }

    private void ensureCapacity() {
        if (size() == entries.length) {
            entries = Arrays.copyOf(entries, entries.length * 2);
        }
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new ArrayMapIterator<>(this.entries);
    }

    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        private int currentIndex = 0;

        public ArrayMapIterator(SimpleEntry<K, V>[] entries) {
            this.entries = entries;
        }

        @Override
        public boolean hasNext() {
            while (currentIndex < entries.length && entries[currentIndex] == null) {
                currentIndex++;
            }
            return currentIndex < entries.length;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return entries[currentIndex++];
        }
    }
}
