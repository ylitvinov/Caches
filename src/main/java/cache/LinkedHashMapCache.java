package cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Yury Litvinov
 */
public class LinkedHashMapCache<K, V> implements ICache<K, V> {
	public static final int INITIAL_CAPACITY = 16;
	public static final float LOAD_FACTOR = 0.75f;

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private final LinkedHashMap<K, V> map;

	LinkedHashMapCache(final int size, boolean accessOrder) {
		if (size <= 0) {
			throw new IllegalArgumentException("Non-positive size: " + size);
		}
		map = new LinkedHashMap<K, V>(INITIAL_CAPACITY, LOAD_FACTOR, accessOrder) {
			@Override
			protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
				return size() > size;
			}
		};
	}

	@Override
	public V get(K key) {
		lock.readLock().lock();
		try {
			return map.get(key);
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public void put(K key, V value) {
		lock.writeLock().lock();
		try {
			map.put(key, value);
		} finally {
			lock.writeLock().unlock();
		}
	}
}
