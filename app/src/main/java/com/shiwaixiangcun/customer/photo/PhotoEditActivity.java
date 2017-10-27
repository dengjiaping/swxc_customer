package com.shiwaixiangcun.customer.photo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.photo.core.PhotoFinal;
import com.shiwaixiangcun.customer.photo.model.PhotoInfo;
import com.shiwaixiangcun.customer.photo.utils.FileUtils;
import com.shiwaixiangcun.customer.photo.utils.PhotoUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;



/**
 * Created by liweiqin on 2016/1/31.
 */
public class PhotoEditActivity extends BasePhotoActivity {

    private static final int TAKE_REQUEST_CODE = 1001;

    private Uri mTakePhotoUri;
    private ImageView iv_take_photo;
    private PhotoInfo photoInfo;

    /**
     * 取某个范围的任意数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandom(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takePhotoAction();
        setContentView(R.layout.activity_edit);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        iv_take_photo = findViewById(R.id.iv_take_photo);
    }

    public void savePhoto(View v) {
        if (photoInfo != null) {
            ArrayList<PhotoInfo> photoList = new ArrayList();
            photoList.add(photoInfo);
            PhotoFinal.getCallback().onHandlerSuccess(PhotoFinal.REQUEST_CODE_CAMERA, photoList);
            PhotoFinal.getSelectPhotoActivityCallback().callback();
            this.finish();
        }
    }

    /**
     * 拍照
     */
    protected void takePhotoAction() {
        File takePhotoFolder = null;
        takePhotoFolder = PhotoFinal.getFunctionConfig().getTakePhotoFolder();
        File toFile = new File(takePhotoFolder, "IMG" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        if (FileUtils.mkdirs(takePhotoFolder)) {
            mTakePhotoUri = Uri.fromFile(toFile);
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mTakePhotoUri);
            startActivityForResult(captureIntent, TAKE_REQUEST_CODE);
        } else {
            takePhotoFailure();
        }
    }

    private void takePhotoFailure() {
        String errormsg = "退出拍照";
        Toast.makeText(getApplicationContext(), errormsg, Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_REQUEST_CODE) {
            Log.e("vvvvvvvvv","111111");
            if (resultCode == RESULT_OK && mTakePhotoUri != null) {
                Log.e("vvvvvvvvv","2222");
                final String path = mTakePhotoUri.getPath();
                if (new File(path).exists()) {
                    Log.e("vvvvvvvvv","3333");

                    final PhotoInfo info = new PhotoInfo();
                    info.setPhotoId(getRandom(10000, 99999));
                    info.setPhotoPath(path);
                    updatMuti(path);
                    takeResult(info);

                    if (photoInfo != null) {
                        ArrayList<PhotoInfo> photoList = new ArrayList();
                        photoList.add(photoInfo);
                        PhotoFinal.getCallback().onHandlerSuccess(PhotoFinal.REQUEST_CODE_CAMERA, photoList);
                        PhotoFinal.getSelectPhotoActivityCallback().callback();
                        this.finish();
                    }
                } else {
                    takePhotoFailure();
                }
            } else {
                takePhotoFailure();
            }
        }
    }

    private void takeResult(PhotoInfo info) {
        photoInfo = info;
        PhotoUtil.display(PhotoEditActivity.this, info.getPhotoPath(), iv_take_photo, mScreenWidth, mScreenHeight);
    }

    /**
     * 更新相册
     */
    private void updatMuti(String filePath) {
        if (mMediaScanner != null) {
            mMediaScanner.scanFile(filePath, "image/jpeg");
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
