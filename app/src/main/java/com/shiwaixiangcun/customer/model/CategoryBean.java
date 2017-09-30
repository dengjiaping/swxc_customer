package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/9/8.
 */

public class CategoryBean implements Parcelable {

    public static final Parcelable.Creator<CategoryBean> CREATOR = new Parcelable.Creator<CategoryBean>() {
        @Override
        public CategoryBean createFromParcel(Parcel source) {
            return new CategoryBean(source);
        }

        @Override
        public CategoryBean[] newArray(int size) {
            return new CategoryBean[size];
        }
    };
    /**
     * data : [{"categoryImg":null,"children":[{"categoryImg":null,"children":[{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4mESAYAgdAACEzv7RgGY596.jpg","children":[],"id":4,"name":"苹果","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4mH6AFlpsAABuEnz2GV0535.jpg","children":[],"id":5,"name":"芒果","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4mAKAU9vIAACzkL_KqTk797.jpg","children":[],"id":28,"name":"火龙果","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4nhSAWj6aAACORu8Vp3Q436.jpg","children":[],"id":29,"name":"柠檬","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4nc-Abc2nAABG_saWNdY413.jpg","children":[],"id":30,"name":"猕猴桃","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4nGyAHYdFAACNuWooJpg387.jpg","children":[],"id":31,"name":"哈密瓜","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4nPeAK70NAABQ0pyQDwU702.jpg","children":[],"id":32,"name":"橘子","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/5D/rBKx51nA4QCAPXuGAAA3mAvIr7s500.jpg","children":[],"id":33,"name":"梨","parentId":3,"parentIds":null,"weight":null}],"id":3,"name":"国内水果","parentId":1,"parentIds":null,"weight":null}],"id":2,"name":"水果蔬菜","parentId":1,"parentIds":null,"weight":null},{"categoryImg":null,"children":[{"categoryImg":null,"children":[{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/5B/rBKx51m_QmiAcaLTAAJ3FdnCnwU992.jpg","children":[],"id":36,"name":"智能手表","parentId":35,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/5B/rBKx51m_QoGADZObAAI2njlltaA681.jpg","children":[],"id":42,"name":"智能配饰","parentId":35,"parentIds":null,"weight":null}],"id":35,"name":"智能设备","parentId":1,"parentIds":null,"weight":null}],"id":34,"name":"健康","parentId":1,"parentIds":null,"weight":null},{"categoryImg":null,"children":[{"categoryImg":null,"children":[{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/5C/rBKx51m_ZomACJTKAAJD6xu2-ag572.jpg","children":[],"id":45,"name":"门锁","parentId":44,"parentIds":null,"weight":null}],"id":44,"name":"智能门锁","parentId":1,"parentIds":null,"weight":null}],"id":43,"name":"智能居家","parentId":1,"parentIds":null,"weight":null},{"categoryImg":null,"children":[{"categoryImg":null,"children":[{"categoryImg":null,"children":[],"id":48,"name":"智能手表","parentId":47,"parentIds":null,"weight":null},{"categoryImg":null,"children":[],"id":49,"name":"智能配饰","parentId":47,"parentIds":null,"weight":null}],"id":47,"name":"健康设备","parentId":1,"parentIds":null,"weight":null}],"id":46,"name":"绿色健康","parentId":1,"parentIds":null,"weight":null},{"categoryImg":null,"children":[{"categoryImg":null,"children":[{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/5E/rBKx51nId62ADDEyAAAyxNR9tng766.jpg","children":[],"id":53,"name":"零食","parentId":51,"parentIds":null,"weight":null},{"categoryImg":null,"children":[],"id":54,"name":"石斛","parentId":51,"parentIds":null,"weight":null},{"categoryImg":null,"children":[],"id":55,"name":"蜂蜜","parentId":51,"parentIds":null,"weight":null},{"categoryImg":null,"children":[],"id":56,"name":"竹荪","parentId":51,"parentIds":null,"weight":null}],"id":51,"name":"特色食品","parentId":1,"parentIds":null,"weight":null},{"categoryImg":null,"children":[{"categoryImg":null,"children":[],"id":57,"name":"遵义红茶","parentId":52,"parentIds":null,"weight":null},{"categoryImg":null,"children":[],"id":58,"name":"湄潭翠芽","parentId":52,"parentIds":null,"weight":null}],"id":52,"name":"茶水饮料","parentId":1,"parentIds":null,"weight":null}],"id":50,"name":"地方特产","parentId":1,"parentIds":null,"weight":null},{"categoryImg":null,"children":[{"categoryImg":null,"children":[{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/5E/rBKx51nIeFmAPReUAAA4ZxYaUzw743.jpg","children":[],"id":63,"name":"三级","parentId":62,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/5E/rBKx51nIeGWAetyGAAAZvkrF0nQ744.jpg","children":[],"id":64,"name":"三级2","parentId":62,"parentIds":null,"weight":null}],"id":62,"name":"二级","parentId":1,"parentIds":null,"weight":null}],"id":61,"name":"一级分类tes","parentId":1,"parentIds":null,"weight":null}]
     * message : 操作成功
     * responseCode : 1001
     * success : true
     */

