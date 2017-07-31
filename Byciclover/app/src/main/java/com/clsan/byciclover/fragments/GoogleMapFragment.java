package com.clsan.byciclover.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.clsan.byciclover.R;
import com.clsan.byciclover.activities.MainActivity;
import com.clsan.byciclover.dialogFragments.AgreementDialogFragment;
import com.clsan.byciclover.dialogFragments.EventFragment;
import com.clsan.byciclover.dialogFragments.NavigateDialogFragment;
import com.clsan.byciclover.dialogFragments.ReturnCompleteDialogFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import lombok.Getter;

/**
 * Created by clsan on 28/04/2017.
 */
public class GoogleMapFragment extends Fragment
    implements OnMapReadyCallback, OnMarkerClickListener
{
  // MAP STATE CONSTANT
  public static final String RETURN = "RETURN_STEP", BORROW = "BORROW_STEP";
  // COMMON CONSTANT
  private static final LatLngBounds LAT_LNG_BOUNDS_SEOUL = new LatLngBounds(
      new LatLng(37.20878777542828,126.685712300241),
      new LatLng(37.83641810384121,127.18009706586598)
  );
  private static final LatLng DEFAULT_LAT_LNG = new LatLng(37.525402, 126.930005);
  private static final LatLng DEFAULT_CURR_LAT_LNG = new LatLng(37.519906, 126.932971);
  private static final LatLng BIKE_MARKER_1_LAT_LNG = new LatLng(37.517691, 126.937401);
  private static final LatLng BIKE_MARKER_2_LAT_LNG = new LatLng(37.530566, 126.928382);
  private static final LatLng RESTROOM_LAT_LNG = new LatLng(37.527430, 126.934477);
  private static final LatLng WATER_LAT_LNG = new LatLng(37.524639, 126.935851);
  private static final LatLng CU_1_LAT_LNG = new LatLng(37.522597, 126.922032);
  private static final LatLng CU_2_LAT_LNG = new LatLng(37.520393, 126.928104);
  // BORROW CONSTANT
  private static final List<LatLng> BORROW_BIKE_MAKER_1_VIA = new ArrayList<>(
      Arrays.asList(DEFAULT_CURR_LAT_LNG,
          new LatLng(37.517644, 126.935120),
          BIKE_MARKER_1_LAT_LNG)
  );
  private static final List<LatLng> BORROW_BIKE_MAKER_2_VIA = new ArrayList<>(
      Arrays.asList(DEFAULT_CURR_LAT_LNG,
          new LatLng(37.527065, 126.925818),
          new LatLng(37.529143, 126.928831),
          BIKE_MARKER_2_LAT_LNG)
  );
  // RETURN CONSTANT
  private static final List<LatLng> RETURN_VIA = new ArrayList<>(
      Arrays.asList(new LatLng(37.523537, 126.933233),
          new LatLng(37.524797, 126.935196),
          new LatLng(37.526822, 126.932568),
          new LatLng(37.528243, 126.930626))
  );
  private static final List<LatLng> RETURN_BIKE_MAKER_1_VIA = new ArrayList<>(
      Arrays.asList(new LatLng(37.520977, 126.940133), BIKE_MARKER_1_LAT_LNG)
  );
  private static final List<LatLng> RETURN_BIKE_MAKER_2_VIA = new ArrayList<>(
      Arrays.asList(new LatLng(37.529932, 126.927923), BIKE_MARKER_2_LAT_LNG)
  );


  // COMMON VARIABLE
  public static boolean isAnimating = false; // Block critical action when animating
  private GroundOverlay groundOverlay; // Ground overlay convers all over the Seoul.

  @Getter
  public GoogleMap mMap;

  private String purpose;

  private MainActivity mainActivity;

  private Marker borrowMarker = null;
  boolean returnButtonClicked = false;
  private Bitmap bikeMarkerBitmap, currLocationMarkerBitmap, blackBackground, cu, restroom, water;

  private List<Marker> bikeMarkers = new ArrayList<>();
  private List<Marker> useless = new ArrayList<>();
  private Marker bikeMarker1, bikeMarker2, currLacationMarker;

  protected View view;

  private Location currLocation;

  // COMMON VIEW
  @BindViews({R.id.btn_curr_location, R.id.btn_headset})
  Button[] mapButtons;
  @BindView(R.id.google_map_title_box_ll)
  LinearLayout titleBoxLL;
  @BindView(R.id.google_map_title_tv)
  TextView titleTV;

  // RETURN VIEW
  @BindView(R.id.google_map_return_title_rl)
  RelativeLayout returnTitleRL;
  @BindView(R.id.google_map_return_title_down_button)
  ImageView returnTitleDownButton;
  @BindView(R.id.google_map_return_bill_rl)
  RelativeLayout returnBillRL;
  @BindView(R.id.google_map_return_button)
  Button returnButton;

  private Unbinder unbinder;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    int height = 135 * 13 / 10;
    int width = 107 * 13 / 10;
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_borrow);
    bikeMarkerBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_cu);
    cu = Bitmap.createScaledBitmap(bitmap, width, height, false);

    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_restroom);
    restroom = Bitmap.createScaledBitmap(bitmap, width, height, false);

    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_water);
    water = Bitmap.createScaledBitmap(bitmap, width, height, false);

    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curr_location_marker);
    currLocationMarkerBitmap = Bitmap.createScaledBitmap(bitmap, 47 * 3 / 2, 47 * 3 / 2, false);

    blackBackground = BitmapFactory.decodeResource(getResources(), R.drawable.black);
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    if (view != null) {
      ViewGroup parent = (ViewGroup) view.getParent();
      if (parent != null)
        parent.removeView(view);
    } else {
      view = inflater.inflate(R.layout.fragment_google_map, container, false);
    }

    if(mMap == null) {
      SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
          .findFragmentById(R.id.fragment_google_map);
      mapFragment.getMapAsync(this);
    }

    unbinder = ButterKnife.bind(this, view);

