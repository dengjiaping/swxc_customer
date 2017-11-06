package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/27.
 */

public class ImageReturnbean implements Serializable {

    /**
     * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/14/rBKx51ko7FqAIth-AAm3OxPBYkY437.jpg
     * fileId : 1243
     * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/14/rBKx51ko7FqAIth-AAm3OxPBYkY437_150x150.jpg
     */

    private String accessUrl;
    private int fileId;
    private String thumbImageURL;

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getThumbImageURL() {
        return thumbImageURL;
    }

    public void setThumbImageURL(String thumbImageURL) {
        this.thumbImageURL = thumbImageURL;
    }
}
