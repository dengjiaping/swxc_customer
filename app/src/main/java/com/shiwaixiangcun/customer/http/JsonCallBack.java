package com.shiwaixiangcun.customer.http;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;
import com.shiwaixiangcun.customer.response.ResponseEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import okhttp3.Response;

/**
 * Created by fyj on 2017/5/24.
 *  自定义回调类型
 */
public abstract class JsonCallBack<T> extends AbsCallback<T> {


    @Override
    public T convertSuccess(Response response) throws Exception {

        Type genType = getClass().getGenericSuperclass();
        //可能有多个泛型参数
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        //取出第一个泛型参数,此处类型为ResponseEntity<T>
        Type type = params[0];

        if (!(type instanceof ParameterizedType)) {throw new IllegalStateException("没有填写泛型参数");}

        Type rawType = ((ParameterizedType) type).getRawType();

        //这里获取最终内部泛型的类型,第一个()
        Type firstTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];

        T data = null;
        if (rawType == ResponseEntity.class){

            data = JSON.parseObject(response.body().bytes(), type);
            response.close();
        }else {
            response.close();
            throw new IllegalStateException("基类错误无法解析!");
        }

        return data;
    }

}
