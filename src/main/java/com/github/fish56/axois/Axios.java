package com.github.fish56.axois;

import com.github.fish56.axois.request.RequestEntity;
import com.github.fish56.axois.response.ResponseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public abstract class Axios {

    /**
     * 基础的前置API
     */
    @Getter @Setter
    protected String baseUrl;

    /**
     * 为后续请求设置基础的header
     */
    @Getter @Setter
    protected Map<String, String> commonHeaders = new HashMap<>();

    /**
     * 将请求实体发送出去，并返回响应实体
     *   如果有异常，包装成HTTP -1 -2 的形式
     * @param requestEntity
     * @return
     */
    public abstract ResponseEntity request(RequestEntity requestEntity);

    /**
     * GET方法发起请求
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public abstract ResponseEntity get(String url, Map<String, String> params, Map<String, String> headers);
    public abstract ResponseEntity get(String url, Map<String, String> params);
    public abstract ResponseEntity get(String url);

    /**
     * POST方法发起请求
     * @param url
     * @param params
     * @param headers
     * @param body
     * @return
     */
    public abstract ResponseEntity post(String url, Map<String, String> params, Map<String, String> headers, String body);
    public abstract ResponseEntity post(String url, Map<String, String> params, String body);
    public abstract ResponseEntity post(String url, String body);
}
