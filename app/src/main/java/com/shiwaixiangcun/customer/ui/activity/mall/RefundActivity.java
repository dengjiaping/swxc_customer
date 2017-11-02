package com.shiwaixiangcun.customer.ui.activity.mall;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.ContextSession;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterImages;
import com.shiwaixiangcun.customer.http.ProgressDialogCallBack;
import com.shiwaixiangcun.customer.model.ImageReturnbean;
import com.shiwaixiangcun.customer.model.OrderDetailBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.photo.core.FunctionConfig;
import com.shiwaixiangcun.customer.photo.core.PhotoFinal;
import com.shiwaixiangcun.customer.photo.model.PhotoInfo;
import com.shiwaixiangcun.customer.ui.dialog.DialogLoginOut;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author xujhin
 * @date
 */
public class RefundActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 最大选择相册数目
     */
    private static final int MAX_IMAGE_NUMBER = 4;
    private static final int CODE_SUCCESS = 1001;
    private static final int CODE_1002 = 1002;
    private static final int MAX_LENGTH = 100;
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.iv_good_cover)
    ImageView mIvGoodCover;
    @BindView(R.id.tv_good_title)
    TextView mTvGoodTitle;
    @BindView(R.id.tv_good_desc)
    TextView mTvGoodDesc;
    @BindView(R.id.tv_good_item_price)
    TextView mTvGoodItemPrice;
    @BindView(R.id.tv_good_amount)
    TextView mTvGoodAmount;
    @BindView(R.id.llayout_good_info)
    LinearLayout mLlayoutGoodInfo;
    @BindView(R.id.edt_content)
    EditText mEdtContent;
    @BindView(R.id.iv_choose_image)
    ImageView mIvChooseImage;


    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.tv_money)
    EditText mTvMoney;
    @BindView(R.id.llayout_good_statue)
    RelativeLayout mLlayoutGoodStatue;
    @BindView(R.id.tv_refund_message)
    TextView mTvRefundMessage;

    @BindView(R.id.tv_stature)
    TextView mTvStature;
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.rv_images)
    RecyclerView mRvImages;
    StringBuilder strMessage = new StringBuilder();
    private String strToken;
    private String strRefreshToken;
    private String goodName;
    private int goodsId;
    private int orderId;
    private String orderStatue = "";
    private OrderDetailBean mOrderDetail;
    private ArrayList<String> selectList = new ArrayList<>();
    private AdapterImages mAdapterImages;
    private StringBuilder mStrImgID;
    private double maxAmount;
    /**
     * 相册回调
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_refund);
        ButterKnife.bind(this);

        initData();
        initViewAndEvent();

    }

    private void initData() {
        initToken();
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        orderId = extras.getInt("orderID");
        mOrderDetail = extras.getParcelable("orderDetail");


    }

    @Override
    protected void onResume() {
        super.onResume();
        initToken();
    }

    private void initToken() {
        strToken = ContextSession.getTokenString();
        strRefreshToken = ContextSession.getRefreshToken();
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initViewAndEvent() {
        mTvPageName.setText("申请退款");
        mBackLeft.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);
        mLlayoutGoodStatue.setOnClickListener(this);
        mTvMoney.setOnClickListener(this);
        mIvChooseImage.setOnClickListener(this);
        OrderDetailBean.GoodsDetailBean goodsDetailBean = mOrderDetail.getGoodsDetail().get(0);
        if (goodsDetailBean == null) {
            return;
        }
        mTvGoodTitle.setText(goodsDetailBean.getGoodName());
        mTvGoodDesc.setText(goodsDetailBean.getAttrDescription());
        mTvGoodItemPrice.setText("¥ " + ArithmeticUtils.format(goodsDetailBean.getPrice()));

        mTvGoodAmount.setText("x" + goodsDetailBean.getAmount());
        ImageDisplayUtil.showImageView(mContext, goodsDetailBean.getImgPath(), mIvGoodCover);
        mTvMoney.setText(ArithmeticUtils.format(mOrderDetail.getOrderInfo().getRealPay()));

        maxAmount = mOrderDetail.getOrderInfo().getRealPay();
        strMessage.append("最多退款 ￥")
                .append(ArithmeticUtils.format(mOrderDetail.getOrderInfo().getRealPay()));
        if (mOrderDetail.getOrderInfo().getTransportMoney() > 0) {
            strMessage.append(", 包含运费 ￥")
                    .append(ArithmeticUtils.format(mOrderDetail.getOrderInfo().getTransportMoney()));
        }
        mTvRefundMessage.setText(strMessage.toString());

        mAdapterImages = new AdapterImages(selectList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvImages.setLayoutManager(linearLayoutManager);
        mRvImages.setAdapter(mAdapterImages);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.tv_money:
                mTvMoney.setText("");
                break;
            case R.id.iv_choose_image:
                final FunctionConfig functionConfig = initPhotoConfig();
                PhotoFinal.openMuti(functionConfig, mOnHandlerResultCallback);
                break;
            case R.id.llayout_good_statue:
                final DialogLoginOut dialogLoginOut = new DialogLoginOut(mContext, R.layout.item_dialog_call_phone);
                dialogLoginOut.setTitle("货物状态");
                dialogLoginOut.setMessage("请选择当前货物状态");
                dialogLoginOut.setYesOnclickListener("已到收货", new DialogLoginOut.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        mTvStature.setText("已到收货");
                        orderStatue = "Received";
                        mIvArrow.setVisibility(View.INVISIBLE);
                        dialogLoginOut.dismiss();
                    }
                });
                dialogLoginOut.setNoOnclickListener("未到收货", new DialogLoginOut.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        mTvStature.setText("未到收货");
                        orderStatue = "NotReceived";
                        mIvArrow.setVisibility(View.INVISIBLE);
                        dialogLoginOut.dismiss();
                    }
                });
                dialogLoginOut.show();
                break;
            case R.id.btn_submit:

                if (mEdtContent.getText().toString().length() > MAX_LENGTH) {
                    showToastShort("字数超过限制(100以内)");
                } else {
                    if (Double.valueOf(mTvMoney.getText().toString()) > maxAmount) {
                        showToastShort("请确认你需要退款的金额");
                    } else if (Double.valueOf(mTvMoney.getText().toString()) <= 0) {
                        showToastShort("退款金额需大于0");
                    } else if ("".equals(orderStatue)) {
                        showToastShort("请选择货物状态");
                    } else {
                        if (selectList.size() > 0) {
                            submitPic(selectList);
                        } else {
                            postData(null);
                        }
                    }
                }
            default:
                break;
        }
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

                            postData(mStrImgID.toString());
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

    /**
     * 上传数据
     */
    private void postData(String imageID) {
        OkGo.<String>post(GlobalAPI.refund)
                .params("access_token", strToken)
                .params("amountOfRefund", mTvMoney.getText().toString())
                .params("cargoStatus", orderStatue)
                .params("imgIds", imageID)
                .params("orderId", orderId)
                .params("problemDes", mEdtContent.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ResponseEntity responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        if (responseEntity == null) {
                            return;
                        }
                        switch (responseEntity.getResponseCode()) {
                            case CODE_SUCCESS:
                                showToastShort("服务单申请成功，待售后审核");
                                finish();
                                break;
                            case CODE_1002:
                                showToastShort(responseEntity.getMessage());
                                break;
                            default:
                                showToastShort("申请失败，请重试");
                                break;
                        }

                    }
                });
    }
}
