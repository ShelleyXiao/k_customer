package com.kidoo.customer.widget.recylerview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;


public class LoadMoreFooterView extends AppCompatTextView implements SwipeTrigger, SwipeLoadMoreTrigger {
    public LoadMoreFooterView(Context context) {
        super(context);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onLoadMore() {
        setText("加载更多...... ");
    }

    @Override
    public void onPrepare() {
        setText("");
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (yScrolled <= -getHeight()) {
                setText("松开立即刷新......");
            } else {
                setText("下拉可以刷新......");
            }
        } else {
            setText("");
        }
    }

    @Override
    public void onRelease() {
        setText("正在加载更多.......");
    }

    @Override
    public void onComplete() {
        setText("加载完成......");
    }

    @Override
    public void onReset() {
        setText("");
    }
}