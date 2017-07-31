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
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.clsan.byciclover.R;
import com.clsan.byciclover.fonts.FontsLoader;
import java.util.List;

/**
 * Created by clsan on 29/04/2017.
 */
public class FirstDetailInfoFragment extends Fragment {

  @BindViews({
      R.id.fragment_info_detail_first_boy_button,
      R.id.fragment_info_detail_first_girl_button,
  })
  List<LinearLayout> genderButtons;
  @BindViews({
      R.id.fragment_info_detail_first_boy_button_text,
      R.id.fragment_info_detail_first_girl_button_text
  })
  List<TextView> genderButtonsText;

  @BindView(R.id.fragment_info_detail_first_line)
  LinearLayout agesLine;
  @BindViews({
      R.id.fragment_info_detail_teens,
      R.id.fragment_info_detail_twenties,
      R.id.fragment_info_detail_thirties,
      R.id.fragment_info_detail_forties
  })
  List<ImageView> agesImageViews;
  ImageView selectedAge;

  @BindViews({
      R.id.fragment_info_detail_alone,
      R.id.fragment_info_detail_couple,
      R.id.fragment_info_detail_friend
  })
  List<ImageView> accompanyImageViews;
  ImageView selectedAccompany;

  private Unbinder unbinder;


  @Override
  public View onCreateView(LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.fragment_info_detail_first, container, false);
    unbinder = ButterKnife.bind(this, view);

    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    for (LinearLayout genderButton : genderButtons) {
      GradientDrawable toGray = (GradientDrawable) genderButton.getBackground();
      toGray.setColor(ContextCompat.getColor(getActivity(), R.color.byciclover_white));
      toGray.setStroke(3, R.color.byciclover_gray);
    }

    for (ImageView agesImageView : agesImageViews) {
      ((GradientDrawable) agesImageView.getDrawable()).setStroke(3, R.color.byciclover_gray);
    }
    agesLine.setBackgroundColor(R.color.byciclover_gray);

    for (ImageView accompanyButton : accompanyImageViews) {
      ((GradientDrawable) accompanyButton.getBackground()).setStroke(3, R.color.byciclover_gray);
    }
  }

  @OnClick({
      R.id.fragment_info_detail_first_boy_button_text,
      R.id.fragment_info_detail_first_girl_button_text
  })
  void onGenderSelect(View view) {
    Boolean isBoySelected = view.getId() == R.id.fragment_info_detail_first_boy_button_text;

    GradientDrawable toPurple = (GradientDrawable) genderButtons
        .get(isBoySelected ? 0 : 1)
        .getBackground();
    toPurple.setColor(ContextCompat.getColor(getActivity(), R.color.byciclover_purple));
    toPurple.setStroke(0, R.color.byciclover_purple);
    TextView toPurpleTextView = genderButtonsText.get(isBoySelected? 0 : 1);
    toPurpleTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.byciclover_white));

    GradientDrawable toGray = (GradientDrawable) genderButtons
        .get(!isBoySelected ? 0 : 1)
        .getBackground();
    toGray.setColor(ContextCompat.getColor(getActivity(), R.color.byciclover_white));
    toGray.setStroke(3, R.color.byciclover_gray);
    TextView toGrayTextView = genderButtonsText.get(!isBoySelected? 0 : 1);
    toGrayTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.byciclover_black));
  }

  @OnClick({
      R.id.fragment_info_detail_teens,
      R.id.fragment_info_detail_twenties,
      R.id.fragment_info_detail_thirties,
      R.id.fragment_info_detail_forties
  })
  void onAgeSelect(View view) {
    if (selectedAge != null && selectedAge.getId() != view.getId()) {
      // 원상 복구
      selectedAge.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.circle_gray_stroke));
      selectedAge.setPadding(26, 26, 26, 26);
    }
    selectedAge = (ImageView) view;
    ((ImageView) view).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.circle_purple));
    view.setPadding(0, 0, 0, 0);
  }

  @OnClick({
      R.id.fragment_info_detail_alone,
      R.id.fragment_info_detail_couple,
      R.id.fragment_info_detail_friend
  })
  void onAccompanySelect(View view) {
    if (selectedAccompany != null) {
      selectedAccompany.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_gray_stroke));
    }

    selectedAccompany = (ImageView) view;
    view.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_purple));

    if (view.getId() == R.id.fragment_info_detail_alone) {
      accompanyImageViews.get(0).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.alone_icon_white));
      accompanyImageViews.get(1).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.couple_icon_gray));
      accompanyImageViews.get(2).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.friend_icon_gray));
    } else if (view.getId() == R.id.fragment_info_detail_couple) {
      accompanyImageViews.get(0).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.alone_icon_gray));
      accompanyImageViews.get(1).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.couple_icon_white));
      accompanyImageViews.get(2).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.friend_icon_gray));
    } else if (view.getId() == R.id.fragment_info_detail_friend) {
      accompanyImageViews.get(0).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.alone_icon_gray));
      accompanyImageViews.get(1).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.couple_icon_gray));
      accompanyImageViews.get(2).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.friend_icon_white));
    }
  }
}
