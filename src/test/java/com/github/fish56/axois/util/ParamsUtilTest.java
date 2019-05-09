package com.github.fish56.axois.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ParamsUtilTest {

    @Test
    public void getQueryStringFromMap() {
        Map<String, String> stringMap = new HashMap<String, String>();
        System.out.println(ParamsUtil.getQueryStringFromMap(stringMap));

        stringMap.put("key1", "value1");
        System.out.println(ParamsUtil.getQueryStringFromMap(stringMap));

        stringMap.put("key2", "value2");
        System.out.println(ParamsUtil.getQueryStringFromMap(stringMap));
    }
}