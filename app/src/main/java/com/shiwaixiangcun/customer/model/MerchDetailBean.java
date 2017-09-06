package com.shiwaixiangcun.customer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */

public class MerchDetailBean implements Serializable {


    /**
     * data : {"atlas":[{"atlasList":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HXuAA0UYAAB6YxNdWvs817.png","fileId":3051,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HXuAA0UYAAB6YxNdWvs817_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HX6AY6NHAAB6YxNdWvs830.png","fileId":3052,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HX6AY6NHAAB6YxNdWvs830_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HYCAXP5XAAB6YxNdWvs863.png","fileId":3053,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HYCAXP5XAAB6YxNdWvs863_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HYOATt7SAAB6YxNdWvs213.png","fileId":3054,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HYOATt7SAAB6YxNdWvs213_150x150.png"}],"title":"特色"},{"atlasList":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HyqAcPUnAA8vBOyXYKc130.gif","fileId":3086,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HyqAcPUnAA8vBOyXYKc130_150x150.gif"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HnmAUtUkAAd3HndtCMg052.png","fileId":3069,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HnmAUtUkAAd3HndtCMg052_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6Hx-AFIaFAAAWalalKNI870.jpg","fileId":3085,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6Hx-AFIaFAAAWalalKNI870_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HzOAbW_FAABpjNMDuPU678.jpg","fileId":3089,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HzOAbW_FAABpjNMDuPU678_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HzeAIt_dAAAEGOSczcU528.jpg","fileId":3090,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HzeAIt_dAAAEGOSczcU528_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HzyAcVXiAABmK73CVCo409.gif","fileId":3091,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HzyAcVXiAABmK73CVCo409_150x150.gif"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6Hz-AbS25AAA2S5g4d1s958.jpg","fileId":3092,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6Hz-AbS25AAA2S5g4d1s958_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6H0KAeC7HAAAMVOIMbiI548.jpg","fileId":3093,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6H0KAeC7HAAAMVOIMbiI548_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6H0WAaiEpAAEIJJOh4dE621.jpg","fileId":3094,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6H0WAaiEpAAEIJJOh4dE621_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rnSANLoEAACyAGGhs9k975.jpg","fileId":3149,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rnSANLoEAACyAGGhs9k975_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rneACBZQAACDlr90Vhg349.jpg","fileId":3150,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rneACBZQAACDlr90Vhg349_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rnqAHiDkAAGtbkNSo2w973.jpg","fileId":3151,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rnqAHiDkAAGtbkNSo2w973_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rnyAb2k8AAlPYAAP0D0185.jpg","fileId":3152,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rnyAb2k8AAlPYAAP0D0185_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rn-ACKVLAAMs8P7ArNI101.jpg","fileId":3153,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rn-ACKVLAAMs8P7ArNI101_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4roKAJsjxAAGtbkNSo2w592.jpg","fileId":3154,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4roKAJsjxAAGtbkNSo2w592_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4roWATLtWAAKQXlEefbU552.jpg","fileId":3155,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4roWATLtWAAKQXlEefbU552_150x150.jpg"}],"title":"1"},{"atlasList":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l6KUGADtTXAAAd_wjlWtQ487.gif","fileId":3127,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l6KUGADtTXAAAd_wjlWtQ487_150x150.gif"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l6KUaAIeVyAAOufYrvqlA103.gif","fileId":3128,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l6KUaAIeVyAAOufYrvqlA103_150x150.gif"}],"title":"test"}],"basicInformation":{"callButton":"联系订餐","cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rV2AQK0xAADuB5oFsus571.jpg","description":"有效期 2015年08月19日至2017年07月27日  可用时间 周末法定节假日通用 17:00 - 次日03:00 预约提示 无需预约，直接消费（高峰期间消费需排号等位） 使用规则 团购用户暂不享受店内其他优惠 每张糯米券不限使用人数 每次消费不限使用糯米券张数，可叠加使用 仅限大厅使用，无包间 代金券不兑现、不找零，超出部分到店另付 温馨提示 本单堂食外带均可 商家不提供餐后打包服务 ","enable":true,"feature":"巴适，好吃的板","id":7,"name":"院坝小龙虾","phone":"40055464568","position":"四川成都九眼桥","recommendIcon":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l5O-qAOyd8AAAEiyt6ujA388.png","recommendStr":"美食推荐","sort":null,"type":"美食","typeId":222},"certificate":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HbuAE8eMAAB6YxNdWvs052.png","fileId":3056,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HbuAE8eMAAB6YxNdWvs052_150x150.png"}]}
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
         * atlas : [{"atlasList":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HXuAA0UYAAB6YxNdWvs817.png","fileId":3051,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HXuAA0UYAAB6YxNdWvs817_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HX6AY6NHAAB6YxNdWvs830.png","fileId":3052,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HX6AY6NHAAB6YxNdWvs830_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HYCAXP5XAAB6YxNdWvs863.png","fileId":3053,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HYCAXP5XAAB6YxNdWvs863_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HYOATt7SAAB6YxNdWvs213.png","fileId":3054,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HYOATt7SAAB6YxNdWvs213_150x150.png"}],"title":"特色"},{"atlasList":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HyqAcPUnAA8vBOyXYKc130.gif","fileId":3086,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HyqAcPUnAA8vBOyXYKc130_150x150.gif"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HnmAUtUkAAd3HndtCMg052.png","fileId":3069,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HnmAUtUkAAd3HndtCMg052_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6Hx-AFIaFAAAWalalKNI870.jpg","fileId":3085,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6Hx-AFIaFAAAWalalKNI870_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HzOAbW_FAABpjNMDuPU678.jpg","fileId":3089,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HzOAbW_FAABpjNMDuPU678_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HzeAIt_dAAAEGOSczcU528.jpg","fileId":3090,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HzeAIt_dAAAEGOSczcU528_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HzyAcVXiAABmK73CVCo409.gif","fileId":3091,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6HzyAcVXiAABmK73CVCo409_150x150.gif"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6Hz-AbS25AAA2S5g4d1s958.jpg","fileId":3092,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6Hz-AbS25AAA2S5g4d1s958_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6H0KAeC7HAAAMVOIMbiI548.jpg","fileId":3093,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6H0KAeC7HAAAMVOIMbiI548_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6H0WAaiEpAAEIJJOh4dE621.jpg","fileId":3094,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3B/rBKx51l6H0WAaiEpAAEIJJOh4dE621_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rnSANLoEAACyAGGhs9k975.jpg","fileId":3149,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rnSANLoEAACyAGGhs9k975_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rneACBZQAACDlr90Vhg349.jpg","fileId":3150,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rneACBZQAACDlr90Vhg349_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rnqAHiDkAAGtbkNSo2w973.jpg","fileId":3151,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rnqAHiDkAAGtbkNSo2w973_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rnyAb2k8AAlPYAAP0D0185.jpg","fileId":3152,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rnyAb2k8AAlPYAAP0D0185_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rn-ACKVLAAMs8P7ArNI101.jpg","fileId":3153,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rn-ACKVLAAMs8P7ArNI101_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4roKAJsjxAAGtbkNSo2w592.jpg","fileId":3154,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4roKAJsjxAAGtbkNSo2w592_150x150.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4roWATLtWAAKQXlEefbU552.jpg","fileId":3155,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4roWATLtWAAKQXlEefbU552_150x150.jpg"}],"title":"1"},{"atlasList":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l6KUGADtTXAAAd_wjlWtQ487.gif","fileId":3127,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l6KUGADtTXAAAd_wjlWtQ487_150x150.gif"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l6KUaAIeVyAAOufYrvqlA103.gif","fileId":3128,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l6KUaAIeVyAAOufYrvqlA103_150x150.gif"}],"title":"test"}]
         * basicInformation : {"callButton":"联系订餐","cover":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rV2AQK0xAADuB5oFsus571.jpg","description":"有效期 2015年08月19日至2017年07月27日  可用时间 周末法定节假日通用 17:00 - 次日03:00 预约提示 无需预约，直接消费（高峰期间消费需排号等位） 使用规则 团购用户暂不享受店内其他优惠 每张糯米券不限使用人数 每次消费不限使用糯米券张数，可叠加使用 仅限大厅使用，无包间 代金券不兑现、不找零，超出部分到店另付 温馨提示 本单堂食外带均可 商家不提供餐后打包服务 ","enable":true,"feature":"巴适，好吃的板","id":7,"name":"院坝小龙虾","phone":"40055464568","position":"四川成都九眼桥","recommendIcon":"http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l5O-qAOyd8AAAEiyt6ujA388.png","recommendStr":"美食推荐","sort":null,"type":"美食","typeId":222}
         * certificate : [{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HbuAE8eMAAB6YxNdWvs052.png","fileId":3056,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HbuAE8eMAAB6YxNdWvs052_150x150.png"}]
         */

