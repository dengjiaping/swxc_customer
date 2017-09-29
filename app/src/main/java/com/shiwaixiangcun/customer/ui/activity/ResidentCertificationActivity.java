package com.shiwaixiangcun.customer.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResidentBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.residentLastBean;
import com.shiwaixiangcun.customer.presenter.impl.ResidentCertificationImpl;
import com.shiwaixiangcun.customer.ui.IResifdentView;
import com.shiwaixiangcun.customer.utils.AppManager;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.ResUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.WheelView;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class ResidentCertificationActivity extends Activity implements View.OnClickListener, WheelView.OnValueChangeListener, IResifdentView {

    String totle_totle = "";
    private ChangeLightImageView back_left;
    private Button btn_to_other;
    private WheelView wheelView;
    private WheelView wheelView_t;
    private WheelView wheelView_h;
    private WheelView wheelView_f;
    private List<ResidentBean.DataBean> data;
    private int position_t = 0;
    private int position_o = 0;
    private int position_h = 0;
    private String name_o;
    private String name_t;
    private String name_h;
    private String name_t_o;
    private String name_t_t;
    private String name_t_h;
    private String name_h_h;
    private String name_h_o;
    private String name_h_t;
    private String totle_h;
    private String totle_t;
    private String total_o;
    private String name_f_t;
    private String name_f_h;
    private String name_f_o;
    private String[] str_all_not = {""};
    private List<residentLastBean.DataBean> data_house;
    private int id_new;
    private String totle;
    private String str_houseId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_certification);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        AppManager.getAppManager().addActivity(this);
        ResidentCertificationImpl residentCertification = new ResidentCertificationImpl(this, "");
        residentCertification.setBgaAdpaterAndClick(this);

        layoutView();
        initData();
    }

    private void layoutView() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        btn_to_other = (Button) findViewById(R.id.btn_to_other);

        initView();

    }

    private void initData() {
        back_left.setOnClickListener(this);
        btn_to_other.setOnClickListener(this);

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.btn_to_other:
                Log.i("bpppaaa", "========" + str_houseId);
                if (Utils.isNotEmpty(totle_totle)) {
                    Intent intent = new Intent(this, ToResidentCertificationActivity.class);
                    intent.putExtra("slectHouse", totle_totle);
                    intent.putExtra("houseId", str_houseId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "请选择房产", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    private void initView() {
        wheelView = (WheelView) findViewById(R.id.WheelView_dialog);
        wheelView_t = (WheelView) findViewById(R.id.WheelView_t);
        wheelView_h = (WheelView) findViewById(R.id.WheelView_h);
        wheelView_f = (WheelView) findViewById(R.id.WheelView_f);
        wheelView.setOnValueChangedListener(this);
        wheelView_t.setOnValueChangedListener(this);
        wheelView_h.setOnValueChangedListener(this);
        wheelView_f.setOnValueChangedListener(this);

        wheelView_t.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
        wheelView_h.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
        wheelView.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
        wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));

    }

    private void setSubView(List<ResidentBean.DataBean> data) {
        String[] str_area = new String[data.size()];
        if (data.size() == 0) {
            String[] stringsNew = {""};
            wheelView.refreshByNewDisplayedValues(stringsNew);
            //设置是否可以上下无限滑动
            wheelView.setWrapSelectorWheel(false);
            wheelView.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
            wheelView.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
            wheelView.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
        } else {
            for (int i = 0; i < data.size(); i++) {
                str_area[i] = data.get(i).getName();
            }
//        String[] str_wheel = {"我的","他的","你的"};
            wheelView.refreshByNewDisplayedValues(str_area);
            //设置是否可以上下无限滑动
            wheelView.setWrapSelectorWheel(false);
            wheelView.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
            wheelView.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
            wheelView.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
        }


        List<ResidentBean.DataBean.ChildrenBeanXXX> children_t = data.get(0).getChildren();
        Log.i("oooooooooooee", children_t.size() + "");
        String[] str_t = new String[children_t.size()];
        if (children_t.size() == 0) {
            String[] stringsNew = {""};
            wheelView_t.refreshByNewDisplayedValues(stringsNew);
            //设置是否可以上下无限滑动
            wheelView_t.setWrapSelectorWheel(false);
            wheelView_t.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
            wheelView_t.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
            wheelView_t.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
        } else {
            for (int i = 0; i < children_t.size(); i++) {
                str_t[i] = children_t.get(i).getName();
            }


            wheelView_t.refreshByNewDisplayedValues(str_t);
            //设置是否可以上下无限滑动
            wheelView_t.setWrapSelectorWheel(false);
            wheelView_t.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
            wheelView_t.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
            wheelView_t.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
        }

        if (children_t.size() != 0){
            List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX> children_h = children_t.get(0).getChildren();
            Log.i("oooooooooooee2", children_h.size() + "");
            String[] str_h = new String[children_h.size()];
            if (children_h.size() == 0) {
                String[] stringsNew = {""};
                wheelView_h.refreshByNewDisplayedValues(stringsNew);
                //设置是否可以上下无限滑动
                wheelView_h.setWrapSelectorWheel(false);
                wheelView_h.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_h.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_h.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            } else {
                for (int i = 0; i < children_h.size(); i++) {
                    str_h[i] = children_h.get(i).getName();
                }
                wheelView_h.refreshByNewDisplayedValues(str_h);
                //设置是否可以上下无限滑动
                wheelView_h.setWrapSelectorWheel(false);
                wheelView_h.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_h.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_h.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            }
        }


//        int id_o = data.get(0).getId();
//        int id_t = children_t.get(0).getId();
//        int id = children_h.get(0).getId();
        if (data.get(0).getChildren().size() == 0){
            totle = data.get(0).getName();
            id_new = data.get(0).getId();
        }else {
            if (data.get(0).getChildren().get(0).getChildren().size() == 0){
                totle = data.get(0).getName() + data.get(0).getChildren().get(0).getName();
                id_new = data.get(0).getChildren().get(0).getId();
            }else {
                totle = data.get(0).getName() + data.get(0).getChildren().get(0).getName() + data.get(0).getChildren().get(0).getChildren().get(0).getName();
            }
        }

//        Log.i("hhhhpppop", id_o + "---" + id_t + "---" + id);

//        if (id)
        sendHouseHttp(this, id_new, totle, 100);


    }

    private void setSubOView(List<ResidentBean.DataBean> data, int position) {
        List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX> children_h = null;
        List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX.ChildrenBeanX> children_f = null;
        position_o = position;
        List<ResidentBean.DataBean.ChildrenBeanXXX> children_t = data.get(position).getChildren();
        Log.i("oooooooooooee", children_t.size() + "");
        String[] str_t = new String[children_t.size()];
        if (children_t.size() == 0) {
            String[] stringsNew = {""};
            wheelView_t.refreshByNewDisplayedValues(stringsNew);
            //设置是否可以上下无限滑动
            wheelView_t.setWrapSelectorWheel(false);
            wheelView_t.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
            wheelView_t.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
            wheelView_t.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
        } else {
            for (int i = 0; i < children_t.size(); i++) {
                str_t[i] = children_t.get(i).getName();
            }


            wheelView_t.refreshByNewDisplayedValues(str_t);
            //设置是否可以上下无限滑动
            wheelView_t.setWrapSelectorWheel(false);
            wheelView_t.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
            wheelView_t.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
            wheelView_t.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
        }


        if (children_t != null && children_t.size() != 0) {
            children_h = children_t.get(0).getChildren();
            Log.i("oooooooooooee2", children_h.size() + "");
            String[] str_h = new String[children_h.size()];
            if (children_h.size() == 0) {
                String[] stringsNew = {""};
                wheelView_h.refreshByNewDisplayedValues(stringsNew);
                //设置是否可以上下无限滑动
                wheelView_h.setWrapSelectorWheel(false);
                wheelView_h.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_h.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_h.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            } else {
                for (int i = 0; i < children_h.size(); i++) {
                    str_h[i] = children_h.get(i).getName();
                }
                wheelView_h.refreshByNewDisplayedValues(str_h);
                //设置是否可以上下无限滑动
                wheelView_h.setWrapSelectorWheel(false);
                wheelView_h.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_h.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_h.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            }
        }


        if (children_h != null && children_h.size() != 0) {
            children_f = children_h.get(0).getChildren();
            Log.i("oooooooooooee3", children_f.size() + "");
            String[] str_f = new String[children_f.size()];

            if (children_f.size() == 0) {
                String[] stringsNew = {""};
                wheelView_f.refreshByNewDisplayedValues(stringsNew);
                //设置是否可以上下无限滑动
                wheelView_f.setWrapSelectorWheel(false);
                wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            } else {
                for (int i = 0; i < children_f.size(); i++) {
                    str_f[i] = children_f.get(i).getName();
                }
                wheelView_f.refreshByNewDisplayedValues(str_f);
                //设置是否可以上下无限滑动
                wheelView_f.setWrapSelectorWheel(false);
                wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            }
        }


    }

    private void setSubTView(List<ResidentBean.DataBean> data, int position) {
        List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX> children_h = null;
        position_t = position;
        List<ResidentBean.DataBean.ChildrenBeanXXX> children_t = data.get(position_o).getChildren();

        if (children_t != null && children_t.size() != 0) {
            children_h = children_t.get(0).getChildren();

            Log.i("oooooooooooee2", children_h.size() + "");
            String[] str_h = new String[children_h.size()];
            if (children_h.size() == 0) {
                String[] stringsNew = {""};
                wheelView_h.refreshByNewDisplayedValues(stringsNew);
                //设置是否可以上下无限滑动
                wheelView_h.setWrapSelectorWheel(false);
                wheelView_h.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_h.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_h.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            } else {
                for (int i = 0; i < children_h.size(); i++) {
                    str_h[i] = children_h.get(i).getName();
                }
                wheelView_h.refreshByNewDisplayedValues(str_h);
                //设置是否可以上下无限滑动
                wheelView_h.setWrapSelectorWheel(false);
                wheelView_h.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_h.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_h.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            }
        }


        if (children_h != null && children_h.size() != 0) {
            List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX.ChildrenBeanX> children_f = children_h.get(0).getChildren();
            Log.i("oooooooooooee3", children_f.size() + "");
            String[] str_f = new String[children_f.size()];

            if (children_f.size() == 0) {
                String[] stringsNew = {""};
                wheelView_f.refreshByNewDisplayedValues(stringsNew);
                //设置是否可以上下无限滑动
                wheelView_f.setWrapSelectorWheel(false);
                wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            } else {
                for (int i = 0; i < children_f.size(); i++) {
                    str_f[i] = children_f.get(i).getName();
                }
                wheelView_f.refreshByNewDisplayedValues(str_f);
                //设置是否可以上下无限滑动
                wheelView_f.setWrapSelectorWheel(false);
                wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            }
        }


    }

    private void setSubHView(List<ResidentBean.DataBean> data, int position) {
        position_h = position;
        List<ResidentBean.DataBean.ChildrenBeanXXX> children_t = data.get(position_o).getChildren();
        if (children_t != null && children_t.size() != 0) {
            List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX> children_h = children_t.get(position_t).getChildren();
            if (children_h != null && children_h.size() != 0) {
                List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX.ChildrenBeanX> children_f = children_h.get(0).getChildren();
                Log.i("oooooooooooee3", children_f.size() + "");
                String[] str_f = new String[children_f.size()];

                if (children_f.size() == 0) {
                    String[] stringsNew = {""};
                    wheelView_f.refreshByNewDisplayedValues(stringsNew);
                    //设置是否可以上下无限滑动
                    wheelView_f.setWrapSelectorWheel(false);
                    wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                    wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                    wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
                } else {
                    for (int i = 0; i < children_f.size(); i++) {
                        str_f[i] = children_f.get(i).getName();
                    }
                    wheelView_f.refreshByNewDisplayedValues(str_f);
                    //设置是否可以上下无限滑动
                    wheelView_f.setWrapSelectorWheel(false);
                    wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                    wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                    wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
                }

            }
        }


    }

    @Override
    public void onValueChange(WheelView picker, int oldVal, int newVal) {
        Log.i("kkkkkkkkkkkkk", oldVal + "----" + newVal);
        String[] content = wheelView.getDisplayedValues();
        String[] content_t = wheelView_t.getDisplayedValues();
        String[] content_h = wheelView_h.getDisplayedValues();
        String[] content_f = wheelView_f.getDisplayedValues();

        int id = picker.getId();
        switch (id) {
            case R.id.WheelView_dialog:
                int id_o = 100000;
                Log.i("hhhhhhhhh", "滑动了1");
                setSubOView(data, newVal);


                if (content.length != 0) {
                    Log.i("bbbbbbnn", (newVal - wheelView.getMinValue()) + "---------" + content.length);
                    Log.i("jjjjjjjjjjj1", content[newVal - wheelView.getMinValue()]);
                    name_o = content[newVal - wheelView.getMinValue()];
                }
                if (data.get(newVal).getChildren().size() != 0) {
                    name_t = data.get(newVal).getChildren().get(0).getName();

                    Log.i("miiioooaaa", "---------mmmmkkaaa");
                    if (data.get(newVal).getChildren().get(0).getChildren().size() != 0) {
                        Log.i("ssssssssaaa", "aaaaaaaaaaaa");
                        name_h = data.get(newVal).getChildren().get(0).getChildren().get(0).getName();
                    } else {
                        Log.i("ssssssssaaa", "aaaaasdsadsa");

                    }

                } else {

                }


                total_o = name_o + name_t + name_h;

                Log.i("miiioooaaa", "---------mmmmkk");

                int id_o_o = data.get(newVal).getId();
                if (data.get(newVal).getChildren().size() == 0) {
                    Log.i("miiioooaaa", "第二格为空");
                    wheelView_h.refreshByNewDisplayedValues(str_all_not);
                    id_o = data.get(newVal).getId();
                } else {
                    Log.i("miiioooaaa", "第二格不为空");
                    if (data.get(newVal).getChildren().get(0).getChildren().size() == 0) {
                        Log.i("miiioooaaa", "第三格为空");
                        id_o = data.get(newVal).getChildren().get(0).getId();
                    } else {
                        Log.i("miiioooaaa", "第三格不为空");
                        id_o = data.get(newVal).getChildren().get(0).getChildren().get(0).getId();
                    }
                }

//                int id_o_t = data.get(newVal).getChildren().get(0).getId();
//                int id_o_h = data.get(newVal).getChildren().get(0).getChildren().get(0).getId();
//                Log.i("uuuuuiiiopaa","---------0000");
//                Log.i("uuuuuiiiopaa",id_o_o+"-----"+id_o_t+"--------"+id_o_h+"");

//                sendHouseHttp(this, data.get(newVal).getChildren().get(0).getChildren().get(0).getId(), total_o, 100);
                sendHouseHttp(this, id_o, total_o, 100);


                Log.i("mmmmmmmmmsssss", id_o + "-----" + total_o);


                break;
            case R.id.WheelView_t:
                Log.i("hhhhhhhhh", "滑动了2");
                setSubTView(data, newVal);


                if (content_t.length != 0) {
                    Log.i("bbbbbbnn", (newVal - wheelView_t.getMinValue()) + "---------" + content_t.length);
                    Log.i("jjjjjjjjjjj2", content_t[newVal - wheelView_t.getMinValue()]);
                    name_t_t = content_t[newVal - wheelView_t.getMinValue()];
                }
                if (data != null && data.size() != 0) {
                    name_t_o = data.get(position_o).getName();
                }
                if (data.get(position_o).getChildren().size() != 0) {
                    if (data.get(position_o).getChildren().get(newVal).getChildren().size() != 0) {
                        name_t_h = data.get(position_o).getChildren().get(newVal).getChildren().get(0).getName();
                    }else {
                        name_t_h = "";
                    }


                }
                totle_t = name_t_o + name_t_t + name_t_h;
                if (data.get(position_o).getChildren().size() != 0) {
                    if (data.get(position_o).getChildren().size() != 0) {
                        if (null != data.get(position_o).getChildren().get(0).getChildren()) {
                            if (data.get(position_o).getChildren().get(0).getChildren().size() != 0) {
                                sendHouseHttp(this, data.get(position_o).getChildren().get(newVal).getChildren().get(0).getId(), totle_t, 100);
                            }else {
                                sendHouseHttp(this, data.get(position_o).getChildren().get(newVal).getId(), totle_t, 100);
                            }
                        }
                    }
                }


                break;
            case R.id.WheelView_h:
                Log.i("hhhhhhhhh", "滑动了3");
                setSubHView(data, newVal);


                if (content_h.length != 0) {
                    Log.i("bbbbbbnn", (newVal - wheelView_h.getMinValue()) + "---------" + content_h.length);
                    Log.i("jjjjjjjjjjj3", content_h[newVal - wheelView_h.getMinValue()]);
                    name_h_h = content_h[newVal - wheelView_h.getMinValue()];
                }
                if (data != null && data.size() != 0) {
                    name_h_o = data.get(position_o).getName();
                }
                if (data.get(position_o).getChildren().get(position_t).getChildren() != null && data.get(position_o).getChildren().get(position_t).getChildren().size() != 0) {
                    name_h_t = data.get(position_o).getChildren().get(position_t).getName();
                }
                Log.i("tttttttttrrr", position_o + "+====" + position_t + "------" + newVal);
                totle_h = name_h_o + name_h_t + name_h_h;
                sendHouseHttp(this, data.get(position_o).getChildren().get(position_t).getChildren().get(newVal).getId(), totle_h, 100);

                break;
            case R.id.WheelView_f:
                Log.i("ssssaaaasdsadsd",newVal+"");
//                Log.i("hhhhhhhhh", "滑动了4" + position_h + position_t + position_o);
//                if (data != null && data.size() != 0) {
//                    name_f_o = data.get(position_o).getName();
//                }
//                if (data.get(position_o).getChildren() != null && data.get(position_o).getChildren().size() != 0) {
//                    name_f_t = data.get(position_o).getChildren().get(position_t).getName();
//                }
//                if (data.get(position_o).getChildren().get(position_t).getChildren() != null && data.get(position_o).getChildren().get(position_t).getChildren().size() != 0) {
//                    name_f_h = data.get(position_o).getChildren().get(position_t).getChildren().get(position_h).getName();
//                }
//                String totle_f = name_f_o + name_f_t + name_t_h;
//                sendHouseHttp(this, data.get(position_o).getChildren().get(position_t).getChildren().get(position_h).getId(), totle_f, newVal);

                str_houseId = data_house.get(newVal).getId()+"";

                break;
        }


    }

    @Override
    public void setBgaAdpaterAndClickResult(ResidentBean result) {
        data = result.getData();
        setSubView(data);
    }

    //某单元下得房屋
    private void sendHouseHttp(Context context, int id, final String little_total, final int value) {
        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("regionId", id);
        hashMap.put("fields", "id,number");

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.houseUnit);
        HttpRequest.get(Common.houseUnit, hashMap, new HttpCallBack() {


            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---aaassaa", responseJson);
//                Type type = new TypeToken<ResponseEntity>() {
//                }.getType();
//                ResponseEntity responseEntity = JsonUtil.fromJson(responseJson, ResponseEntity.class);
                residentLastBean residentLastBean = new Gson().fromJson(responseJson, residentLastBean.class);
                data_house = residentLastBean.getData();
                if (data_house.size() != 0){
                    Log.i("oooooooooooee3", data_house.size() + "");
                    String[] str_f = new String[data_house.size()];


                    if (value == 100) {
                        str_houseId = data_house.get(0).getId() + "";
                        if (data_house.size() == 0) {
                            String[] stringsNew = {""};
                            wheelView_f.refreshByNewDisplayedValues(stringsNew);
                            //设置是否可以上下无限滑动
                            wheelView_f.setWrapSelectorWheel(false);
                            wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                            wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                            wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
                        } else {

                            for (int i = 0; i < data_house.size(); i++) {
                                str_f[i] = data_house.get(i).getNumber();
                            }
                            wheelView_f.refreshByNewDisplayedValues(str_f);
                            //设置是否可以上下无限滑动
                            wheelView_f.setWrapSelectorWheel(false);
                            wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                            wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                            wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
                        }
                        totle_totle = little_total + data_house.get(0).getNumber();
                    } else {
                        Log.i("ggggggggtt", value + "");
                        str_houseId = data_house.get(value).getId() + "";
                        totle_totle = little_total + data_house.get(value).getNumber();
                    }

                    Log.i("bbbbbbttt", totle_totle);
                }else {
                    wheelView_f.refreshByNewDisplayedValues(str_all_not);
                }




            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
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
