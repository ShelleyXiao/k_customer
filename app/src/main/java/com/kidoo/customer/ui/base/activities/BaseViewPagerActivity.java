package com.kidoo.customer.ui.base.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.kidoo.customer.R;
import com.kidoo.customer.widget.tablayout.CommonTabLayout;
import com.kidoo.customer.widget.tablayout.CustomTabEntity;
import com.kidoo.customer.widget.tablayout.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * User: ShaudXiao
 * Date: 2017-10-13
 * Time: 10:32
 * Company: zx
 * Description:
 * FIXME
 */


public abstract class BaseViewPagerActivity extends BackActivity {

    @BindView(R.id.tab_nav)
    protected CommonTabLayout mTabNav;

    @BindView(R.id.base_viewPager)
    protected ViewPager mBaseViewPager;


    @Override
    protected int getContentView() {
        return R.layout.activity_base_viewpager;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        BaseViewPagerAdapter adapter = new BaseViewPagerAdapter(getSupportFragmentManager(), getPagers());
        mBaseViewPager.setAdapter(adapter);
//        mTabNav.setupWithViewPager(mBaseViewPager);
        mTabNav.setTabData(getTabEntity());
        mBaseViewPager.setCurrentItem(0, true);

        mTabNav.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mBaseViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mBaseViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabNav.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected abstract PagerInfo[] getPagers();

    protected abstract ArrayList<CustomTabEntity> getTabEntity();

    public static class PagerInfo {
        private String title;
        private Class<?> clx;
        private Bundle args;

        public PagerInfo(String title, Class<?> clx, Bundle args) {
            this.title = title;
            this.clx = clx;
            this.args = args;
        }
    }

    public class TabEntity implements CustomTabEntity {
        public String title;
        public int selectedIcon;
        public int unSelectedIcon;

        public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
            this.title = title;
            this.selectedIcon = selectedIcon;
            this.unSelectedIcon = unSelectedIcon;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return selectedIcon;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unSelectedIcon;
        }
    }

    public class BaseViewPagerAdapter extends FragmentPagerAdapter {
        private PagerInfo[] mInfoList;
        private Fragment mCurFragment;

        public BaseViewPagerAdapter(FragmentManager fm, PagerInfo[] infoList) {
            super(fm);
            mInfoList = infoList;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if (object instanceof Fragment) {
                mCurFragment = (Fragment) object;
            }
        }

        public Fragment getCurFragment() {
            return mCurFragment;
        }

        @Override
        public Fragment getItem(int position) {
            PagerInfo info = mInfoList[position];
            return Fragment.instantiate(BaseViewPagerActivity.this, info.clx.getName(), info.args);
        }

        @Override
        public int getCount() {
            return mInfoList.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mInfoList[position].title;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

}
