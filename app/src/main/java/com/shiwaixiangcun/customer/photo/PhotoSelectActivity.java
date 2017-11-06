package com.shiwaixiangcun.customer.photo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.photo.adpater.FolderListAdapter;
import com.shiwaixiangcun.customer.photo.adpater.PhotoListAdapter;
import com.shiwaixiangcun.customer.photo.core.FunctionConfig;
import com.shiwaixiangcun.customer.photo.core.PhotoFinal;
import com.shiwaixiangcun.customer.photo.model.PhotoFolderInfo;
import com.shiwaixiangcun.customer.photo.model.PhotoInfo;
import com.shiwaixiangcun.customer.photo.utils.PhotoUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * 这里就是展示图片的界面了
 * <p/>
 * Created by liweiqin on 2016/1/31.
 */
public class PhotoSelectActivity extends BasePhotoActivity implements View.OnClickListener, AbsListView.OnItemClickListener, Callback {

    private static final int HANDLER_REFRESH_LIST_EVENT = 1002;

    private Callback mCallback;

    /**
     * loading...
     */
    private TextView tv_empty_view;
    /**
     * back
     */
    private ImageView iv_back;
    /**
     * finish to select picture
     */
    private TextView tv_select_finish;

    /**
     * finish to selct picture folder
     */
    private TextView tv_photo_folder;

    /***
     * photo gridview
     */
    private GridView gv_photo_list;

    /**
     * show picture folder
     */
    private LinearLayout ll_folder_panel;
    /**
     * photoFolder  listview
     */
    private ListView lv_folder_list;

    /**
     * 当前展示图片的list
     */
    private List<PhotoInfo> mCurrentList;
    private PhotoListAdapter mPhotoListAdapter;


    /**
     * 所有的图片文件列表
     */
    private List<PhotoFolderInfo> mAllPhotoFolderList;
    private FolderListAdapter mFolderListAdapter;

