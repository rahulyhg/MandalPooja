package com.spps.mandal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ImageForAds extends AppCompatActivity implements View.OnClickListener{

    private static int SPLASH_TIME_OUT = 10000;

    RelativeLayout imageAds;
    //ImageView imageAds;
    Button closeBtn;
    String checkCounter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageview_for_ads);

        imageAds = (RelativeLayout) findViewById(R.id.imageAds);
        //imageAds = (ImageView) findViewById(R.id.imageAds);
        closeBtn = (Button) findViewById(R.id.closeAds);

        Intent intent = getIntent();
        if (null != intent) {
            checkCounter = intent.getStringExtra("counter");
        }

//        imageAds.setOnClickListener(this);
//        closeBtn.setOnClickListener(this);

        imageAds.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.studentcares.net/"));
                startActivity(browserIntent);
                return false;
            }
        });
        closeBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
               ImageForAds.this.finish();
//                if(checkCounter.equals("1")){
//                    Intent indexIntent = new Intent(ImageForAds.this, MainActivity.class);
//                    startActivity(indexIntent);
//                }
                if(checkCounter.equals("2")){
                    Intent indexIntent = new Intent(ImageForAds.this, AddYaatra.class);
                    startActivity(indexIntent);
                }else if(checkCounter.equals("3")){
                    Intent indexIntent = new Intent(ImageForAds.this, EditYaatra.class);
                    startActivity(indexIntent);
                }else if(checkCounter.equals("4")){
                    Intent indexIntent = new Intent(ImageForAds.this, YaatraList.class);
                    startActivity(indexIntent);
                }
                return false;
            }
        });

//        new Handler().postDelayed(new Runnable() {
//
//           @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//                if(checkCounter.equals("1")){
//                    Intent indexIntent = new Intent(ImageForAds.this, MainActivity.class);
//                    startActivity(indexIntent);
//                }else if(checkCounter.equals("2")){
//                    Intent indexIntent = new Intent(ImageForAds.this, AddYaatra.class);
//                    startActivity(indexIntent);
//                }else if(checkCounter.equals("3")){
//                    Intent indexIntent = new Intent(ImageForAds.this, EditYaatra.class);
//                    startActivity(indexIntent);
//                }
//                else if(checkCounter.equals("4")){
//                    Intent indexIntent = new Intent(ImageForAds.this, ShowYaatra.class);
//                    startActivity(indexIntent);
//                }
//
//
//                // close this activity
//                finish();
//            }
//        }, SPLASH_TIME_OUT);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.imageAds) {
            //open in browser
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.studentcares.net/"));
            startActivity(browserIntent);
        }
//        else if (v.getId() == R.id.closeAds){
//            ImageForAds.this.finish();
//            if(checkCounter.equals("1")){
//                Intent indexIntent = new Intent(ImageForAds.this, MainActivity.class);
//                startActivity(indexIntent);
//            }else if(checkCounter.equals("2")){
//                Intent indexIntent = new Intent(ImageForAds.this, AddYaatra.class);
//                startActivity(indexIntent);
//            }else if(checkCounter.equals("3")){
//                Intent indexIntent = new Intent(ImageForAds.this, EditYaatra.class);
//                startActivity(indexIntent);
//            }else if(checkCounter.equals("4")){
//                Intent indexIntent = new Intent(ImageForAds.this, ShowYaatra.class);
//                startActivity(indexIntent);
//            }
//        }

    }
}
