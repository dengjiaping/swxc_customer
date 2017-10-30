package com.shiwaixiangcun.customer.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.HouseSelectListBean;
import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.impl.HousetoRentImpl;
import com.shiwaixiangcun.customer.ui.IHouseToRentView;
import com.shiwaixiangcun.customer.utils.AppManager;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.SelfDialog;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ItoRentActivity extends AppCompatActivity implements View.OnClickListener, IHouseToRentView {

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    finish();
                }
            });
        }
    };
    private ChangeLightImageView back_left;
    private RelativeLayout rl_select_house_to;
    private Button btn_submit_torent;
    private RelativeLayout rl_success_submit;
    private Button btn_ok;
    private String str_select_id = "";
    private EditText et_rent_money;
    private EditText et_post_content;
    private int MIN_MARK = 0;
    private int MAX_MARK = 1000000;
    private TextView tv_et_tv;
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("TAG", "[TextWatcher][onTextChanged]" + s);
            if (Utils.isNotEmpty(s.toString())) {
                tv_et_tv.setText(s.toString() + " (元/月)");
            } else {
                tv_et_tv.setText("");
            }


            if (s != null && !s.equals("")) {
                if (MIN_MARK != -1 && MAX_MARK != -1) {
                    int markVal = 0;
                    try {
                        markVal = Integer.parseInt(s.toString());
                    } catch (NumberFormatException e) {
                        markVal = 0;
                    }
                    if (markVal > MAX_MARK) {
                        Toast.makeText(getBaseContext(), "不能超过"+MAX_MARK, Toast.LENGTH_SHORT).show();
                        tv_et_tv.setText(String.valueOf(MAX_MARK));
                        et_rent_money.setText(String.valueOf(MAX_MARK));
                    }
                    return;
                }
            }


        }

    };
    private TextView tv_ok_select_house;
    private List<HouseSelectListBean> result_house;
    private Intent intent;
    private TextView tv_page_name;
    private String houseName_select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ito_rent);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        AppManager.getAppManager().addActivity(this);
        layoutView();
        initData();
    }

    private void layoutView() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        rl_select_house_to = (RelativeLayout) findViewById(R.id.rl_select_house_to);
        btn_submit_torent = (Button) findViewById(R.id.btn_submit_torent);
        rl_success_submit = (RelativeLayout) findViewById(R.id.rl_success_submit);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        et_rent_money = (EditText) findViewById(R.id.et_rent_money);
        et_post_content = (EditText) findViewById(R.id.et_post_content);
        tv_et_tv = (TextView) findViewById(R.id.tv_et_tv);
        tv_ok_select_house = (TextView) findViewById(R.id.tv_ok_select_house);


        et_rent_money.addTextChangedListener(watcher);


    }

    private void initData() {
        tv_page_name.setText("我要出租");
        HousetoRentImpl housetoRent_select = new HousetoRentImpl(this, "", "");
        housetoRent_select.setHouseList(this);
//        housetoRent_select.setInformation(this);

        back_left.setOnClickListener(this);
        rl_select_house_to.setOnClickListener(this);
        btn_submit_torent.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.rl_select_house_to:
                SharePreference.saveStringToSpParams(this, Common.ISSELECTHOSE, Common.SISELECTHOSE, "");
                HousetoRentImpl housetoRent_select = new HousetoRentImpl(this, "", "");
                housetoRent_select.setHouseList(this);
                showLoginoutDialog();
                break;
            case R.id.btn_submit_torent:
                if (result_house != null && result_house.size() != 0) {
                    if (result_house.size() == 1) {
//                        if (!Utils.isNotEmpty(result_house.get(0).getId()+"")){
//                            Toast.makeText(this,"请选择房产",Toast.LENGTH_LONG).show();
//                            return;
//                        }else if (!Utils.isNotEmpty(et_rent_money.getText().toString().trim())){
//                            Toast.makeText(this,"请填写您的预期出售价格",Toast.LENGTH_LONG).show();
//                            return;
//                        }else if (!Utils.isNotEmpty(et_post_content.getText().toString().trim())){
//                            Toast.makeText(this,"请填写你对租客的要求",Toast.LENGTH_LONG).show();
//                            return;
//                        }
                        if (Utils.isNotEmpty(tv_ok_select_house.getText().toString().trim())) {
                            String s_total = "房号:" + tv_ok_select_house.getText().toString().trim() + "\n" + "预期价格:" + et_rent_money.getText().toString().trim() + "\n" + "其他要求:" + et_post_content.getText().toString().trim();
                            HousetoRentImpl housetoRent = new HousetoRentImpl(this, "" + result_house.get(0).getId(), s_total);
                            housetoRent.setBgaAdpaterAndClick(this);
                        } else {
                            Toast.makeText(this, "房屋信息不能为空", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        String s_total = "房号:" + houseName_select + "\n" + "预期价格:" + et_rent_money.getText().toString().trim() + "\n" + "其他要求:" + et_post_content.getText().toString().trim();
                        HousetoRentImpl housetoRent = new HousetoRentImpl(this, str_select_id, s_total);
                        housetoRent.setBgaAdpaterAndClick(this);
                    }

                }

                break;
            case R.id.btn_ok:
                finish();
                break;
        }
    }

    private void showLoginoutDialog() {
        final SelfDialog selfLoginoutDialog = new SelfDialog(this, R.layout.free_exercise_sure_dialog_layout);
        selfLoginoutDialog.setTitle("选择");
        selfLoginoutDialog.setMessage("退出账号后，您的信息将不会失去。但您将收不到关于您的任何通知信息。");
//        selfLoginoutDialog.setColor();
        selfLoginoutDialog.setYesOnclickListener("确认退出", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                selfLoginoutDialog.dismiss();
            }
        });
        selfLoginoutDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfLoginoutDialog.dismiss();
                SharePreference.saveStringToSpParams(ItoRentActivity.this, Common.ISSELECTHOSE, Common.SISELECTHOSE, "");
            }
        });
        selfLoginoutDialog.setListItemClickListenser(new SelfDialog.onItemclickListener() {
            @Override
            public void onitemClick(String str_id, String houseName) {
                Log.i("qqqqqqqqqqqqqqqq", str_id + "----" + houseName);
                tv_ok_select_house.setVisibility(View.VISIBLE);
                rl_select_house_to.setVisibility(View.GONE);
                tv_ok_select_house.setText(houseName);
                str_select_id = str_id;
                houseName_select = houseName;
                selfLoginoutDialog.dismiss();
            }
        });
        selfLoginoutDialog.show();
    }


    @Override
    public void setBgaAdpaterAndClickResult(ResponseEntity result) {
        if (result.getResponseCode() == 1001) {
            rl_success_submit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setHouseListResult(List<HouseSelectListBean> result) {
        result_house = result;
        if (result != null && result.size() != 0) {
            if (result.size() == 1) {
                tv_ok_select_house.setVisibility(View.VISIBLE);
                rl_select_house_to.setVisibility(View.GONE);
                tv_ok_select_house.setText(result.get(0).getNumberDesc());
            } else {
                tv_ok_select_house.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showLoginoutDialog();
                    }
                });
            }
        }
    }

    ///////
    @Override
    public void setInformationResult(InformationBean result) {
//        Log.i("fffffffffaa",result.getData().isPropertyAuth()+"");
//        if (result != null) {
//            boolean propertyAuth = result.getData().isPropertyAuth();
//            if (propertyAuth) {
//                intent = new Intent(this, ResidentCertificationActivity.class);
//
//                startActivityForResult(intent, 1009);
//            }
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1009) {
            if (null != data) {
                timer.schedule(task, 500, 1000);

            }
        }
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
package com.shiwaixiangcun.customer.ui.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.model.HouseSelectListBean;
import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.presenter.impl.HousetoRentImpl;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppManager;
import com.shiwaixiangcun.customer.utils.ShareUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.SelfDialog;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.ui.IHouseToRentView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ItoRentActivity extends AppCompatActivity implements View.OnClickListener, IHouseToRentView {

    private ChangeLightImageView back_left;
    private RelativeLayout rl_select_house_to;
    private Button btn_submit_torent;
    private RelativeLayout rl_success_submit;
    private Button btn_ok;
    private String str_select_id = "";
    private EditText et_rent_money;
    private EditText et_post_content;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    finish();
                }
            });
        }
    };

    private int MIN_MARK = 0;
    private int MAX_MARK = 1000000;
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("TAG", "[TextWatcher][onTextChanged]" + s);
            if (Utils.isNotEmpty(s.toString())) {
                tv_et_tv.setText(s.toString() + " (元/月)");
            } else {
                tv_et_tv.setText("");
            }


            if (s != null && !s.equals("")) {
                if (MIN_MARK != -1 && MAX_MARK != -1) {
                    int markVal = 0;
                    try {
                        markVal = Integer.parseInt(s.toString());
                    } catch (NumberFormatException e) {
                        markVal = 0;
                    }
                    if (markVal > MAX_MARK) {
                        Toast.makeText(getBaseContext(), "不能超过"+MAX_MARK, Toast.LENGTH_SHORT).show();
                        tv_et_tv.setText(String.valueOf(MAX_MARK));
                        et_rent_money.setText(String.valueOf(MAX_MARK));
                    }
                    return;
                }
            }


        }

    };
    private TextView tv_et_tv;
    private TextView tv_ok_select_house;
    private List<HouseSelectListBean> result_house;
    private Intent intent;
    private TextView tv_page_name;
    private String houseName_select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ito_rent);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        AppManager.getAppManager().addActivity(this);
        layoutView();
        initData();
    }

    private void layoutView() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        rl_select_house_to = (RelativeLayout) findViewById(R.id.rl_select_house_to);
        btn_submit_torent = (Button) findViewById(R.id.btn_submit_torent);
        rl_success_submit = (RelativeLayout) findViewById(R.id.rl_success_submit);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        et_rent_money = (EditText) findViewById(R.id.et_rent_money);
        et_post_content = (EditText) findViewById(R.id.et_post_content);
        tv_et_tv = (TextView) findViewById(R.id.tv_et_tv);
        tv_ok_select_house = (TextView) findViewById(R.id.tv_ok_select_house);


        et_rent_money.addTextChangedListener(watcher);


    }

    private void initData() {
        tv_page_name.setText("我要出租");
        HousetoRentImpl housetoRent_select = new HousetoRentImpl(this, "", "");
        housetoRent_select.setHouseList(this);
//        housetoRent_select.setInformation(this);

        back_left.setOnClickListener(this);
        rl_select_house_to.setOnClickListener(this);
        btn_submit_torent.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.rl_select_house_to:
                ShareUtil.saveStringToSpParams(this, Common.ISSELECTHOSE, Common.SISELECTHOSE, "");
                HousetoRentImpl housetoRent_select = new HousetoRentImpl(this, "", "");
                housetoRent_select.setHouseList(this);
                showLoginoutDialog();
                break;
            case R.id.btn_submit_torent:
                if (result_house != null && result_house.size() != 0) {
                    if (result_house.size() == 1) {
//                        if (!Utils.isNotEmpty(result_house.get(0).getId()+"")){
//                            Toast.makeText(this,"请选择房产",Toast.LENGTH_LONG).show();
//                            return;
//                        }else if (!Utils.isNotEmpty(et_rent_money.getText().toString().trim())){
//                            Toast.makeText(this,"请填写您的预期出售价格",Toast.LENGTH_LONG).show();
//                            return;
//                        }else if (!Utils.isNotEmpty(et_post_content.getText().toString().trim())){
//                            Toast.makeText(this,"请填写你对租客的要求",Toast.LENGTH_LONG).show();
//                            return;
//                        }
                        if (Utils.isNotEmpty(tv_ok_select_house.getText().toString().trim())) {
                            String s_total = "房号:" + tv_ok_select_house.getText().toString().trim() + "\n" + "预期价格:" + et_rent_money.getText().toString().trim() + "\n" + "其他要求:" + et_post_content.getText().toString().trim();
                            HousetoRentImpl housetoRent = new HousetoRentImpl(this, "" + result_house.get(0).getId(), s_total);
                            housetoRent.setBgaAdpaterAndClick(this);
                        } else {
                            Toast.makeText(this, "房屋信息不能为空", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        String s_total = "房号:" + houseName_select + "\n" + "预期价格:" + et_rent_money.getText().toString().trim() + "\n" + "其他要求:" + et_post_content.getText().toString().trim();
                        HousetoRentImpl housetoRent = new HousetoRentImpl(this, str_select_id, s_total);
                        housetoRent.setBgaAdpaterAndClick(this);
                    }

                }

                break;
            case R.id.btn_ok:
                finish();
                break;
        }
    }

    private void showLoginoutDialog() {
        final SelfDialog selfLoginoutDialog = new SelfDialog(this, R.layout.free_exercise_sure_dialog_layout);
        selfLoginoutDialog.setTitle("选择");
        selfLoginoutDialog.setMessage("退出账号后，您的信息将不会失去。但您将收不到关于您的任何通知信息。");
//        selfLoginoutDialog.setColor();
        selfLoginoutDialog.setYesOnclickListener("确认退出", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                selfLoginoutDialog.dismiss();
            }
        });
        selfLoginoutDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfLoginoutDialog.dismiss();
                ShareUtil.saveStringToSpParams(ItoRentActivity.this, Common.ISSELECTHOSE, Common.SISELECTHOSE, "");
            }
        });
        selfLoginoutDialog.setListItemClickListenser(new SelfDialog.onItemclickListener() {
            @Override
            public void onitemClick(String str_id, String houseName) {
                Log.i("qqqqqqqqqqqqqqqq", str_id + "----" + houseName);
                tv_ok_select_house.setVisibility(View.VISIBLE);
                rl_select_house_to.setVisibility(View.GONE);
                tv_ok_select_house.setText(houseName);
                str_select_id = str_id;
                houseName_select = houseName;
                selfLoginoutDialog.dismiss();
            }
        });
        selfLoginoutDialog.show();
    }


    @Override
    public void setBgaAdpaterAndClickResult(ResponseEntity result) {
        if (result.getResponseCode() == 1001) {
            rl_success_submit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setHouseListResult(List<HouseSelectListBean> result) {
        result_house = result;
        if (result != null && result.size() != 0) {
            if (result.size() == 1) {
                tv_ok_select_house.setVisibility(View.VISIBLE);
                rl_select_house_to.setVisibility(View.GONE);
                tv_ok_select_house.setText(result.get(0).getNumberDesc());
            } else {
                tv_ok_select_house.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showLoginoutDialog();
                    }
                });
            }
        }
    }

    ///////
    @Override
    public void setInformationResult(InformationBean result) {
//        Log.i("fffffffffaa",result.getData().isPropertyAuth()+"");
//        if (result != null) {
//            boolean propertyAuth = result.getData().isPropertyAuth();
//            if (propertyAuth) {
//                intent = new Intent(this, ResidentCertificationActivity.class);
//
//                startActivityForResult(intent, 1009);
//            }
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1009) {
            if (null != data) {
                timer.schedule(task, 500, 1000);

            }
        }
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
