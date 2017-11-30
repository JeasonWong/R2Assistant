package me.wangyuwei.r2assistant.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.wangyuwei.r2assistant.submodule1.Sub1Activity;
import me.wangyuwei.r2assistant.submodule2.Sub2Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn1, R.id.btn2})
    protected void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn1:
                intent.setClass(this, Sub1Activity.class);
                break;
            case R.id.btn2:
                intent.setClass(this, Sub2Activity.class);
                break;
        }
        startActivity(intent);
    }

}
