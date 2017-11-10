package com.shiwaixiangcun.customer.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/10/11
 */

public class Site {

    /**
     * defaultShow : true
     * name : 天鹅堡森林公园
     */


    private boolean defaultShow;
    private String name;
    private int id;
    /**
     * bindingCompany : null
     * branchDefaultIcon : null
     * companyId : null
     * createTime : null
     * createdBy : null
     * deleted : false
     * expand : false
     * icon : null
     * lastModifiedBy : null
     * lastModifiedDate : null
     * leaf : true
     * leafDefaultIcon : null
     * new : false
     * orgPath : null
     * parentId : 19
     * parentIdPath : [1,19,20]
     * parentIds : 0/1/19/
     * root : false
     * rootDefaultIcon : null
     * separator : /
     * siteLevel : SITE
     * status : ENABLE
     * version : null
     * weight : 1
     */

    private String bindingCompany;
    private String branchDefaultIcon;
    private String companyId;
    private String createTime;
    private String createdBy;
    private boolean deleted;
    private boolean expand;
    private String icon;
    private String lastModifiedBy;
    private String lastModifiedDate;
    private boolean leaf;
    private String leafDefaultIcon;
    @SerializedName("new")
    private boolean newX;
    private String orgPath;
    private int parentId;
    private String parentIds;
    private boolean root;
    private String rootDefaultIcon;
    private String separator;
    private String siteLevel;
    private String status;
    private String version;
    private int weight;
    private List<Integer> parentIdPath;

    public Site(boolean defaultShow, String name) {
        this.defaultShow = defaultShow;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDefaultShow() {
        return defaultShow;
    }

    public void setDefaultShow(boolean defaultShow) {
        this.defaultShow = defaultShow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBindingCompany() {
        return bindingCompany;
    }

    public void setBindingCompany(String bindingCompany) {
        this.bindingCompany = bindingCompany;
    }

    public String getBranchDefaultIcon() {
        return branchDefaultIcon;
    }

    public void setBranchDefaultIcon(String branchDefaultIcon) {
        this.branchDefaultIcon = branchDefaultIcon;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getLeafDefaultIcon() {
        return leafDefaultIcon;
    }

    public void setLeafDefaultIcon(String leafDefaultIcon) {
        this.leafDefaultIcon = leafDefaultIcon;
    }

    public boolean isNewX() {
        return newX;
    }

    public void setNewX(boolean newX) {
        this.newX = newX;
    }

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public String getRootDefaultIcon() {
        return rootDefaultIcon;
    }

    public void setRootDefaultIcon(String rootDefaultIcon) {
        this.rootDefaultIcon = rootDefaultIcon;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getSiteLevel() {
        return siteLevel;
    }

    public void setSiteLevel(String siteLevel) {
        this.siteLevel = siteLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Integer> getParentIdPath() {
        return parentIdPath;
    }

    public void setParentIdPath(List<Integer> parentIdPath) {
        this.parentIdPath = parentIdPath;
    }
}
