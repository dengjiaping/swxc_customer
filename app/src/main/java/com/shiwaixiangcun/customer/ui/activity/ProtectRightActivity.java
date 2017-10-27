package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterImages;
import com.shiwaixiangcun.customer.http.ProgressDialogCallBack;
import com.shiwaixiangcun.customer.model.ImageReturnbean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.photo.core.FunctionConfig;
import com.shiwaixiangcun.customer.photo.core.PhotoFinal;
import com.shiwaixiangcun.customer.photo.model.PhotoInfo;
import com.shiwaixiangcun.customer.ui.dialog.DialogStatus;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.StringUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Xujhin
 */
public class ProtectRightActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 请求相册的返回码
     */
    private static final int REQUEST_CODE_CHOOSE = 23;
    /**
     * 最大选择相册数目
     */
    private static final int MAX_IMAGE_NUMBER = 4;
    private static final int CODE_SUCCESS = 1001;
    String strToken;
    String strContent;
    String strRefreshToken;
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    @BindView(R.id.iv_choose_image)
    ImageView mIvChooseImage;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.edt_content)
    EditText mEdtContent;
    @BindView(R.id.rv_images)
    RecyclerView mRvImages;
    private ArrayList<String> selectList = new ArrayList<String>();
    private AdapterImages mAdapterImages;
    private StringBuilder mStrImgID;
    /**
     * 回调
     */
    private PhotoFinal.OnHandlerResultCallback mOnHandlerResultCallback = new PhotoFinal.OnHandlerResultCallback() {
        @Override
        public void onHandlerSuccess(int requestCode, List<PhotoInfo> resultList) {
            if (requestCode == PhotoFinal.REQUEST_CODE_MUTI) {
                //是选择图片回来的照片
                selectList.clear();
                for (PhotoInfo info : resultList) {
                    selectList.add(info.getPhotoPath());
                }
                if (selectList.size() > 0) {
                    mRvImages.setVisibility(View.VISIBLE);
                    mAdapterImages.notifyDataSetChanged();
                }
            } else if (requestCode == PhotoFinal.REQUEST_CODE_CAMERA) {

                selectList.add(resultList.get(0).getPhotoPath());
                mAdapterImages.notifyDataSetChanged();
            }

            if (selectList.size() == MAX_IMAGE_NUMBER) {
                mIvChooseImage.setVisibility(View.GONE);
            }

        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protect_right);
        ButterKnife.bind(this);
        initViewAndEvent();
    }

    private void initViewAndEvent() {

        mTvTopRight.setText("维权记录");
        mTvPageName.setText("消费维权");
        strToken = (String) AppSharePreferenceMgr.get(this, GlobalConfig.TOKEN, "");
        strRefreshToken = (String) AppSharePreferenceMgr.get(this, GlobalConfig.TOKEN, "");
        mTvTopRight.setVisibility(View.VISIBLE);
        mTvTopRight.setOnClickListener(this);
        mBackLeft.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);
        mIvChooseImage.setOnClickListener(this);

        mAdapterImages = new AdapterImages(selectList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvImages.setLayoutManager(linearLayoutManager);
        mRvImages.setAdapter(mAdapterImages);
        mAdapterImages.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapterImages.remove(position);
                mIvChooseImage.setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.tv_top_right:
                readyGo(RightsRecordActivity.class);
                break;
            case R.id.btn_submit:
                strContent = mEdtContent.getText().toString().trim();
                if (StringUtil.isEmpty(strContent)) {
                    showToastShort(getResources().getString(R.string.protect_right_hint));
                } else {
                    if (getCurrentFocus() != null) {
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus()
                                                .getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                    }

                    if (selectList.size() > 0) {
                        submitPic(selectList);
                    } else {
                        submitInfo(strContent, "");
                    }
                }
                break;
            case R.id.iv_choose_image:
                final FunctionConfig functionConfig = initPhotoConfig();
                PhotoFinal.openMuti(functionConfig, mOnHandlerResultCallback);
                break;
            default:
                break;
        }
    }

    /**
     * 提交照片
     *
     * @param selectList
     */
    private void submitPic(ArrayList<String> selectList) {

        List<File> files = new ArrayList<>();
        if (selectList != null && selectList.size() > 0) {
            for (String string : selectList) {
                files.add(new File(string));
            }
        }

        OkGo.<String>post(Common.fileSend)
                .addFileParams("images", files)
                .execute(new ProgressDialogCallBack(this) {

                    @Override
                    public void onSuccess(Response<String> response) {

                        Log.e(BUG_TAG, response.body());
                        Type type = new TypeToken<ResponseEntity<List<ImageReturnbean>>>() {
                        }.getType();
                        ResponseEntity<List<ImageReturnbean>> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;
                        }
                        if (responseEntity.getResponseCode() == CODE_SUCCESS) {
                            List<ImageReturnbean> dataImages = responseEntity.getData();
                            mStrImgID = new StringBuilder();
                            for (ImageReturnbean item : dataImages) {
                                mStrImgID.append(item.getFileId()).append(",");
                            }
                            if (mStrImgID.length() > 0) {
                                mStrImgID.deleteCharAt(mStrImgID.length() - 1);
                            }

                            submitInfo(strContent, mStrImgID.toString());
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        showToastShort("提交图片失败");
                        Log.e(BUG_TAG, "error " + response.body());
                    }

                });

    }

    private FunctionConfig initPhotoConfig() {
        final FunctionConfig.Builder functionBuilder = new FunctionConfig.Builder();
        final FunctionConfig functionConfig = functionBuilder
                //设置最大选择数

                .setMaxSize(MAX_IMAGE_NUMBER)
                //设置选泽的照片集
                .setSelected(selectList)
                .setContext(this)
                //设置拍照存放地址 默认为null
                .setTakePhotoFolder(null)
                .build();
        PhotoFinal.init(functionConfig);
        return functionConfig;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
        }
    }

    private void submitInfo(String strContent, String imageIds) {
        OkGo.<String>post(GlobalAPI.right)
                .params("access_token", strToken)
                .params("content", strContent)
                .params("imageIds", imageIds)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }

                    public void onSuccess(Response<String> response) {

                        ResponseEntity responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        if (responseEntity == null) {
                            return;

                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                DialogStatus dialogStatus = new DialogStatus(mContext);
                                dialogStatus.setListener(new DialogStatus.onCallBackListener() {
                                    @Override
                                    public void closeBtn(DialogStatus dialog) {
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void confirmBtn(DialogStatus dialog) {
                                        dialog.dismiss();

                                    }
                                });
                                dialogStatus.show();
                                break;
                            case 1018:
                                RefreshTokenUtil.sendIntDataInvatation(mContext, strRefreshToken);
                                break;
                            default:

                                showToastShort("提交失败");
                                Log.e(BUG_TAG, "加载失败");
                                break;
                        }
                    }
                });

    }

}
