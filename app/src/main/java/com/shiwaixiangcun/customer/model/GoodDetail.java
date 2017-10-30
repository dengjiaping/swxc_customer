package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2017/9/11
 */

public class GoodDetail {


    /**
     * data : {"adSellTime":null,"advanceSellTime":null,"advanceStatus":"NoAdvance","badTotal":0,"categoryFullName":"地方特产-特色食品-零食","categoryId":10,"cityName":"赤水","evaluateTotal":2,"evaluates":[{"attrDescription":"重量:2kg","avatar":"http://resource.shiwaixiangcun.cn/group1/M00/00/00/rBKx5VkZZlOAIivEAAAcGHPVROQ697.png","content":"垃圾商品","evaluateTime":"2017-10-30","id":1,"images":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-eAeprFADb-QImlinU709.jpg","fileId":3958,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-eAeprFADb-QImlinU709.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-2AZgiIACm1-ZELSxQ766.jpg","fileId":3959,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-2AZgiIACm1-ZELSxQ766.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n_SAUQ9KADQ9oFFyyDg811.jpg","fileId":3960,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n_SAUQ9KADQ9oFFyyDg811.jpg"}],"nick":"1***6","score":3},{"attrDescription":"重量:1kg","avatar":"http://resource.shiwaixiangcun.cn/group1/M00/00/00/rBKx5VkZZlOAIivEAAAcGHPVROQ697.png","content":"商品质量差得令人窒息","evaluateTime":"2017-10-30","id":2,"images":[],"nick":"1***6","score":2}],"feature":"好吃","goodsCode":null,"goodsDetail":"<p>撒娇回复我改为覆盖物if贵司发布该会所的覅USD浩丰科技收到货覅&nbsp;&nbsp;<\/p>\n\n<p>ksafkljdskfhidsf<\/p>\n\n<p>&nbsp;<\/p>\n","goodsName":"零食","goodsNumber":100020,"goodsPriceStores":[{"attributeIds":null,"attributes":"1kg_3","goodsCode":null,"price":0.01,"sellerNumber":null,"storeAmount":1231243},{"attributeIds":null,"attributes":"2kg_3","goodsCode":null,"price":0.01,"sellerNumber":null,"storeAmount":23241}],"goodsStatus":"ENABLE","highTotal":0,"id":20,"images":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":3954,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2iveAKFyQAAAl6egL2YI348.jpg","fileId":3955,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2iveAKFyQAAAl6egL2YI348.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivuAXF8nAACEsbNYTTk159.jpg","fileId":3956,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivuAXF8nAACEsbNYTTk159.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2iv-AdqF7AACW98spLrw343.jpg","fileId":3957,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2iv-AdqF7AACW98spLrw343.jpg"}],"lastDeliveryTime":null,"latestDeliveryTime":null,"limitBuyAmount":0,"midTotal":2,"minPrice":0.01,"publishTime":null,"publishWay":"Now","published":true,"salesVolume":2,"sellerId":1,"sellerNumber":null,"services":[{"id":226,"name":"7天退换","remark":"商家承诺7天无理由退换货"},{"id":227,"name":"假一赔十","remark":"若收到的商品是假买品牌，可获得加倍赔偿"},{"id":228,"name":"48小时快速退款","remark":"收到退货包裹并确认无误后，将在48小时内办理退款，退款将原路返回，不同银行处理时间不同，预计1-5个工作日到账"}],"shopName":"世外健康旗舰店","specifications":[{"attributes":[{"id":12,"selected":false,"value":"1kg"},{"id":13,"selected":false,"value":"2kg"}],"id":3,"name":"重量"}],"stock":1254484,"transportMoney":0}
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

    public static class DataBean implements Parcelable {
        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
        /**
         * adSellTime : null
         * advanceSellTime : null
         * advanceStatus : NoAdvance
         * badTotal : 0
         * categoryFullName : 地方特产-特色食品-零食
         * categoryId : 10
         * cityName : 赤水
         * evaluateTotal : 2
         * evaluates : [{"attrDescription":"重量:2kg","avatar":"http://resource.shiwaixiangcun.cn/group1/M00/00/00/rBKx5VkZZlOAIivEAAAcGHPVROQ697.png","content":"垃圾商品","evaluateTime":"2017-10-30","id":1,"images":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-eAeprFADb-QImlinU709.jpg","fileId":3958,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-eAeprFADb-QImlinU709.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-2AZgiIACm1-ZELSxQ766.jpg","fileId":3959,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-2AZgiIACm1-ZELSxQ766.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n_SAUQ9KADQ9oFFyyDg811.jpg","fileId":3960,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n_SAUQ9KADQ9oFFyyDg811.jpg"}],"nick":"1***6","score":3},{"attrDescription":"重量:1kg","avatar":"http://resource.shiwaixiangcun.cn/group1/M00/00/00/rBKx5VkZZlOAIivEAAAcGHPVROQ697.png","content":"商品质量差得令人窒息","evaluateTime":"2017-10-30","id":2,"images":[],"nick":"1***6","score":2}]
         * feature : 好吃
         * goodsCode : null
         * goodsDetail : <p>撒娇回复我改为覆盖物if贵司发布该会所的覅USD浩丰科技收到货覅&nbsp;&nbsp;</p>

         <p>ksafkljdskfhidsf</p>

         <p>&nbsp;</p>

         * goodsName : 零食
         * goodsNumber : 100020
         * goodsPriceStores : [{"attributeIds":null,"attributes":"1kg_3","goodsCode":null,"price":0.01,"sellerNumber":null,"storeAmount":1231243},{"attributeIds":null,"attributes":"2kg_3","goodsCode":null,"price":0.01,"sellerNumber":null,"storeAmount":23241}]
         * goodsStatus : ENABLE
         * highTotal : 0
         * id : 20
         * images : [{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":3954,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2iveAKFyQAAAl6egL2YI348.jpg","fileId":3955,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2iveAKFyQAAAl6egL2YI348.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivuAXF8nAACEsbNYTTk159.jpg","fileId":3956,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivuAXF8nAACEsbNYTTk159.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2iv-AdqF7AACW98spLrw343.jpg","fileId":3957,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2iv-AdqF7AACW98spLrw343.jpg"}]
         * lastDeliveryTime : null
         * latestDeliveryTime : null
         * limitBuyAmount : 0
         * midTotal : 2
         * minPrice : 0.01
         * publishTime : null
         * publishWay : Now
         * published : true
         * salesVolume : 2
         * sellerId : 1
         * sellerNumber : null
         * services : [{"id":226,"name":"7天退换","remark":"商家承诺7天无理由退换货"},{"id":227,"name":"假一赔十","remark":"若收到的商品是假买品牌，可获得加倍赔偿"},{"id":228,"name":"48小时快速退款","remark":"收到退货包裹并确认无误后，将在48小时内办理退款，退款将原路返回，不同银行处理时间不同，预计1-5个工作日到账"}]
         * shopName : 世外健康旗舰店
         * specifications : [{"attributes":[{"id":12,"selected":false,"value":"1kg"},{"id":13,"selected":false,"value":"2kg"}],"id":3,"name":"重量"}]
         * stock : 1254484
         * transportMoney : 0
         */

