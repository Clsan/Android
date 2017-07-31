package com.clsan.byciclover.fragments;

import static com.clsan.byciclover.adapters.CarouselPageAdapter.BIG_SCALE;
import static com.clsan.byciclover.adapters.CarouselPageAdapter.DIFF_SCALE;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.clsan.byciclover.R;
import com.clsan.byciclover.activities.MainActivity;
import com.clsan.byciclover.activities.NicknameActivity;
import com.clsan.byciclover.adapters.CarouselPageAdapter;
import com.clsan.byciclover.layouts.CarouselLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by clsan on 16/04/2017.
 */
public class SelectMapFragment extends Fragment {
  public final static int PAGES = 4;
  public final static int LOOPS = 1;
  public final static int FIRST_PAGE = 0;
  public static int CURR_PAGE = 0;
  public static Map<Integer, ImageView> cardButtons = new HashMap<>();

  public CarouselPageAdapter adapter;
  public static ViewPager pager;

  List<Drawable> locationDrawables;

  protected View view;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    if (view != null) {
      ViewGroup parent = (ViewGroup) view.getParent();
      if (parent != null)
        parent.removeView(view);
    } else {
      view = inflater.inflate(R.layout.fragment_map_select, container, false);
    }
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    pager = (ViewPager) getActivity().findViewById(R.id.pager_view_carousel);

    if (adapter == null) {
      adapter = new CarouselPageAdapter((MainActivity) getActivity(),
          getActivity().getSupportFragmentManager());
      pager.setAdapter(adapter);
    }

    pager.setCurrentItem(FIRST_PAGE);

    // Necessary or the pager will only have one extra page to show
    // make this at least however many pages you can see
    pager.setOffscreenPageLimit(3);

    // Set margin for pages as a negative number, so a part of next and
    // previous pages will be showed
    pager.setPageMargin(-850);

    pager.setPageTransformer(false, new PageTransformer() {
      @Override
      public void transformPage(View page, float position) {
        CarouselLayout myLinearLayout = (CarouselLayout) page.findViewById(R.id.root);
        ImageView button = (ImageView) myLinearLayout.findViewById(R.id.fragment_carousel_content);
        float scale = BIG_SCALE;
        if (position > 0) {
          scale = scale - position * DIFF_SCALE;
        } else {
          scale = scale + position * DIFF_SCALE;
        }

        if (scale < 0.8f) {
          scale = 0.8f;
        }

        myLinearLayout.setScaleX(scale);
        button.setScaleY(scale);
      }
    });

    pager.addOnPageChangeListener(new OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        CURR_PAGE = pager.getCurrentItem();
        setStringsByPos(position);
        for (int i = 0; i < 4; i++) {
          cardButtons.get(i).setAlpha((i == pager.getCurrentItem() - 1) ? 3f : 255f);
        }
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });

    setStringsByPos(0);
  }

  private void setStringsByPos(int pos) {
    if (locationDrawables == null) {
      locationDrawables = NicknameActivity.locationImages.getLocationDrawables();
    }

    TextView parkNameKo = (TextView) view.findViewById(R.id.park_name_ko);
    TextView parkNameEn = (TextView) view.findViewById(R.id.park_name_en);
    TextView parkLength = (TextView) view.findViewById(R.id.fragment_carousel_park_length);
    TextView parkDescription = (TextView) view.findViewById(R.id.fragment_carousel_park_description);
    ImageView location = (ImageView) view.findViewById(R.id.fragment_carousel_location);

    String koParkNameStr, enParkNameStr, lengthStr, descriptionStr;
    Drawable locationDrawable;
    if (pos == 0) {
      koParkNameStr = getActivity().getString(R.string.select_map_nanji_ko);
      enParkNameStr = getActivity().getString(R.string.select_map_nanji_en);
      lengthStr = getActivity().getString(R.string.select_map_nanji_length);
      descriptionStr = getActivity().getString(R.string.select_map_nanji_description);
      locationDrawable = locationDrawables.get(0);
    } else if (pos == 1) {
      koParkNameStr = getActivity().getString(R.string.select_map_yeouido_ko);
      enParkNameStr = getActivity().getString(R.string.select_map_yeouido_en);
      lengthStr = getActivity().getString(R.string.select_map_yeouido_length);
      descriptionStr = getActivity().getString(R.string.select_map_yeouido_description);
      locationDrawable = locationDrawables.get(1);
    } else if (pos == 2) {
      koParkNameStr = getActivity().getString(R.string.select_map_banpo_ko);
      enParkNameStr = getActivity().getString(R.string.select_map_banpo_en);
      lengthStr = getActivity().getString(R.string.select_map_banpo_length);
      descriptionStr = getActivity().getString(R.string.select_map_banpo_description);
      locationDrawable = locationDrawables.get(2);
    } else if (pos == 3) {
      koParkNameStr = getActivity().getString(R.string.select_map_ttugseom_ko);
      enParkNameStr = getActivity().getString(R.string.select_map_ttugseom_en);
      lengthStr = getActivity().getString(R.string.select_map_ttugseom_length);
      descriptionStr = getActivity().getString(R.string.select_map_ttugseom_description);
      locationDrawable = locationDrawables.get(3);
    } else {
      throw new RuntimeException("존재하지 않는 position 입니다");
    }

    parkNameKo.setText(koParkNameStr);
    parkNameEn.setText(enParkNameStr);
    parkLength.setText(lengthStr);
    parkDescription.setText(descriptionStr);
    location.setBackground(locationDrawable);
    ((TextView) view.findViewById(R.id.fragment_carousel_alone_text)).setText(String.valueOf(10 + new Random().nextInt(70)));
    ((TextView) view.findViewById(R.id.fragment_carousel_couple_text)).setText(String.valueOf(10 + new Random().nextInt(70)));
    ((TextView) view.findViewById(R.id.fragment_carousel_friend_text)).setText(String.valueOf(10 + new Random().nextInt(70)));
  }
}
