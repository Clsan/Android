package com.clsan.byciclover.dialogFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.clsan.byciclover.R;
import com.clsan.byciclover.fragments.GoogleMapFragment;
import com.google.android.gms.maps.model.Marker;
import java.util.List;
import lombok.Setter;

/**
 * Created by clsan on 28/05/2017.
 */
public class NavigateDialogFragment extends Fragment {
  @Setter
  private Marker marker;

  @BindView(R.id.navigate_dialog_layout)
  LinearLayout layout;
  @BindViews({R.id.navigate_button_agree_fda, R.id.navigate_button_disagree_fda})
  List<Button> agreementButtons;

  private Unbinder unbinder;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_dialog_navigate, container, false);
    unbinder = ButterKnife.bind(this, view);

    return view;
  }


  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    // TODO:회색 opacity와 색 코드 받기
    // layout.setBackgroundResource(R.drawable.opacity_background);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({R.id.navigate_button_agree_fda, R.id.navigate_button_disagree_fda})
  public void onAgreementButtonsClick(View view) {
    GoogleMapFragment googleMapFragment = (GoogleMapFragment) getActivity()
        .getSupportFragmentManager()
        .findFragmentByTag(GoogleMapFragment.BORROW);
    googleMapFragment.onNavigateSettle(view.getId() == R.id.navigate_button_agree_fda, this.marker);
    layout.setVisibility(View.GONE);
  }
}
