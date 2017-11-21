package com.kidoo.customer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.kidoo.customer.R;
import com.kidoo.customer.bean.Broadcast;
import com.kidoo.customer.ui.base.activities.BackActivity;
import com.kidoo.customer.ui.fragment.GroupBroadcastDetilaFragment;
import com.kidoo.customer.utils.LogUtils;

/**
 * description: 广播详情
 * autour: ShaudXiao
 * date: 2017/10/22
 * update: 2017/10/22
 * version:
 */

public class BroadcastDetailActivity extends BackActivity {

    public static final String BUNDLE_BROADCAST_KEY = "broadcast";
    private Broadcast mBroadcast;


    public static void show(Context context, Broadcast broadcast) {
        Intent intent = new Intent(context, BroadcastDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_BROADCAST_KEY, broadcast);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    public static void show(Context context, long broadcastId, int broadcastType, int playerType) {
        Intent intent = new Intent(context, BroadcastDetailActivity.class);
        Bundle bundle = new Bundle();

        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    public String getToolbarTitle() {
        return getString(R.string.broadcast_detial_toolbar_title);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_broadcast_detail;
    }

    @Override
    public boolean initBundle(Bundle bundle) {
        mBroadcast = (Broadcast) bundle.getSerializable(BUNDLE_BROADCAST_KEY);
        return true;
    }

    @Override
    public void initWindow() {
        super.initWindow();

//        View view = mToolBar.getRootView();
//        ImageView imageMenu = (ImageView) view.findViewById(R.id.toolbar_menu);
//        imageMenu.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_chat_menu_bg));
//        imageMenu.setVisibility(View.VISIBLE);
//        imageMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public void initWidget() {
        super.initWidget();
        LogUtils.w("mBroadcast" + mBroadcast);
        if(mBroadcast != null) {
            Fragment detailFragment = GroupBroadcastDetilaFragment.instantiate(this,
                    Integer.valueOf(mBroadcast.getId()).intValue());
            addFragment(R.id.lay_container, detailFragment);
        }
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_broadcast_detail_chat, menu);
        MenuItem item = menu.findItem(R.id.menu_broadcast_group_chat);
        if (item != null) {
            View action = item.getActionView();

        }
        return true;
    }
}
