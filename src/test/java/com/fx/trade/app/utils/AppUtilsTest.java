package com.fx.trade.app.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class AppUtilsTest {

    @Test
    public void splitQueryParameters() {
        String s = "http://localhost:8000/rates/latest?userId";
        Map<String, List<String>> stringListMap = AppUtils.splitQueryParameters(s + "=123");
        Assert.assertNotNull(stringListMap);
        Assert.assertNotNull(stringListMap.get(s));
        Assert.assertEquals("123",stringListMap.get(s).get(0));

        Map<String, List<String>> stringListMap1 = AppUtils.splitQueryParameters("");
        Assert.assertNotNull(stringListMap1);
        Assert.assertTrue(stringListMap1.isEmpty());
    }

    @Test
    public void notNullOrEmpty() {
        Assert.assertTrue(AppUtils.notNullOrEmpty("A"));
        Assert.assertFalse(AppUtils.notNullOrEmpty(""));
    }
}