package com.example.hw.android6weeks;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {

    Button btnHttp;
    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = (TextView) findViewById(R.id.textView);

        btnHttp = (Button)findViewById(R.id.idbtnHttp);
        btnHttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {
                    public void run(){
                        String response = getHttpResponse();
                        Bundle bun = new Bundle();
                        bun.putString("RESULT", response);
                        Message msg = handler.obtainMessage();
                        msg.setData(bun);
                        handler.sendMessage(msg);
                    }
                }.start();
            }
        });
    }

    private String getHttpResponse() {
        BufferedReader reader;
        URL urlObj = null;
        try {
            urlObj = new URL("http://google.co.kr");
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            Log.d("HTTPTEST", "length = " + connection.getResponseCode());
            Log.d("HTTPTEST", "respCode = " + connection.getContentType());
            Log.d("HTTPTEST", "contentType = " + connection.getContent());

            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;

            while((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Not thing";
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            String result = bun.getString("RESULT");
            textResult.setText(result);
        }
    };
}

