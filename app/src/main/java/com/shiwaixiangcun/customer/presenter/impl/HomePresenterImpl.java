package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.NoticeBean;
import com.shiwaixiangcun.customer.model.PageBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.WeatherDataBean;
import com.shiwaixiangcun.customer.presenter.IHomePresenter;
import com.shiwaixiangcun.customer.ui.IHomeView;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by fyj on 2017/05/22.
 */
public class HomePresenterImpl implements IHomePresenter {

    private final String BUG_TAG = this.getClass().getSimpleName();
    private IHomeView iHomeView;
    private int siteID;
    public HomePresenterImpl(IHomeView iHomeView) {
        this.iHomeView = iHomeView;

    }


    /**
     * 请求位置2的Banner
     */
    private void requestBannerSecond() {

        OkGo.<String>get(Common.listpage)
                .params("position", GlobalConfig.home_02)
                .params("siteId", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, response.getRawCall().request().toString());
                        iHomeView.setBannerSecond(response.body());
                    }
                });


    }

    @Override
    public void setBannerFirst(Context context) {
        requestBannerFirst();

    }

    @Override
    public void setBannerSecond(Context context) {
        requestBannerSecond();

    }

    @Override
    public void setAnnouncement(Context context) {
        sendAnnouncementListpageHttp();

    }

    @Override
    public void setHeadline(Context context) {
        sendHeadlinesListpageHttp();
    }

    @Override
    public void setInformation(Context context) {
        sendInformationHttp(context);
    }

    @Override
    public void setWeatherHomeClick(Context context, String cityCode) {
//        sendWeatherHttp(context, cityCode);
    }

    /**
     * 请求位置1的banner
     */
    private void requestBannerFirst() {
        OkGo.<String>get(Common.listpage)
                .params("position", GlobalConfig.home_01)
                .params("siteId", GlobalConfig.DEFAULT_SITE_ID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, response.getRawCall().request().toString());
                        iHomeView.setBannerFirst(response.body());
                    }
                });
    }


    // 公告 public
    private void sendAnnouncementListpageHttp() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("page.pn", "1");
        hashMap.put("page.size", "3");
        hashMap.put("position", "COMMUNITY_ANNOUNCEMENT");

        HttpRequest.get(Common.articleListpage, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                // 分页列表
                Type type = new TypeToken<ResponseEntity<PageBean<NoticeBean>>>() {
                }.getType();
                ResponseEntity<PageBean<NoticeBean>> responseEntity_ann = JsonUtil.fromJson(responseJson, type);
                iHomeView.setAnnouncementResult(responseEntity_ann);

            }

            @Override
            public void onFailed(Exception e) {
            }
        });
    }

    // 头条 public
    private void sendHeadlinesListpageHttp() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("page.pn", 1);
        hashMap.put("page.size", 15);
        hashMap.put("position", "COMMUNITY_HEADLINES");

        HttpRequest.get(Common.articleListpage, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                // 分页列表
                Type type = new TypeToken<ResponseEntity<PageBean<NoticeBean>>>() {
                }.getType();
                ResponseEntity<PageBean<NoticeBean>> responseEntity_ann = JsonUtil.fromJson(responseJson, type);
                iHomeView.setHeadlineResult(responseEntity_ann);

            }

            @Override
            public void onFailed(Exception e) {
            }
        });
    }


    //个人信息
    private void sendInformationHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());

        HttpRequest.get(Common.information, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                InformationBean user = new Gson().fromJson(responseJson, InformationBean.class);
                if (user.getResponseCode() == 1001) {
                    iHomeView.setInformationResult(user);
                } else if (user.getResponseCode() == 1018) {
                    RefreshTokenUtil.sendIntDataInvatation(context, refresh_token);
                } else if (user.getResponseCode() == 1019) {
                    LoginOutUtil.sendLoginOutUtil(context);
                }
            }

            @Override
            public void onFailed(Exception e) {
            }
        });
    }


    //天气数据
    private void sendWeatherHttp(final Context context, String cityCode) {
        HashMap<String, Object> hashMap = new HashMap<>();

        HttpRequest.get("http://tqapi.mobile.360.cn/v4/" + cityCode + ".json", hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
//                 JSON.parse(responseJson);
                WeatherDataBean weatherDataBean = new Gson().fromJson(responseJson, WeatherDataBean.class);
                iHomeView.setHomeWeatherClick(weatherDataBean);


            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(context, "网络异常，请稍后再试...", Toast.LENGTH_LONG).show();

            }
        });
    }
}