        private BasicInformationBean basicInformation;
        private List<AtlasBean> atlas;
        private List<CertificateBean> certificate;

        public BasicInformationBean getBasicInformation() {
            return basicInformation;
        }

        public void setBasicInformation(BasicInformationBean basicInformation) {
            this.basicInformation = basicInformation;
        }

        public List<AtlasBean> getAtlas() {
            return atlas;
        }

        public void setAtlas(List<AtlasBean> atlas) {
            this.atlas = atlas;
        }

        public List<CertificateBean> getCertificate() {
            return certificate;
        }

        public void setCertificate(List<CertificateBean> certificate) {
            this.certificate = certificate;
        }

        public static class BasicInformationBean implements Serializable{
            /**
             * callButton : 联系订餐
             * cover : http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l4rV2AQK0xAADuB5oFsus571.jpg
             * description : 有效期 2015年08月19日至2017年07月27日  可用时间 周末法定节假日通用 17:00 - 次日03:00 预约提示 无需预约，直接消费（高峰期间消费需排号等位） 使用规则 团购用户暂不享受店内其他优惠 每张糯米券不限使用人数 每次消费不限使用糯米券张数，可叠加使用 仅限大厅使用，无包间 代金券不兑现、不找零，超出部分到店另付 温馨提示 本单堂食外带均可 商家不提供餐后打包服务
             * enable : true
             * feature : 巴适，好吃的板
             * id : 7
             * name : 院坝小龙虾
             * phone : 40055464568
             * position : 四川成都九眼桥
             * recommendIcon : http://resource.shiwaixiangcun.cn/group1/M00/00/3C/rBKx51l5O-qAOyd8AAAEiyt6ujA388.png
             * recommendStr : 美食推荐
             * sort : null
             * type : 美食
             * typeId : 222
             */

