package com.kidoo.customer.ui.activity.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kidoo.customer.R;
import com.kidoo.customer.ui.base.activities.BaseBackActivity;
import com.kidoo.customer.widget.dialog.CommonDialog;

import butterknife.BindView;

/**
 * User: ShaudXiao
 * Date: 2017-12-12
 * Time: 19:06
 * Company: zx
 * Description:
 * FIXME
 */


public class SettingActivity extends BaseBackActivity {

    @BindView(R.id.rl_modify_pwd)
    LinearLayout rlModifyPwd;
    @BindView(R.id.rl_aboutas)
    LinearLayout rlAboutas;
    @BindView(R.id.rl_sggestion)
    LinearLayout rlSggestion;
    @BindView(R.id.bt_exit)
    Button btExit;


    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initWidget() {
        super.initWidget();


        rlModifyPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent =new Intent(mContext, ProfileActivity.class);
//                mContext.startActivity(intent);

            }
        });

        rlAboutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SettingActivity.this, AboutAsAcitivty.class);
                startActivity(intent);
            }
        });

        rlSggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SettingActivity.this, SuggestionActivity.class);
                startActivity(intent);
            }
        });

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonDialog dialog = new CommonDialog(SettingActivity.this);
                dialog.setTitle("");
                dialog.setMessage(getString(R.string.exit_dialog_msg));
                dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}
