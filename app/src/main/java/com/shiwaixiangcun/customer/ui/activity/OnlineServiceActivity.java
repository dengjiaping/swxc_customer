package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.photo.BasePhotoActivity;
import com.shiwaixiangcun.customer.photo.adpater.PhotoShowListAdapter;
import com.shiwaixiangcun.customer.photo.core.FunctionConfig;
import com.shiwaixiangcun.customer.photo.core.PhotoFinal;
import com.shiwaixiangcun.customer.photo.model.PhotoInfo;
import com.shiwaixiangcun.customer.presenter.impl.OnlineServiceImpl;
import com.shiwaixiangcun.customer.ui.IOnlineServiceView;
import com.shiwaixiangcun.customer.utils.AppManager;
import com.shiwaixiangcun.customer.utils.NoFastClickUtil;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 */
public class OnlineServiceActivity extends BasePhotoActivity implements AdapterView.OnItemClickListener, View.OnClickListener, IOnlineServiceView {

    private TextView tvTopRight;
    private ChangeLightImageView backLeft;
    private Intent intent = new Intent();


    /**
     * 存放选择的照片
     */
    private ArrayList<String> selectList = new ArrayList<String>();

    private GridView selectView;
    private PhotoShowListAdapter listAdapter;
    private EditText etOnlineContent;
    private Button btnSubmitOnline;
    private OnlineServiceImpl onlineService;
    private RelativeLayout rlSuccessSubmit;
    private Button btnOk;
    private boolean isnotImage = false;

