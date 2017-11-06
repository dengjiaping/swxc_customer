package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/10/30
 */

public class EvaluatesListBean {


    /**
     * data : {"elements":[{"attrDescription":"重量:2kg","avatar":"http://resource.shiwaixiangcun.cn/group1/M00/00/00/rBKx5VkZZlOAIivEAAAcGHPVROQ697.png","content":"垃圾商品","evaluateTime":"2017-10-30","id":1,"images":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-eAeprFADb-QImlinU709.jpg","fileId":3958,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-eAeprFADb-QImlinU709.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-2AZgiIACm1-ZELSxQ766.jpg","fileId":3959,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-2AZgiIACm1-ZELSxQ766.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n_SAUQ9KADQ9oFFyyDg811.jpg","fileId":3960,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n_SAUQ9KADQ9oFFyyDg811.jpg"}],"nick":"1***6","score":3},{"attrDescription":"重量:1kg","avatar":"http://resource.shiwaixiangcun.cn/group1/M00/00/00/rBKx5VkZZlOAIivEAAAcGHPVROQ697.png","content":"商品质量差得令人窒息","evaluateTime":"2017-10-30","id":2,"images":[],"nick":"1***6","score":2}],"page":1,"size":10,"totalAmount":2,"totalPages":1}
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
         * elements : [{"attrDescription":"重量:2kg","avatar":"http://resource.shiwaixiangcun.cn/group1/M00/00/00/rBKx5VkZZlOAIivEAAAcGHPVROQ697.png","content":"垃圾商品","evaluateTime":"2017-10-30","id":1,"images":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-eAeprFADb-QImlinU709.jpg","fileId":3958,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-eAeprFADb-QImlinU709.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-2AZgiIACm1-ZELSxQ766.jpg","fileId":3959,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-2AZgiIACm1-ZELSxQ766.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n_SAUQ9KADQ9oFFyyDg811.jpg","fileId":3960,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n_SAUQ9KADQ9oFFyyDg811.jpg"}],"nick":"1***6","score":3},{"attrDescription":"重量:1kg","avatar":"http://resource.shiwaixiangcun.cn/group1/M00/00/00/rBKx5VkZZlOAIivEAAAcGHPVROQ697.png","content":"商品质量差得令人窒息","evaluateTime":"2017-10-30","id":2,"images":[],"nick":"1***6","score":2}]
         * page : 1
         * size : 10
         * totalAmount : 2
         * totalPages : 1
         */

        private int badTotal;
        private int highTotal;
        private int midTotal;
        private int page;
        private int size;
        private int totalAmount;
        private int totalPages;
        private List<GoodDetail.DataBean.EvaluatesBean> elements;

        public int getBadTotal() {
            return badTotal;
        }

        public int getHighTotal() {
            return highTotal;
        }

        public int getMidTotal() {
            return midTotal;
        }

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

        public List<GoodDetail.DataBean.EvaluatesBean> getElements() {
            return elements;
        }

        public void setElements(List<GoodDetail.DataBean.EvaluatesBean> elements) {
            this.elements = elements;
        }
    }
}
