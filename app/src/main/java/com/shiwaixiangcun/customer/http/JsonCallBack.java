package com.shiwaixiangcun.customer.http;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by fyj on 2017/5/24.
 *  自定义回调类型
 */
public abstract class JsonCallBack<T> extends AbsCallback<T> {


    private Type type;
    private Class<T> clazz;

    public JsonCallBack() {
    }

    public JsonCallBack(Type type) {
        this.type = type;
    }

    public JsonCallBack(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
        request.headers("header1", "HeaderValue1");//

    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {

        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用

        //详细自定义的原理和文档，看这里： https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback

        if (type == null) {
            if (clazz == null) {
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                JsonConvert<T> convert = new JsonConvert<>(clazz);
                return convert.convertResponse(response);
            }
        }

        JsonConvert<T> convert = new JsonConvert<>(type);
        return convert.convertResponse(response);
    }
}
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
