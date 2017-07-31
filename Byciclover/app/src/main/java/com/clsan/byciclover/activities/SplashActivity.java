package com.clsan.byciclover.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.clsan.byciclover.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by clsan on 05/05/2017.
 */
public class SplashActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
//
//    String gifUrl = "http://i.kinja-img.com/gawker-media/image/upload/s--B7tUiM5l--/gf2r69yorbdesguga10i.gif";
//    String url = "https://media1.giphy.com/media/l3zoKeX8bMG5sMP4s/giphy.gif";
//    Glide.with(getApplicationContext())
//        .load(url)
//        .asGif()
//        .into((ImageView) findViewById(R.id.splash));

    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      public void run() {
        Intent intent = new Intent(getApplicationContext(), NicknameActivity.class);
        startActivity(intent);
        finish();
      }
    }, 0);
  }
}