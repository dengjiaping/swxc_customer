package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */

public class GoodDetail {
    /**
     * data : {"categoryFullName":"水果蔬菜","categoryId":2,"cityName":"通州","feature":"打发我个","goodsCode":null,"goodsDetail":"<p>dsfwgwergrw<\/p>\n\n<p>&nbsp;<\/p>\n\n<p>rgrg<\/p>\n\n<p>&nbsp;<\/p>\n\n<p>rgrg<\/p>\n\n<p><img alt=\"\" height=\"91\" src=\"http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2Q2-AcXT_AAA2Ynb5NIs251.jpg\" width=\"121\" /><\/p>\n","goodsName":"芒果01（有运费）","goodsNumber":100004,"goodsPriceStores":[{"attributeIds":null,"attributes":"1kg_1","goodsCode":null,"price":0.01,"sellerNumber":null,"storeAmount":9}],"goodsStatus":"ENABLE","id":109,"images":[{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2Q0WATSGoAAAyxNR9tng611.jpg","fileId":4427,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2Q0WATSGoAAAyxNR9tng611.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2Q0eAV8mqAAA76G3dmhA833.jpg","fileId":4428,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2Q0eAV8mqAAA76G3dmhA833.jpg"}],"limitBuyAmount":3,"minPrice":0.01,"publishTime":null,"publishWay":"Now","published":true,"salesVolume":0,"sellerId":1070,"sellerNumber":null,"services":[{"id":240,"name":"7天退换","remark":"商家承诺7天无理由退换货"},{"id":241,"name":"48小时快速退款","remark":"收到退货包裹并确认无误后，将在48小时内办理退款，退款将原路返回，不同银行处理时间不同，预计1-5个工作日到账"},{"id":242,"name":"世外自营","remark":"世外生活负责发货并提供售后服务"},{"id":243,"name":"货到付款","remark":"支持送货上门后再收款，支持现金、微信等方式"}],"shopName":"helin旗舰店","specifications":[{"attributes":[{"id":36,"selected":false,"value":"1kg"}],"id":1,"name":"重量"}],"stock":12,"transportMoney":23.3}
     * message : 操作成功
     * responseCode : 1001
     * success : true
     */

    private DataBean data;
    private String message;
    private int responseCode;
    private boolean success;

    public GoodDetail() {
    }

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
         * categoryFullName : 水果蔬菜
         * categoryId : 2
         * cityName : 通州
         * feature : 打发我个
         * goodsCode : null
         * goodsDetail : <p>dsfwgwergrw</p>

         <p>&nbsp;</p>

         <p>rgrg</p>

         <p>&nbsp;</p>

         <p>rgrg</p>

         <p><img alt="" height="91" src="http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2Q2-AcXT_AAA2Ynb5NIs251.jpg" width="121" /></p>

         * goodsName : 芒果01（有运费）
         * goodsNumber : 100004
         * goodsPriceStores : [{"attributeIds":null,"attributes":"1kg_1","goodsCode":null,"price":0.01,"sellerNumber":null,"storeAmount":9}]
         * goodsStatus : ENABLE
         * id : 109
         * images : [{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2Q0WATSGoAAAyxNR9tng611.jpg","fileId":4427,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2Q0WATSGoAAAyxNR9tng611.jpg"},{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2Q0eAV8mqAAA76G3dmhA833.jpg","fileId":4428,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2Q0eAV8mqAAA76G3dmhA833.jpg"}]
         * limitBuyAmount : 3
         * minPrice : 0.01
         * publishTime : null
         * publishWay : Now
         * published : true
         * salesVolume : 0
         * sellerId : 1070
         * sellerNumber : null
         * services : [{"id":240,"name":"7天退换","remark":"商家承诺7天无理由退换货"},{"id":241,"name":"48小时快速退款","remark":"收到退货包裹并确认无误后，将在48小时内办理退款，退款将原路返回，不同银行处理时间不同，预计1-5个工作日到账"},{"id":242,"name":"世外自营","remark":"世外生活负责发货并提供售后服务"},{"id":243,"name":"货到付款","remark":"支持送货上门后再收款，支持现金、微信等方式"}]
         * shopName : helin旗舰店
         * specifications : [{"attributes":[{"id":36,"selected":false,"value":"1kg"}],"id":1,"name":"重量"}]
         * stock : 12
         * transportMoney : 23.3
         */


