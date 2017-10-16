package com.kidoo.customer.ui.activity;

import com.kidoo.customer.R;
import com.kidoo.customer.ui.base.activities.BaseViewPagerActivity;
import com.kidoo.customer.ui.fragment.MyBroadcastFragment;
import com.kidoo.customer.ui.fragment.MyGameFragment;
import com.kidoo.customer.widget.tablayout.CustomTabEntity;

import java.util.ArrayList;

/**
 * User: ShaudXiao
 * Date: 2017-10-13
 * Time: 10:47
 * Company: zx
 * Description:
 * FIXME
 */


public class MyBroadcastDetailActivity extends BaseViewPagerActivity {


    @Override
    protected PagerInfo[] getPagers() {
        return new PagerInfo[] {
                new PagerInfo(getString(R.string.my_broadcast_detail_broadcast_title), MyBroadcastFragment.class, null),
                new PagerInfo(getString(R.string.my_broadcast_detail_game_title), MyGameFragment.class, null),

        };
    }

    @Override
    protected ArrayList<CustomTabEntity> getTabEntity() {
        ArrayList<CustomTabEntity> customTabEntities = new ArrayList<>();
        customTabEntities.add(new TabEntity(getString(R.string.my_broadcast_detail_broadcast_title), R.drawable.tab_icon_me, R.drawable.tab_icon_me));
        customTabEntities.add(new TabEntity(getString(R.string.my_broadcast_detail_game_title), R.drawable.tab_icon_me, R.drawable.tab_icon_me));
        return customTabEntities;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.my_broadcast_detail_toolbar_title);
    }
}
