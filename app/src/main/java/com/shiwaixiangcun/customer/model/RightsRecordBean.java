package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public class RightsRecordBean {

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

    public static class ElementsBean implements Parcelable {
        public static final Parcelable.Creator<ElementsBean> CREATOR = new Parcelable.Creator<ElementsBean>() {
            @Override
            public ElementsBean createFromParcel(Parcel source) {
                return new ElementsBean(source);
            }

            @Override
            public ElementsBean[] newArray(int size) {
                return new ElementsBean[size];
            }
        };
        /**
         * images : 1
         * content : 测试投诉维权
         * id : 665
         * number : 00091
         * status : UNTREATED
         * time : 1506066130000
         */

        private int images;
        private String content;
        private int id;
        private String number;
        private String status;
        private long time;

        public ElementsBean() {
        }

        protected ElementsBean(Parcel in) {
            this.images = in.readInt();
            this.content = in.readString();
            this.id = in.readInt();
            this.number = in.readString();
            this.status = in.readString();
            this.time = in.readLong();
        }

        public int getImages() {
            return images;
        }

        public void setImages(int images) {
            this.images = images;
        }

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.images);
            dest.writeString(this.content);
            dest.writeInt(this.id);
            dest.writeString(this.number);
            dest.writeString(this.status);
            dest.writeLong(this.time);
        }
    }

}
