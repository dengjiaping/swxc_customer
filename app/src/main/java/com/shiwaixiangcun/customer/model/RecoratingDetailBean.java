package com.shiwaixiangcun.customer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class RecoratingDetailBean implements Serializable{


    /**
     * data : {"authentication":true,"certificates":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kitmiACX16AAAPdCkiBaM798.png","fileId":1066,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kitmiACX16AAAPdCkiBaM798_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kitm2ASqbpAAAPdCkiBaM460.png","fileId":1067,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kitm2ASqbpAAAPdCkiBaM460_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kjsQuAfuLbAAAPdCkiBaM857.png","fileId":1071,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kjsQuAfuLbAAAPdCkiBaM857_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kjseyAaTunAAAPdCkiBaM690.png","fileId":1073,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kjseyAaTunAAAPdCkiBaM690_150x150.png"}],"decoratePlanDtos":[{"designCharts":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEJ6AbAwPABk27CHyoFU831.png","fileId":1103,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEJ6AbAwPABk27CHyoFU831_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEbqAL4fsAALm9JFg9-k109.png","fileId":1105,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEbqAL4fsAALm9JFg9-k109_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEf6AIM-WAALm9JFg9-k975.png","fileId":1106,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEf6AIM-WAALm9JFg9-k975_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkFk2AFd2DAALm9JFg9-k702.png","fileId":1107,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkFk2AFd2DAALm9JFg9-k702_150x150.png"}],"planId":1,"planName":"1"}],"id":1,"introduce":"test","logo":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kiVF2ACyQvAAEwtTzBN_c923.jpg","name":"test","phone":"17790266069","summary":"test"}
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

    public static class DataBean implements Serializable{
        /**
         * authentication : true
         * certificates : [{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kitmiACX16AAAPdCkiBaM798.png","fileId":1066,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kitmiACX16AAAPdCkiBaM798_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kitm2ASqbpAAAPdCkiBaM460.png","fileId":1067,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kitm2ASqbpAAAPdCkiBaM460_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kjsQuAfuLbAAAPdCkiBaM857.png","fileId":1071,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kjsQuAfuLbAAAPdCkiBaM857_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kjseyAaTunAAAPdCkiBaM690.png","fileId":1073,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kjseyAaTunAAAPdCkiBaM690_150x150.png"}]
         * decoratePlanDtos : [{"designCharts":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEJ6AbAwPABk27CHyoFU831.png","fileId":1103,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEJ6AbAwPABk27CHyoFU831_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEbqAL4fsAALm9JFg9-k109.png","fileId":1105,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEbqAL4fsAALm9JFg9-k109_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEf6AIM-WAALm9JFg9-k975.png","fileId":1106,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEf6AIM-WAALm9JFg9-k975_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkFk2AFd2DAALm9JFg9-k702.png","fileId":1107,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkFk2AFd2DAALm9JFg9-k702_150x150.png"}],"planId":1,"planName":"1"}]
         * id : 1
         * introduce : test
         * logo : http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kiVF2ACyQvAAEwtTzBN_c923.jpg
         * name : test
         * phone : 17790266069
         * summary : test
         */

        private boolean authentication;
        private int id;
        private String introduce;
        private String logo;
        private String name;
        private String phone;
        private String summary;
        private List<CertificatesBean> certificates;
        private List<DecoratePlanDtosBean> decoratePlanDtos;

        public boolean isAuthentication() {
            return authentication;
        }

        public void setAuthentication(boolean authentication) {
            this.authentication = authentication;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<CertificatesBean> getCertificates() {
            return certificates;
        }

        public void setCertificates(List<CertificatesBean> certificates) {
            this.certificates = certificates;
        }

        public List<DecoratePlanDtosBean> getDecoratePlanDtos() {
            return decoratePlanDtos;
        }

        public void setDecoratePlanDtos(List<DecoratePlanDtosBean> decoratePlanDtos) {
            this.decoratePlanDtos = decoratePlanDtos;
        }

        public static class CertificatesBean implements Serializable{
            /**
             * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kitmiACX16AAAPdCkiBaM798.png
             * fileId : 1066
             * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/11/rBKx51kitmiACX16AAAPdCkiBaM798_150x150.png
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

        public static class DecoratePlanDtosBean implements Serializable{
            /**
             * designCharts : [{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEJ6AbAwPABk27CHyoFU831.png","fileId":1103,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEJ6AbAwPABk27CHyoFU831_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEbqAL4fsAALm9JFg9-k109.png","fileId":1105,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEbqAL4fsAALm9JFg9-k109_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEf6AIM-WAALm9JFg9-k975.png","fileId":1106,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEf6AIM-WAALm9JFg9-k975_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkFk2AFd2DAALm9JFg9-k702.png","fileId":1107,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkFk2AFd2DAALm9JFg9-k702_150x150.png"}]
             * planId : 1
             * planName : 1
             */

            private int planId;
            private String planName;
            private List<DesignChartsBean> designCharts;

            public int getPlanId() {
                return planId;
            }

            public void setPlanId(int planId) {
                this.planId = planId;
            }

            public String getPlanName() {
                return planName;
            }

            public void setPlanName(String planName) {
                this.planName = planName;
            }

            public List<DesignChartsBean> getDesignCharts() {
                return designCharts;
            }

            public void setDesignCharts(List<DesignChartsBean> designCharts) {
                this.designCharts = designCharts;
            }

            public static class DesignChartsBean implements Serializable{
                /**
                 * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEJ6AbAwPABk27CHyoFU831.png
                 * fileId : 1103
                 * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/12/rBKx51kkEJ6AbAwPABk27CHyoFU831_150x150.png
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
    }
}
