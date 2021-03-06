package com.shiwaixiangcun.customer.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.UpdateAppBean;
import com.shiwaixiangcun.customer.presenter.impl.MyMineImpl;
import com.shiwaixiangcun.customer.ui.IMyMineView;
import com.shiwaixiangcun.customer.ui.activity.mall.MallActivity;
import com.shiwaixiangcun.customer.ui.activity.mall.ManageAddressActivity;
import com.shiwaixiangcun.customer.ui.activity.mall.OrderActivity;
import com.shiwaixiangcun.customer.ui.dialog.DialogLoginOut;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.utils.VersionUpdateUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.CircleImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MymineActivity extends AppCompatActivity implements View.OnClickListener, IMyMineView {

    long m_newVerCode; //最新版的版本号
    String m_newVerName; //最新版的版本名
    String m_appNameStr; //下载到本地要给这个APP命的名字
    View.OnClickListener btnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            new checkNewestVersionAsyncTask().execute();
        }
    };
    private ChangeLightImageView back_left;
    private RelativeLayout rl_head_mine;
    private RelativeLayout rl_feed_back;
    private TextView tv_user_name;
    private CircleImageView iv_head_my_image;
    private MyMineImpl myMine;
    private TextView tv_wy_phone;
    private RelativeLayout rl_wy_lay;
    private RelativeLayout rl_for_life;
    private RelativeLayout rl_app_updata;
    private RelativeLayout rl_app_address;
    private RelativeLayout rl_app_order;
    private Handler m_mainHandler;
    private ProgressDialog m_progressDlg;
    private String verName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymine);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        layoutView();
        initData();
    }

    private void layoutView() {
        back_left = findViewById(R.id.back_left);
        rl_head_mine = findViewById(R.id.rl_head_mine);
        rl_feed_back = findViewById(R.id.rl_feed_back);
        tv_user_name = findViewById(R.id.tv_user_name);
        iv_head_my_image = findViewById(R.id.iv_head_my_image);
        tv_wy_phone = findViewById(R.id.tv_wy_phone);
        rl_wy_lay = findViewById(R.id.rl_wy_lay);
        rl_for_life = findViewById(R.id.rl_for_life);
        rl_app_updata = findViewById(R.id.rl_app_updata);
        rl_app_address = findViewById(R.id.rl_app_address);
        rl_app_order = findViewById(R.id.rl_app_order);

        //初始化相关变量
        initVariable();
        rl_app_updata.setOnClickListener(btnClickListener);
    }

    private void initData() {

        back_left.setOnClickListener(this);
        rl_head_mine.setOnClickListener(this);
        rl_feed_back.setOnClickListener(this);
        rl_wy_lay.setOnClickListener(this);
        rl_for_life.setOnClickListener(this);
        rl_app_order.setOnClickListener(this);
        rl_app_address.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent;
        String isOrnotLogin_renting = SharePreference.getStringSpParams(MymineActivity.this, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.rl_head_mine:
                String isOrnotLogin_service = SharePreference.getStringSpParams(MymineActivity.this, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
                if (Utils.isNotEmpty(isOrnotLogin_service)) {
                    intent = new Intent(this, InformationActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("mineLogin", "mineLogin");
                    startActivityForResult(intent, 1020);
                }
                break;
            case R.id.rl_feed_back:
                Intent intent_feed = new Intent(this, MallActivity.class);
                startActivity(intent_feed);
                break;
            case R.id.rl_wy_lay:
                showLoginoutDialog(tv_wy_phone.getText().toString().trim());
                break;
            case R.id.rl_for_life:
                Intent intent_version = new Intent(this, AboutActivity.class);
                startActivity(intent_version);
                break;
            case R.id.rl_app_address:
                if (Utils.isNotEmpty(isOrnotLogin_renting)) {
                    intent = new Intent(this, ManageAddressActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("clickable", false);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.rl_app_order:
                if (Utils.isNotEmpty(isOrnotLogin_renting)) {
                    Intent intent_order = new Intent(this, OrderActivity.class);
                    startActivity(intent_order);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }

        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(UpdateAppBean result) {
        if (result.getResponseCode() == 1001) {
            if (result.getData().isNeedUpdate()) {//如果有最新版本
                doNewVersionUpdate(result.getData().getLatestVersion(), result.getData().getDownloadUrl()); // 更新新版本
            } else {
                notNewVersionDlgShow(); // 提示当前为最新版本
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String isOrnotLogin_service = SharePreference.getStringSpParams(MymineActivity.this, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
        if (Utils.isNotEmpty(isOrnotLogin_service)) {
            String head_image_path = SharePreference.getStringSpParams(this, Common.ISIMAGEHEAD, Common.SIIMAGEHEAD);
            String username = SharePreference.getStringSpParams(this, Common.ISUSERNAME, Common.SIUSERNAME);
            Log.i("1111111111122", head_image_path);

            tv_user_name.setText(username);
            if (Utils.isNotEmpty(head_image_path)) {
                ImageDisplayUtil.showImageView(this, head_image_path, iv_head_my_image);
            } else {
                iv_head_my_image.setImageResource(R.mipmap.defalt_image);
            }

        } else {
            tv_user_name.setText("立即登录");
            iv_head_my_image.setImageResource(R.mipmap.defalt_image);
        }

        StatService.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    private void showLoginoutDialog(final String phone) {
        final DialogLoginOut dialogLoginOut = new DialogLoginOut(MymineActivity.this, R.layout.item_dialog_call_phone);
        dialogLoginOut.setTitle("是否要拨打此电话？");
        dialogLoginOut.setMessage(phone);
//        dialogLoginOut.setColor();
        dialogLoginOut.setYesOnclickListener("是", new DialogLoginOut.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                dialogLoginOut.dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(MymineActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
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

    /**
     * 从服务器获取当前最新版本号，如果成功返回TURE，如果失败，返回FALSE
     *
     * @return
     */
    private Boolean postCheckNewestVersionCommand2Server() {
        StringBuilder builder = new StringBuilder();
        JSONArray jsonArray = null;
        try {
            // 构造POST方法的{name:value} 参数对
            List<NameValuePair> vps = new ArrayList<NameValuePair>();
            // 将参数传入post方法中
            vps.add(new BasicNameValuePair("action", "checkNewestVersion"));
            builder = VersionUpdateUtil.post_to_server(vps);
            jsonArray = new JSONArray(builder.toString());
            if (jsonArray.length() > 0) {
                if (jsonArray.getJSONObject(0).getInt("id") == 1) {
                    m_newVerName = jsonArray.getJSONObject(0).getString("verName");
                    m_newVerCode = jsonArray.getJSONObject(0).getLong("verCode");

                    return true;
                }
            }

            return true;
        } catch (Exception e) {
//            Log.e("msg",e.getMessage());
            m_newVerName = "";
            m_newVerCode = -1;
            return false;
        }
    }

    /**
     * 提示更新新版本
     */
    private void doNewVersionUpdate(String str_version, final String str_url) {
        int verCode = VersionUpdateUtil.getVerCode(getApplicationContext());
        verName = VersionUpdateUtil.getVerName(getApplicationContext());

        String str = "当前版本：" + verName + " ,发现新版本：" + str_version + " ,是否更新？";
        Dialog dialog = new AlertDialog.Builder(this).setTitle("软件更新").setMessage(str)
                // 设置内容
                .setPositiveButton("更新",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                m_progressDlg.setTitle("正在下载");
                                m_progressDlg.setMessage("请稍候...");
                                downFile(str_url);  //开始下载
                                Log.i("ggggggggggaaa", "qqqqqqqqqq" + str_url);
                            }
                        })
                .setNegativeButton("暂不更新",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 点击"取消"按钮之后退出程序
//                                finish();
                            }
                        }).create();// 创建
        // 显示对话框
        dialog.show();
    }

    /**
     * 提示当前为最新版本
     */
    private void notNewVersionDlgShow() {
        int verCode = VersionUpdateUtil.getVerCode(this);
        String verName = VersionUpdateUtil.getVerName(this);
        String str = "当前版本:" + verName + ",已是最新版,无需更新!";
        Dialog dialog = new AlertDialog.Builder(this).setTitle("软件更新")
                .setMessage(str)// 设置内容
                .setPositiveButton("确定",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }).create();// 创建
        // 显示对话框
        dialog.show();
    }

    private void initVariable() {
        m_mainHandler = new Handler();
        m_progressDlg = new ProgressDialog(this);
        m_progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        m_progressDlg.setIndeterminate(false);
        m_appNameStr = "haha.apk";
    }

    private void downFile(final String url) {
        m_progressDlg.show();
        new Thread() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                String trim = url.trim();
                HttpGet get = new HttpGet(trim);
                HttpResponse response;
                try {
                    response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    long length = entity.getContentLength();

                    m_progressDlg.setMax((int) length);//设置进度条的最大值

                    InputStream is = entity.getContent();
                    FileOutputStream fileOutputStream = null;
                    if (is != null) {
                        File file = new File(
                                Environment.getExternalStorageDirectory(),
                                m_appNameStr);
                        fileOutputStream = new FileOutputStream(file);
                        byte[] buf = new byte[1024];
                        int ch = -1;
                        int count = 0;
                        while ((ch = is.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, ch);
                            count += ch;
                            if (length > 0) {
                                m_progressDlg.setProgress(count);
                            }
                        }

                    }

                    fileOutputStream.flush();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    down();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void down() {
        m_mainHandler.post(new Runnable() {
            public void run() {
                m_progressDlg.cancel();
                update();
            }
        });
    }

    void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), m_appNameStr)),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1020:
                finish();
                break;
        }
    }

    class checkNewestVersionAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            if (postCheckNewestVersionCommand2Server()) {
                int vercode = VersionUpdateUtil.getVerCode(getApplicationContext()); // 用到前面第一节写的方法
                return m_newVerCode > vercode;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.i("aaaaaaaaabbvva", "请求版本更新接口");
//            sendAppUpdateInvatation();
            myMine = new MyMineImpl(MymineActivity.this, "");
            myMine.setBgaAdpaterAndClick(MymineActivity.this);

            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
