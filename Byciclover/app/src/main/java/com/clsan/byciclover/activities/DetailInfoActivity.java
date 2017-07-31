package com.clsan.byciclover.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.clsan.byciclover.R;
import com.clsan.byciclover.fragments.FirstDetailInfoFragment;
import com.clsan.byciclover.fragments.SecondDetailInfoFragment;
import java.util.List;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by clsan on 06/04/2017.
 */
public class DetailInfoActivity extends AppCompatActivity {
  // Bottom bar
  @BindView(R.id.activity_info_detail_bottom_button)
  Button bottomButton;
  @BindViews({
      R.id.activity_info_detail_bottom_first_indicator,
      R.id.activity_info_detail_bottom_second_indicator
  })
  List<ImageView> pageIndicators;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_info_detail);
    ButterKnife.bind(this);

    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.framelayout_activity_info_detail,
            new FirstDetailInfoFragment(),
            FirstDetailInfoFragment.class.getSimpleName())
        .addToBackStack(FirstDetailInfoFragment.class.getSimpleName()).commit();
  }

  @OnClick(R.id.activity_info_detail_bottom_button)
  void onStartButtonClick() {
    if (getString(R.string.detail_info_start).equals(bottomButton.getText())) {
      // Start main
      Intent intent = new Intent(this, MainActivity.class);
      startActivity(intent);
      finish();
    } else {
      bottomButton.setText(getString(R.string.detail_info_start));
      pageIndicators.get(0).setBackground(getResources().getDrawable(R.drawable.circle_soft_purple));
      pageIndicators.get(1).setBackground(getResources().getDrawable(R.drawable.circle_purple));

      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.framelayout_activity_info_detail,
              new SecondDetailInfoFragment(),
              SecondDetailInfoFragment.class.getSimpleName())
          .addToBackStack(SecondDetailInfoFragment.class.getSimpleName()).commit();
    }
  }

  @Override
  public void onBackPressed() {
    if (getString(R.string.detail_info_next).equals(bottomButton.getText())) {
      Intent intent = new Intent(this, NicknameActivity.class);
      startActivity(intent);
      finish();
    } else {
      super.onBackPressed();
      if (getString(R.string.detail_info_start).equals(bottomButton.getText())) {
        bottomButton.setText(getString(R.string.detail_info_next));
        pageIndicators.get(0).setBackground(getResources().getDrawable(R.drawable.circle_purple));
        pageIndicators.get(1).setBackground(getResources().getDrawable(R.drawable.circle_soft_purple));
      }
    }
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