    private TextView tvPageName;
    private HashMap<String, File> hashImage = new HashMap<>();
    /**
     * 回调
     */
    private PhotoFinal.OnHandlerResultCallback mOnHandlerResultCallback = new PhotoFinal.OnHandlerResultCallback() {
        @Override
        public void onHandlerSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (reqeustCode == PhotoFinal.REQUEST_CODE_MUTI) {
                //是选择图片回来的照片
                selectList.clear();
                for (PhotoInfo info : resultList) {
                    selectList.add(info.getPhotoPath());
                }
                listAdapter.notifyDataSetChanged();
                // Toast.makeText(getApplicationContext(), "size:" + resultList.size(), Toast.LENGTH_LONG).show();
            } else if (reqeustCode == PhotoFinal.REQUEST_CODE_CAMERA) {
                //是拍照带回来的照片
                selectList.add(resultList.get(0).getPhotoPath());
                listAdapter.notifyDataSetChanged();
            }

            isnotImage = selectList.size() > 0;

            for (int i = 0; i < selectList.size(); i++) {
                Bitmap bitmap = BitmapFactory.decodeFile(selectList.get(i));
                try {
                    File file = saveFile(bitmap, "btm" + i);
                    hashImage.put("btm" + i, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(OnlineServiceActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

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
        setContentView(R.layout.activity_online_service);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        AppManager.getAppManager().addActivity(this);
        layoutView();


        selectView = findViewById(R.id.gv_selected);
        listAdapter = new PhotoShowListAdapter(this, selectList, mScreenWidth);


        selectView.setAdapter(listAdapter);
        selectView.setOnItemClickListener(this);
        initData();

        listAdapter.setImageListener(new PhotoShowListAdapter.onImageListener() {
            @Override
            public void imageScence(List<String> json) {
                for (int i = 0; i < hashImage.size(); i++) {
                    hashImage.clear();
                }
                for (int i = 0; i < json.size(); i++) {
                    Bitmap bitmap = BitmapFactory.decodeFile(json.get(i));
                    try {
                        File file = saveFile(bitmap, "btm" + i);
                        hashImage.put("btm" + i, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void layoutView() {
        tvTopRight = findViewById(R.id.tv_top_right);
        backLeft = findViewById(R.id.back_left);
        etOnlineContent = findViewById(R.id.et_online_content);
        btnSubmitOnline = findViewById(R.id.btn_submit_online);
        rlSuccessSubmit = findViewById(R.id.rl_success_submit);
        btnOk = findViewById(R.id.btn_ok);
        tvPageName = findViewById(R.id.tv_page_name);


    }

    private void initData() {
        onlineService = new OnlineServiceImpl(this, "", hashImage);
//        onlineService.setInformation(this);

        tvTopRight.setVisibility(View.VISIBLE);
        tvTopRight.setText("报修记录");
        tvPageName.setText("在线报修");
        tvTopRight.setTextColor(Color.parseColor("#1CCC8C"));
        backLeft.setOnClickListener(this);
        tvTopRight.setOnClickListener(this);
        btnSubmitOnline.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.tv_top_right:
                intent.setClass(this, RecordsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_submit_online:
                if (isnotImage) {

                    if (NoFastClickUtil.isFastClick()) {
                        //快速点击后的逻辑，可以提示用户点击太快，休息一会
                        Toast.makeText(OnlineServiceActivity.this, "点击太快，休息一会", Toast.LENGTH_SHORT).show();
                    } else {

                        if (Utils.isNotEmpty(etOnlineContent.getText().toString().trim())) {
                            onlineService = new OnlineServiceImpl(this, etOnlineContent.getText().toString().trim(), hashImage);
                            onlineService.setHaveImageClick(this);
                        } else {
                            Toast.makeText(this, "请填写内容后再提交！", Toast.LENGTH_LONG).show();
                        }
                    }


                } else {
                    if (NoFastClickUtil.isFastClick()) {
                        //快速点击后的逻辑，可以提示用户点击太快，休息一会
                        Toast.makeText(OnlineServiceActivity.this, "点击太快，休息一会", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Utils.isNotEmpty(etOnlineContent.getText().toString().trim())) {
                            onlineService = new OnlineServiceImpl(this, etOnlineContent.getText().toString().trim(), hashImage);
                            onlineService.setBgaAdpaterAndClick(this);
                        } else {
                            Toast.makeText(this, "请填写内容后再提交！", Toast.LENGTH_LONG).show();
                        }
                    }

                }


                break;
            case R.id.btn_ok:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 加载配置的信息
     */
    private FunctionConfig initConfig() {
        final FunctionConfig.Builder functionBuilder = new FunctionConfig.Builder();
        final FunctionConfig functionConfig = functionBuilder.setMaxSize(4)//设置最大选择数
                .setSelected(selectList)//设置选泽的照片集
                .setContext(this)//设置上下文对象
                .setTakePhotoFolder(null)//设置拍照存放地址 默认为null
                .build();
        PhotoFinal.init(functionConfig);
        return functionConfig;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PhotoShowListAdapter.PhotoViewHolder vh = (PhotoShowListAdapter.PhotoViewHolder) view.getTag();
        if (position == selectList.size() && vh.iv_thumb.getVisibility() != View.GONE) {
            final FunctionConfig functionConfig = initConfig();
            PhotoFinal.openMuti(functionConfig, mOnHandlerResultCallback);
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(ResponseEntity result) {
        if (result.getResponseCode() == 1001) {
            rlSuccessSubmit.setVisibility(View.VISIBLE);
            tvTopRight.setVisibility(View.GONE);
        }

    }

    @Override
    public void setHaveImageResult(ResponseEntity result) {
        if (result.getResponseCode() == 1001) {
            rlSuccessSubmit.setVisibility(View.VISIBLE);
            tvTopRight.setVisibility(View.GONE);
        }
    }

    @Override
    public void setInformation(InformationBean result) {
//        if (result != null) {
//            boolean propertyAuth = result.getData().isPropertyAuth();
//            if (!propertyAuth) {
//                SharePreference.saveStringToSpParams(this, Common.ISRESIDENT,Common.SIRESIDENT,"online");
//                intent = new Intent(this, ResidentCertificationActivity.class);
//
//                startActivityForResult(intent, 1009);
//            }
//        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
