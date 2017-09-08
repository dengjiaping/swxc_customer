package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */

public class GoodDetail {

    /**
     * data : {"categoryFullName":"水果蔬菜-水果1-苹果","categoryId":4,"cityName":"朝阳","feature":"大哥发伟哥伟哥伟哥","goodsCode":null,"goodsDetail":"<p>但是但是但是是<img src=\"data:image/png;base64,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\">鬼地方个电饭锅地方刚发的刚发的刚发的刚发的刚发的刚发的刚发的刚发的工单个地方好地方和梵蒂冈花费更多沪电股份和梵蒂冈发给黑寡妇黑寡妇黑寡妇和规范化规范黑寡妇很反感很反感很反感黑寡妇规范化<\/p>","goodsName":"喂喂喂喂干","goodsNumber":100001,"goodsPriceStores":[{"attributeIds":null,"attributes":"0.5kg_1,灰白色_3,2G_4,3G_4","goodsCode":"05465161","price":214.12,"sellerNumber":"164654","storeAmount":32},{"attributeIds":null,"attributes":"2.0kg_1,绿色_3,银色_3,4G_4","goodsCode":"88448465","price":54.12,"sellerNumber":"5548815","storeAmount":22}],"goodsStatus":"ENABLE","id":1,"images":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/15/rBKx51kuWf6AfG61AACtIGv8FOc221.png","fileId":1297,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/15/rBKx51kuWf6AfG61AACtIGv8FOc221.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/15/rBKx51kuWf-ARjofAACtIGv8FOc152.png","fileId":1298,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/15/rBKx51kuWf-ARjofAACtIGv8FOc152.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/16/rBKx51kuWgGADiNYAAFe1p-qeiY130.png","fileId":1299,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/16/rBKx51kuWgGADiNYAAFe1p-qeiY130.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/16/rBKx51kvvZKAEsfEAAQodgauSzo136.png","fileId":1336,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/16/rBKx51kvvZKAEsfEAAQodgauSzo136.png"}],"limitBuyAmount":0,"minPrice":15,"publishTime":"2017-08-28 09:38","publishWay":"FixedTime","published":false,"salesVolume":1000,"sellerId":2,"sellerNumber":null,"services":[{"id":240,"name":"7天退换","remark":"商家承诺7天无理由退换货"},{"id":242,"name":"世外自营","remark":"世外生活负责发货并提供售后服务"}],"shopName":"田野旗舰店","specifications":[{"attributes":[{"id":1,"selected":false,"value":"0.5kg"},{"id":3,"selected":false,"value":"2.0kg"}],"id":1,"name":"重量"},{"attributes":[{"id":15,"selected":false,"value":"灰白色"},{"id":24,"selected":false,"value":"绿色"}],"id":3,"name":"颜色"},{"attributes":[{"id":27,"selected":false,"value":"2G"}],"id":4,"name":"内存大小"},{"attributes":[{"id":18,"selected":false,"value":"银色"}],"id":3,"name":"颜色"},{"attributes":[{"id":43,"selected":false,"value":"3G"},{"id":28,"selected":false,"value":"4G"}],"id":4,"name":"内存大小"}],"stock":4001,"transportMoney":0}
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
         * categoryFullName : 水果蔬菜-水果1-苹果
         * categoryId : 4
         * cityName : 朝阳
         * feature : 大哥发伟哥伟哥伟哥
         * goodsCode : null
         * goodsDetail : <p>但是但是但是是<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAARsAAABuCAYAAAAXtbfnAAALQklEQVR4nO3df0zTZx4H8Pf4VX60FApCCyhFQAlERDZr6ombP3ZOTbZwmZecumTLLdlyf2yJnpfb/lnuj2XJ7c5kW7LckttpstuWqDnvSNTTI7uIZs3wTpk7OMaUiSCViVVoAYuU3fPtj6NgAafrp519vxKx/fL98dCnfffzPN9v4aFvFRARRVlSrBtARImBYUNEIhg2RCSCYUNEIhg2RCSCYUNEIhg2RCSCYUNEIhg2RCSCYUNEIhg2RCSCYUNEIhg2RCSCYUNEIhg2RCSCYUNEIhg2RCSCYUNEIhg2RCQiZcgzFus2EFECeIi/8JyIJHAYRUQiGDZEJIJhQ0QiGDZEJIJhQ0QiGDZEJIJhQ0QiGDZEJIJhQ0QiGDZEJIJhQ0QiGDZEJIJhQ0QiGDZEJIJhQ0QiUmLdgEgm7PZYN4G+oxSHI9ZNoDjHyoaIRDBsiEgEw4aIRDBsiEgEw4aIRDBsiEhEXJ76ngtPscYOL0mg+8HKhohEMGyISATDhohEMGyISATDhohEMGyISMQP7tT32sN7Y92EB5o+VQerIQ87l9qw2rxY7LjfuG6iu7cf7pExTPh8YsclFQLJyTBkZWDxwiIUmHKidpy4q2xa+r+KdRMSmue2F/9x9ePXjr+K9cXVQRf+3d6FG8MeBk0MaI+59thrfaD1RbTEXdh81PWvWDeBgqT64uu+qyLHoflFsy/iLmwuDQ/GugkUJNUX7pFRkePQ/KLZF3EXNqO+27FuAgVJ9YVvclLkODS/aPZF3IUNET2YGDZEJIJhQ0QiGDZEJIJhQ0QiGDZEJIJhQ0QifnCfjfr+WfHKxidhG21G46cdd7lNLd7cbEda18d4+eLwHOuV4LUf/wQVgwfwzNnwKzPL1fKtqBt1YM/pM7gQYUtz/bM4kP8NXj1xFKexAm9tXYcVaZGO4cKxw/vxxl22nOZTCJu9FHkRXxlj6Dv1Bb6YbdOiKjxaPIneji50j+SgfqUVKc4utPZpF8qVoaEhF96LZ9Har+7mqvsVOlzp7ES3O1o/S3xJmLB5es0v8NKC9GnLenr+iL9lbsFmg3oYDE+gpfGJ4HfmewGfx7nRx/Bi1SY8ffEgDs22WmUD1qQ68V6nChoVHi2lpunfz2rAnxobgndu4dz5d1V4abezscOYDeeNZhU0iPD9kE34oNEy789O38UAWh0DWPawDTnuVpzq0pZpQaHHzYu3UdhgU28hM0wMo8PRiZ7r1+EuLsXS2hpkdLbjv1du4UflVbChUwVO2Pq5i2CvykPqcC+cCRI0moQJm0On3/WHwhOrX8Du1LN4/OQlbF+9DTvSL+F3nxxF05CqJoo34PVaKzx9Duz7/5baC7oGpRH3uhAvNe7CS9OWhYLKitfLctHR+WccCl0BPt6Lt49ECietcgn8MvFXNu5S4RdcnPNTtGjPbHcvziEdK2p3oaV25rYu3G09RvepvxMDFhtMI2042TkeWLa4BpsLAf91t95rOHteva/UFiErIw1jav02/TLUWSyw9E1dmbuwKBdpKmgc7QOqVkocCRM2AdXYoB9Bkxq6VNftxI5cHdLSqvDL9epf+GoVW3FA/evp2auGP9qCux2qTFUatvrHUKGGSS+77fjgyXK4hlTipEUKpxBVuaivbzTvxT7Vtg8KXNijhlBt9/sj0z3TF9r8QRIwhpvq6/D4BApTc6FVQJqFWTrVdYPohVFVQ+Uwjfai9czn/hDRqqOSTG2tDNQFC1h9uQ15/luleKyhYO5h2QMmocLGXLMcdVkFWLVpF7b5A+QiqhtVEPiHJ1pQhG4HKoxq/1bH8czhuz1CaF0zXss3waKGSQcWpMDrbsc7bgtW6GerbMJVY7clGxcuu7C78Vl0qJD7KsIQcJpZKya6H56B6cMozTfe26hWQ1yTChsXDFiQngLPkDYfN66GvbdQWFwG+/IMnP/88ox9zFBUhY3lqVI/SlxIoLBRL+JFFujUC39tM8LmOqYPT0rDbve4Is/1zMd97Z/YemIv3qv5Gd63TmDfp8fRWvWsKrOz8NSslQ38ldQerwpEOPH7dh92LAksP3TagYat62ByhiqtoPJtOFIF7GPQREWkymbM5YW3MB3FOq3ezUdWuhcjXYEh1WB3Bz4br8LK4kxoBQ9NlzBhY1tlR3XqBHDHB5lDE6+RK5vQXM8dtAlfy8isFYW5eAveLM/FhfbAnI05OVkt9QWrkAsqPJbj6pHPYfb/vx8IHm9Nbi506el4tTGwn1IVTmuutfvPWJWWqiCcOXmk9kfREamyweCgGkpVwFRmUNVNNvS3PGgfmtrG3deJT4KTwcswM7BmSqQZm4QJmxJsMPjQNjCMBsP8a0ekqpR/aFWKCpeP7mJ1W5kVpckpauj2vKqUbsE5ohbenus0ecBUuAXmfzoO/wXH8pfiuVUIm0MKClY2JERnQH66B903xrAqz4oapGJ4oBfhv9vOoobqy1Ju4ASHUXdIkLDpwxvN+wPVSPhiVe7q1TCqdI5hVMjTWsUx2jE9aCJN+PqHacfRpEKjKbTMuAJvrV4HDKm3vJwKtc3CwPLGdcH/d00dz2jF+oJyNCywwKzeO0sbn8f6m1/7zzixshGiQiUpCUjNKMcjyzORka5Tz5Mk6G1LYXL14GT7VVy3lyFv8gbausfDNkxTYaTDhKp2QljZTEmQsAljqEGLf4jiwuUiE/J8Tvyh6WMVIrNNECsFW7BNm7cZX4DtRuCjUNk8x8SsOb8aT5XVYE3+ApjVsEiHEXzW51XL5xpGLcWbqzahOnkYl4e88GAIp4+/j9+MaqfGy+DsP4L3giW6PseKFUaL2n8WVq1cA8+Z0/h7lB+6xFACe70ZBoxjbFIHjHgwMHALSZXpcGlnjnQmLH94MfLghTfFiIolRji7gk8IXRGyMyfgcU69S7GymZJ4YROsPJCpXsDr1uH6oCPCsCgbaaEPchhX4q2VVTC5L+AUrHjx0Z+joe88PvTPwcwmGy/UbfRP9LZ2HUVryZPYlnoJH14BdtdFXj9wvC+x58SXwWVa+OkwPhq6engC3sJNeEV7l/R5cdXrBZIyoVPNWGQ0wRRpt3QP+uBw9M1YVobCSvVwm1XFWZYH/eQwvjzfCadxCexllVifegVn2p1wF2epnvTiUv/8R8lISrxPCiVW2Jzdj7Wh2xYrFk324sO2GZfEFe5Uw6gCdUNVIhfN2L6sHhXeTrzz6VE0jWZj/bINeK5kJV5L185QGSNeN+N1teLx5reD91RYlA/jWNtxtKkACTiHl4+c89+qG6zH22qoFDhepEZPrXuH4JzNweYmno2KumRkFxuR5LmKtq7LcKqsx0gXHONW1FdaUFsxDGeGDr7RG7g02y6MlXi0NheZwbu+0Wu4ItL2+PDQt0qsGxFO+7tQn/z24KzfX/+rbYKtoZbgfJJmwm6fdb0Uh+Oej3HsVOs9b0vfv80NtqjsN/FqOSKKCYYNEYlg2BCRCIYNEYlg2BCRCIYNEYlg2BCRiLgLm8zkxLqEO55J9UVyAl5NG6+i2Rdx18vW7PxYN4GCpPrCkJU5/0okIpp9EXdhs33JI7FuAgVJ9UVZiVnkODS/aPZF3H02am1RJSZi3YgEpk/VwWrIw86lNqw2LxY5pjnfhIdrlqC7tx/ukTFM+Hwix6WAlORkVdFkYPHCIhSYcqJ3nKjtOUrCP6tDDw7tSR7NJzrFXtwNo4jowcSwISIRP7hh1Fy/5oCI4hcrGyISwbAhIhEMGyISwbAhIhEMGyISwbAhIhFx99cViOjBxMqGiEQwbIhIBMOGiEQwbIhIBMOGiEQwbIhIBMOGiEQwbIhIBMOGiEQwbIhIBMOGiEQwbIhIxP8A1EN7H/TweYkAAAAASUVORK5CYII=">鬼地方个电饭锅地方刚发的刚发的刚发的刚发的刚发的刚发的刚发的刚发的工单个地方好地方和梵蒂冈花费更多沪电股份和梵蒂冈发给黑寡妇黑寡妇黑寡妇和规范化规范黑寡妇很反感很反感很反感黑寡妇规范化</p>
         * goodsName : 喂喂喂喂干
         * goodsNumber : 100001
         * goodsPriceStores : [{"attributeIds":null,"attributes":"0.5kg_1,灰白色_3,2G_4,3G_4","goodsCode":"05465161","price":214.12,"sellerNumber":"164654","storeAmount":32},{"attributeIds":null,"attributes":"2.0kg_1,绿色_3,银色_3,4G_4","goodsCode":"88448465","price":54.12,"sellerNumber":"5548815","storeAmount":22}]
         * goodsStatus : ENABLE
         * id : 1
         * images : [{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/15/rBKx51kuWf6AfG61AACtIGv8FOc221.png","fileId":1297,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/15/rBKx51kuWf6AfG61AACtIGv8FOc221.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/15/rBKx51kuWf-ARjofAACtIGv8FOc152.png","fileId":1298,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/15/rBKx51kuWf-ARjofAACtIGv8FOc152.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/16/rBKx51kuWgGADiNYAAFe1p-qeiY130.png","fileId":1299,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/16/rBKx51kuWgGADiNYAAFe1p-qeiY130.png"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/16/rBKx51kvvZKAEsfEAAQodgauSzo136.png","fileId":1336,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/16/rBKx51kvvZKAEsfEAAQodgauSzo136.png"}]
         * limitBuyAmount : 0
         * minPrice : 15
         * publishTime : 2017-08-28 09:38
         * publishWay : FixedTime
         * published : false
         * salesVolume : 1000
         * sellerId : 2
         * sellerNumber : null
         * services : [{"id":240,"name":"7天退换","remark":"商家承诺7天无理由退换货"},{"id":242,"name":"世外自营","remark":"世外生活负责发货并提供售后服务"}]
         * shopName : 田野旗舰店
         * specifications : [{"attributes":[{"id":1,"selected":false,"value":"0.5kg"},{"id":3,"selected":false,"value":"2.0kg"}],"id":1,"name":"重量"},{"attributes":[{"id":15,"selected":false,"value":"灰白色"},{"id":24,"selected":false,"value":"绿色"}],"id":3,"name":"颜色"},{"attributes":[{"id":27,"selected":false,"value":"2G"}],"id":4,"name":"内存大小"},{"attributes":[{"id":18,"selected":false,"value":"银色"}],"id":3,"name":"颜色"},{"attributes":[{"id":43,"selected":false,"value":"3G"},{"id":28,"selected":false,"value":"4G"}],"id":4,"name":"内存大小"}]
         * stock : 4001
         * transportMoney : 0
         */

