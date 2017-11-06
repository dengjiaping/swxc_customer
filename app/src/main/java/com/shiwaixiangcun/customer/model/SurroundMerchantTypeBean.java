package com.shiwaixiangcun.customer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */

public class SurroundMerchantTypeBean implements Serializable {


    /**
     * data : [{"all":true,"detailId":null,"id":220,"image":{"accessUrl":null,"fileId":null,"thumbImageURL":null},"name":null,"value":null},{"all":false,"detailId":null,"id":221,"image":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/32/rBKx51lzBSaAMoqYAABE9sV0cSU883.png","fileId":2849,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/32/rBKx51lzBSaAMoqYAABE9sV0cSU883_300x300.png"},"name":"酒店","value":"merchantType"},{"all":false,"detailId":null,"id":222,"image":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/32/rBKx51lzBQmAHVeeAAA_Kfi4ObU931.png","fileId":2848,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/32/rBKx51lzBQmAHVeeAAA_Kfi4ObU931_300x300.png"},"name":"美食","value":"merchantType"},{"all":false,"detailId":null,"id":223,"image":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/32/rBKx51lzBUWAAxVkAABGA8lX-bU851.png","fileId":2850,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/32/rBKx51lzBUWAAxVkAABGA8lX-bU851_300x300.png"},"name":"超市","value":"merchantTypeMarket"},{"all":false,"detailId":19,"id":224,"image":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/32/rBKx51lzBWCAUVnWAABFItMEhsc031.png","fileId":2851,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/32/rBKx51lzBWCAUVnWAABFItMEhsc031_300x300.png"},"name":"医院","value":"merchantTypeHospital"},{"all":false,"detailId":null,"id":225,"image":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/32/rBKx51lzBXmAO27TAABMxThFi3g207.png","fileId":2852,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/32/rBKx51lzBXmAO27TAABMxThFi3g207_300x300.png"},"name":"旅游","value":"merchantType"}]
     * message : 操作成功
     * responseCode : 1001
     * success : true
     */

    private String message;
    private int responseCode;
    private boolean success;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * all : true
         * detailId : null
         * id : 220
         * image : {"accessUrl":null,"fileId":null,"thumbImageURL":null}
         * name : null
         * value : null
         */

        private boolean all;
        private String detailId;
        private int id;
        private ImageBean image;
        private String name;
        private String value;

        public DataBean(boolean all, String detailId, int id, ImageBean image, String name, String value) {
            this.all = all;
            this.detailId = detailId;
            this.id = id;
            this.image = image;
            this.name = name;
            this.value = value;
        }

        public boolean isAll() {
            return all;
        }

        public void setAll(boolean all) {
            this.all = all;
        }

        public String getDetailId() {
            return detailId;
        }

        public void setDetailId(String detailId) {
            this.detailId = detailId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ImageBean getImage() {
            return image;
        }

        public void setImage(ImageBean image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static class ImageBean implements Serializable{
            /**
             * accessUrl : null
             * fileId : null
             * thumbImageURL : null
             */

            private String accessUrl;
            private String fileId;
            private String thumbImageURL;

            public ImageBean(String accessUrl, String fileId, String thumbImageURL) {
                this.accessUrl = accessUrl;
                this.fileId = fileId;
                this.thumbImageURL = thumbImageURL;
            }

            public String getAccessUrl() {
                return accessUrl;
            }

            public void setAccessUrl(String accessUrl) {
                this.accessUrl = accessUrl;
            }

            public String getFileId() {
                return fileId;
            }

            public void setFileId(String fileId) {
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
