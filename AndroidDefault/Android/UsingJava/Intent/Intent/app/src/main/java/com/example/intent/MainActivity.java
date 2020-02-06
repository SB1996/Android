package com.example.intent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edMassage;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edMassage = (EditText)findViewById(R.id.massage_et);
        nextButton = (Button)findViewById(R.id.next_btn);

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*String url = "http://www.google.co.in";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);*/

                String massage = edMassage.getText().toString();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("SendKey",massage);
                startActivity(intent);

            }
        });

    }
}
