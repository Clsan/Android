package com.clsan.byciclover.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.clsan.byciclover.activities.MainActivity;
import com.clsan.byciclover.fragments.CarouselFragment;
import com.clsan.byciclover.fragments.SelectMapFragment;
import com.clsan.byciclover.layouts.CarouselLayout;

/**
 * Created by clsan on 22/04/2017.
 */
public class CarouselPageAdapter extends FragmentPagerAdapter {
  public final static float BIG_SCALE = 1.0f;
  public final static float SMALL_SCALE = 0.4f;
  public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;

  private CarouselLayout cur = null;
  private CarouselLayout next = null;
  private MainActivity context;
  private FragmentManager fm;
  private float scale;

  public CarouselPageAdapter(MainActivity context, FragmentManager fm) {
    super(fm);
    this.fm = fm;
    this.context = context;
  }

  @Override
  public Fragment getItem(int position) {
    // make the first pager bigger than others
    if (position == SelectMapFragment.FIRST_PAGE)
      scale = BIG_SCALE;
    else
      scale = SMALL_SCALE;

    position = position % SelectMapFragment.PAGES;
    return CarouselFragment.newInstance(context, position, scale);
  }

  @Override
  public int getCount() {
    return SelectMapFragment.PAGES * SelectMapFragment.LOOPS;
  }
}
