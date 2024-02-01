package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {
    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = 1;
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 8;
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 2;

    AbstractIterableMap<K, V>[] chains;

    int size;

    private final double resizingLoadFactorThreshold;
    private final int chainInitialCapacity;

    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        chains = createArrayOfChains(initialChainCount);
        for (int i = 0; i < initialChainCount; i++) {
            chains[i] = new ArrayMap<>(chainInitialCapacity);
        }
        size = 0;
        this.resizingLoadFactorThreshold = resizingLoadFactorThreshold;
        this.chainInitialCapacity = chainInitialCapacity;
    }

    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    @Override
    public V get(Object key) {
        return chains[Math.abs(Objects.hashCode(key) % chains.length)].get(key);
    }

    @Override
    public V put(K key, V value) {
        if ((double) size / chains.length >= resizingLoadFactorThreshold) {
            AbstractIterableMap<K, V>[] arr = createArrayOfChains(size * 2);
            for (int i = 0; i < arr.length; i++) {
                arr[i] = new ArrayMap<>(chainInitialCapacity);
            }

            for (AbstractIterableMap<K, V> chain : chains) {
                for (Entry<K, V> entry : chain) {
                    arr[Math.abs(Objects.hashCode(entry.getKey()) % arr.length)].put(entry.getKey(), entry.getValue());
                }
            }
            chains = arr;
        }

        if (containsKey(key)) {
            V ret = get(key);
            chains[Math.abs(Objects.hashCode(key) % chains.length)].put(key, value);
            return ret;
        }

        chains[Math.abs(Objects.hashCode(key) % chains.length)].put(key, value);
        size++;
        return null;
    }

    @Override
    public V remove(Object key) {
        if (!containsKey(key)) {
            return null;
        }
        size--;
        return chains[Math.abs(Objects.hashCode(key) % chains.length)].remove(key);
    }

    @Override
    public void clear() {
        for (int i = 0; i < chains.length; i++) {
            chains[i] = new ArrayMap<>();
        }
        size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return chains[Math.abs(Objects.hashCode(key) % chains.length)].containsKey(key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ChainedHashMapIterator<>(this.chains);
    }

    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final AbstractIterableMap<K, V>[] chains;
        private int currChain;
        private Iterator<Entry<K, V>> iterator;


        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains) {
            this.chains = chains;
            currChain = 0;
            iterator = chains[0].iterator();

        }

        @Override
        public boolean hasNext() {
            while (currChain < chains.length) {
                if (iterator.hasNext()) {
                    return true;
                }
                iterator = chains[currChain].iterator();
                currChain++;
            }
            return false;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (hasNext()) {
                return iterator.next();
            }
            throw new NoSuchElementException();
        }
    }
}
