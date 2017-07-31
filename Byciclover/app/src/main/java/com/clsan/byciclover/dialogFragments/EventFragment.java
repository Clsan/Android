package com.clsan.byciclover.dialogFragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.clsan.byciclover.R;
import com.clsan.byciclover.fragments.GoogleMapFragment;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import lombok.Setter;

/**
 * Created by clsan on 05/06/2017.
 */
public class EventFragment extends Fragment {
  @Setter
  private int eventId;

  @BindView(R.id.fragment_event_ll)
  LinearLayout eventLL;

  @BindView(R.id.fragment_event_iv)
  ImageView eventIV;
  @BindView(R.id.fragment_event_tv)
  TextView eventTV;

  private Unbinder unbinder;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_event, container, false);
    this.unbinder = ButterKnife.bind(this, view);

    return view;
  }


  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    eventLL.setVisibility(View.VISIBLE);
    // Set image and text
    RequestManager imageRequestManager = Glide.with(this.getActivity());
    DrawableTypeRequest<Integer> drawableTypeRequest = null;
    switch (this.eventId) {
      case 0:
        drawableTypeRequest = imageRequestManager.load(R.drawable.event_0);
        eventTV.setText(Html.fromHtml("오늘 한강공원 <b>불꽃놀이</b>하는데...<br>넌 볼 사람이 없네..."));
        break;
      case 1:
        drawableTypeRequest = imageRequestManager.load(R.drawable.event_1);
        eventTV.setText(Html.fromHtml("<b>도깨비 야시장</b> 있어요<br>자기야 우리 잠시 쉬었다 갈까?"));
        break;
      case 2:
        drawableTypeRequest = imageRequestManager.load(R.drawable.event_2);
        eventTV.setText(Html.fromHtml("잠깐 쉬었다 가세요~<br>강 너머로<br><b>참치타워</b>가 보인답니다!"));
        break;
      default:
        return;
    }
    drawableTypeRequest.into(eventIV);


    final Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        eventLL.setVisibility(View.INVISIBLE);
        ((GoogleMapFragment) getActivity()
            .getSupportFragmentManager()
            .findFragmentByTag(GoogleMapFragment.RETURN))
            .afterEvent(eventId);
        onDetach();
      }
    }, 3000);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}