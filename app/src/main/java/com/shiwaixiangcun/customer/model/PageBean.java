package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * Created by fyj on 2017/5/24.
 *  分页对象
 */
public class PageBean<T> {

        private List<T> elements;
        private int page;
        private int size;
        private int totalAmount;
        private int totalPages;

        public List<T> getElements() {
            return elements;
        }

        public void setElements(List<T> elements) {
            this.elements = elements;
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
}
