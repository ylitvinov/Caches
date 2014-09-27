package cache;

/**
 * @author Yury Litvinov
 */
public interface ICache<K, V> {
	V get(K key);

	void put(K key, V value);
}
