package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;

/**
 * Created by Administrator on 2017/5/24.
 */
public class TestActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                HttpRequest.get("").E

                HttpRequest.get("http://shiwaixiangcun.cn/mobile/ignore/banner/listpage.json").addParams("position", "OWNER_APP").
                        addParams("fields", "imagePath,link").executeJson(new HttpCallBack() {

                    @Override
                    public void onSuccess(String responseJson) {
                        Log.e("success", responseJson.toString());


//                        ResponseEntity<List<Banner>> responseEntity = JSON.parseObject(responseJson, ResponseEntity.class);
//                        System.out.println(responseEntity.getData().get(0).getImagePath());

//                        列表
//                        Type type = new TypeToken<ResponseEntity<List<Banner>>>() {
//                        }.getType();
//                        ResponseEntity<List<Banner>> responseEntity = JsonUtil.fromJson(responseJson, type);
//
//                        System.out.println(responseEntity.getData().get(0).getImagePath());





                            //单实体
//                        Type type = new TypeToken<ResponseEntity<Banner>>() {
//                        }.getType();
//                        ResponseEntity<Banner> responseEntity = JsonUtil.fromJson(responseJson, ResponseEntity.class);





                        // 分页列表
//                        Type type = new TypeToken<ResponseEntity<PageBean<Banner>>>() {
//                        }.getType();
//                        ResponseEntity<PageBean<Banner>> responseEntity = JsonUtil.fromJson(responseJson, type);

//                        HttpRequest.get

                    }

                    @Override
                    public void onFailed(Exception e) {
                        Log.e("fail", e.toString());
                        System.out.println(e);
                    }
                });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }
}
