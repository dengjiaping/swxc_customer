package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/11/8
 */

public class FamilyNumberBean {

    /**
     * incomingRestriction : false
     * sosDialCycleTimes : true
     * sosPhoneDtos : [{"dialFlag":true,"id":39,"name":"徐静","phone":"徐静","seqid":1},{"dialFlag":false,"id":40,"name":"高桥凉介","phone":"高桥凉介","seqid":2},{"dialFlag":true,"id":41,"name":"越前龙马","phone":"越前龙马","seqid":3}]
     */

    private boolean incomingRestriction;
    private boolean sosDialCycleTimes;
    private List<SosPhoneDtosBean> sosPhoneDtos;

    public boolean isIncomingRestriction() {
        return incomingRestriction;
    }

    public void setIncomingRestriction(boolean incomingRestriction) {
        this.incomingRestriction = incomingRestriction;
    }

    public boolean isSosDialCycleTimes() {
        return sosDialCycleTimes;
    }

    public void setSosDialCycleTimes(boolean sosDialCycleTimes) {
        this.sosDialCycleTimes = sosDialCycleTimes;
    }

    public List<SosPhoneDtosBean> getSosPhoneDtos() {
        return sosPhoneDtos;
    }

    public void setSosPhoneDtos(List<SosPhoneDtosBean> sosPhoneDtos) {
        this.sosPhoneDtos = sosPhoneDtos;
    }

    public static class SosPhoneDtosBean implements Parcelable {
        public static final Parcelable.Creator<SosPhoneDtosBean> CREATOR = new Parcelable.Creator<SosPhoneDtosBean>() {
            @Override
            public SosPhoneDtosBean createFromParcel(Parcel source) {
                return new SosPhoneDtosBean(source);
            }

            @Override
            public SosPhoneDtosBean[] newArray(int size) {
                return new SosPhoneDtosBean[size];
            }
        };
        /**
         * dialFlag : true
         * id : 39
         * name : 徐静
         * phone : 徐静
         * seqid : 1
         */

        private boolean dialFlag;
        private int id;
        private String name;
        private String phone;
        private int seqid;

        public SosPhoneDtosBean() {
        }

        protected SosPhoneDtosBean(Parcel in) {
            this.dialFlag = in.readByte() != 0;
            this.id = in.readInt();
            this.name = in.readString();
            this.phone = in.readString();
            this.seqid = in.readInt();
        }

        public boolean isDialFlag() {
            return dialFlag;
        }

        public void setDialFlag(boolean dialFlag) {
            this.dialFlag = dialFlag;
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

        public int getSeqid() {
            return seqid;
        }

        public void setSeqid(int seqid) {
            this.seqid = seqid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte(this.dialFlag ? (byte) 1 : (byte) 0);
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.phone);
            dest.writeInt(this.seqid);
        }
    }
}
