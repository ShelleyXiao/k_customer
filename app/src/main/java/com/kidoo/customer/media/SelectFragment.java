package com.kidoo.customer.media;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.kidoo.customer.R;
import com.kidoo.customer.media.adapter.ImageAdapter;
import com.kidoo.customer.media.adapter.ImageFolderAdapter;
import com.kidoo.customer.media.adapter.SpaceGridItemDecoration;
import com.kidoo.customer.media.bean.Image;
import com.kidoo.customer.media.bean.ImageFolder;
import com.kidoo.customer.media.config.ImageLoaderListener;
import com.kidoo.customer.media.config.SelectOption;
import com.kidoo.customer.media.contract.SelectImageContract;
import com.kidoo.customer.media.crop.CropActivity;
import com.kidoo.customer.ui.base.adapter.BaseRecyclerAdapter;
import com.kidoo.customer.ui.base.fragment.BaseFragment;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.utils.TDevice;
import com.kidoo.customer.widget.EmptyLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * User: ShaudXiao
 * Date: 2017-09-25
 * Time: 19:17
 * Company: zx
 * Description:
 * FIXME
 */


public class SelectFragment extends BaseFragment implements SelectImageContract.View, View.OnClickListener,
        ImageLoaderListener, BaseRecyclerAdapter.OnItemClickListener  {

    @BindView(R.id.rv_image)
    RecyclerView mContentView;
    @BindView(R.id.btn_title_select)
    Button mSelectFolderView;
    @BindView(R.id.iv_title_select)
    ImageView mSelectFolderIcon;
    @BindView(R.id.toolbar)
    View mToolbar;
    @BindView(R.id.btn_done)
    Button mDoneView;
    @BindView(R.id.btn_preview)
    Button mPreviewView;

    @BindView(R.id.error_layout)
    EmptyLayout mErrorLayout;

    private static SelectOption mOption;
    private SelectImageContract.Operator mOperator;

    private ImageFolderPopupWindow mFolderPopupWindow;
    private ImageFolderAdapter mImageFolderAdapter;
    private ImageAdapter mImageAdapter;

    private String mCamImageName;
    private List<Image> mSelectedImage;

    private LoaderListener mCursorLoader = new LoaderListener();

    public static SelectFragment newInstance(SelectOption options) {
        mOption = options;
        return new SelectFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_image;
    }

    @Override
    public void onAttach(Context context) {
        this.mOperator = (SelectImageContract.Operator)context;
        this.mOperator.setDataView(this);
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        mOption = null;
        super.onDestroy();
    }

    @Override
    protected void initWidget(View view) {
        if (mOption == null) {
            getActivity().finish();
            return;
        }
        mContentView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mContentView.addItemDecoration(new SpaceGridItemDecoration((int) TDevice.dipToPx(getResources(), 1)));
        mImageAdapter = new ImageAdapter(getContext(), this);
        mImageAdapter.setSingleSelect(mOption.getSelectCount() <= 1);
        mRoot.findViewById(R.id.lay_button).setVisibility(mOption.getSelectCount() == 1 ? View.GONE : View.VISIBLE);
        mImageFolderAdapter = new ImageFolderAdapter(getActivity());
        mImageFolderAdapter.setLoaer(this);
        mContentView.setAdapter(mImageAdapter);
        mContentView.setItemAnimator(null);
        mImageAdapter.setOnItemClickListener(this);
        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                getLoaderManager().initLoader(0, null, mCursorLoader);
            }
        });
    }

    @Override
    protected void initData() {
        if(mOption == null){
            getActivity().finish();
            return;
        }
        mSelectedImage = new ArrayList<>();

        if (mOption.getSelectCount() > 1 && mOption.getSelectedImages() != null) {
            List<String> images = mOption.getSelectedImages();
            for (String s : images) {
                // checkShare file exists
                if (s != null && new File(s).exists()) {
                    Image image = new Image();
                    image.setSelect(true);
                    image.setPath(s);
                    mSelectedImage.add(image);
                }
            }
        }
        getLoaderManager().initLoader(0, null, mCursorLoader);
    }



    @OnClick({R.id.btn_preview, R.id.icon_back, R.id.btn_title_select, R.id.btn_done})
    @Override
    public void onClick(View v) {

       switch (v.getId()) {
           case R.id.icon_back:
               mOperator.onBack();
               break;
           case R.id.btn_preview:
               LogUtils.w("onclick size = " + mSelectedImage.size());
               if (mSelectedImage.size() > 0) {
                   ImageGalleryActivity.show(getActivity(), Util.toArray(mSelectedImage), 0, false);
               }
               break;
           case R.id.btn_title_select:
               showPopuFolderList();
               break;
           case R.id.btn_done:
               onSelectComplete();
               break;
       }
    }

    /**
     * 拍照完成通知系统添加照片
     *
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppCompatActivity.RESULT_OK) {
            switch (requestCode) {
                case 0x03:
                    if (mCamImageName == null) return;
                    Uri localUri = Uri.fromFile(new File(Util.getCameraPath() + mCamImageName));
                    Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
                    getActivity().sendBroadcast(localIntent);
                    break;
                case 0x04:
                    if (data == null) return;
                    mOption.getCallback().doSelected(new String[]{data.getStringExtra("crop_path")});
                    getActivity().finish();
                    break;
            }
        }
    }

    @Override
    public void displayImage(ImageView iv, String path) {
        getImgLoader().asBitmap().load(path)
                .centerCrop()
                .error(R.drawable.ic_split_graph)
                .into(iv);
    }

    @Override
    public void onOpenCameraSuccess() {
        goToOpenCamera();
    }

    @Override
    public void onCameraPermissionDenied() {

    }

    @Override
    public void onItemClick(int position, long itemId) {

//        LogUtils.w("******w***** 0");

        if (mOption.isHasCam()) {
            if (position != 0) {
                handleSelectChange(position);
            } else {
                if (mSelectedImage.size() < mOption.getSelectCount()) {
                    mOperator.requestCamera();
                } else {
                    Toast.makeText(getActivity(), "最多只能选择 " + mOption.getSelectCount() + " 张图片", Toast.LENGTH_SHORT).show();
                }
            }
        } else {

            handleSelectChange(position);
        }
    }

    private void showPopuFolderList() {
        if(null == mFolderPopupWindow) {
            ImageFolderPopupWindow popupWindow = new ImageFolderPopupWindow(getActivity(), new ImageFolderPopupWindow.Callback() {
                @Override
                public void onSelect(ImageFolderPopupWindow popupWindow, ImageFolder folder) {
                    addImageToAdapter(folder.getImages());
                    mContentView.scrollToPosition(0);
                    popupWindow.dismiss();
                    mSelectFolderView.setText(folder.getName());
                }

                @Override
                public void onDismiss() {
                    mSelectFolderIcon.setImageResource(R.drawable.ic_arrow_bottom);
                }

                @Override
                public void onShow() {
                    mSelectFolderIcon.setImageResource(R.drawable.ic_arrow_top);
                }
            });
            popupWindow.setAdapter(mImageFolderAdapter);
            mFolderPopupWindow = popupWindow;
        }
        mFolderPopupWindow.showAsDropDown(mToolbar);
    }

    private void onSelectComplete() {
        handleResult();
    }

    private void addImageToAdapter(ArrayList<Image> images) {
        mImageAdapter.clear();
        if(mOption.isHasCam()) {
            Image cam = new Image();
            mImageAdapter.addItem(cam);
        }
        mImageAdapter.addAll(images);
    }

    private void handleResult() {

        if(mSelectedImage.size() != 0) {
//            LogUtils.e("*********** 1" + mOption.isCrop());
            if(mOption.isCrop()) {
                List<String> selectedImage = mOption.getSelectedImages();
                selectedImage.clear();
                selectedImage.add(mSelectedImage.get(0).getPath());
                mSelectedImage.clear();
                // goto crop
                CropActivity.show(this, mOption);
            } else {
                mOption.getCallback().doSelected(Util.toArray(mSelectedImage));
                getActivity().finish();
            }
        }
    }
    private void handleSelectSizeChange(int selectedSize) {
        if(selectedSize > 0) {
            mPreviewView.setEnabled(true);
            mDoneView.setEnabled(true);
            mDoneView.setText(String.format("%s(%s)", getString(R.string.media_image_selected_done), selectedSize));
        } else {
            mPreviewView.setEnabled(false);
            mDoneView.setEnabled(false);
            mDoneView.setText(R.string.media_image_selected_done);
        }
    }

    private void handleSelectChange(int position) {
        Image image = mImageAdapter.getItem(position);
        if(image == null)
            return;

        //如果是多选模式
        final int selectCount = mOption.getSelectCount();
        if (selectCount > 1) {
            if (image.isSelect()) {
                image.setSelect(false);
                mSelectedImage.remove(image);
                mImageAdapter.updateItem(position);
            } else {
                if (mSelectedImage.size() == selectCount) {
                    Toast.makeText(getActivity(), getString(R.string.media_image_selected_count_hint)
                            + selectCount
                            + getString(R.string.media_image_selected_count_hint_1),
                            Toast.LENGTH_SHORT).show();
                } else {
                    image.setSelect(true);
                    mSelectedImage.add(image);
                    mImageAdapter.updateItem(position);
                }
            }
            handleSelectSizeChange(mSelectedImage.size());
        } else {
            mSelectedImage.add(image);
            handleResult();
        }
    }

    private void goToOpenCamera() {
        // 判断是否挂载了SD卡
        mCamImageName = null;
        String savePath = "";
        if (Util.hasSDCard()) {
            savePath = Util.getCameraPath();
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
        }

        // 没有挂载SD卡，无法保存文件
        if (TextUtils.isEmpty(savePath)) {
            Toast.makeText(getActivity(), getString(R.string.media_image_store_image_capacity) , Toast.LENGTH_LONG).show();
            return;
        }

        mCamImageName = Util.getSaveImageFullName();
        File out = new File(savePath, mCamImageName);

        /**
         * android N 系统适配
         */
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(getContext(), "net.oschina.app.provider", out);
        } else {
            uri = Uri.fromFile(out);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent,
                0x03);
    }


    private class LoaderListener implements LoaderManager.LoaderCallbacks<Cursor> {

        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.MINI_THUMB_MAGIC,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if(id == 0) {
                //数据库光标加载器
                return new CursorLoader(getContext(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        null, null, IMAGE_PROJECTION[2] + " DESC"
                        );
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if(null != data) {
                final ArrayList<Image> images = new ArrayList<>();
                final ArrayList<ImageFolder> imageFolders = new ArrayList<>();

                final ImageFolder defaultFolder = new ImageFolder();
                defaultFolder.setName(getString(R.string.media_image_menu_aullof));
                defaultFolder.setPath("");
                imageFolders.add(defaultFolder);

                int count = data.getCount();
                if(count > 0) {
                    data.moveToFirst();
                    do {
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                        int id = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[3]));
                        String thumbPath = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[4]));
                        String bucket = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[5]));

                        Image image = new Image();
                        image.setPath(path);
                        image.setName(name);
                        image.setDate(dateTime);
                        image.setId(id);
                        image.setThumbpath(thumbPath);
                        image.setFolderName(bucket);

                        images.add(image);

                        //如果是新拍的照片
                        if (mCamImageName != null && mCamImageName.equals(image.getName())) {
                            image.setSelect(true);
                            mSelectedImage.add(image);
                        }

                        //如果是被选中的图片
                        if (mSelectedImage.size() > 0) {
                            for (Image i : mSelectedImage) {
                                if (i.getPath().equals(image.getPath())) {
                                    image.setSelect(true);
                                }
                            }
                        }

                        File imageFile = new File(path);
                        File folderFile = imageFile.getParentFile();
                        ImageFolder folder = new ImageFolder();
                        folder.setName(folderFile.getName());
                        folder.setPath(folderFile.getAbsolutePath());
                        if (!imageFolders.contains(folder)) {
                            folder.getImages().add(image);
                            folder.setAlbumPath(image.getPath());//默认相册封面
                            imageFolders.add(folder);
                        } else {
                            // 更新
                            ImageFolder f = imageFolders.get(imageFolders.indexOf(folder));
                            f.getImages().add(image);
                        }


                    } while (data.moveToNext());
                }

                addImageToAdapter(images);
                defaultFolder.getImages().addAll(images);
                if(mOption.isHasCam()) {
                    defaultFolder.setAlbumPath(images.size() > 1 ? images.get(1).getPath() : null);
                } else {
                    defaultFolder.setAlbumPath(images.size() > 1 ? images.get(0).getPath() : null);
                }

                mImageFolderAdapter.resetItem(imageFolders);

                if(mSelectedImage.size() > 0) {
                    List<Image> rs = new ArrayList<>();
                    for(Image image : mSelectedImage) {
                        File f = new File(image.getPath());
                        if(!f.exists()) {
                            rs.add(image);
                        }
                    }
                    mSelectedImage.removeAll(rs);
                }

                if (mOption.getSelectCount() == 1 && mCamImageName != null) {
                    handleResult();
                }
                handleSelectSizeChange(mSelectedImage.size());
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }


}
