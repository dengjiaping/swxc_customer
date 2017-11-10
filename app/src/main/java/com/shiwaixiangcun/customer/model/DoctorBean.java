package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/11/10
 */

public class DoctorBean {


    /**
     * elements : [{"avatar":null,"department":null,"goodField":null,"id":13,"inauguralHospital":null,"name":"李四","titleOfDoctor":"副主任医师"},{"avatar":"http://resource.shiwaixiangcun.cn/group1/M00/00/82/rBKx51oFFcCAGX8TAAKBBMknm-E807.png","department":null,"goodField":"擅长领域","id":12,"inauguralHospital":null,"name":"张三","titleOfDoctor":"主任医师"},{"avatar":"http://resource.shiwaixiangcun.cn/group1/M00/00/80/rBKx51oEEruAR7XAAABzCMShGUU765.png","department":null,"goodField":"11,22","id":11,"inauguralHospital":null,"name":"ces","titleOfDoctor":"主治医师"},{"avatar":"http://resource.shiwaixiangcun.cn/group1/M00/00/6C/rBKx51oBSPCAJF4GAACl6D8hbEA159.png","department":null,"goodField":"精神,神经","id":10,"inauguralHospital":null,"name":"王五","titleOfDoctor":"主治医师"},{"avatar":"http://resource.shiwaixiangcun.cn/group1/M00/00/6C/rBKx51oBTLGAYxrsAACiyP-ewK4836.png","department":null,"goodField":"11,22","id":9,"inauguralHospital":null,"name":"test","titleOfDoctor":"副主任医师"}]
     * page : 1
     * size : 20
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
         * avatar : null
         * department : null
         * goodField : null
         * id : 13
         * inauguralHospital : null
         * name : 李四
         * titleOfDoctor : 副主任医师
         */

        private String avatar;
        private String department;
        private String goodField;
        private int id;
        private String inauguralHospital;
        private String name;
        private String titleOfDoctor;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getGoodField() {
            return goodField;
        }

        public void setGoodField(String goodField) {
            this.goodField = goodField;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInauguralHospital() {
            return inauguralHospital;
        }

        public void setInauguralHospital(String inauguralHospital) {
            this.inauguralHospital = inauguralHospital;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitleOfDoctor() {
            return titleOfDoctor;
        }

        public void setTitleOfDoctor(String titleOfDoctor) {
            this.titleOfDoctor = titleOfDoctor;
        }
    }
}
