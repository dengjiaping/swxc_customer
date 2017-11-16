package com.shiwaixiangcun.customer.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ToolCategoryBean;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/10/18
 */

public class AdapterService extends BaseSectionQuickAdapter<AdapterService.MySection, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public AdapterService(List<MySection> data) {
        super(R.layout.item_category_service, R.layout.item_section_service, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MySection item) {

        int adapterPosition = helper.getAdapterPosition();
        if (adapterPosition == 0) {
            helper.getView(R.id.view_divider).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_section, item.getStrSection());

    }

    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        helper.setText(R.id.tv_tool_name, item.getTreeBean().getName());
        ImageView ivTool = helper.getView(R.id.iv_tool);

        ImageDisplayUtil.showImageView(mContext, item.getTreeBean().getImageLink(), ivTool);

    }

    public static class MySection extends SectionEntity<ToolCategoryBean.ChildrenBeanX.ChildrenBean> {
        ToolCategoryBean.ChildrenBeanX.ChildrenBean mTreeBean;
        String strSection;
        String strType;
        String typeJson;

        public MySection(boolean isHeader, String header, String strType, String typeJson) {
            super(isHeader, header);
            this.strSection = header;
            this.strType = strType;
            this.typeJson = typeJson;
        }

        public MySection(ToolCategoryBean.ChildrenBeanX.ChildrenBean treeBean, String strType, String typeJson) {
            super(treeBean);
            this.mTreeBean = treeBean;
            this.strType = strType;
            this.typeJson = typeJson;
        }

        public String getStrType() {
            return strType;
        }

        public void setStrType(String strType) {
            this.strType = strType;
        }

        public String getTypeJson() {
            return typeJson;
        }

        public void setTypeJson(String typeJson) {
            this.typeJson = typeJson;
        }

        public ToolCategoryBean.ChildrenBeanX.ChildrenBean getTreeBean() {
            return mTreeBean;
        }

        public void setTreeBean(ToolCategoryBean.ChildrenBeanX.ChildrenBean treeBean) {
            mTreeBean = treeBean;
        }

        public String getStrSection() {
            return strSection;
        }

        public void setStrSection(String strSection) {
            this.strSection = strSection;
        }
    }

}
