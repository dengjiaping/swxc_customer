package com.shiwaixiangcun.customer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.shiwaixiangcun.customer.model.SurroundmerchantTypeBean;
import com.shiwaixiangcun.customer.presenter.impl.HomeSurroundImpl;
import com.shiwaixiangcun.customer.pullableview.MyListener;
import com.shiwaixiangcun.customer.pullableview.PullToRefreshLayout;
import com.shiwaixiangcun.customer.pullableview.PullableListView;
import com.shiwaixiangcun.customer.ui.IHomeSurroundView;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.MyGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.shiwaixiangcun.customer.app.App.getContext;

public class SurroundLifeActivity extends AppCompatActivity implements View.OnClickListener,IHomeSurroundView{

    private ChangeLightImageView back_left;
    private TextView tv_page_name;
    private PullableListView lv_surround;
    private List<String> list_surround = new ArrayList<>();
    private MyGridView mgv_merch_type;
    private List<SurroundmerchantTypeBean.DataBean> list_merch_type = new ArrayList<>();
    private MerchTypeAdapter merchTypeAdapter;
    private HomeSurroundImpl homeSurroundImpl;
    private List<AllMerchBean.DataBean.ElementsBean> list_merch_all = new ArrayList<>();
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
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        lv_surround = (PullableListView) findViewById(R.id.lv_surround);



        MyListener myListener = new MyListener();
        PullToRefreshLayout refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refresh_view.setOnRefreshListener(myListener);
        myListener.setRefreshListener(new MyListener.onRefreshListener() {
            @Override
            public void refreshScence(boolean isnot) {
                homeSurroundImpl.setAllMerchClick(SurroundLifeActivity.this,"");
                homeSurroundImpl.setBgaAdpaterAndClick(SurroundLifeActivity.this);
            }
        });




        View surround_view = LayoutInflater.from(this).inflate(R.layout.head_surround, null);
        mgv_merch_type = (MyGridView) surround_view.findViewById(R.id.mgv_merch_type);
        tv_first_name_merch = (TextView) surround_view.findViewById(R.id.tv_first_name_merch);
        tv_first_feature = (TextView) surround_view.findViewById(R.id.tv_first_feature);
        iv_first_merch = (ImageView) surround_view.findViewById(R.id.iv_first_merch);
        ll_merch_wonderful = (LinearLayout) surround_view.findViewById(R.id.ll_merch_wonderful);
        tv_find_wonderful = (TextView) surround_view.findViewById(R.id.tv_find_wonderful);
        iv_find_image_a = (ImageView) surround_view.findViewById(R.id.iv_find_image_a);
        iv_find_image_b = (ImageView) surround_view.findViewById(R.id.iv_find_image_b);
        lv_surround.addHeaderView(surround_view);
    }

    private void initData() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();


        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) iv_first_merch.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20

        linearParams.width = width-100;
        linearParams.height = (width-100)/2;

        iv_first_merch.setLayoutParams(linearParams);

        homeSurroundImpl = new HomeSurroundImpl(this);
        homeSurroundImpl.setBgaAdpaterAndClick(this);
        tv_page_name.setText("周边生活");
        for (int i = 0; i < 6; i++) {
            list_surround.add("周边"+i);
        }
        //商铺类型
        merchTypeAdapter = new MerchTypeAdapter(list_merch_type,this);
        mgv_merch_type.setAdapter(merchTypeAdapter);


        homeSurroundAdapter = new HomeSurroundAdapter(list_merch_all,this);
        lv_surround.setAdapter(homeSurroundAdapter);

