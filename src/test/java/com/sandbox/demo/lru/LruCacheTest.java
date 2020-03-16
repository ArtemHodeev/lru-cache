package com.sandbox.demo.lru;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;


public class LruCacheTest {
    private LruCache lruCache;

    @Before
    public void createCache() {
        lruCache = LruCache.getInstance();
    }

    @Test
    public void clearCacheTest() {
        List<String> testSample = Arrays.asList( "December", "January", "February", "March", "April");
        for (String i: testSample) {
            lruCache.getCachedValue(i);
        }

        lruCache.clearCache();

        assertEquals(0,lruCache.getAllCachedValues().size());
    }


    @Test
    public void getNonExistedItemLessCacheNotExceededTest() {
        List<String> testSample = Arrays.asList( "December", "January", "February", "March", "April");
        List<String> reversedTestSample = Arrays.asList("April","March", "February", "January","December");

        for (String i: testSample) {
            lruCache.getCachedValue(i);
        }

        List<String> allCachedValues = lruCache.getAllCachedValues();

        assertEquals(allCachedValues.size(),reversedTestSample.size());

        for (int i = 0; i < allCachedValues.size(); i ++) {
            assertEquals(allCachedValues.get(i),reversedTestSample.get(i));
        }
    }

    @Test
    public void getNonExistedItemLessCacheExceededTest() {
        List<String> testSample = Arrays.asList( "December", "January", "February", "March", "April", "June");
        List<String> reversedTestSample = Arrays.asList("June","April","March", "February", "January");

        for (String i: testSample) {
            lruCache.getCachedValue(i);
        }

        List<String> allCachedValues = lruCache.getAllCachedValues();

        assertEquals(allCachedValues.size(),reversedTestSample.size());

        for (int i = 0; i < allCachedValues.size(); i ++) {
            assertEquals(allCachedValues.get(i),reversedTestSample.get(i));
        }
    }

    @Test
    public void getLeastRecentUpdatedCachedItem() {
        List<String> testSample = Arrays.asList( "December", "January", "February", "March", "April", "December");
        List<String> reversedTestSample = Arrays.asList( "December","April","March", "February", "January");

        for (String i: testSample) {
            lruCache.getCachedValue(i);
        }

        List<String> allCachedValues = lruCache.getAllCachedValues();

        assertEquals(allCachedValues.size(),reversedTestSample.size());

        for (int i = 0; i < allCachedValues.size(); i ++) {
            assertEquals(allCachedValues.get(i),reversedTestSample.get(i));
        }
    }

    @Test
    public void getMostRecentUpdatedCachedItem() {
        List<String> testSample = Arrays.asList("December", "January", "February", "March", "April", "April");
        List<String> reversedTestSample = Arrays.asList("April","March", "February", "January", "December");

        for (String i: testSample) {
            lruCache.getCachedValue(i);
        }

        List<String> allCachedValues = lruCache.getAllCachedValues();

        assertEquals(allCachedValues.size(),reversedTestSample.size());

        for (int i = 0; i < allCachedValues.size(); i ++) {
            assertEquals(allCachedValues.get(i),reversedTestSample.get(i));
        }
    }
}
