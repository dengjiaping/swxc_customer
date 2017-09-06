package com.shiwaixiangcun.customer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */

public class AwardBean implements Serializable {


    /**
     * data : {"elements":[{"coverPath":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/28/rBKx51lbAdiAaKx6AADVA5n-6xA715.png","fileId":2296,"height":"667","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/28/rBKx51lbAdiAaKx6AADVA5n-6xA715_150x150.png","width":"892"},"id":80},{"coverPath":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/1C/rBKx51lCJaiAe39lAAI5jg_bMaU020.jpg","fileId":1701,"height":"425","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/1C/rBKx51lCJaiAe39lAAI5jg_bMaU020_150x150.jpg","width":"640"},"id":61}],"page":1,"size":100,"totalAmount":2,"totalPages":1}
     * message : 操作成功
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
         * elements : [{"coverPath":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/28/rBKx51lbAdiAaKx6AADVA5n-6xA715.png","fileId":2296,"height":"667","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/28/rBKx51lbAdiAaKx6AADVA5n-6xA715_150x150.png","width":"892"},"id":80},{"coverPath":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/1C/rBKx51lCJaiAe39lAAI5jg_bMaU020.jpg","fileId":1701,"height":"425","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/1C/rBKx51lCJaiAe39lAAI5jg_bMaU020_150x150.jpg","width":"640"},"id":61}]
         * page : 1
         * size : 100
         * totalAmount : 2
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
             * coverPath : {"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/28/rBKx51lbAdiAaKx6AADVA5n-6xA715.png","fileId":2296,"height":"667","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/28/rBKx51lbAdiAaKx6AADVA5n-6xA715_150x150.png","width":"892"}
             * id : 80
             */

            private CoverPathBean coverPath;
            private int id;

            public CoverPathBean getCoverPath() {
                return coverPath;
            }

            public void setCoverPath(CoverPathBean coverPath) {
                this.coverPath = coverPath;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public static class CoverPathBean {
                /**
                 * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/28/rBKx51lbAdiAaKx6AADVA5n-6xA715.png
                 * fileId : 2296
                 * height : 667
                 * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/28/rBKx51lbAdiAaKx6AADVA5n-6xA715_150x150.png
                 * width : 892
                 */

                private String accessUrl;
                private int fileId;
                private String height;
                private String thumbImageURL;
                private String width;

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

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getThumbImageURL() {
                    return thumbImageURL;
                }

                public void setThumbImageURL(String thumbImageURL) {
                    this.thumbImageURL = thumbImageURL;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }
            }
        }
    }
}
