package com.clsan.byciclover.fragments;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.clsan.byciclover.R;
import java.util.List;

/**
 * Created by clsan on 29/04/2017.
 */
public class SecondDetailInfoFragment extends Fragment {
  // Q4
  @BindViews({
      R.id.fragment_info_detail_q4_1,
      R.id.fragment_info_detail_q4_2,
      R.id.fragment_info_detail_q4_3
  })
  List<TextView> q4s;
  public TextView a4;

  // Q5
  @BindViews({R.id.fragment_info_detail_q5_1, R.id.fragment_info_detail_q5_2})
  List<TextView> q5s;
  public TextView a5;

  // Q6
  @BindViews({R.id.fragment_info_detail_q6_1, R.id.fragment_info_detail_q6_2})
  List<ImageView> q6s;
  ImageView a6;

  private Unbinder unbinder;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_info_detail_second, container, false);
    unbinder = ButterKnife.bind(this, view);

    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    for (ImageView q6 :q6s) {
      ((GradientDrawable) q6.getBackground())
          .setColor(ContextCompat.getColor(getActivity(), R.color.byciclover_white));
      ((GradientDrawable) q6.getBackground()).setStroke(3, R.color.byciclover_gray);
    }
    q6s.get(0).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.good_icon_gray));
    q6s.get(1).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.bad_icon_gray));
  }

  @OnClick({
      R.id.fragment_info_detail_q4_1,
      R.id.fragment_info_detail_q4_2,
      R.id.fragment_info_detail_q4_3
  })
  void onQ4Select(View view) {
    int selected = 0;
    if (view.getId() == R.id.fragment_info_detail_q4_1) {
      selected = 0;
    } else if (view.getId() == R.id.fragment_info_detail_q4_2) {
      selected = 1;
    } else if (view.getId() == R.id.fragment_info_detail_q4_3) {
      selected = 2;
    } else {
      throw new RuntimeException("Q4에는 그러한 응답이 없습니다");
    }

    for (int i = 0; i < 3; i++) {
      GradientDrawable gd = (GradientDrawable) q4s
          .get(i)
          .getBackground();
      gd.setColor(ContextCompat.getColor(getActivity(),
          i == selected ? R.color.byciclover_purple : R.color.byciclover_white));
      gd.setStroke(i == selected ? 0 : 3,
          i == selected ? R.color.byciclover_purple : R.color.byciclover_gray);

      TextView tv = q4s.get(i);
      tv.setTextColor(ContextCompat.getColor(getActivity(),
          i == selected ? R.color.byciclover_white : R.color.byciclover_black));
    }
  }

  @OnClick({R.id.fragment_info_detail_q5_1, R.id.fragment_info_detail_q5_2})
  void onQ5Select(View view) {
    Boolean isQ5_1Selected = view.getId() == R.id.fragment_info_detail_q5_1;

    GradientDrawable toPurple = (GradientDrawable) q5s
        .get(isQ5_1Selected ? 0 : 1)
        .getBackground();
    toPurple.setColor(ContextCompat.getColor(getActivity(), R.color.byciclover_purple));
    toPurple.setStroke(0, R.color.byciclover_purple);
    TextView toPurpleTextView = q5s.get(isQ5_1Selected? 0 : 1);
    toPurpleTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.byciclover_white));

    GradientDrawable toGray = (GradientDrawable) q5s
        .get(!isQ5_1Selected ? 0 : 1)
        .getBackground();
    toGray.setColor(ContextCompat.getColor(getActivity(), R.color.byciclover_white));
    toGray.setStroke(3, R.color.byciclover_gray);
    TextView toGrayTextView = q5s.get(!isQ5_1Selected? 0 : 1);
    toGrayTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.byciclover_black));
  }

  @OnClick({R.id.fragment_info_detail_q6_1, R.id.fragment_info_detail_q6_2})
  void onQ6Select(View view) {
    Boolean isQ6_1Selected = view.getId() == R.id.fragment_info_detail_q6_1;

    GradientDrawable toPurple = (GradientDrawable) q6s
        .get(isQ6_1Selected ? 0 : 1)
        .getBackground();
    toPurple.setColor(ContextCompat.getColor(getActivity(), R.color.byciclover_purple));
    toPurple.setStroke(0, R.color.byciclover_white);
    q6s.get(isQ6_1Selected ? 0 : 1).setImageDrawable(getActivity().getResources()
        .getDrawable(isQ6_1Selected ? R.drawable.good_icon_white : R.drawable.bad_icon_white));

    GradientDrawable toGray = (GradientDrawable) q6s
        .get(!isQ6_1Selected ? 0 : 1)
        .getBackground();
    toGray.setColor(ContextCompat.getColor(getActivity(), R.color.byciclover_white));
    toGray.setStroke(3, R.color.byciclover_gray);
    q6s.get(!isQ6_1Selected ? 0 : 1).setImageDrawable(getActivity().getResources()
        .getDrawable(!isQ6_1Selected ? R.drawable.good_icon_gray : R.drawable.bad_icon_gray));
  }
}
