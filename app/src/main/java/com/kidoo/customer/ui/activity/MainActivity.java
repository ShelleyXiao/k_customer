package com.kidoo.customer.ui;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kidoo.customer.R;
import com.kidoo.customer.ui.base.BaseActivity;
import com.tencent.bugly.crashreport.CrashReport;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private long mBackPressedTime;

    private Button btnTestJavaCrash;
    private Button btnTestANRCrash;
    private Button btnTestNativeCrash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTestJavaCrash = (Button) findViewById(R.id.btnTestJavaCrash);
        btnTestANRCrash = (Button) findViewById(R.id.btnTestANRCrash);
        btnTestNativeCrash = (Button)findViewById(R.id.btnTestNativeCrash);
        btnTestJavaCrash.setOnClickListener(this);
        btnTestANRCrash.setOnClickListener(this);
        btnTestNativeCrash.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {


        long curTime = SystemClock.uptimeMillis();
        if ((curTime - mBackPressedTime) < (3 * 1000)) {
            finish();
        } else {
            mBackPressedTime = curTime;
            Toast.makeText(this, R.string.tip_double_click_exit, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTestJavaCrash: // 点击测试Java Crash
                CrashReport.testJavaCrash();
                break;
            case R.id.btnTestANRCrash: // 点击测试ANR Crash
                CrashReport.testANRCrash();
                break;
            case R.id.btnTestNativeCrash: // 点击测试Native Crash
                CrashReport.testNativeCrash();
                break;
        }
    }
}
