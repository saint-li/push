package com.saint.push_lib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.saint.pushlib.PushControl;

public class Main2Activity extends AppCompatActivity {

    TextView tvInitResult;
    public MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tvInitResult = findViewById(R.id.tv_init_result);
        initReceiver();
        PushControl.INSTANCE.init(true
                , getApplication()
                , false
                , true
                , true);
        tvInitResult.postDelayed(new Runnable() {
            @Override
            public void run() {
                PushControl.INSTANCE.loginIn();
            }
        }, 2000);
    }

    private void initReceiver() {
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastUtil.ACTION_INIT);
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, filter);
    }


    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(BroadcastUtil.ACTION_INIT, action)) {
                tvInitResult.setText(intent.getStringExtra("init"));
            }
        }
    }
}
