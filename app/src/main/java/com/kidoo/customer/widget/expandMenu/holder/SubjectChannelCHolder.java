package com.kidoo.customer.widget.expandMenu.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kidoo.customer.R;
import com.kidoo.customer.bean.ChannelC;

import java.util.List;


/** 
 * description:
 * autour: ShaudXiao
 * date: 2017/12/9  
 * update: 2017/12/9
 * version: 
*/

public class SubjectChannelCHolder extends BaseWidgetHolder<List<ChannelC>>{

    private List<ChannelC> mDataList;

    private ListView lvChannelCView;
    private ImageView mRightRecordImageView = null;
    private int mSelectedIndex = -1;

    private OnChannelCListViewItemSelectedListener onChannelCListViewItemSelectedListener;

    public SubjectChannelCHolder(Context context) {
        super(context);
    }

    @Override
    public View initView() {

        View view = View.inflate(mContext, R.layout.layout_holder_subject_channelc, null);

        lvChannelCView = (ListView) view.findViewById(R.id.listview);


        lvChannelCView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedIndex = position;
                ImageView imageView = (ImageView) view.findViewById(R.id.list2_right);

                if(mRightRecordImageView != null) {
                    mRightRecordImageView.setVisibility(View.INVISIBLE);
                }

                imageView.setVisibility(View.VISIBLE);

                mRightRecordImageView = imageView;

                if(onChannelCListViewItemSelectedListener != null){


                    String text = mDataList.get(mSelectedIndex).getName();

                    onChannelCListViewItemSelectedListener.OnListViewItemSelected(mSelectedIndex, text);
                }
            }
        });


        return view;
    }

    @Override
    public void refreshView(List<ChannelC> data) {
        if(data != null) {
            mDataList = data;
            ChannelCAdapter ad = new ChannelCAdapter(mDataList);
            lvChannelCView.setAdapter(ad);

        }
    }

    public void refreshViewData(List<ChannelC> data, int selectIndex) {
        if(data != null) {
            mDataList = data;
            ChannelCAdapter ad = new ChannelCAdapter(mDataList);
            lvChannelCView.setAdapter(ad);
            mSelectedIndex = selectIndex;

        }
    }


    public void setOnChannelCListViewItemSelectedListener(OnChannelCListViewItemSelectedListener onChannelCListViewItemSelectedListener) {
        this.onChannelCListViewItemSelectedListener = onChannelCListViewItemSelectedListener;
    }



    public interface OnSortInfoSelectedListener{
        void onSortInfoSelected(String info);
    }

    private class ChannelCAdapter extends BaseAdapter {

        private List<ChannelC> mRightDataList;

        public ChannelCAdapter(List<ChannelC> list){
            this.mRightDataList = list;
        }

        public void setDataList(List<ChannelC> list ){
            this.mRightDataList = list;
        }

        @Override
        public int getCount() {
            return mRightDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mRightDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ChannelCViewHolder holder;
            if(convertView == null){
                holder = new ChannelCViewHolder();
                convertView = View.inflate(mContext, R.layout.layout_child_menu_item, null);
                holder.rightText = (TextView) convertView.findViewById(R.id.child_textView);
                holder.selectedImage = (ImageView)convertView.findViewById(R.id.list2_right);
                convertView.setTag(holder);
            }else{
                holder = (ChannelCViewHolder) convertView.getTag();
            }

            holder.rightText.setText(mRightDataList.get(position).getName());
            if(mSelectedIndex == position){
                holder.selectedImage.setVisibility(View.VISIBLE);
                mRightRecordImageView = holder.selectedImage;
            }else{
                holder.selectedImage.setVisibility(View.INVISIBLE);
            }

            return convertView;
        }
    }



    private static class ChannelCViewHolder{
        TextView rightText;
        ImageView selectedImage;
    }

    public interface OnChannelCListViewItemSelectedListener{
        void OnListViewItemSelected(int index, String text);
    }
}