    private String message;
    private int responseCode;
    private boolean success;
    private List<DataBean> data;

    public CategoryBean() {
    }

    protected CategoryBean(Parcel in) {
        this.message = in.readString();
        this.responseCode = in.readInt();
        this.success = in.readByte() != 0;
        this.data = in.createTypedArrayList(DataBean.CREATOR);
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeInt(this.responseCode);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.data);
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
         * categoryImg : null
         * children : [{"categoryImg":null,"children":[{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4mESAYAgdAACEzv7RgGY596.jpg","children":[],"id":4,"name":"苹果","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4mH6AFlpsAABuEnz2GV0535.jpg","children":[],"id":5,"name":"芒果","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4mAKAU9vIAACzkL_KqTk797.jpg","children":[],"id":28,"name":"火龙果","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4nhSAWj6aAACORu8Vp3Q436.jpg","children":[],"id":29,"name":"柠檬","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4nc-Abc2nAABG_saWNdY413.jpg","children":[],"id":30,"name":"猕猴桃","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4nGyAHYdFAACNuWooJpg387.jpg","children":[],"id":31,"name":"哈密瓜","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4nPeAK70NAABQ0pyQDwU702.jpg","children":[],"id":32,"name":"橘子","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/5D/rBKx51nA4QCAPXuGAAA3mAvIr7s500.jpg","children":[],"id":33,"name":"梨","parentId":3,"parentIds":null,"weight":null}],"id":3,"name":"国内水果","parentId":1,"parentIds":null,"weight":null}]
         * id : 2
         * name : 水果蔬菜
         * parentId : 1
         * parentIds : null
         * weight : null
         */

        private String categoryImg;
        private int id;
        private String name;
        private int parentId;
        private int parentIds;
        private String weight;
        private List<ChildrenBeanX> children;

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.categoryImg = in.readString();
            this.id = in.readInt();
            this.name = in.readString();
            this.parentId = in.readInt();
            this.parentIds = in.readInt();
            this.weight = in.readString();
            this.children = in.createTypedArrayList(ChildrenBeanX.CREATOR);
        }

        public String getCategoryImg() {
            return categoryImg;
        }

        public void setCategoryImg(String categoryImg) {
            this.categoryImg = categoryImg;
        }

        public int getParentIds() {
            return parentIds;
        }

        public void setParentIds(int parentIds) {
            this.parentIds = parentIds;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
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

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public List<ChildrenBeanX> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBeanX> children) {
            this.children = children;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.categoryImg);
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeInt(this.parentId);
            dest.writeInt(this.parentIds);
            dest.writeString(this.weight);
            dest.writeTypedList(this.children);
        }

        public static class ChildrenBeanX implements Parcelable {
            public static final Parcelable.Creator<ChildrenBeanX> CREATOR = new Parcelable.Creator<ChildrenBeanX>() {
                @Override
                public ChildrenBeanX createFromParcel(Parcel source) {
                    return new ChildrenBeanX(source);
                }

                @Override
                public ChildrenBeanX[] newArray(int size) {
                    return new ChildrenBeanX[size];
                }
            };
            /**
             * categoryImg : null
             * children : [{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4mESAYAgdAACEzv7RgGY596.jpg","children":[],"id":4,"name":"苹果","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4mH6AFlpsAABuEnz2GV0535.jpg","children":[],"id":5,"name":"芒果","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4mAKAU9vIAACzkL_KqTk797.jpg","children":[],"id":28,"name":"火龙果","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4nhSAWj6aAACORu8Vp3Q436.jpg","children":[],"id":29,"name":"柠檬","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4nc-Abc2nAABG_saWNdY413.jpg","children":[],"id":30,"name":"猕猴桃","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4nGyAHYdFAACNuWooJpg387.jpg","children":[],"id":31,"name":"哈密瓜","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4nPeAK70NAABQ0pyQDwU702.jpg","children":[],"id":32,"name":"橘子","parentId":3,"parentIds":null,"weight":null},{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/5D/rBKx51nA4QCAPXuGAAA3mAvIr7s500.jpg","children":[],"id":33,"name":"梨","parentId":3,"parentIds":null,"weight":null}]
             * id : 3
             * name : 国内水果
             * parentId : 1
             * parentIds : null
             * weight : null
             */

            private String categoryImg;
            private int id;
            private String name;
            private int parentId;
            private int parentIds;
            private String weight;
            private List<ChildrenBean> children;

            public ChildrenBeanX() {
            }

            protected ChildrenBeanX(Parcel in) {
                this.categoryImg = in.readString();
                this.id = in.readInt();
                this.name = in.readString();
                this.parentId = in.readInt();
                this.parentIds = in.readInt();
                this.weight = in.readString();
                this.children = in.createTypedArrayList(ChildrenBean.CREATOR);
            }

            public String getCategoryImg() {
                return categoryImg;
            }

            public void setCategoryImg(String categoryImg) {
                this.categoryImg = categoryImg;
            }

            public int getParentIds() {
                return parentIds;
            }

            public void setParentIds(int parentIds) {
                this.parentIds = parentIds;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
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

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public List<ChildrenBean> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBean> children) {
                this.children = children;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.categoryImg);
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeInt(this.parentId);
                dest.writeInt(this.parentIds);
                dest.writeString(this.weight);
                dest.writeTypedList(this.children);
            }

            public static class ChildrenBean implements Parcelable {
                public static final Parcelable.Creator<ChildrenBean> CREATOR = new Parcelable.Creator<ChildrenBean>() {
                    @Override
                    public ChildrenBean createFromParcel(Parcel source) {
                        return new ChildrenBean(source);
                    }

                    @Override
                    public ChildrenBean[] newArray(int size) {
                        return new ChildrenBean[size];
                    }
                };
                /**
                 * categoryImg : http://resource.shiwaixiangcun.cn/group1/M00/00/57/rBKx51m4mESAYAgdAACEzv7RgGY596.jpg
                 * children : []
                 * id : 4
                 * name : 苹果
                 * parentId : 3
                 * parentIds : null
                 * weight : null
                 */


                private String categoryImg;
                private int id;
                private String name;
                private int parentId;
                private int parentIds;
                private String weight;
                private List<?> children;

                public ChildrenBean() {
                }

                protected ChildrenBean(Parcel in) {
                    this.categoryImg = in.readString();
                    this.id = in.readInt();
                    this.name = in.readString();
                    this.parentId = in.readInt();
                    this.parentIds = in.readInt();
                    this.weight = in.readString();
//                    this.children = new ArrayList<?>();
//                    in.readList(this.children, ?.class.getClassLoader());
                }

                public int getParentIds() {
                    return parentIds;
                }

                public void setParentIds(int parentIds) {
                    this.parentIds = parentIds;
                }

                public String getWeight() {
                    return weight;
                }

                public void setWeight(String weight) {
                    this.weight = weight;
                }

                public String getCategoryImg() {
                    return categoryImg;
                }

                public void setCategoryImg(String categoryImg) {
                    this.categoryImg = categoryImg;
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

                public int getParentId() {
                    return parentId;
                }

                public void setParentId(int parentId) {
                    this.parentId = parentId;
                }

                public List<?> getChildren() {
                    return children;
                }

                public void setChildren(List<?> children) {
                    this.children = children;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.categoryImg);
                    dest.writeInt(this.id);
                    dest.writeString(this.name);
                    dest.writeInt(this.parentId);
                    dest.writeInt(this.parentIds);
                    dest.writeString(this.weight);
                    dest.writeList(this.children);
                }
            }
        }
    }
}