package com.shiwaixiangcun.customer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/1.
 */

public class ResidentBean implements Serializable {

    /**
     * data : [{"children":[{"children":[{"children":[{"children":[{"children":null,"id":133,"name":"1单元"}],"id":133,"name":"1单元"}],"id":6,"name":"4栋"}],"id":2,"name":"A区"}],"id":1,"name":"天鹅堡"}]
     * message : 操作成功
     * responseCode : 1001
     * success : true
     */

    private String message;
    private int responseCode;
    private boolean success;
    private List<DataBean> data;

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

    public static class DataBean {
        /**
         * children : [{"children":[{"children":[{"children":[{"children":null,"id":133,"name":"1单元"}],"id":133,"name":"1单元"}],"id":6,"name":"4栋"}],"id":2,"name":"A区"}]
         * id : 1
         * name : 天鹅堡
         */

        private int id;
        private String name;
        private List<ChildrenBeanXXX> children;

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

        public List<ChildrenBeanXXX> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBeanXXX> children) {
            this.children = children;
        }

        public static class ChildrenBeanXXX {
            /**
             * children : [{"children":[{"children":[{"children":null,"id":133,"name":"1单元"}],"id":133,"name":"1单元"}],"id":6,"name":"4栋"}]
             * id : 2
             * name : A区
             */

            private int id;
            private String name;
            private List<ChildrenBeanXX> children;

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

            public List<ChildrenBeanXX> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBeanXX> children) {
                this.children = children;
            }

            public static class ChildrenBeanXX {
                /**
                 * children : [{"children":[{"children":null,"id":133,"name":"1单元"}],"id":133,"name":"1单元"}]
                 * id : 6
                 * name : 4栋
                 */

                private int id;
                private String name;
                private List<ChildrenBeanX> children;

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

                public List<ChildrenBeanX> getChildren() {
                    return children;
                }

                public void setChildren(List<ChildrenBeanX> children) {
                    this.children = children;
                }

                public static class ChildrenBeanX {
                    /**
                     * children : [{"children":null,"id":133,"name":"1单元"}]
                     * id : 133
                     * name : 1单元
                     */

                    private int id;
                    private String name;
                    private List<ChildrenBean> children;

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

                    public List<ChildrenBean> getChildren() {
                        return children;
                    }

                    public void setChildren(List<ChildrenBean> children) {
                        this.children = children;
                    }

                    public static class ChildrenBean {
                        /**
                         * children : null
                         * id : 133
                         * name : 1单元
                         */

                        private Object children;
                        private int id;
                        private String name;

                        public Object getChildren() {
                            return children;
                        }

                        public void setChildren(Object children) {
                            this.children = children;
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
                    }
                }
            }
        }
    }
}
