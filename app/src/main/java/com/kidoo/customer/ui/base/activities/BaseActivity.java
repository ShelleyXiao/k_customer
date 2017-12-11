package com.kidoo.customer.ui.base.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.kidoo.customer.GlideApp;
import com.kidoo.customer.GlideRequests;
import com.kidoo.customer.component.ActivityLifeCycleEvent;
import com.kidoo.customer.mvp.view.BaseView;
import com.kidoo.customer.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * User: ShaudXiao
 * Date: 2017-09-12
 * Time: 19:11
 * Company: zx
 * Description:
 * FIXME
 */


public abstract class BaseActivity extends SupportActivity implements BaseView {

    protected GlideRequests mGlideRequests;
    private boolean mIsDestroy;
    private Fragment mFragment;
    private Unbinder mUnbinder;

    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();

//    protected Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(initBundle(getIntent().getExtras())) {

            setContentView(getContentView());
            initWindow();

            mUnbinder = ButterKnife.bind(this);

            initWidget();
            initData();

            initEventAndData();

        } else {
            LogUtils.w("INIT BUNDLE FALSE , FINISH THIS");
            finish();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mIsDestroy = true;
        super.onDestroy();
        mUnbinder.unbind();
        dispose();

        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
    }

    protected void addFragment(int frameLayoutId, Fragment fragment) {
        if (null != fragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(fragment.isAdded()) {
                if(null != mFragment) {
                    ft.hide(mFragment);
                } else {
                    ft.show(fragment);
                }
            } else {
                if(null != mFragment) {
                    ft.hide(mFragment).add(frameLayoutId, fragment);
                } else {
                    ft.add(frameLayoutId, fragment);
                }
            }
            mFragment = fragment;
            ft.commit();
        }
    }

    protected void replaceFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayoutId, fragment);
            transaction.commit();
        }
    }

    public boolean isDestroy() {
        return mIsDestroy;
    }

    protected abstract int getContentView();

    protected boolean initBundle(Bundle bundle) {
        return true;
    }

    protected void initWindow() {
    }

    protected void initWidget() {
    }

    protected void initData() {
    }

    protected  void initEventAndData(){

    }

    public synchronized GlideRequests getImageLoader() {
        if (mGlideRequests == null)
            mGlideRequests = GlideApp.with(this);
        return mGlideRequests;
    }

//    /**
//     * showToast
//     *
//     * @param text text
//     */
//    @SuppressLint("InflateParams")
//    private void showToast(String text) {
//        Toast toast = this.mToast;
//        if (toast == null) {
//            toast = initToast();
//        }
//        View rootView = LayoutInflater.from(this).inflate(R.layout.view_toast, null, false);
//        TextView textView = (TextView) rootView.findViewById(R.id.title_tv);
//        textView.setText(text);
//        toast.setView(rootView);
//        initToastGravity(toast);
//        toast.show();
//    }
//
//    /**
//     * showToast
//     *
//     * @param id id
//     */
//    @SuppressLint("InflateParams")
//    private void showToast(@StringRes int id) {
//        Toast toast = this.mToast;
//        if (toast == null) {
//            toast = initToast();
//        }
//        View rootView = LayoutInflater.from(this).inflate(R.layout.view_toast, null, false);
//        TextView textView = (TextView) rootView.findViewById(R.id.title_tv);
//        textView.setText(id);
//        toast.setView(rootView);
//        initToastGravity(toast);
//        toast.show();
//    }
//
//    @NonNull
//    private Toast initToast() {
//        Toast toast;
//        toast = new Toast(this);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        this.mToast = toast;
//        return toast;
//    }
//
//    private void initToastGravity(Toast toast) {
//        boolean isCenter = true;
//        if (isCenter) {
//            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
//        } else {
//            toast.setGravity(Gravity.BOTTOM, 0, getResources().getDimensionPixelSize(R.dimen.toast_y_offset));
//        }
//    }
//
//
//    protected void showToastForKeyBord(@StringRes int id) {
//        showToast(id);
//    }
//
//    protected void showToastForKeyBord(String message) {
//        showToast(message);
//    }


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

}
