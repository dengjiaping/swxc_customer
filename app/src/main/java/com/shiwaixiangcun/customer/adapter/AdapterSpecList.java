package com.shiwaixiangcun.customer.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.shiwaixiangcun.customer.model.GoodDetail;

/**
 * Created by Administrator on 2017/9/12.
 */

public class AdapterSpecList extends BaseListAdapter<GoodDetail.DataBean.SpecificationsBean.AttributesBean> {
    public AdapterSpecList(Activity context) {
        super(context);
    }

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {
        return null;
    }
//    private ArrayList<Long> canChooses = new ArrayList<Long>();
//    private Long chooseId = -1L;
//    private HashMap<String, GoodsInfo> goodsInfoMap;
//    private HashMap<Long, AdapterSpecList> adapterMap;
//    private ArrayList<Long> specIdList;
//    private GoodDetail.DataBean.SpecificationsBean spec;
//    private GoodsChooseListener chooseListener;
//
//    public AdapterSpecList(Context mContext, GoodsSpec spec, HashMap<String, GoodsInfo> goodsInfoMap, HashMap<Long, AdapterSpecList> adapterMap, ArrayList<Long> specIdList, GoodsChooseListener chooseListener) {
//        super(mContext, spec.specValueList);
//        this.spec = spec;
//        this.goodsInfoMap = goodsInfoMap;
//        this.adapterMap = adapterMap;
//        this.specIdList = specIdList;
//        this.chooseListener = chooseListener;
//    }
//    //京东类型的sku
//    @Override
//    public View createView(final int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = mInflater.inflate(R.layout.ui_spec_list_item, null);
//        }
//        TextView spec_item_text = ViewHolder.findViewById(convertView, R.id.spec_item_text);
//        spec_item_text.setEnabled(false);
//        spec_item_text.setSelected(false);
//        spec_item_text.setActivated(false);
//        GoodsSpec.SpecValue item = getItem(position);
//        if (item != null) {
//            if (canChooses.contains(item.specDetailId)) {
//                spec_item_text.setEnabled(true);
//                if (isHaveGoodsInfo(spec.specId,item.specDetailId)){
//                    spec_item_text.setActivated(true);
//                }
//                if (chooseId != null && chooseId.equals(item.specDetailId)) {
//                    spec_item_text.setSelected(true);
//                }
//            }
//            spec_item_text.setText(item.specValueRemark);
//            spec_item_text.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    GoodsSpec.SpecValue item = getItem(position);
//                    if (chooseId != null && chooseId.equals(item.specDetailId)) {
//                        chooseId = -1L;
//                    } else {
//                        chooseId = item.specDetailId;
//                        for (Long tmpspecId : specIdList){
//                            if (!tmpspecId.equals(spec.specId)){
//                                AdapterSpecList adapter = adapterMap.get(tmpspecId);
//                                Long tmpchooseId = adapter.getChooseId();
//                                if (chooseId != null && chooseId > 0) {
//                                    HashMap<Long, Long> specDetailIds = new HashMap<Long, Long>();
//                                    specDetailIds.put(spec.specId,chooseId);
//                                    specDetailIds.put(tmpspecId,tmpchooseId);
//                                    if (getGoodsInfo(specDetailIds)==null){
//                                        adapter.setChooseId(-1L);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    notifyAllAdapter();
//                }
//            });
//        }
//        return convertView;
//    }
////    @Override
////    public View createView(final int position, View convertView, ViewGroup parent) {
////        if (convertView == null) {
////            convertView = mInflater.inflate(R.layout.ui_spec_list_item, null);
////        }
////        TextView spec_item_text = ViewHolder.findViewById(convertView, R.id.spec_item_text);
////        spec_item_text.setEnabled(false);
////        spec_item_text.setSelected(false);
////        spec_item_text.setActivated(true);
////        GoodsSpec.SpecValue item = getItem(position);
////        if (item != null) {
////            if (canChooses.contains(item.specDetailId)&&isHaveGoodsInfo(spec.specId,item.specDetailId)) {
////                spec_item_text.setEnabled(true);
////                if (chooseId != null && chooseId.equals(item.specDetailId)) {
////                    spec_item_text.setSelected(true);
////                }
////            }
////            spec_item_text.setText(item.specValueRemark);
////            spec_item_text.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    GoodsSpec.SpecValue item = getItem(position);
////                    if (chooseId != null && chooseId.equals(item.specDetailId)) {
////                        chooseId = -1L;
////                    } else {
////                        chooseId = item.specDetailId;
////                    }
////                    notifyAllAdapter();
////                }
////            });
////        }
////        return convertView;
////    }
//
//    public void notifyAllAdapter() {
//        String key = "";
//        boolean isfull = true;
//        for (Long tmpspecId : specIdList) {
//            AdapterSpecList adapter = adapterMap.get(tmpspecId);
//            adapter.notifyDataSetChanged();
//            Long chooseId = adapter.getChooseId();
//            if (isfull) {
//                if (chooseId != null && chooseId > 0) {
//                    key = key + chooseId+",";
//                } else {
//                    isfull = false;
//                }
//            }
//        }
//        if (chooseListener != null) {
//            if (isfull) {
//                chooseListener.chooseGoods(goodsInfoMap.get(key));
//            } else {
//                chooseListener.chooseGoods(null);
//            }
//        }
//    }
//
//    public interface GoodsChooseListener {
//        void chooseGoods(GoodsInfo goodsinfo);
//    }
//
//    public Long getChooseId() {
//        return chooseId;
//    }
//
//    public void setChooseId(Long chooseId) {
//        this.chooseId = chooseId;
//    }
//
//    public void addCanChoose(Long specDetailId) {
//        if (!canChooses.contains(specDetailId)) {
//            canChooses.add(specDetailId);
//        }
//    }
//
//    private boolean isHaveGoodsInfo(Long specId,Long specDetailId) {
//        HashMap<Long, Long> specDetailIds = new HashMap<Long, Long>();
//        for (Long tmpspecId : specIdList) {
//            AdapterSpecList adapter = adapterMap.get(tmpspecId);
//            Long chooseId = adapter.getChooseId();
//            if (chooseId != null && chooseId > 0) {
//                specDetailIds.put(tmpspecId, chooseId);
//            }
//        }
//        if (specDetailIds.isEmpty()) {
//            return true;
//        } else {
//            specDetailIds.put(specId, specDetailId);
//            if (getGoodsInfo(specDetailIds) == null) {
//                return false;
//            } else {
//                return true;
//            }
//        }
//    }
//
//    public GoodsInfo getGoodsInfo(HashMap<Long, Long> specDetailIds) {
//        GoodsInfo goodsInfo = null;
//        boolean isfull = true;
//        for (Long tmpspecId : specIdList) {
//            if (!specDetailIds.containsKey(tmpspecId)) {
//                isfull = false;
//                AdapterSpecList adapter = adapterMap.get(tmpspecId);
//                ArrayList<Long> canChooses = adapter.getCanChooses();
//                for (Long tmpspecDetailId : canChooses) {
//                    specDetailIds.put(tmpspecId, tmpspecDetailId);
//                    goodsInfo = getGoodsInfo((HashMap<Long, Long>) specDetailIds.clone());
//                    if (goodsInfo != null) {
//                        break;
//                    }
//                }
//            }
//        }
//        if (isfull) {
//            String key = "";
//            for (Long tmpspecId : specIdList) {
//                key = key + specDetailIds.get(tmpspecId)+",";
//            }
//            goodsInfo = goodsInfoMap.get(key);
//        }
//        return goodsInfo;
//    }
//    public ArrayList<Long> getCanChooses() {
//        return canChooses;
//    }
}
