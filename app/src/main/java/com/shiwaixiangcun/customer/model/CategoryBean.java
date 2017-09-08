package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
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
     * data : [{"categoryImg":null,"children":[{"categoryImg":null,"children":[{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/52/rBKx51mt_TeAGefEAAFDd3_-mrw369.jpg","children":[],"id":4,"name":"苹果","parentId":3,"parentIds":null,"weight":null},{"categoryImg":null,"children":[],"id":5,"name":"芒果","parentId":3,"parentIds":null,"weight":null}],"id":3,"name":"水果1","parentId":1,"parentIds":null,"weight":null}],"id":2,"name":"水果蔬菜","parentId":1,"parentIds":null,"weight":null},{"categoryImg":null,"children":[{"categoryImg":null,"children":[{"categoryImg":null,"children":[],"id":11,"name":"牛仔裤","parentId":10,"parentIds":null,"weight":null}],"id":10,"name":"男装","parentId":1,"parentIds":null,"weight":null}],"id":6,"name":"服饰","parentId":1,"parentIds":null,"weight":null},{"categoryImg":null,"children":[{"categoryImg":null,"children":[{"categoryImg":null,"children":[],"id":22,"name":"三级分类02","parentId":14,"parentIds":null,"weight":null}],"id":14,"name":"二级分类---三级级分类01不展示","parentId":1,"parentIds":null,"weight":null}],"id":13,"name":"一级分类01","parentId":1,"parentIds":null,"weight":null}]
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
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
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
        dest.writeList(this.data);
    }

    public static class DataBean implements Parcelable {
        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
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
         * children : [{"categoryImg":null,"children":[{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/52/rBKx51mt_TeAGefEAAFDd3_-mrw369.jpg","children":[],"id":4,"name":"苹果","parentId":3,"parentIds":null,"weight":null},{"categoryImg":null,"children":[],"id":5,"name":"芒果","parentId":3,"parentIds":null,"weight":null}],"id":3,"name":"水果1","parentId":1,"parentIds":null,"weight":null}]
         * id : 2
         * name : 水果蔬菜
         * parentId : 1
         * parentIds : null
         * weight : null
         */

        private Object categoryImg;
        private int id;
        private String name;
        private int parentId;
        private Object parentIds;
        private Object weight;
        private List<ChildrenBeanX> children;

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.categoryImg = in.readParcelable(Object.class.getClassLoader());
            this.id = in.readInt();
            this.name = in.readString();
            this.parentId = in.readInt();
            this.parentIds = in.readParcelable(Object.class.getClassLoader());
            this.weight = in.readParcelable(Object.class.getClassLoader());
            this.children = new ArrayList<ChildrenBeanX>();
            in.readList(this.children, ChildrenBeanX.class.getClassLoader());
        }

        public Object getCategoryImg() {
            return categoryImg;
        }

        public void setCategoryImg(Object categoryImg) {
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

        public Object getParentIds() {
            return parentIds;
        }

        public void setParentIds(Object parentIds) {
            this.parentIds = parentIds;
        }

        public Object getWeight() {
            return weight;
        }

        public void setWeight(Object weight) {
            this.weight = weight;
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
            dest.writeParcelable((Parcelable) this.categoryImg, flags);
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeInt(this.parentId);
            dest.writeParcelable((Parcelable) this.parentIds, flags);
            dest.writeParcelable((Parcelable) this.weight, flags);
            dest.writeList(this.children);
        }

        public static class ChildrenBeanX {
            /**
             * categoryImg : null
             * children : [{"categoryImg":"http://resource.shiwaixiangcun.cn/group1/M00/00/52/rBKx51mt_TeAGefEAAFDd3_-mrw369.jpg","children":[],"id":4,"name":"苹果","parentId":3,"parentIds":null,"weight":null},{"categoryImg":null,"children":[],"id":5,"name":"芒果","parentId":3,"parentIds":null,"weight":null}]
             * id : 3
             * name : 水果1
             * parentId : 1
             * parentIds : null
             * weight : null
             */

            private Object categoryImg;
            private int id;
            private String name;
            private int parentId;
            private Object parentIds;
            private Object weight;
            private List<ChildrenBean> children;

            public Object getCategoryImg() {
                return categoryImg;
            }

            public void setCategoryImg(Object categoryImg) {
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

            public Object getParentIds() {
                return parentIds;
            }

            public void setParentIds(Object parentIds) {
                this.parentIds = parentIds;
            }

            public Object getWeight() {
                return weight;
            }

            public void setWeight(Object weight) {
                this.weight = weight;
            }

            public List<ChildrenBean> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBean> children) {
                this.children = children;
            }

            public static class ChildrenBean {
                /**
                 * categoryImg : http://resource.shiwaixiangcun.cn/group1/M00/00/52/rBKx51mt_TeAGefEAAFDd3_-mrw369.jpg
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
                private Object parentIds;
                private Object weight;
                private List<?> children;

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

                public Object getParentIds() {
                    return parentIds;
                }

                public void setParentIds(Object parentIds) {
                    this.parentIds = parentIds;
                }

                public Object getWeight() {
                    return weight;
                }

                public void setWeight(Object weight) {
                    this.weight = weight;
                }

                public List<?> getChildren() {
                    return children;
                }

                public void setChildren(List<?> children) {
                    this.children = children;
                }
            }
        }
    }
}
