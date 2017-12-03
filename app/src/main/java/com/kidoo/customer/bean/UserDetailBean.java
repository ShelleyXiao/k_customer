package com.kidoo.customer.bean;

import java.io.Serializable;
import java.util.List;



public class UserDetailBean implements Serializable{

    private List<MedalBean> medalList;
    private Customer customer;

    public List<MedalBean> getMedalList() {
        return medalList;
    }

    public void setMedalList(List<MedalBean> medalList) {
        this.medalList = medalList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "UserDetailBean{" +
                "medalList=" + medalList +
                ", customer=" + customer +
                '}';
    }
}
