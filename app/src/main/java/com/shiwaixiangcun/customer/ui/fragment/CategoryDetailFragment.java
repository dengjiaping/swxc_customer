package com.shiwaixiangcun.customer.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterDetail;
import com.shiwaixiangcun.customer.interfaces.CheckListener;
import com.shiwaixiangcun.customer.interfaces.RvListener;
import com.shiwaixiangcun.customer.model.CategoryBean;
import com.shiwaixiangcun.customer.model.RightBean;
import com.shiwaixiangcun.customer.ui.activity.mall.CategoryActivity;
import com.shiwaixiangcun.customer.utils.ItemHeaderDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Administrator
 */
public class CategoryDetailFragment extends Fragment implements CheckListener {

    @BindView(R.id.detail_recyclerView)
    RecyclerView mDetailRecyclerView;
    Unbinder unbinder;
    ArrayList<? extends CategoryBean.DataBean> right;
    private Context mContext;
    private AdapterDetail mAdapterDetail;
    private List<RightBean> mList = new ArrayList<>();
    private int mIndex = 0;
    private GridLayoutManager mGridLayoutManager;
    private boolean move = false;
    private CheckListener mCheckListener;
    private ItemHeaderDecoration mDecoration;

    public CategoryDetailFragment() {
        // Required empty public constructor
    }

    public static CategoryDetailFragment newInstance() {
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        right = getArguments().getParcelableArrayList("right");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_category_detail_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        initView(view);
        initData(right);
        initListener();
    }


    /**
     * rightList  中 name 为一级标题
     *
     * @param rightList
     */
    private void initData(ArrayList<? extends CategoryBean.DataBean> rightList) {


        for (int i = 0, size = rightList.size(); i < size; i++) {
            List<CategoryBean.DataBean.ChildrenBeanX> secondList = rightList.get(i).getChildren();
            for (int j = 0, second = secondList.size(); j < second; j++) {

                //获取二级目录的标题
                RightBean head = new RightBean();
                head.setTitle(true);
                head.setTag(String.valueOf(i));
                head.setTitleName(secondList.get(j).getName());
                mList.add(head);
                List<CategoryBean.DataBean.ChildrenBeanX.ChildrenBean> thirdList = secondList.get(j).getChildren();
                for (int h = 0, third = thirdList.size(); h < third; h++) {
                    CategoryBean.DataBean.ChildrenBeanX.ChildrenBean childrenBean = thirdList.get(h);
                    RightBean body = new RightBean();
                    body.setTitle(false);
                    body.setTag(String.valueOf(i));
                    body.setName(childrenBean.getName());
                    body.setId(childrenBean.getId());
                    body.setCategoryImg(childrenBean.getCategoryImg());
                    body.setParentId(childrenBean.getParentId());
                    body.setParentIds(childrenBean.getParentIds() + "");
                    body.setWeight(childrenBean.getWeight());
                    mList.add(body);
                }
            }
        }

        mAdapterDetail.notifyDataSetChanged();
        mDecoration.setData(mList);

    }

    private void initListener() {

        mDetailRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    move = false;
                    int n = mIndex = mGridLayoutManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < mDetailRecyclerView.getChildCount()) {
                        int top = mDetailRecyclerView.getChildAt(n).getTop();
                        mDetailRecyclerView.smoothScrollBy(0, top);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (move) {
                    move = false;
                    int n = mIndex - mGridLayoutManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < mDetailRecyclerView.getChildCount()) {
                        int top = mDetailRecyclerView.getChildAt(n).getTop();
                        mDetailRecyclerView.scrollBy(0, top);

                    }
                }
            }
        });

    }

    private void initView(View view) {
        mDecoration = new ItemHeaderDecoration(mContext, mList);
        mGridLayoutManager = new GridLayoutManager(mContext, 3);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mList.get(position).isTitle() ? 3 : 1;
            }
        });
        mAdapterDetail = new AdapterDetail(mList, mContext, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("categoryId", mList.get(position).getId());
                bundle.putString("categoryName", mList.get(position).getName());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(mContext, CategoryActivity.class);
                mContext.startActivity(intent);
            }
        });
        mDetailRecyclerView.setLayoutManager(mGridLayoutManager);
        mDetailRecyclerView.setAdapter(mAdapterDetail);
        mDetailRecyclerView.addItemDecoration(mDecoration);
        mDecoration.setCheckListener(mCheckListener);
    }

    private void showRightPage(int i) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setData(int count) {
        mIndex = count;
        mDetailRecyclerView.stopScroll();
        smoothMoveToPosition(count);
    }

    public void setListener(CheckListener listener) {
        this.mCheckListener = listener;
    }

    private void smoothMoveToPosition(int position) {
        int firstItem = mGridLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mGridLayoutManager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            mDetailRecyclerView.scrollToPosition(position);
        } else if (position <= lastItem) {
            int top = mDetailRecyclerView.getChildAt(position - firstItem).getTop();

            mDetailRecyclerView.scrollBy(0, top);
        } else {
            mDetailRecyclerView.scrollToPosition(position);
            move = true;
        }
    }

    @Override
    public void check(int position, boolean isScroll) {
        mCheckListener.check(position, isScroll);

    }
}
