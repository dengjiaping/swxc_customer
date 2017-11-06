package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/15.
 */
public class SimpleResponse implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;
    /**
     * message : 移除成功
     * responseCode : 1001
     * success : true
     */

    public String message;
    public int responseCode;
    public boolean success;

    public LzyResponse toLzyResponse() {
        LzyResponse lzyResponse = new LzyResponse();
        lzyResponse.code = responseCode;
        lzyResponse.msg = message;
        return lzyResponse;
    }


}
