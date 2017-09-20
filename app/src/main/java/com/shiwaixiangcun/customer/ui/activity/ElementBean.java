package com.shiwaixiangcun.customer.ui.activity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 * <p>
 * 商城首页的数据
 */

public class ElementBean {


    /**
     * elements : [{"feature":"5斤装，现摘发货","goodsId":137,"goodsName":"云南红肉红心火龙果5斤 新鲜水果批发包邮当季时令现摘水果","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/5C/rBKx51m_koCAF7dXAACW98spLrw708.jpg","minPrice":45.89,"subjectId":245},{"feature":"鞍山南果梨正宗包邮低价南国梨免运费9斤新鲜水果特产精品","goodsId":132,"goodsName":"四川阿坝金川雪梨鸡腿梨正宗雪梨水果包邮新鲜9斤现摘现发","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/5A/rBKx51m7fCaAAnx_AABh8P8cd7c479.jpg","minPrice":33,"subjectId":245},{"feature":"触控屏 ip68防水 菜鸟物流 只换不修","goodsId":145,"goodsName":"乐心智能手环测心率防水计步器安卓苹果男女蓝牙运动手表mamb","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/5C/rBKx51m_dJKAUQAyAAAMMLH_CqI860.jpg","minPrice":168.88,"subjectId":245},{"feature":" 香甜如蜜 2个瓜装约9斤","goodsId":128,"goodsName":"新疆哈密瓜 新鲜水果吐鲁番西州蜜甜瓜2个约10斤包邮","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/58/rBKx51m6S5yAQrqXAABYFg08cFI369.jpg","minPrice":55.9,"subjectId":245},{"feature":"新品特惠 包退换货 送运费险","goodsId":125,"goodsName":"NIAN JEEP吉普盾牛仔裤男弹力宽松直筒商务休闲男士大码","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/5D/rBKx51m_lJ6AXf4oAABIDMlR8jE146.jpg","minPrice":78.88,"subjectId":245},{"feature":"品名：脆甜丰水梨，秒发果园现摘发货 包邮包售后 产地：山东 ","goodsId":124,"goodsName":"脆甜丰水梨 现货5斤 新鲜水果 秋梨 梨 果园现摘发货 包邮","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/5C/rBKx51m_YM-AM8BrAAAuIS1CuU8636.jpg","minPrice":6,"subjectId":245},{"feature":"肉厚多汁 回馈老顾客","goodsId":136,"goodsName":"【买4斤送4斤】新鲜水果四川攀枝花大芒果8斤 黄青皮芒果批发","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/5D/rBKx51m_k5aATmW6AAAyxNR9tng649.jpg","minPrice":38.88,"subjectId":245},{"feature":"洗洗带皮吃 新鲜脆甜 天然种植","goodsId":123,"goodsName":"王小二 新鲜苹果水果批发包邮山东烟台栖霞红富士5斤果园平果","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/58/rBKx51m54mGAfphOAAA4yy5sgE4776.jpg","minPrice":28.8,"subjectId":245},{"feature":"比脸还大的芒果 肉厚多汁","goodsId":109,"goodsName":"【第2件6.6元 合并发8斤装】太阳果 攀枝花凯特大芒果 新","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/5D/rBKx51m_leWAKmIxAAO8nzNBP9s905.jpg","minPrice":5.5,"subjectId":245},{"feature":"双卫星定位IP67防水","goodsId":138,"goodsName":"爱牵挂S1新款老年人儿童智能定位黄手环 一键紧急呼叫","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/5B/rBKx51m_Q7-AS8EOAAPE-iuZrCY933.jpg","minPrice":298,"subjectId":245}]
     * page : 1
     * size : 10
     * totalAmount : 11
     * totalPages : 2
     */

    private int page;
    private int size;
    private int totalAmount;
    private int totalPages;
    private List<ElementsBean> elements;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ElementsBean> getElements() {
        return elements;
    }

    public void setElements(List<ElementsBean> elements) {
        this.elements = elements;
    }

    public static class ElementsBean {
        /**
         * feature : 5斤装，现摘发货
         * goodsId : 137
         * goodsName : 云南红肉红心火龙果5斤 新鲜水果批发包邮当季时令现摘水果
         * imagePath : http://resource.shiwaixiangcun.cn/group1/M00/00/5C/rBKx51m_koCAF7dXAACW98spLrw708.jpg
         * minPrice : 45.89
         * subjectId : 245
         */

        private String feature;
        private int goodsId;
        private String goodsName;
        private String imagePath;
        private double minPrice;
        private int subjectId;

        public String getFeature() {
            return feature;
        }

        public void setFeature(String feature) {
            this.feature = feature;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public double getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(double minPrice) {
            this.minPrice = minPrice;
        }

        public int getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(int subjectId) {
            this.subjectId = subjectId;
        }
    }
}