    private FunctionConfig mFunctionConfig;
    /**
     * current selected picture
     */
    private LinkedHashMap<String, PhotoInfo> mSelectPhotoMap = new LinkedHashMap<>();


    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == HANDLER_REFRESH_LIST_EVENT) {
                refreshSelectCount();
                mPhotoListAdapter.notifyDataSetChanged();
                mFolderListAdapter.notifyDataSetChanged();
                if (mAllPhotoFolderList.get(0).getPhotoInfoList() == null || mAllPhotoFolderList.get(0).getPhotoInfoList().size() == 0) {
                    tv_empty_view.setText("没有照片");
                }
                gv_photo_list.setEnabled(true);
                tv_select_finish.setEnabled(true);
                lv_folder_list.setEnabled(true);
            }
        }
    };
    private ChangeLightImageView back_left;
    private TextView tv_page_name;
    private TextView tv_top_right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFunctionConfig = PhotoFinal.getFunctionConfig();
        setContentView(R.layout.activity_select);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        initView();
        setClikListener();
        setCallback(this);

        mCurrentList = new ArrayList<PhotoInfo>();
        mPhotoListAdapter = new PhotoListAdapter(PhotoSelectActivity.this, mCurrentList, mSelectPhotoMap, mScreenWidth);
        gv_photo_list.setEmptyView(tv_empty_view);
        gv_photo_list.setAdapter(mPhotoListAdapter);
        mAllPhotoFolderList = new ArrayList<PhotoFolderInfo>();
        mFolderListAdapter = new FolderListAdapter(PhotoSelectActivity.this, mAllPhotoFolderList);
        lv_folder_list.setAdapter(mFolderListAdapter);

        refreshSelectCount();
        getPhotos();


    }


    private void refreshSelectCount() {
//        tv_select_finish.setText(getString(R.string.selected, mSelectPhotoMap.size(), mFunctionConfig.getMaxSize()));
        tv_photo_folder.setText("已添加"+mSelectPhotoMap.size()+"张");
    }

    private void setClikListener() {
        iv_back.setOnClickListener(this);
        tv_select_finish.setOnClickListener(this);
//        tv_photo_folder.setOnClickListener(this);
        lv_folder_list.setOnItemClickListener(this);
        gv_photo_list.setOnItemClickListener(this);
        back_left.setOnClickListener(this);

    }

    private void initView() {
        tv_empty_view = findViewById(R.id.tv_empty_view);
        iv_back = findViewById(R.id.iv_back);
        tv_select_finish = findViewById(R.id.tv_select_finish);
        tv_photo_folder = findViewById(R.id.tv_photo_folder);
        gv_photo_list = findViewById(R.id.gv_photo_list);
        ll_folder_panel = findViewById(R.id.ll_folder_panel);
        lv_folder_list = findViewById(R.id.lv_folder_list);
        back_left = findViewById(R.id.back_left);
        tv_page_name = findViewById(R.id.tv_page_name);
        tv_top_right = findViewById(R.id.tv_top_right);
        this.tv_page_name.setText("添加照片");
        tv_top_right.setText("相机胶卷");
        tv_top_right.setTextColor(Color.parseColor("#1CCC8C"));
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_select_finish:
                if (PhotoFinal.getCallback() != null)
                    PhotoFinal.getCallback().onHandlerSuccess(PhotoFinal.REQUEST_CODE_MUTI, new ArrayList<PhotoInfo>(mSelectPhotoMap.values()));
                this.finish();
                break;
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.tv_photo_folder:
                if (ll_folder_panel.getVisibility() == View.GONE) {
                    ll_folder_panel.setVisibility(View.VISIBLE);
                } else {
                    ll_folder_panel.setVisibility(View.GONE);
                }
                break;
            case R.id.back_left:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 获取照片集
     * <p/>
     * 先清除图片集列表 再加载图片集列表 清除单前图集的所有图片 再加载当前图片
     */
    public void getPhotos() {
        tv_select_finish.setEnabled(false);
        gv_photo_list.setEnabled(false);
        lv_folder_list.setEnabled(false);
        new Thread() {
            @Override
            public void run() {
                super.run();
                mAllPhotoFolderList.clear();
                List<PhotoFolderInfo> allFolderList = PhotoUtil.getAllPhotoFolder(PhotoSelectActivity.this, mSelectPhotoMap);
                mAllPhotoFolderList.addAll(allFolderList);

                mCurrentList.clear();
                if (allFolderList.size() > 0) {
                    if (allFolderList.get(0).getPhotoInfoList() != null) {
                        //多加一张拍照的图片
                        setAllPhotoList(allFolderList);
                        mFolderListAdapter.setmSelectPhotoFolderInfo(allFolderList.get(0));
                    }
                }
                refreshAdapter();
            }
        }.start();

    }

    private void setAllPhotoList(List<PhotoFolderInfo> allFolderList) {
        PhotoInfo photoInfo = new PhotoInfo();
        photoInfo.setPhotoId(-100);
        photoInfo.setPhotoPath(null);
        photoInfo.setDrawable(R.mipmap.photo_camera);
        mCurrentList.add(photoInfo);
        mCurrentList.addAll(allFolderList.get(0).getPhotoInfoList());
    }

    private void refreshAdapter() {
        mHanlder.sendEmptyMessageDelayed(HANDLER_REFRESH_LIST_EVENT, 100);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.lv_folder_list) {
            onItemClickForFolderList(position);
        } else {
            onItemClickForPhotoList(view, position);
        }
    }


    private void onItemClickForFolderList(int position) {
        PhotoFolderInfo photoFolderInfo = mAllPhotoFolderList.get(position);
        tv_photo_folder.setText(photoFolderInfo.getFolderName());
        ll_folder_panel.setVisibility(View.GONE);
        mCurrentList.clear();
        if (position == 0) {
            setAllPhotoList(mAllPhotoFolderList);
        } else {
            mCurrentList.addAll(photoFolderInfo.getPhotoInfoList());
        }
        mFolderListAdapter.setmSelectPhotoFolderInfo(photoFolderInfo);

        mPhotoListAdapter.notifyDataSetChanged();
        mFolderListAdapter.notifyDataSetChanged();
        gv_photo_list.smoothScrollToPosition(0);
        if (mCurrentList.size() == 0) {
            tv_empty_view.setText("没有照片");
        }

    }


    private void onItemClickForPhotoList(View view, int position) {
        PhotoListAdapter.PhotoViewHolder holder = (PhotoListAdapter.PhotoViewHolder) view.getTag();
        PhotoInfo photoInfo = mCurrentList.get(position);
        if (photoInfo.getPhotoId() != -100 && photoInfo.getDrawable() == 0) {
            if (mSelectPhotoMap.containsKey(photoInfo.getPhotoPath())) {
                // selected
                mSelectPhotoMap.remove(photoInfo.getPhotoPath());
                if (holder != null) holder.iv_check.setSelected(false);
            } else {
                // un selected
                if (mSelectPhotoMap.size() == mFunctionConfig.getMaxSize()) {
                    Toast.makeText(this, "最多只能选择" + PhotoFinal.getFunctionConfig().getMaxSize(), Toast.LENGTH_SHORT).show();
                    return;
                }
                mSelectPhotoMap.put(photoInfo.getPhotoPath(), photoInfo);
                if (holder != null) holder.iv_check.setSelected(true);
            }
        } else {
            PhotoFinal.openCamera(PhotoFinal.getCallback(), mCallback);
        }
        refreshSelectCount();
    }


    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    @Override
    public void callback() {
        mAllPhotoFolderList.clear();
        mSelectPhotoMap.clear();
        this.finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }
}
