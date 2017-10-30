package com.shiwaixiangcun.customer.http;

import com.shiwaixiangcun.customer.model.ResponseEntity;

/**
 * Created by fyj on 2017/5/24.
 */
public abstract class HttpCallBack<T> implements CallBack{

    /**
     *  成功回调, 返回json
     * @param responseJson
     */
    public void onSuccess(String responseJson){}

    /**
     *  成功回调, 返回实体
     * @param responseEntity
     */
    public void onSuccess(ResponseEntity<T> responseEntity){}
}
package com.shiwaixiangcun.customer.http;

import com.shiwaixiangcun.customer.response.ResponseEntity;

/**
 * Created by fyj on 2017/5/24.
 */
public abstract class HttpCallBack<T> implements CallBack{

    /**
     *  成功回调, 返回json
     * @param responseJson
     */
    public void onSuccess(String responseJson){}

    /**
     *  成功回调, 返回实体
     * @param responseEntity
     */
    public void onSuccess(ResponseEntity<T> responseEntity){}
}