        private long adSellTime;
        private long advanceSellTime;
        private String advanceStatus;
        private int badTotal;
        private String categoryFullName;
        private int categoryId;
        private String cityName;
        private int evaluateTotal;
        private String feature;
        private String goodsCode;
        private String goodsDetail;
        private String goodsName;
        private int goodsNumber;
        private String goodsStatus;
        private int highTotal;
        private int id;
        private long lastDeliveryTime;
        private String latestDeliveryTime;
        private int limitBuyAmount;
        private int midTotal;
        private double minPrice;
        private String publishTime;
        private String publishWay;
        private boolean published;
        private int salesVolume;
        private int sellerId;
        private String sellerNumber;
        private String shopName;
        private int stock;
        private int transportMoney;
        private List<EvaluatesBean> evaluates;
        private List<GoodsPriceStoresBean> goodsPriceStores;
        private List<ImagesBeanX> images;
        private List<ServicesBean> services;
        private List<SpecificationsBean> specifications;

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.adSellTime = in.readLong();
            this.advanceSellTime = in.readLong();
            this.advanceStatus = in.readString();
            this.badTotal = in.readInt();
            this.categoryFullName = in.readString();
            this.categoryId = in.readInt();
            this.cityName = in.readString();
            this.evaluateTotal = in.readInt();
            this.feature = in.readString();
            this.goodsCode = in.readString();
            this.goodsDetail = in.readString();
            this.goodsName = in.readString();
            this.goodsNumber = in.readInt();
            this.goodsStatus = in.readString();
            this.highTotal = in.readInt();
            this.id = in.readInt();
            this.lastDeliveryTime = in.readLong();
            this.latestDeliveryTime = in.readString();
            this.limitBuyAmount = in.readInt();
            this.midTotal = in.readInt();
            this.minPrice = in.readDouble();
            this.publishTime = in.readString();
            this.publishWay = in.readString();
            this.published = in.readByte() != 0;
            this.salesVolume = in.readInt();
            this.sellerId = in.readInt();
            this.sellerNumber = in.readString();
            this.shopName = in.readString();
            this.stock = in.readInt();
            this.transportMoney = in.readInt();
            this.evaluates = in.createTypedArrayList(EvaluatesBean.CREATOR);
            this.goodsPriceStores = in.createTypedArrayList(GoodsPriceStoresBean.CREATOR);
            this.images = in.createTypedArrayList(ImagesBeanX.CREATOR);
            this.services = in.createTypedArrayList(ServicesBean.CREATOR);
            this.specifications = in.createTypedArrayList(SpecificationsBean.CREATOR);
        }

        public long getAdSellTime() {
            return adSellTime;
        }

        public void setAdSellTime(long adSellTime) {
            this.adSellTime = adSellTime;
        }

        public long getAdvanceSellTime() {
            return advanceSellTime;
        }

        public void setAdvanceSellTime(long advanceSellTime) {
            this.advanceSellTime = advanceSellTime;
        }

        public String getAdvanceStatus() {
            return advanceStatus;
        }

        public void setAdvanceStatus(String advanceStatus) {
            this.advanceStatus = advanceStatus;
        }

        public int getBadTotal() {
            return badTotal;
        }

        public void setBadTotal(int badTotal) {
            this.badTotal = badTotal;
        }

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

        public int getEvaluateTotal() {
            return evaluateTotal;
        }

        public void setEvaluateTotal(int evaluateTotal) {
            this.evaluateTotal = evaluateTotal;
        }

        public String getFeature() {
            return feature;
        }

        public void setFeature(String feature) {
            this.feature = feature;
        }

        public String getGoodsCode() {
            return goodsCode;
        }

        public void setGoodsCode(String goodsCode) {
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

        public int getHighTotal() {
            return highTotal;
        }

        public void setHighTotal(int highTotal) {
            this.highTotal = highTotal;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getLastDeliveryTime() {
            return lastDeliveryTime;
        }

        public void setLastDeliveryTime(long lastDeliveryTime) {
            this.lastDeliveryTime = lastDeliveryTime;
        }

        public String getLatestDeliveryTime() {
            return latestDeliveryTime;
        }

        public void setLatestDeliveryTime(String latestDeliveryTime) {
            this.latestDeliveryTime = latestDeliveryTime;
        }

        public int getLimitBuyAmount() {
            return limitBuyAmount;
        }

        public void setLimitBuyAmount(int limitBuyAmount) {
            this.limitBuyAmount = limitBuyAmount;
        }

        public int getMidTotal() {
            return midTotal;
        }

        public void setMidTotal(int midTotal) {
            this.midTotal = midTotal;
        }

        public double getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(double minPrice) {
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

        public String getSellerNumber() {
            return sellerNumber;
        }

        public void setSellerNumber(String sellerNumber) {
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

        public List<EvaluatesBean> getEvaluates() {
            return evaluates;
        }

        public void setEvaluates(List<EvaluatesBean> evaluates) {
            this.evaluates = evaluates;
        }

        public List<GoodsPriceStoresBean> getGoodsPriceStores() {
            return goodsPriceStores;
        }

        public void setGoodsPriceStores(List<GoodsPriceStoresBean> goodsPriceStores) {
            this.goodsPriceStores = goodsPriceStores;
        }

        public List<ImagesBeanX> getImages() {
            return images;
        }

        public void setImages(List<ImagesBeanX> images) {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.adSellTime);
            dest.writeLong(this.advanceSellTime);
            dest.writeString(this.advanceStatus);
            dest.writeInt(this.badTotal);
            dest.writeString(this.categoryFullName);
            dest.writeInt(this.categoryId);
            dest.writeString(this.cityName);
            dest.writeInt(this.evaluateTotal);
            dest.writeString(this.feature);
            dest.writeString(this.goodsCode);
            dest.writeString(this.goodsDetail);
            dest.writeString(this.goodsName);
            dest.writeInt(this.goodsNumber);
            dest.writeString(this.goodsStatus);
            dest.writeInt(this.highTotal);
            dest.writeInt(this.id);
            dest.writeLong(this.lastDeliveryTime);
            dest.writeString(this.latestDeliveryTime);
            dest.writeInt(this.limitBuyAmount);
            dest.writeInt(this.midTotal);
            dest.writeDouble(this.minPrice);
            dest.writeString(this.publishTime);
            dest.writeString(this.publishWay);
            dest.writeByte(this.published ? (byte) 1 : (byte) 0);
            dest.writeInt(this.salesVolume);
            dest.writeInt(this.sellerId);
            dest.writeString(this.sellerNumber);
            dest.writeString(this.shopName);
            dest.writeInt(this.stock);
            dest.writeInt(this.transportMoney);
            dest.writeTypedList(this.evaluates);
            dest.writeTypedList(this.goodsPriceStores);
            dest.writeTypedList(this.images);
            dest.writeTypedList(this.services);
            dest.writeTypedList(this.specifications);
        }

        public static class EvaluatesBean implements Parcelable {
            public static final Parcelable.Creator<EvaluatesBean> CREATOR = new Parcelable.Creator<EvaluatesBean>() {
                @Override
                public EvaluatesBean createFromParcel(Parcel source) {
                    return new EvaluatesBean(source);
                }

                @Override
                public EvaluatesBean[] newArray(int size) {
                    return new EvaluatesBean[size];
                }
            };
            /**
             * attrDescription : 重量:2kg
             * avatar : http://resource.shiwaixiangcun.cn/group1/M00/00/00/rBKx5VkZZlOAIivEAAAcGHPVROQ697.png
             * content : 垃圾商品
             * evaluateTime : 2017-10-30
             * id : 1
             * images : [{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-eAeprFADb-QImlinU709.jpg","fileId":3958,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-eAeprFADb-QImlinU709.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-2AZgiIACm1-ZELSxQ766.jpg","fileId":3959,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-2AZgiIACm1-ZELSxQ766.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n_SAUQ9KADQ9oFFyyDg811.jpg","fileId":3960,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n_SAUQ9KADQ9oFFyyDg811.jpg"}]
             * nick : 1***6
             * score : 3
             */

            private String attrDescription;
            private String avatar;
            private String content;
            private String evaluateTime;
            private int id;
            private String nick;
            private int score;
            private List<ImagesBean> images;

            public EvaluatesBean() {
            }

            protected EvaluatesBean(Parcel in) {
                this.attrDescription = in.readString();
                this.avatar = in.readString();
                this.content = in.readString();
                this.evaluateTime = in.readString();
                this.id = in.readInt();
                this.nick = in.readString();
                this.score = in.readInt();
                this.images = new ArrayList<ImagesBean>();
                in.readList(this.images, ImagesBean.class.getClassLoader());
            }

            public String getAttrDescription() {
                return attrDescription;
            }

            public void setAttrDescription(String attrDescription) {
                this.attrDescription = attrDescription;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getEvaluateTime() {
                return evaluateTime;
            }

            public void setEvaluateTime(String evaluateTime) {
                this.evaluateTime = evaluateTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNick() {
                return nick;
            }

            public void setNick(String nick) {
                this.nick = nick;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public List<ImagesBean> getImages() {
                return images;
            }

            public void setImages(List<ImagesBean> images) {
                this.images = images;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.attrDescription);
                dest.writeString(this.avatar);
                dest.writeString(this.content);
                dest.writeString(this.evaluateTime);
                dest.writeInt(this.id);
                dest.writeString(this.nick);
                dest.writeInt(this.score);
                dest.writeList(this.images);
            }

            public static class ImagesBean implements Parcelable {
                public static final Creator<ImagesBean> CREATOR = new Creator<ImagesBean>() {
                    @Override
                    public ImagesBean createFromParcel(Parcel source) {
                        return new ImagesBean(source);
                    }

                    @Override
                    public ImagesBean[] newArray(int size) {
                        return new ImagesBean[size];
                    }
                };
                /**
                 * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-eAeprFADb-QImlinU709.jpg
                 * fileId : 3958
                 * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2n-eAeprFADb-QImlinU709.jpg
                 */

                private String accessUrl;
                private int fileId;
                private String thumbImageURL;

                public ImagesBean() {
                }

                protected ImagesBean(Parcel in) {
                    this.accessUrl = in.readString();
                    this.fileId = in.readInt();
                    this.thumbImageURL = in.readString();
                }

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

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.accessUrl);
                    dest.writeInt(this.fileId);
                    dest.writeString(this.thumbImageURL);
                }
            }
        }

        public static class GoodsPriceStoresBean implements Parcelable {
            public static final Parcelable.Creator<GoodsPriceStoresBean> CREATOR = new Parcelable.Creator<GoodsPriceStoresBean>() {
                @Override
                public GoodsPriceStoresBean createFromParcel(Parcel source) {
                    return new GoodsPriceStoresBean(source);
                }

                @Override
                public GoodsPriceStoresBean[] newArray(int size) {
                    return new GoodsPriceStoresBean[size];
                }
            };
            /**
             * attributeIds : null
             * attributes : 1kg_3
             * goodsCode : null
             * price : 0.01
             * sellerNumber : null
             * storeAmount : 1231243
             */

            private String attributeIds;
            private String attributes;
            private String goodsCode;
            private double price;
            private String sellerNumber;
            private int storeAmount;

            public GoodsPriceStoresBean() {
            }

            protected GoodsPriceStoresBean(Parcel in) {
                this.attributeIds = in.readString();
                this.attributes = in.readString();
                this.goodsCode = in.readString();
                this.price = in.readDouble();
                this.sellerNumber = in.readString();
                this.storeAmount = in.readInt();
            }

            public String getAttributeIds() {
                return attributeIds;
            }

            public void setAttributeIds(String attributeIds) {
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.attributeIds);
                dest.writeString(this.attributes);
                dest.writeString(this.goodsCode);
                dest.writeDouble(this.price);
                dest.writeString(this.sellerNumber);
                dest.writeInt(this.storeAmount);
            }
        }

        public static class ImagesBeanX implements Parcelable {
            public static final Parcelable.Creator<ImagesBeanX> CREATOR = new Parcelable.Creator<ImagesBeanX>() {
                @Override
                public ImagesBeanX createFromParcel(Parcel source) {
                    return new ImagesBeanX(source);
                }

                @Override
                public ImagesBeanX[] newArray(int size) {
                    return new ImagesBeanX[size];
                }
            };
            /**
             * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg
             * fileId : 3954
             * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg
             */

            private String accessUrl;
            private int fileId;
            private String thumbImageURL;

            public ImagesBeanX() {
            }

            protected ImagesBeanX(Parcel in) {
                this.accessUrl = in.readString();
                this.fileId = in.readInt();
                this.thumbImageURL = in.readString();
            }

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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.accessUrl);
                dest.writeInt(this.fileId);
                dest.writeString(this.thumbImageURL);
            }
        }

        public static class ServicesBean implements Parcelable {
            public static final Parcelable.Creator<ServicesBean> CREATOR = new Parcelable.Creator<ServicesBean>() {
                @Override
                public ServicesBean createFromParcel(Parcel source) {
                    return new ServicesBean(source);
                }

                @Override
                public ServicesBean[] newArray(int size) {
                    return new ServicesBean[size];
                }
            };
            /**
             * id : 226
             * name : 7天退换
             * remark : 商家承诺7天无理由退换货
             */

            private int id;
            private String name;
            private String remark;

            public ServicesBean() {
            }

            protected ServicesBean(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
                this.remark = in.readString();
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

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeString(this.remark);
            }
        }

        public static class SpecificationsBean implements Parcelable {
            public static final Parcelable.Creator<SpecificationsBean> CREATOR = new Parcelable.Creator<SpecificationsBean>() {
                @Override
                public SpecificationsBean createFromParcel(Parcel source) {
                    return new SpecificationsBean(source);
                }

                @Override
                public SpecificationsBean[] newArray(int size) {
                    return new SpecificationsBean[size];
                }
            };
            /**
             * attributes : [{"id":12,"selected":false,"value":"1kg"},{"id":13,"selected":false,"value":"2kg"}]
             * id : 3
             * name : 重量
             */

            private int id;
            private String name;
            private List<AttributesBean> attributes;

            public SpecificationsBean() {
            }

            protected SpecificationsBean(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
                this.attributes = new ArrayList<AttributesBean>();
                in.readList(this.attributes, AttributesBean.class.getClassLoader());
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

            public List<AttributesBean> getAttributes() {
                return attributes;
            }

            public void setAttributes(List<AttributesBean> attributes) {
                this.attributes = attributes;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeList(this.attributes);
            }

            public static class AttributesBean implements Parcelable {
                public static final Creator<AttributesBean> CREATOR = new Creator<AttributesBean>() {
                    @Override
                    public AttributesBean createFromParcel(Parcel source) {
                        return new AttributesBean(source);
                    }

                    @Override
                    public AttributesBean[] newArray(int size) {
                        return new AttributesBean[size];
                    }
                };
                /**
                 * id : 12
                 * selected : false
                 * value : 1kg
                 */

                private int id;
                private boolean selected;
                private String value;

                public AttributesBean() {
                }

                protected AttributesBean(Parcel in) {
                    this.id = in.readInt();
                    this.selected = in.readByte() != 0;
                    this.value = in.readString();
                }

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

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.id);
                    dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
                    dest.writeString(this.value);
                }
            }
        }
    }
}