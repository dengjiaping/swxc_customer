package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * Created by Administrator on 2017/10/12.
 */

public class MessageBean {


    /**
     * elements : [{"content":"测试医生医生回复消息, 点击查看","customerId":250,"messageCoreType":"INTERROGATION","partner":"shiwaishenghuo","problemId":"s_mrEG5u9zlZqrN_s0WvDg","sendDate":1506398144000,"title":"问诊提醒"},{"content":"测试医生医生回复消息, 点击查看","customerId":250,"messageCoreType":"INTERROGATION","partner":"shiwaishenghuo","problemId":"s_mrEG5u9zlZqrN_s0WvDg","sendDate":1506398133000,"title":"问诊提醒"},{"content":"测试医生医生回复消息, 点击查看","customerId":250,"messageCoreType":"INTERROGATION","partner":"shiwaishenghuo","problemId":"s_mrEG5u9zlZqrN_s0WvDg","sendDate":1506396963000,"title":"问诊提醒"},{"content":"测试医生医生回复消息, 点击查看","customerId":250,"messageCoreType":"INTERROGATION","partner":"shiwaishenghuo","problemId":"s_mrEG5u9zlZqrN_s0WvDg","sendDate":1506396961000,"title":"问诊提醒"},{"content":"测试医生医生回复消息, 点击查看","customerId":250,"messageCoreType":"INTERROGATION","partner":"shiwaishenghuo","problemId":"s_mrEG5u9zlZqrN_s0WvDg","sendDate":1506396959000,"title":"问诊提醒"},{"content":"测试医生医生回复消息, 点击查看","customerId":250,"messageCoreType":"INTERROGATION","partner":"shiwaishenghuo","problemId":"s_mrEG5u9zlZqrN_s0WvDg","sendDate":1506395717000,"title":"问诊提醒"},{"content":"测试医生医生回复消息, 点击查看","customerId":250,"messageCoreType":"INTERROGATION","partner":"shiwaishenghuo","problemId":"s_mrEG5u9zlZqrN_s0WvDg","sendDate":1506393864000,"title":"问诊提醒"},{"content":"测试医生医生回复消息, 点击查看","customerId":250,"messageCoreType":"INTERROGATION","partner":"shiwaishenghuo","problemId":"s_mrEG5u9zlZqrN_s0WvDg","sendDate":1506393838000,"title":"问诊提醒"},{"content":"测试医生医生回复消息, 点击查看","customerId":250,"messageCoreType":"INTERROGATION","partner":"shiwaishenghuo","problemId":"s_mrEG5u9zlZqrN_s0WvDg","sendDate":1506393775000,"title":"问诊提醒"},{"content":"测试医生医生回复消息, 点击查看","customerId":250,"messageCoreType":"INTERROGATION","partner":"shiwaishenghuo","problemId":"s_mrEG5u9zlZqrN_s0WvDg","sendDate":1506393762000,"title":"问诊提醒"}]
     * page : 1
     * size : 10
     * totalAmount : 10
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
         * content : 测试医生医生回复消息, 点击查看
         * customerId : 250
         * messageCoreType : INTERROGATION
         * partner : shiwaishenghuo
         * problemId : s_mrEG5u9zlZqrN_s0WvDg
         * sendDate : 1506398144000
         * title : 问诊提醒
         */

        private String content;
        private int customerId;
        private String messageCoreType;
        private String partner;
        private String problemId;
        private long sendDate;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getMessageCoreType() {
            return messageCoreType;
        }

        public void setMessageCoreType(String messageCoreType) {
            this.messageCoreType = messageCoreType;
        }

        public String getPartner() {
            return partner;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }

        public String getProblemId() {
            return problemId;
        }

        public void setProblemId(String problemId) {
            this.problemId = problemId;
        }

        public long getSendDate() {
            return sendDate;
        }

        public void setSendDate(long sendDate) {
            this.sendDate = sendDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
