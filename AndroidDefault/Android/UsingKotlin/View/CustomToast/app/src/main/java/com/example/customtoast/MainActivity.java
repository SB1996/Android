package com.example.customtoast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button toastMassage  = findViewById(R.id.custom_toast);

        toastMassage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /*Context context = getApplicationContext();
                CharSequence text = "Hello toast!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();*/


                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_success_view, (ViewGroup) findViewById(R.id.success_custom_toast));

                TextView text = (TextView) layout.findViewById(R.id.success_massage);
                ImageView icon = (ImageView) layout.findViewById(R.id.success_ic);
                text.setText("Success");

                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });
    }
}
