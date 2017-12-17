package com.kidoo.customer.ui.fragment.channelCampaign;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.R;
import com.kidoo.customer.adapter.competion.CompetionAlbumAdapter;
import com.kidoo.customer.adapter.itemDecoration.SpaceItemDecoration;
import com.kidoo.customer.bean.CompetionAlbumBean;
import com.kidoo.customer.bean.CompetionDetailResult;
import com.kidoo.customer.component.RxBus;
import com.kidoo.customer.media.ImageGalleryActivity;
import com.kidoo.customer.ui.base.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/12/16.
 */

public class CompetionAlbumFragment extends BaseFragment {

    @BindView(R.id.album)
    RecyclerView rvAlbum;

    private List<CompetionAlbumBean> mAlbumDatas = new ArrayList<>();
    private CompetionAlbumAdapter albumAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_competion_album_layout;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        albumAdapter = new CompetionAlbumAdapter(getActivity(), mAlbumDatas);
        rvAlbum.setAdapter(albumAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        rvAlbum.setLayoutManager(gridLayoutManager);
        rvAlbum.setHasFixedSize(true);
        rvAlbum.addItemDecoration(new SpaceItemDecoration(15));

    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        RxBus.getDefault().toObservableSticky(CompetionDetailResult.class)
                .subscribe(new Consumer<CompetionDetailResult>() {
                    @Override
                    public void accept(CompetionDetailResult result) throws Exception {
//                        LogUtils.i(result.toString());
                        if (result != null) {
                            albumAdapter.replaceData(result.getAlbumList());
                        }
                    }
                });

        albumAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArrayList<String>  picUrls = new ArrayList<>();
                String baseUrl = AppContext.context().getInitData().getQnDomain() ;
                for(CompetionAlbumBean bean : mAlbumDatas) {
                    String url = baseUrl + bean.getPic();
                    picUrls.add(url);
                }

                ImageGalleryActivity.show(getActivity(), (String[]) picUrls.toArray(), position);
            }
        });
    }
}
