package com.shiwaixiangcun.customer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.HomeSurroundAdapter;
import com.shiwaixiangcun.customer.adapter.MerchTypeAdapter;
import com.shiwaixiangcun.customer.model.AllMerchBean;
import com.shiwaixiangcun.customer.model.SurroundMerchantTypeBean;
import com.shiwaixiangcun.customer.presenter.impl.HomeSurroundImpl;
import com.shiwaixiangcun.customer.ui.IHomeSurroundView;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.MyGridView;
import com.shiwaixiangcun.customer.widget.pullableview.MyListener;
import com.shiwaixiangcun.customer.widget.pullableview.PullToRefreshLayout;
import com.shiwaixiangcun.customer.widget.pullableview.PullableListView;

import java.util.ArrayList;
import java.util.List;

import static com.shiwaixiangcun.customer.app.App.getContext;

/**
 * @author Administrator
 */
public class SurroundLifeActivity extends AppCompatActivity implements View.OnClickListener, IHomeSurroundView {

    private ChangeLightImageView back_left;
    private TextView tv_page_name;
    private PullableListView lv_surround;
    private List<String> list_surround = new ArrayList<>();
    private MyGridView mGvMerchType;

    //商铺类型
    private List<SurroundMerchantTypeBean.DataBean> mListMerchType = new ArrayList<>();
    private MerchTypeAdapter merchTypeAdapter;
    private HomeSurroundImpl homeSurroundImpl;
    private List<AllMerchBean.DataBean.ElementsBean> mListMerchAll = new ArrayList<>();
    private HomeSurroundAdapter homeSurroundAdapter;
    private String all_id = "";
    private TextView tv_first_name_merch;
    private TextView tv_first_feature;
    private ImageView iv_first_merch;
    private LinearLayout ll_merch_wonderful;
    private TextView tv_find_wonderful;
    private ImageView iv_find_image_a;
    private ImageView iv_find_image_b;
    private List<AllMerchBean.DataBean.ElementsBean> all_march_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surround_life);
        initLayout();
        initData();
    }

    private void initLayout() {
        back_left = findViewById(R.id.back_left);
        tv_page_name = findViewById(R.id.tv_page_name);
        lv_surround = findViewById(R.id.lv_surround);


        MyListener myListener = new MyListener();
        PullToRefreshLayout refresh_view = findViewById(R.id.refresh_view);
        refresh_view.setOnRefreshListener(myListener);
        myListener.setRefreshListener(new MyListener.onRefreshListener() {
            @Override
            public void refreshScence(boolean isnot) {
                homeSurroundImpl.setAllMerchClick(SurroundLifeActivity.this, "");
                homeSurroundImpl.setBgaAdpaterAndClick(SurroundLifeActivity.this);
            }
        });


        View surround_view = LayoutInflater.from(this).inflate(R.layout.head_surround, null);
        mGvMerchType = surround_view.findViewById(R.id.mgv_merch_type);
        tv_first_name_merch = surround_view.findViewById(R.id.tv_first_name_merch);
        tv_first_feature = surround_view.findViewById(R.id.tv_first_feature);
        iv_first_merch = surround_view.findViewById(R.id.iv_first_merch);
        ll_merch_wonderful = surround_view.findViewById(R.id.ll_merch_wonderful);
        tv_find_wonderful = surround_view.findViewById(R.id.tv_find_wonderful);
        iv_find_image_a = surround_view.findViewById(R.id.iv_find_image_a);
        iv_find_image_b = surround_view.findViewById(R.id.iv_find_image_b);
        lv_surround.addHeaderView(surround_view);
    }

    private void initData() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) iv_first_merch.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        linearParams.width = width - 100;
        linearParams.height = (width - 100) / 2;
        iv_first_merch.setLayoutParams(linearParams);
        homeSurroundImpl = new HomeSurroundImpl(this);
        homeSurroundImpl.setBgaAdpaterAndClick(this);
        tv_page_name.setText("周边生活");

        //商铺类型
        merchTypeAdapter = new MerchTypeAdapter(mListMerchType, this);
        mGvMerchType.setAdapter(merchTypeAdapter);


        homeSurroundAdapter = new HomeSurroundAdapter(mListMerchAll, this);
        lv_surround.setAdapter(homeSurroundAdapter);
        back_left.setOnClickListener(this);

        lv_surround.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SurroundLifeActivity.this, SurroundDetailActivity.class);
                intent.putExtra("merchId", mListMerchAll.get(i - 1).getId() + "");
                startActivity(intent);
            }
        });
        mGvMerchType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String detailId = mListMerchType.get(i).getDetailId();
                if (Utils.isNotEmpty(detailId)) {
                    Intent intent = new Intent(SurroundLifeActivity.this, SurroundDetailActivity.class);
                    intent.putExtra("merchId", detailId);
                    startActivity(intent);
                } else {
                    if (mListMerchType.get(i).getName().equals("找装修")) {
                        Intent intent = new Intent(SurroundLifeActivity.this, LookDecoratingActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SurroundLifeActivity.this, SurroundTopActivity.class);
                        intent.putExtra("merchant", mListMerchType.get(i).getId() + "");
                        intent.putExtra("nameType", mListMerchType.get(i).getName());
                        startActivity(intent);
                    }
                }

            }
        });

        ll_merch_wonderful.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.ll_merch_wonderful:
                if (mListMerchAll.size() != 0) {
                    Intent intent = new Intent(SurroundLifeActivity.this, SurroundDetailActivity.class);
                    intent.putExtra("merchId", all_march_list.get(0).getId() + "");
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(SurroundMerchantTypeBean result) {
        mListMerchType.clear();
        List<SurroundMerchantTypeBean.DataBean> data_merch_type = result.getData();
        if (data_merch_type.size() != 0) {

            for (int i = 0; i < data_merch_type.size(); i++) {
                if (data_merch_type.get(i).isAll()) {
                    all_id = data_merch_type.get(i).getId() + "";
                } else {
                    mListMerchType.add(data_merch_type.get(i));
                }
            }
            SurroundMerchantTypeBean.DataBean.ImageBean imageBean = new SurroundMerchantTypeBean.DataBean.ImageBean(R.drawable.decorate + "", "", "" + R.drawable.decorate);
            SurroundMerchantTypeBean.DataBean dataBean = new SurroundMerchantTypeBean.DataBean(false, "", 0, imageBean, "找装修", "merchantType");
            mListMerchType.add(dataBean);

            merchTypeAdapter.notifyDataSetChanged();


            if (homeSurroundImpl != null) {
                if (Utils.isNotEmpty(all_id)) {
                    homeSurroundImpl.setAllMerchClick(SurroundLifeActivity.this, all_id);
                }

            }
        }

    }

    @Override
    public void setAllMerchResult(AllMerchBean result) {
        mListMerchAll.clear();
        tv_find_wonderful.setText("发现精彩");
        iv_find_image_a.setImageResource(R.mipmap.surround_left);
        iv_find_image_b.setImageResource(R.mipmap.surround_right);
        all_march_list = result.getData().getElements();

        if (all_march_list.size() != 0) {
            for (int i = 0; i < all_march_list.size(); i++) {
                if (i > 0) {
                    mListMerchAll.add(all_march_list.get(i));
                }
            }
            if (homeSurroundAdapter != null) {
                homeSurroundAdapter.notifyDataSetChanged();
            }

            tv_first_name_merch.setText(all_march_list.get(0).getName());
            tv_first_feature.setText(all_march_list.get(0).getFeature());
            if (Utils.isNotEmpty(all_march_list.get(0).getCover())) {


                ImageDisplayUtil.showImageView(SurroundLifeActivity.this, all_march_list.get(0).getCover(), iv_first_merch);
            }
        }

        if (mListMerchAll.size() == 0) {
            ll_merch_wonderful.setVisibility(View.GONE);
        } else {
            ll_merch_wonderful.setVisibility(View.VISIBLE);
        }


    }


}
