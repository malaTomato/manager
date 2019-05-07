package com.neptune.manager.common;

import lombok.Data;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author xiongwu
 **/
@Data
public class Result<T> {

    private Integer code;
    private String msg;


    private T data;


    /**
     * 判断是否为空
     * @return
     */
    public boolean isPresent(){
        return Objects.isNull(data);
    }

    public void ifPresent(Consumer<? super T> consumer){
        if(data == null){
            throw new RuntimeException("值为空");
        }
        consumer.accept(data);
    }

    public T get(){
        if(data == null){
            throw new RuntimeException("值为空");
        }
        return data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }
}
