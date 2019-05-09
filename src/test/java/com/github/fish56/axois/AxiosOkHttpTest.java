package com.github.fish56.axois;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.axois.request.RequestEntity;
import com.github.fish56.axois.response.ResponseEntity;
import lombok.val;
import org.junit.Test;

import java.util.HashMap;

public class AxiosOkHttpTest {
    @Test
    public void isEnvOk(){}

    @Test
    public void request() {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl("https://github.com/fish56");
        requestEntity.setMethod("GET");
        Axios axios = new AxiosOkHttp();
        ResponseEntity responseEntity = axios.request(requestEntity);
        System.out.println(responseEntity.getBody());
        System.out.println(JSONObject.toJSONString(responseEntity, true));
    }

    @Test
    public void request2() {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl("https://api.github.com/repos/fish56");
        requestEntity.setMethod("GET");
        val header = new HashMap<String, String>();
        header.put("User-Agent", "FishAxiosJava");
        requestEntity.setHeaders(header);
        Axios axios = new AxiosOkHttp();
        ResponseEntity responseEntity = axios.request(requestEntity);
        System.out.println(responseEntity.getBody());
        System.out.println(JSONObject.toJSONString(responseEntity, true));
    }

    @Test
    public void get() {
        Axios axios = new AxiosOkHttp();
        ResponseEntity responseEntity = axios.get("https://github.com/fish56");
        if (responseEntity.is2xx()) {
            System.out.println(JSONObject.toJSONString(responseEntity.getBody(), true));
        } else {
            System.out.println(responseEntity.getBody());
        }
    }
}