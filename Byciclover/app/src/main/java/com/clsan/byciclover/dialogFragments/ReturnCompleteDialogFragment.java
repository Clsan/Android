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
import java.util.List;

/**
 * Created by clsan on 04/06/2017.
 */
public class ReturnCompleteDialogFragment extends Fragment {
  private Unbinder unbinder;

  @BindView(R.id.return_dialog_layout)
  LinearLayout layout;

  @BindView(R.id.return_dialog_button)
  Button confirmButton;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_dialog_complete_return, container, false);
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

  @OnClick(R.id.return_dialog_button)
  public void onConfirmButtonClick() {
    layout.setVisibility(View.GONE);
    getActivity().onBackPressed();
  }
}