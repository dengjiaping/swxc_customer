package com.shiwaixiangcun.customer.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.shiwaixiangcun.customer.ui.dialog.DialogSku;
import com.shiwaixiangcun.customer.ui.dialog.DialogSupport;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品详情Activity
 */
public class GoodDetailActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;

    @BindView(R.id.banner_details)
    Banner mBannerDetails;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_price_fare)
    TextView mTvPriceFare;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;
    @BindView(R.id.llayout_service)
    LinearLayout mLlayoutService;
    @BindView(R.id.textView4)
    TextView mTextView4;
    @BindView(R.id.tv_categoty)
    TextView mTvCategory;
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.rl_choice)
    RelativeLayout mRlChoice;
    @BindView(R.id.taglayout)
    TagFlowLayout mTaglayout;
    @BindView(R.id.btn_purchase)
    Button mBtnPurchase;
    @BindView(R.id.iv_share_right)
    ImageView mIvShareRight;
    @BindView(R.id.webview)
    WebView mWebview;

    List<String> services = new ArrayList<>();
    List<String> images = new ArrayList<>();
    //商品图片展示
    List<GoodDetail.DataBean.ImagesBean> mListImage = new ArrayList<>();
    //商品库存
    List<GoodDetail.DataBean.GoodsPriceStoresBean> mListGoodsStores = new ArrayList<>();
    //商品所提供的服务
    List<GoodDetail.DataBean.ServicesBean> mListServices = new ArrayList<>();
    //商品的SKU
    List<GoodDetail.DataBean.SpecificationsBean> mListSpecifications = new ArrayList<>();

    DialogSku dialogSku;

    private int mGoodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        iniData();
        initView();
        iniEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(GoodDetail.DataBean goodBean) {
        dialogSku.setData(goodBean.getTransportMoney(), goodBean.getShopName());
        //更新界面
        mTvTitle.setText(goodBean.getGoodsName());
        mTvDesc.setText(goodBean.getFeature());
        mTvPrice.setText("¥ " + goodBean.getMinPrice());
        mTvPriceFare.setText("¥ " + goodBean.getTransportMoney());
        mTvAmount.setText(goodBean.getSellerNumber() + "");
        //获取List
        mListImage = goodBean.getImages();
        mListServices = goodBean.getServices();
        mListGoodsStores = goodBean.getGoodsPriceStores();
        mListSpecifications = goodBean.getSpecifications();

        images.clear();
        services.clear();
        for (int i = 0, size = mListImage.size(); i < size; i++) {
            String url = mListImage.get(i).getAccessUrl();
            images.add(url);
        }
        mBannerDetails.setImages(images).setImageLoader(new GlideImageLoader()).setDelayTime(3000).start();

        for (int i = 0, size = mListServices.size(); i < size; i++) {
            String service = mListServices.get(i).getName();
            services.add(service);
        }
        //添加支持的服务到布局
        mTaglayout.setAdapter(new TagAdapter<String>(services) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.tag, mTaglayout, false);
                TextView textView = (TextView) view.findViewById(R.id.tv_support);
                textView.setText(services.get(position));
                return view;
            }
        });
        mWebview.loadUrl(goodBean.getGoodsDetail());

    }

    private void iniData() {

        Bundle bundle = getIntent().getExtras();
        mGoodId = bundle.getInt("goodId");
        Log.e(BUG_TAG, mGoodId + "");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", mGoodId);
        HttpRequest.get("http://mk.shiwaixiangcun.cn/mi/goods/detail.json", hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                GoodDetail goodDetail = JsonUtil.fromJson(responseJson, GoodDetail.class);
                if (null == goodDetail) {
                    return;
                }
                if (1001 == goodDetail.getResponseCode()) {
                    GoodDetail.DataBean data = goodDetail.getData();
                    EventBus.getDefault().post(data);
                }
            }

            @Override
            public void onFailed(Exception e) {
            }
        });
    }

    /**
     * 初始化点击事件
     */
    private void iniEvent() {
        mTaglayout.setClickable(false);
        mBackLeft.setOnClickListener(this);
        mLlayoutService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogSupport dialogSupport = new DialogSupport(mContext);
                dialogSupport.show();
                dialogSupport.setData(mListServices);

            }
        });
        mIvShareRight.setOnClickListener(this);
        mRlChoice.setOnClickListener(this);
        mBtnPurchase.setOnClickListener(this);
    }

    /**
     * 初始化视图
     */

    private void initView() {
        mIvShareRight.setVisibility(View.VISIBLE);
        mBannerDetails.setBannerStyle(BannerConfig.NOT_INDICATOR);
        dialogSku = new DialogSku(mContext, R.style.AlertDialogStyle, mGoodId);
        mTvPageName.setText("商品详情");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_share_right:
                finish();
                break;
            case R.id.back_left:
                finish();
                break;
            case R.id.rl_choice:

                dialogSku.show();
                break;
            case R.id.btn_purchase:
                dialogSku.show();
                break;
        }
    }

    static class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .into(imageView);
        }
    }
}
