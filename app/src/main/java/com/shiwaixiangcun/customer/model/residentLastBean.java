package com.shiwaixiangcun.customer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/1.
 */

public class residentLastBean implements Serializable{

    /**
     * data : [{"id":1,"number":"404"},{"id":2,"number":"403"},{"id":3,"number":"402"},{"id":4,"number":"401"},{"id":5,"number":"308"},{"id":6,"number":"307"},{"id":7,"number":"306"},{"id":8,"number":"305"},{"id":9,"number":"304"},{"id":10,"number":"303"},{"id":11,"number":"302"},{"id":12,"number":"301"},{"id":13,"number":"208"},{"id":14,"number":"207"},{"id":15,"number":"206"},{"id":16,"number":"205"},{"id":17,"number":"B204"},{"id":18,"number":"204"},{"id":19,"number":"B203"},{"id":20,"number":"203"},{"id":21,"number":"B202"},{"id":22,"number":"202"},{"id":23,"number":"B201"},{"id":24,"number":"201"},{"id":25,"number":"108"},{"id":26,"number":"107"},{"id":27,"number":"106"},{"id":28,"number":"105"},{"id":29,"number":"B104"},{"id":30,"number":"104"},{"id":31,"number":"B103"},{"id":32,"number":"103"},{"id":33,"number":"B102"},{"id":34,"number":"102"},{"id":35,"number":"B101"},{"id":36,"number":"101"},{"id":2011,"number":"B506"},{"id":2012,"number":"B601"},{"id":2013,"number":"505"},{"id":2015,"number":"555"},{"id":2016,"number":"909"},{"id":2018,"number":"YY"},{"id":2019,"number":"707"},{"id":2020,"number":"501"},{"id":2021,"number":"808"},{"id":2022,"number":"676"},{"id":2023,"number":"10"},{"id":2024,"number":"111"},{"id":2025,"number":"109"},{"id":2026,"number":"10001"},{"id":2027,"number":"123456"},{"id":2028,"number":"123"},{"id":2029,"number":"1234567"},{"id":2030,"number":"12345678"},{"id":2031,"number":"112"},{"id":2032,"number":"520"},{"id":2033,"number":"4324342"},{"id":2034,"number":"111111"},{"id":2035,"number":"4134124"},{"id":2036,"number":"YYaaa"},{"id":2037,"number":"502"},{"id":2038,"number":"YYB"},{"id":2040,"number":"111111111111"},{"id":2041,"number":"112131"},{"id":2042,"number":"11111111111"},{"id":2043,"number":"432432443242"},{"id":2044,"number":"3202"},{"id":2045,"number":"3333334"},{"id":2046,"number":"3201"},{"id":2047,"number":"333"},{"id":2048,"number":"yy2"},{"id":2050,"number":"1001"},{"id":2051,"number":"1002"},{"id":2053,"number":"22"}]
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
         * id : 1
         * number : 404
         */

        private int id;
        private String numberDesc;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNumberDesc() {
            return numberDesc;
        }

        public void setNumberDesc(String number) {
            this.numberDesc = number;
        }
    }
}
package com.shiwaixiangcun.customer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/1.
 */

public class residentLastBean implements Serializable{

    /**
     * data : [{"id":1,"number":"404"},{"id":2,"number":"403"},{"id":3,"number":"402"},{"id":4,"number":"401"},{"id":5,"number":"308"},{"id":6,"number":"307"},{"id":7,"number":"306"},{"id":8,"number":"305"},{"id":9,"number":"304"},{"id":10,"number":"303"},{"id":11,"number":"302"},{"id":12,"number":"301"},{"id":13,"number":"208"},{"id":14,"number":"207"},{"id":15,"number":"206"},{"id":16,"number":"205"},{"id":17,"number":"B204"},{"id":18,"number":"204"},{"id":19,"number":"B203"},{"id":20,"number":"203"},{"id":21,"number":"B202"},{"id":22,"number":"202"},{"id":23,"number":"B201"},{"id":24,"number":"201"},{"id":25,"number":"108"},{"id":26,"number":"107"},{"id":27,"number":"106"},{"id":28,"number":"105"},{"id":29,"number":"B104"},{"id":30,"number":"104"},{"id":31,"number":"B103"},{"id":32,"number":"103"},{"id":33,"number":"B102"},{"id":34,"number":"102"},{"id":35,"number":"B101"},{"id":36,"number":"101"},{"id":2011,"number":"B506"},{"id":2012,"number":"B601"},{"id":2013,"number":"505"},{"id":2015,"number":"555"},{"id":2016,"number":"909"},{"id":2018,"number":"YY"},{"id":2019,"number":"707"},{"id":2020,"number":"501"},{"id":2021,"number":"808"},{"id":2022,"number":"676"},{"id":2023,"number":"10"},{"id":2024,"number":"111"},{"id":2025,"number":"109"},{"id":2026,"number":"10001"},{"id":2027,"number":"123456"},{"id":2028,"number":"123"},{"id":2029,"number":"1234567"},{"id":2030,"number":"12345678"},{"id":2031,"number":"112"},{"id":2032,"number":"520"},{"id":2033,"number":"4324342"},{"id":2034,"number":"111111"},{"id":2035,"number":"4134124"},{"id":2036,"number":"YYaaa"},{"id":2037,"number":"502"},{"id":2038,"number":"YYB"},{"id":2040,"number":"111111111111"},{"id":2041,"number":"112131"},{"id":2042,"number":"11111111111"},{"id":2043,"number":"432432443242"},{"id":2044,"number":"3202"},{"id":2045,"number":"3333334"},{"id":2046,"number":"3201"},{"id":2047,"number":"333"},{"id":2048,"number":"yy2"},{"id":2050,"number":"1001"},{"id":2051,"number":"1002"},{"id":2053,"number":"22"}]
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
         * id : 1
         * number : 404
         */

        private int id;
        private String number;

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
    }
}