//    getActivity().findViewById(R.id.topPanel).setBackgroundResource(R.drawable.top_bar_green);
    return view;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    this.mainActivity = (MainActivity) getActivity();

    // Set state of map.
    this.purpose = this.getTag();

    // Process view by state.
    switch (this.purpose) {
      case BORROW:
        this.mainActivity.getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.framelayout_map,
                new AgreementDialogFragment(),
                AgreementDialogFragment.class.getSimpleName())
            .addToBackStack("AGREEMENT").commit();
        this.titleBoxLL.setVisibility(View.VISIBLE);
        break;

      case RETURN:
        Glide.with(getActivity())
            .load(R.drawable.down_arrow)
            .into(returnTitleDownButton);
        this.returnTitleRL.setVisibility(View.VISIBLE);
        Glide.with(getActivity())
            .load(R.drawable.return_bill_background)
            .into((ImageView) getActivity().findViewById(R.id.google_map_return_bill_background));

        this.titleTV.setText("반납할 대여소를 선택하세요.");
        break;

      default:
        throw new RuntimeException("Invalid purpose of map.");
    }
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    // Default settings for map.
    this.mMap = googleMap;
    this.mMap.getUiSettings().setMyLocationButtonEnabled(true);
    this.mMap.getUiSettings().setZoomControlsEnabled(false);
    this.mMap.getUiSettings().setMapToolbarEnabled(false);
    // When zoom level goes down, map covers much more space.
    this.mMap.moveCamera(CameraUpdateFactory.zoomTo(14.5f));
    this.mMap.setOnMarkerClickListener(this);

    // TODO(clsan) : 선택된 카드에 따라 올바르게 지도를 이동시킬 것.
    // Move camera by selected park. (SelectMapFragment.CURR_PAGE)
    // But, currently just move camera to yeouido park.
    this.mMap.moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_LAT_LNG));

    GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions()
        .image(BitmapDescriptorFactory.fromBitmap(blackBackground))
        .transparency(0.5f)
        .positionFromBounds(LAT_LNG_BOUNDS_SEOUL);
    groundOverlay = mMap.addGroundOverlay(groundOverlayOptions);

    // Add bike markers.
    this.bikeMarker1 = this.mMap.addMarker(new MarkerOptions()
        .position(BIKE_MARKER_1_LAT_LNG)
        .icon(BitmapDescriptorFactory.fromBitmap(this.bikeMarkerBitmap)));
    this.bikeMarker2 = this.mMap.addMarker(new MarkerOptions()
        .position(BIKE_MARKER_2_LAT_LNG)
        .icon(BitmapDescriptorFactory.fromBitmap(this.bikeMarkerBitmap)));
    this.bikeMarkers.add(this.bikeMarker1);
    this.bikeMarkers.add(this.bikeMarker2);

    // In Return step, user already agreed location policy.
    // So, it is okay to draw current location marker.
    if (this.purpose.equals(RETURN)) {
      this.currLacationMarker = this.mMap.addMarker(new MarkerOptions()
          .position(RETURN_VIA.get(0))
          .icon(BitmapDescriptorFactory.fromBitmap(this.currLocationMarkerBitmap)));
      groundOverlay.setVisible(false);

      Marker m1 = this.mMap.addMarker(new MarkerOptions()
          .position(RESTROOM_LAT_LNG)
          .icon(BitmapDescriptorFactory.fromBitmap(this.restroom)));
      Marker m2 = this.mMap.addMarker(new MarkerOptions()
          .position(WATER_LAT_LNG)
          .icon(BitmapDescriptorFactory.fromBitmap(this.water)));
      Marker m3 = this.mMap.addMarker(new MarkerOptions()
          .position(CU_1_LAT_LNG)
          .icon(BitmapDescriptorFactory.fromBitmap(this.cu)));
      Marker m4 = this.mMap.addMarker(new MarkerOptions()
          .position(CU_2_LAT_LNG)
          .icon(BitmapDescriptorFactory.fromBitmap(this.cu)));

      useless.addAll(Arrays.asList(m1, m2, m3, m4));

      // Marker 이동,,,
      isAnimating = true;
      animateMarker(this.currLacationMarker, RETURN_VIA.get(1), this.mainActivity, 0);
    }
  }

  // https://stackoverflow.com/questions/13728041/move-markers-in-google-map-v2-android
  private void animateMarker(
      final Marker marker,
      final LatLng toPosition,
      final MainActivity mainActivity,
      final int eventId
  ) {
    final Handler handler = new Handler();
    final long start = SystemClock.uptimeMillis();
    Projection proj = this.mMap.getProjection();
    Point startPoint = proj.toScreenLocation(marker.getPosition());
    final LatLng startLatLng = proj.fromScreenLocation(startPoint);
    final long duration = 5000;

    final Interpolator interpolator = new LinearInterpolator();

    handler.post(new Runnable() {
      @Override
      public void run() {
        long elapsed = SystemClock.uptimeMillis() - start;
        float t = interpolator.getInterpolation((float) elapsed
            / duration);
        double lng = t * toPosition.longitude + (1 - t)
            * startLatLng.longitude;
        double lat = t * toPosition.latitude + (1 - t)
            * startLatLng.latitude;
        marker.setPosition(new LatLng(lat, lng));

        if (t < 1.0) {
          // Post again 16ms later.
          handler.postDelayed(this, 16);
        } else {
          GoogleMapFragment.afterAnimateMarker(mainActivity, eventId);
        }
      }
    });
  }

  private static void afterAnimateMarker(MainActivity mainActivity, int eventId) {
    EventFragment eventFragment = new EventFragment();
    eventFragment.setEventId(eventId);

    if (isAnimating) {
      mainActivity.getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.framelayout_map,
              eventFragment,
              EventFragment.class.getSimpleName())
          .addToBackStack("EVENT")
          .commit();
    }
  }

  public void afterEvent(int eventId) {
    if (eventId == 2) {
      isAnimating = false;
      return;
    }
    eventId++;
    animateMarker(this.currLacationMarker,
        RETURN_VIA.get(eventId + 1),
        this.mainActivity, eventId);
  }

  public void moveCamera(LatLng latLng) {
    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
  }

  @OnClick({R.id.btn_curr_location, R.id.btn_headset})
  void onMapButtonClick(View view) {
    switch (view.getId()) {
      case R.id.btn_curr_location:
        if (mapButtons[0].getTag().toString() == "") {
          mapButtons[0].setBackgroundResource(R.drawable.map_curr_location_clicked);
          mapButtons[0].setTag("clicked");
        } else {
          mapButtons[0].setBackgroundResource(R.drawable.map_curr_location);
          mapButtons[0].setTag("");
        }
        break;

      case R.id.btn_headset:
        if (mapButtons[1].getTag().toString() == "") {
          mapButtons[1].setBackgroundResource(R.drawable.map_headset_clicked);
          mapButtons[1].setTag("clicked");
        } else {
          mapButtons[1].setBackgroundResource(R.drawable.map_headset);
          mapButtons[1].setTag("");
        }
        break;

      default:
        break;
    }
  }

  @Override
  public boolean onMarkerClick(Marker marker) {
    if (this.purpose.equals(BORROW)) {
      if (borrowMarker == null) {
        NavigateDialogFragment navigateDialogFragment = new NavigateDialogFragment();
        navigateDialogFragment.setMarker(marker);

        this.mainActivity
            .getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.framelayout_map,
                navigateDialogFragment,
                NavigateDialogFragment.class.getSimpleName())
            .addToBackStack("NAVIGATE")
            .commit();
      } else {
        if (borrowMarker.getId().equals(marker.getId())) {
          this.mainActivity
              .getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.framelayout_main,
                  new FirstBorrowFragment(),
                  FirstBorrowFragment.class.getSimpleName())
              .addToBackStack("BORROW_FIRST_STEP")
              .commit();
        }
      }
    } else if (this.purpose.equals(RETURN) && returnButtonClicked) {
      groundOverlay.setVisible(false);
      // Navigate return path
      // Default PolylineOptions
      PolylineOptions polylineOptions = new PolylineOptions()
          .width(20)
          .color(getResources().getColor(R.color.byciclover_green_line))
          .add(currLacationMarker.getPosition());

      // Navigate path by selected marker.
      if (marker.getId().equals(this.bikeMarker1.getId())) {
        for (LatLng latLng : RETURN_BIKE_MAKER_1_VIA) {
          polylineOptions.add(latLng);
        }
      } else if (marker.getId().equals(this.bikeMarker2.getId())) {
        for (LatLng latLng : RETURN_BIKE_MAKER_2_VIA) {
          polylineOptions.add(latLng);
        }
      }

      // Draw line.
      mMap.addPolyline(polylineOptions);
      titleBoxLL.setVisibility(View.INVISIBLE);

      TimerTask task = new TimerTask(){
        public void run() {
          try {
            getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout_map,
                    new ReturnCompleteDialogFragment(),
                    ReturnCompleteDialogFragment.class.getSimpleName())
                .addToBackStack("RETURN_COMPLETE").commit();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      };
      Timer mTimer = new Timer();
      mTimer.schedule(task, 3000);
// 3초후 run을 실행하고 종료 timer.schedule(task, 3000);
// 10초후 run을 실행하고 매3초마다 실행 timer.schedule(task, 10000, 3000);
    }
    return false;
  }

  public void onAgreementSettle(boolean isAgreed) {
    titleBoxLL.setVisibility(View.VISIBLE);

    // Curr location : 37.519906, 126.932971
    mMap.addMarker(new MarkerOptions()
        .position(new LatLng(37.519906, 126.932971))
        .icon(BitmapDescriptorFactory.fromBitmap(currLocationMarkerBitmap)));
  }

  public void onNavigateSettle(boolean isAgreed, Marker marker) {
    if (!isAgreed) {
      return;
    }

    groundOverlay.setVisible(false);
    this.borrowMarker = marker;

    // Default PolylineOptions
    PolylineOptions polylineOptions = new PolylineOptions()
        .width(20)
        .color(getResources().getColor(R.color.byciclover_green_line));

    // Navigate path by selected marker.
    if (marker.getId().equals(this.bikeMarker1.getId())) {
      for (LatLng latLng : BORROW_BIKE_MAKER_1_VIA) {
        polylineOptions.add(latLng);
      }
    } else if (marker.getId().equals(this.bikeMarker2.getId())) {
      for (LatLng latLng : BORROW_BIKE_MAKER_2_VIA) {
        polylineOptions.add(latLng);
      }
    }

    // Draw line.
    mMap.addPolyline(polylineOptions);
  }

  @OnClick(R.id.google_map_return_title_down_button)
  void onReturnDownButtonClick() {
    if (isAnimating) {
      return;
    }
    int visibility = returnBillRL.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;
    returnBillRL.setVisibility(visibility);
  }

  @OnClick(R.id.google_map_return_button)
  void onReturnButtonClick() {
    groundOverlay.setVisible(true);
    returnButtonClicked = true;
    returnTitleRL.setVisibility(View.INVISIBLE);
    returnBillRL.setVisibility(View.INVISIBLE);
    titleTV.setVisibility(View.VISIBLE);
    titleBoxLL.setVisibility(View.VISIBLE);

    for (Marker m : useless) {
      m.setVisible(false);
    }
  }
}
