package com.shiwaixiangcun.customer.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterDetail;
import com.shiwaixiangcun.customer.interfaces.RvListener;
import com.shiwaixiangcun.customer.model.CategoryBean;
import com.shiwaixiangcun.customer.model.RightBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoryDetailFragment extends Fragment {

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

    public CategoryDetailFragment() {
        // Required empty public constructor
    }

    public static CategoryDetailFragment newInstance() {
        CategoryDetailFragment fragment = new CategoryDetailFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        right = getArguments().getParcelableArrayList("right");

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
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
        initData(right);
        initView(view);
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
                head.setTitleName(secondList.get(j).getName());
                mList.add(head);
                List<CategoryBean.DataBean.ChildrenBeanX.ChildrenBean> thirdList = secondList.get(j).getChildren();
                for (int h = 0, third = thirdList.size(); h < third; h++) {
                    CategoryBean.DataBean.ChildrenBeanX.ChildrenBean childrenBean = thirdList.get(h);
                    RightBean body = new RightBean();
                    body.setTitle(false);
                    body.setName(childrenBean.getName());
                    body.setId(childrenBean.getId());
                    body.setCategoryImg(childrenBean.getCategoryImg());
                    body.setParentId(childrenBean.getParentId());
                    body.setParentIds(childrenBean.getParentIds());
                    body.setWeight(childrenBean.getWeight());
                    mList.add(body);
                }
            }
        }
        mAdapterDetail = new AdapterDetail(mList, mContext, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                Toast.makeText(mContext, mList.get(position).getName(), Toast.LENGTH_SHORT).show();

            }
        });

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
        mGridLayoutManager = new GridLayoutManager(mContext, 3);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mList.get(position).isTitle() ? 3 : 1;
            }
        });
        mDetailRecyclerView.setLayoutManager(mGridLayoutManager);
        mDetailRecyclerView.setAdapter(mAdapterDetail);
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

    private void smoothMoveToPosition(int position) {
        int firstItem = mGridLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mGridLayoutManager.findLastVisibleItemPosition();
        Log.d("first>>>>>>>>>", firstItem + "");
        Log.d("lash>>>>>>>>>>", lastItem + "");
        if (position <= firstItem) {
            mDetailRecyclerView.scrollToPosition(position);
        } else if (position <= lastItem) {
            Log.d("pos---->", String.valueOf(position) + "VS" + firstItem);
            int top = mDetailRecyclerView.getChildAt(position - firstItem).getTop();
            Log.d("top---->", String.valueOf(top));
            mDetailRecyclerView.scrollBy(0, top);
        } else {
            mDetailRecyclerView.scrollToPosition(position);
            move = true;
        }
    }
}
