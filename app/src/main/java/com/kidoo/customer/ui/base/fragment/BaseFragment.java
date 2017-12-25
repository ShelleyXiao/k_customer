package com.kidoo.customer.ui.base.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kidoo.customer.GlideApp;
import com.kidoo.customer.GlideRequests;
import com.kidoo.customer.component.ActivityLifeCycleEvent;
import com.kidoo.customer.utils.DialogHelper;
import com.kidoo.customer.utils.ImageLoader;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import me.yokeyword.fragmentation.SupportFragment;

/** 
 * description: Fragmetn基础类
 * autour: ShaudXiao
 * date: 2017/9/16  
 * update: 2017/9/16
 * version: 
*/

public abstract class BaseFragment extends SupportFragment {

    protected Context mContext;
    protected View mRoot;
    protected Bundle mBundle;
    private Unbinder mUnbinder;
    protected LayoutInflater mInflater;

  //  private RequestManager mImgLoader;
    private GlideRequests mGlideRequests;

    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();

    private boolean isFirst = true;

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;

    private Dialog mWaitDialog;

    //setUserVisibleHint(boolean isVisibleToUser)方法会多次回调,而且可能会在onCreateView()方法执行完毕之前回调
    // 如果isVisibleToUser==true,然后进行数据加载和控件数据填充,但是onCreateView()方法并未执行完毕,
    // 此时就会出现NullPointerException空指针异常. 所以给onCreateView 添加标记位
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isUIVisible = isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        initBundle(mBundle);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if( mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if(null != parent) {
                parent.removeView(mRoot);
            }
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            mInflater = inflater;
            // Do somethings
            onBindViewBefore(mRoot);
            //Bind View
            mUnbinder = ButterKnife.bind(this, mRoot);

            if(savedInstanceState != null) {
                onRestartInstance(savedInstanceState);
            }

            isViewCreated =true;

            // 初始化子部件
            initWidget(mRoot);

            initData();

            initEventAndData();
        }

        return mRoot;
    }

    protected void onBindViewBefore(View root) {
        // ...
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initEventAndData();
        isFirst = false;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mUnbinder != null) {
            mUnbinder.unbind();
        }
        mBundle = null;

        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected  abstract  int getLayoutId();

    protected  void initBundle(Bundle bundle) {

    }

    protected  void initWidget(View root) {

    }

    protected void initData() {

    }

    protected  void initEventAndData(){

    }

    protected CompositeDisposable mCompositeDisposable;

    protected void dispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }


    protected <T extends View> T findView(int viewId) {
        return (T) mRoot.findViewById(viewId);
    }

    protected <T extends Serializable> T getBundleSerializable(String key) {
        if (mBundle == null) {
            return null;
        }

        return (T) mBundle.getSerializable(key);
    }

    protected void setText(int viewId, String text) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            return;
        }
        textView.setText(text);
    }

    protected void setText(int viewId, String text, String emptyTip) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            textView.setText(emptyTip);
            return;
        }
        textView.setText(text);
    }

    protected void setTextEmptyGone(int viewId, String text) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
            return;
        }
        textView.setText(text);
    }

    protected <T extends View> T setGone(int id) {
        T view = findView(id);
        view.setVisibility(View.GONE);
        return view;
    }

    protected <T extends View> T setVisibility(int id) {
        T view = findView(id);
        view.setVisibility(View.VISIBLE);
        return view;
    }

    protected void setInVisibility(int id) {
        findView(id).setVisibility(View.INVISIBLE);
    }

    protected void onRestartInstance(Bundle bundle) {

    }

    /**
     * 获取一个图片加载管理器
     *
     * @return RequestManager
     */
    public synchronized GlideRequests getImgLoader() {
        if (mGlideRequests == null)
            mGlideRequests = GlideApp.with(this);

        return mGlideRequests;
    }

    /***
     * 从网络中加载数据
     *
     * @param viewId   view的id
     * @param imageUrl 图片地址
     */
    protected void setImageFromNet(int viewId, String imageUrl) {
        setImageFromNet(viewId, imageUrl, 0);
    }

    /***
     * 从网络中加载数据
     *
     * @param viewId      view的id
     * @param imageUrl    图片地址
     * @param placeholder 图片地址为空时的资源
     */
    protected void setImageFromNet(int viewId, String imageUrl, int placeholder) {
        ImageView imageView = findView(viewId);
        setImageFromNet(imageView, imageUrl, placeholder);
    }

    /***
     * 从网络中加载数据
     *
     * @param imageView imageView
     * @param imageUrl  图片地址
     */
    protected void setImageFromNet(ImageView imageView, String imageUrl) {
        setImageFromNet(imageView, imageUrl, 0);
    }

    /***
     * 从网络中加载数据
     *
     * @param imageView   imageView
     * @param imageUrl    图片地址
     * @param placeholder 图片地址为空时的资源
     */
    protected void setImageFromNet(ImageView imageView, String imageUrl, int placeholder) {
        ImageLoader.loadImage(getImgLoader(), imageView, imageUrl, placeholder);
    }

    protected void showLoadingDialog(String message) {
        if (mWaitDialog == null) {
            mWaitDialog = DialogHelper.getLoadingDialog(getActivity());
        }

        mWaitDialog.show();
    }

    protected void dismissLoadingDialog() {
        if (mWaitDialog == null) return;
        mWaitDialog.dismiss();
    }
}
