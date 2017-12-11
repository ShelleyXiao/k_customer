package com.kidoo.customer.bean;

import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-12-11
 * Time: 18:43
 * Company: zx
 * Description:
 * FIXME
 */


public class NewBananersResult extends News{

    private List<BannerBean> bannerList;

    public List<BannerBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerBean> bannerList) {
        this.bannerList = bannerList;
    }
}
