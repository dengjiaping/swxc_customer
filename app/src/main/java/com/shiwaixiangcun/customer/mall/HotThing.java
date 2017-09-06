package com.shiwaixiangcun.customer.mall;

/**
 * Created by Administrator on 2017/9/6.
 */

public class HotThing {

    public int productid;
    public String ProductName;
    public String PromotionInfo;
    public String PromotionInfoPrice;
    public String Picture;
    public String picture_square;
    public String OriginStoreName;
    public String OriginStoreColor;
    public String CreateTime;
    public String LastModifyTime;
    public String ExpiredTime;
    public String BuyUrl;
    public String QuanUrl;
    public String MixCouponUrl;
    public String QuanInfo;
    public String DiscountRate;
    public String RealPrice;
    public String SalesVolume;
    public boolean IsGoUland;
    public String HalfUlandUrl;
    public Object From;
    public String ItemPath;
    public String malltoken;

    public HotThing(String productName, String promotionInfoPrice, String picture) {
        ProductName = productName;
        PromotionInfoPrice = promotionInfoPrice;
        Picture = picture;
    }
}
