package com.shiwaixiangcun.customer.ui.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterCategory;
import com.shiwaixiangcun.customer.interfaces.CheckListener;
import com.shiwaixiangcun.customer.interfaces.RvListener;
import com.shiwaixiangcun.customer.model.CategoryBean;
import com.shiwaixiangcun.customer.ui.fragment.CategoryDetailFragment;
import com.shiwaixiangcun.customer.utils.ItemHeaderDecoration;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MallCategoryActivity extends BaseActivity implements View.OnClickListener, CheckListener {

    @BindView(R.id.back_left)
    ChangeLightImageView backLeft;
    @BindView(R.id.tv_page_name)
    TextView tvPageName;
    @BindView(R.id.top_bar_write)
    RelativeLayout topBarWrite;
    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.flayout_pin)
    FrameLayout flayoutPin;
    List<String> mCategoryNameList = new ArrayList<>();
    private Context mContext;
    private CategoryBean mCategory;
    private AdapterCategory mAdapterCategory;
    private LinearLayoutManager mLinearLayoutManager;
    private CategoryDetailFragment mDetailFragment;
    private boolean isMoved = false;
    private int targetPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_category);
        ButterKnife.bind(this);
        mContext = this;
        requestData();
        initView();
    }

    /**
     * 获取数据
     */
    private void requestData() { //获取asset目录下的资源文件
        OkGo.<String>get(GlobalConfig.getCategory)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mCategory = JsonUtil.fromJson(response.body(), CategoryBean.class);
                        if (mCategory == null) {
                            return;
                        }
                        List<CategoryBean.DataBean> mCategoryList = mCategory.getData();
                        for (int i = 0, count = mCategoryList.size(); i < count; i++) {
                            mCategoryNameList.add(mCategoryList.get(i).getName());
                        }
                        mAdapterCategory.addData(mCategoryNameList);
                        createFragment();
                    }
                });

    }

    private void setChecked(int position, boolean isLeft) {
        if (isLeft) {
            mAdapterCategory.setCheckedPosition(position);
            int count = 0;
            for (int i = 0; i < position; i++) {
                CategoryBean.DataBean dataBean = mCategory.getData().get(i);
                count += dataBean.getChildren().size();
                for (int j = 0; j < dataBean.getChildren().size(); j++) {
                    count += dataBean.getChildren().get(j).getChildren().size();
                }

            }
            mDetailFragment.setData(count);
            ItemHeaderDecoration.setCurrentTag(String.valueOf(targetPosition));
        } else {
            if (isMoved) {
                isMoved = false;

            } else mAdapterCategory.setCheckedPosition(position);
            ItemHeaderDecoration.setCurrentTag(String.valueOf(position));
        }
        moveToCenter(position);
    }

    private void moveToCenter(int position) {
        View childAt = rvCategory.getChildAt(position - mLinearLayoutManager.findFirstVisibleItemPosition());

        if (childAt != null) {
            int y = (childAt.getTop() - rvCategory.getHeight() / 2);
            rvCategory.smoothScrollBy(0, y);
        }
    }

    private void createFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mDetailFragment = CategoryDetailFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("right", (ArrayList<? extends Parcelable>) mCategory.getData());
        mDetailFragment.setArguments(bundle);
        mDetailFragment.setListener(this);
        fragmentTransaction.add(R.id.flayout_pin, mDetailFragment);
        fragmentTransaction.commit();
    }

    private void initView() {
        tvPageName.setText("商品分类");
        mAdapterCategory = new AdapterCategory(mContext, mCategoryNameList, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                setChecked(position, true);
                if (mDetailFragment != null) {
                    isMoved = true;
                    targetPosition = position;
                    setChecked(position, true);
                }
            }
        });
        backLeft.setOnClickListener(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCategory.setAdapter(mAdapterCategory);
        rvCategory.setLayoutManager(mLinearLayoutManager);
        rvCategory.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;
        }
    }

    @Override
    public void check(int position, boolean isScroll) {
        setChecked(position, isScroll);
    }


    /**
     * recyclerView  分割线
     */
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
