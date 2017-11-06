package com.shiwaixiangcun.customer.ui.activity.heath;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ToolBean;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Desc:    健康评测
 */
public class HealthEvaluationActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_grid)
    RecyclerView mGridView;
    private GridAdapter mGridAdapter;
    private List<ToolBean> mToolList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_evaluation);
        ButterKnife.bind(this);
        initData();
        initView();

    }

    private void initData() {

        mToolList = new ArrayList<>();
        ToolBean toolBean1 = new ToolBean(1, "中医体质", R.drawable.ctcm);
        ToolBean toolBean2 = new ToolBean(2, "饮食生活习惯", R.drawable.lifestyle);
        ToolBean toolBean3 = new ToolBean(3, "内分泌系统", R.drawable.endocrinium);
        ToolBean toolBean4 = new ToolBean(4, "免疫系统", R.drawable.system_immune);
        ToolBean toolBean5 = new ToolBean(5, "消化系统", R.drawable.system_digestive);
        ToolBean toolBean6 = new ToolBean(6, "呼吸系统", R.drawable.system_respiratory);
        ToolBean toolBean7 = new ToolBean(7, "循环系统", R.drawable.system_cyclic);
        ToolBean toolBean8 = new ToolBean(8, "运动系统", R.drawable.system_motor);
        ToolBean toolBean9 = new ToolBean(9, "心理健康", R.drawable.mental_health);
        ToolBean toolBean10 = new ToolBean(10, "男性健康", R.drawable.male_health);
        ToolBean toolBean11 = new ToolBean(11, "女性健康", R.drawable.female_health);
        mToolList.add(toolBean1);
        mToolList.add(toolBean2);
        mToolList.add(toolBean3);
        mToolList.add(toolBean4);
        mToolList.add(toolBean5);
        mToolList.add(toolBean6);
        mToolList.add(toolBean7);
        mToolList.add(toolBean8);
        mToolList.add(toolBean9);
        mToolList.add(toolBean10);
        mToolList.add(toolBean11);

    }

    private void initView() {
        mTvPageName.setText("健康评测");
        mGridAdapter = new GridAdapter(R.layout.layout_tool, mToolList);
        mGridView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mGridView.setAdapter(mGridAdapter);
        mBackLeft.setOnClickListener(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this);
        mGridView.addItemDecoration(dividerItemDecoration);
        mGridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("healthEvaluating", mToolList.get(position));
                readyGo(CommonActivity.class, bundle);
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

    /**
     * 分割线
     */
    public final static class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
        private Drawable mDivider;

        public DividerItemDecoration(Context context) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

            drawHorizontal(c, parent);
            drawVertical(c, parent);

        }

        private int getSpanCount(RecyclerView parent) {
            // 列数
            int spanCount = -1;
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {

                spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                spanCount = ((StaggeredGridLayoutManager) layoutManager)
                        .getSpanCount();
            }
            return spanCount;
        }

        public void drawHorizontal(Canvas c, RecyclerView parent) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getLeft() - params.leftMargin;
                final int right = child.getRight() + params.rightMargin
                        + mDivider.getIntrinsicWidth();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        public void drawVertical(Canvas c, RecyclerView parent) {
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);

                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getTop() - params.topMargin;
                final int bottom = child.getBottom() + params.bottomMargin;
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicWidth();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                    int childCount) {
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int orientation = ((StaggeredGridLayoutManager) layoutManager)
                        .getOrientation();
                if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                    if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                    {
                        return true;
                    }
                } else {
                    childCount = childCount - childCount % spanCount;
                    if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                        return true;
                }
            }
            return false;
        }

        private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                                  int childCount) {
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                    return true;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int orientation = ((StaggeredGridLayoutManager) layoutManager)
                        .getOrientation();
                // StaggeredGridLayoutManager 且纵向滚动
                if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                    childCount = childCount - childCount % spanCount;
                    // 如果是最后一行，则不需要绘制底部
                    if (pos >= childCount)
                        return true;
                } else
                // StaggeredGridLayoutManager 且横向滚动
                {
                    // 如果是最后一行，则不需要绘制底部
                    if ((pos + 1) % spanCount == 0) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition,
                                   RecyclerView parent) {
            int spanCount = getSpanCount(parent);
            int childCount = parent.getAdapter().getItemCount();
            if (isLastRaw(parent, itemPosition, spanCount, childCount))// 如果是最后一行，则不需要绘制底部
            {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            } else if (isLastColum(parent, itemPosition, spanCount, childCount))// 如果是最后一列，则不需要绘制右边
            {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(),
                        mDivider.getIntrinsicHeight());
            }
        }
    }

    /**
     * Adapter
     */
    private final class GridAdapter extends BaseQuickAdapter<ToolBean, BaseViewHolder> {


        public GridAdapter(@LayoutRes int layoutResId, List<ToolBean> toolList) {
            super(R.layout.layout_health, toolList);
        }

        @Override
        protected void convert(BaseViewHolder helper, ToolBean item) {
            helper.setText(R.id.tv_tool_name, item.name);
            Glide.with(mContext).load(item.pic).into((ImageView) helper.getView(R.id.iv_tool));
        }

    }
}
