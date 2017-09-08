package com.shiwaixiangcun.customer.ui.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterSearchResult;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.SearchResult;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.edit_search)
    EditText mEditSearch;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_search_result)
    RecyclerView mRvSearchResult;

    private AdapterSearchResult mAdapter;
    private List<SearchResult.DataBean.ElementsBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_reasult);
        ButterKnife.bind(this);
        initView();
        requestData();
    }

    private void requestData() {
        // TODO: 2017/9/8 向网络请求数据

        HashMap<String, Object> params = new HashMap();
        params.put("goodsSubjectValue", "DailySelection");
        params.put("page.pn", "1");
        params.put("page.size", "6");
        params.put("siteId", "1");
        HttpRequest.get("http://mk.shiwaixiangcun.cn/mi/goods/subject/listpage.json", params, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.e(BUG_TAG, "请求成功");
                SearchResult mallGoods = JsonUtil.fromJson(responseJson, SearchResult.class);
                if (mallGoods != null) {
                    mAdapter.addData(mallGoods.getData().getElements());
                    mAdapter.notifyDataSetChanged();
                }
                super.onSuccess(responseJson);
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(BUG_TAG, "请求失败");

            }
        });
    }

    private void initView() {
        mRvSearchResult.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AdapterSearchResult(mList);
        mRvSearchResult.setAdapter(mAdapter);
        mRvSearchResult.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
        }
    }

    private static class RecycleViewDivider extends RecyclerView.ItemDecoration {
        private final int[] ATTRS = new int[]{android.R.attr.listDivider};
        private Paint mPaint;
        private Drawable mDivider;
        private int mDividerHeight = 3;//分割线高度，默认为1px
        private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL

        /**
         * 默认分割线：高度为2px，颜色为灰色
         *
         * @param context
         * @param orientation 列表方向
         */
        public RecycleViewDivider(Context context, int orientation) {
            if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
                throw new IllegalArgumentException("请输入正确的参数！");
            }
            mOrientation = orientation;
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        /**
         * 自定义分割线
         *
         * @param context
         * @param orientation 列表方向
         * @param drawableId  分割线图片
         */
        public RecycleViewDivider(Context context, int orientation, int drawableId) {
            this(context, orientation);
            mDivider = ContextCompat.getDrawable(context, drawableId);
            mDividerHeight = mDivider.getIntrinsicHeight();
        }

        /**
         * 自定义分割线
         *
         * @param context
         * @param orientation   列表方向
         * @param dividerHeight 分割线高度
         * @param dividerColor  分割线颜色
         */
        public RecycleViewDivider(Context context, int orientation, int dividerHeight, int dividerColor) {
            this(context, orientation);
            mDividerHeight = dividerHeight;
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(dividerColor);
            mPaint.setStyle(Paint.Style.FILL);
        }

        //获取分割线尺寸
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, mDividerHeight);
        }

        //绘制分割线
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        //绘制横向 item 分割线
        private void drawHorizontal(Canvas canvas, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + layoutParams.bottomMargin;
                final int bottom = top + mDividerHeight;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }

        //绘制纵向 item 分割线
        private void drawVertical(Canvas canvas, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + layoutParams.rightMargin;
                final int right = left + mDividerHeight;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }
    }
}
