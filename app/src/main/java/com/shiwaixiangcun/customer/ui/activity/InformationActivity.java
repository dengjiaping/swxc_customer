package com.shiwaixiangcun.customer.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.example.liangmutian.mypicker.DatePickerDialog;
import com.example.liangmutian.mypicker.DateUtil;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.ImageReturnbean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.LogoutBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.User;
import com.shiwaixiangcun.customer.model.UserInfoBean;
import com.shiwaixiangcun.customer.presenter.impl.HouseInformationImpl;
import com.shiwaixiangcun.customer.ui.IHouseInformationView;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.CompressionImageUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.ResUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.TimerToTimerUtil;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.utils.WheelPriorityDialogFragment;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.ImageViewPlus;
import com.shiwaixiangcun.customer.widget.PhotoAlbumDialog;
import com.shiwaixiangcun.customer.widget.SelfLoginoutDialog;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener, IHouseInformationView {

    private static final int MSG_CODE = 1001;
    private final int RESULT_CODE = 1;
    private final int SAVE_CODE = 1010;      //点击添加图片
    private ChangeLightImageView back_left;
    private TextView tv_page_name;
    private ImageViewPlus iv_head_image;
    private TextView tv_information_name;
    private TextView tv_information_nv;
    private TextView tv_information_old;
    private TextView tv_information_phone;
    private Button btn_loginout;
    private RelativeLayout rl_information_name;
    private HouseInformationImpl houseInformation;
    private RelativeLayout rl_head_image;
    private PhotoAlbumDialog selfDialog;
    private Bitmap bitmap1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //接收并处理消息
            if (msg.what == MSG_CODE) {
                //UI更新
                iv_head_image.setImageBitmap(bitmap1);
            }

        }

    };
    private RelativeLayout rl_gender;
    private Dialog dateDialog;
    private RelativeLayout rl_data_dialog;
    private String str_sex_get;
    private String str_sex_get_to;
    private String str_trim;
    private String str_sex = "";

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
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
        iv_head_image = (ImageViewPlus) findViewById(R.id.iv_head_image);
        tv_information_name = (TextView) findViewById(R.id.tv_information_name);
        tv_information_nv = (TextView) findViewById(R.id.tv_information_nv);
        tv_information_old = (TextView) findViewById(R.id.tv_information_old);
        tv_information_phone = (TextView) findViewById(R.id.tv_information_phone);
        btn_loginout = (Button) findViewById(R.id.btn_loginout);
        rl_information_name = (RelativeLayout) findViewById(R.id.rl_information_name);
        rl_head_image = (RelativeLayout) findViewById(R.id.rl_head_image);
        rl_gender = (RelativeLayout) findViewById(R.id.rl_gender);
        rl_data_dialog = (RelativeLayout) findViewById(R.id.rl_data_dialog);
    }

    private void initData() {
//        houseInformation = new HouseInformationImpl(this, "");
//        houseInformation.setBgaAdapterAndClick(this);
        tv_page_name.setText("个人信息");
        String image_head = SharePreference.getStringSpParams(this, Common.ISIMAGEHEAD, Common.SIIMAGEHEAD);
        String name = SharePreference.getStringSpParams(this, Common.ISUSERNAME, Common.SIUSERNAME);
        String sex = SharePreference.getStringSpParams(this, Common.ISUSERSEX, Common.SIUSERSEX);
        String old = SharePreference.getStringSpParams(this, Common.ISUSEROLD, Common.SIUSEROLD);
        String phone = SharePreference.getStringSpParams(this, Common.ISUSERPHONE, Common.SIUSERPHONE);
        if (Utils.isNotEmpty(image_head)) {
            Picasso.with(this).load(image_head).into(iv_head_image);
        }
        if (Utils.isNotEmpty(name)) {
            tv_information_name.setText(name);
        }
        if (Utils.isNotEmpty(sex)) {

            if (sex.equals("MAN")){
                str_sex_get_to = "男";
            }else if (sex.equals("WOMAN")){
                str_sex_get_to = "女";
            }else {
                str_sex_get_to = "未设置";
            }
            tv_information_nv.setText(str_sex_get_to);
        }
        if (Utils.isNotEmpty(old)) {
            tv_information_old.setText(old);
        }
        if (Utils.isNotEmpty(phone)) {
            tv_information_phone.setText(phone);
        }

        back_left.setOnClickListener(this);
        btn_loginout.setOnClickListener(this);
        rl_information_name.setOnClickListener(this);
        rl_head_image.setOnClickListener(this);
        rl_gender.setOnClickListener(this);
        rl_data_dialog.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.btn_loginout:
                showLoginoutDialog();
                break;
            case R.id.rl_information_name:
                Intent intent = new Intent(this, EditNameActivity.class);
                startActivityForResult(intent, 1716);
                break;
            case R.id.rl_head_image:
                showDialog();
                break;
            case R.id.rl_gender:
                initPriorityEvent();
                break;
            case R.id.rl_data_dialog:
                String trim = tv_information_old.getText().toString().trim();
                if (Utils.isNotEmpty(trim)){
                    str_trim = trim;
                }else {
                    str_trim = "1990-01-01";
                }
                showDateDialog(DateUtil.getDateForString(str_trim));
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(UserInfoBean result) {

        if (Utils.isNotEmpty(result.getData().getAvatar().getAccessUrl())) {
            Picasso.with(this).load(result.getData().getAvatar().getAccessUrl()).into(iv_head_image);
        }
        if (Utils.isNotEmpty(result.getData().getName())) {
            tv_information_name.setText(result.getData().getName());
        }
        if (Utils.isNotEmpty(result.getData().getSex())) {
            if (result.getData().getSex().equals("MAN")){
                str_sex_get = "男";
            }else if (result.getData().getSex().equals("WOMAN")){
                str_sex_get = "女";
            }else {
                str_sex_get = "未设置";
            }
            tv_information_nv.setText(str_sex_get);
        }
        if (Utils.isNotEmpty(result.getData().getBirthday())) {

            tv_information_old.setText(TimerToTimerUtil.stampToInspectionDate(result.getData().getBirthday() + ""));
        }
        if (Utils.isNotEmpty(result.getData().getCompanyPhone())) {
            tv_information_phone.setText(result.getData().getCompanyPhone());
        }


    }

    @Override
    public void setHeadImage(User result) {

    }

    @Override
    public void setSex(User result) {

    }

    @Override
    public void setData(User result) {

    }

    @Override
    public void setLogout(LogoutBean user) {
        if (user.getResponseCode() == 1001) {
            SharePreference.saveStringToSpParams(InformationActivity.this, Common.ISORNOLOGIN, Common.SIORNOLOGIN, "");
            finish();
        }
    }

    private void showLoginoutDialog() {
        final SelfLoginoutDialog selfLoginoutDialog = new SelfLoginoutDialog(InformationActivity.this, R.layout.item_dialog_loginout);
        selfLoginoutDialog.setTitle("你确定要退出账号吗？");
        selfLoginoutDialog.setMessage("退出账号后，您的信息将不会失去。但您将收不到关于您的任何通知信息。");
//        selfLoginoutDialog.setColor();
        selfLoginoutDialog.setYesOnclickListener("确认退出", new SelfLoginoutDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                houseInformation = new HouseInformationImpl(InformationActivity.this, "");
                houseInformation.setLogout(InformationActivity.this);
                selfLoginoutDialog.dismiss();
            }
        });
        selfLoginoutDialog.setNoOnclickListener("取消", new SelfLoginoutDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {

                selfLoginoutDialog.dismiss();
            }
        });
        selfLoginoutDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1716) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                String newName = bundle.getString("newName");
                tv_information_name.setText(newName);

            }
        }

        if (requestCode == RESULT_CODE) {
            if (null != data && null != (data.getExtras())) {
                // 通过Bundle获取data里面传输数据
                Bundle bundle = data.getExtras();
                // 转化数据
                Bitmap bitmap = (Bitmap) bundle.get("data");
                // 将图片添加到imagineview1中
                iv_head_image.setImageBitmap(bitmap);
                try {
                    File headImage = saveFile(bitmap, "headImage");
                    sendImageHttp(headImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        if (requestCode == SAVE_CODE) {
            if (null != data) {
                // 加载本地库的图片
                final Uri uri = data.getData();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = null;
                        try {

                            bitmap = Picasso.with(InformationActivity.this).load(uri).get();
                            bitmap1 = CompressionImageUtil.compressScale(bitmap);

                            File headImage = saveFile(bitmap1, "headImage");
                            sendImageHttp(headImage);
                            //子线程发送信息
                            Message msg = mHandler.obtainMessage(MSG_CODE);
                            msg.sendToTarget();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

        }
    }

    private void showDialog() {
        selfDialog = new PhotoAlbumDialog(InformationActivity.this, R.layout.photo_album_dialog_layout);
        selfDialog.setTitle("");
        selfDialog.setMessage("");
        selfDialog.setYesOnclickListener("拍照", new PhotoAlbumDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {


                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                startActivityForResult(intent, RESULT_CODE);

                selfDialog.dismiss();
            }
        });

        selfDialog.setYesAlbumOnclickListener("从本地选择", new PhotoAlbumDialog.onYesAlbumOnclickListener() {
            @Override
            public void onYesAlbumClick() {

                Intent intent1 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent1.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                startActivityForResult(intent1, SAVE_CODE);

                selfDialog.dismiss();
            }
        });

        selfDialog.setNoOnclickListener("取消", new PhotoAlbumDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {

                selfDialog.dismiss();
            }
        });


        selfDialog.show();
    }

    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public File saveFile(Bitmap bm, String fileName) throws IOException {
        String path = getSDPath() + "/revoeye/";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName + ".jpg");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

    //图片提交
    private void sendImageHttp(File file) {

        String login_detail = SharePreference.getStringSpParams(this, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        String token = (String) AppSharePreferenceMgr.get(this, GlobalConfig.TOKEN, "");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", token);
        hashMap.put("images", file);


        HttpRequest.post(Common.fileSend, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {

                Type type = new TypeToken<ResponseEntity<List<ImageReturnbean>>>() {
                }.getType();
                ResponseEntity<List<ImageReturnbean>> responseEntity = JsonUtil.fromJson(responseJson, type);

                if (responseEntity.getResponseCode() == 1001) {
                    if (responseEntity.getData() != null && responseEntity.getData().size() != 0) {

                        String fileId = responseEntity.getData().get(0).getFileId() + "";
                        houseInformation = new HouseInformationImpl(InformationActivity.this, fileId);
                        houseInformation.setHeadImageXg(InformationActivity.this);
                        SharePreference.saveStringToSpParams(InformationActivity.this, Common.ISIMAGEHEAD, Common.SIIMAGEHEAD, responseEntity.getData().get(0).getThumbImageURL());
                    }

                } else if (responseEntity.getResponseCode() == 1018) {
                    RefreshTokenUtil.sendIntDataInvatation(InformationActivity.this, refresh_token);
                } else if (responseEntity.getResponseCode() == 1019) {
                    LoginOutUtil.sendLoginOutUtil(InformationActivity.this);
                }



            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    //性别
    private void initPriorityEvent() {
        final WheelPriorityDialogFragment wheelViewDialogFragment = WheelPriorityDialogFragment
                .newInstance(ResUtil.getStringArray(R.array.main_gender_menu),
                        ResUtil.getString(R.string.app_cancel),
                        ResUtil.getString(R.string.app_sure), true, false, true);
        wheelViewDialogFragment.setWheelDialogListener(new WheelPriorityDialogFragment.OnWheelDialogListener() {
            @Override
            public void onClickLeft(String value) {
                wheelViewDialogFragment.dismiss();
            }

            @Override
            public void onClickRight(String value) {
                wheelViewDialogFragment.dismiss();

                if (value.equals("男")) {
                    str_sex = "MAN";
                    tv_information_nv.setText("男");
                    SharePreference.saveStringToSpParams(InformationActivity.this, Common.ISUSERSEX, Common.SIUSERSEX, "MAN");
                } else if (value.equals("女")) {
                    str_sex = "WOMAN";
                    tv_information_nv.setText("女");
                    SharePreference.saveStringToSpParams(InformationActivity.this, Common.ISUSERSEX, Common.SIUSERSEX, "WOMAN");
                } else {
                    str_sex = "NONE";
                    tv_information_nv.setText("未设置");
                }

                houseInformation = new HouseInformationImpl(InformationActivity.this, str_sex);
                houseInformation.setSex(InformationActivity.this);

            }

            @Override
            public void onAllcenter(String value) {


                wheelViewDialogFragment.dismiss();
            }

            @Override
            public void onValueChanged(String value) {
                Log.i("", "current value: " + value);
            }
        });
        wheelViewDialogFragment.show(getSupportFragmentManager(), "");

    }


    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {

//                mTextView.setText(dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
//                        + (dates[2] > 9 ? dates[2] : ("0" + dates[2])));
                Log.i("gggggggggghha", dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                        + (dates[2] > 9 ? dates[2] : ("0" + dates[2])));
                String s_data = dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                        + (dates[2] > 9 ? dates[2] : ("0" + dates[2]));
                tv_information_old.setText(s_data);
                SharePreference.saveStringToSpParams(InformationActivity.this, Common.ISUSEROLD, Common.SIUSEROLD, s_data);
                houseInformation = new HouseInformationImpl(InformationActivity.this, s_data);
                houseInformation.setData(InformationActivity.this);
            }

            @Override
            public void onCancel() {

            }
        })

                .setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1);

        builder.setMaxYear(DateUtil.getYear());
        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        dateDialog = builder.create();
        dateDialog.show();
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
