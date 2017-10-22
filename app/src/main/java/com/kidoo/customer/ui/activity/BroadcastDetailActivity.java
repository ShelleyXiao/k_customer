package com.kidoo.customer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.kidoo.customer.R;
import com.kidoo.customer.ui.base.activities.BackActivity;
import com.kidoo.customer.ui.base.fragment.BaseBroadcastDetailFragment;

/**
 * description: 广播详情
 * autour: ShaudXiao
 * date: 2017/10/22
 * update: 2017/10/22
 * version:
 */

public class BroadcastDetailActivity extends BackActivity {


    protected BaseBroadcastDetailFragment mDetailFragment;


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

        mDetailFragment = getDetailFragment();
        addFragment(R.id.lay_container, mDetailFragment);

    }


    protected BaseBroadcastDetailFragment getDetailFragment() {
        return null;
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