            private String callButton;
            private String cover;
            private String description;
            private boolean enable;
            private String feature;
            private int id;
            private String name;
            private String phone;
            private String position;
            private String recommendIcon;
            private String recommendStr;
            private Object sort;
            private String type;
            private int typeId;

            public String getCallButton() {
                return callButton;
            }

            public void setCallButton(String callButton) {
                this.callButton = callButton;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getRecommendIcon() {
                return recommendIcon;
            }

            public void setRecommendIcon(String recommendIcon) {
                this.recommendIcon = recommendIcon;
            }

            public String getRecommendStr() {
                return recommendStr;
            }

            public void setRecommendStr(String recommendStr) {
                this.recommendStr = recommendStr;
            }

            public Object getSort() {
                return sort;
            }

            public void setSort(Object sort) {
                this.sort = sort;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }
        }

        public static class AtlasBean implements Serializable{
            /**
             * atlasList : [{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HXuAA0UYAAB6YxNdWvs817.png","fileId":3051,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HXuAA0UYAAB6YxNdWvs817_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HX6AY6NHAAB6YxNdWvs830.png","fileId":3052,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HX6AY6NHAAB6YxNdWvs830_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HYCAXP5XAAB6YxNdWvs863.png","fileId":3053,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HYCAXP5XAAB6YxNdWvs863_150x150.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HYOATt7SAAB6YxNdWvs213.png","fileId":3054,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HYOATt7SAAB6YxNdWvs213_150x150.png"}]
             * title : 特色
             */

            private String title;
            private List<AtlasListBean> atlasList;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<AtlasListBean> getAtlasList() {
                return atlasList;
            }

            public void setAtlasList(List<AtlasListBean> atlasList) {
                this.atlasList = atlasList;
            }

            public static class AtlasListBean implements Serializable{
                /**
                 * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HXuAA0UYAAB6YxNdWvs817.png
                 * fileId : 3051
                 * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HXuAA0UYAAB6YxNdWvs817_150x150.png
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

        public static class CertificateBean implements Serializable{
            /**
             * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HbuAE8eMAAB6YxNdWvs052.png
             * fileId : 3056
             * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/3A/rBKx51l6HbuAE8eMAAB6YxNdWvs052_150x150.png
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
