package com.shiwaixiangcun.customer.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterPhone;
import com.shiwaixiangcun.customer.model.PhoneBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.ui.dialog.DialogLoginOut;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 救助电话Activity
 *
 * @author Administrator
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
    private String strToken;
    private LocationManager locationManager;
    private String locationProvider;
    private Location mLocation;

    private String longitude = "";
    private String latitude = "";
    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示

            if (location != null) {
                longitude = String.valueOf(location.getLongitude());
                latitude = String.valueOf(location.getLatitude());
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone);
        ButterKnife.bind(this);
        initData();
        getLocation();


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
                final DialogLoginOut dialogLoginOut = new DialogLoginOut(mContext, R.layout.item_dialog_call_phone);
                dialogLoginOut.setTitle("是否要拨打此电话？");
                dialogLoginOut.setMessage(phoneBean.getNumber());
                dialogLoginOut.setYesOnclickListener("是", new DialogLoginOut.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {

                        putData(phoneBean.getTitle());
                        dialogLoginOut.dismiss();
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneBean.getNumber()));
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                        startActivity(intent);
                    }
                });

                dialogLoginOut.setNoOnclickListener("否", new DialogLoginOut.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {

                        dialogLoginOut.dismiss();
                    }
                });
                dialogLoginOut.show();
            }
        });
    }

    /**
     * @param title
     */
    private void putData(String title) {
        strToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        OkGo.<String>post(GlobalAPI.callSOS)
                .params("access_token", strToken)
                .params("callObject", title)
                .params("longitude", longitude)
                .params("latitude", latitude)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        ResponseEntity responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        if (responseEntity != null) {
                            switch (responseEntity.getResponseCode()) {
                                case 1001:
                                    Log.e(BUG_TAG, "success");
                                    break;
                                default:

                                    Log.e(BUG_TAG, "fail");
                                    break;

                            }
                        }
                    }
                });

    }

    private void initData() {
        mList = new ArrayList<>();
        PhoneBean hospital = new PhoneBean("天鹅堡医院", "0851-28798777");

        PhoneBean wuye = new PhoneBean("天鹅堡物业中心", "0851-22851555");

        PhoneBean jingwushi = new PhoneBean("天鹅堡警务室", "0851-28790151");
        PhoneBean jiudian = new PhoneBean("酒店前台", "0851-23313111");

        PhoneBean Aqu = new PhoneBean("A区区域管家", "13027872786");
        PhoneBean Bqu = new PhoneBean("B区区域管家", "13087897558");

        PhoneBean Equ = new PhoneBean("E区1-16栋管家", "15599222838");
        PhoneBean Equ32 = new PhoneBean("E区1-32栋管家", "13096735118");
        PhoneBean Equ49 = new PhoneBean("E区1-49栋管家", "15599225286");

        mList.add(hospital);
        mList.add(wuye);
        mList.add(jingwushi);
        mList.add(jiudian);
        mList.add(Aqu);
        mList.add(Bqu);
        mList.add(Equ);
        mList.add(Equ32);
        mList.add(Equ49);


    }

    @OnClick(R.id.back_left)
    public void onViewClicked() {
        finish();
    }

    public void getLocation() {

        //获取地理位置管理器
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return;
        }
        //获取Location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            longitude = String.valueOf(location.getLongitude());
            latitude = String.valueOf(location.getLatitude());
        }

        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            //移除监听器
            locationManager.removeUpdates(locationListener);
        }
    }
}
