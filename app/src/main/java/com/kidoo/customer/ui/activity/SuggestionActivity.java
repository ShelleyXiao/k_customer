package com.kidoo.customer.ui.activity;

import android.view.View;
import android.widget.Button;

import com.kidoo.customer.R;
import com.kidoo.customer.ui.base.activities.BaseBackActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;

/**
 * User: ShaudXiao
 * Date: 2017-12-12
 * Time: 19:25
 * Company: zx
 * Description:
 * FIXME
 */


public class SuggestionActivity extends BaseBackActivity {

    @BindView(R.id.bt_send)
    Button btSend;
    @BindView(R.id.met_content)
    MaterialEditText materialEditText;

    @Override
    protected int getContentView() {
        return R.layout.activity_suggestion;
    }

    private void sendSuggestion() {
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                RetrofitHelper retrofitHelper = new RetrofitHelper(SuggestionActivity.this);
//                retrofitHelper.fetchSuggestion(materialEditText.getText().toString())
//                        .compose(RxUtil.<SuggestionBean>rxSchedulerHelper())
//                        .subscribe(new Action1<SuggestionBean>() {
//                            @Override
//                            public void call(SuggestionBean suggestionBean) {
//                                ToastUtils.showShrotMsg(mContext,suggestionBean.getMessage());
//                            }
//                        }, new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//
//                            }
//                        });
            }
        });
    }
}
