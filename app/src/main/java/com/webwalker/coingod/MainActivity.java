package com.webwalker.coingod;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webwalker.core.utility.Logger;
import com.webwalker.http.OkHttpUtils;
import com.webwalker.http.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btnStart)
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick({R.id.btnStart})
    public void click(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnStart:
                
                break;
        }
    }
}
