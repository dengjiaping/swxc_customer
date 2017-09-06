package com.shiwaixiangcun.customer.http;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.okgo.request.DeleteRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.PutRequest;
import com.shiwaixiangcun.customer.response.ResponseEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fyj on 2017/5/23.
 */
public class HttpExcuteType {

    private GetRequest getRequest;
    private PostRequest postRequest;
    private PutRequest putRequest;
    private DeleteRequest deleteRequest;
    private RequestType requestType;

    public HttpExcuteType(RequestType requestType, String url){
        this.requestType = requestType;
        switch (requestType){
            case GET :
                getRequest = OkGo.get(url).headers("Authorization", HttpRequest.getTokenByApplication());
                break;
            case POST :
                postRequest = OkGo.post(url).headers("Authorization", HttpRequest.getTokenByApplication());
                break;
            case PUT:
                putRequest = OkGo.put(url).headers("Authorization", HttpRequest.getTokenByApplication());
                break;
            case DELETE :
                deleteRequest = OkGo.delete(url).headers("Authorization", HttpRequest.getTokenByApplication());
                break;
        }

    }

    public HttpExcuteType addParams(String key, Object value){

        switch (requestType){
            case GET :
                getRequest.params(key, value.toString());
                break;

            case POST :
                if (value instanceof List || value instanceof ArrayList){
                     postRequest.addFileParams(key, (List<File>) value);
                }else if (value instanceof File){
                    postRequest.params(key, (File) value);
                }else {
                    postRequest.params(key, value.toString());
                }
                break;

            case PUT:
                putRequest.params(key, value.toString());
                break;

            case DELETE :
                deleteRequest.params(key, value.toString());
                break;
        }

        return this;
    }


    public void executeJson(final HttpCallBack httpCallBack){

        switch (requestType){
            case GET :
                getRequest.execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        httpCallBack.onSuccess(s);
                    }

//                    @Override
//                    public void onBefore(BaseRequest request) {
//                        super.onBefore(request);
//                        request.headers("Requested", "XMLHttpRequest");
//                        request.headers("X-Requested-With", "android");
//                        request.headers("Cookie", "uid" + "");
//
//                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        httpCallBack.onFailed(e);
                    }
                });
                break;
            case POST :
                postRequest.execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        httpCallBack.onSuccess(s);
                    }

//                    @Override
//                    public void onBefore(BaseRequest request) {
//                        super.onBefore(request);
//                        request.headers("Requested", "XMLHttpRequest");
//                        request.headers("X-Requested-With", "android");
//                        request.headers("Cookie", "uid" + "");
//                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        httpCallBack.onFailed(e);
                    }
                });
                break;
            case PUT:
                putRequest.execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        httpCallBack.onSuccess(s);
                    }

//                    @Override
//                    public void onBefore(BaseRequest request) {
//                        super.onBefore(request);
//                        request.headers("Requested", "XMLHttpRequest");
//                        request.headers("X-Requested-With", "android");
//                        request.headers("Cookie", "uid" + "");
//                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        httpCallBack.onFailed(e);
                    }
                });
                break;
            case DELETE :
                deleteRequest.execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        httpCallBack.onSuccess(s);
                    }

//                    @Override
//                    public void onBefore(BaseRequest request) {
//                        super.onBefore(request);
//                        request.headers("Requested", "XMLHttpRequest");
//                        request.headers("X-Requested-With", "android");
//                        request.headers("Cookie", "uid" + "");
//                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        httpCallBack.onFailed(e);
                    }
                });
                break;
        }

    }


    public <T> void executeEntity(final HttpCallBack<T> httpCallBack){

        switch (requestType){
            case GET :
                getRequest.execute(new JsonCallBack<ResponseEntity<T>>() {
                    @Override
                    public void onSuccess(ResponseEntity<T> responseEntity, Call call, Response response) {
                        httpCallBack.onSuccess(responseEntity);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        httpCallBack.onFailed(e);
                    }

                });
                break;
            case POST :
                postRequest.execute(new JsonCallBack<ResponseEntity<T>>() {

                    @Override
                    public void onSuccess(ResponseEntity<T> responseEntity, Call call, Response response) {
                        httpCallBack.onSuccess(responseEntity);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        httpCallBack.onFailed(e);
                    }
                });
                break;
            case PUT:
                putRequest.execute(new JsonCallBack<ResponseEntity<T>>() {
                    @Override
                    public void onSuccess(ResponseEntity<T> responseEntity, Call call, Response response) {
                        httpCallBack.onSuccess(responseEntity);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        httpCallBack.onFailed(e);
                    }
                });
                break;
            case DELETE :
                deleteRequest.execute(new JsonCallBack<ResponseEntity<T>>() {

                    @Override
                    public void onSuccess(ResponseEntity<T> responseEntity, Call call, Response response) {
                        httpCallBack.onSuccess(responseEntity);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        httpCallBack.onFailed(e);
                    }
                });
                break;
        }

    }

}
