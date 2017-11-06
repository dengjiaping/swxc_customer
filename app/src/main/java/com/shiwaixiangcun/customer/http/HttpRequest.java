package com.shiwaixiangcun.customer.http;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.DeleteRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.PutRequest;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.app.App;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.Utils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fyj on 2017/5/23.
 * 网络请求
 */

public class HttpRequest {

    private static String BUG_TAG = "HttpRequest";

    public static HttpExecuteType get(String url) {
        return new HttpExecuteType(RequestType.GET, url);
    }

    public static HttpExecuteType post(String url) {
        return new HttpExecuteType(RequestType.POST, url);
    }

    public static HttpExecuteType put(String url) {
        return new HttpExecuteType(RequestType.PUT, url);
    }

    public static HttpExecuteType delete(String url) {
        return new HttpExecuteType(RequestType.DELETE, url);
    }

    public static void post(String url, Map<String, Object> params, final HttpCallBack httpCallBack) {
//        Context context = App.getContext();
//        String cookie = SharePreference.getStringSpParams(context, Common.ISCOOKIE, Common.SICOOKIE);
//        OkGo.post(url).headers("X-Requested-With", "XMLHttpRequest");
//        OkGo.post(url).headers("User-Agent", "android");
//        OkGo.post(url).headers("Cookie", "uid=" + cookie);


        generateHttpParams(params, OkGo.<String>post(url)).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                httpCallBack.onSuccess(response.body());
            }

        });
    }

    public static void get(String url, Map<String, Object> params, final HttpCallBack httpCallBack) {
//        Context context = App.getContext();
//        String cookie = SharePreference.getStringSpParams(context, Common.ISCOOKIE, Common.SICOOKIE);
//        OkGo.get(url).headers("X-Requested-With", "XMLHttpRequest");
//        OkGo.get(url).headers("User-Agent", "android");
//        OkGo.get(url).headers("Cookie", "uid=" + cookie);


        generateHttpParams(params, OkGo.<String>get(url)).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                httpCallBack.onSuccess(response.body());
            }


        });
    }


    public static void put(String url, Map<String, Object> params, final HttpCallBack httpCallBack) {
//        Context context = App.getContext();
//        String cookie = SharePreference.getStringSpParams(context, Common.ISCOOKIE, Common.SICOOKIE);
//        OkGo.put(url).headers("X-Requested-With", "XMLHttpRequest");
//        OkGo.put(url).headers("User-Agent", "android");
//        OkGo.put(url).headers("Cookie", "uid=" + cookie);
        generateHttpParams(params, OkGo.<String>put(url)).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                httpCallBack.onSuccess(response.body());
            }


        });
    }


    public static void delete(String url, Map<String, Object> params, final HttpCallBack httpCallBack) {
//        Context context = App.getContext();
//        String cookie = SharePreference.getStringSpParams(context, Common.ISCOOKIE, Common.SICOOKIE);
//        OkGo.delete(url).headers("X-Requested-With", "XMLHttpRequest");
//        OkGo.delete(url).headers("User-Agent", "android");
//        OkGo.delete(url).headers("Cookie", "uid =" + cookie);
        generateHttpParams(params, OkGo.<String>delete(url)).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                httpCallBack.onSuccess(response.body());
            }

        });
    }


    private static PostRequest generateHttpParams(Map<String, Object> params, PostRequest postRequest) {

        if (Utils.isNotEmpty(params)) {

            Set<Map.Entry<String, Object>> entries = params.entrySet();
            Iterator<Map.Entry<String, Object>> it = entries.iterator();
            while (it.hasNext()) {

                Map.Entry<String, Object> entry = it.next();
                if (entry.getValue() != null) {

                    if (entry.getValue() instanceof Boolean) {
                        postRequest.params(entry.getKey(), (boolean) entry.getValue());

                    } else if (entry.getValue() instanceof List || entry.getValue() instanceof ArrayList) {
                        postRequest.addFileParams(entry.getKey(), (List<File>) entry.getValue());

                    } else if (entry.getValue() instanceof File) {
                        postRequest.params(entry.getKey(), (File) entry.getValue());

                    } else {
                        postRequest.params(entry.getKey(), entry.getValue().toString());
                    }

                }
            }
        }

        return (PostRequest) postRequest.headers("Authorization", getTokenByApplication());
    }

    private static GetRequest generateHttpParams(Map<String, Object> params, GetRequest getRequest) {

        if (Utils.isNotEmpty(params)) {

            Set<Map.Entry<String, Object>> entries = params.entrySet();
            Iterator<Map.Entry<String, Object>> it = entries.iterator();
            while (it.hasNext()) {

                Map.Entry<String, Object> entry = it.next();
                if (entry.getValue() != null) {

                    if (entry.getValue() instanceof Boolean) {
                        getRequest.params(entry.getKey(), (boolean) entry.getValue());
                    } else {
                        getRequest.params(entry.getKey(), entry.getValue().toString());
                    }

                }
            }
        }

        return (GetRequest) getRequest.headers("Authorization", getTokenByApplication());
    }

    private static DeleteRequest generateHttpParams(Map<String, Object> params, DeleteRequest deleteRequest) {

        if (Utils.isNotEmpty(params)) {

            Set<Map.Entry<String, Object>> entries = params.entrySet();
            Iterator<Map.Entry<String, Object>> it = entries.iterator();
            while (it.hasNext()) {

                Map.Entry<String, Object> entry = it.next();
                if (entry.getValue() != null) {

                    if (entry.getValue() instanceof Boolean) {
                        deleteRequest.params(entry.getKey(), (boolean) entry.getValue());
                    } else {
                        deleteRequest.params(entry.getKey(), entry.getValue().toString());
                    }

                }
            }
        }

        return (DeleteRequest) deleteRequest.headers("Authorization", getTokenByApplication());
    }

    private static PutRequest generateHttpParams(Map<String, Object> params, PutRequest putRequest) {


        if (Utils.isNotEmpty(params)) {

            Set<Map.Entry<String, Object>> entries = params.entrySet();
            Iterator<Map.Entry<String, Object>> it = entries.iterator();
            while (it.hasNext()) {

                Map.Entry<String, Object> entry = it.next();
                if (entry.getValue() != null) {

                    if (entry.getValue() instanceof Boolean) {
                        putRequest.params(entry.getKey(), (boolean) entry.getValue());
                    } else {
                        putRequest.params(entry.getKey(), entry.getValue().toString());
                    }

                }
            }
        }

        return (PutRequest) putRequest.headers("Authorization", getTokenByApplication());
    }


    public static String getTokenByApplication() {
        String login_detail = SharePreference.getStringSpParams(App.getInstance(), Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        if (Utils.isNotEmpty(login_detail)) {
            Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
            }.getType();
            ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
//        if (Utils.isNotEmpty())
            String access_token = "bearer " + responseEntity.getData().getAccess_token();
            return access_token;

        }

        return "";
    }
}
