package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MallGoods {

    /**
     * data : {"elements":[{"feature":"耐穿又不贵","goodsId":70,"goodsName":"Lee","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/52/rBKx51muATWAc_X0AAES-j5ikmU911.jpg","minPrice":169,"subjectId":248},{"feature":null,"goodsId":72,"goodsName":"牛仔裤test01","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/54/rBKx51mvkNeATh1gAD-2MmYfpeg819.jpg","minPrice":12,"subjectId":248},{"feature":"346","goodsId":69,"goodsName":"343","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/52/rBKx51mn2eeAXFPlAAERCOllPFA639.png","minPrice":4,"subjectId":248},{"feature":"卖点","goodsId":65,"goodsName":"水果蔬菜-水果-苹果02","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnb2SADRhjAAAUnqGei5c185.jpg","minPrice":2,"subjectId":248}],"page":1,"size":6,"totalAmount":4,"totalPages":1}
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
         * elements : [{"feature":"耐穿又不贵","goodsId":70,"goodsName":"Lee","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/52/rBKx51muATWAc_X0AAES-j5ikmU911.jpg","minPrice":169,"subjectId":248},{"feature":null,"goodsId":72,"goodsName":"牛仔裤test01","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/54/rBKx51mvkNeATh1gAD-2MmYfpeg819.jpg","minPrice":12,"subjectId":248},{"feature":"346","goodsId":69,"goodsName":"343","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/52/rBKx51mn2eeAXFPlAAERCOllPFA639.png","minPrice":4,"subjectId":248},{"feature":"卖点","goodsId":65,"goodsName":"水果蔬菜-水果-苹果02","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnb2SADRhjAAAUnqGei5c185.jpg","minPrice":2,"subjectId":248}]
         * page : 1
         * size : 6
         * totalAmount : 4
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

        public static class ElementsBean extends GoodBean {

        }
    }
}
