package cache;

/**
 * @author Yury Litvinov
 */
public class CacheFactory {
	public static CacheFactory instance = new CacheFactory();

	public <K, V> ICache<K, V> createLRUCache(int size) {
		return new LinkedHashMapCache<K, V>(size, true);
	}

	public <K, V> ICache<K, V> createFIFOCache(int size) {
		return new LinkedHashMapCache<K, V>(size, false);
	}
}
