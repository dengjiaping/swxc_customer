package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/9/25.
 */

public class ToolBean implements Parcelable {

    public static final Parcelable.Creator<ToolBean> CREATOR = new Parcelable.Creator<ToolBean>() {
        @Override
        public ToolBean createFromParcel(Parcel source) {
            return new ToolBean(source);
        }

        @Override
        public ToolBean[] newArray(int size) {
            return new ToolBean[size];
        }
    };
    public int id;
    public String name;
    public int pic;

    public ToolBean(int id, String name, int pic) {
        this.id = id;
        this.name = name;
        this.pic = pic;
    }

    public ToolBean() {
    }


    protected ToolBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.pic = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.pic);
    }
}
