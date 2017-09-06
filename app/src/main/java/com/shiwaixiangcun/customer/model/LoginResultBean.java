package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/25.
 */

public class LoginResultBean implements Serializable {

    /**
     * access_token : 83ae5a24769f8e0f41dd05d89dc13ac6
     * expires_in : 43200
     * refresh_token : 5dc421d52487978a40fe34a21e394622
     * token_type : Bearer
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String token_type;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}
