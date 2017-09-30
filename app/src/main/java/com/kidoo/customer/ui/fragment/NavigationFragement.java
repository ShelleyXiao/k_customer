package com.kidoo.customer.ui.fragment;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.kidoo.customer.R;
import com.kidoo.customer.ui.base.fragment.BaseFragment;
import com.kidoo.customer.widget.BorderShaper;
import com.kidoo.customer.widget.NavButtomButton;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * description: 底部导航栏主类
 * autour: ShaudXiao
 * date: 2017/9/17
 * update: 2017/9/17
 * version:
 */
public class NavigationFragement extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.nav_item_broadcast)
    NavButtomButton mBroadcast;
    @BindView(R.id.nav_item_message)
    NavButtomButton mMessage;
    @BindView(R.id.nav_item_explore)
    NavButtomButton mNavExplore;
    @BindView(R.id.nav_item_me)
    NavButtomButton mNavMe;


    private Context mContext;
    private int mContainerId;
    private FragmentManager mFragmentManager;
    private NavButtomButton mCurrentNavButton;
    private OnNavigationReselectListener mOnNavigationReselectListener;


    public NavigationFragement() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_main_nav;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        ShapeDrawable lineDrawable = new ShapeDrawable(new BorderShaper(new RectF(0, 1, 0, 0)));
        lineDrawable.getPaint().setColor(getResources().getColor(R.color.list_divider_color));
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{
                new ColorDrawable(getResources().getColor(R.color.white)),
                lineDrawable
        });
        root.setBackgroundDrawable(layerDrawable);

        mBroadcast.init(R.drawable.tab_icon_gb,
                R.string.main_tab_name_broadcast,
                BroadcastTabFragment.class);

        mMessage.init(R.drawable.tab_icon_message,
                R.string.main_tab_name_message,
                MeeageTabFragment.class);

        mNavExplore.init(R.drawable.tab_icon_fx,
                R.string.main_tab_name_explore,
                ExporeTabFragment.class);

        mNavMe.init(R.drawable.tab_icon_me,
                R.string.main_tab_name_me,
                MeeageTabFragment.class);


    }

    @OnClick({R.id.nav_item_broadcast, R.id.nav_item_message,
            R.id.nav_item_explore, R.id.nav_item_me})

    @Override
    public void onClick(View v) {
        if (v instanceof NavButtomButton) {
            NavButtomButton nav = (NavButtomButton) v;
            doSelect(nav);
        }
    }

    public void setup(Context context, FragmentManager fragmentManager, int contentId, OnNavigationReselectListener listener) {
        mContext = context;
        mFragmentManager = fragmentManager;
        mContainerId = contentId;
        mOnNavigationReselectListener = listener;

        // do clear
        clearOldFragment();
        // do select first
        doSelect(mBroadcast);
    }

    public void select(int index) {
        if (mNavMe != null)
            doSelect(mNavMe);
    }

    @SuppressWarnings("RestrictedApi")
    private void clearOldFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (transaction == null || fragments == null || fragments.size() == 0)
            return;
        boolean doCommit = false;
        for (Fragment fragment : fragments) {
            if (fragment != this && fragment != null) {
                transaction.remove(fragment);
                doCommit = true;
            }
        }
        if (doCommit)
            transaction.commitNow();
    }

    private void doSelect(NavButtomButton newNavButton) {
        // If the new navigation is me info fragment, we intercept it
        /*
        if (newNavButton == mNavMe) {
            if (interceptMessageSkip())
                return;
        }
        */

        NavButtomButton oldNavButton = null;
        if (mCurrentNavButton != null) {
            oldNavButton = mCurrentNavButton;
            if (oldNavButton == newNavButton) {
                onReselect(oldNavButton);
                return;
            }
            oldNavButton.setSelected(false);
        }
        newNavButton.setSelected(true);
        doTabChanged(oldNavButton, newNavButton);
        mCurrentNavButton = newNavButton;
    }

    private void doTabChanged(NavButtomButton oldNavButton, NavButtomButton newNavButton) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (oldNavButton != null) {
            if (oldNavButton.getFragment() != null) {
                ft.detach(oldNavButton.getFragment());
            }
        }
        if (newNavButton != null) {
            if (newNavButton.getFragment() == null) {
                Fragment fragment = Fragment.instantiate(mContext,
                        newNavButton.getCls().getName(), null);
                ft.add(mContainerId, fragment, newNavButton.getTag());
                newNavButton.setFragment(fragment);
            } else {
                ft.attach(newNavButton.getFragment());
            }
        }
        ft.commit();
    }



    private void onReselect(NavButtomButton navigationButton) {
        OnNavigationReselectListener listener = mOnNavigationReselectListener;
        if (listener != null) {
            listener.onReselect(navigationButton);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    public interface OnNavigationReselectListener {
        void onReselect(NavButtomButton navigationButton);
    }
}
