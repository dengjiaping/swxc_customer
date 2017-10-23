package com.shiwaixiangcun.customer.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterPhone;
import com.shiwaixiangcun.customer.model.PhoneBean;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.SelfLoginoutDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 救助电话Activity
 */
public class TelephoneActivity extends BaseActivity {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_number)
    RecyclerView mRvNumber;

    AdapterPhone mAdapterPhone;
    List<PhoneBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone);
        ButterKnife.bind(this);
        initData();
        initViewAndEvent();
    }

    private void initViewAndEvent() {
        mTvPageName.setText("救助电话");
        mAdapterPhone = new AdapterPhone(mList);
        mRvNumber.setLayoutManager(new LinearLayoutManager(this));
        mRvNumber.setAdapter(mAdapterPhone);
        mAdapterPhone.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final PhoneBean phoneBean = mAdapterPhone.getData().get(position);
                final SelfLoginoutDialog selfLoginoutDialog = new SelfLoginoutDialog(mContext, R.layout.item_dialog_call_phone);
                selfLoginoutDialog.setTitle("是否要拨打此电话？");
                selfLoginoutDialog.setMessage(phoneBean.getNumber());
                selfLoginoutDialog.setYesOnclickListener("是", new SelfLoginoutDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        selfLoginoutDialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneBean.getNumber()));
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                        startActivity(intent);
                    }
                });

                selfLoginoutDialog.setNoOnclickListener("否", new SelfLoginoutDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {

                        selfLoginoutDialog.dismiss();
                    }
                });
                selfLoginoutDialog.show();
            }
        });
    }

    private void initData() {
        mList = new ArrayList<>();
        PhoneBean hospital = new PhoneBean("天鹅堡医院", "0851-28798777");

        PhoneBean wuye = new PhoneBean("天鹅堡物业中心", "0851-22851555");

        PhoneBean jingwushi = new PhoneBean("天鹅堡警务室", "0851-28790151");
        PhoneBean jiudian = new PhoneBean("酒店前台", "0851-28790151");

        PhoneBean Aqu = new PhoneBean("A区区域管家", "13027872786");
        PhoneBean Bqu = new PhoneBean("B区区域管家", "13027872786");

        PhoneBean Equ = new PhoneBean("E区1-16栋管家", "13027872786");

        mList.add(hospital);
        mList.add(wuye);
        mList.add(jingwushi);
        mList.add(jiudian);
        mList.add(Aqu);
        mList.add(Bqu);
        mList.add(Equ);


    }

    @OnClick(R.id.back_left)
    public void onViewClicked() {
        finish();
    }
}
