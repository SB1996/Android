package com.example.intentfilters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button sendMail;
    private EditText massageText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        massageText = (EditText) findViewById(R.id.msgtxt_et);
        sendMail = (Button) findViewById(R.id.sendmail_btn);
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = massageText.getText().toString();
                Toast.makeText(getApplicationContext(),"Button Clicked : "+data,Toast.LENGTH_SHORT).show();
                if(true){
                    String url = "http://www.google.co.in";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.putExtra(Intent.EXTRA_TEXT, "Hi Guys, Welcome to Android Tutorial.");
                    intent.setData(Uri.parse(url));

                    startActivity(Intent.createChooser(intent, "Choose Web app."));
                }else{
                    Intent intentMail = new Intent(Intent.ACTION_SEND);
                    intentMail.setType("message/rfc822");
                    intentMail.putExtra(Intent.EXTRA_EMAIL, new String[]{"santanu.banik1996@gmail.com"});
                    intentMail.putExtra(Intent.EXTRA_SUBJECT, "Welcome to Android application development by Santanu");
                    intentMail.putExtra(Intent.EXTRA_TEXT, "Hi Guys, Welcome to Android Tutorial.");

                    startActivity(Intent.createChooser(intentMail,"Choose Mail App"));
                }
            }
        });
    }
}
