package com.shiwaixiangcun.customer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/22.
 */

public class AllMerchBean implements Serializable {


    /**
     * data : {"elements":[{"cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/33/rBKx51l1R-GABu98AAWFgrGNh7o008.png","feature":"111","id":1,"name":"111"},{"cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/34/rBKx51l1W2KANIsNAAWFgrGNh7o494.png","feature":"55","id":5,"name":"55"},{"cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/34/rBKx51l1WkmAKGpoAAWFgrGNh7o759.png","feature":"44","id":4,"name":"44"},{"cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/34/rBKx51l1WVCAc3i2AAWFgrGNh7o864.png","feature":"33","id":3,"name":"33"},{"cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/34/rBKx51l1WJyAG0yvAAWFgrGNh7o397.png","feature":"22","id":2,"name":"22"}],"page":1,"size":10,"totalAmount":5,"totalPages":1}
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
         * elements : [{"cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/33/rBKx51l1R-GABu98AAWFgrGNh7o008.png","feature":"111","id":1,"name":"111"},{"cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/34/rBKx51l1W2KANIsNAAWFgrGNh7o494.png","feature":"55","id":5,"name":"55"},{"cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/34/rBKx51l1WkmAKGpoAAWFgrGNh7o759.png","feature":"44","id":4,"name":"44"},{"cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/34/rBKx51l1WVCAc3i2AAWFgrGNh7o864.png","feature":"33","id":3,"name":"33"},{"cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/34/rBKx51l1WJyAG0yvAAWFgrGNh7o397.png","feature":"22","id":2,"name":"22"}]
         * page : 1
         * size : 10
         * totalAmount : 5
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
             * cover : http://resource.shiwaixiangcun.cn/group1/M00/00/33/rBKx51l1R-GABu98AAWFgrGNh7o008.png
             * feature : 111
             * id : 1
             * name : 111
             */

            private String cover;
            private String feature;
            private int id;
            private String name;

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getFeature() {
                return feature;
            }

            public void setFeature(String feature) {
                this.feature = feature;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
