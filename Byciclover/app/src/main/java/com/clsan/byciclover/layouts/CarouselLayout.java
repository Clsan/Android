package com.clsan.byciclover.layouts;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.clsan.byciclover.adapters.CarouselPageAdapter;

/**
 * Created by clsan on 22/04/2017.
 */
public class CarouselLayout extends LinearLayout {

  private float scale = CarouselPageAdapter.BIG_SCALE;

  public CarouselLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void setScaleBoth(float scale) {
    this.scale = scale;
    this.invalidate();    // If you want to see the scale every time you set
    // scale you need to have this line here,
    // invalidate() function will call onDraw(Canvas)
    // to redraw the view for you
  }

  @Override
  protected void onDraw(Canvas canvas) {
    int w = this.getWidth();
    int h = this.getHeight();
    canvas.scale(scale, scale, w / 3, h / 3);

    super.onDraw(canvas);
  }
}