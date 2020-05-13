package com.saint.push_lib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.saint.pushlib.PushManager;
import com.saint.pushlib.util.PushLog;

public class Main2Activity extends AppCompatActivity {

    TextView tvInitResult;
    public MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tvInitResult = findViewById(R.id.tv_init_result);
        NotificationHelper helper = new NotificationHelper(this);
        initReceiver();
//        PushControl.INSTANCE.init(true, getApplication(), true, true, true);

        tvInitResult.postDelayed(new Runnable() {
            @Override
            public void run() {
                PushManager.INSTANCE.loginIn();
            }
        }, 500);
    }

    private void initReceiver() {
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastUtil.ACTION_INIT);
        filter.addAction(BroadcastUtil.ACTION_TOKEN);
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, filter);
    }

    public void goSplash(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tudingprotection://splash"));
        PushLog.e("intent==》》  " + intent.toUri(Intent.URI_INTENT_SCHEME));
        startActivity(intent);
    }

    public void goMain(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tudingprotection://main"));
        intent.putExtra("from", "msgnotice");
        PushLog.e("intent==》》  " + intent.toUri(Intent.URI_INTENT_SCHEME));
        startActivity(intent);
    }

    public void pushStatus(View v) {
        PushManager.INSTANCE.pushStatus();
    }

    public void getId(View v) {
        PushManager.INSTANCE.loginIn();
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
            } else if (TextUtils.equals(BroadcastUtil.ACTION_TOKEN, action)) {
                tvInitResult.append("\n");
                tvInitResult.append(intent.getStringExtra("token"));
            }
        }
    }
}
