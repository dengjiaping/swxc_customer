package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */

public class Keyword implements Parcelable {


    /**
     * guide : 猕猴桃
     * hotList : ["猕猴桃","火龙果","甘肃苹果","芒果","16g内存条","NVIDIA TITAN"]
     */

    private String guide;
    private List<String> hotList;

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public List<String> getHotList() {
        return hotList;
    }

    public void setHotList(List<String> hotList) {
        this.hotList = hotList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.guide);
        dest.writeStringList(this.hotList);
    }

    public Keyword() {
    }

    protected Keyword(Parcel in) {
        this.guide = in.readString();
        this.hotList = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Keyword> CREATOR = new Parcelable.Creator<Keyword>() {
        @Override
        public Keyword createFromParcel(Parcel source) {
            return new Keyword(source);
        }

        @Override
        public Keyword[] newArray(int size) {
            return new Keyword[size];
        }
    };
}
