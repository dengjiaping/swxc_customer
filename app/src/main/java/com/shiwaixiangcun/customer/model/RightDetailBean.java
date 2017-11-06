package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public class RightDetailBean {

    /**
     * data : {"baseInfo":{"content":"民工民工匿名","id":669,"images":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/60/rBKx51nOEY-AEXSTAAIQgKVURRw66.jpeg","fileId":4928,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/60/rBKx51nOEY-AEXSTAAIQgKVURRw66_150x150.jpeg"}],"number":"00095","status":"ACCEPTED","time":1506677141000},"process":[{"mess":"消费维权申请提交成功，请等待处理","time":1506677141000,"type":"ACCEPTED"}]}
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
         * baseInfo : {"content":"民工民工匿名","id":669,"images":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/60/rBKx51nOEY-AEXSTAAIQgKVURRw66.jpeg","fileId":4928,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/60/rBKx51nOEY-AEXSTAAIQgKVURRw66_150x150.jpeg"}],"number":"00095","status":"ACCEPTED","time":1506677141000}
         * process : [{"mess":"消费维权申请提交成功，请等待处理","time":1506677141000,"type":"ACCEPTED"}]
         */

        private BaseInfoBean baseInfo;
        private List<ProcessBean> process;

        public BaseInfoBean getBaseInfo() {
            return baseInfo;
        }

        public void setBaseInfo(BaseInfoBean baseInfo) {
            this.baseInfo = baseInfo;
        }

        public List<ProcessBean> getProcess() {
            return process;
        }

        public void setProcess(List<ProcessBean> process) {
            this.process = process;
        }

        public static class BaseInfoBean {
            /**
             * content : 民工民工匿名
             * id : 669
             * images : [{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/60/rBKx51nOEY-AEXSTAAIQgKVURRw66.jpeg","fileId":4928,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/60/rBKx51nOEY-AEXSTAAIQgKVURRw66_150x150.jpeg"}]
             * number : 00095
             * status : ACCEPTED
             * time : 1506677141000
             */

            private String content;
            private int id;
            private String number;
            private String status;
            private long time;
            private List<ImagesBean> images;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public List<ImagesBean> getImages() {
                return images;
            }

            public void setImages(List<ImagesBean> images) {
                this.images = images;
            }

            public static class ImagesBean {
                /**
                 * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/60/rBKx51nOEY-AEXSTAAIQgKVURRw66.jpeg
                 * fileId : 4928
                 * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/60/rBKx51nOEY-AEXSTAAIQgKVURRw66_150x150.jpeg
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

        public static class ProcessBean {
            /**
             * mess : 消费维权申请提交成功，请等待处理
             * time : 1506677141000
             * type : ACCEPTED
             */

            private String mess;
            private long time;
            private String type;

            public String getMess() {
                return mess;
            }

            public void setMess(String mess) {
                this.mess = mess;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
