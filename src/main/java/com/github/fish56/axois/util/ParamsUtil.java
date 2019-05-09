package com.github.fish56.axois.util;

import java.util.Map;

public class ParamsUtil {
    public static String getQueryStringFromMap(Map<String, String> params){
        StringBuilder qs = new StringBuilder();
        for (String key : params.keySet()){
            if (params.get(key) == null){
                continue;
            }
            qs.append(key);
            qs.append("=");
            qs.append(params.get(key));
            qs.append("&");
        }
        if (qs.length() > 0) {
            qs.deleteCharAt(qs.length() - 1);
        }
        return qs.toString();
    }
}
