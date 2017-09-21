package com.shiwaixiangcun.customer.http;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.DeleteRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.PutRequest;
import com.shiwaixiangcun.customer.model.ResponseEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by fyj on 2017/5/23.
 */
public class HttpExecuteType {

    private GetRequest getRequest;
    private PostRequest postRequest;
    private PutRequest putRequest;
    private DeleteRequest deleteRequest;
    private RequestType requestType;

    public HttpExecuteType(RequestType requestType, String url) {
        this.requestType = requestType;
        switch (requestType){
            case GET :
                getRequest = OkGo.<String>get(url).headers("Authorization", HttpRequest.getTokenByApplication());
                break;
            case POST :
                postRequest = OkGo.<String>post(url).headers("Authorization", HttpRequest.getTokenByApplication());
                break;
            case PUT:
                putRequest = OkGo.<String>put(url).headers("Authorization", HttpRequest.getTokenByApplication());
                break;
            case DELETE :
                deleteRequest = OkGo.<String>delete(url).headers("Authorization", HttpRequest.getTokenByApplication());
                break;
        }

    }

    public HttpExecuteType addParams(String key, Object value) {

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
                    public void onSuccess(Response<String> response) {
                        httpCallBack.onSuccess(response.body());
                    }

                });
                break;
            case POST :
                postRequest.execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        httpCallBack.onSuccess(response.body());
                    }

                });
                break;
            case PUT:
                putRequest.execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        httpCallBack.onSuccess(response.body());
                    }

                });
                break;
            case DELETE :
                deleteRequest.execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        httpCallBack.onSuccess(response.body());
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
                    public void onSuccess(Response<ResponseEntity<T>> response) {
                        httpCallBack.onSuccess(response.body());
                    }
//                    @Override
//                    public void onSuccess(ResponseEntity<T> responseEntity, Call call, Response response) {
//                        httpCallBack.onSuccess(responseEntity);
//                    }

//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        httpCallBack.onFailed(e);
//                    }

                });
                break;
            case POST :
                postRequest.execute(new JsonCallBack<ResponseEntity<T>>() {

                    @Override
                    public void onSuccess(Response<ResponseEntity<T>> response) {
                        httpCallBack.onSuccess(response.body());
                    }
                });
                break;
            case PUT:
                putRequest.execute(new JsonCallBack<ResponseEntity<T>>() {
                    @Override
                    public void onSuccess(Response<ResponseEntity<T>> response) {
                        httpCallBack.onSuccess(response.body());
                    }
                });
                break;
            case DELETE :
                deleteRequest.execute(new JsonCallBack<ResponseEntity<T>>() {

                    @Override
                    public void onSuccess(Response<ResponseEntity<T>> response) {
                        httpCallBack.onSuccess(response.body());
                    }
                });
                break;
        }

    }


}