        private String categoryFullName;
        private int categoryId;
        private String cityName;
        private String feature;
        private Object goodsCode;
        private String goodsDetail;
        private String goodsName;
        private int goodsNumber;
        private String goodsStatus;
        private int id;
        private int limitBuyAmount;
        private int minPrice;
        private String publishTime;
        private String publishWay;
        private boolean published;
        private int salesVolume;
        private int sellerId;
        private Object sellerNumber;
        private String shopName;
        private int stock;
        private int transportMoney;
        private List<GoodsPriceStoresBean> goodsPriceStores;
        private List<ImagesBean> images;
        private List<ServicesBean> services;
        private List<SpecificationsBean> specifications;

        public String getCategoryFullName() {
            return categoryFullName;
        }

        public void setCategoryFullName(String categoryFullName) {
            this.categoryFullName = categoryFullName;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getFeature() {
            return feature;
        }

        public void setFeature(String feature) {
            this.feature = feature;
        }

        public Object getGoodsCode() {
            return goodsCode;
        }

        public void setGoodsCode(Object goodsCode) {
            this.goodsCode = goodsCode;
        }

        public String getGoodsDetail() {
            return goodsDetail;
        }

        public void setGoodsDetail(String goodsDetail) {
            this.goodsDetail = goodsDetail;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public int getGoodsNumber() {
            return goodsNumber;
        }

        public void setGoodsNumber(int goodsNumber) {
            this.goodsNumber = goodsNumber;
        }

        public String getGoodsStatus() {
            return goodsStatus;
        }

        public void setGoodsStatus(String goodsStatus) {
            this.goodsStatus = goodsStatus;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLimitBuyAmount() {
            return limitBuyAmount;
        }

        public void setLimitBuyAmount(int limitBuyAmount) {
            this.limitBuyAmount = limitBuyAmount;
        }

        public int getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(int minPrice) {
            this.minPrice = minPrice;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getPublishWay() {
            return publishWay;
        }

        public void setPublishWay(String publishWay) {
            this.publishWay = publishWay;
        }

        public boolean isPublished() {
            return published;
        }

        public void setPublished(boolean published) {
            this.published = published;
        }

        public int getSalesVolume() {
            return salesVolume;
        }

        public void setSalesVolume(int salesVolume) {
            this.salesVolume = salesVolume;
        }

        public int getSellerId() {
            return sellerId;
        }

        public void setSellerId(int sellerId) {
            this.sellerId = sellerId;
        }

        public Object getSellerNumber() {
            return sellerNumber;
        }

        public void setSellerNumber(Object sellerNumber) {
            this.sellerNumber = sellerNumber;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getTransportMoney() {
            return transportMoney;
        }

        public void setTransportMoney(int transportMoney) {
            this.transportMoney = transportMoney;
        }

        public List<GoodsPriceStoresBean> getGoodsPriceStores() {
            return goodsPriceStores;
        }

        public void setGoodsPriceStores(List<GoodsPriceStoresBean> goodsPriceStores) {
            this.goodsPriceStores = goodsPriceStores;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public List<ServicesBean> getServices() {
            return services;
        }

        public void setServices(List<ServicesBean> services) {
            this.services = services;
        }

        public List<SpecificationsBean> getSpecifications() {
            return specifications;
        }

        public void setSpecifications(List<SpecificationsBean> specifications) {
            this.specifications = specifications;
        }

        public static class GoodsPriceStoresBean {
            /**
             * attributeIds : null
             * attributes : 0.5kg_1,灰白色_3,2G_4,3G_4
             * goodsCode : 05465161
             * price : 214.12
             * sellerNumber : 164654
             * storeAmount : 32
             */

            private Object attributeIds;
            private String attributes;
            private String goodsCode;
            private double price;
            private String sellerNumber;
            private int storeAmount;

            public Object getAttributeIds() {
                return attributeIds;
            }

            public void setAttributeIds(Object attributeIds) {
                this.attributeIds = attributeIds;
            }

            public String getAttributes() {
                return attributes;
            }

            public void setAttributes(String attributes) {
                this.attributes = attributes;
            }

            public String getGoodsCode() {
                return goodsCode;
            }

            public void setGoodsCode(String goodsCode) {
                this.goodsCode = goodsCode;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getSellerNumber() {
                return sellerNumber;
            }

            public void setSellerNumber(String sellerNumber) {
                this.sellerNumber = sellerNumber;
            }

            public int getStoreAmount() {
                return storeAmount;
            }

            public void setStoreAmount(int storeAmount) {
                this.storeAmount = storeAmount;
            }
        }

        public static class ImagesBean {
            /**
             * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/15/rBKx51kuWf6AfG61AACtIGv8FOc221.png
             * fileId : 1297
             * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/15/rBKx51kuWf6AfG61AACtIGv8FOc221.png
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

        public static class ServicesBean {
            /**
             * id : 240
             * name : 7天退换
             * remark : 商家承诺7天无理由退换货
             */

            private int id;
            private String name;
            private String remark;

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

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }

        public static class SpecificationsBean {
            /**
             * attributes : [{"id":1,"selected":false,"value":"0.5kg"},{"id":3,"selected":false,"value":"2.0kg"}]
             * id : 1
             * name : 重量
             */

            private int id;
            private String name;
            private List<AttributesBean> attributes;

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

            public List<AttributesBean> getAttributes() {
                return attributes;
            }

            public void setAttributes(List<AttributesBean> attributes) {
                this.attributes = attributes;
            }

            public static class AttributesBean {
                /**
                 * id : 1
                 * selected : false
                 * value : 0.5kg
                 */

                private int id;
                private boolean selected;
                private String value;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public boolean isSelected() {
                    return selected;
                }

                public void setSelected(boolean selected) {
                    this.selected = selected;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }
    }
}
