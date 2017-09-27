package com.kidoo.customer.media.bean;

/**
 * User: ShaudXiao
 * Date: 2017-09-26
 * Time: 10:31
 * Company: zx
 * Description:
 * FIXME
 */


public class Image  {
    private int id;
    private String name;
    private String path;
    private String thumbpath;
    private boolean isSelect;
    private String folderName;
    private long date;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbpath() {
        return thumbpath;
    }

    public void setThumbpath(String thumbpath) {
        this.thumbpath = thumbpath;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return " " + thumbpath +
                ":" + path;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Image) {
            return this.path.equals(((Image) obj).getPath());
        }
        return false;
    }
}
