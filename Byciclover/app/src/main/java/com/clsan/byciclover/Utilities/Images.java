package com.clsan.byciclover.Utilities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import com.clsan.byciclover.R;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * Created by clsan on 05/05/2017.
 */
public class Images {
  public static class Locations {
    @Getter
    public static List<Drawable> locationDrawables;
    public Locations(Activity activity) {
      if (locationDrawables == null) {
        locationDrawables = new ArrayList<>();
        locationDrawables.add(activity.getResources().getDrawable(R.drawable.location_nanji));
        locationDrawables.add(activity.getResources().getDrawable(R.drawable.location_yeouido));
        locationDrawables.add(activity.getResources().getDrawable(R.drawable.location_banpo));
        locationDrawables.add(activity.getResources().getDrawable(R.drawable.location_ttugseom));
      }
    }
  }
}
