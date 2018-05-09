package com.example.pcg.mvptest.api;

/**
 * @author Mr_Peng
 * @created at 2017-08-17.
 * @describe: null
 */

public class BaseResult<T> {
    public int code;
    public String message;
    public T data;

}
