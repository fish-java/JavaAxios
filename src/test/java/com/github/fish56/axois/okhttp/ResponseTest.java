package com.github.fish56.axois.okhttp;

import com.alibaba.fastjson.JSONObject;
import lombok.val;
import okhttp3.*;
import org.junit.Test;

public class ResponseTest {
    private OkHttpClient okHttpClient = new OkHttpClient();

    @Test
    public void res() throws Exception{
        Request request = new Request.Builder()
                .url("https://github.com/fish56")
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();

        System.out.println(JSONObject.toJSONString(response, true));

        Headers headers = response.headers();
        System.out.println("header to JSON");
        System.out.println(JSONObject.toJSONString(headers, true));

        val headersMap = headers.toMultimap();
        System.out.println("headers map");
        System.out.println(JSONObject.toJSONString(headersMap, true));

        for (String key : headersMap.keySet()){
            System.out.println(key);
            // System.out.println(headersMap.get(key).toString());
            for (String value : headersMap.get(key)){
                System.out.println(value);
            }
        }

        System.out.println(response.headers());
        System.out.println(response.body().string());

    }

    @Test
    public void request(){

        RequestBody body = RequestBody.create(MediaType.get("text/plain; charset=utf-8"), "");



        Request.Builder builder = new Request.Builder()
                .url("https://github.com/fish56")
                .method("POST", body);
        Request request = builder.build();
        System.out.println(JSONObject.toJSONString(request));
    }
}
