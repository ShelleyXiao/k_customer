package com.kidoo.customer.media.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-09-25
 * Time: 18:58
 * Company: zx
 * Description:选择图片时配置参数
 * FIXME
 */


public final class SelectOption {

    private boolean isCrop;
    private int mCropWidth, mCropHeight;
    private Callback mCallback;
    private boolean hasCam;
    private int mSelectCount;
    private List<String> mSelectedImages;

    private SelectOption() {

    }

    public boolean isCrop() {
        return isCrop;
    }

    public int getCropWidth() {
        return mCropWidth;
    }

    public int getCropHeight() {
        return mCropHeight;
    }

    public Callback getCallback() {
        return mCallback;
    }

    public boolean isHasCam() {
        return hasCam;
    }

    public int getSelectCount() {
        return mSelectCount;
    }

    public List<String> getSelectedImages() {
        return mSelectedImages;
    }

    public interface Callback {
        void doSelected(String[] images);
    }


    /*构建器*/
    public static class Builder {
        private boolean isCrop;
        private int cropWidth, cropHeight;
        private Callback callback;
        private boolean hasCam;
        private int selectCount;
        private List<String> selectedImages;

        public Builder() {
            selectCount = 1;
            hasCam = true;
            selectedImages = new ArrayList<>();
        }

        public Builder setCrop(int cropWidth, int cropHeight) {
            if (cropWidth <= 0 || cropHeight <= 0)
                throw new IllegalArgumentException("cropWidth or cropHeight mast be greater than 0 ");
            this.isCrop = true;
            this.cropWidth = cropWidth;
            this.cropHeight = cropHeight;
            return this;
        }

        public Builder setCallback(Callback callback) {
            this.callback = callback;
            return this;
        }

        public Builder setHasCam(boolean hasCam) {
            this.hasCam = hasCam;
            return this;
        }

        public Builder setSelectCount(int selectCount) {
            if (selectCount <= 0)
                throw new IllegalArgumentException("selectCount mast be greater than 0 ");
            this.selectCount = selectCount;
            return this;
        }

        public Builder setSelectedImages(List<String> selectedImages) {
            if (selectedImages == null || selectedImages.size() == 0) return this;
            this.selectedImages.addAll(selectedImages);
            return this;
        }

        public Builder setSelectedImages(String[] selectedImages) {
            if (selectedImages == null || selectedImages.length == 0) return this;
            if (this.selectedImages == null) this.selectedImages = new ArrayList<>();
            this.selectedImages.addAll(Arrays.asList(selectedImages));
            return this;
        }

        public SelectOption build() {
            SelectOption options = new SelectOption();
            options.hasCam = hasCam;
            options.isCrop = isCrop;
            options.mCropHeight = cropHeight;
            options.mCropWidth = cropWidth;
            options.mCallback = callback;
            options.mSelectCount = selectCount;
            options.mSelectedImages = selectedImages;
            return options;
        }
    }

}
