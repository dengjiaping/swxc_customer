package com.shiwaixiangcun.customer.utils;


import java.util.Collection;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/23.
 */
public class Utils {


    /**
     * 判断对象是否为NotEmpty(!null或元素>0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    public static boolean isNotEmpty(Object pObj) {
        if (pObj == null)
            return false;
        if (pObj instanceof String) {
            if (((String) ((String) pObj).trim()).length() == 0) {
                return false;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return false;
            }
        } else if (pObj instanceof Map) {
             if (((Map) pObj).size() == 0) {
                return false;
            }
        }
        return true;
    }


}
