package com.clsan.byciclover.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.clsan.byciclover.R;
import com.clsan.byciclover.Utilities.Images;
import com.clsan.byciclover.Utilities.Images.Locations;
import com.clsan.byciclover.db.Actions;
import com.clsan.byciclover.db.UserDataController;
import com.clsan.byciclover.models.User;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by clsan on 05/04/2017.
 */
public class NicknameActivity extends AppCompatActivity {
  private UserDataController mUserDataController;

  @BindView(R.id.text_edit_nickname)
  EditText nickname;
  @BindView(R.id.activity_nickname_erase)
  ImageView erase;
  @BindView(R.id.activity_nickname_warning)
  TextView warning;
  @BindView(R.id.button_confirm_nickname)
  Button confirmButton;

  public static Locations locationImages;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    mUserDataController = new UserDataController(getApplicationContext());
    // When nickname is not set
    setContentView(R.layout.activity_nickname);
    // When nickname was set

    ButterKnife.bind(this);

    if (mUserDataController.getCount() > 0) {
      nickname.setText(mUserDataController.getUser().getMNickname());
    }

    nickname.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        erase.setVisibility(View.VISIBLE);
        if (nickname.getText().toString().equals("sona")) {
          warning.setVisibility(View.VISIBLE);
          warning.setText(R.string.nickname_already_exist);
          warning.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.byciclover_red));
        } else {
          warning.setVisibility(View.VISIBLE);
          warning.setText(R.string.nickname_looks_good);
          warning.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.byciclover_green));
        }
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });

    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        .setDefaultFontPath("fonts/NotoSansCJKkr-Regular.otf")
        .setFontAttrId(R.attr.fontPath)
        .build());

    locationImages = new Locations(this);

//
//    String gifUrl = "http://i.kinja-img.com/gawker-media/image/upload/s--B7tUiM5l--/gf2r69yorbdesguga10i.gif";
//    String url = "https://media1.giphy.com/media/l3zoKeX8bMG5sMP4s/giphy.gif";
//    Glide
//        .with( getApplicationContext() )
//        .load( url )
//        .asGif()
//        .into( (ImageView) findViewById(R.id.splash_test) );
  }

  @OnClick(R.id.button_confirm_nickname)
  void onConfirmButtonClick() {
    if (nickname.getText().toString().equals("sona")) {
      warning.setVisibility(View.VISIBLE);
      warning.setText(R.string.nickname_already_exist);
      warning.setTextColor(ContextCompat.getColor(this, R.color.byciclover_red));
      return;
    }
    // Save nickname
    if (mUserDataController.getCount() > 0) {
      mUserDataController.upsertUser(new User(nickname.getText().toString()), Actions.UPDATE);
    } else {
      mUserDataController.upsertUser(new User(nickname.getText().toString()), Actions.INSERT);
    }

    nickname.setText("저장된 이름 :" + mUserDataController.getUser().getMNickname());

    // Start main
    Intent intent = new Intent(this, DetailInfoActivity.class);
    startActivity(intent);
    finish();
  }

  @OnClick(R.id.activity_nickname_erase)
  void onErase() {
    nickname.setText("");
    erase.setVisibility(View.INVISIBLE);
    warning.setVisibility(View.INVISIBLE);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
