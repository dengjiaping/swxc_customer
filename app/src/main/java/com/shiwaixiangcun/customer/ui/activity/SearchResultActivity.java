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
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterSearchResult;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
    @BindView(R.id.rlayout_no_data)
    RelativeLayout mRlayoutNoData;
    private AdapterSearchResult mAdapter;
    private List<ElementBean.ElementsBean> mList = new ArrayList<>();
    private String searchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        EventCenter.getInstance().register(this);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        searchKey = bundle.getString("name");
        Log.e(BUG_TAG, searchKey);
        requestData(searchKey, 1, 10);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventCenter.getInstance().unregister(this);
    }

    /**
     * 从服务器获取数据以后更新
     *
     * @param simpleEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(SimpleEvent simpleEvent) {
        if (simpleEvent.mEventType != SimpleEvent.UPDATE_SEARCH) {
            return;
        }
        switch (simpleEvent.mEventValue) {
            case 1:
                ElementBean guessData = (ElementBean) simpleEvent.getData();
                mList.clear();
                mList.addAll(guessData.getElements());
                mAdapter.notifyDataSetChanged();
                break;


        }
    }

    /**
     * 请求网络数据
     *
     * @param page 页码
     * @param size 每页数目
     */
    private void requestData(String keyword, int page, int size) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("search", keyword);
        httpParams.put("page.pn", page);
        httpParams.put("page.size", size);
        httpParams.put("siteId", 1);
        OkGo.<String>get(GlobalConfig.searchGood)
                .params(httpParams)
                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String jsonString = response.body();
                        Type type = new TypeToken<ResponseEntity<ElementBean>>() {
                        }.getType();
                        Gson gson = new Gson();
                        ResponseEntity<ElementBean> data = gson.fromJson(jsonString, type);
                        if (data == null) {
                            return;
                        }
                        switch (data.getResponseCode()) {
                            case 1001:
                                if (data.getData().getElements().size() == 0) {
                                    mRlayoutNoData.setVisibility(View.VISIBLE);
                                    return;
                                }
                                mRlayoutNoData.setVisibility(View.GONE);
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_SEARCH, 1, data.getData()));
                                break;
                        }
                    }
                });


    }

    private void initView() {


        mRvSearchResult.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AdapterSearchResult(mList);
        mRvSearchResult.setAdapter(mAdapter);
        mBackLeft.setOnClickListener(this);
        mRvSearchResult.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("goodId", mList.get(position).getGoodsId());
                readyGo(GoodDetailActivity.class, bundle);
            }
        });

        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                searchKey = mEditSearch.getText().toString();
                Log.e(BUG_TAG, searchKey);
                requestData(searchKey, 1, 10);
                return false;
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;
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
