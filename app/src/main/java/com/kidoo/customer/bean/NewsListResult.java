package com.kidoo.customer.bean;

import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-12-11
 * Time: 18:44
 * Company: zx
 * Description:
 * FIXME
 */


public class NewsListResult extends News{

    private List<NewsBean> newsList;

    public List<NewsBean> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsBean> newsList) {
        this.newsList = newsList;
    }
}
