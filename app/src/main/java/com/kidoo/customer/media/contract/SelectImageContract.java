package com.kidoo.customer.media.contract;


/**
 * User: ShaudXiao
 * Date: 2017-09-25
 * Time: 19:08
 * Company: zx
 * Description:图片选择器建立契约关系，将权限操作放在Activity，具体数据放在Fragment (mvp clean 分层)
 * FIXME
 */
public interface SelectImageContract {
    interface Operator {
        void requestCamera();

        void requestExternalStorage();

        void onBack();

        void setDataView(View view);
    }

    interface View {

        void onOpenCameraSuccess();

        void onCameraPermissionDenied();
    }
}
