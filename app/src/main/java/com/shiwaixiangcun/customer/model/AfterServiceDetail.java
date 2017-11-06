package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */

public class AfterServiceDetail {


    /**
     * data : {"description":{"afterSaleDate":1507616302000,"content":"就是想退！","goodsStatus":"Received","images":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/61/rBKx51ncZi2ADZanAAcTuDS4tOo95.jpeg","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/61/rBKx51ncZi2ADZanAAcTuDS4tOo95_150x150.jpeg"}],"number":"0000000009","price":0.02,"status":"CancelServer"},"records":[{"content":"服务单申请成功，待售后审核","name":"15608222076","time":1507616302000},{"content":"您的服务单已取消","name":"15608222076","time":1507626130000}]}
     * message : 鎿嶄綔鎴愬姛
     * responseCode : 1001
     * success : true
     */

    private DataBean data;
    private String message;
    private int responseCode;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * description : {"afterSaleDate":1507616302000,"content":"就是想退！","goodsStatus":"Received","images":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/61/rBKx51ncZi2ADZanAAcTuDS4tOo95.jpeg","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/61/rBKx51ncZi2ADZanAAcTuDS4tOo95_150x150.jpeg"}],"number":"0000000009","price":0.02,"status":"CancelServer"}
         * records : [{"content":"服务单申请成功，待售后审核","name":"15608222076","time":1507616302000},{"content":"您的服务单已取消","name":"15608222076","time":1507626130000}]
         */

        private DescriptionBean description;
        private List<RecordsBean> records;

        public DescriptionBean getDescription() {
            return description;
        }

        public void setDescription(DescriptionBean description) {
            this.description = description;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class DescriptionBean {
            /**
             * afterSaleDate : 1507616302000
             * content : 就是想退！
             * goodsStatus : Received
             * images : [{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/61/rBKx51ncZi2ADZanAAcTuDS4tOo95.jpeg","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/61/rBKx51ncZi2ADZanAAcTuDS4tOo95_150x150.jpeg"}]
             * number : 0000000009
             * price : 0.02
             * status : CancelServer
             */

            private long afterSaleDate;
            private String content;
            private String goodsStatus;
            private String number;
            private double price;
            private String status;
            private List<ImagesBean> images;

            public long getAfterSaleDate() {
                return afterSaleDate;
            }

            public void setAfterSaleDate(long afterSaleDate) {
                this.afterSaleDate = afterSaleDate;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getGoodsStatus() {
                return goodsStatus;
            }

            public void setGoodsStatus(String goodsStatus) {
                this.goodsStatus = goodsStatus;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public List<ImagesBean> getImages() {
                return images;
            }

            public void setImages(List<ImagesBean> images) {
                this.images = images;
            }

            public static class ImagesBean {
                /**
                 * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/61/rBKx51ncZi2ADZanAAcTuDS4tOo95.jpeg
                 * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/61/rBKx51ncZi2ADZanAAcTuDS4tOo95_150x150.jpeg
                 */

                private String accessUrl;
                private String thumbImageURL;

                public String getAccessUrl() {
                    return accessUrl;
                }

                public void setAccessUrl(String accessUrl) {
                    this.accessUrl = accessUrl;
                }

                public String getThumbImageURL() {
                    return thumbImageURL;
                }

                public void setThumbImageURL(String thumbImageURL) {
                    this.thumbImageURL = thumbImageURL;
                }
            }
        }

        public static class RecordsBean {
            /**
             * content : 服务单申请成功，待售后审核
             * name : 15608222076
             * time : 1507616302000
             */

            private String content;
            private String name;
            private long time;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }
        }
    }
}