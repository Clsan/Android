package com.clsan.byciclover.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.clsan.byciclover.R;
import com.clsan.byciclover.fragments.GoogleMapFragment;
import com.clsan.byciclover.fragments.SelectMapFragment;
import java.util.List;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener
{
  // Navigation Drawer.
  @BindView(R.id.layout_drawer)
  DrawerLayout drawerLayout;
  @BindView(R.id.view_navigation)
  public NavigationView navigationView;
  @BindViews({R.id.top_bar_menu_btn, R.id.top_bar_back_btn})
  List<ImageView> navigationDrawerButtons;

  @BindView(R.id.fab)
  Button fab;

  private Toast quitToast;

  public static BitmapTypeRequest<Integer> borrowBackground;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    // Init navigation drawer.
    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    navigationView.setNavigationItemSelectedListener(MainActivity.this);

    SelectMapFragment selectMapFragment = (SelectMapFragment) getSupportFragmentManager()
        .findFragmentByTag(SelectMapFragment.class.getSimpleName());
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.framelayout_main,
            selectMapFragment == null ? new SelectMapFragment() : selectMapFragment,
            SelectMapFragment.class.getSimpleName())
        .addToBackStack("MapSelectStack").commit();

    borrowBackground = Glide.with(this).load(R.drawable.borrow_background).asBitmap();
  }

  @OnClick(R.id.fab)
  void onFabClick() {
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @OnClick({R.id.top_bar_back_btn, R.id.top_bar_menu_btn})
  void onNavigationButtonsClick(View v) {
    switch (v.getId()) {
      case R.id.top_bar_menu_btn:
        drawerLayout.openDrawer(navigationView);

        final ImageView bob = (ImageView) findViewById(R.id.nav_profile_image);
        Glide.with(this)
            .load(R.drawable.bob)
            .asBitmap()
            .centerCrop()
            .into(new BitmapImageViewTarget(bob) {
              @Override
              protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                    .create(getApplicationContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                bob.setImageDrawable(circularBitmapDrawable);
              }
            });
        break;

      case R.id.top_bar_back_btn:
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        break;
    }
  }

  public Bitmap getRoundCroppedBitmap(Bitmap bitmap) {
    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
        bitmap.getHeight(), Config.ARGB_8888);
    Canvas canvas = new Canvas(output);

    final int color = 0xff424242;
    final Paint paint = new Paint();
    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(color);
    // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
    canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
        bitmap.getWidth() / 2, paint);
    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
    canvas.drawBitmap(bitmap, rect, rect, paint);
    //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
    //return _bmp;
    return output;
  }

  @Override
  public void onBackPressed() {
    if (GoogleMapFragment.isAnimating) {
      return;
    }

    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } if (!getSupportFragmentManager().findFragmentByTag(SelectMapFragment.class.getSimpleName()).isVisible()) {
      SelectMapFragment selectMapFragment = (SelectMapFragment) getSupportFragmentManager()
          .findFragmentByTag(SelectMapFragment.class.getSimpleName());
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.framelayout_main,
              selectMapFragment == null ? new SelectMapFragment() : selectMapFragment,
              SelectMapFragment.class.getSimpleName())
          .addToBackStack("MapSelectStack").commit();
    } else {
      if (quitToast == null || quitToast.getView().getWindowVisibility() != View.VISIBLE) {
        quitToast = Toast.makeText(this, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        quitToast.show();
      } else {
        finish();
      }
    }
  }

  @Override
  protected void onPause() {
    GoogleMapFragment.isAnimating = false;
    super.onPause();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    return false;
  }

}
