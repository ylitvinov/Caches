package cache;

/**
 * This factory might help to avoid creating many instances in multi-threading environment.
 *
 * @author Yury Litvinov
 */
public abstract class AbstractCachedFactory<K, V> {
    private final ICache<K, V> cache;

    public AbstractCachedFactory(ICache<K, V> cache) {
        this.cache = cache;
    }

    public V getValue(K key) {
        V value = cache.get(key);
        if (value == null) {
            synchronized (this) {
                value = cache.get(key);
                if (value == null) {
                    value = createValue(key);
                    cache.put(key, value);
                }
            }
        }
        return value;
    }

    protected abstract V createValue(K key);

}
