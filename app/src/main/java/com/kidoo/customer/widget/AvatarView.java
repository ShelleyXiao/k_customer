package com.kidoo.customer.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.kidoo.customer.GlideApp;
import com.kidoo.customer.R;
import com.kidoo.customer.utils.UIHelper;

/** 
 * description: 通讯模块用户头像
 * autour: ShaudXiao
 * date: 2017/9/24  
 * update: 2017/9/24
 * version: 
*/
public class AvatarView extends CircleImageView {
    public static final String AVATAR_SIZE_REG = "_[0-9]{1,3}";
    public static final String MIDDLE_SIZE = "_100";
    public static final String LARGE_SIZE = "_200";

    private int id;
    private String name;
    private Activity aty;

    public AvatarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AvatarView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        aty = (Activity) context;
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(name)) {
                    UIHelper.showUserCenter(getContext(), id, name);
                }
            }
        });
    }

    public void setUserInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setAvatarUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            setImageResource(R.drawable.def_user);
            return;
        }
        // 由于头像地址默认加了一段参数需要去掉
        final String headUrl = url;
//        if (end > 0) {
//            headUrl = url.substring(0, end);
//        } else {
//            headUrl = url;
//        }

        if (aty != null) {
            GlideApp.with(aty).load(headUrl)
                    .placeholder(R.drawable.def_user)
                    .error(R.drawable.def_user)
                    .into(this);

        }
    }

    public static String getSmallAvatar(String source) {
        return source;
    }

    public static String getMiddleAvatar(String source) {
        if (source == null)
            return "";
        return source.replaceAll(AVATAR_SIZE_REG, MIDDLE_SIZE);
    }

    public static String getLargeAvatar(String source) {
        if (source == null)
            return "";
        return source.replaceAll(AVATAR_SIZE_REG, LARGE_SIZE);
    }
}
