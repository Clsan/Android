package com.clsan.byciclover.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.clsan.byciclover.R;
import com.clsan.byciclover.activities.MainActivity;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.List;

/**
 * Created by clsan on 28/05/2017.
 */
public class FirstBorrowFragment extends Fragment {
  @BindView(R.id.fragment_borrow_first_bottom_button)
  Button borrow;

  @BindViews({
      R.id.borrow_select_bike_marker_1,
      R.id.borrow_select_bike_marker_2,
      R.id.borrow_select_bike_marker_3,
      R.id.borrow_select_bike_marker_5,
      R.id.borrow_select_bike_marker_7
  })
  List<ImageView> ivMarkers;

  @BindViews({
      R.id.borrow_select_bike_cradle_and_bike_1,
      R.id.borrow_select_bike_cradle_and_bike_2,
      R.id.borrow_select_bike_cradle_and_bike_3,
      R.id.borrow_select_bike_cradle_and_bike_5,
      R.id.borrow_select_bike_cradle_and_bike_7
  })
  List<ImageView> ivBikeAndCradles;

  @BindViews({
      R.id.borrow_select_bike_tv_1,
      R.id.borrow_select_bike_tv_2,
      R.id.borrow_select_bike_tv_3,
      R.id.borrow_select_bike_tv_5,
      R.id.borrow_select_bike_tv_7
  })
  List<TextView> tvNumbers;

  @BindViews({
      R.id.borrow_select_bike_rl_1,
      R.id.borrow_select_bike_rl_2,
      R.id.borrow_select_bike_rl_3,
      R.id.borrow_select_bike_rl_5,
      R.id.borrow_select_bike_rl_7
  })
  List<RelativeLayout> rls;

  private int clicked = 2;

  private Unbinder unbinder;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_borrow_first, container, false);
    unbinder = ButterKnife.bind(this, view);

    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    MainActivity.borrowBackground.into((ImageView) getActivity().findViewById(R.id.bill_layout));
    // Hide fab.
    getActivity().findViewById(R.id.fab).setVisibility(View.INVISIBLE);
  }

  @OnClick(R.id.fragment_borrow_first_bottom_button)
  void onBorrow() {
    // Show complete page.
    getActivity()
        .getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.framelayout_main,
            new CompleteBorrowFragment(),
            CompleteBorrowFragment.class.getSimpleName())
        .addToBackStack("BORROW_COMPLETE_STEP").commit();
  }

  @OnClick({
      R.id.borrow_select_bike_rl_1,
      R.id.borrow_select_bike_rl_2,
      R.id.borrow_select_bike_rl_3,
      R.id.borrow_select_bike_rl_5,
      R.id.borrow_select_bike_rl_7
  })
  void onBikeSelect(View view) {
    final DrawableTypeRequest<Integer> markerRequest = Glide.with(getActivity())
        .load(R.drawable.borrow_bike_select_marker);
    final DrawableTypeRequest<Integer> selectedRequest = Glide.with(getActivity())
        .load(R.drawable.borrow_bike_selected);
    final DrawableTypeRequest<Integer> notSelectedRequest = Glide.with(getActivity())
        .load(R.drawable.borrow_bike_not_selected);

    // Delete image of marker.
    if (clicked != -1) {
      ivMarkers.get(clicked).setVisibility(View.INVISIBLE);
      tvNumbers.get(clicked).setTextColor(getResources().getColor(R.color.byciclover_white));
      tvNumbers.get(clicked).setPadding(0, 9, 0, 0);
      notSelectedRequest.into(ivBikeAndCradles.get(clicked));
    }

    switch (view.getId()) {
      case R.id.borrow_select_bike_rl_1:
        clicked = 0;
        break;
      case R.id.borrow_select_bike_rl_2:
        clicked = 1;
        break;
      case R.id.borrow_select_bike_rl_3:
        clicked = 2;
        break;
      case R.id.borrow_select_bike_rl_5:
        clicked = 3;
        break;
      case R.id.borrow_select_bike_rl_7:
        clicked = 4;
        break;
    }

    markerRequest.into(ivMarkers.get(clicked));
    ivMarkers.get(clicked).setVisibility(View.VISIBLE);
    selectedRequest.into(ivBikeAndCradles.get(clicked));
    tvNumbers.get(clicked).setTextColor(getResources().getColor(R.color.byciclover_main_text));
    tvNumbers.get(clicked).setPadding(0, 6, 0, 0);
  }
}
