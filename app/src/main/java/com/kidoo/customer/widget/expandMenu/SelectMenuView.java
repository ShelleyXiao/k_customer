package com.kidoo.customer.widget.expandMenu;

import android.animation.LayoutTransition;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kidoo.customer.R;
import com.kidoo.customer.bean.ChannelA;
import com.kidoo.customer.bean.ChannelB;
import com.kidoo.customer.bean.ChannelC;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.expandMenu.holder.SelectHolder;
import com.kidoo.customer.widget.expandMenu.holder.SortHolder;
import com.kidoo.customer.widget.expandMenu.holder.SubjectChannelABHolder;
import com.kidoo.customer.widget.expandMenu.holder.SubjectChannelCHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 搜索菜单栏
 * autour: ShaudXiao
 * date: 2017/12/9
 * update: 2017/12/9
 * version:
 */
public class SelectMenuView extends LinearLayout {

    private static final int TAB_SUBJECT = 1;
    private static final int TAB_SORT = 2;
    private static final int TAB_SELECT = 3;

    private Context mContext;

    private View mSubjectView;
    private View mSortView;
    private View mSelectView;

    private View mRootView;

    private View mPopupWindowView;

    private RelativeLayout mMainContentLayout;
    private View mBackView;

    private View mDivderView;

    /**
     * ChannelA & B
     */
    private SubjectChannelABHolder mSubjectABHolder;

    private SubjectChannelCHolder mSubjectCHolder;

    /**
     *
     * 综合排序
     */
    private SortHolder mSortHolder;
    /**
     * 筛选
     */
    private SelectHolder mSelectHolder;

    private OnMenuSelectDataChangedListener mOnMenuSelectDataChangedListener;

    private RelativeLayout mContentLayout;

    private TextView mChannelBText;
    private ImageView mSubjectArrowImage;
    private TextView mChannelCText;
    private ImageView mSortArrowImage;
    private TextView mSelectText;
    private ImageView mSelectArrowImage;


    private List<ChannelA> mChannelDataList = new ArrayList<>();

    private List<ChannelC> mChannelCList = new ArrayList<>();

    private int mTabRecorder = -1;

    private int mChannelASelectIndex = 0;
    private int mChannelBSelectIndex = 0;
    private int mChannelCSelectIndex = 0;

    private Animation inAnimation;
    private Animation outAnimation;

    private LayoutTransition mTransitioner;

    public SelectMenuView(Context context) {
        super(context);
        this.mContext = context;
        this.mRootView = this;
        init();
    }

    public SelectMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mRootView = this;
        init();
    }

    public void setDataList(List<ChannelA> aDataList) {
        if (aDataList != null) {
            mChannelDataList.clear();
            mChannelDataList.addAll(aDataList);
            mSubjectABHolder.refreshData(mChannelDataList, 0, 0);

            mChannelBText.setText(mChannelDataList.get(mChannelASelectIndex)
                    .getChannelBList()
                    .get(mChannelBSelectIndex).getName());

            mChannelCList.addAll(aDataList.get(mChannelASelectIndex)
                    .getChannelBList()
                    .get(mChannelBSelectIndex)
                    .getChannelCList());

            mSubjectCHolder.refreshViewData(mChannelCList, mChannelCSelectIndex);
            mChannelCText.setText(mChannelCList.get(mChannelCSelectIndex).getName());
        }

    }

    private void init() {


        mSubjectABHolder = new SubjectChannelABHolder(mContext);
//        mSubjectABHolder.refreshData(mChannelDataList, 0, -1);
        mSubjectABHolder.setOnRightListViewItemSelectedListener(new SubjectChannelABHolder.OnRightListViewItemSelectedListener() {
            @Override
            public void OnRightListViewItemSelected(int leftIndex, int rightIndex, String text) {

                if (mOnMenuSelectDataChangedListener != null) {

                    mChannelASelectIndex = leftIndex;
                    mChannelBSelectIndex = rightIndex;

                    mOnMenuSelectDataChangedListener.onSubjectABChanged(leftIndex, rightIndex);
                    LogUtils.i("menu select " + text +
                    "leftIndex = " + leftIndex + "rightIndex =  " + rightIndex);
                }

                dismissPopupWindow();
                //Toast.makeText(UIUtils.getContext(), text, Toast.LENGTH_SHORT).show();
                mChannelBText.setText(text);
                List<ChannelB> channelBs = mChannelDataList.get(leftIndex).getChannelBList();
                List<ChannelC> channelCS = channelBs.get(rightIndex).getChannelCList();

                mChannelCList.clear();
                mChannelCList.addAll(channelCS);
                mSubjectCHolder.refreshViewData(mChannelCList, mChannelCSelectIndex);
                mChannelCText.setText(mChannelCList.get(mChannelCSelectIndex).getName());
            }
        });

        mSubjectCHolder = new SubjectChannelCHolder(mContext);
        mSubjectCHolder.setOnChannelCListViewItemSelectedListener(new SubjectChannelCHolder.OnChannelCListViewItemSelectedListener() {
            @Override
            public void OnListViewItemSelected(int index, String text) {
                if (mOnMenuSelectDataChangedListener != null) {

                    mOnMenuSelectDataChangedListener.onSubjectCChanged(index);
                    LogUtils.i("menu select " + text);
                }
                mChannelCSelectIndex = index;

                dismissPopupWindow();
                mChannelCText.setText(mChannelCList.get(index).getName());
            }
        });

        inAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in_anim);
        outAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_out_anim);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(mContext, R.layout.layout_search_menu, this);

        mChannelBText = (TextView) findViewById(R.id.subject);
        mSubjectArrowImage = (ImageView) findViewById(R.id.img_sub);

        mChannelCText = (TextView) findViewById(R.id.comprehensive_sorting);
        mSortArrowImage = (ImageView) findViewById(R.id.img_cs);

        mSelectText = (TextView) findViewById(R.id.tv_select);
        mSelectArrowImage = (ImageView) findViewById(R.id.img_sc);

        mDivderView = findViewById(R.id.divder_line);

        mContentLayout = (RelativeLayout) findViewById(R.id.rl_content);

        mPopupWindowView = View.inflate(mContext, R.layout.layout_search_menu_content, null);
        mMainContentLayout = (RelativeLayout) mPopupWindowView.findViewById(R.id.rl_main);
        //mBackView = mPopupWindowView.findViewById(R.id.ll_background);

        mSubjectView = findViewById(R.id.ll_subject);
        mSortView = findViewById(R.id.ll_sort);
        mSelectView = findViewById(R.id.ll_select);

        //点击 科目 弹出菜单
        mSubjectView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onViewClicked(mSubjectView);
                }
                handleClickSubjectView();
            }
        });
        //点击 综合排序 弹出菜单
        mSortView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onViewClicked(mSortView);
                }
                handleClickSortView();
            }
        });
        //点击 筛选 弹出菜单
        mSelectView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onViewClicked(mSelectView);
                }
                handleClickSelectView();
            }
        });

        mContentLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPopupWindow();
            }
        });

