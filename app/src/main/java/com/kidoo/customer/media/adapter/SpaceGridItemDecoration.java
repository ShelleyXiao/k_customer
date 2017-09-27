package com.kidoo.customer.media.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * User: ShaudXiao
 * Date: 2017-09-26
 * Time: 10:28
 * Company: zx
 * Description:
 * FIXME
 */

public class SpaceGridItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceGridItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = space;
    }
}
