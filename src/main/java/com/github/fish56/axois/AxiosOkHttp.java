package com.github.fish56.axois;

import com.github.fish56.axois.request.RequestEntity;
import com.github.fish56.axois.response.ResponseEntity;
import lombok.NoArgsConstructor;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class AxiosOkHttp extends Axios{
    /**
     * 发起请求的客户端
     */
    private OkHttpClient okHttpClient = new OkHttpClient();

    /**
     * 将自定义实体适配OkHttp的请求实体
     *   搞不懂OkHTTP为什么把这么简单是事情搞这么复杂
     * @param requestEntity
     * @return
     */
    private Request getRequest(RequestEntity requestEntity){
        // 获得请求url
        String url = baseUrl == null ? requestEntity.getFullUrl()
                : baseUrl + requestEntity.getFullUrl();

        // 设置contentType
        //   如果请求实体中没有就尝试在baseHeader中寻找
        //   如果还没有，就是用默认的text/plain; charset=utf-8
        String contentType = requestEntity.getHeaders().get("Content-Type");
        contentType = contentType == null ? commonHeaders.get("Content-Type") : contentType;
        MediaType mediaType = contentType == null
                ? MediaType.get("text/plain; charset=utf-8")
                : MediaType.get(contentType);

        //
        String method = requestEntity.getMethod().toUpperCase();

        // 设置请求体
        String bodyString = requestEntity.getBody();

        RequestBody body = null;

        // okhttp 禁止 GET 和 HEAD的方法发出body，真的服了，多余设置
        if (!method.equals("GET") && !method.equals("HEAD")){
            // 防止post等请求的请求体为null
            bodyString = bodyString == null ? "" : bodyString;
            body = RequestBody.create(mediaType, bodyString);
        }

        // 设置Header
        Headers.Builder headersBuiler = new Headers.Builder();
        for (String headerKey : commonHeaders.keySet()){
            headersBuiler.add(headerKey, commonHeaders.get(headerKey));
        }
        // 实体中的header被允许覆盖默认的通过请求头
        for (String headerKey : requestEntity.getHeaders().keySet()){
            headersBuiler.add(headerKey, requestEntity.getHeaders().get(headerKey));
        }
        Headers headers = headersBuiler.build();

        Request request = new Request.Builder()
                .url(requestEntity.getFullUrl())
                .headers(headers)
                .method(requestEntity.getMethod(), body)
                .build();

        return request;
    }

    /**
     * 将OkHttp响应实体转化为自定义响应实体
     * @param response
     * @return
     */
    private ResponseEntity getResponseEntity(Response response){
        // 获取状态码
        Integer statusCode = response.code();

        // 将响应头部转化成k-v的形式
        Map<String, String> headers = new HashMap<>();
        for (String header : response.headers().toMultimap().keySet()){
            // 原来的设计冗余且无效，响应头部就是字符串挺好的
            //   列表第一个元素就是真正的响应头部的值
            headers.put(header, response.headers().toMultimap().get(header).get(0));
        }

        // 获取响应头部
        String responseBodyString;
        try {
            responseBodyString = response.body().string();
            return new ResponseEntity(statusCode, headers, responseBodyString);
        } catch (IOException e){
            return new ResponseEntity(-1, headers, e.getMessage());
        }
    }

    @Override
    public ResponseEntity request(RequestEntity requestEntity) {
        Call call = okHttpClient.newCall(getRequest(requestEntity));
        ResponseEntity responseEntity = new ResponseEntity();

        Response response;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            // 如果发生异常，把异常信息包装成Http响应报文
            return new ResponseEntity(-1, null, e.getMessage());
        }
        return getResponseEntity(response);
    }

    @Override
    public ResponseEntity get(String url, Map<String, String> params, Map<String, String> headers) {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(url);
        requestEntity.setParams(params);
        requestEntity.setHeaders(headers);
        requestEntity.setMethod("GET");
        return request(requestEntity);
    }

    @Override
    public ResponseEntity get(String url, Map<String, String> params) {
        return get(url, params, null);
    }

    @Override
    public ResponseEntity get(String url) {
        return get(url, null, null);
    }

    @Override
    public ResponseEntity post(String url, Map<String, String> params, Map<String, String> headers, String body) {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(url);
        requestEntity.setParams(params);
        requestEntity.setHeaders(headers);
        requestEntity.setMethod("POST");
        requestEntity.setBody(body);
        return request(requestEntity);
    }

    @Override
    public ResponseEntity post(String url, Map<String, String> params, String body) {
        return post(url, params, null, body);
    }

    @Override
    public ResponseEntity post(String url, String body) {
        return post(url, null, null, body);
    }
}