//        mTransitioner = new LayoutTransition();
//        mContentLayout.setLayoutTransition(mTransitioner);

//        setTransition();
    }

    private void handleClickSubjectView() {

        mMainContentLayout.removeAllViews();
        mMainContentLayout.addView(mSubjectABHolder.getRootView(), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        popUpWindow(TAB_SUBJECT);
    }

    private void handleClickSortView() {

        mMainContentLayout.removeAllViews();
        mMainContentLayout.addView(mSubjectCHolder.getRootView(), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        popUpWindow(TAB_SORT);
    }

    private void handleClickSelectView() {

        mMainContentLayout.removeAllViews();
        mMainContentLayout.addView(mSelectHolder.getRootView(), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        popUpWindow(TAB_SELECT);
    }

    private void popUpWindow(int tab) {
        if (mTabRecorder != -1) {
            resetTabExtend(mTabRecorder);
        }

        mDivderView.setVisibility(View.VISIBLE);

        extendsContent();
        setTabExtend(tab);
        mTabRecorder = tab;

        mMainContentLayout.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_in_anim));
    }

    private void extendsContent() {
        mDivderView.setVisibility(View.GONE);

        mContentLayout.removeAllViews();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentLayout.addView(mPopupWindowView, params);
    }

    private void dismissPopupWindow() {
        if (!isShowing()) {
            return;
        }

        mContentLayout.removeAllViews();
        setTabClose();

        mMainContentLayout.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_out_anim));

    }

    public boolean isShowing() {
        View view = mContentLayout.findViewById(R.id.ll_background);
        return view != null;
    }

    public void setOnMenuSelectDataChangedListener(OnMenuSelectDataChangedListener onMenuSelectDataChangedListener) {
        this.mOnMenuSelectDataChangedListener = onMenuSelectDataChangedListener;
    }

    public interface OnMenuSelectDataChangedListener {

        void onSubjectABChanged(int indexChannalA, int indexChannalB);

        void onSubjectCChanged(int indexChannalC);

        void onViewClicked(View view);

        //筛选菜单，当点击其他处菜单收回后，需要更新当前选中项
        void onSelectedDismissed(int indexChannalA, int indexChannalB);
    }

    private void setTabExtend(int tab) {
        if (tab == TAB_SUBJECT) {
            mChannelBText.setTextColor(getResources().getColor(R.color.blue_50));
            mSubjectArrowImage.setImageResource(R.drawable.channel_open_icon);
        } else if (tab == TAB_SORT) {
            mChannelCText.setTextColor(getResources().getColor(R.color.blue_50));
            mSortArrowImage.setImageResource(R.drawable.channel_open_icon);
        } else if (tab == TAB_SELECT) {
            mSelectText.setTextColor(getResources().getColor(R.color.blue_50));
            mSelectArrowImage.setImageResource(R.drawable.channel_open_icon);
        }
    }

    private void resetTabExtend(int tab) {
        if (tab == TAB_SUBJECT) {
            mChannelBText.setTextColor(getResources().getColor(R.color.blue_grey_600));
            mSubjectArrowImage.setImageResource(R.drawable.channel_close_icon);
        } else if (tab == TAB_SORT) {
            mChannelCText.setTextColor(getResources().getColor(R.color.blue_grey_600));
            mSortArrowImage.setImageResource(R.drawable.channel_close_icon);
        } else if (tab == TAB_SELECT) {
            mSelectText.setTextColor(getResources().getColor(R.color.blue_grey_600));
            mSelectArrowImage.setImageResource(R.drawable.channel_close_icon);
        }
    }

    private void setTabClose() {

        mChannelBText.setTextColor(getResources().getColor(R.color.text_dark));
        mSubjectArrowImage.setImageResource(R.drawable.channel_close_icon);

        mChannelCText.setTextColor(getResources().getColor(R.color.text_dark));
        mSortArrowImage.setImageResource(R.drawable.channel_close_icon);

        mSelectText.setTextColor(getResources().getColor(R.color.text_dark));
        mSelectArrowImage.setImageResource(R.drawable.channel_close_icon);
    }


    public void clearAllInfo() {
        //清除控件内部选项
        mSubjectABHolder.refreshData(mChannelDataList, 0, -1);
        mSubjectCHolder.refreshView(null);
//        mSelectHolder.refreshView(null);

        //清除菜单栏显示
        mChannelBText.setText("type1");
        mChannelCText.setText("type2");
    }
}