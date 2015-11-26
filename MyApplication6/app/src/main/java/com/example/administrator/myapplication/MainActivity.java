package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button mbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbtn = (Button)this.findViewById(R.id.key);
        mbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "000000" , Toast.LENGTH_LONG).show();
            }
        });

    }



    public void popup(View v) {
        Toast.makeText(MainActivity.this, "12345" , Toast.LENGTH_LONG).show();
    }

}
