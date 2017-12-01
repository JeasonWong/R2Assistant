package me.wangyuwei.r2assistant.submodule1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Sub1Activity extends AppCompatActivity {

    @BindView(R2.id.sub1)
    protected TextView vText;

    @BindView(R2.id.ttt)
    protected TextView vText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1);

        ButterKnife.bind(this);

        vText.setText("success");
    }
}
