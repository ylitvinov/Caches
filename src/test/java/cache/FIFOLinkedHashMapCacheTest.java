package cache;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yury Litvinov
 */
public class FIFOLinkedHashMapCacheTest {

	@Test(expected = IllegalArgumentException.class)
	public void test_wrongSize() throws Exception {
		CacheFactory.instance.createFIFOCache(0);
	}

	@Test
	public void test_empty() throws Exception {
		//given
		ICache<Integer, Integer> cache = CacheFactory.instance.createFIFOCache(1);

		//then
		Assert.assertNull(cache.get(null));
		Assert.assertNull(cache.get(1));
	}

	@Test
	public void test_duplicate_key() throws Exception {
		//given
		ICache<Integer, Integer> cache = CacheFactory.instance.createFIFOCache(1);

		//when
		cache.put(1, 1);
		cache.put(1, 2);

		//then
		Assert.assertEquals(Integer.valueOf(2), cache.get(1));
	}

	@Test
	public void test_putOnly() throws Exception {
		//given
		ICache<Integer, Integer> cache = CacheFactory.instance.createFIFOCache(2);

		//when
		cache.put(1, 1);
		cache.put(2, 2);
		cache.put(3, 3);

		//then
		Assert.assertEquals(null, cache.get(1));
		Assert.assertEquals(Integer.valueOf(2), cache.get(2));
		Assert.assertEquals(Integer.valueOf(3), cache.get(3));
	}

	@Test
	public void test_orderChange() throws Exception {
		//given
		ICache<Integer, Integer> cache = CacheFactory.instance.createFIFOCache(2);

		//when
		cache.put(1, 1);
		cache.put(2, 2);
		cache.get(1);
		cache.put(3, 3);

		//then
		Assert.assertEquals(null, cache.get(1));
		Assert.assertEquals(Integer.valueOf(2), cache.get(2));
		Assert.assertEquals(Integer.valueOf(3), cache.get(3));
	}
}