        private String categoryFullName;
        private int categoryId;
        private String cityName;
        private String feature;
        private String goodsCode;
        private String goodsDetail;
        private String goodsName;
        private int goodsNumber;
        private String goodsStatus;
        private int id;
        private int limitBuyAmount;
        private double minPrice;
        private String publishTime;
        private String publishWay;
        private boolean published;
        private int salesVolume;
        private int sellerId;
        private String sellerNumber;
        private String shopName;
        private int stock;
        private double transportMoney;
        private List<GoodsPriceStoresBean> goodsPriceStores;
        private List<ImagesBean> images;
        private List<ServicesBean> services;
        private List<SpecificationsBean> specifications;

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.categoryFullName = in.readString();
            this.categoryId = in.readInt();
            this.cityName = in.readString();
            this.feature = in.readString();
            this.goodsCode = in.readString();
            this.goodsDetail = in.readString();
            this.goodsName = in.readString();
            this.goodsNumber = in.readInt();
            this.goodsStatus = in.readString();
            this.id = in.readInt();
            this.limitBuyAmount = in.readInt();
            this.minPrice = in.readDouble();
            this.publishTime = in.readString();
            this.publishWay = in.readString();
            this.published = in.readByte() != 0;
            this.salesVolume = in.readInt();
            this.sellerId = in.readInt();
            this.sellerNumber = in.readString();
            this.shopName = in.readString();
            this.stock = in.readInt();
            this.transportMoney = in.readDouble();
            this.goodsPriceStores = new ArrayList<GoodsPriceStoresBean>();
            in.readList(this.goodsPriceStores, GoodsPriceStoresBean.class.getClassLoader());
            this.images = new ArrayList<ImagesBean>();
            in.readList(this.images, ImagesBean.class.getClassLoader());
            this.services = new ArrayList<ServicesBean>();
            in.readList(this.services, ServicesBean.class.getClassLoader());
            this.specifications = new ArrayList<SpecificationsBean>();
            in.readList(this.specifications, SpecificationsBean.class.getClassLoader());
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

        public double getTransportMoney() {
            return transportMoney;
        }

        public void setTransportMoney(double transportMoney) {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.categoryFullName);
            dest.writeInt(this.categoryId);
            dest.writeString(this.cityName);
            dest.writeString(this.feature);
            dest.writeString(this.goodsCode);
            dest.writeString(this.goodsDetail);
            dest.writeString(this.goodsName);
            dest.writeInt(this.goodsNumber);
            dest.writeString(this.goodsStatus);
            dest.writeInt(this.id);
            dest.writeInt(this.limitBuyAmount);
            dest.writeDouble(this.minPrice);
            dest.writeString(this.publishTime);
            dest.writeString(this.publishWay);
            dest.writeByte(this.published ? (byte) 1 : (byte) 0);
            dest.writeInt(this.salesVolume);
            dest.writeInt(this.sellerId);
            dest.writeString(this.sellerNumber);
            dest.writeString(this.shopName);
            dest.writeInt(this.stock);
            dest.writeDouble(this.transportMoney);
            dest.writeList(this.goodsPriceStores);
            dest.writeList(this.images);
            dest.writeList(this.services);
            dest.writeList(this.specifications);
        }

        public static class GoodsPriceStoresBean implements Parcelable {
            public static final Creator<GoodsPriceStoresBean> CREATOR = new Creator<GoodsPriceStoresBean>() {
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
             * attributes : 1kg_1
             * goodsCode : null
             * price : 0.01
             * sellerNumber : null
             * storeAmount : 9
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
             * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2Q0WATSGoAAAyxNR9tng611.jpg
             * fileId : 4427
             * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2Q0WATSGoAAAyxNR9tng611.jpg
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

        public static class ServicesBean implements Parcelable {
            public static final Creator<ServicesBean> CREATOR = new Creator<ServicesBean>() {
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
             * id : 240
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
            public static final Creator<SpecificationsBean> CREATOR = new Creator<SpecificationsBean>() {
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
             * attributes : [{"id":36,"selected":false,"value":"1kg"}]
             * id : 1
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
                 * id : 36
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