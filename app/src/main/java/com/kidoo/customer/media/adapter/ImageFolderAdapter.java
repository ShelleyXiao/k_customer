package com.kidoo.customer.media.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kidoo.customer.R;
import com.kidoo.customer.media.bean.ImageFolder;
import com.kidoo.customer.media.config.ImageLoaderListener;
import com.kidoo.customer.ui.base.adapter.BaseRecyleAdapter;

/**
 * User: ShaudXiao
 * Date: 2017-09-26
 * Time: 10:28
 * Company: zx
 * Description:
 * FIXME
 */


public class ImageFolderAdapter extends BaseRecyleAdapter<ImageFolder> {

    private ImageLoaderListener loader;

    public ImageFolderAdapter(Context context) {
        super(context, NEITHER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new FolderViewHolder(mInflater.inflate(R.layout.item_list_folder, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, ImageFolder item, int position) {
        FolderViewHolder  viewHolder = (FolderViewHolder) holder;
        viewHolder.tv_name.setText(item.getName());
        viewHolder.tv_size.setText(String.format("(%s)", item.getImages().size()));
        if(null != loader) {
            loader.displayImage(viewHolder.iv_image, item.getAlbumPath());
        }
    }

    public void setLoaer(ImageLoaderListener listener) {
        this.loader = listener;
    }

    public static class FolderViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_name, tv_size;

        public FolderViewHolder(View itemView) {
            super(itemView);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_folder);
            tv_name = (TextView) itemView.findViewById(R.id.tv_folder_name);
            tv_size = (TextView) itemView.findViewById(R.id.tv_size);
        }
    }
}
