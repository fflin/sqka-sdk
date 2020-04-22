package com.hengxin.basic.base;


/**
 * Created by chunyang on 2017/11/2.
 */

public class BaseResult<T> {

    public int error;
    public String message;
    public T data;
    public GotoDate err_data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public GotoDate getErr_data() {
        return err_data;
    }

    public void setErr_data(GotoDate err_data) {
        this.err_data = err_data;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", err_data=" + err_data +
                '}';
    }
}
