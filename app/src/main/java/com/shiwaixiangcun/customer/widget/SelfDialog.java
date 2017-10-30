package com.shiwaixiangcun.customer.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.SelfdialogListAdapter;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.HouseSelectListBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 创建自定义的dialog，主要学习其实现原理
 * Created by chengguo on 2016/3/22.
 */
public class SelfDialog extends Dialog {

    private Button yes;//确定按钮
    private Button no;//取消按钮
    private TextView titleTv;//消息标题文本
    private TextView messageTv;//消息提示文本
    private String titleStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本
    //确定文本和取消文本的显示内容
    private String yesStr, noStr;

    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    private onItemclickListener onItemclickListener;
    private ListView lv_select_dialog;
    private Context context;
    private SelfdialogListAdapter selfdialogListAdapter;
    private List<HouseSelectListBean> data;
    private String numberDesc;
    private int layoutId;
    private List<String> list_self = new ArrayList<>();
    private String str_id;

    public SelfDialog(Context context, int layoutId) {
        super(context, R.style.AlertDialogStyle);
        this.layoutId = layoutId;
        this.context = context;
    }

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param str
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            noStr = str;
        }
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = onYesOnclickListener;

    }

    public void setListItemClickListenser(onItemclickListener onItemclickListener) {
        this.onItemclickListener = onItemclickListener;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//            setContentView(R.layout.free_exercise_sure_dialog_layout);
        setContentView(layoutId);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        Log.i("oooiiippp", "hhhaaaa");
        lv_select_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("oooiiippp", "");
                int l1 = (int) l;
                Log.i("oooiiippp", l1 + "----" + l);

                if (data != null) {
                    for (int j = 0; j < data.size(); j++) {
                        if (j == (int) l) {
                            str_id = data.get(j).getId() + "";
                            numberDesc = data.get(j).getNumberDesc();
                        }

                    }
                    if (onItemclickListener != null) {
                        onItemclickListener.onitemClick(str_id, numberDesc);
                    }
                    SharePreference.saveStringToSpParams(context, Common.ISSELECTHOSE, Common.SISELECTHOSE, "" + l1);
                    selfdialogListAdapter.notifyDataSetChanged();
                }

            }
        });


        sendToRentHttp(context);
        for (int i = 0; i < 4; i++) {
            list_self.add("1");
        }

//        selfdialogListAdapter = new SelfdialogListAdapter(list_self);
//        lv_select_dialog.setAdapter(selfdialogListAdapter);


        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (titleStr != null) {
            titleTv.setText(titleStr);
        }
        if (messageStr != null) {
            messageTv.setText(messageStr);
        }
        //如果设置按钮的文字
        if (yesStr != null) {
            yes.setText(yesStr);
        }
        if (noStr != null) {
            no.setText(noStr);
        }
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);
        titleTv = (TextView) findViewById(R.id.title);
        messageTv = (TextView) findViewById(R.id.message);
        lv_select_dialog = (ListView) findViewById(R.id.lv_select_dialog);

    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        titleStr = title;
    }

    public void setColor() {
        yes.setTextColor(Color.parseColor("#fb5f5f"));
    }

    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public void setMessage(String message) {
        messageStr = message;
    }

    //出租房
    private void sendToRentHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        String tokenString = (String) AppSharePreferenceMgr.get(context, GlobalConfig.TOKEN, "");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", tokenString);
        hashMap.put("fields", "id,numberDesc");


        Log.i("dddddd", hashMap.toString() + "-----------" + Common.associatedHouses);
        HttpRequest.get(Common.associatedHouses, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---aaaa", responseJson);
                Type type = new TypeToken<ResponseEntity<List<HouseSelectListBean>>>() {
                }.getType();
                ResponseEntity<List<HouseSelectListBean>> responseEntity = JsonUtil.fromJson(responseJson, type);
                if (null != responseEntity.getData()) {
                    if (responseEntity.getResponseCode() == 1001) {
                        data = responseEntity.getData();
                        selfdialogListAdapter = new SelfdialogListAdapter(data);
                        lv_select_dialog.setAdapter(selfdialogListAdapter);
                    } else if (responseEntity.getResponseCode() == 1018) {
                        RefreshTokenUtil.sendIntDataInvatation(context, refresh_token);
                    } else if (responseEntity.getResponseCode() == 1019) {
                        LoginOutUtil.sendLoginOutUtil(context);
                    }
                }


            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        void onYesClick();
    }

    public interface onNoOnclickListener {
        void onNoClick();
    }


    public interface onItemclickListener {
        void onitemClick(String str_id, String houseName);
    }


}