package com.clsan.byciclover.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.clsan.byciclover.R;
import com.clsan.byciclover.activities.MainActivity;
import com.clsan.byciclover.layouts.CarouselLayout;

/**
 * Created by clsan on 22/04/2017.
 */
public class CarouselFragment extends Fragment {
  public static Fragment newInstance(MainActivity context, int pos, float scale) {
    Bundle b = new Bundle();
    b.putInt("pos", pos);
    b.putFloat("scale", scale);
    return Fragment.instantiate(context, CarouselFragment.class.getName(), b);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    if (container == null) {
      return null;
    }

    CarouselLayout l = (CarouselLayout)
        inflater.inflate(R.layout.fragment_carousel, container, false);
    int pos = this.getArguments().getInt("pos");
    ImageView card = (ImageView) l.findViewById(R.id.fragment_carousel_content);
    SelectMapFragment.cardButtons.put(pos, card);
    RequestManager cardImageRequestManager = Glide.with(getActivity());
    BitmapTypeRequest<Integer> cardImageRequest = null;
    if (pos == 0) {
      cardImageRequest = cardImageRequestManager.load(R.drawable.card_nanji_min).asBitmap();
    } else if (pos == 1) {
      cardImageRequest = cardImageRequestManager.load(R.drawable.card_yeouido_min).asBitmap();
    } else if (pos == 2) {
      cardImageRequest = cardImageRequestManager.load(R.drawable.card_banpo_min).asBitmap();
    } else if (pos == 3) {
      cardImageRequest = cardImageRequestManager.load(R.drawable.card_ttugseom_min).asBitmap();
    } else  {
      throw new RuntimeException("존재하지 않는 position 입니다");
    }
    cardImageRequest.into(card);

    card.setTag(String.valueOf(pos));
    card.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (v.getTag().equals(String.valueOf(SelectMapFragment.CURR_PAGE))) {
//          Toast.makeText(getActivity(),
//              String.valueOf(SelectMapFragment.CURR_PAGE),
//              Toast.LENGTH_SHORT).show();

          getActivity()
              .getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.framelayout_main,
                  new GoogleMapFragment(),
                  "BORROW_STEP")
              .addToBackStack((String) v.getTag()).commit();
        } else {
          SelectMapFragment.pager.setCurrentItem(Integer.parseInt((String) v.getTag()));
          SelectMapFragment.CURR_PAGE = Integer.parseInt((String) v.getTag());
        }
      }
    });

    float scale = this.getArguments().getFloat("scale");
    l.setScaleX(scale);
    card.setScaleY(scale);
    return l;
  }
}