//        getlt(list_surround);



        back_left.setOnClickListener(this);

        lv_surround.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SurroundLifeActivity.this,SurroundDetailActivity.class);
                intent.putExtra("merchId",list_merch_all.get(i-1).getId()+"");
                startActivity(intent);
            }
        });
        mgv_merch_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.e("nnnnnnnnnnnnnnnn",i+"");
                String detailId = list_merch_type.get(i).getDetailId();
                if (Utils.isNotEmpty(detailId)){
                    Intent intent = new Intent(SurroundLifeActivity.this,SurroundDetailActivity.class);
                    intent.putExtra("merchId",detailId);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SurroundLifeActivity.this,SurroundTopActivity.class);
                    intent.putExtra("merchant",list_merch_type.get(i).getId()+"");
                    intent.putExtra("nameType",list_merch_type.get(i).getName());
                    startActivity(intent);
                }

            }
        });

        ll_merch_wonderful.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.back_left:
                finish();
                break;
            case R.id.ll_merch_wonderful:
                if (list_merch_all.size() != 0){
                    Intent intent = new Intent(SurroundLifeActivity.this,SurroundDetailActivity.class);
                    intent.putExtra("merchId",all_march_list.get(0).getId()+"");
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(SurroundmerchantTypeBean result) {
        list_merch_type.clear();
        List<SurroundmerchantTypeBean.DataBean> data_merch_type = result.getData();
        if (data_merch_type.size() != 0){

            for (int i = 0; i < data_merch_type.size(); i++) {
                if (data_merch_type.get(i).isAll()){
                    all_id = data_merch_type.get(i).getId()+"";
                }else {
                    list_merch_type.add(data_merch_type.get(i));
                }
            }

            Log.e("rrrrrrrrraaa",list_merch_all.size()+"");
            merchTypeAdapter.notifyDataSetChanged();



            if (homeSurroundImpl != null){
                if (Utils.isNotEmpty(all_id)){
                    homeSurroundImpl.setAllMerchClick(SurroundLifeActivity.this,all_id);
                }

            }
        }

    }

    @Override
    public void setAllMerchResult(AllMerchBean result) {
        list_merch_all.clear();
        tv_find_wonderful.setText("发现精彩");
        iv_find_image_a.setImageResource(R.mipmap.surround_left);
        iv_find_image_b.setImageResource(R.mipmap.surround_right);
        all_march_list = result.getData().getElements();

        if (all_march_list.size() != 0){
            for (int i = 0; i < all_march_list.size(); i++) {
                if (i > 0){
                    list_merch_all.add(all_march_list.get(i));
                }
            }
//            list_merch_all.addAll(all_march_list);
            if (homeSurroundAdapter != null){
                homeSurroundAdapter.notifyDataSetChanged();
            }

            tv_first_name_merch.setText(all_march_list.get(0).getName());
            tv_first_feature.setText(all_march_list.get(0).getFeature());
            if (Utils.isNotEmpty(all_march_list.get(0).getCover())){
                Picasso.with(SurroundLifeActivity.this).load(all_march_list.get(0).getCover()).into(iv_first_merch);
            }
        }

        if (list_merch_all.size() == 0){
            ll_merch_wonderful.setVisibility(View.GONE);
        }else {
            ll_merch_wonderful.setVisibility(View.VISIBLE);
        }


    }


//    private void getlt(final List<SurroundmerchantTypeBean.DataBean> certificates) {
//
//
//
//        for (int i = 0; i < certificates.size(); i++) {
//
//            View view = LayoutInflater.from(this).inflate(R.layout.item_home_surround_image, id_gallery, false);
//            ImageView iv_certificates = (ImageView) view.findViewById(R.id.iv_certificates);
//            TextView tv_name_merch_type = (TextView) view.findViewById(R.id.tv_name_merch_type);
////            if (Utils.isNotEmpty(certificates.get(i).getOrgPath())) {
////                Picasso.with(this).load(certificates.get(i).get()).into(iv_certificates);
////
////            }
//            tv_name_merch_type.setText(certificates.get(i).getName());
//            iv_certificates.setImageResource(R.mipmap.image_black);
//            final int finalI = i;
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.e("vvvviiia","----------aaa"+ finalI);
//                    Intent intent = new Intent(SurroundLifeActivity.this,SurroundTopActivity.class);
//                    startActivity(intent);
//                }
//            });
//            id_gallery.addView(view);
//        }
//    }
}
