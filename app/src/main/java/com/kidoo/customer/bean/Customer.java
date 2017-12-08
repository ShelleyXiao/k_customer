package com.kidoo.customer.bean;

/**
 * User: ShaudXiao
 * Date: 2017-10-10
 * Time: 18:03
 * Company: zx
 * Description:
 * FIXME
 */


public class Customer {
    private int id;
    private String mobile;
    private String customId;
    private String payId;
    private int paySupplier;
    private String imId;
    private String imPassword;
    private String imSupplier;
    private String nickName;
    private String portrait;
    private int sex;
    private String email;
    private long birthday;
    private String sign;
    private int status;
    private int applyType;
    private String password;
    private long createTime;
    private long updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public int getPaySupplier() {
        return paySupplier;
    }

    public void setPaySupplier(int paySupplier) {
        this.paySupplier = paySupplier;
    }

    public String getImId() {
        return imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
    }

    public String getImPassword() {
        return imPassword;
    }

    public void setImPassword(String imPassword) {
        this.imPassword = imPassword;
    }

    public String getImSupplier() {
        return imSupplier;
    }

    public void setImSupplier(String imSupplier) {
        this.imSupplier = imSupplier;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getApplyType() {
        return applyType;
    }

    public void setApplyType(int applyType) {
        this.applyType = applyType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", customId='" + customId + '\'' +
                ", payId='" + payId + '\'' +
                ", paySupplier=" + paySupplier +
                ", imId='" + imId + '\'' +
                ", imPassword='" + imPassword + '\'' +
                ", imSupplier='" + imSupplier + '\'' +
                ", nickName='" + nickName + '\'' +
                ", portrait='" + portrait + '\'' +
                ", sex=" + sex +
                ", email=" + email +
                ", birthday=" + birthday +
                ", sign='" + sign + '\'' +
                ", status=" + status +
                ", applyType=" + applyType +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
