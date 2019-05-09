package com.github.fish56.axois.response;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class ResponseEntity {
    // 这里我希望Axios的实现类可以修改statusCode，但是其他类不行
    //   Java中好像不支持这种语法，C++中的友元函数可以？
    @Getter
    private Integer statusCode;
    @Getter
    private Map<String, String> headers = new HashMap<>();
    @Getter
    private String body;

    public ResponseEntity(Integer statusCode, Map<String, String> headers, String body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public <T> T json(Class<T> clazz){
        return JSONObject.parseObject(body, clazz);
    }

    /**
     * IO 异常之类的状态码，不在HTTP协议规范中
     * 这种处理可以避免繁琐的异常处理
     * @return
     */
    public boolean is0xx(){
        return  statusCode >= 0 && statusCode <= 99;
    }
    public boolean is1xx(){
        return  statusCode >= 100 && statusCode <= 199;
    }
    public boolean is2xx(){
        return  statusCode >= 200 && statusCode <= 299;
    }
    public boolean is3xx(){
        return  statusCode >= 300 && statusCode <= 399;
    }
    public boolean is4xx(){
        return  statusCode >= 400 && statusCode <= 499;
    }
    public boolean is5xx(){
        return  statusCode >= 500 && statusCode <= 599;
    }
}
