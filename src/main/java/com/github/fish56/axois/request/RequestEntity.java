package com.github.fish56.axois.request;

import com.github.fish56.axois.util.ParamsUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class RequestEntity {
    private String method;
    private String url;
    // 初始化，使得get方法不会报错
    private Map<String, String> params = new HashMap<String, String>();
    private Map<String, String> headers = new HashMap<String, String>() ;
    private String body;

    // 确保值不是null
    public void setParams(Map<String, String> params) {
        if (params == null) {
            this.params = new HashMap<String, String>() ;
        } else {
            this.params = params;
        }
    }

    public void setHeaders(Map<String, String> headers) {
        if (headers == null) {
            this.headers = new HashMap<String, String>() ;
        } else {
            this.headers = headers;
        }
    }

    public RequestEntity(String method, String url, Map<String, String> params, Map<String, String> headers, String body) {
        this.method = method;
        this.url = url;
        this.params = params;
        this.headers = headers;
        this.body = body;
    }

    public String getFullUrl(){
        if (params != null) {
            return url + "?" + ParamsUtil.getQueryStringFromMap(params);
        }
        return url;
    }

    public void setHeader(String key, String value){
        headers.put(key, value);
    }
    public void setParam(String key, String value){
        params.put(key, value);
    }
    public void setMethod(String method){
        this.method = method.toUpperCase();
    }
}
