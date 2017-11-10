package com.shiwaixiangcun.customer.adapter;

import java.util.List;

/**
 * 活动报名
 *
 * @author Administrator
 * @date 2017/11/10
 */

public class RegisterBean {


    /**
     * elements : [{"activityStartDate":1510281420000,"detailAddress":"或或或或或或或或或或或或或或或或哈哈哈哈哈或或或或或或或或或或","id":13,"poster":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/82/rBKx51oFEE6ADAbqAAICk9hoJRI456.png","fileId":6197,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/82/rBKx51oFEE6ADAbqAAICk9hoJRI456.png"},"status":"ActivityEnd","title":"物业活动bbbbbbbbb"},{"activityStartDate":1510281060000,"detailAddress":"sajajajajajajkh","id":12,"poster":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/82/rBKx51oFDteAQRcnAALknwYPZ9Y315.png","fileId":6191,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/82/rBKx51oFDteAQRcnAALknwYPZ9Y315.png"},"status":"EnterEnd","title":"物业活动aaaaaa"},{"activityStartDate":1510280820000,"detailAddress":"详细地址bhhbbhbhb","id":11,"poster":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/82/rBKx51oFDhKAG3zTAAKBBMknm-E085.png","fileId":6187,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/82/rBKx51oFDhKAG3zTAAKBBMknm-E085.png"},"status":"ActivityEnd","title":"运营活动bbbbbbbb"},{"activityStartDate":1510366440000,"detailAddress":"丰德国际广场","id":10,"poster":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/82/rBKx51oFC7uAdBNXAALknwYPZ9Y730.png","fileId":6182,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/82/rBKx51oFC7uAdBNXAALknwYPZ9Y730.png"},"status":"Enter","title":"运营活动aaaaaaa"},{"activityStartDate":1510298280000,"detailAddress":"3333","id":9,"poster":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/80/rBKx51oEAVaAfADkAAAaiCPP2ig950.png","fileId":5975,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/80/rBKx51oEAVaAfADkAAAaiCPP2ig950.png"},"status":"Enter","title":"地方 3333"},{"activityStartDate":1510292220000,"detailAddress":"都是范德萨","id":8,"poster":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/7D/rBKx51oD6baAT3FuAAAaiCPP2ig687.png","fileId":5729,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/7D/rBKx51oD6baAT3FuAAAaiCPP2ig687.png"},"status":"Enter","title":"打发第三方"},{"activityStartDate":1510286040000,"detailAddress":"地方3333","id":7,"poster":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/7A/rBKx51oCgBuAWcQZAAAaiCPP2ig080.png","fileId":5396,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/7A/rBKx51oCgBuAWcQZAAAaiCPP2ig080.png"},"status":"Enter","title":"的说法测试33"},{"activityStartDate":1510134060000,"detailAddress":"333","id":3,"poster":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/6E/rBKx51oBf-eAPlTHAAAaiCPP2ig370.png","fileId":4290,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/6E/rBKx51oBf-eAPlTHAAAaiCPP2ig370.png"},"status":"Draft","title":"地方333"}]
     * page : 1
     * size : 20
     * totalAmount : 8
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
         * activityStartDate : 1510281420000
         * detailAddress : 或或或或或或或或或或或或或或或或哈哈哈哈哈或或或或或或或或或或
         * id : 13
         * poster : {"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/82/rBKx51oFEE6ADAbqAAICk9hoJRI456.png","fileId":6197,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/82/rBKx51oFEE6ADAbqAAICk9hoJRI456.png"}
         * status : ActivityEnd
         * title : 物业活动bbbbbbbbb
         */

        private long activityStartDate;
        private String detailAddress;
        private int id;
        private PosterBean poster;
        private String status;
        private String title;

        public long getActivityStartDate() {
            return activityStartDate;
        }

        public void setActivityStartDate(long activityStartDate) {
            this.activityStartDate = activityStartDate;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public PosterBean getPoster() {
            return poster;
        }

        public void setPoster(PosterBean poster) {
            this.poster = poster;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public static class PosterBean {
            /**
             * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/82/rBKx51oFEE6ADAbqAAICk9hoJRI456.png
             * fileId : 6197
             * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/82/rBKx51oFEE6ADAbqAAICk9hoJRI456.png
             */

            private String accessUrl;
            private int fileId;
            private String thumbImageURL;

            public String getAccessUrl() {
                return accessUrl;
            }

            public void setAccessUrl(String accessUrl) {
                this.accessUrl = accessUrl;
            }

            public int getFileId() {
                return fileId;
            }

            public void setFileId(int fileId) {
                this.fileId = fileId;
            }

            public String getThumbImageURL() {
                return thumbImageURL;
            }

            public void setThumbImageURL(String thumbImageURL) {
                this.thumbImageURL = thumbImageURL;
            }
        }
    }
}
