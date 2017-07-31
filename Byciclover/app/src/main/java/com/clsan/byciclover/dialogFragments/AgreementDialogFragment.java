package com.clsan.byciclover.dialogFragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import java.util.List;

/**
 * Created by yoon on 2016. 10. 31..
 */
public class AgreementDialogFragment extends Fragment {
  private Unbinder unbinder;

  @BindView(R.id.dialog_layout)
  LinearLayout layout;

  @BindViews({R.id.button_agree_fda, R.id.button_disagree_fda})
  List<Button> agreementButtons;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_dialog_agreement, container, false);
    unbinder = ButterKnife.bind(this, view);

    return view;
  }


  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({R.id.button_agree_fda, R.id.button_disagree_fda})
  public void onAgreementButtonsClick(View view) {
    GoogleMapFragment googleMapFragment = (GoogleMapFragment) getActivity()
        .getSupportFragmentManager()
        .findFragmentByTag(GoogleMapFragment.BORROW);
    googleMapFragment.onAgreementSettle(view.getId() == R.id.button_agree_fda);
    layout.setVisibility(View.GONE);
  }
}
