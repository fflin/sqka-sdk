package com.hengxin.basic.api;


import com.hengxin.basic.base.BaseResult;
import com.hengxin.basic.base.GotoDate;

public class ApiException extends RuntimeException {

//    static final String regex = ",";

    public String serverMessage;
    public int error;
    public GotoDate gotoDate;

    public ApiException(String mess) {
        super(mess);
    }

    public ApiException(BaseResult base) {
        super(base.message);
        this.error = base.error;
        this.serverMessage = base.message;
        this.gotoDate = base.err_data;
    }


}