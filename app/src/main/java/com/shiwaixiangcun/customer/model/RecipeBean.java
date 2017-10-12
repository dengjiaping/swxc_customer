package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class RecipeBean {


    /**
     * elements : [{"articleId":99,"cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/5F/rBKx51nJ-uiALoWOAACH0TSAC2k844.jpg","summary":"杏鲍菇肉质肥厚有嚼头，具有降血脂、降胆固醇、促进胃肠消化、增强机体免疫能力、防止心血管病等功效。挑菇伞在一元硬币大小，菇柄直径约为5公分左右且顺直的为好。用流水略冲去表面浮尘即可，千万不要用水泡。","title":"宫保杏鲍菇(请勿动此篇)"},{"articleId":113,"cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/60/rBKx51nLePWANYUQAADIKGNPo6I090.jpg","summary":"竹荪不但是珍贵的食用菌，同时对高血压、神经衰弱和肠胃疾病有一定的食疗效果。","title":"竹荪炖鸡汤（请勿动此篇）"},{"articleId":112,"cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/5F/rBKx51nKAzOAJjUTAAD_UeyY2Jk251.jpg","summary":"竹荪能够保护肝脏，减少腹壁脂肪的积存，有俗称\u201c刮油\u201d的作用，从而产生降血压、降血脂和减肥的效果显著。","title":"竹荪炖鲍鱼(请勿动此篇)"},{"articleId":105,"cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/5E/rBKx51nIahWAbW_hAAD62t-eEks098.png","summary":"一股同一个yutyuityouy","title":"ewgewgegegregrere"},{"articleId":104,"cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/5E/rBKx51nIaIWAbkypAALq3M6GGss788.png","summary":"34二个热狗34提434天","title":"TEST html5页面中点击跳转到原生页面"},{"articleId":100,"cover":null,"summary":"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","title":"健康食谱"}]
     * page : 1
     * size : 10
     * totalAmount : 6
     * totalPages : 1
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
         * articleId : 99
         * cover : http://resource.shiwaixiangcun.cn/group1/M00/00/5F/rBKx51nJ-uiALoWOAACH0TSAC2k844.jpg
         * summary : 杏鲍菇肉质肥厚有嚼头，具有降血脂、降胆固醇、促进胃肠消化、增强机体免疫能力、防止心血管病等功效。挑菇伞在一元硬币大小，菇柄直径约为5公分左右且顺直的为好。用流水略冲去表面浮尘即可，千万不要用水泡。
         * title : 宫保杏鲍菇(请勿动此篇)
         */

        private int articleId;
        private String cover;
        private String summary;
        private String title;

        public int getArticleId() {
            return articleId;
        }

        public void setArticleId(int articleId) {
            this.articleId = articleId;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
