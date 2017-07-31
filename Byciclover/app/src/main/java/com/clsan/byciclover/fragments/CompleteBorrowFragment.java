package com.clsan.byciclover.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.clsan.byciclover.R;
import com.clsan.byciclover.activities.MainActivity;

/**
 * Created by clsan on 03/06/2017.
 */
public class CompleteBorrowFragment extends Fragment {
  @BindView(R.id.fragment_borrow_complete_bottom_button)
  Button borrow;

  private Unbinder unbinder;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_borrow_complete, container, false);
    unbinder = ButterKnife.bind(this, view);

    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Glide.with(getActivity()).load(R.drawable.borrow_complete_background).into((ImageView) getActivity().findViewById(R.id.borrow_complete_background));
  }

  @OnClick(R.id.fragment_borrow_complete_bottom_button)
  void onBorrowComplete() {
    // Show complete page.
    getActivity()
        .getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.framelayout_main,
            new GoogleMapFragment(),
            "RETURN_STEP")
        .addToBackStack("RETURN_STEP").commit();
    getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
  }
}
