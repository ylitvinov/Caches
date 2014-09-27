package cache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yury Litvinov
 */
public class AbstractCachedFactoryTest {
    private AbstractCachedFactory<String, String> factory;

    @Before
    public void before() throws Exception {
        factory = new CachedFactoryMock();
    }

    @Test
    public void testName() throws Exception {
        String a1 = factory.getValue("a");
        String a2 = factory.getValue("a");
        Assert.assertTrue(a1 == a2);

        String b1 = factory.getValue("b");
        String b2 = factory.getValue("b");
        Assert.assertTrue(b1 == b2);

        String a3 = factory.getValue("a");
        Assert.assertTrue(a3 != a1);
    }

    class CachedFactoryMock extends AbstractCachedFactory<String, String> {
        private final List<String> invocations = new ArrayList<String>();

        public CachedFactoryMock() {
            super(CacheFactory.instance.<String, String>createFIFOCache(1));
        }

        @Override
        protected String createValue(String key) {
            invocations.add(key);
            return key + "!";
        }
    }
}
