package com.coding.jjlop.forestappupt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash__screen);
        getSupportActionBar().hide();
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);
                    Intent ite = new Intent(Splash_Screen.this,Login.class);
                    startActivity(ite);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();


    }
}
