
package com.example.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private TextView tvShow ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tvShow = (TextView)findViewById(R.id.show_tv);
        Intent intent = getIntent();
        String result = (String)intent.getSerializableExtra("SendKey");
        tvShow.setText("Hii ! Mr."+result);
    }
}
