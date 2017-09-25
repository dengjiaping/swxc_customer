package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterTag;
import com.shiwaixiangcun.customer.model.Keyword;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.flowtag.FlowTagLayout;
import com.shiwaixiangcun.customer.widget.flowtag.OnTagClickListener;
import com.shiwaixiangcun.customer.widget.flowtag.OnTagSelectListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.edit_search)
    EditText mEditSearch;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fowtag)
    FlowTagLayout mFowTagLayout;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;


    private String mSearchString;
    private Keyword keyword;
    private AdapterTag mTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Bundle bundle = getIntent().getExtras();
        keyword = bundle.getParcelable("keyword");
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        mTagAdapter = new AdapterTag(this);
        mFowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        mFowTagLayout.setAdapter(mTagAdapter);
        mTagAdapter.onlyAddAll(keyword.getHotList());
        mTagAdapter.notifyDataSetChanged();
        mEditSearch.setText(keyword.getGuide());
        mFowTagLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                Snackbar.make(view, "颜色:" + parent.getAdapter().getItem(position), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Toast.makeText(mContext, "点击", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("name", keyword.getHotList().get(position));
                intent.setClass(mContext, SearchResultActivity.class);
                startActivity(intent);
//                readyGo(SearchResultActivity.class, bundle);
                finish();

            }
        });
        mFowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList == null || selectedList.size() == 0) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int i : selectedList) {
                    stringBuilder.append(parent.getAdapter().getItem(i));
                }
                mFowTagLayout.clearAllOption();
                Bundle bundle = new Bundle();
                bundle.putString("name", stringBuilder.toString());
                readyGo(SearchResultActivity.class, bundle);
            }
        });
        mBackLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                mSearchString = mEditSearch.getText().toString();
                Log.e(BUG_TAG, mSearchString);
                Bundle bundle = new Bundle();
                bundle.putString("name", mSearchString);
                readyGo(SearchResultActivity.class, bundle);
                return false;
            }
        });
    }


}
