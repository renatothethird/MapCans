package com.example.masterpeps.mapcans2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import com.example.masterpeps.mapcans2.R;

import org.w3c.dom.Text;

public class SplashScreen extends AppCompatActivity {

    TextView mapCans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mapCans = (TextView) findViewById(R.id.textView_mapCansSplash);
        String text = "<font color=#000>Map</font><font color=#4CAF50>Cans</font>";
        mapCans.setText(Html.fromHtml(text));
        Thread loadingThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);  //Delay of 2 seconds
                } catch (Exception e) {

                } finally {
                    Intent intent = new Intent(com.example.masterpeps.mapcans2.SplashScreen.this,
                            MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        loadingThread.start();
    }
}
