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
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.HousePhoneBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.impl.HousePhoneImpl;
import com.shiwaixiangcun.customer.ui.IHousePhoneView;
import com.shiwaixiangcun.customer.utils.AppManager;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.List;

/**
 * @author Administrator
 */
public class ToResidentCertificationActivity extends AppCompatActivity implements View.OnClickListener, IHousePhoneView {

    private ChangeLightImageView backLeft;
    private String houseId;
    private TextView tvLittlePhone;
    private TextView tvNextPhone;
    private List<HousePhoneBean> data;
    private Button btnSubmitOpen;
    private EditText etLittlePhone;
    private RelativeLayout rlSuccessSubmit;
    private Button btnOk;
    private ImageView ivSubmitExpression;
    private TextView tvSubmitSuccsse;
    private TextView tvContent;
    private int nextI = 0;
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
        backLeft = findViewById(R.id.back_left);
        tvLittlePhone = findViewById(R.id.tv_little_phone);
        tvNextPhone = findViewById(R.id.tv_next_phone);
        btnSubmitOpen = findViewById(R.id.btn_submit_open);
        etLittlePhone = findViewById(R.id.et_little_phone);
        rlSuccessSubmit = findViewById(R.id.rl_success_submit);
        btnOk = findViewById(R.id.btn_ok);
        ivSubmitExpression = findViewById(R.id.iv_submit_expression);
        tvSubmitSuccsse = findViewById(R.id.tv_submit_succsse);
        tvContent = findViewById(R.id.tv_content);
    }

    private void initData() {
        if (Utils.isNotEmpty(houseId)) {
            HousePhoneImpl housePhone = new HousePhoneImpl(this, houseId, "");
            housePhone.getHouseNumber(this);
        }
        backLeft.setOnClickListener(this);
        tvNextPhone.setOnClickListener(this);
        btnSubmitOpen.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.tv_next_phone:
                nextI++;
                if (nextI >= data.size()) {
                    nextI = 0;
                }
                if (data != null && data.size() != 0) {
                    tvLittlePhone.setText(data.get(nextI).getPhone());
                }
                break;
            case R.id.btn_submit_open:
                String s_phone = tvLittlePhone.getText().toString().trim() + etLittlePhone.getText().toString().trim();
                if (!Utils.isNotEmpty(etLittlePhone.getText().toString().trim())) {
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
            default:
                break;
        }
    }

    @Override
    public void setPhoneInfo(ResponseEntity<List<HousePhoneBean>> result) {
        data = result.getData();
        if (data != null && data.size() != 0) {
            tvLittlePhone.setText(data.get(0).getPhone());
        }
    }

    @Override
    public void setPhoneResult(ResponseEntity result) {
        if (null != result) {
            if (result.getResponseCode() == 1001) {
                tvContent.setText("恭喜您认证成功");
                ivSubmitExpression.setImageResource(R.mipmap.submit_success);
                tvSubmitSuccsse.setText("认证成功");
                AppSharePreferenceMgr.put(this, GlobalConfig.propertyAuth, true);
                rlSuccessSubmit.setVisibility(View.VISIBLE);
                btnSubmitOpen.setVisibility(View.GONE);
            } else {
                ivSubmitExpression.setImageResource(R.mipmap.submit_default);
                tvSubmitSuccsse.setText("认证失败");
                rlSuccessSubmit.setVisibility(View.VISIBLE);
                btnSubmitOpen.setVisibility(View.GONE);
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
