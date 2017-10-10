package com.kidoo.customer.bean;

/**
 * User: ShaudXiao
 * Date: 2017-10-10
 * Time: 16:01
 * Company: zx
 * Description:
 * FIXME
 */


public class PageInfo {

    private int totalRecord;
    private int pageSize;
    private int pageNo;
    private int totalPage;

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "totalRecord=" + totalRecord +
                ", pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", totalPage=" + totalPage +
                '}';
    }
}
