package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.impl.EditNameImpl;
import com.shiwaixiangcun.customer.ui.IEditNameView;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

public class EditNameActivity extends AppCompatActivity implements View.OnClickListener, IEditNameView {

    private ChangeLightImageView back_left;
    private TextView tv_page_name;
    private TextView tv_top_right;
    private EditText et_new_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        layoutView();
        initData();
    }

    private void layoutView() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        et_new_name = (EditText) findViewById(R.id.et_new_name);
    }

    private void initData() {
        tv_page_name.setText("编辑姓名");
        tv_top_right.setVisibility(View.VISIBLE);
        tv_top_right.setText("保存");
        tv_top_right.setTextColor(Color.parseColor("#1CCC8C"));
        back_left.setOnClickListener(this);
        tv_top_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.tv_top_right:
                String str_name = this.et_new_name.getText().toString().trim();
                EditNameImpl editName = new EditNameImpl(this, str_name);
                editName.setBgaAdpaterAndClick(this);
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(ResponseEntity result) {
        if (result.getResponseCode() == 1001){
            SharePreference.saveStringToSpParams(EditNameActivity.this, Common.ISUSERNAME, Common.SIUSERNAME, et_new_name.getText().toString().trim());
            Intent intent = new Intent();
            intent.putExtra("newName",et_new_name.getText().toString().trim());
            setResult(1717,intent);
            finish();
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
