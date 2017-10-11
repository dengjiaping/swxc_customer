package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.HousePhoneBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.impl.HousePhoneImpl;
import com.shiwaixiangcun.customer.ui.IHousePhoneView;
import com.shiwaixiangcun.customer.utils.AppManager;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.List;

public class ToResidentCertificationActivity extends AppCompatActivity implements View.OnClickListener, IHousePhoneView {

    private ChangeLightImageView back_left;
    private String houseId;
    private TextView tv_little_phone;
    private TextView tv_next_phone;
    private List<HousePhoneBean> data;
    private Button btn_submit_open;
    private EditText et_little_phone;
    private RelativeLayout rl_success_submit;
    private Button btn_ok;
    private ImageView iv_submit_expression;
    private TextView tv_submit_succsse;
    private TextView tv_content;
    private int next_i = 0;
    private String houseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_resident_certification);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        AppManager.getAppManager().addActivity(this);
        Intent intent = getIntent();
        houseId = String.valueOf(intent.getIntExtra("houseId", 0));
        houseName = intent.getStringExtra("slectHouse");


        layoutView();
        initData();
    }

    private void layoutView() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_little_phone = (TextView) findViewById(R.id.tv_little_phone);
        tv_next_phone = (TextView) findViewById(R.id.tv_next_phone);
        btn_submit_open = (Button) findViewById(R.id.btn_submit_open);
        et_little_phone = (EditText) findViewById(R.id.et_little_phone);
        rl_success_submit = (RelativeLayout) findViewById(R.id.rl_success_submit);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        iv_submit_expression = (ImageView) findViewById(R.id.iv_submit_expression);
        tv_submit_succsse = (TextView) findViewById(R.id.tv_submit_succsse);
        tv_content = (TextView) findViewById(R.id.tv_content);
    }

    private void initData() {
        if (Utils.isNotEmpty(houseId)) {
            HousePhoneImpl housePhone = new HousePhoneImpl(this, houseId, "");
            housePhone.getHouseNumber(this);
        }
        back_left.setOnClickListener(this);
        tv_next_phone.setOnClickListener(this);
        btn_submit_open.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.tv_next_phone:
                next_i++;
                if (next_i >= data.size()) {
                    next_i = 0;
                }
                if (data != null && data.size() != 0) {
                    tv_little_phone.setText(data.get(next_i).getPhone());
                }
                break;
            case R.id.btn_submit_open:
                String s_phone = tv_little_phone.getText().toString().trim() + et_little_phone.getText().toString().trim();
                if (!Utils.isNotEmpty(et_little_phone.getText().toString().trim())) {
                    Toast.makeText(this, "请补全电话号码后四位", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Utils.isNotEmpty(houseId)) {
                    HousePhoneImpl housePhone = new HousePhoneImpl(this, houseId, s_phone);
                    housePhone.validateNumber(this);
                } else {
                    Toast.makeText(this, "房产信息错误", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_ok:
                AppManager.getAppManager().finishAllActivity();
                String resident = SharePreference.getStringSpParams(this, Common.ISRESIDENT, Common.SIRESIDENT);
                if (resident.equals("torent")) {
//                    Intent intent = new Intent(this,ItoRentActivity.class);
//                    startActivity(intent);
                    finish();
                } else if (resident.equals("tosell")) {
//                    Intent intent = new Intent(this,ItoSellerActivity.class);
//                    startActivity(intent);
                    finish();
                } else if (resident.equals("online")) {
//                    Intent intent = new Intent(this,HomeActivity.class);
//                    startActivity(intent);
                    finish();
                }


                break;
        }
    }

    @Override
    public void setPhoneInfo(ResponseEntity<List<HousePhoneBean>> result) {
        data = result.getData();
        if (data != null && data.size() != 0) {
            tv_little_phone.setText(data.get(0).getPhone());
        }
    }

    @Override
    public void setPhoneResult(ResponseEntity result) {
        if (null != result) {
            if (result.getResponseCode() == 1001) {
                tv_content.setText("恭喜您认证成功");
                iv_submit_expression.setImageResource(R.mipmap.submit_success);
                tv_submit_succsse.setText("认证成功");
                rl_success_submit.setVisibility(View.VISIBLE);
                btn_submit_open.setVisibility(View.GONE);
            } else {
                iv_submit_expression.setImageResource(R.mipmap.submit_default);
                tv_submit_succsse.setText("认证失败");
                rl_success_submit.setVisibility(View.VISIBLE);
                btn_submit_open.setVisibility(View.GONE);
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
